package com.nuoxin.virtual.rep.api.dao;

import com.nuoxin.virtual.rep.api.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * Created by fenggang on 9/11/17.
 */
public interface QuestionRepository extends JpaRepository<Question,Long>,JpaSpecificationExecutor<Question> {

}