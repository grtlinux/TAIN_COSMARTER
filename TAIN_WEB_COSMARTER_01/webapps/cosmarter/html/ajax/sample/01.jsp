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
	bean.setCmd("ps -ef | grep ibridge | grep -v grep | grep -v root | grep -v defunct");
	// bean.setCmd("ps -ef | grep ibridge | grep -v grep | grep -v root");
	bean.setRetInfo("uid:1, pid:1, ppid:1, cpu:1, stime:1, tty:1, time:1, cmd:1");
	bean.setSkip("0");
	
	CoBeanClient.getInstance().process(bean);
	
	String message = bean.getResult();
	
	if (flag)
		message = message.replaceAll("<","&lt;").replaceAll(">","&gt;");
%>
<%= message %>
