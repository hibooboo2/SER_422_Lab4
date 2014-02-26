<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>I have No IDEA News</title>
</head>
<% for (int i=0;i<4;i++){ %>
<jsp:include page="./NewsArticle/NewsArticle.jsp"></jsp:include><form action="CRUDArticle" method="post"><input value="Delete" type="submit"></button></form>
<%} %>
<jsp:include page="./login.html"></jsp:include> 
<table border="0">
	<tr>
		<td><a href="./about.html">About</a></td>
		<td><a href="./login.html">Login</a></td>
	</tr>
</table>

</body>
</html>
<!-- Diplay Articles.Based on Permissions Per role. Edit Delete Links Based on permissions.  
	Have Login button. To go to Login.
	About goes to a site About Page.
 -->