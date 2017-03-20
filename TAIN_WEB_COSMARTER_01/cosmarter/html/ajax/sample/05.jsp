<%@ page contentType="text/plain; charset=euc-kr" %>
<%@ page import = "tain.kr.com.proj.cosmarter.v01.bean.SimpleBean" %>
<%@ page import = "tain.kr.com.proj.cosmarter.v01.util.DateTime" %>
<%@ page import = "tain.kr.com.proj.cosmarter.v01.main.client.CoBeanClient" %>
<%
	boolean flag = true;

	request.setCharacterEncoding("euc-kr");
	String code = request.getParameter("code");
	
	String strDate = DateTime.getInstance().getYYYYMMDD();  // "20160509"
	
	System.out.println("KANG : Hello");
	
	SimpleBean bean = new SimpleBean();
	bean.setIpAddr("172.30.225.53");
	bean.setPort("7412");
	
	bean.setName("lstFile");
	bean.setCmd("ls -alF /hw01/ibridge/KANG/POSTNET_AGENT/env/attachment/received | grep " + strDate + " | egrep 'recv_|delv_'");
	bean.setRetInfo("fld1:0, fld2:0, fld3:0, fld4:0, fld5:1, fld6:1, fld7:1, fld8:1, fld9:1");
	
	bean.setSkip("0");
	
	CoBeanClient.getInstance().process(bean);
	
	String message = bean.getResult();
	
	if (flag)
		message = message.replaceAll("<","&lt;").replaceAll(">","&gt;");
%>
<%= message %>
