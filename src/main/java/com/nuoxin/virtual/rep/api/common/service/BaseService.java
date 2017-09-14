package com.nuoxin.virtual.rep.api.common.service;

import com.nuoxin.virtual.rep.api.common.bean.PageRequestBean;
import org.springframework.data.domain.PageRequest;

/**
 * Created by fenggang on 9/13/17.
 */
public class BaseService {

    protected PageRequest getPage(PageRequestBean bean){
        PageRequest pageable = new PageRequest(bean.getPage(), bean.getPageSize());
        return pageable;
    }
}
