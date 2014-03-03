<%@ page import="edu.asupoly.ser422.lab4.model.*"%>
<%
	//TODO Display Comments
		CommentBean comment= (CommentBean) request.getAttribute("comment");
%>
<p><%=comment.getComment()%></p>
<p>
	Author:
	<%=comment.getUserId()%></p>
<hr />