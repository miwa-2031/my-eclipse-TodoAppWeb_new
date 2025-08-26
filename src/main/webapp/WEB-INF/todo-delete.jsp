<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="model.Todo" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>ToDo削除確認画面</title>
</head>
<body>
    <h1>削除確認</h1>

    <% Todo todo = (Todo) request.getAttribute("todo"); %>
    <p>本当に「<%= todo.getTitle() %>」を削除しますか？</p>

    <form action="${pageContext.request.contextPath}/todo/delete" method="post">
        <input type="hidden" name="id" value="<%= todo.getId() %>">
        <input type="submit" value="削除する">
    </form>

    <a href="${pageContext.request.contextPath}/todos">キャンセル</a>
</body>
</html>
