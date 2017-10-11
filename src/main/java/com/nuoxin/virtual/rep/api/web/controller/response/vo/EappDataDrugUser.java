package com.nuoxin.virtual.rep.api.web.controller.response.vo;

/**
 * Created by tiancun on 7/28/17.
 * <p></>
 * 营销中心药企用户
 */

public class EappDataDrugUser{

    private static final long serialVersionUID = 8109155258691598416L;

    private Long id;

    private String userName;

    private String email;

    private String password;

    private Long drugId;

    private String leaderPath;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Long getDrugId() {
        return drugId;
    }

    public void setDrugId(Long drugId) {
        this.drugId = drugId;
    }

    public String getLeaderPath() {
        return leaderPath;
    }

    public void setLeaderPath(String leaderPath) {
        this.leaderPath = leaderPath;
    }
}
