package com.gjh.sssp.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.taglibs.standard.lang.jstl.BooleanLiteral;
import org.hibernate.engine.jdbc.spi.ResultSetReturn;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.gjh.sssp.utils.ParseSessionObj;
import com.gjh.sssp.utils.UserMsg;

public class AuthorityInterceptor implements HandlerInterceptor {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		// TODO Auto-generated method stub
		String path=request.getServletPath();
		String method=request.getMethod().trim().toUpperCase();
		path=handlepath(path,method);
//		System.out.println("------>"+path);
		int authority_result=handleResult(path,request.getSession());
		if(authority_result==1) {
			response.sendRedirect(request.getServletContext().getContextPath()+"/noLogin.jsp");
			return false;
		}else if(authority_result==2) {
			response.sendRedirect(request.getServletContext().getContextPath()+"/noAuthorityjsp.jsp");
			return false;
		}
		return true;
	}

	private String handlepath(String path, String method) {
		int index=path.lastIndexOf("/");
		String laststr=path.substring(index+1);
		boolean b=isNumer(laststr);
		if(b) {
		    return path.substring(0, index+1)+method;
		}else {
			return path;
		}
	}

	private boolean isNumer(String laststr) {
		// TODO Auto-generated method stub
		for (int i = laststr.length();--i>=0;){  
		       if (!Character.isDigit(laststr.charAt(i))){
		           return false;
		       }
		   }
		   return true;
	}

	/**
	 * 
	 * @param path
	 * @param session
	 * @return
	 * 0 免验证   1 未登录   2 无权限
	 */
	private int  handleResult(String path, HttpSession session) {
		// TODO Auto-generated method stub
		if(path.indexOf(".js")!=-1) {
			//JS，JSP文件放行
			return 0;
		}
		if(path.trim().equals("/saveAuthority") || path.trim().equals("/saveUser")) {
			//权限验证文件放行
			return 0;
		}
		UserMsg userMsg=ParseSessionObj.getUserMsg(session);
		if(userMsg==null) {
			return 1;
		}
		boolean is_valid=ParseSessionObj.validate(session, path);
        if(!is_valid)
        {
            return 2;
        }
		return 0;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// TODO Auto-generated method stub
		
	}

}
