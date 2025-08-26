<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>ToDo登録画面</title>
</head>
<body>
    <h1>新しいToDoを登録</h1>

    <form action="${pageContext.request.contextPath}/todo/add" method="post">
        <input type="text" name="title" placeholder="タスクを入力" required>
        <input type="submit" value="登録">
    </form>

    <a href="${pageContext.request.contextPath}/todos">一覧に戻る</a>
</body>
</html>
