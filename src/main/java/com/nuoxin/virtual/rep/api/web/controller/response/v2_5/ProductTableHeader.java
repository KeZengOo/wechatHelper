package com.nuoxin.virtual.rep.api.web.controller.response.v2_5;

import java.util.ArrayList;
import java.util.List;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

@ApiModel
@EqualsAndHashCode(callSuper=false)
@Data
public class ProductTableHeader extends TableHeader {
	private List<TableHeader>info = new ArrayList<>(5);
}
