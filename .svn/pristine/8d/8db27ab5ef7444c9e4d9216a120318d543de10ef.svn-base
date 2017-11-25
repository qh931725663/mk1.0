<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="java.sql.SQLException"%>
<%@ page import="java.sql.ResultSet"%>
<%@ page import="java.sql.Connection"%>
<%@ page import="java.sql.DriverManager"%>
<%@ page import="java.sql.PreparedStatement"%>;
<%@ page import="com.haaa.cloudmedical.common.util.ReadFile"%>
<%@ page import="java.io.File"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>javabean javebean</title>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="????">
<link rel="stylesheet" type="text/css"
	href="Contents/shop/common/general/style/base.css" />
<link rel="stylesheet" type="text/css" href="style/error.css" />
<title>测试例子说明</title>

<script>!function(e) {
		var c = {
				nonSecure : "8123",
				secure : "8124"
			},
			t = {
				nonSecure : "http://",
				secure : "https://"
			},
			r = {
				nonSecure : "127.0.0.1",
				secure : "gapdebug.local.genuitec.com"
			},
			n = "https:" === window.location.protocol ? "secure" : "nonSecure";
		script = e.createElement("script"), script.type = "text/javascript", script.async = !0, script.src = t[n] + r[n] + ":" + c[n] + "/codelive-assets/bundle.js", e.getElementsByTagName("head")[0].appendChild(script)
	}(document);
</script>
</head>
<body>
	<%
		String path = application.getRealPath("/");

		String dir = new File(path).getAbsolutePath();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		String we = "WEB-INF";
		String[] wk = { "1", "2", "3", "4", "5" };
		String sdir = dir + "/" + we + "/" + "classes" + "/" + "dbconfig.properties";
		out.println("显示" + sdir);

		String aa = request.getParameter("fname");
		if ((aa == null) || aa.equals("")) {
		} else {
			File file = new File(sdir);
			wk = ReadFile.cs(ReadFile.readerFilecs(file));
			out.println("显示" + wk[1] + " " + wk[2] + " " + wk[3] + " " + wk[4]);

			conn = (Connection) DriverManager.getConnection(wk[1], wk[2], wk[3]);
			pstmt = (PreparedStatement) conn.prepareStatement(aa);
			rs = pstmt.executeQuery();
	%>







	<table>
		<%
			while (rs.next()) {
		%>
		<tr>
			<td><%=rs.getString(1)%></td>
			<td><%=rs.getString(2)%></td>
			<td><%=rs.getString(3)%></td>
		</tr>
		<%
			}

			}
		%>



	</table>
 
			
	
	


	<form action="/mkh1.0/jsp/test.jsp" method="get">
		First name: <input type="text" name="fname" /> <input type="submit"
			value="Submit" />
	</form>


</body>
</html>
