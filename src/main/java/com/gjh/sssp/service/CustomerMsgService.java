package com.gjh.sssp.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.MapAttribute;
import javax.persistence.metamodel.SingularAttribute;

import org.hibernate.dialect.function.VarArgsSQLFunction;
import org.hibernate.ejb.criteria.path.MapKeyHelpers.MapKeyAttribute;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gjh.sssp.entity.CustomerMsg;
import com.gjh.sssp.repository.CneeMsgRepository;
import com.gjh.sssp.repository.CustomerMsgRepository;

@Service
public class CustomerMsgService {
	@Autowired
	private CustomerMsgRepository customerMsgRepository;
	@Autowired
	private CneeMsgRepository cneeMsgRepository;
 
	@PersistenceContext
	private EntityManager manager;
	@Transactional
	public Integer save(List<CustomerMsg> list) {
		return customerMsgRepository.save(list).size();

	}

	@Transactional
	public Page<CustomerMsg> getCustomerByPage(int pageNo, int pageSize, final CustomerMsg msg) {
		PageRequest pageRequest = new PageRequest(pageNo, pageSize);
		Specification<CustomerMsg> specification = new Specification<CustomerMsg>() {

			public Predicate toPredicate(Root<CustomerMsg> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				// TODO Auto-generated method stub
				Path<Integer> path1 = root.get("id");
				Path<String> path2 = root.get("customer");
				Path<String> path3 = root.get("address");
				Path<String> path4 = root.get("cneeMsg").get("cnee");
				List<Predicate> list = new ArrayList<Predicate>();
				Predicate predicate_arr[] = new Predicate[4];
				if (msg.getCustomer() != null || msg.getAddress() != null || msg.getCneeMsg()!= null) {
					if (!msg.getCustomer().trim().equals("") && msg.getCustomer() != null) {
						list.add(cb.like(path2, "%" + msg.getCustomer() + "%"));
					}
					if (!msg.getAddress().trim().equals("") && msg.getAddress() != null) {
						list.add(cb.like(path3, "%" + msg.getAddress() + "%"));
					}
					if (!msg.getCneeMsg().getCnee().trim().equals("") && msg.getCneeMsg().getCnee() != null) {
						list.add(cb.like(path4, "%" + msg.getCneeMsg().getCnee() + "%"));
					}
				}
				//list.add(cb.gt(path1, 0));
				Predicate predicate = cb.and(list.toArray(new Predicate[list.size()]));
				return predicate;
			}
		};
		Page<CustomerMsg> page = customerMsgRepository.findAll(specification, pageRequest);
		return page;
	}

	@Transactional
	public void delete(Integer id) {
		customerMsgRepository.delete(id);
	}

	@Transactional
	public CustomerMsg getById(Integer id) {
		return customerMsgRepository.getById(id);
	}

	@Transactional
	public CustomerMsg saveOne(CustomerMsg customerMsg) {
		cneeMsgRepository.save(customerMsg.getCneeMsg());
		return customerMsgRepository.save(customerMsg);
	}
	@Transactional
	public String getCusomerMsg() {
		String sql="select a.customer,a.address,b.cnee,a.update_user,a.update_time from " + 
				"customer_msg a inner join cnee_msg b on a.cnee_id=b.id";
		List<Object[]> list=manager.createNativeQuery(sql).getResultList();
		StringBuffer buffer=new StringBuffer();
		String[] cSVTitle=new String[]{"Customer","Address","CNEE_CODE","UPDATE_USER","UPDATE_TIME"};
		for(String title:cSVTitle)
		{
			buffer.append("\"").append(title).append("\"").append(",");
		}
		buffer.deleteCharAt(buffer.length()-1);
		buffer.append("\r\n");
		for(Object[] obj:list)
		{
			buffer.append("\"").append(obj[0]).append("\"").append(",");
			buffer.append("\"").append(obj[1]).append("\"").append(",");
			buffer.append("\"").append(obj[2]).append("\"").append(",");
			buffer.append("\"").append(obj[3]).append("\"").append(",");
			buffer.append("\"").append(obj[4]).append("\"");
			buffer.append("\r\n");
		}
		return buffer.toString();
	}

}
