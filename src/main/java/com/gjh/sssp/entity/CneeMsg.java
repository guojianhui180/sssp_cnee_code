package com.gjh.sssp.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.validator.constraints.NotEmpty;
@Entity
public class CneeMsg {
	private Integer id;
	@NotEmpty
	private String cnee;
	private CustomerMsg customerMsg;
	private String update_user;
	private Date update_time;
	@GeneratedValue
	@Id
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getCnee() {
		return cnee;
	}
	public void setCnee(String cnee) {
		this.cnee = cnee;
	}
	
	public String getUpdate_user() {
		return update_user;
	}

	public void setUpdate_user(String update_user) {
		this.update_user = update_user;
	}

	@Temporal(TemporalType.TIMESTAMP)
	public Date getUpdate_time() {
		return update_time;
	}

	public void setUpdate_time(Date update_time) {
		this.update_time = update_time;
	}
	
    @OneToOne(mappedBy="cneeMsg",fetch=FetchType.EAGER)
	public CustomerMsg getCustomerMsg() {
		return customerMsg;
	}

	public void setCustomerMsg(CustomerMsg customerMsg) {
		this.customerMsg = customerMsg;
	}

	
	@Override
	public String toString() {
		return "CneeMsg [cnee=" + cnee + "]";
	}

	public CneeMsg(Integer id, String cnee, CustomerMsg customerMsg, String update_user, Date update_time) {
		super();
		this.id = id;
		this.cnee = cnee;
		this.customerMsg = customerMsg;
		this.update_user = update_user;
		this.update_time = update_time;
	}

	public CneeMsg(Integer id, String cnee) {
		super();
		this.id = id;
		this.cnee = cnee;
	}

	public CneeMsg() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	

}
