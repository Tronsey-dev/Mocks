<%@ page import="ru.appline.users.logic.Model" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Title</title>
</head>
<body>
<h1>Домашняя страница пользователя</h1>
Введите Id пользователя ( 0 для вывода списка пользователей)
<br/>
Доступно: <% Model model = Model.getInstance();
    out.print(model.getFromList().size());
%>
<br/>
<form method="get" action="get">
    <label> ID:
        <input type="text" name="id"><br/>
    </label><br/>
    <button type="submit">Поиск</button>
</form>
<br/>
<a href="addUser.html">Создать нового пользователя</a>
</body>
</html>