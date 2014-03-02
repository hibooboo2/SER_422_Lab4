<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<%
		if(((String)(request.getSession(false).getAttribute("role"))).equalsIgnoreCase("Reporter")) {
	%>
	<form action="./?action=createNewsStory" method="post">
		Story Title: <input type="text" name="newsTitle" id="titleField"></input><br />
		Story: <textarea name="newsStory" id="storyField"></textarea><br />
		Is this story public?: <select name="isPublic">
			<option value="true">Yes</option>
			<option value="false">No</option>
		</select>
		<input type="submit" value="Publish News Story">
	</form>
	<%
		}
	%>
	<jsp:include page="/goHome.jsp"></jsp:include>
</body>
</html>