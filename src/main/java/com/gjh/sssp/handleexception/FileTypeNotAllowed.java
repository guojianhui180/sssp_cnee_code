package com.gjh.sssp.handleexception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.FORBIDDEN,reason="文件类型不允许")
public class FileTypeNotAllowed extends RuntimeException {

	private static final long serialVersionUID = 1L;

}
