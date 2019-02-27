package com.nuoxin.virtual.rep.api.service;

import java.text.SimpleDateFormat;
import java.util.*;

import com.nuoxin.virtual.rep.api.entity.v2_5.VirtualWechatDate;
import com.nuoxin.virtual.rep.api.entity.v2_5.VirtualWechatVisitCountAndCycleConfigParams;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.nuoxin.virtual.rep.api.dao.DrugUserRepository;
import com.nuoxin.virtual.rep.api.entity.DrugUser;
import com.nuoxin.virtual.rep.api.entity.ProductLine;
import com.nuoxin.virtual.rep.api.mybatis.ProductLineMapper;
import com.nuoxin.virtual.rep.api.web.controller.response.product.ProductResponseBean;

/**
 * Created by fenggang on 8/4/17.
 */
@Service
public class ProductLineService {

    @Autowired
    private ProductLineMapper productLineMapper;
    @Autowired
    private DrugUserRepository drugUserRepository;

    /**
     * 获取产品
     * @param productId
     * @return
     */
    @Cacheable(value = "virtual_app_web_product", key = "'_'+#productId")
    public ProductLine findById(Long productId){
        ProductLine productLine = new ProductLine();
        productLine.setId(productId);
        return productLineMapper.selectOne(productLine);
    }

    /**
     * 获取产品id集合
     * @param leaderPath
     * @return
     */
    @Cacheable(value = "virtual_app_web_product", key = "'_ids_'+#leaderPath")
    public List<Long> getProductIds(String leaderPath){
        return productLineMapper.getProductIds(leaderPath);
    }

    /**
     * 获取产品集合
     * @param drugUserId
     * @return
     */
    public List<ProductResponseBean> getList(Long drugUserId, Long doctorId){
        DrugUser drugUser = drugUserRepository.findFirstById(drugUserId);
        String leaderPath = drugUser.getLeaderPath();
        if (leaderPath == null){
            leaderPath = "";
        }
        leaderPath = leaderPath + "%";

        return productLineMapper.getList(leaderPath, doctorId);
    }

    /**
     * 在客户聊天记录中，当前代表在过去N天内是否与医生有微信聊天记录
     * @param drugUserId
     * @param doctorId
     * @param dayNum
     * @return count
     */
    public Integer wechatChatRecordIsExist(Long drugUserId, Long doctorId, Integer dayNum){
        return productLineMapper.wechatChatRecordIsExist(drugUserId,doctorId,dayNum);
    }

    /**
     * 一个自然天内，存在多少条微信的拜访记录
     * @param drugUserId
     * @param doctorId
     * @return count
     */
    public Integer wechatVisitLogCountOneDay(Long drugUserId, Long doctorId){
        return productLineMapper.wechatVisitLogCountOneDay(drugUserId,doctorId);
    }

    /**
     * 微信聊天记录天数和一天内可添加微信拜访记录的配置表
     * @return VirtualWechatVisitCountAndCycleConfigParams
     */
    public VirtualWechatVisitCountAndCycleConfigParams virtualWechatVisitCountAndCycleConfig ()
    {
        return productLineMapper.virtualWechatVisitCountAndCycleConfig();
    }

    /**
     * 获取N天内的所有日期，有微信聊天记录的日期，有微信拜访的日期
     * @param drugUserId
     * @param doctorId
     * @return String
     */
    public String wechatIsExistDateList(Long drugUserId, Long doctorId, Integer dayNum){

        SimpleDateFormat sdf  = new SimpleDateFormat("yyyy-M-d");

        //在客户聊天记录中，当前代表在过去N天内是否与医生有微信聊天记录的日期 type 1
        List<VirtualWechatDate> wechatChatRecordDateList = productLineMapper.wechatChatRecordDate(drugUserId, doctorId, dayNum);
        //当前代表在过去N天内是否存在微信拜访记录日期 type 2
        List<VirtualWechatDate> wechatVisitLogsDateList = productLineMapper.wechatVisitLogsDate(drugUserId, doctorId, dayNum);

        //获取时间区间内的所有日期
        Map<String, Integer> dateMaps = getDayMaps(dayNum);

        //keySet获取map集合key的集合  然后在遍历key即可
        for (String key:dateMaps.keySet()){
            //判断更新微信聊天记录的日期在区间日期内的状态
            for (int i = 0; i<wechatChatRecordDateList.size(); i++){
                if(key.equals(wechatChatRecordDateList.get(i).getExistDate())){
                    dateMaps.put(key,1);
                }
            }

            //判断更新微信拜访记录的日期在区间日期内的状态
            for (int i = 0; i<wechatVisitLogsDateList.size(); i++){
                if(key.equals(wechatVisitLogsDateList.get(i).getExistDate())){
                    dateMaps.put(key,2);
                }
            }
        }

        //org.json.JSONObject 将Map转换为JSON方法
        JSONObject jsonObject =new JSONObject(dateMaps);
        String json = jsonObject.toString();
        return json;
    }

    /**
     * 获取当前日期前30天的日期
     */
    public static Map<String, Integer> getDayMaps(Integer dayNum){
        SimpleDateFormat sdf  = new SimpleDateFormat("yyyy-M-d");
        try {
            Calendar calc =Calendar.getInstance();
            Map<String, Integer> bloodMap = new HashMap<String, Integer>();
            for(int i=0;i<dayNum;i++){
                calc.setTime(new Date());
                calc.add(Calendar.DATE, -i);
                Date minDate = calc.getTime();
                bloodMap.put(sdf.format(minDate), 0);
            }
            return bloodMap;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
