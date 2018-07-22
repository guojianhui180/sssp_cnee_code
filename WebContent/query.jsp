<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<style type="text/css">
   body{
      font-size:12px;
      magin:0px;
      padding:0px;
   }
</style>

<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/jquery-1.9.1.min.js">
</script>
<script src="${pageContext.request.contextPath}/scripts/Ajax.js"></script>
<script src="${pageContext.request.contextPath}/scripts/jquery.blockUI.js"></script>
<script src="${pageContext.request.contextPath}/scripts/php_server_valiate.js"></script>
<script type="text/javascript">
  $(function(){
	  $(":button[name=query]").click(function(){
		  var customer=$("input[name=customer]").val();
		  customer=$.trim(customer);
		  var address=$("input[name=address]").val();
		  address=$.trim(address);
		  var cnee=$("input[name=cnee]").val();
		  cnee=$.trim(cnee);
		  var querydata={"customer":customer,"address":address,"cneeMsg.cnee":cnee};
		  $.post("msg/list",querydata,function(data){
			  $("#divcontent").html(data);
			  
		  });
		  
	  });
	  $(":button[name=export]").click(function(){
		  var customer=$("input[name=customer]").val();
		  customer=$.trim(customer);
		  var address=$("input[name=address]").val();
		  address=$.trim(address);
		  var cnee=$("input[name=cnee]").val();
		  cnee=$.trim(cnee);
		  var url="${pageContext.request.contextPath}/msg/export/csv?1=1";
		  if(customer!=""){
			  url+="&customer="+customer;
		  }
		  if(address!=""){
			  url+="&address="+address;
		  }
		  if(cnee!=""){
			  url+="&cneeMsg.cnee="+cnee;
		  }
		  location=url;
	  });
	  
  });
</script>
</head>
<body>
    <table border="0px" width="100%">
        <tr align="right">
           <td>Customer:<input type="text" name="customer"/></td>
           <td>Address:<input type="text" name="address"/></td>
           <td>CNEE code:<input type="text" name="cnee"/></td>
        </tr>
        <tr align="center">
           <td colspan="3">
                <input type="button" name="query" value="查询">
                <input type="button" name="export" value="导出">
           </td>
        </tr>
    </table>
    <div style="width:auto;align:center;padding-left:10%;padding-right:10%;margin-left:10%;margin-right:10%" id="divcontent"></div>
</body>
</html>