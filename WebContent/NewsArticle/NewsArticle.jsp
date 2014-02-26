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

//TODO Implement Favorite (Favorite Button)
//TODO Display Article
//TODO Display Comments
//TODO Display Comment Addition Form
%>
<jsp:useBean id="theArticle" class=edu.asupoly.ser422.lab4.model.NewsItemBean"></jsp:useBean>
<jsp:getProperty property="itemStory" name="theArticle"/>

</body>
</html>