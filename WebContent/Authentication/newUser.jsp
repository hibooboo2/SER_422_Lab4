<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta charset="ISO-8859-1">
</head>
<body>
	<jsp:include page="/header.jsp"></jsp:include>
	<%
		if (request.getAttribute("msg") != null)
		{
	%>
	<%=request.getAttribute("msg")%>
	<%
		}
	%>
	<form method="post" action="./?action=makeUser">
		What role should this user take?: <select name="role">
			<option value="subscriber">Subscriber</option>
			<option value="reporter">Reporter</option>
		</select> <input type="hidden" name="userID"
			value="<%=request.getParameter("userid")%>" /> <input type="submit"
			value="Create User" />
	</form>
	<form action="./">
		<input type="submit" value="Cancel" />
	</form>
</body>
</html>