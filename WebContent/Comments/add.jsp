<h3>Add a Comment</h3>
<form action="./?action=addComment&articleID=${article.getItemId()}" method="post">
<textarea name="comment" id="commentField"></textarea>

<input type="hidden" name="from"value="<%=request.getQueryString()%>">
<input type="submit" value="Add Comment">
</form>
