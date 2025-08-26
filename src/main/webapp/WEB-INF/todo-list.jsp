<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="model.Todo" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>ToDo一覧画面</title>
</head>
<body>
    <h1>ToDo一覧</h1>

	<%-- メッセージ表示 --%>
    <% String message = (String) request.getAttribute("message"); %>
    <% if (message != null) { %>
        <p style="color: green;"><%= message %></p>
    <% } %>
    
    <a href="${pageContext.request.contextPath}/todo/add">新規登録</a>

    <ul>
    <% List<Todo> todos = (List<Todo>) request.getAttribute("todos"); %>
    <% for (Todo todo : todos) { %>
        <li>
            <%= todo.getTitle() %>
            <a href="${pageContext.request.contextPath}/todo/delete?id=<%= todo.getId() %>">削除</a>
        </li>
    <% } %>
    </ul>
</body>
</html>
