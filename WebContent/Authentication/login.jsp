<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<body>
	<%
		if (request.getAttribute("msg") != null)
		{
	%>
	<%=request.getAttribute("msg")%>
	<%
		}
	%>
	<form method="post" action="?action=login">
		User ID: <input type="text" name="userid" size="25" /> Password: <input
			type="password" name="passwd" size="25" /><br />
		<p></p>
		<input type="submit" value="Login" /> <input type="reset"
			value="Reset" />
	</form>
	<jsp:include page="/goHome.jsp"></jsp:include>
</body>
</html>