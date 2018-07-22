package com.gjh.sssp.entity;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.validator.constraints.NotEmpty;
@Entity
public class CustomerMsg {
	private Integer id;
	@NotEmpty
	private String customer;
	@NotEmpty
	private String address;
	private CneeMsg cneeMsg;
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
	public String getCustomer() {
		return customer;
	}
	public void setCustomer(String customer) {
		this.customer = customer;
	}
	@Column(length=500)
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
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
	@JoinColumn(name="CNEE_ID",unique=true)
	@OneToOne(cascade=CascadeType.REMOVE,fetch=FetchType.EAGER)
	public CneeMsg getCneeMsg() {
		return cneeMsg;
	}
	public void setCneeMsg(CneeMsg cneeMsg) {
		this.cneeMsg = cneeMsg;
	}
	public CustomerMsg() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public CustomerMsg(Integer id, String customer, String address, CneeMsg cneeMsg, String update_user,
			Date update_time) {
		super();
		this.id = id;
		this.customer = customer;
		this.address = address;
		this.cneeMsg = cneeMsg;
		this.update_user = update_user;
		this.update_time = update_time;
	}
	@Override
	public String toString() {
		return "CustomerMsg [id=" + id + ", customer=" + customer + ", address=" + address + ", cneeMsg=" + cneeMsg
				+ ", update_user=" + update_user + ", update_time=" + update_time + "]";
	}
	

}
