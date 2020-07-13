package com.yqkj.components.dtransaction.dao;

import com.yqkj.components.dtransaction.pojo.DTXLogs;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DTXLogsDao extends JpaRepository<DTXLogs, Long> {
}
