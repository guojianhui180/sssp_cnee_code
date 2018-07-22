package com.gjh.sssp.handler;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gjh.sssp.utils.CharSetUtils;
@Controller
public class AuthorityHandler {
	@ResponseBody
	@RequestMapping("/saveAuthority")
	public String saveAuthority(@RequestParam("php_session") String php_session,HttpSession session) {
        String php_session_decode = CharSetUtils.decodeUnicode(php_session);
        String php_session_replace = php_session_decode.replace("\\", "");
        session.setAttribute("php_session_authority", php_session_replace);
        System.out.println("php_session---->"+php_session_replace);
		return "success";
	}
	
	@ResponseBody
	@RequestMapping("/saveUser")
	public String saveUser(@RequestParam("php_user") String php_session_user,HttpSession session) {
        String php_session_decode=CharSetUtils.decodeUnicode(php_session_user);
        String php_session=php_session_decode.replace("\\", "");
        session.setAttribute("php_session_user", php_session);
        System.out.println("php_session_authority---->"+php_session);
		return "success";
	}
	

}
