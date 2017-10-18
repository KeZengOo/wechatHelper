package com.nuoxin.virtual.rep.api.service;

import com.nuoxin.virtual.rep.api.common.bean.DoctorExcel;
import com.nuoxin.virtual.rep.api.common.bean.PageResponseBean;
import com.nuoxin.virtual.rep.api.common.enums.ErrorEnum;
import com.nuoxin.virtual.rep.api.common.exception.BusinessException;
import com.nuoxin.virtual.rep.api.common.service.BaseService;
import com.nuoxin.virtual.rep.api.common.util.StringUtils;
import com.nuoxin.virtual.rep.api.dao.DoctorRepository;
import com.nuoxin.virtual.rep.api.dao.DoctorVirtualRepository;
import com.nuoxin.virtual.rep.api.dao.DrugUserDoctorRepository;
import com.nuoxin.virtual.rep.api.entity.*;
import com.nuoxin.virtual.rep.api.web.controller.request.QueryRequestBean;
import com.nuoxin.virtual.rep.api.web.controller.request.doctor.DoctorRequestBean;
import com.nuoxin.virtual.rep.api.web.controller.request.doctor.RelationRequestBean;
import com.nuoxin.virtual.rep.api.web.controller.response.doctor.DoctorResponseBean;
import com.nuoxin.virtual.rep.api.web.controller.response.doctor.DoctorStatResponseBean;
import com.nuoxin.virtual.rep.api.web.controller.response.product.ProductResponseBean;
import com.nuoxin.virtual.rep.api.web.controller.response.vo.Hcp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.management.StringValueExp;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.*;

/**
 * Created by fenggang on 9/11/17.
 */
@Service
@Transactional(readOnly = true)
public class DoctorService extends BaseService {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private DoctorRepository doctorRepository;
    @Autowired
    private DrugUserService drugUserService;
    @Autowired
    private DoctorVirtualService doctorVirtualService;
    @Autowired
    private MasterDataService masterDataService;
    @Autowired
    private DrugUserDoctorRepository drugUserDoctorRepository;
    @Autowired
    private DoctorDynamicFieldValueService DoctorDynamicFieldValueService;

    @Autowired
    private ProductLineService productLineService;

    @Cacheable(value = "virtual_rep_api_doctor", key = "'_details_'+#id")
    public Doctor findById(Long id) {
        return doctorRepository.findOne(id);
    }

    @Cacheable(value = "virtual_rep_api_doctor", key = "'_mobile_'+#mobile")
    public Doctor findByMobile(String mobile) {
        return doctorRepository.findTopByMobile(mobile);
    }

    public List<Doctor> findByIdIn(Collection<Long> ids) {
        return doctorRepository.findByIdIn(ids);
    }

    public List<Doctor> findByMobileIn(Collection<String> mobiles) {
        return doctorRepository.findByMobileIn(mobiles);
    }

    public List<Doctor> findByEmailIn(Collection<String> emails) {
        return doctorRepository.findByEmailIn(emails);
    }

    @Cacheable(value = "virtual_rep_api_doctor", key = "'_page_'+#bean")
    public PageResponseBean<DoctorResponseBean> page(QueryRequestBean bean) {
//        PageRequest pageable = super.getPage(bean);
//        Specification<Doctor> spec = new Specification<Doctor>() {
//            @Override
//            public Predicate toPredicate(Root<Doctor> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
//                List<Predicate> predicates = new ArrayList<>();
//
//                query.where(cb.and(cb.and(predicates.toArray(new Predicate[0]))));
//                return query.getRestriction();
//            }
//        };
//        Page<Doctor> page = doctorRepository.findAll(spec, pageable);
//        PageResponseBean<DoctorResponseBean> responseBean = new PageResponseBean<>(page);
//        List<Doctor> pageList = page.getContent();
//        if (pageList != null && !pageList.isEmpty()) {
//            List<DoctorResponseBean> list = new ArrayList<>();
//            for (Doctor doctor : pageList) {
//                DoctorResponseBean listBean = new DoctorResponseBean();
//                BeanUtils.copyProperties(doctor, listBean);
//                listBean.setDoctorId(doctor.getId());
//                listBean.setDoctorMobile(doctor.getMobile());
//                listBean.setDoctorName(doctor.getName());
//                list.add(listBean);
//            }
//            responseBean.setContent(list);
//        }
        bean.setCurrentSize(bean.getPageSize()*bean.getPage());
        if (StringUtils.isNotEmtity(bean.getName())) {
            bean.setName( "%" + bean.getName() + "%");
        }
        if (StringUtils.isNotEmtity(bean.getMobile())) {
            bean.setMobile( "%" + bean.getMobile() + "%");
        }
        if (StringUtils.isNotEmtity(bean.getDepartment())) {
            bean.setDepartment( "%" + bean.getDepartment() + "%");
        }
        if (StringUtils.isNotEmtity(bean.getDoctorLevel())) {
            bean.setDoctorLevel( "%" + bean.getDoctorLevel() + "%");
        }
        if (StringUtils.isNotEmtity(bean.getHospital())) {
            bean.setHospital( "%" + bean.getHospital() + "%");
        }
        List<DoctorResponseBean> list = drugUserService.doctorPage(bean);
        Integer count = drugUserService.doctorPageCount(bean);
        PageResponseBean<DoctorResponseBean> responseBean = new PageResponseBean<>(bean,count,list);
        return responseBean;
    }

    @Cacheable(value = "virtual_rep_api_doctor", key = "'_stat_'+#drugUserId")
    public DoctorStatResponseBean stat(Long drugUserId) {
        DoctorStatResponseBean responseBean = new DoctorStatResponseBean();
        Map<String, Long> map = doctorRepository.statDrugUserDoctorNum("%," + drugUserId + ",%");
        if (map != null) {
            responseBean.setDoctorNum(map.get("doctorNum") != null ? Integer.valueOf(map.get("doctorNum") + "") : 0);
            responseBean.setHospitalNum(map.get("hospitalNum") != null ? Integer.valueOf(map.get("hospitalNum") + "") : 0);
        }
        return responseBean;
    }

    @Transactional(readOnly = false)
    @CacheEvict(value = "virtual_rep_api_doctor", allEntries = true)
    public Boolean save(DoctorRequestBean bean) {
        Doctor doctor = doctorRepository.findTopByMobile(bean.getMobile());
        DoctorVirtual virtual = new DoctorVirtual();
        if (doctor == null) {
            doctor = new Doctor();
            virtual.setDrugUserIds(this.assembleLeaderPath(bean.getLeaderPath(),bean.getDrugUserId()));
        } else {
            virtual = doctor.getDoctorVirtual();
            virtual.setDrugUserIds(this.assembleLeaderPath(this.assembleLeaderPath(virtual.getDrugUserIds(),bean.getDrugUserId()),bean.getDrugUserId()));
        }
//        BeanUtils.copyProperties(bean,doctor);
        doctor.setCity(bean.getCity());
        //doctor.setClientLevel(bean.getClientLevel());
        doctor.setDepartment(bean.getDepartment());
        doctor.setDoctorLevel(bean.getDoctorLevel());
       // doctor.setHospitalLevel(bean.getHospitalLevel());
        doctor.setHospitalName(bean.getHospitalName());
        doctor.setMobile(bean.getMobile());
        doctor.setName(bean.getName());
        doctor.setStatus(1);
        virtual.setClientLevel(bean.getClientLevel());
        virtual.setHospitalLevel(bean.getHospitalLevel());
        //TODO  获取主数据id
        logger.info("保存【{}】医生时查询主数据对应的医生id写入数据库", doctor.getName());
        if (StringUtils.isNotEmtity(bean.getHospitalName())) {
            Hcp hcp = masterDataService.getHcpByHciIdAndHcpName(bean.getHospitalName(), bean.getName());
            if (hcp != null) {
                logger.info("保存【{}】医生时查询主数据对应的医生id写入数据库,写入成功", doctor.getName());
                //doctor.setMasterDateId(hcp.getId());
                virtual.setMasterDateId(hcp.getId());
                doctor.setHospitalId(hcp.getHciId());
            }
        }

        //TODO 营销数据
//        DoctorVo vo = centerDataService.checkout(doctor);
//        if(vo!=null){
//            doctor.setEappId(vo.getId());
//        }

        doctor = doctorRepository.saveAndFlush(doctor);
        if (doctor.getId() == null) {
            throw new BusinessException(ErrorEnum.ERROR.getStatus(), "医生添加失败");
        }
        virtual.setDoctorId(doctor.getId());
        doctorVirtualService.save(virtual);
        doctor.setDoctorVirtual(virtual);
        doctorRepository.saveAndFlush(doctor);
        //TODO 添加关系到关系表
        DrugUserDoctor dud = new DrugUserDoctor();
        dud.setDoctorId(doctor.getId());
        dud.setProductId(bean.getProductId());
        dud.setDrugUserId(bean.getDrugUserId());
        dud.setCreateTime(new Date());
        drugUserDoctorRepository.saveAndFlush(dud);


        Boolean flag = DoctorDynamicFieldValueService.add(doctor.getId(),bean.getList());
        if (!flag) {
            throw new BusinessException(ErrorEnum.ERROR.getStatus(), "医生动态属性数据添加修改");
        }
        return true;
    }

    @Transactional(readOnly = false)
    @CacheEvict(value = "virtual_rep_api_doctor", allEntries = true)
    public Boolean update(DoctorRequestBean bean) {
        Doctor doctor = doctorRepository.findTopByMobile(bean.getMobile());
        DoctorVirtual virtual = new DoctorVirtual();
        if (doctor == null) {
            doctor = new Doctor();
            virtual.setDrugUserIds(this.assembleLeaderPath(bean.getLeaderPath(),bean.getDrugUserId()));
        } else {
            virtual = doctor.getDoctorVirtual();
            virtual.setDrugUserIds(this.assembleLeaderPath(this.assembleLeaderPath(virtual.getDrugUserIds(),bean.getDrugUserId()),bean.getDrugUserId()));
        }
//        BeanUtils.copyProperties(bean,doctor);
        doctor.setCity(bean.getCity());
        //doctor.setClientLevel(bean.getClientLevel());
        doctor.setDepartment(bean.getDepartment());
        doctor.setDoctorLevel(bean.getDoctorLevel());
        //doctor.setHospitalLevel(bean.getHospitalLevel());
        doctor.setHospitalName(bean.getHospitalName());
        doctor.setMobile(bean.getMobile());
        doctor.setName(bean.getName());
        virtual.setClientLevel(bean.getClientLevel());
        virtual.setHospitalLevel(bean.getHospitalLevel());
        //TODO  获取主数据id
        if (StringUtils.isNotEmtity(bean.getHospitalName())) {
            Hcp hcp = masterDataService.getHcpByHciIdAndHcpName(bean.getHospitalName(), bean.getName());
            if (hcp != null) {
//                doctor.setMasterDateId(hcp.getId());
                virtual.setMasterDateId(hcp.getId());
                doctor.setHospitalId(hcp.getHciId());
            }
        }

        //TODO 营销数据
//        DoctorVo vo = centerDataService.checkout(doctor);
//        if(vo!=null){
//            doctor.setEappId(vo.getId());
//        }

        doctor = doctorRepository.saveAndFlush(doctor);
        if (doctor.getId() == null) {
            throw new BusinessException(ErrorEnum.ERROR.getStatus(), "医生修改失败");
        }

        virtual.setDoctorId(doctor.getId());
        doctorVirtualService.save(virtual);

        doctor.setDoctorVirtual(virtual);
        doctorRepository.saveAndFlush(doctor);
        //TODO 添加关系到关系表
        DrugUserDoctor dud = drugUserDoctorRepository.findByDoctorIdAndDrugUserIdAndProductId(doctor.getId(), bean.getDrugUserId(), bean.getProductId());
        if (dud == null) {
            dud = new DrugUserDoctor();
            dud.setDoctorId(doctor.getId());
            dud.setProductId(bean.getProductId());
            dud.setDrugUserId(bean.getDrugUserId());
            dud.setCreateTime(new Date());
            drugUserDoctorRepository.saveAndFlush(dud);
        }


        Boolean flag = DoctorDynamicFieldValueService.add(doctor.getId(), bean.getList());
        if (!flag) {
            throw new BusinessException(ErrorEnum.ERROR.getStatus(), "医生动态属性数据修改修改");
        }
        return true;
    }

//    @Transactional(readOnly = false)
//    public Boolean save(List<DraTable> list){
//        draTableRepository.save(list);
//        return true;
//    }

    @Transactional(readOnly = false)
    @CacheEvict(value = "virtual_rep_api_doctor", allEntries = true)
    public Boolean saves(List<DoctorExcel> list) {
        List<String> mobiles = new ArrayList<>();
        for (int i = 0, leng = list.size(); i < leng; i++) {
            DoctorExcel excel = list.get(i);
            if (StringUtils.isNotEmtity(excel.getMobile())) {
                mobiles.add(excel.getMobile());
            }
        }
        List<Doctor> doctors = new ArrayList<>();
        if (!mobiles.isEmpty()) {
            doctors = this.findByMobileIn(mobiles);
        }

        Map<String, DrugUser> map = new HashMap<>();
        List<Doctor> savelist = new ArrayList<>();
        for (int i = 0, leng = list.size(); i < leng; i++) {
            DoctorExcel excel = list.get(i);
            Doctor doctor = new Doctor();
            DoctorVirtual virtual = new DoctorVirtual();
            if (doctors != null && !doctors.isEmpty() && StringUtils.isNotEmtity(excel.getMobile())) {
                for (Doctor d : doctors) {
                    if (d.getMobile().equals(excel.getMobile())) {
                        doctor = d;
                    }
                }
            }
            if(doctor.getMobile()!=null){
                virtual = doctor.getDoctorVirtual();
            }
            doctor.setCity(excel.getCity());
            doctor.setName(excel.getDoctorName());
            doctor.setHospitalName(excel.getHospitalName());
            doctor.setDepartment(excel.getDepartment());
            doctor.setProvince(excel.getProvince());
            doctor.setDoctorLevel(excel.getPosition());
            doctor.setMobile(excel.getMobile());
            virtual.setClientLevel(excel.getSex());
            //TODO 主数据id
            logger.info("保存【{}】医生时查询主数据对应的医生id写入数据库", doctor.getName());
            if (StringUtils.isNotEmtity(excel.getHospitalName())) {
                Hcp hcp = masterDataService.getHcpByHciIdAndHcpName(excel.getHospitalName(), excel.getDoctorName());
                if (hcp != null) {
                    logger.info("保存【{}】医生时查询主数据对应的医生id写入数据库,写入成功", doctor.getName());
                    virtual.setMasterDateId(hcp.getId());
                    doctor.setHospitalId(hcp.getHciId());
                    virtual.setHospitalLevel("");
                }
            }

            Long drugUserId = null;
            //TODO 销售代表
            logger.info("保存【{}】医生时查询销售坐席写入医生表", doctor.getName());
            if (StringUtils.isNotEmtity(excel.getDrugUserEmail())) {
                if (map.get(excel.getDrugUserEmail()) == null) {
                    DrugUser drugUser = drugUserService.findByEmail(excel.getDrugUserEmail());
                    if (drugUser != null) {
                        if (StringUtils.isNotEmtity(virtual.getDrugUserIds())) {
                            virtual.setDrugUserIds(virtual.getDrugUserIds() + drugUser.getId() + ",");
                            virtual.setDrugUserIds(this.assembleLeaderPath(virtual.getDrugUserIds()+drugUser.getLeaderPath(),drugUser.getId()));
                        } else {
                            virtual.setDrugUserIds(this.assembleLeaderPath(drugUser.getLeaderPath(),drugUser.getId()));
                        }
                        map.put(excel.getDrugUserEmail(), drugUser);
                        drugUserId = drugUser.getId();
                    }
                } else {
                    if (StringUtils.isNotEmtity(virtual.getDrugUserIds())) {
                        virtual.setDrugUserIds(this.assembleLeaderPath(virtual.getDrugUserIds() + map.get(excel.getDrugUserEmail()).getLeaderPath() ,map.get(excel.getDrugUserEmail()).getId()));
                    } else {
                        virtual.setDrugUserIds(this.assembleLeaderPath(map.get(excel.getDrugUserEmail()).getLeaderPath(),map.get(excel.getDrugUserEmail()).getId()));
                    }
                    drugUserId = map.get(excel.getDrugUserEmail()).getId();
                }

            }
            //TODO 营销id
//          DoctorVo vo = centerDataService.checkout(doctor);
//          if (vo != null) {
//              doctor.setEappId(vo.getId());
//          }
            doctorRepository.saveAndFlush(doctor);
            virtual.setDoctorId(doctor.getId());
            doctorVirtualService.save(virtual);
            //TODO 添加关系到关系表

            savelist.add(doctor);
        }
        doctorRepository.updateVirtualDoctorId();

        //TODO 添加关系到关系表
        return true;
    }

    @Transactional(readOnly = false)
    @CacheEvict(value = "virtual_rep_api_doctor", allEntries = true)
    public Boolean saves(List<DoctorExcel> list,Long productId,DrugUser user) {
        List<String> mobiles = new ArrayList<>();
        for (int i = 0, leng = list.size(); i < leng; i++) {
            DoctorExcel excel = list.get(i);
            if (StringUtils.isNotEmtity(excel.getMobile())) {
                mobiles.add(excel.getMobile());
            }
        }
        List<Doctor> doctors = new ArrayList<>();
        if (!mobiles.isEmpty()) {
            doctors = this.findByMobileIn(mobiles);
        }

        Map<String, DrugUser> map = new HashMap<>();
        List<Doctor> savelist = new ArrayList<>();
        for (int i = 0, leng = list.size(); i < leng; i++) {
            DoctorExcel excel = list.get(i);
            Doctor doctor = new Doctor();
            DoctorVirtual virtual = new DoctorVirtual();
            if (doctors != null && !doctors.isEmpty() && StringUtils.isNotEmtity(excel.getMobile())) {
                for (Doctor d : doctors) {
                    if (d.getMobile().equals(excel.getMobile())) {
                        doctor = d;
                    }
                }
            }
            if(doctor.getMobile()!=null){
                virtual = doctor.getDoctorVirtual();
            }
            doctor.setCity(excel.getCity());
            doctor.setName(excel.getDoctorName());
            doctor.setHospitalName(excel.getHospitalName());
            doctor.setDepartment(excel.getDepartment());
            doctor.setProvince(excel.getProvince());
            doctor.setDoctorLevel(excel.getPosition());
            doctor.setMobile(excel.getMobile());
            virtual.setClientLevel(excel.getSex());
            //TODO 主数据id
            logger.info("保存【{}】医生时查询主数据对应的医生id写入数据库", doctor.getName());
            if (StringUtils.isNotEmtity(excel.getHospitalName())) {
                Hcp hcp = masterDataService.getHcpByHciIdAndHcpName(excel.getHospitalName(), excel.getDoctorName());
                if (hcp != null) {
                    logger.info("保存【{}】医生时查询主数据对应的医生id写入数据库,写入成功", doctor.getName());
                    virtual.setMasterDateId(hcp.getId());
                    doctor.setHospitalId(hcp.getHciId());
                    virtual.setHospitalLevel("");
                }
            }

            //TODO 销售代表
            logger.info("保存【{}】医生时查询销售坐席写入医生表", doctor.getName());
            if (StringUtils.isNotEmtity(excel.getDrugUserEmail())) {
                if (map.get(excel.getDrugUserEmail()) == null) {
                    DrugUser drugUser = drugUserService.findByEmail(excel.getDrugUserEmail());
                    if (drugUser != null) {
                        if (StringUtils.isNotEmtity(virtual.getDrugUserIds())) {
                            virtual.setDrugUserIds(virtual.getDrugUserIds() + drugUser.getId() + ",");
                            virtual.setDrugUserIds(this.assembleLeaderPath(virtual.getDrugUserIds()+drugUser.getLeaderPath(),drugUser.getId()));
                        } else {
                            virtual.setDrugUserIds(this.assembleLeaderPath(drugUser.getLeaderPath(),drugUser.getId()));
                        }
                        map.put(excel.getDrugUserEmail(), drugUser);
                    }
                } else {
                    if (StringUtils.isNotEmtity(virtual.getDrugUserIds())) {
                        virtual.setDrugUserIds(this.assembleLeaderPath(virtual.getDrugUserIds() + map.get(excel.getDrugUserEmail()).getLeaderPath() ,map.get(excel.getDrugUserEmail()).getId()));
                    } else {
                        virtual.setDrugUserIds(this.assembleLeaderPath(map.get(excel.getDrugUserEmail()).getLeaderPath(),map.get(excel.getDrugUserEmail()).getId()));
                    }
                }

            }
            //TODO 营销id
//          DoctorVo vo = centerDataService.checkout(doctor);
//          if (vo != null) {
//              doctor.setEappId(vo.getId());
//          }
            doctorRepository.saveAndFlush(doctor);
            virtual.setDoctorId(doctor.getId());
            doctorVirtualService.save(virtual);
            //TODO 添加关系到关系表
            DrugUserDoctor dud = drugUserDoctorRepository.findByDoctorIdAndDrugUserIdAndProductId(doctor.getId(), user.getId(), productId);
            if (dud == null) {
                dud = new DrugUserDoctor();
                dud.setDoctorId(doctor.getId());
                dud.setProductId(productId);
                dud.setDrugUserId(user.getId());
                dud.setCreateTime(new Date());
                drugUserDoctorRepository.saveAndFlush(dud);
            }
            savelist.add(doctor);
        }
        doctorRepository.updateVirtualDoctorId();

        //TODO 添加关系到关系表
        return true;
    }

    @Transactional(readOnly = false)
    public boolean delete(RelationRequestBean bean) {
        List<Long> ids = bean.getIds();
        List<Long> pIds = bean.getpIds();
        if (ids != null && !ids.isEmpty()) {
            for (int i=0,leng=ids.size();i<leng;i++) {
                Long id = ids.get(i);
                List<DrugUserDoctor> list = drugUserDoctorRepository.findByDoctorIdAndProductId(id,pIds.get(i));
                DoctorVirtual virtual = doctorVirtualService.findByDoctorId(id);
                if(list!=null && !list.isEmpty()){
                    Map<String,String> map = new HashMap<>();
                    if(virtual!=null){
                        String drugUsreIds = virtual.getDrugUserIds();
                        if(StringUtils.isNotEmtity(drugUsreIds)){
                            String[] drugUserid = drugUsreIds.split(".");
                            for (String duserId:drugUserid) {
                                if(StringUtils.isNotEmtity(duserId)){
                                    map.put(duserId,duserId);
                                }
                            }
                        }
                    }
                    for (DrugUserDoctor drugUserDoctor:list) {
                        String value = map.get(drugUserDoctor.getDoctorId().toString());
                        if(StringUtils.isNotEmtity(value)){
                            map.remove(value);
                        }
                    }
                    StringBuffer sb = new StringBuffer(",");
                    if(map!=null && !map.isEmpty()){

                        for (String key:map.keySet()) {
                            sb.append(map.get(key)+",");
                        }
                    }
                    virtual.setDrugUserIds(sb.toString());
                    doctorVirtualService.save(virtual);
                    drugUserDoctorRepository.delete(list);
                }
            }
        }
        return true;
    }

    @Transactional(readOnly = false)
    public boolean relation(RelationRequestBean bean) {
        List<Long> pId = bean.getpIds();

        List<Long> ids = bean.getIds();
        if (ids != null && !ids.isEmpty()) {
            for (Long id : ids) {
                List<DrugUserDoctor> drugUserDoctors = drugUserDoctorRepository.findByDoctorIdAndProductId(id,pId.get(0));
                DoctorVirtual virtual = doctorVirtualService.findByDoctorId(id);
                String drugUserIds = virtual.getDrugUserIds();
                if(drugUserDoctors!=null && !drugUserDoctors.isEmpty()){
                    for (DrugUserDoctor druguserdoctr:drugUserDoctors) {
                        drugUserIds = drugUserIds.replaceAll(","+druguserdoctr.getDrugUserId()+",",",");
                    }
                    drugUserDoctorRepository.delete(drugUserDoctors);
                }

                virtual.setDrugUserIds(drugUserIds+bean.getNewDrugUserId()+",");
                doctorVirtualService.save(virtual);
                DrugUserDoctor entity = new DrugUserDoctor();
                entity.setCreateTime(new Date());
                entity.setDrugUserId(bean.getNewDrugUserId());
                entity.setProductId(pId.get(0));
                entity.setDoctorId(id);
                drugUserDoctorRepository.saveAndFlush(entity);
            }
        }
        return true;
    }

    private String assembleLeaderPath(String drugUserIds,Long drugUserId){
        String[] ids = drugUserIds.split(",");
        Map<String,String> map = new HashMap<>();
        for (String id:ids) {
            if(StringUtils.isNotEmtity(id))
                map.put(id,id);
        }
        StringBuffer sb = new StringBuffer(",");
        map.put(drugUserId.toString(),drugUserIds.toLowerCase());
        for (String key:map.keySet()) {
            sb.append(map.get(key)+",");
        }
        return sb.toString();
    }
}
