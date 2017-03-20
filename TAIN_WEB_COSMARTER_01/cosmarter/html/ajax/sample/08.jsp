<%@ page contentType="text/plain; charset=euc-kr" %>
<%@ page import = "tain.kr.com.proj.cosmarter.v04.bean.BeanCommand" %>
<%@ page import = "tain.kr.com.proj.cosmarter.v04.main.client.BeanClient" %>
<%
	boolean flag = true;

	request.setCharacterEncoding("euc-kr");
	String name = request.getParameter("name");
	
	System.out.println("KANG : Hello");
	
	BeanCommand bean = new BeanCommand();
	
	bean.setName("testCommand");
	bean.setDesc("test command");
	
	bean.setHost("127.0.0.1");
	bean.setPort("7412");
	
	bean.setCmd(new String[] { "cmd", "/c", "dir" });
	bean.setEnv(new String[] { "PARAM1=hello", "PARAM2=world" });
	bean.setDir("./");
	bean.setArgs(null);
	
	bean.setSkipCmd(new String[] { "W" });
	bean.setFldName(new String[] { "fld1" });
	if (flag) bean.print();

	BeanClient.getInstance().process(bean);
	
	String result = bean.getResult();
	if (flag) System.out.println(result);
	
	if (flag) result = result.replaceAll("<","&lt;").replaceAll(">","&gt;");
%>

<%= result %>