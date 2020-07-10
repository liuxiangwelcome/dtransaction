package com.yqkj.components.dtransaction.dao;

import com.yqkj.components.dtransaction.pojo.DTXStep;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DTXStepDao extends JpaRepository<DTXStep, Long> {

    @Query(value = "select * from dtx_step where session_id = ?1 order by id", nativeQuery = true)
    public List<DTXStep> queryStepBySession(Long sessionId);
}
