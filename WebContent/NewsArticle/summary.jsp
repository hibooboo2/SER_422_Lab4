
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
	boolean canManageArticle= ((String) request.getAttribute("canManageArticle")).equalsIgnoreCase("true");
%>
<tr>
	<td><a
		href="?action=viewArticle&articleID=<%=article.getItemId()%>"><%=article.getItemTitle()%></a></td>
		<%
			if (canManageArticle)
			{
		%>
	<td>
		<form method="post" action="./?action=DeleteArticle">
			<INPUT TYPE="image" SRC="http://i.imgur.com/jxLMgcu.png" ALT="Delete">
			<input type="hidden" name="articleID"
				value="<%=article.getItemId()%>" />
		</form>
		</td><td>
		<form method="post" action="./?action=EditArticleScreen">
			<INPUT TYPE="image" SRC="http://i.imgur.com/5kwu1ga.png" ALT="Edit">
			<input type="hidden" name="articleID"
				value="<%=article.getItemId()%>" />
		</form>
		</td>
		 <%
 	}
 %> <%
 	String isFav= ((String) request.getAttribute("isFav"));
 	if (isFav.equalsIgnoreCase("false"))
 	{
 %>
		<td>
		<form method="post" action="./?action=favArticle">
			<INPUT TYPE="image" SRC="http://i.imgur.com/xDvlHnA.png"
				ALT="Favorite"> <input type="hidden" name="articleID"
				value="<%=article.getItemId()%>" />
		</form>
		</td>
		 <%
 	}
 	else
 	{
 %>
	<td>
		<form method="post" action="./?action=unFavArticle">
			<INPUT TYPE="image" SRC="http://i.imgur.com/EDyRVs5.png"
				ALT="UnFavorite"> <input type="hidden" name="articleID"
				value="<%=article.getItemId()%>" />
		</form>
	</td>
	<%
 	}
 %>
</tr>


