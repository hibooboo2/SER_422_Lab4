<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<form action="./?action=addNewsArticle" method="post">
<input type="text" name="newsTitle" id="titleField"></input>
<textarea name="newsStroy" id="storyField"></textarea>
<% request.getSession(false).setAttribute("currentAction", "addNewsArticle"); %>
<input type="submit" value="Publish News Story">
</form>
</body>
</html>