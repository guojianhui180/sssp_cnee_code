package com.gjh.sssp.handler;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.gjh.sssp.entity.CneeMsg;
import com.gjh.sssp.entity.CustomerMsg;
import com.gjh.sssp.handleexception.ExcelHasNoData;
import com.gjh.sssp.handleexception.FileTypeNotAllowed;
import com.gjh.sssp.handleexception.NoExcelException;
import com.gjh.sssp.service.CneeMsgService;
import com.gjh.sssp.service.CustomerMsgService;
import com.gjh.sssp.utils.GetCellValue;
import com.gjh.sssp.utils.ParseSessionObj;
import com.gjh.sssp.utils.UserMsg;

@Controller
public class CustomerMsgHandler {
	@Autowired
	private CustomerMsgService customerMsgService;
	@Autowired
	private CneeMsgService cneeMsgService;

	@RequestMapping("/msg/save")
	public String saveCustomerFromExcel() {
		List<CustomerMsg> list = new ArrayList<CustomerMsg>();
		List<CneeMsg> list1 = new ArrayList<CneeMsg>();
		CneeMsg c11 = new CneeMsg(null, "cnee1");
		CneeMsg c22 = new CneeMsg(null, "cnee2");
		CneeMsg c33 = new CneeMsg(null, "cnee3");
		list1.add(c11);
		list1.add(c22);
		list1.add(c33);
		CustomerMsg c1 = new CustomerMsg(null, "customer1", "address1", c11, "gegjh1", new Date());
		CustomerMsg c2 = new CustomerMsg(null, "customer2", "address2", c22, "gegjh2", new Date());
		CustomerMsg c3 = new CustomerMsg(null, "customer3", "address3", c33, "gegjh3", new Date());
		list.add(c1);
		list.add(c2);
		list.add(c3);
		cneeMsgService.save(list1);
		customerMsgService.save(list);
		return "success";
	}

	@RequestMapping("/msg/list")
	public String listCustomerMsg(Map<String, Object> map, CustomerMsg msg,
			@RequestParam(value = "pageNo", required = false) Integer pageNo) {
		if (pageNo == null) {
			pageNo = 0;
		} else {
			pageNo = pageNo - 1;
		}
		Page<CustomerMsg> page = customerMsgService.getCustomerByPage(pageNo, 5, msg);
		map.put("page", page);
		return "list";
	}

	@ResponseBody
	@RequestMapping(value = "/msg/{id}", method = RequestMethod.DELETE, produces = { "text/html;charset=UTF-8;",
			"application/json;" })
	public String customerMsgDelete(@PathVariable("id") Integer id) {
		customerMsgService.delete(id);
		// return "redirect:/msg/list";
		return "删除成功!";
	}

	/**
	 * 获取id的CustomerMsg转向input
	 * 
	 * @param id
	 * @param map
	 * @return
	 */
	@RequestMapping(value = "/msg/{id}", method = RequestMethod.GET)
	public String customerMsgInput(@PathVariable("id") Integer id, Map<String, Object> map) {
		CustomerMsg customerMsg = customerMsgService.getById(id);
		map.put("customerMsg", customerMsg);
		return "input";
	}

	@RequestMapping(value = "/msg/{id}", method = RequestMethod.PUT)
	public String customerMsgDelete1( @Valid CustomerMsg customerMsg,BindingResult result,@PathVariable("id") Integer id,HttpSession session) {
		if(result.hasErrors()) {
			System.out.println("Error ...");
			return "redirect:/msg/"+id;
		}
		customerMsg.setUpdate_time(new Date());
		customerMsg.setUpdate_user(getUser(session));
		customerMsgService.saveOne(customerMsg);
		return "redirect:/msg/list";
	}

	private String getUser(HttpSession session) {
		// TODO Auto-generated method stub
		UserMsg userMsg=ParseSessionObj.getUserMsg(session);
		return userMsg.getName();
	}

	@RequestMapping("/msg/export/csv")
	public ResponseEntity<byte[]> customerMsgExport() {
		byte[] body = customerMsgService.getCusomerMsg().getBytes();
		MultiValueMap<String, String> headers = new HttpHeaders();
		headers.add("Content-Disposition", "attachment; filename=CustomerMsg.csv");
		HttpStatus statusCode = HttpStatus.OK;
		ResponseEntity<byte[]> responseEntity = new ResponseEntity<byte[]>(body, headers, statusCode);
		return responseEntity;
	}

	@RequestMapping("/msg/download/module")
	public ResponseEntity<byte[]> customerDownloadModlue(HttpServletRequest request) throws IOException {
		String path = request.getServletContext().getRealPath("/files/module.xlsx");
		FileInputStream inputStream = new FileInputStream(new File(path));
		int length = inputStream.available();
		byte[] body = new byte[length];
		inputStream.read(body);
		MultiValueMap<String, String> headers = new HttpHeaders();
		headers.add("Content-Disposition", "attachment; filename=module.xlsx");
		HttpStatus statusCode = HttpStatus.OK;
		ResponseEntity<byte[]> responseEntity = new ResponseEntity<byte[]>(body, headers, statusCode);
		return responseEntity;
	}

	@RequestMapping("/msg/upload")
	public String customerUploadAddMsg(@RequestParam("file")MultipartFile file,HttpSession session){
		if(file.isEmpty()) {
			throw new NoExcelException();
		}
		if(!file.getContentType().trim().equals("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")) {
			throw new FileTypeNotAllowed();
		}
		String user=getUser(session);
		InputStream inputStream = null;
		try {
			inputStream = file.getInputStream();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        XSSFWorkbook workbook = null;
		try {
			workbook = new XSSFWorkbook(inputStream);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        XSSFSheet sheet1 = workbook.getSheet("Sheet1");
        if (sheet1 == null) {
            System.out.print("未找到工作表Sheet1");
            throw new NoExcelException("未找到工作表Sheet1");
        } else {
        	int lastRowNum = sheet1.getLastRowNum();
        	System.out.println(lastRowNum);
        	if(lastRowNum<1) {
        		throw new ExcelHasNoData();
        	}
        	List<CustomerMsg> list_customer=new ArrayList<CustomerMsg>();
    		List<CneeMsg> list_cnee=new ArrayList<CneeMsg>();
    		for (int i = 1; i <= lastRowNum; i++) {
                XSSFRow row = sheet1.getRow(i);
                String customer = GetCellValue.getVale(row.getCell(0)).toString();
                String address = GetCellValue.getVale(row.getCell(1)).toString();
                String cnee=GetCellValue.getVale(row.getCell(2)).toString();
                CneeMsg cneeMsg=new CneeMsg();
                cneeMsg.setCnee(cnee);
                cneeMsg.setUpdate_time(new Date());
                cneeMsg.setUpdate_user(user);
                CustomerMsg customerMsg=new CustomerMsg();
                customerMsg.setCustomer(customer);
                customerMsg.setAddress(address);
                customerMsg.setUpdate_time(new Date());
                customerMsg.setUpdate_user(user);
                customerMsg.setCneeMsg(cneeMsg);
//                cneeMsg.setCustomerMsg(customerMsg);
                list_cnee.add(cneeMsg);
                list_customer.add(customerMsg);
                
    		}
    		cneeMsgService.save(list_cnee);
    		customerMsgService.save(list_customer);
        }
		return "success";
	}

	// @InitBinder
	// public void InitBinder(WebDataBinder binder) {
	// binder.setDisallowedFields("update_user");
	// }

}
