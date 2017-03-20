<%@ page contentType="text/plain; charset=euc-kr" %>
<%@ page import = "tain.kr.com.proj.cosmarter.v01.bean.SimpleBean" %>
<%@ page import = "tain.kr.com.proj.cosmarter.v01.main.client.CoBeanClient" %>
<%
	boolean flag = true;

	request.setCharacterEncoding("euc-kr");
	String code = request.getParameter("code");
	
	// System.out.println("KANG : Hello");
	
	SimpleBean bean = new SimpleBean();
	bean.setIpAddr("172.30.225.53");
	bean.setPort("7412");
	
	bean.setName("lstps");

	if ("01".equals(code)) {
		bean.setCmd("/hw01/ibridge/KANG/tools/Run_01_01.sh");
	} else if ("02".equals(code)) {
		bean.setCmd("/hw01/ibridge/KANG/tools/Run_01_02.sh");
	} else if ("03".equals(code)) {
		bean.setCmd("/hw01/ibridge/KANG/tools/Run_01_03.sh");
	}
		
	bean.setRetInfo("fld1:1");
	bean.setSkip("0");
	
	CoBeanClient.getInstance().process(bean);
	
	String message = bean.getResult();
	
	if (flag)
		message = message.replaceAll("<","&lt;").replaceAll(">","&gt;");
%>
<%= message %>
