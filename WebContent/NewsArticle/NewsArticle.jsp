<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<%@ page import="edu.asupoly.ser422.lab4.dao.*"%>
	<%@ page import="edu.asupoly.ser422.lab4.model.*"%>
	<%
		//TODO Implement Favorite (Favorite Button)
		NewsItemBean article= (NewsItemBean) request.getAttribute("article");
	%>
	<%
		boolean isReporter= ((String) request.getSession(false).getAttribute("role")).equalsIgnoreCase("Reporter");
		boolean isSubscriber= ((String) request.getSession(false).getAttribute("role")).equalsIgnoreCase("Subscriber");
		boolean isArticleAuthor= ((String) request.getSession(false).getAttribute("user")).equalsIgnoreCase(article.getReporterId());
		boolean isGuest= ((String) request.getSession(false).getAttribute("role")).equalsIgnoreCase("Guest");
		if ((article.isPublic() || isSubscriber) || (isReporter && isArticleAuthor))
		{
	%>
	<h1><%=article.getItemTitle()%></h1>
	<p>
		Written by:
		<%=article.getReporterId()%></p>
	<hr />
	<p><%=article.getItemStory()%></p>
	<hr />
	<h3>Comments:</h3>
	<%
		}
	%>
	<%
		for (int i= 0; i < article.getComments().length; i++)
		{
			request.setAttribute("comment", article.getComments()[i]);
	%>
	<jsp:include page="/Comments/view.jsp"></jsp:include>
	<%
		}
	%>
	<%
		if (!isGuest || (isReporter && isArticleAuthor))
		{
	%>
	<jsp:include page="/Comments/add.jsp"></jsp:include>
	<%
		}
	%>
</body>
</html>