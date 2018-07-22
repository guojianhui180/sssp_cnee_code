package com.gjh.sssp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gjh.sssp.entity.CneeMsg;
import com.gjh.sssp.entity.CustomerMsg;
import com.gjh.sssp.repository.CneeMsgRepository;
@Service
public class CneeMsgService {
	@Autowired
	private CneeMsgRepository repository;
	@Transactional
	public Integer save(List <CneeMsg> list) {
		return repository.save(list).size();
		
	}
	

}
