package com.nuoxin.virtual.rep.api.common.service;

import com.nuoxin.virtual.rep.api.common.bean.PageRequestBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

/**
 * Created by fenggang on 9/13/17.
 */
public class BaseService {

    protected static Logger logger = LoggerFactory.getLogger(BaseService.class);

    protected PageRequest getPage(PageRequestBean bean){
        PageRequest pageable = new PageRequest(bean.getPage(), bean.getPageSize());
        return pageable;
    }

    protected PageRequest getPage(PageRequestBean bean, Sort sort){
        PageRequest pageable = new PageRequest(bean.getPage(), bean.getPageSize(),sort);
        return pageable;
    }
}
