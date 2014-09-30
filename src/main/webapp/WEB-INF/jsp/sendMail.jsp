<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>发送邮件</title>
<link rel="stylesheet" href="../editor/themes/simple/simple.css" charset="utf-8"/>
<script charset="utf-8" src="../js/jquery-1.11.1.js"></script>
<script charset="utf-8" src="../editor/kindeditor.js"></script>
<script charset="utf-8" src="../editor/lang/zh_CN.js"></script>
<script type="text/javascript">
var editor;
//关闭过滤模式，保留所有标签
KindEditor.options.filterMode = true;
KindEditor.ready(function(K) {
        editor = K.create('#editor_id', {
                themeType : 'simple'
        });
});

function sendData(){
	alert(editor.html());
}

</script>
</head>
<body>
 <form action="sendMail.do" method="post" onsubmit="sendData()">
 <textarea id="editor_id" name="content" style="width:700px;height:300px;">
  </textarea>
<input type="submit" name="提交" />
</form>
   
</body>
</html>