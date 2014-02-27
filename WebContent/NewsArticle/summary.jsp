<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="edu.asupoly.ser422.lab4.model.*"%>
<%
	//TODO Implement Favorite (Favorite Button)
	//TODO Display Article
	//TODO Display Comments
	//TODO Display Comment Addition Form
	NewsItemBean article= (NewsItemBean) request.getAttribute("article");
%>
<h3><a href="?action=viewArticle&articleID=<%=article.getItemId()%>"><%=article.getItemTitle()%></a><input value="Delete" type="submit" /> <input value="Favorite" type="submit" /></h3> 
<!-- Display Comments Here -->