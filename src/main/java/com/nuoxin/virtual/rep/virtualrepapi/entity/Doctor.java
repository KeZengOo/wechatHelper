package com.nuoxin.virtual.rep.virtualrepapi.entity;

import com.nuoxin.virtual.rep.virtualrepapi.common.entity.IdEntity;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by fenggang on 9/11/17.
 */
@Entity
@Table(name = "virtual_doctor")
public class Doctor extends IdEntity {

    private static final long serialVersionUID = 4739090689831737455L;
}
