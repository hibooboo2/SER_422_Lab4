<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

</head>
<body>
	<%@ page import="edu.asupoly.ser422.lab4.model.*"%>
	<jsp:include page="/header.jsp"></jsp:include>
	<%
		NewsItemBean article= (NewsItemBean) request.getAttribute("article");
	%>
	<form action="./?action=EditArticle" method="post">
		<input type="hidden" value="<%=article.getItemId()%>" name="articleID">
		Story Title: <input type="text" name="newsTitle" id="titleField"
			value="<%=article.getItemTitle()%>"></input><br /> Story:
		<textarea name="newsStory" id="storyField"><%=article.getItemStory()%></textarea>
		<br /> Is this story public?: <select name="isPublic">
			<option value="true" <%if (article.isPublic())
			{%> selected <%}%>>Yes</option>
			<option value="false" <%if (!article.isPublic())
			{%> selected
				<%}%>>No</option>
		</select> <input type="submit" value="Save News Story">
	</form>
</body>
</html>