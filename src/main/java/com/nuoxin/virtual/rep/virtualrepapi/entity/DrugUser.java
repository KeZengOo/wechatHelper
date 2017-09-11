package com.nuoxin.virtual.rep.virtualrepapi.entity;

import com.nuoxin.virtual.rep.virtualrepapi.common.entity.IdEntity;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by fenggang on 9/11/17.
 */
@Entity
@Table(name = "virtual_drug_user")
public class DrugUser extends IdEntity {

    private static final long serialVersionUID = 9090513690988883094L;

}
