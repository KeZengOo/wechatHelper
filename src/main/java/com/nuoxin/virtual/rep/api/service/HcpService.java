package com.nuoxin.virtual.rep.api.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.nuoxin.virtual.rep.api.common.bean.DefaultResponseBean;
import com.nuoxin.virtual.rep.api.common.bean.PageResponseBean;
import com.nuoxin.virtual.rep.api.common.service.BaseService;
import com.nuoxin.virtual.rep.api.dao.DoctorRepository;
import com.nuoxin.virtual.rep.api.dao.DrugUserRepository;
import com.nuoxin.virtual.rep.api.entity.Doctor;
import com.nuoxin.virtual.rep.api.entity.DrugUser;
import com.nuoxin.virtual.rep.api.enums.HospitalLevelEnum;
import com.nuoxin.virtual.rep.api.enums.MagazineTypeEnum;
import com.nuoxin.virtual.rep.api.utils.CollectionUtil;
import com.nuoxin.virtual.rep.api.utils.DateUtil;
import com.nuoxin.virtual.rep.api.web.controller.request.hcp.HcpRequestBean;
import com.nuoxin.virtual.rep.api.web.controller.response.hcp.*;
import com.nuoxin.virtual.rep.api.web.controller.response.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import java.util.*;

/**
 * Created by tiancun on 17/8/2.
 * 主数据医生的相关接口
 */
@Service
public class HcpService extends BaseService {

    @Autowired
    private MasterDataService masterDataService;

//    @Autowired
//    private DrugUserService drugUserService;
//
//    @Autowired
//    private DoctorMapper doctorMapper;

    @Autowired
    private DrugUserRepository drugUserRepository;

    @Autowired
    private DoctorRepository doctorRepository;


    @Autowired
    private RestTemplate restTemplate;

    @Value("${data.center.prefix.url}")
    private String prefixUrl;



    private Long getMasterDataIdByHcpId(Long hcpId){
        Long id = 0L;
        Doctor doctor = doctorRepository.findFirstById(hcpId);
//        if (null != doctor){
//            Long masterDataId = doctor.getMasterDateId();
//            if (masterDataId != null && masterDataId > 0){
//                id = masterDataId;
//            }
//
//        }

        //改成查营销的数据库了
        if (null != doctor){
            String name = doctor.getName();
            String hospitalName = doctor.getHospitalName();
            Hcp hcp = masterDataService.getHcpByHciIdAndHcpName(hospitalName, name);
            if (null != hcp){
                id = hcp.getId();
            }
        }


        return id;
    }


    /**
     * 医生的基本信息
     * @param id
     * @return
     */
    @Cacheable(value = "virtual_rep_api_hcp_service", key="'getHcpBaseInfo'+#id" )
    public HcpBaseInfoResponseBean getHcpBaseInfo(Long id){
        HcpBaseInfoResponseBean hcpBaseInfoResponseBean = new HcpBaseInfoResponseBean();
        Long hcpId = getMasterDataIdByHcpId(id);


        //第一个圈内容基本信息
//        long hciId = 0;
//        Hcp hcp = masterDataService.getHcpById(hcpId);
//        if (null != hcp){
//            hciId = hcp.getHciId();
//            hcpBaseInfoResponseBean.setDoctorName(hcp.getName());
//        }
//
//        Hci hci = masterDataService.getHciById(hciId);
//        if (null != hci){
//            String hospitalName = hci.getName();
//            int hospitalLevel = hci.getMedicalGrade();
//            hcpBaseInfoResponseBean.setHospitalName(hospitalName);
//            String name = HospitalLevelEnum.getName(hospitalLevel);
//            hcpBaseInfoResponseBean.setHospitalLevel(name);
//        }


        Doctor doctor = doctorRepository.findFirstById(id);
        if (null != doctor){
            hcpBaseInfoResponseBean.setDoctorName(doctor.getName());
            hcpBaseInfoResponseBean.setHospitalName(doctor.getHospitalName());
            hcpBaseInfoResponseBean.setHospitalLevel(doctor.getHospitalLevel());
        }


        //第二个圈
        Map<String, String> map = masterDataService.getMapHcpInfo(hcpId);
        if (null != map && map.size() > 0){
            //好大夫在线门诊量趋势图
            String inlineInquiryMapJsonStr = map.get("inline_inquiry_map");

            //得到趋势图及好大夫的门诊总量
            //访问总量
            int inquirySum =0 ;
            Map<String, Object> inlineInquiryMap = getInlineInquiryMap(inlineInquiryMapJsonStr);
            if (inlineInquiryMap != null && inlineInquiryMap.size() > 0){

                Object inlineInquirySumObj = inlineInquiryMap.get("inlineInquirySum");
                if (inlineInquirySumObj != null){
                    //好大夫门诊总量
                    int inlineInquirySum = Integer.parseInt(inlineInquirySumObj.toString());
                    inquirySum += inlineInquirySum;
                }
            }

            //挂号网门诊量
            String guahao_inline_inquiry_numStr = map.get("guahao_inline_inquiry_num");
            if(null != guahao_inline_inquiry_numStr && !"".equals(guahao_inline_inquiry_numStr.trim()) && !"0".equals(guahao_inline_inquiry_numStr)) {
                int guahao_inline_inquiry_num = Integer.parseInt(guahao_inline_inquiry_numStr);
                inquirySum += guahao_inline_inquiry_num;
            }
            hcpBaseInfoResponseBean.setOutpatientVolume(inquirySum);

            //预约量
            String reservationsCount = map.get("reservationsCount");
            if (null != reservationsCount && !"".equals(reservationsCount) && !"0".equals(reservationsCount)){
                hcpBaseInfoResponseBean.setOrderNum(Integer.parseInt(reservationsCount));
            }

            //综合评分
            String recommendIndex = map.get("recommendIndex");
            if (null != recommendIndex && !"".equals(recommendIndex) && !"0".equals(recommendIndex)){
                hcpBaseInfoResponseBean.setComprehensiveScore(recommendIndex);
            }
        }


        //第三个圈，论文关键词
        List<DocKeywordResponseBean> docKeywordList = getDocKeywordList(hcpId);
        hcpBaseInfoResponseBean.setKeywordList(docKeywordList);

        return hcpBaseInfoResponseBean;

    }


    /**
     * 根据主数据医生id得到社会影响力
     * @param hcpId
     * @return
     */
    @Cacheable(value = "virtual_rep_api_hcp_service", key="'getHcpSociety'+#hcpId" )
    public HcpSocietyResponseBean getHcpSociety(Long hcpId){
        HcpSocietyResponseBean hcpSocietyResponseBean = new HcpSocietyResponseBean();

        //改成查营销的数据库了
        //Doctor doctor = doctorRepository.findFirstById(hcpId);
        hcpId = getMasterDataIdByHcpId(hcpId);



        Map<String, String> map = masterDataService.getMapHcpInfo(hcpId);
        if (map != null && map.size() > 0){
           //目前只有9种
            /**
             * map.put("inline_inquiry_num", inline_inquiry_num);
             map.put("inline_inquiry_map", inline_inquiry_map);
             map.put("hcp_H_index", hcp_H_index);
             map.put("hcp_G_index", hcp_G_index);
             map.put("votesCount", votesCount);
             map.put("thanksCount", thanksCount);
             map.put("reservationsCount", reservationsCount);
             map.put("recommendIndex", recommendIndex);
             map.put("guahao_inline_inquiry_num", guahao_inline_inquiry_num);
             */
            //好大夫在线门诊量(暂时不用)
            //String inline_inquiry_num = map.get("inline_inquiry_num");

            //好大夫在线门诊量趋势图
            String inlineInquiryMapJsonStr = map.get("inline_inquiry_map");

            //得到趋势图及好大夫的门诊总量
            //访问总量
            int inquirySum =0 ;
            Map<String, Object> inlineInquiryMap = getInlineInquiryMap(inlineInquiryMapJsonStr);
            if (inlineInquiryMap != null && inlineInquiryMap.size() > 0){

                Object inlineInquirySumObj = inlineInquiryMap.get("inlineInquirySum");
                if (inlineInquirySumObj != null){
                    //好大夫门诊总量
                    int inlineInquirySum = Integer.parseInt(inlineInquirySumObj.toString());
                    inquirySum += inlineInquirySum;
                }

                Object outpatientVolumeMapObj = inlineInquiryMap.get("outpatientVolumeMap");
                if (outpatientVolumeMapObj != null){
                    TreeMap<Integer,Integer> treeMap = (TreeMap) inlineInquiryMap.get("outpatientVolumeMap");
                    //取最新的4年
                    TreeMap<Integer, Integer> integerIntegerTreeMap = subTreeMap(treeMap, 4);

                    hcpSocietyResponseBean.setOutpatientVolumeMap(integerIntegerTreeMap);
                }
            }

            //挂号网门诊量
            String guahao_inline_inquiry_numStr = map.get("guahao_inline_inquiry_num");
            if(null != guahao_inline_inquiry_numStr && !"".equals(guahao_inline_inquiry_numStr.trim()) && !"0".equals(guahao_inline_inquiry_numStr)) {
                int guahao_inline_inquiry_num = Integer.parseInt(guahao_inline_inquiry_numStr);
                inquirySum += guahao_inline_inquiry_num;
            }
            hcpSocietyResponseBean.setOutpatientVolume(inquirySum);

            //预约量
            String reservationsCount = map.get("reservationsCount");
            if (null != reservationsCount && !"".equals(reservationsCount) && !"0".equals(reservationsCount)){
                hcpSocietyResponseBean.setOrderNum(Integer.parseInt(reservationsCount));
            }

            //综合评分
            String recommendIndex = map.get("recommendIndex");
            if (null != recommendIndex && !"".equals(recommendIndex) && !"0".equals(recommendIndex)){
                hcpSocietyResponseBean.setComprehensiveScore(recommendIndex);
            }

        }

        return hcpSocietyResponseBean;
    }


    /**
     * 分页得到医生的对话信息
     * @param bean 请求bean
     * @return
     */
    @Cacheable(value = "virtual_rep_api_hcp_service", key="'getDialogList'+#bean" )
    public PageResponseBean<HcpDialogResponseBean> getDialogList(HcpRequestBean bean){

        Long hcpId = bean.getHcpId();

        //改成查营销的数据了
        //Doctor doctor = doctorRepository.findFirstById(hcpId);
        hcpId = getMasterDataIdByHcpId(hcpId);



        Integer page = bean.getPage() + 1;
        Integer pageSize = bean.getPageSize();


        Long drugUserId = bean.getDrugUserId();
        DrugUser firstById = drugUserRepository.findFirstById(drugUserId);
        Long eappId = 0L;
        if (firstById != null){
            eappId = firstById.getEappId();
        }

        //得到的关键词
        String allkeyword = "";
        Map<String, String> keys = getKeys(eappId);
        if (null != keys && keys.size() > 0){
            allkeyword = keys.get("allkeyword");
        }
        //有关键词的对话
        List<ConsultDetail> consultDetailListByKey = masterDataService.getConsultDetailListByKey(hcpId, allkeyword);

        //所有的对话
        List<ConsultDetail> consultDetailList = masterDataService.getConsultDetailListByKey(hcpId, null);

        //排序有关键词的先放到上面
        List<ConsultDetail> sortconsultDetailList = getSortConsultDetailListByKey(consultDetailListByKey,consultDetailList);
        int total = 0;
        if (sortconsultDetailList != null && sortconsultDetailList.size() > 0){
            total = sortconsultDetailList.size();
        }

        //由于查询所有，只能在list中分页
        List<ConsultDetail> pageSortconsultDetailList = getPageSortconsultDetailList(sortconsultDetailList, page, pageSize);
        List<HcpDialogResponseBean> list = new ArrayList<>();
        if (null != pageSortconsultDetailList && pageSortconsultDetailList.size() > 0){
            for (ConsultDetail consultDetail:pageSortconsultDetailList){
                HcpDialogResponseBean hcpDialogResponseBean = new HcpDialogResponseBean();
                if (null != consultDetail){
                    Date startTime = consultDetail.getStartTime();
                    String dateTimeString = DateUtil.getDateTimeString(startTime);
                    hcpDialogResponseBean.setId(consultDetail.getId());
                    hcpDialogResponseBean.setStartTime(dateTimeString);
                    hcpDialogResponseBean.setContent(consultDetail.getContent());
                    hcpDialogResponseBean.setAllKeyWord(allkeyword);
                    list.add(hcpDialogResponseBean);
                }

            }
        }


        PageResponseBean<HcpDialogResponseBean> pageResponseBean = new PageResponseBean<>(bean, total, list );
        return pageResponseBean;

    }


    /**
     * 对话中关键词统计
     * @param bean
     * @return
     */
    @Cacheable(value = "virtual_rep_api_hcp_service", key="'getKeywordList'+#bean" )
    public KeywordListResponseBean getKeywordList(HcpRequestBean bean){
        KeywordListResponseBean keywordListResponseBean = new KeywordListResponseBean();
        Long hcpId = bean.getHcpId();

        hcpId = getMasterDataIdByHcpId(hcpId);


        Long drugUserId = bean.getDrugUserId();

        DrugUser firstById = drugUserRepository.findFirstById(drugUserId);
        Long eappId = 0L;
        if (firstById != null){
            eappId = firstById.getEappId();
        }

        String allkeyword = "";
        Map<String, String> keys = getKeys(eappId);
        if (null != keys && keys.size() > 0){
            allkeyword = keys.get("allkeyword");
        }
        //有关键词的对话
        List<ConsultDetail> consultDetailListByKey = masterDataService.getConsultDetailListByKey(hcpId, allkeyword);
        if (null == consultDetailListByKey || consultDetailListByKey.size() == 0){
            return keywordListResponseBean;
        }

        Map<String, List<String>> map = getKeysList(eappId);
        List<String> ckeywordList = new ArrayList<>();
        List<String> pkeywordList = new ArrayList<>();
        if (null != map && map.size() > 0){
            ckeywordList = map.get("ckeywordList");
            pkeywordList = map.get("pkeywordList");
        }

        List<KeywordResponseBean> ckList = new ArrayList<>();
        List<KeywordResponseBean> pkList = new ArrayList<>();
        //竞品关键词统计数量
        if (null !=ckeywordList && ckeywordList.size() > 0){
            for (String ck:ckeywordList){

                int ckCount = 0;
               for (ConsultDetail consultDetail:consultDetailListByKey){
                   String content = consultDetail.getContent();
                   if (!StringUtils.isEmpty(content)){
                       int subCount = getSubCount(content, ck);
                       ckCount += subCount;
                   }
               }
                KeywordResponseBean ckBean = new KeywordResponseBean();
                ckBean.setKeyword(ck);
                ckBean.setCount(ckCount);
                ckList.add(ckBean);
            }
        }


        //产品关键词统计数量
        if (null !=pkeywordList && pkeywordList.size() > 0){
            for (String pk:pkeywordList){
                int pkCount = 0;
                for (ConsultDetail consultDetail:consultDetailListByKey){
                    String content = consultDetail.getContent();
                    if (!StringUtils.isEmpty(content)){
                        int subCount = getSubCount(content, pk);
                        pkCount += subCount;
                    }
                }

                KeywordResponseBean pkBean = new KeywordResponseBean();
                pkBean.setKeyword(pk);
                pkBean.setCount(pkCount);
                pkList.add(pkBean);
            }
        }

        if (null != ckList && ckList.size() > 0){
            Collections.sort(ckList);
            keywordListResponseBean.setCkeywordList(ckList);
        }

        if (null != pkList && pkList.size() > 0){
            Collections.sort(pkList);
            keywordListResponseBean.setPkeywordList(pkList);
        }

       return keywordListResponseBean;
    }


    /**
     * 得到医生的五角(雷达)图
     * @param hcpId
     * @return
     */
    @Cacheable(value = "virtual_rep_api_hcp_service", key="'getHcpPentagon'+#hcpId" )
    public HcpPentagonResponseBean getHcpPentagon(Long hcpId){
        HcpPentagonResponseBean hcpPentagonResponseBean = new HcpPentagonResponseBean();

        //改成查营销的数据库了
        //Doctor doctor = doctorRepository.findFirstById(hcpId);
        hcpId = getMasterDataIdByHcpId(hcpId);


        HcpResearchInfo hcpResearchInfo = masterDataService.getHcpResearchInfo(hcpId);
        if (hcpResearchInfo != null){

            //论文总数
            int totalDocNum = hcpResearchInfo.getTotalDocNum();
            //论文引用总数
            int totalReferenceNum = hcpResearchInfo.getTotalReferenceNum();
            //合作作者数
            int coAuthorNum = 0;
            List<HcpCoAuthor> coAuthorList = hcpResearchInfo.getCoAuthorList();
            if(coAuthorList !=null && coAuthorList.size() > 0){
                for(HcpCoAuthor hcpCoAuthor:coAuthorList){
                    int coNum = hcpCoAuthor.getCoNum();
                    coAuthorNum += coNum;
                }
            }

            hcpPentagonResponseBean.setPaper("" + totalDocNum);
            hcpPentagonResponseBean.setCitation("" + totalReferenceNum);
            hcpPentagonResponseBean.setSociability("" + coAuthorNum);

            Map<String, String> map = masterDataService.getMapHcpInfo(hcpId);
            if (null != map && map.size() > 0){
                String hcp_H_index = map.get("hcp_H_index");
                if (null != hcp_H_index && !"".equals(hcp_H_index.trim()) && !"0".equals(hcp_H_index)){
                    hcpPentagonResponseBean.setH_index(hcp_H_index);
                }

                String hcp_G_index = map.get("hcp_G_index");
                if (null != hcp_G_index && !"".equals(hcp_G_index.trim()) && !"0".equals(hcp_G_index)){
                    hcpPentagonResponseBean.setG_index(hcp_G_index);
                }

            }

        }

        return hcpPentagonResponseBean;

    }

    /**
     * 得到医生的学术信息
     * @param hcpId
     * @return
     */
    @Cacheable(value = "virtual_rep_api_hcp_service", key="'getHcpResearchInfo'+#hcpId" )
    public HcpResearchInfoResponseBean getHcpResearchInfo(Long hcpId){
        HcpResearchInfoResponseBean hcpResearchInfoResponseBean = new HcpResearchInfoResponseBean();

        //改成查营销的数据库了
        //Doctor doctor = doctorRepository.findFirstById(hcpId);
        hcpId = getMasterDataIdByHcpId(hcpId);


        //论文关键词
//        List<DocKeywordResponseBean> docKeywordList = new ArrayList<>();
//        List<DocKeyWord> docKeyWordList = masterDataService.getDocKeyWordList(hcpId);
//        if (docKeyWordList != null && docKeyWordList.size() > 0){
//            for (DocKeyWord docKeyWord:docKeyWordList){
//                DocKeywordResponseBean docKeywordResponseBean = new DocKeywordResponseBean();
//                docKeywordResponseBean.setCount(docKeyWord.getCount());
//                docKeywordResponseBean.setKeyword(docKeyWord.getKeyword());
//                docKeywordList.add(docKeywordResponseBean);
//            }
//        }

        //论文关键词
        List<DocKeywordResponseBean> docKeywordList = getDocKeywordList(hcpId);


        hcpResearchInfoResponseBean.setDocKeywordList(docKeywordList);

        HcpResearchInfo hcpResearchInfo = masterDataService.getHcpResearchInfo(hcpId);
        List<HcpCoAuthorResponseBean> hcpCoAuthorList = new ArrayList<>();
        List<HcpCoMagazineResponseBean> hcpCoMagazineList = new ArrayList<>();
        if (null != hcpResearchInfo){

            //合作作者
            List<HcpCoAuthor> coAuthorList = hcpResearchInfo.getCoAuthorList();
            if (coAuthorList != null && coAuthorList.size() > 0){
                for (HcpCoAuthor hcpCoAuthor:coAuthorList){
                    HcpCoAuthorResponseBean hcpCoAuthorResponseBean = new HcpCoAuthorResponseBean();
                    if (null != hcpCoAuthor){
                        hcpCoAuthorResponseBean.setAuthorName(hcpCoAuthor.getAuthorName());
                        hcpCoAuthorResponseBean.setCoNum(hcpCoAuthor.getCoNum());
                        hcpCoAuthorList.add(hcpCoAuthorResponseBean);
                    }

                }

                //按照数量从大到小排序
                Collections.sort(hcpCoAuthorList);
                hcpResearchInfoResponseBean.setHcpCoAuthorList(hcpCoAuthorList);
            }

            //合作专刊
            List<HcpCoMagazine> coMagazineList = hcpResearchInfo.getCoMagazineList();
            if (null != coMagazineList && coMagazineList.size() > 0){
                for (HcpCoMagazine hcpCoMagazine:coMagazineList){
                    HcpCoMagazineResponseBean hcpCoMagazineResponseBean = new HcpCoMagazineResponseBean();
                    if (null != hcpCoMagazine){
                        hcpCoMagazineResponseBean.setCoNum(hcpCoMagazine.getCoNum());
                        hcpCoMagazineResponseBean.setMagazineName(hcpCoMagazine.getMagazineName());
                        hcpCoMagazineList.add(hcpCoMagazineResponseBean);
                    }
                }

                //按照数量总小到大排序
                Collections.sort(hcpCoMagazineList);
                hcpResearchInfoResponseBean.setHcpCoMagazineList(hcpCoMagazineList);
            }

        }


        return hcpResearchInfoResponseBean;

    }


    /**
     * 得到医生的论文列表
     * @param bean 请求bean
     * @return
     */
    @Cacheable(value = "dashboard_api_hcp_service", key="'getDocList'+#bean" )
    public PageResponseBean<HcpDocResponseBean> getDocList(HcpRequestBean bean){
        Long hcpId = bean.getHcpId();

        hcpId = getMasterDataIdByHcpId(hcpId);
        Integer page = bean.getPage() + 1;
        Integer pageSize = bean.getPageSize();


        Long drugUserId = bean.getDrugUserId();
        DrugUser firstById = drugUserRepository.findFirstById(drugUserId);
        Long eappId = 0L;
        if (firstById != null){
            eappId = firstById.getEappId();
        }

        HcpResearchInfo hcpResearchInfo = masterDataService.getHcpResearchInfo(hcpId);
        List<Doc> docList = new ArrayList<>();
        List<Doc> keyDocList = new ArrayList<>();
        if (null != hcpResearchInfo){
            List<Doc> docs = hcpResearchInfo.getDocList();
            if (null != docs && docs.size() > 0){
                docList = docs;
            }
        }

        /**
         * 得到后台配置的更关键词
         */
        //得到的关键词
        String allkeyword = "";
        String ckeyword = "";
        String pkeyword = "";
        Map<String, String> keys = getKeys(eappId);
        if (null != keys && keys.size() > 0){
            allkeyword = keys.get("allkeyword");
            ckeyword = keys.get("ckeyword");
            pkeyword = keys.get("pkeyword");
        }



        //有关键词的论文
        KeyWordDoc hcpDocListByKeys = masterDataService.getHcpDocListByKeys(hcpId, allkeyword, page, pageSize);
        if (null != hcpDocListByKeys){
            List<Doc> list = hcpDocListByKeys.getDatas();
            if (null != list && list.size() > 0){
                keyDocList = list;
            }

        }


        //排序有关键词的先放到上面
        List<Doc> sortDocList = getSortDocByKey(keyDocList, docList);

        List<HcpDocResponseBean> hcpDocResponseBeanList = new ArrayList<>();
        if (null != sortDocList && sortDocList.size() > 0){
            for (Doc doc:sortDocList){
                if (null != doc){
                    long magazineId = doc.getMagazineId();
                    Magazine magazine = masterDataService.getMagazine(magazineId);
                    if (null != magazine){
                        String type = magazine.getType();
                        String name = doc.getName();
                        String magazineNo = doc.getMagazineNo();
                        int referenceNum = doc.getReferenceNum();
                        // 根据期刊和类型得到时间
                        String thesisNo = getThesisNo(magazineNo, type);
                        String authors = doc.getAuthors();
                        HcpDocResponseBean hcpDocResponseBean = new HcpDocResponseBean();
                        hcpDocResponseBean.setThesisName(name);
                        hcpDocResponseBean.setThesisNo(thesisNo);
                        hcpDocResponseBean.setThesisReferenceNum(referenceNum);
                        hcpDocResponseBean.setAuthors(authors);
                        hcpDocResponseBeanList.add(hcpDocResponseBean);
                    }
                }
            }
        }

        int totalCount = 0;
        if (null != hcpDocResponseBeanList && hcpDocResponseBeanList.size() > 0){
            totalCount = hcpDocResponseBeanList.size();
        }


        //论文是全部获取，在List中分页
        List<HcpDocResponseBean> listPage = CollectionUtil.getListPage(hcpDocResponseBeanList, page, pageSize);
        PageResponseBean<HcpDocResponseBean> hcpDocResponseBeanPage = new PageResponseBean<>(bean, totalCount, listPage);

        return hcpDocResponseBeanPage;
    }


    /**
     * 得到论文趋势图
     * @param hcpId 主数据医生id
     * @return
     */
    @Cacheable(value = "dashboard_api_hcp_service", key="'getHcpDocTrend'+#hcpId" )
    public TreeMap<Integer, Integer> getHcpDocTrend(Long hcpId){

        hcpId = getMasterDataIdByHcpId(hcpId);

        HcpResearchInfo hcpResearchInfo = masterDataService.getHcpResearchInfo(hcpId);
        //截取时间后的论文
        List<Doc> newHcpDocList = new ArrayList<>();
        if (null != hcpResearchInfo){
            List<Doc> docList = hcpResearchInfo.getDocList();
            if (null != docList && docList.size() > 0) {
                for (Doc doc:docList){
                    if (null != doc){
                        //截取期刊由2016-09 获得2016
                        String magazinNo = doc.getMagazineNo().substring(0, 4);
                        if (null == magazinNo || "".equals(magazinNo.trim())){
                            magazinNo = "1998";//没有就给个默认的
                        }
                        doc.setMagazineNo(magazinNo);
                        newHcpDocList.add(doc);
                    }
                }
            }
        }



        //得到每一年论文的数量
        TreeMap<Integer, Integer> treeMap = new TreeMap<>();
        if (newHcpDocList != null && newHcpDocList.size() > 0) {

            for (Doc doc : newHcpDocList) {
                Integer magazineNo = Integer.parseInt(doc.getMagazineNo());
                if (treeMap.containsKey(magazineNo)) {
                    treeMap.put(magazineNo, treeMap.get(magazineNo) + 1);
                } else {
                    treeMap.put(magazineNo, 1);
                }
            }
        }


        TreeMap<Integer, Integer> subTreeMap = subTreeMap(treeMap,4);

        return subTreeMap;
    }

    /**
     * 根据主数据医生id得到论文关键词
     * @param hcpId 主数据id
     * @return
     */
    @Cacheable(value = "dashboard_api_hcp_service", key="'getDocKeywordList'+#hcpId" )
    public List<DocKeywordResponseBean> getDocKeywordList(Long hcpId){
        //此处不用加
        //hcpId = getMasterDataIdByHcpId(hcpId);

        List<DocKeywordResponseBean> docKeywordList = new ArrayList<>();
        List<DocKeyWord> docKeyWordList = masterDataService.getDocKeyWordList(hcpId);
        if (docKeyWordList != null && docKeyWordList.size() > 0){
            for (DocKeyWord docKeyWord:docKeyWordList){
                DocKeywordResponseBean docKeywordResponseBean = new DocKeywordResponseBean();
                docKeywordResponseBean.setCount(docKeyWord.getCount());
                docKeywordResponseBean.setKeyword(docKeyWord.getKeyword());
                docKeywordList.add(docKeywordResponseBean);
            }
        }

        return docKeywordList;
    }



    /**
     * 得到后台配置的关键词，valus以字符串返回
     * @param drugUser
     * @return
     */
    /**
     * 暂时没有实现
     * @param drugUserId
     * @return
     */
    private Map<String,String> getKeys(Long drugUserId){
        Map<String,String> map = new HashMap<>();
        String url = prefixUrl + "/hcp/getProductKeys/{id}";
        DefaultResponseBean<HcpSocietyResponseBean> responseBean = restTemplate.getForObject(url, DefaultResponseBean.class, drugUserId);
        JSONObject json = (JSONObject) JSONObject.toJSON(responseBean);
        if (null != json){
            int code = (int) json.get("code");
            if (code == 200){
                map = (Map<String, String>) json.get("data");
            }
        }


        return map;
    }

    private Map<String,List<String>> getKeysList(Long drugUserId){

        Map<String,List<String>> map = new HashMap<>();

        List<String> ckeywordList = new ArrayList<>();
        List<String> pkeyworkList = new ArrayList<>();

        Map<String, String> keys = getKeys(drugUserId);
        String pkeyword = keys.get("pkeyword");
        if (!StringUtils.isEmpty(pkeyword)){
            String[] split = pkeyword.split(",");
            if (null != split && split.length > 0){
                for (String pkey:split){
                    pkeyworkList.add(pkey);
                }
            }
        }

        String ckeyword = keys.get("ckeyword");
        if (!StringUtils.isEmpty(ckeyword)){
            String[] split = ckeyword.split(",");
            if (null != split && split.length > 0){
                for (String ckey:split){
                    ckeywordList.add(ckey);
                }
            }
        }

        map.put("ckeywordList",ckeywordList);
        map.put("pkeywordList",pkeyworkList);

        return map;

    }


    /**
     * 得到后台配置的关键词，value以list返回
     * @param drugUser
     * @return
     */

    /**
     * 暂时没有实现
     * @param drugUser
     * @return
     */
    private Map<String,List<String>> getKeysList(DrugUser drugUser){
        Map<String,List<String>> map = new HashMap<>();

//        List<String> ckeywordList = new ArrayList<>();
//        List<String> pkeyworkList = new ArrayList<>();
//
//        List<ProductLine> productKeyWordList = drugUserService.findByProductKeyWord(drugUser);
//        if(null != productKeyWordList && productKeyWordList.size() > 0){
//            StringBuilder allkeyWordStr = new StringBuilder("");
//            for (ProductLine productLine:productKeyWordList){
//                //竞品关键词
//                String ckeyWord = productLine.getCkeyWord();
//                if (null != ckeyWord && !"".equals(ckeyWord.trim())){
//                    String[] ckeyWordArrays = ckeyWord.split(",");
//                    if (null != ckeyWordArrays && ckeyWordArrays.length > 0){
//                        for (String ckeyWordArray:ckeyWordArrays){
//                            ckeywordList.add(ckeyWordArray);
//                        }
//                    }
//                }
//
//                //产品关键词
//                String pkeyword = productLine.getPkeyWord();
//                if (null != pkeyword && !"".equals(pkeyword.trim())){
//                    String[] pkeywordArrays = pkeyword.split(",");
//                    if (null != pkeywordArrays && pkeywordArrays.length > 0){
//                        for (String pkeywordArray:pkeywordArrays){
//                            pkeyworkList.add(pkeywordArray);
//                        }
//                    }
//                }
//
//
//            }
//            map.put("ckeywordList",ckeywordList);
//            map.put("pkeywordList",pkeyworkList);
//
//
//        }


        return map;
    }



    /**
     *
     * @param map
     * @param n
     * @return
     */
    private TreeMap<Integer, Integer> subTreeMap(TreeMap<Integer, Integer> map, int n){
        if (map == null || map.size() == 0){
            return null;
        }


        TreeMap<Integer, Integer> subMap = null;
        if (map != null && map.size() > 0) {
            subMap = new TreeMap<>();
            int size = map.size();
            if (size > n) {
                // 此方法不可行，时间有可能不是连续的
                // Integer lastKey = map.lastKey();
                // for(int i = 0; i< 4; i++){
                // subMap.put(lastKey - i, map.get(lastKey - i));
                // }

                Set<Integer> set = map.keySet();
                Iterator<Integer> it = set.iterator();
                TreeSet<Integer> ts = new TreeSet<Integer>();
                while (it.hasNext()) {
                    ts.add(it.next());
                }

                Iterator<Integer> it2 = ts.descendingIterator();

                int i = 0;
                while (it2.hasNext()) {
                    if (i == n) {
                        break;
                    }
                    Integer next = it2.next();
                    subMap.put(next, map.get(next));
                    i++;
                }

            } else if (size == n) {
                subMap = map;
            } else if (size < n) {
                int size2 = map.size();
                subMap = map;
                Integer firstKey = map.firstKey();
                for (int i = 0; i < n - size2; i++) {
                    subMap.put(firstKey - i - 1, 0);
                }
            }
        }

        return subMap;
    }


    // 根据期刊和类型得到时间
    private String getThesisNo(String magazineNo, String type) {
        if(magazineNo == null || magazineNo.trim().equals("")){
            magazineNo = "1998/01";
        }
        String[] magazineNos = magazineNo.split("/");
        if (magazineNos == null || magazineNos.length == 0) {
            // 如果没有期刊，给个默认的
            return "1998/01";
        }
        int no = 0;
        if (magazineNos != null && magazineNos.length > 1) {
            String noStr = magazineNos[1];
            if (noStr != null) {
                try {
                    no = Integer.parseInt(noStr);
                } catch (Exception e) {
                    no = 1;
                }

            }
        }
        if (magazineNos != null && magazineNos.length == 1){
            no = 1;
        }

        int value = 0;
        if(type == null || type.trim().equals("")){
            type = "半月";
        }

        if (type.equals(MagazineTypeEnum.HALF_MONTH.getName())) {
            value = MagazineTypeEnum.HALF_MONTH.getValue();

        } else if (type.equals(MagazineTypeEnum.DOUBLE_MONTH.getName())) {
            value = MagazineTypeEnum.DOUBLE_MONTH.getValue();

        } else if (type.equals(MagazineTypeEnum.MONTH.getName())) {
            value = MagazineTypeEnum.MONTH.getValue();

        } else if (type.equals(MagazineTypeEnum.TEN.getName())) {
            value = MagazineTypeEnum.TEN.getValue();

        } else if (type.equals(MagazineTypeEnum.WEEK.getName())) {
            value = MagazineTypeEnum.WEEK.getValue();

        } else if (type.equals(MagazineTypeEnum.SEASON.getName())) {
            value = MagazineTypeEnum.SEASON.getValue();

        } else if (type.equals(MagazineTypeEnum.WEEK2.getName())) {
            value = MagazineTypeEnum.WEEK2.getValue();

        } else if (type.equals(MagazineTypeEnum.YEAR.getName())) {
            value = MagazineTypeEnum.YEAR.getValue();

        } else if (type.equals(MagazineTypeEnum.HALF_YEAR.getName())) {
            value = MagazineTypeEnum.HALF_YEAR.getValue();
        }

        int days = no * value;
        int month = days / 30;
        if (month == 0) {
            month = 1;
        }
        if (month > 12) {
            month = 12;
        }

        String yearStr = magazineNos[0];
        String thesisNo = yearStr + "/" + month;
        return thesisNo;
    }


    /**
     * 排序，有关键词的先放到上面
     * @param keyDocList
     * @param docList
     * @return
     */
    private List<Doc> getSortDocByKey(List<Doc> keyDocList, List<Doc> docList){
        List<Doc> list = new ArrayList<>();
        if (null == keyDocList || keyDocList.size() == 0){
            return list;
        }

        if (null == docList || docList.size() == 0){
            return list;
        }

        //先移除掉有关键词的论文
        for (Doc doc:keyDocList){
            Iterator<Doc> docIter = docList.iterator();
            while(docIter.hasNext()){
                Doc d = docIter.next();
                if (null != d && null != doc){
                    Long dId = d.getId();
                    Long docId = doc.getId();
                    if (dId == docId || dId.equals(docId)){
                        docIter.remove();
                    }
                }

            }
        }

        //再将有关键词论文放到前面
        for (int i = 0; i < keyDocList.size(); i++){
            docList.add(i,keyDocList.get(i));
        }

        return  docList;

    }


    /**
     * List中分页医生的对话信息
     * @param sortconsultDetailList
     * @param page
     * @param pageSize
     * @return
     */
    private List<Object> getListPage(List<? extends Object> sortconsultDetailList,Integer page, Integer pageSize){
        List<Object> list = new ArrayList<>();
        if (sortconsultDetailList == null && sortconsultDetailList.size() == 0){
            return list;
        }

        int totalCount = sortconsultDetailList.size();
        int pageCount = 0;
        int m = totalCount % pageSize;
        if (m > 0){
            pageCount = totalCount/pageSize+1;
        } else{
            pageCount = totalCount/pageSize;
        }

        if (page > totalCount){
            return list;
        }

        if (page == pageCount){
            List<? extends Object> subList = sortconsultDetailList.subList((page-1) * pageSize,totalCount);
            list.addAll(subList);
        }else{
            List<? extends Object> subList = sortconsultDetailList.subList((page-1) * pageSize, pageSize * page);
            list.addAll(subList);
        }

//        if (m == 0){
//            List<ConsultDetail> subList = sortconsultDetailList.subList((page-1) * pageSize,pageSize * (page));
//            list.addAll(subList);
//        }else{
//            if (page == pageCount){
//                List<ConsultDetail> subList = sortconsultDetailList.subList((page-1) * pageSize,totalCount);
//                list.addAll(subList);
//            }else{
//                List<ConsultDetail> subList = sortconsultDetailList.subList((page-1) * pageSize,pageSize * (page));
//                list.addAll(subList);
//            }
//
//
//        }

        return list;
    }

    /**
     * List中分页医生的对话信息
     * @param sortconsultDetailList
     * @param page
     * @param pageSize
     * @return
     */
    private List<ConsultDetail> getPageSortconsultDetailList(List<ConsultDetail> sortconsultDetailList,Integer page, Integer pageSize){
        List<ConsultDetail> list = new ArrayList<>();
        if (sortconsultDetailList == null && sortconsultDetailList.size() == 0){
            return list;
        }

        int totalCount = sortconsultDetailList.size();
        int pageCount = 0;
        int m = totalCount % pageSize;
        if (m > 0){
            pageCount = totalCount/pageSize+1;
        } else{
            pageCount = totalCount/pageSize;
        }

        if (page > totalCount){
            return list;
        }

        if (page == pageCount){
            List<ConsultDetail> subList = sortconsultDetailList.subList((page-1) * pageSize,totalCount);
            list.addAll(subList);
        }else{
            List<ConsultDetail> subList = sortconsultDetailList.subList((page-1) * pageSize, pageSize * page);
            list.addAll(subList);
        }

//        if (m == 0){
//            List<ConsultDetail> subList = sortconsultDetailList.subList((page-1) * pageSize,pageSize * (page));
//            list.addAll(subList);
//        }else{
//            if (page == pageCount){
//                List<ConsultDetail> subList = sortconsultDetailList.subList((page-1) * pageSize,totalCount);
//                list.addAll(subList);
//            }else{
//                List<ConsultDetail> subList = sortconsultDetailList.subList((page-1) * pageSize,pageSize * (page));
//                list.addAll(subList);
//            }
//
//
//        }

        return list;
    }

    /**
     * 排序，有关键词的先放到上面
     * @param consultDetailListByKey
     * @param consultDetailList
     * @return
     */
    private List<ConsultDetail> getSortConsultDetailListByKey(List<ConsultDetail> consultDetailListByKey,List<ConsultDetail> consultDetailList){

        if (null != consultDetailListByKey && consultDetailListByKey.size() > 0){
            //先移除掉有关键词的对话
            for(ConsultDetail consultDetail:consultDetailListByKey){
                if(consultDetailList != null && consultDetailList.size() > 0){
                    Iterator<ConsultDetail> iter = consultDetailList.iterator();
                    while(iter.hasNext()){
                        Long consultDetailId = consultDetail.getId();
                        Long id = iter.next().getId();
                        if(consultDetailId == id || consultDetailId.equals(id)){
                            iter.remove();
                        }
                    }
                }
            }


            //再把有关键词的放到最上面
            for (int i = 0; i < consultDetailListByKey.size(); i++){
                consultDetailList.add(i,consultDetailListByKey.get(i) );
            }

        }

        return consultDetailList;

    }

    /**
     * 得到医生的在线门诊总量以及各个年的门诊量map
     * @param inlineInquiryMapJsonStr
     * @return
     */
    private Map<String, Object> getInlineInquiryMap(String inlineInquiryMapJsonStr) {
        Map<String, Object> map = new HashMap<>();
        try {
            if (inlineInquiryMapJsonStr != null && !inlineInquiryMapJsonStr.equals("")) {
                Map<String, Integer> inline_inquiry_map = (Map) JSON.parse(inlineInquiryMapJsonStr);
                if (inline_inquiry_map != null && inline_inquiry_map.size() > 0) {
                    TreeMap<Integer, Integer> outpatientVolumeMap = new TreeMap<>();
                    int inlineInquirySum = 0;
                    for (Map.Entry<String, Integer> entry : inline_inquiry_map.entrySet()) {
                        String key = entry.getKey();
                        int value = entry.getValue();
                        outpatientVolumeMap.put(Integer.parseInt(key), value);
                        inlineInquirySum += value;
                    }
                    map.put("inlineInquirySum", inlineInquirySum);
                    map.put("outpatientVolumeMap", outpatientVolumeMap);
                }

            }
        } catch (Exception e) {
            throw e;
        }

        return map;
    }


    /**
     * 根据主数据医院名称和和医生名称得到营销那边医生
     * @param list 主数据医院名称包括别名
     * @param hcpName 主数据医生名称
     * @return
     */
//    private DoctorResponseBean getDoctorByHciNameAndHcpName(List<HciAlias> list, String hcpName, String leaderPath){
//        if (null == list || list.size() == 0){
//            return null;
//        }
//
//        DoctorResponseBean doctorResponseBean = null;
//
//        for (HciAlias hciAlias:list){
//            if (null != hciAlias){
//                String alias = hciAlias.getAlias();
//                doctorResponseBean = doctorMapper.selectDoctorByHciNameAndHcpName(alias, hcpName, leaderPath);
//                if (null != doctorResponseBean){
//                    break;
//                }
//
//            }
//        }
//
//        return doctorResponseBean;
//    }

    /**
     * String字符串中某个关键词的统计次数
     * @param str
     * @param key
     * @return
     */
    private int getSubCount(String str, String key) {
        int count = 0;
        int index = 0;
        while ((index = str.indexOf(key)) != -1) {
            str = str.substring(index + key.length());
            count++;
        }
        return count;
    }


    /**
     * 得到医生的药企，多个以逗号分割
     * @param name
     * @param hciName
     * @param leaderPath
     * @return
     */
//    public String getDrugName(String name, String hciName,String leaderPath, Long drugUserId){
//        String drugName = "";
//        StringBuffer sb = new StringBuffer("");
//        List<String> drugNames = doctorMapper.getDrugName(name, hciName, leaderPath, drugUserId);
//        if (null != drugNames && drugNames.size() > 0){
//            for (int i = 0; i< drugNames.size(); i++){
//                if (i == drugNames.size()-1){
//                    sb.append(drugNames.get(i));
//                }else{
//                    sb.append(drugNames.get(i));
//                    sb.append(",");
//                }
//            }
//        }
//        drugName = sb.toString();
//
//        return drugName;
//    }


}
