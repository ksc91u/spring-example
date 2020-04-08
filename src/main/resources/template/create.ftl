<html>
<head>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">
    <title>新增</title>

    <#include "./common/header.ftl">

</head>
<body>
<div class="row">
    <div class="col d-flex justify-content-center">
    <form action="/add" method="post">
        標題：<input name="title" type="text"><br>
        內文：<textarea name="content"></textarea><br>
        目標 URL：<input name="routeUrl"><br>
        縮圖 URL：<input name="imgUrl"><br>
        <input value="送出表單" type="submit">
    </form>
    </div>
</div>
</body>
</html>

