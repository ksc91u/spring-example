<html>

<body>
<table id="articlesDataTable" class="table table-responsive table-bordered">
    <thead>
    <th>id</th>
    <th>hashKey</th>
    <th>routeUrl</th>
    <th>title</th>
    <th>content</th>
    <th>imgUrl</th>
    </thead>
    <tbody>
    <#-- 使用FTL指令 -->
    <#list mappings as mapping>
        <tr>
            <td>${mapping.id}</td>
            <td>${mapping.hashKey}</td>
            <td>${mapping.routeUrl}</td>
            <td>${mapping.title}</td>
            <td>${mapping.content}</td>
            <td>${mapping.imgUrl}</td>
        </tr>
    </#list>
    </tbody>
</table>
</body>
</html>