<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<h2>NEW News</h2>
<p>NEW news is a news resource about NEW stuff.</p>
<p>NEW news is a fictional organization managing fictional news
	items for a non-fictional web programming class.</p>
<pre>
This is an hardcoded page. In fact this page should display all 
dynamically (whether you are logged in
or not, and whether you want
news item titles, with the proper links in the bottom nav generated
to create a new news story if you are a reporter).
</pre>
<br />
<table border="0">
	<tr>
		<td><a href="./about.html">About</a></td>
<jsp:include page="./login.html"></jsp:include> 
		<td><a href="./login.html">Login</a></td>
	</tr>
</table>

</body>
</html>
<!-- Diplay Articles.Based on Permissions Per role. Edit Delete Links Based on permissions.  
	Have Login button. To go to Login.
	About goes to a site About Page.
 -->