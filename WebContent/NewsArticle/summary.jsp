
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="edu.asupoly.ser422.lab4.model.*"%>
<%
	//TODO Implement Favorite (Favorite Button)
	//TODO Display Article
	NewsItemBean article= (NewsItemBean) request.getAttribute("article");
%>

<%
	boolean isReporter= ((String) request.getSession(false).getAttribute("role")).equalsIgnoreCase("Reporter");
	boolean isSubscriber= ((String) request.getSession(false).getAttribute("role")).equalsIgnoreCase("Subscriber");
	boolean didWriteArticle= ((String) request.getSession(false).getAttribute("user")).equalsIgnoreCase(article.getReporterId());
	if ((article.isPublic() || isSubscriber) || (isReporter && didWriteArticle))
	{
%>
	<tr>
		<td><a
			href="?action=viewArticle&articleID=<%=article.getItemId()%>"><%=article.getItemTitle()%></a></td>
		<td>
		<%
			if (didWriteArticle && isReporter)
				{
		%>
			<form method="post" action="./?action=DeleteArticle">
				<input value="Delete" type="submit" /> <input type="hidden"
					name="articleID" value="<%=article.getItemId()%>" />
			</form>
		
		<%
			}
		%>
		<%
			if (isSubscriber || isReporter)
				{
		%>
		
			<form method="post" action="./?action=favArticle">
				<input value="Favorite" type="submit" /> <input type="hidden"
					name="articleID" value="<%=article.getItemId()%>" />
			</form>
		<%
			}
		%>
		</td>
	</tr>

<%
	}
%>

