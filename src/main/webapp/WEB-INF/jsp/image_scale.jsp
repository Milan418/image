<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>图片缩放</title>
</head>
<body>
	<h2>---图片上传---</h2>
	<div>
		<form id="upload_form" enctype="multipart/form-data" method="post" action="${pageContext.request.contextPath }/scale/upload.do">
			<input type="file" name="image" id="image">
			<input type="submit" value="上传">
		</form>
	</div>
</body>
</html>