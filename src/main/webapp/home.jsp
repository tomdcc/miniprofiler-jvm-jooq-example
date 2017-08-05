<%--
Copyright 2017 the original author or authors.

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
--%>

<%@ page import="io.jdev.miniprofiler.ScriptTagWriter" %>
<%@ page import="io.jdev.miniprofiler.examples.jooq.db.tables.pojos.Animal" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>MiniProfiler JVM jOOQ Example App</title>
    <link rel="stylesheet" href="css/bootstrap.min.css"/>
    <link rel="stylesheet" href="css/bootstrap-theme.min.css"/>
    <link rel="stylesheet" href="css/main.css"/>
</head>
<body>
<nav class="navbar navbar-default">
    <div class="container">
        <div class="navbar-header">
            <a class="navbar-brand" href="#">MiniProfiler JVM jOOQ Example App</a>
        </div>
    </div>
</nav>
<span class="arrow glyphicon glyphicon-arrow-up" aria-hidden="true"></span>
<div class="container">
    <h2>Animals</h2>
    <table class='table table-striped'>
        <thead>
        <tr>
            <th>ID</th>
            <th>Name</th>
            <th>Type</th>
        </tr>
        </thead>
        <tbody>
        <% for(Animal a : (List<Animal>) request.getAttribute("animals")) { %>
        <tr>
            <td><%= a.getId() %></td>
            <td><%= a.getName() %></td>
            <td><%= a.getType() %></td>
        </tr>
        <% } %>
        </tbody>
    </table>
</div>
<%= ((ScriptTagWriter) application.getAttribute("scriptTagWriter")).printScriptTag() %>
</body>
</html>
