package com.gjh.sssp.handleexception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.FORBIDDEN,reason="Excel里无任何文件")
public class ExcelHasNoData extends RuntimeException{
	private static final long serialVersionUID = 1L;

}
