<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8" />
<script>
</script>
</head>
<body>
<div>${userId}</div>
<form action="http://facebook.gamebox.com/facebook/delete" method="get">
	<input type="hidden" value="1" name="deleteStatus" />
	<input type="submit" value="delete"/>
</form>

</body>