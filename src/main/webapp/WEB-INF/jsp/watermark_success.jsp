<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>图片缩放结果</title>
</head>
<body>
	<h2>图片缩放结果</h2>
	<table>
		<tr>
			<td width="50%" align="center">
				<img alt="原始图" src="${pageContext.request.contextPath }${imageUrl}" width="500">
			</td>
			<td width="50%" align="center">
				<img alt="水印图" src="${pageContext.request.contextPath }${watermarkImageUrl}" width="500">
			</td>
		</tr>
	</table>
	<hr>
	<a href="${pageContext.request.contextPath }/watermark/page.do">返回</a>
</body>
</html>