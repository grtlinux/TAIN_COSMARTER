<%@ page contentType="text/plain; charset=euc-kr" %>
<%@ page import = "tain.kr.common.Version" %>
<%
	boolean flag = true;

	System.out.println("KANG");
	
	String message = "Hello world!!!!";
	message += Version.getInstance().getDesc();
%>
<%= message %>
