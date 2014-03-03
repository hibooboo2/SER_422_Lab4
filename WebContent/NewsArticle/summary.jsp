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

<%
boolean isReporter = ((String) request.getSession(false).getAttribute("role")).equalsIgnoreCase("Reporter");
boolean isSubscriber = ((String) request.getSession(false).getAttribute("role")).equalsIgnoreCase("Subscriber");
boolean didWriteArticle= ((String) request.getSession(false).getAttribute("user")).equalsIgnoreCase(article.getReporterId());
	if ((article.isPublic()||isSubscriber)||(isReporter&&didWriteArticle))
 	{
%>
<table>
	<tr>
		<td><a
			href="?action=viewArticle&articleID=<%=article.getItemId()%>"><%=article.getItemTitle()%></a></td>
		<% if(didWriteArticle&&isReporter){ %>
		<td>
			<form method="post" action="./?action=DeleteArticle">
				<input value="Delete" type="submit" />
				<input type="hidden" name="articleID" value="<%= article.getItemId() %>"/>
			</form>
		</td>
		<% } %>
		<% if(isSubscriber||isReporter){ %>
		<td>
			<form method="post" action="./?action=favArticle">
				<input value="Favorite" type="submit" />
				<input type="hidden" name="articleID" value="<%= article.getItemId() %>"/>
			</form>
		</td>
		<% } %>
	</tr>
</table>
<%
	}
%>
<!-- Display Comments Here -->