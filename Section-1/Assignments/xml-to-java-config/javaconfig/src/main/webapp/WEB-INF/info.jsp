<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Spring5-in-7days</title>
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/styles/mainStyle.css" />
</head>
<body>
	<c:if test="${not empty name}">
	Hello <h2>${name}</h2>
	</c:if>
	<br>
	<c:if test="${not empty phone}">
	Your phone: ${phone}
	</c:if>
	<c:if test="${not empty error}">
	<h3 style="color:red;">${error}</h3>
	</c:if>
	<c:if test="${not empty user}">
		<h2>User details</h2>
		<table>
			<tr>
				<td>Name</td>
				<td>${user.userName}</td>
			</tr>
			<tr>
				<td>Phone No</td>
				<td>${user.phone}</td>
			</tr>
		</table>
	</c:if>
	<div id="next" class="modalDialog">
		<div>
		<c:url var="homeUrl" value="/"></c:url>
		<a href="${homeUrl}" title="Close" class="close">X</a> 
		<c:if test="${not empty redirectedUser}">
		<h3 style="color:green;">Signup successful with below details</h3>
		<table>
			<tr>
				<td>Name</td>
				<td>${redirectedUser.userName}</td>
			</tr>
			<tr>
				<td>Phone No</td>
				<td>${redirectedUser.phone}</td>
			</tr>
		</table>
		</c:if>
		</div>
	</div>
</body>
</html>