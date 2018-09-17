package com.nuoxin.virtual.rep.api.entity.v2_5;

import java.util.List;

import com.nuoxin.virtual.rep.api.common.bean.PageRequestBean;
import com.nuoxin.virtual.rep.api.common.bean.PageResponseBean;
import com.nuoxin.virtual.rep.api.web.controller.response.v2_5.TableHeader;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = false)
@Data
public class CustomerFollowUpPageResponseBean<T> extends PageResponseBean<T> {

	private static final long serialVersionUID = 1372479968903861151L;

	@ApiModelProperty(value = "表头信息")
	private List<TableHeader> tableHeaders;

	public CustomerFollowUpPageResponseBean(PageRequestBean bean, Integer count, List<T> list) {
		super(bean, count, list);
	}
}
