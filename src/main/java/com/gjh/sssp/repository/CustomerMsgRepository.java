package com.gjh.sssp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.gjh.sssp.entity.CustomerMsg;

public interface CustomerMsgRepository extends JpaRepository<CustomerMsg, Integer>,JpaSpecificationExecutor<CustomerMsg>{
	public CustomerMsg getById(Integer id);

}
