<%@ page contentType="text/plain; charset=euc-kr" %>
<%@ page import = "tain.kr.com.proj.cosmarter.v01.bean.SimpleBean" %>
<%@ page import = "tain.kr.com.proj.cosmarter.v01.main.client.CoBeanClient" %>
<%
	boolean flag = true;

	request.setCharacterEncoding("euc-kr");
	String name = request.getParameter("name");
	
	System.out.println("KANG : Hello");
	
	SimpleBean bean = new SimpleBean();
	bean.setIpAddr("172.30.225.53");
	bean.setPort("7412");
	
	bean.setName("lstps");
	bean.setCmd("netstat -naplt | grep ESTABL");
	bean.setRetInfo("fld1:1, fld2:0, fld3:0, fld4:1, fld5:1, fld6:1, prog:1");
	bean.setSkip("0");
	
	CoBeanClient.getInstance().process(bean);
	
	String message = bean.getResult();
	
	if (flag)
		message = message.replaceAll("<","&lt;").replaceAll(">","&gt;");
%>
<%= message %>
