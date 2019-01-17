package com.nuoxin.virtual.rep.api.entity.v2_5;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * <p>
 * 产品与拜访结果表
 * </p>
 *
 * @author lichengxin
 * @since 2018-10-30
 */
@Data
public class ProductVisitResultDO implements Serializable {


    private static final long serialVersionUID = 1L;

	private Long id;

    /**
     * 产品id
     */
	private Long productId;
    /**
     * 拜访类型 1接触医生 2成功医生 3覆盖医生 可多选 例子：1,2
     */
	private String visitType;
    /**
     * 拜访将结果
     */
	private String visitResult;




}