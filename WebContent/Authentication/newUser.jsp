<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<%if (request.getAttribute("msg")!=null) {%>
<%=request.getAttribute("msg")%>
<%} %>
	<form method="post" action="./?action=newUser">
		What role should this user take?: 
		<select>
			<option value="subscriber">Subscriber</option>
			<option value="reporter">Reporter</option>
		</select> 
		<input type="submit" value="Create User" /> 
		<input type="submit" value="Cancel" />
	</form>
</body>
</html>