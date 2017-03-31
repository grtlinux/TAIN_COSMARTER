<%@ page contentType="text/plain; charset=euc-kr" %>
<%@ page import = "java.text.SimpleDateFormat" %>
<%@ page import = "java.util.Date" %>
<%
	boolean flag = true;

	String strDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new Date());
	
	if (flag) System.out.printf("KANG [%s]\n", strDate);
	
	String message = String.format("Hello world!!!![%s]", strDate);
%>
<%= message %>
