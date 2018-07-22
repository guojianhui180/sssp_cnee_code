<%@page import="org.springframework.data.domain.Page"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src="${pageContext.request.contextPath }/scripts/jquery-1.9.1.min.js"></script>
<script>
   $(function(){
	   $(".delete").click(function(){
		   var url=this.href;
		   var flag=confirm("确定要删除该条记录吗?");
		   if(flag){
			  // $("#delete_form").attr("action",url);
			   //$("#delete_form").submit();
			   $.ajax({
	                "url":url,
	                "type":"DELETE",
	                success:function(data){
	                    alert(data);
	                    $(":button[name=query]").click();
	                },
	                error:function(e){
	                    alert("错误！！");
	                }
	            }); 
			   
			   
		   }
		   return false;
	   });
	   $(".link").click(function(){
			  var url=this.href;
			  var customer=$("input[name=customer]").val();
			  customer=$.trim(customer);
			  var address=$("input[name=address]").val();
			  address=$.trim(address);
			  var cnee=$("input[name=cnee]").val();
			  cnee=$.trim(cnee);
			  var querydata={"customer":customer,"address":address,"cneeMsg.cnee":cnee};
			  $.post(url,querydata,function(data){
				  $("#divcontent").html(data);
				  
			  });
			  return false;
		  });
	   
   });
</script>
<title>Insert title here</title>
</head>
<body>
   <form id="delete_form" action="" method="POST">
      <input type="hidden" name="_method" value="DELETE">
   </form>
   <c:if test="${page==null || page.numberOfElements == 0 }">
               没有任何记录
   </c:if>
   <c:if test="${page!=null && page.numberOfElements>0 }">
      <table cellpadding="10px" cellspacing="0px" border="1px">
          <tr> 
              <th>address</th>
              <th>customer</th>
              <th>cnee</th>
              <th>update_time</th>
              <th>update_user</th>
              <th>edit</th>
              <th>delete</th>
          </tr>
          <c:forEach items="${page.content }" var="msg">
             <tr>
                <td>${msg.address}</td>
                <td>${msg.customer}</td>
                <td>${msg.cneeMsg.cnee}</td>
                <td>${msg.update_time}</td>
                <td>${msg.update_user}</td>
                <td><a class="edit" href="${pageContext.request.contextPath}/msg/${msg.id}">Edit</a>
                <td><a class="delete" href="${pageContext.request.contextPath}/msg/${msg.id}">Delete</a>
             </tr>
          </c:forEach>
          <tr>
            <td colspan="7">
            <!-- 不是第一页则有上一页 -->
            <c:if test="${page.number!=null && page.number>0}">
               <a class="link" href="${pageContext.request.contextPath }/msg/list?pageNo=${page.number + 1 - 1 }">上一页</a>&nbsp;
            </c:if>
            <c:set value="${page.number+1}" var="PAGE"></c:set>
            
            <c:if test="${PAGE != page.totalPages }">
					<a class="link" href="${pageContext.request.contextPath }/msg/list?pageNo=${page.number + 1 + 1 }">下一页</a>&nbsp;
			</c:if>
			&nbsp; 共${page.totalElements}条记录
		    </td>
          </tr>
      </table>
   </c:if>
</body>
</html>