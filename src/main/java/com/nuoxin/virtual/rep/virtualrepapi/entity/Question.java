package com.nuoxin.virtual.rep.virtualrepapi.entity;

import com.nuoxin.virtual.rep.virtualrepapi.common.entity.IdEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

/**
 * Created by fenggang on 9/11/17.
 */
@Entity
@Table(name = "virtual_question")
public class Question extends IdEntity {

    private static final long serialVersionUID = 968093081188132499L;

    @Column(name = "type")
    private Integer type;
    @Column(name = "title")
    private String title;
    @Column(name = "options")
    private String options;
    @Column(name = "virtual_questionnaire_id")
    private Long optionsnaireId;
    @Column(name = "answer")
    private String answer;
    @Column(name = "create_time")
    private Date createTime;

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOptions() {
        return options;
    }

    public void setOptions(String options) {
        this.options = options;
    }

    public Long getOptionsnaireId() {
        return optionsnaireId;
    }

    public void setOptionsnaireId(Long optionsnaireId) {
        this.optionsnaireId = optionsnaireId;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}