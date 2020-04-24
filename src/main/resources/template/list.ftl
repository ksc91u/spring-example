<html>
<head>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">
    <title>已建立的連結</title>

    <#include "./common/header.ftl">

</head>

<body>
<div class="row">
    <div class="col d-flex justify-content-center">
        <table id="articlesDataTable" class="table table-responsive table-bordered">
            <thead>
            <th>id</th>
            <th>hashKey 複製貼到 FB, 第一次可能不會成功抓到縮圖</th>
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
                    <td><a href="../share/${mapping.hashKey}" target="_blank">${mapping.hashKey}</a></td>
                    <td>${mapping.routeUrl}</td>
                    <td>${mapping.title}</td>
                    <td>${mapping.content}</td>
                    <td>${mapping.imgUrl}</td>
                </tr>
            </#list>
            </tbody>
        </table>
    </div>
</div>
</body>
</html>