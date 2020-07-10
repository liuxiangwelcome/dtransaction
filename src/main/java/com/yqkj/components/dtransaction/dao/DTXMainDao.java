package com.yqkj.components.dtransaction.dao;

import com.yqkj.components.dtransaction.pojo.DTXMain;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface DTXMainDao extends JpaRepository<DTXMain, Long> {

    @Query(value = "select * from dtx_main where session_id = ?1 limit 1", nativeQuery = true)
    public DTXMain queryMainBySession(Long sessionId);
}
