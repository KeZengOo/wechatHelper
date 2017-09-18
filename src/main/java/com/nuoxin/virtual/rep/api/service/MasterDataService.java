package com.nuoxin.virtual.rep.api.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.nuoxin.virtual.rep.api.common.bean.WebResult;
import com.nuoxin.virtual.rep.api.config.CityJsonConfig;
import com.nuoxin.virtual.rep.api.web.controller.response.vo.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 主数据相关接口
 * Created by tiancun on 17/8/1.
 */
@Service
public class MasterDataService {

    private static Logger logger = LoggerFactory.getLogger(MasterDataService.class);

    @Value("${master.data.prefix.url}")
    private String prefixUrl;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private CityJsonConfig cityJsonConfig;


    /**
     * 根据医生id得到医生
     * @param hcpId 主数据医生id
     * @return 主数据医生
     */
    @Cacheable(value = "virtual_rep_api_master_data", key="'getHcpById_'+#hcpId" )
    public Hcp getHcpById(Long hcpId){
        Hcp hcp = null;

        WebResult result = restTemplate.getForObject(prefixUrl + "api/hcp/getById/{id}", WebResult.class, hcpId);
        long hciId = 0;
        if (null != result){
            int resultcode = result.getResultcode();
            if (resultcode == 200){
                Object data = result.getData();
                if (null != data){
                    JSON json = (JSON) JSON.toJSON(data);
                    hcp = JSON.toJavaObject(json, Hcp.class);
                    if (null != hcp){
                        return hcp;
                    }
                }
            }else {
                logger.error("getHcpById of MasterDataService error!!!  args hcpId="+ hcpId +" resultcode =" +  result.getResultcode()
                        + " msg=" + result.getMsg() + " data=" + result.getData());
            }
        }



        return hcp;

    }


    /**
     * 根据主数据医院id和医生姓名得到主数据医生
     * @param hciId 主数据医院id
     * @param name 主数据医生姓名
     * @return
     */
    @Cacheable(value = "virtual_rep_api_master_data", key="'getHcpByHciIdAndHcpName'+#hciId+#name" )
    public Hcp getHcpByHciIdAndHcpName(Long hciId, String name){
        Hcp hcp = null;

        WebResult hcpResult = restTemplate.getForObject(prefixUrl + "api/hcp/getByHciIdAndName?hciId={id}&name={name}", WebResult.class,hciId,name);
        if(hcpResult != null){
            int hcpResultCode = hcpResult.getResultcode();
            if(hcpResultCode == 200){
                Object data = hcpResult.getData();
                if(data != null){
                    JSON json = (JSON) JSON.toJSON(data);
                    if(json != null){
                        hcp = JSON.toJavaObject(json, Hcp.class);
                    }
                }
            }else{
                logger.error("getHcpByHciIdAndHcpName of MasterDataService error!!!  args hciId="+ hciId + " name =" + name + " resultcode =" +  hcpResult.getResultcode()
                        + " msg=" + hcpResult.getMsg() + " data=" + hcpResult.getData());
            }
        }

        return hcp;

    }



    /**
     * 根据主数据医院id得到医院
     * @param hciId 主数据医院id
     * @return 主数据医院
     */
    @Cacheable(value = "virtual_rep_api_master_data", key="'getHciById'+#hciId" )
    public Hci getHciById(Long hciId){
        Hci hci = null;

        WebResult result = restTemplate.getForObject(prefixUrl + "api/hci/getById/{id}", WebResult.class, hciId);
        if (null != result){
            int resultcode = result.getResultcode();
            if (resultcode == 200){
                Object data = result.getData();
                if (null != data){
                    JSON json = (JSON) JSON.toJSON(data);
                    hci = JSON.toJavaObject(json, Hci.class);
                    if (null != hci){
                        return hci;
                    }
                }
            }else {
                logger.error("getHciById of MasterDataService error!!! args hciId= "+ hciId +" resultcode =" +  result.getResultcode()
                        + " msg=" + result.getMsg() + " data=" + result.getData());
            }
        }

        return hci;

    }

    /**
     * 根据主数据医院名称或者医院别名得到医院
     * @param name 主数据医院名称或者医院别名
     * @return
     */
    @Cacheable(value = "virtual_rep_api_master_data", key="'getHciByName'+#name" )
    public Hci getHciByName(String name){
        Hci hci = null;

        WebResult result = restTemplate.getForObject(prefixUrl + "api/hci/getByName?name={name}", WebResult.class, name);
        if (null != result){
            int resultcode = result.getResultcode();
            if (resultcode == 200){
                Object data = result.getData();
                if (null != data){
                    JSON json = (JSON) JSON.toJSON(data);
                    hci = JSON.toJavaObject(json, Hci.class);
                    if (null != hci){
                        return hci;
                    }
                }
            }else {
                logger.error("getHciByName of MasterDataService error!!! args name= "+ name +" resultcode =" +  result.getResultcode()
                        + " msg=" + result.getMsg() + " data=" + result.getData());
            }
        }

        return hci;

    }




    /**
     * 根据主数据医生id得到医生的社会信息map
     * @param hcpId 主数据医生id
     * @return 返回的主数据医生的社会信息map
     */
    @Cacheable(value = "virtual_rep_api_master_data", key="'getHcpInfo_'+#hcpId" )
    public Map<String,String> getMapHcpInfo(Long hcpId){
        Map<String, String> map = new HashMap<>();
        WebResult result = restTemplate.getForObject( prefixUrl + "api/hcp/hcpInfo/{id}", WebResult.class, hcpId);
        if (result != null){
            int resultcode = result.getResultcode();
            if (resultcode == 200){
                Object data = result.getData();
                if (data != null){
                    JSONObject json = (JSONObject) JSON.toJSON(data);
                    if (json != null) {
                        //目前是有9中标签
                        //好大夫门诊量
                        String inline_inquiry_num = (String) json.get("inline_inquiry_num");
                        //好大夫门诊量趋势图
                        String inline_inquiry_map = (String) json.get("inline_inquiry_map");
                        //H-index
                        String hcp_H_index = (String) json.get("hcp_H_index");
                        //G-index
                        String hcp_G_index = (String) json.get("hcp_G_index");
                        //投票数
                        String votesCount = (String) json.get("votesCount");
                        //感谢数
                        String thanksCount = (String) json.get("thanksCount");
                        //预约量
                        String reservationsCount = (String) json.get("reservationsCount");
                        //综合评分
                        String recommendIndex = (String) json.get("recommendIndex");
                        //挂号网门诊量
                        String guahao_inline_inquiry_num = (String) json.get("guahao_inline_inquiry_num");


                        map.put("inline_inquiry_num", inline_inquiry_num);
                        map.put("inline_inquiry_map", inline_inquiry_map);
                        map.put("hcp_H_index", hcp_H_index);
                        map.put("hcp_G_index", hcp_G_index);
                        map.put("votesCount", votesCount);
                        map.put("thanksCount", thanksCount);
                        map.put("reservationsCount", reservationsCount);
                        map.put("recommendIndex", recommendIndex);
                        map.put("guahao_inline_inquiry_num", guahao_inline_inquiry_num);

                    }
                }
            }else {
                logger.error("getMapHcpInfo of MasterDataService error!!! args hcpId= "+ hcpId +" resultcode =" +  result.getResultcode()
                        + " msg=" + result.getMsg() + " data=" + result.getData());
            }
        }

        return map;
    }

    /**
     * 根据主数据的医生id得到医生的学术信息
     * @param hcpId 主数据医生id
     * @return 主数据医生的学术类
     */
    @Cacheable(value = "virtual_rep_api_master_data", key = "'getHcpResearchInfo_'+#hcpId" )
    public HcpResearchInfo getHcpResearchInfo(Long hcpId){
        HcpResearchInfo hcpResearchInfo = null;
        WebResult result = restTemplate.getForObject(prefixUrl + "api/hcp/research/{id}", WebResult.class,hcpId);
        if(result != null){
            int researchInfoCode = result.getResultcode();
            if(researchInfoCode == 200){
                Object data = result.getData();
                if(data != null){
                    JSON json = (JSON) JSON.toJSON(data);
                    hcpResearchInfo = JSON.toJavaObject(json, HcpResearchInfo.class);
                    if(hcpResearchInfo != null){
                        return hcpResearchInfo;
                    }
                }
            }else {
                logger.error("getHcpResearchInfo of MasterDataService error!!! args hcpId= "+ hcpId +" resultcode =" +  result.getResultcode()
                        + " msg=" + result.getMsg() + " data=" + result.getData());
            }
        }
        return hcpResearchInfo;
    }



    /**
     * 根据主数据医生id得到论文关键词(没有分页分页)
     * @param hcpId 主数据医生id
     * @return 论文关键词列表
     */
    @Cacheable(value = "virtual_rep_api_master_data", key = "'getDocKeyWordPage'+#hcpId")
    public List<DocKeyWord> getDocKeyWordList(Long hcpId){
        List<DocKeyWord> list = new ArrayList<>();
        DocKeyWordPage docKeyWordPage = new DocKeyWordPage();
        WebResult result = restTemplate.getForObject(prefixUrl + "api/dockeyword/getListByHcpId?hcpId={id}", WebResult.class,hcpId);
        if(result != null){
            int resultcode = result.getResultcode();
            if(resultcode == 200){
                Object data = result.getData();
                if(data != null){
                    JSON json = (JSON) JSON.toJSON(data);
                    if(json != null){
                        List<DocKeyWord> docKeyWordList = JSON.parseArray(json.toJSONString(), DocKeyWord.class);
                        if (docKeyWordList != null && docKeyWordList.size() > 0){
                            return docKeyWordList;
                        }
                    }
                }
            }else {
                logger.error("getDocKeyWordList of MasterDataService error!!! args hcpId= "+ hcpId +" resultcode =" +  result.getResultcode()
                        + " msg=" + result.getMsg() + " data=" + result.getData());
            }
        }

        return list;
    }

    /**
     * 根据主数据医生id得到论文关键词(分页)
     * @param hcpId 主数据医生id
     * @param page 当前页
     * @param pageSize 每页的数量
     * @return
     */
    @Cacheable(value = "virtual_rep_api_master_data", key = "'getDocKeyWordPage'+#hcpId+#page+#pageSize")
    public DocKeyWordPage getDocKeyWordPage(Long hcpId, int page,int pageSize){
        DocKeyWordPage docKeyWordPage = null;
        WebResult result = restTemplate.getForObject(prefixUrl + "api/dockeyword/getPageListByHcpId?hcpId={id}&offset={offset}&size={size}", WebResult.class,hcpId,page,pageSize);
        if(result != null){
            int resultcode = result.getResultcode();
            if(resultcode == 200){
                Object data = result.getData();
                if(data != null){
                    JSON json = (JSON) JSON.toJSON(data);
                    if(json != null){
                        DocKeyWordPage javaObject = JSON.toJavaObject(json, DocKeyWordPage.class);
                        if(javaObject !=null){
                            return javaObject;
                        }
                    }
                }
            }else {
                logger.error("getDocKeyWordPage of MasterDataService error!!! args hcpId= "+ hcpId
                        + " page=" + page
                        + " pageSize= " + pageSize
                        +" resultcode =" +  result.getResultcode()
                        + " msg=" + result.getMsg() + " data=" + result.getData());
            }
        }

        return docKeyWordPage;
    }


    /**
     * 根据多个主数据医生id得到论文关键词
     * @param hcpIds 医生id以英文逗号隔开
     * @return map
     */
    @Cacheable(value = "virtual_rep_api_master_data", key = "'getDocKeyWordList'+#hcpIds")
    public   Map<Long, List<DocKeyWord>> getDocKeyWordList(String hcpIds){
        Map<Long, List<DocKeyWord>> map  = null;
        WebResult result = restTemplate.getForObject(prefixUrl + "api/dockeyword/getListByHcpIds?hcpIds={hcpIds}", WebResult.class,hcpIds);
        if (null != result){
            int resultcode = result.getResultcode();
            if (resultcode == 200){
                Object data = result.getData();
                if (null != data){
                    JSON json = (JSON) JSON.toJSON(data);
                    if(json != null){
                        map =(Map<Long, List<DocKeyWord>>) JSON.parse(json.toJSONString());
                    }
                }
            } else{
                logger.error("getDocKeyWordList of MasterDataService error!!! args hcpIds= "+ hcpIds
                        +" resultcode =" +  result.getResultcode()
                        + " msg=" + result.getMsg() + " data=" + result.getData());
            }
        }

        return map;
    }



    /**
     * 根据医生id和关键词查询对话
     * @param hcpId 主数据医生id
     * @param key 对话关键词，多个以英文逗号分隔
     * @return
     */
    @Cacheable(value = "virtual_rep_api_master_data", key = "'getConsultDetailListByKey'+#hcpId")
    public List<ConsultDetail> getConsultDetailListByKey(Long hcpId, String key){
        List<ConsultDetail> list = new ArrayList<>();
        if (key == null){
            key = "";
        }

        WebResult result = restTemplate.getForObject(prefixUrl + "api/consult/getListByHcpIdAndKeys?hcpId={id}&keys={key}", WebResult.class,hcpId,key);
        if(result != null){
            int resultcode = result.getResultcode();
            if(resultcode == 200){
                Object data = result.getData();
                if(data != null){
                    JSON json = (JSON) JSON.toJSON(data);
                    if(json != null){
                        list = JSON.parseArray(json.toJSONString(), ConsultDetail.class);
                    }

                }
            }else {
                logger.error("getConsultDetailListByKey of MasterDataService error!!! args hcpId= "+ hcpId
                        + " key=" + key
                        +" resultcode =" +  result.getResultcode()
                        + " msg=" + result.getMsg() + " data=" + result.getData());
            }
        }

        return list;

    }


    /**
     * 根据医生id和关键词查询对话分页
     * @param hcpId 主数据医生id
     * @param key 对话关键词，多个以英文逗号隔开
     * @param page 当前页
     * @param pageSize 每页显示的数量
     * @return
     */
    @Cacheable(value = "virtual_rep_api_master_data", key = "'getConsultDetailListByKeyAndPage'+#hcpId+#key+#page+#pageSize")
    public ConsultDetailPage getConsultDetailListByKeyAndPage(Long hcpId, String key, int page, int pageSize){
        ConsultDetailPage consultDetailPage = null;

        if (key == null){
            key = "";
        }

        WebResult result = restTemplate.getForObject(prefixUrl + "api/consult/getPageListByHcpIdAndKeys?hcpId={id}&keys={key}&offset={offset}&size={size}", WebResult.class,hcpId,key,page,pageSize);
        if(result != null){
            int resultcode = result.getResultcode();
            if(resultcode == 200){
                Object data = result.getData();
                if(data != null){
                    JSON json = (JSON) JSON.toJSON(data);
                    if(json != null){
                        ConsultDetailPage javaObject = JSON.toJavaObject(json, ConsultDetailPage.class);
                        if(javaObject != null){
                            return javaObject;
                        }
                    }
                }
            }else {
                logger.error("getConsultDetailListByKeyAndPage of MasterDataService error!!! args hcpId= "+ hcpId
                        + " key=" + key
                        + " page=" + page
                        + " pageSize=" + pageSize
                        +" resultcode =" +  result.getResultcode()
                        + " msg=" + result.getMsg() + " data=" + result.getData());
            }
        }

        return consultDetailPage;
    }


    /**
     * 根据关键词得到医生的论文
     * @param hcpId 主数据医生id
     * @param keys 论文关键词，多个以英文逗号隔开
     * @param pageSize 每页的数量
     * @param page 当前页
     * @return
     */
    @Cacheable(value = "virtual_rep_api_master_data", key = "'getHcpDocListByKeys'+#hcpId+#keys+#page+#pageSize")
    public KeyWordDoc getHcpDocListByKeys(Long hcpId,String keys, int page,int pageSize){
        KeyWordDoc keyWordDoc = null;
        if (keys == null){
            keys = "";
        }
        WebResult result = restTemplate.getForObject(prefixUrl + "api/doc/getPageListByHcpKeys?hcpId={id}&keys={keys},&offset={offset}&size={size}", WebResult.class,hcpId,keys,page,pageSize);
        if(result != null){
            int resultcode = result.getResultcode();
            if(resultcode == 200){
                Object data = result.getData();
                if(data != null){
                    JSON json = (JSON) JSON.toJSON(data);
                    if(json != null){
                        keyWordDoc = JSON.toJavaObject(json, KeyWordDoc.class);
                        if(keyWordDoc != null){
                            return keyWordDoc;
                        }
                    }
                }
            }else {
                logger.error("getHcpDocListByKeys of MasterDataService error!!! args hcpId= "+ hcpId
                        + " keys=" + keys
                        + " page=" + page
                        + " pageSize=" + pageSize
                        +" resultcode =" +  result.getResultcode()
                        + " msg=" + result.getMsg() + " data=" + result.getData());
            }
        }

        return keyWordDoc;
    }



    /**
     * 根据杂志id得到杂志
     * @param id 主数据杂志id
     * @return
     */
    @Cacheable(value = "virtual_rep_api_master_data", key = "'getMagazine'+#id")
    public Magazine getMagazine(Long id){
        Magazine magazine = null;
        WebResult magazineResult = restTemplate.getForObject(prefixUrl + "api/magazine/getById/{id}", WebResult.class,id);
        if(magazineResult != null){
            int magazineCode = magazineResult.getResultcode();
            if(magazineCode == 200){
                Object data = magazineResult.getData();
                if(data != null){
                    JSON json = (JSON) JSON.toJSON(data);
                    if(json != null){
                        magazine = JSON.toJavaObject(json, Magazine.class);
                        if(magazine != null){
                            return magazine;
                        }
                    }
                }
            }else {
                logger.error("getHcpDocListByKeys of MasterDataService error!!! args id= "+ id

                        +" resultcode =" +  magazineResult.getResultcode()
                        + " msg=" + magazineResult.getMsg() + " data=" + magazineResult.getData());
            }
        }

        return magazine;
    }


    /**
     * 首页主要统计数据（医院总数、医生总数、医院等级分组、医生职称分组）
     * @param province 省份
     * @return
     */
    @Cacheable(value = "virtual_rep_api_master_data", key = "'getIndexStatistics'+#province")
    public IndexStatistics getIndexStatistics(String province){
        province = cityJsonConfig.checkProvince(province);
        IndexStatistics indexStatistics = null;
        if (province == null){
            province = "";
        }

        WebResult result = restTemplate.getForObject(prefixUrl + "api/statistic/getTotalInfoByProvince?province={province}", WebResult.class,province);
        if (null != result){
            int resultcode = result.getResultcode();
            if (resultcode == 200){
                Object data = result.getData();
                if (null != data){
                    JSON json = (JSON) JSON.toJSON(data);
                    indexStatistics = JSON.toJavaObject(json, IndexStatistics.class);
                }
            }else {
                logger.error("getIndexStatistics of MasterDataService error!!! args province= "+ province

                        +" resultcode =" +  result.getResultcode()
                        + " msg=" + result.getMsg() + " data=" + result.getData());
            }
        }


        return indexStatistics;
    }


    /**
     * 首页地图上有数据省份
     * @return
     */
    @Cacheable(value = "virtual_rep_api_master_data", key = "getMapProvinceStatisticsList")
    public List<MapProvinceStatistics> getMapProvinceStatisticsList(){
        List<MapProvinceStatistics> list = new ArrayList<>();

        WebResult result = restTemplate.getForObject(prefixUrl + "api/hci/getGroupCountByProvince", WebResult.class);
        if (null != result){
            int resultcode = result.getResultcode();
            if (resultcode == 200){
                Object data = result.getData();
                if (null != data){
                    JSON json =(JSON) JSON.toJSON(data);
                    list = JSON.parseArray(json.toJSONString(), MapProvinceStatistics.class);
                }
            }else {
                logger.error("getMapProvinceStatisticsList of MasterDataService error!!!  "
                        +" resultcode =" +  result.getResultcode()
                        + " msg=" + result.getMsg() + " data=" + result.getData());
            }
        }


        return list;
    }


    /**
     * 根据条件查询医院
     * @param name 医院名称
     * @param province 省份
     * @param city 城市
     * @param grade 医院等级
     * @param dept 科室
     * @param offset 分页偏移量
     * @param size 分页数量
     * @return
     */
    @Cacheable(value = "virtual_rep_api_master_data", key = "'getHciPage'+#name+#province+#city+#grade+#dept+#offset+#size")
    public HciStatisticsPage getHciPage(String name, String province, String city, Integer grade, String dept, Integer offset, Integer size){
        HciStatisticsPage hciStatisticsPage = null;
        province = cityJsonConfig.checkProvince(province);
        if (name == null){
            name = "";
        }

        if (province == null){
            province = "";
        }

        if (city == null){
            city = "";
        }

        if (grade == null){
            grade = -1;
        }

        if (dept == null){
            dept = "";
        }


        WebResult result = restTemplate.getForObject(prefixUrl + "api/hci/getPageListByParam?name={name}&province={province}&city={city}&grade={grade}&dept={dept}&offset={offset}&size={size}",
                WebResult.class, name, province, city, grade, dept, offset, size);
        if (null != result){
            int resultcode = result.getResultcode();
            if (resultcode == 200){
                Object data = result.getData();
                if (null != data){
                    JSON json = (JSON) JSON.toJSON(data);
                    hciStatisticsPage = JSON.toJavaObject(json, HciStatisticsPage.class);
                }
            }else {
                logger.error("getHciStatisticsPage of MasterDataService error!!!  args= "
                        + " name= " + name
                        + " province " + province
                        + " city " + city
                        + " grade " + grade
                        + " dept " + dept
                        + " offset " + offset
                        + " size " + size
                        +" resultcode =" +  result.getResultcode()
                        + " msg=" + result.getMsg() + " data=" + result.getData());
            }
        }


        return hciStatisticsPage;
    }



    /**
     * 根据医生名称或者医院id或者科室或者医生级别得到医生列表
     * @param name 主数据医生名称
     * @param hciId 主数据医院id
     * @param levelName 医生级别
     * @param dept 医生科室
     * @param offset
     * @param size
     * @return
     */
    @Cacheable(value = "virtual_rep_api_master_data", key = "'getHcpTitlePage'+#name+#hciId+#levelName+#dept+#offset+#size")
    public HcpTitlePage getHcpTitlePage(String name, Long hciId, String levelName, String dept, Integer offset, Integer size){
        HcpTitlePage hcpTitlePage = null;

        if (name == null){
            name = "";
        }

        if (hciId == null){
            hciId = -1L;
        }

        if (levelName == null){
            levelName = "";
        }

        if (dept == null){
            dept = "";
        }

        WebResult result = restTemplate.getForObject(prefixUrl + "api/hcp/getPageListByParam?name={name}&hciId={id}&title={title}&dept={dept}&offset={offset}&size={size}", WebResult.class, name, hciId, levelName, dept, offset, size);
        if (null != result){
            int resultcode = result.getResultcode();
            if (resultcode == 200){
                Object data = result.getData();
                if (null != data){
                    JSON json = (JSON) JSON.toJSON(data);
                    if (null != json){
                        hcpTitlePage = JSON.toJavaObject(json, HcpTitlePage.class);
                    }
                }
            }else {
                logger.error("getHcpTitlePage of MasterDataService error!!!  args= "
                        + " name= " + name
                        + " hciId= " + hciId
                        + " levelName= " + levelName
                        + " dept= " + dept
                        + " offset " + offset
                        + " size " + size
                        +" resultcode =" +  result.getResultcode()
                        + " msg=" + result.getMsg() + " data=" + result.getData());
            }
        }


        return hcpTitlePage;
    }


    /**
     * 根据省份城市获取医院数量
     * @param province
     * @param city
     * @return
     */
    @Cacheable(value = "virtual_rep_api_master_data", key = "'getCountByParam'+#province+#city")
    public Integer getCountByParam(String province, String city){
        province = cityJsonConfig.checkProvince(province);
        int count = 0;
        if (province == null){
            province = "";
        }

        if (city == null){
            city = "";
        }

        WebResult result = restTemplate.getForObject(prefixUrl + "api/hci/getCountByParam?province={province}&city={city}", WebResult.class, province, city);
        if (null != result){
            int resultcode = result.getResultcode();
            if (resultcode == 200){
                Object data = result.getData();
                if (null != data){
                    JSONObject json = (JSONObject) JSON.toJSON(data);
                    if (null != json){
                        count = (int)json.get("count");
                    }
                }
            }else {
                logger.error("getHcpTitlePage of MasterDataService error!!!  args= "
                        + " province= " + province
                        + " city " + city
                        +" resultcode =" +  result.getResultcode()
                        + " msg=" + result.getMsg() + " data=" + result.getData());
            }
        }

        return count;
    }


    /**
     * 根据 医院名称得到科室
     * @param name 医院名称
     * @return
     */
    @Cacheable(value = "virtual_rep_api_master_data", key = "'getDeptListByName'+#name")
    public List<Dept> getDeptListByName(String name){
        List<Dept> list = new ArrayList<>();

        WebResult result = restTemplate.getForObject(prefixUrl + "api/hci/getDeptListByName?name={name}", WebResult.class, name);
        if (null != result){
            int resultcode = result.getResultcode();
            if (resultcode == 200){
                Object data = result.getData();
                if (null != data){
                    JSON json = (JSON) JSON.toJSON(data);
                    list = JSON.parseArray(json.toJSONString(), Dept.class);
                }

            }else {
                logger.error("getDeptListByName of MasterDataService error!!!  args= "
                        + " name= " + name
                        +" resultcode =" +  result.getResultcode()
                        + " msg=" + result.getMsg() + " data=" + result.getData());
            }
        }


        return list;

    }


    /**
     * 根据主数据医院id得到所有别名
     * @param hciId
     * @return
     */
    @Cacheable(value = "virtual_rep_api_master_data", key = "'getHciAliasList'+#hciId")
    public List<HciAlias> getHciAliasList(Long hciId){
        List<HciAlias> list = null;

        WebResult result = restTemplate.getForObject(prefixUrl + "api/hci/getAliasListByHciId?hciId={id}", WebResult.class, hciId);
        if (null != result){
            int resultcode = result.getResultcode();
            if (resultcode == 200){
                Object data = result.getData();
                if (null != data){
                    JSON json = (JSON) JSON.toJSON(data);
                    list = JSON.parseArray(json.toJSONString(), HciAlias.class);
                }

            }else {
                logger.error("getHciAliasList of MasterDataService error!!!  args= "
                        + " hciId= " + hciId
                        +" resultcode =" +  result.getResultcode()
                        + " msg=" + result.getMsg() + " data=" + result.getData());
            }
        }

        return  list;

    }

    /**
     * 根据医院获取医院医生数量
     * @param hospitalName
     * @return
     */
//    @Cacheable(value = "dashboard_api_master_data", key = "'_getCountByParam_'+#hospitalName")
//    public Integer getCountByParam(String hospitalName){
//        Integer count = 0;
//        WebResult result = restTemplate.getForObject(prefixUrl + "api/hcp/getCountByParam?hciName={hospitalName}", WebResult.class, hospitalName);
//        if (null != result){
//            int resultcode = result.getResultcode();
//            if (resultcode == 200){
//                //http://47.93.118.67:9999/api/hcp/getCountByParam?hciName=%E5%8C%97%E4%BA%AC%E8%80%81%E5%B9%B4%E5%8C%BB%E9%99%A2
//
//                Object data = result.getData();
//                if(data!=null){
//                    JSON json = (JSON) JSON.toJSON(data);
//                    HospitalCountBean bean = JSON.parseObject(json.toJSONString(), HospitalCountBean.class);
//                    if(bean!=null){
//                        return bean.getCount();
//                    }
//                }
//            }else {
//                logger.error("error , api/hcp/getCountByParam?hciName={}",hospitalName);
//            }
//        }
//        return 0;
//    }

    /**
     * 得到所有的医生级别
     * @return
     */
    @Cacheable(value = "virtual_rep_api_master_data", key = "_getAllHcpTitle_")
    public List<HcpLevel> getAllHcpTitle(){
        List<HcpLevel> list = null;
        WebResult result = restTemplate.getForObject(prefixUrl + "api/hcp/getTitleList", WebResult.class);
        if (null != result){
            int resultcode = result.getResultcode();
            if (resultcode == 200){

                Object data = result.getData();
                if(data!=null){
                    JSON json = (JSON) JSON.toJSON(data);
                    list = JSON.parseArray(json.toJSONString(), HcpLevel.class);
                }
            }else {
                logger.error("error , api/hcp/getTitleList");
            }
        }

        return list;

    }
}
