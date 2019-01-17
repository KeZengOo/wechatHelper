package com.nuoxin.virtual.rep.api.entity.v2_5;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NonNull;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * 产品与拜访结果入参
 * </p>
 *
 * @author lichengxin
 * @since 2018-10-30
 */
@Data
public class ProductVisitResultParams {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    private Long id;

    /**
     * 产品id
     */
    @NotNull(message="productId is null")
    @ApiModelProperty(value = "产品id")
    private Long productId;

    /**
     * 拜访类型 1接触医生 2成功医生 3覆盖医生 可多选 例子：1,2
     */
    @ApiModelProperty(value = "前端不用传",hidden = true)
    private String visitType;

    /**
     * 拜访将结果
     */
    @NotNull(message="visitResult is null")
    @ApiModelProperty(value = "拜访将结果")
    private String visitResult;

    /**
     * 拜访类型集合
     */
    @NotNull(message="visitTypeList is null")
    @ApiModelProperty(value = "拜访类型 1接触医生 2成功医生 3覆盖医生")
    private List<Integer> visitTypeList;

}