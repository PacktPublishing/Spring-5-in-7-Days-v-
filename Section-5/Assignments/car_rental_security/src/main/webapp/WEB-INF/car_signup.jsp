<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Spring5-in-7Days</title>
</head>
<body>
	Hello and Welcome to Spring5-in-7days tutorial
	<br>
	<br>
	<c:if test="${not empty error}">
	<h3 style="color:red;">${error}</h3>
	</c:if>
	<c:if test="${not empty errorList}">
	<c:forEach var="validationError" items="${errorList}">
		<h3 style="color:red;">${validationError}</h3>
	</c:forEach>
	</c:if>
	<table>
		<tr>
			<td>
				<fieldset style="width: fit-content;">
					<legend>Car Registration</legend>
					<c:url value="/car/registration" var="signUpUrl" />
					<form action="${signUpUrl}" method="post">
						<table>
							<tr>
								<td>License No</td>
								<td><input type="text" name="licensePlate"
									placeholder="Enter Car license Number"></td>
							</tr>
							<tr>
								<td>Rental cost</td>
								<td><input type="text" name="cost"
									placeholder="Enter rental cost"></td>
							</tr>
							<tr>
								<td>Car Manufacturer</td>
								<td><input type="text" name="manufacturer"
									placeholder="Enter manufacturer"></td>
							</tr>
							<tr>
								<td></td>
								<td><input type="submit" value="Register"></td>
							</tr>
						</table>
					</form>
				</fieldset>
			</td>
		</tr>
	</table>
	
</body>
</html>