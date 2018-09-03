package com.nuoxin.virtual.rep.api.common.bean;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author xiekaiyu
 */
@Data
@ApiModel
public class ResponseObj implements Serializable {

	private static final long serialVersionUID = -1282337275554135379L;
	
	@ApiModelProperty(value="状态")
    private Integer code = 200;
	@ApiModelProperty(value="返回消息（用于弹框提示）")
	private String message;
    @ApiModelProperty(value="返回数据")
    private Object data;
    @ApiModelProperty(value="描述(开发测试人员看的)")
    private String description;

    public static ResponseObj clone(String message,Integer status,String description){
        return new ResponseObj(message, status, description);
    }
    
    public ResponseObj(){
        super();
    }

    public ResponseObj(String message, Integer status, String description) {
        super();
        this.code = status;
        this.message=message;
        this.description = description;
    }
    
}
