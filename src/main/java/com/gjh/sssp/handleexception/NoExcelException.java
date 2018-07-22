package com.gjh.sssp.handleexception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.FORBIDDEN,reason="未上传任何Excel文件或Excel没有Sheet1表")
public class NoExcelException extends RuntimeException {
	public NoExcelException(String string) {
		// TODO Auto-generated constructor stub
	}

	public NoExcelException() {
		// TODO Auto-generated constructor stub
	}

	private static final long serialVersionUID = 1L;

}
