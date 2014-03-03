<%@page import="org.apache.jasper.tagplugins.jstl.core.ForEach"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>I have No IDEA News</title>
</head>
<body>
	<%@ page import="edu.asupoly.ser422.lab4.dao.*"%>
	<%@ page import="edu.asupoly.ser422.lab4.model.*"%>
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
	<table>
	<%
		NewsItemBean[] stories= (NewsItemBean[]) request.getAttribute("stories");
		for (int i= 0; i < stories.length; i++)
		{
			request.setAttribute("article", stories[i]);
			request.getRequestDispatcher("NewsArticle/summary.jsp").include(request, response);
		}
	%>
	</table>
</body>
</html>
<!-- Diplay Articles.Based on Permissions Per role. Edit Delete Links Based on permissions.  
	Have Login button. To go to Login.
	About goes to a site About Page.
 -->