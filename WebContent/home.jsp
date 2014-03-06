<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>I have No IDEA News</title>
</head>
<body>
	<%@ page import="edu.asupoly.ser422.lab4.model.*"%>
	<%@ page import="java.util.Enumeration"%>
	<%
		Enumeration<String> attris= request.getSession().getAttributeNames();
		while (attris.hasMoreElements())
		{
			String attribute= attris.nextElement();
	%>
	<%=attribute%>
	<%=":"%>
	<%
		if (request.getSession() != null && request.getSession().getAttribute(attribute) != null)
			{
	%>
	<%=request.getSession().getAttribute(attribute)%>
	<%="  "%>
	<%
		}
		}
	%>
	<br>
	<%
		if (request.getParameter("msg") != null)
		{
	%>
	<%=request.getParameter("msg")%>
	<%
		}
	%>
	<table>
		<tr>
			<th>Stories</th>
			<th>Buttons</th>
		</tr>
		<%
			NewsItemBean[] favStories= (NewsItemBean[]) request.getAttribute("favstories");
			for (int i= 0; i < favStories.length; i++)
			{
				request.setAttribute("isFav", "true");
				request.setAttribute("article", favStories[i]);
		%>
		<jsp:include page="NewsArticle/summary.jsp"></jsp:include>
		<%
			}
		%>
		<%
			NewsItemBean[] notFavStories= (NewsItemBean[]) request.getAttribute("stories");
			for (int i= 0; i < notFavStories.length; i++)
			{
				request.setAttribute("isFav", "false");
				request.setAttribute("article", notFavStories[i]);
		%>
		<jsp:include page="NewsArticle/summary.jsp"></jsp:include>
		<%
			}
		%>
	</table>
</body>
</html>