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
<table>
	<tr>
		<td><a
			href="?action=viewArticle&articleID=<%=article.getItemId()%>"><%=article.getItemTitle()%></a></td>
		<td>
			<form method="get" action="./?action=DeleteArticle">
				<input value="Delete" type="submit" />
			</form>
		</td>
		<td>
			<form method="get" action="./?action=favArticle">
				<input value="Favorite" type="submit" />
			</form>
		</td>
	</tr>
</table>

<!-- Display Comments Here -->