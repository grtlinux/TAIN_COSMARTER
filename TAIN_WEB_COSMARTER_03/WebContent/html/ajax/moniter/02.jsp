<%@ page contentType="text/plain; charset=euc-kr" %>
<%@ page import = "tain.kr.com.proj.cosmarter.v04.bean.BeanCommand" %>
<%@ page import = "tain.kr.com.proj.cosmarter.v04.main.client.BeanClient" %>
<%
	boolean flag = true;

	String result = null;
	
	if (!flag) {
		/*
		 * before
		 */
		request.setCharacterEncoding("euc-kr");
		String name = request.getParameter("name");
		
		if (!flag) System.out.println("KANG : Hello");
		
		BeanCommand bean = new BeanCommand();
		
		bean.setName("lstResult");
		bean.setDesc("list Result");
		
		bean.setHost("127.0.0.1");
		bean.setPort("7412");
		
		bean.setCmd(new String[] { "cmd", "/c", "dir" });
		bean.setEnv(new String[] { "PARAM1=hello", "PARAM2=world" });
		bean.setDir("./");
		bean.setArgs(null);
		
		bean.setSkipCmd(new String[] { "W", "L2", "L10", "R3-7", "Y오전", "Y오후" });
		bean.setFldName(new String[] { "일자", "구분", "시간", "정보" });
		if (flag) bean.print();

		BeanClient.getInstance().process(bean);
		
		result = bean.getResult();
		if (flag) System.out.println(result);
		
		if (flag) result = result.replaceAll("<","&lt;").replaceAll(">","&gt;");
	}
	
	if (flag) {
		/*
		 * chart data
		 */
		result = "";
	}
%>
<%= result %>
['City', '2010 Population', '2000 Population'],
['New York City, NY', 8175000, 8008000],
['Los Angeles, CA', 3792000, 3694000],
['Chicago, IL', 2695000, 2896000],
['Houston, TX', 2099000, 1953000],
['Philadelphia, PA', 1526000, 1517000],
