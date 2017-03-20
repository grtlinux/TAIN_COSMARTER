<%@ page contentType="text/plain; charset=euc-kr" %>
<%@ page import = "tain.kr.com.proj.cosmarter.v04.base.Version" %>
<%
	boolean flag = true;

	System.out.println("KANG");
	
	String message = "Hello world!!!!";
	message += Version.getInstance().getVersion();
%>
<%= message %>
