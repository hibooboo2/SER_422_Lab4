
<head>
<style>
#homeButton {
	position: absolute;
	top: 1px;
	right: 15px;
	width: 100px;
}

#reporterMenu {
	position: absolute;
	top: 1px;
	right: 115px;
	width: 40px;
}
</style>
<%
	if (request.getSession(false) == null)
	{
		request.getSession();
		request.getSession().setAttribute("role", "Guest");
		request.getSession().setAttribute("user", "none");
	}
%>
</head>
<%
	if (((String) request.getSession(false).getAttribute("role")).equalsIgnoreCase("Reporter"))
	{
%>
<div id="reporterMenu">
	<a href="?action=createNewsStory"><img src="http://goo.gl/pXWTGl"
		alt="Create New News Article" height="40" width="40"> </a> <br>
	Create Article
</div>
<%
	}
%>
<div id="homeButton">
	<table>
		<tr>
			<td><a href="./?action=news"><img src="http://goo.gl/Ac8kn9"
					alt="Home Button" height="40" width="40"> </a> <%
 	if (request.getSession().getAttribute("user") == "none")
 	{
 %> <a href="?action=login"><img
					src="http://i.imgur.com/5INsaoH.png" alt="Login Button"> </a> <%
 	}
 	else
 	{
 %> <a href="?action=logout"><img
					src="http://i.imgur.com/pZLEL8e.png" alt="Logout Button"> </a> <%
 	}
 %></td>
		</tr>
	</table>

</div>