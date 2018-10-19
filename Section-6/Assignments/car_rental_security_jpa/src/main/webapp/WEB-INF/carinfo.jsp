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
	<c:if test="${not empty error}">
	<h3 style="color:red;">${error}</h3>
	</c:if>
	<c:if test="${not empty success}">
		<h3 style="color:green;">${success}</h3>
	</c:if>
	<c:if test="${not empty car}">
		<h2>Car details</h2>
		<table>
			<tr>
				<td>License No</td>
				<td>${car.licensePlate}</td>
			</tr>
			<tr>
				<td>Rental Cost</td>
				<td>${car.cost}</td>
			</tr>
			<tr>
				<td>Manufacturer</td>
				<td>${car.manufacturer}</td>
			</tr>
		</table>
		
		<fieldset style="width: fit-content;">
					<legend>Update Car Details</legend>
					<c:url value="/car/updatePage" var="updatePage" />
					<form action="${updatePage}" method="post">
						<input type="hidden" name="id" value="${car.carId}" />
						<input type="submit" value="Update Car Details" />
					</form>
		</fieldset>
		
		<fieldset style="width: fit-content;">
					<legend>Unregister Car</legend>
					<c:url value="/car/delete" var="deletePage" />
					<form action="${deletePage}" method="post">
						<input type="hidden" name="carId" value="${car.carId}" />
						<input type="submit" value="Deregister Car" />
					</form>
		</fieldset>
	</c:if>
	<div id="next" class="modalDialog">
		<div>
		<c:url var="infoUrl" value="/car/searchById"></c:url>
		<form action="${infoUrl}" method="post">
			<input type="hidden" name="carid" value="${redirectedCar.carId}" />
			<input type="submit" class="close" value="X" />
		</form> 
		<c:if test="${not empty redirectedCar}">
		<h3 style="color:green;">${success}</h3>
		<table>
			<tr>
				<td>License Plate</td>
				<td>${redirectedCar.licensePlate}</td>
			</tr>
			<tr>
				<td>Cost</td>
				<td>${redirectedCar.cost}</td>
			</tr>
			<tr>
				<td>Manufacturer</td>
				<td>${redirectedCar.manufacturer}</td>
			</tr>
		</table>
		</c:if>
		</div>
	</div>
	
	<div id="update" class="modalDialog">
		<div>
		<c:url var="infoUrl" value="/car/searchById"></c:url>
		<form action="${infoUrl}" method="post">
			<input type="hidden" name="carid" value="${toUpdate.carId}" />
			<input type="submit" class="close" value="X" />
		</form>
		<fieldset style="width: fit-content;">
					<legend>Car Update</legend>
					<c:url value="/car/update" var="updateUrl" />
					<form action="${updateUrl}" method="post">
						<table>
							<tr>
								<td>License No</td>
								<td><input type="text" name="licensePlate"
									value="${toUpdate.licensePlate}"></td>
							</tr>
							<tr>
								<td>Rental Cost</td>
								<td><input type="text" name="cost"
									value="${toUpdate.cost}"></td>
							</tr>
							<tr>
								<td>manufacturer</td>
								<td><input type="text" name="manufacturer"
									value="${toUpdate.manufacturer}"></td>
							</tr>
							<tr>
								<td><input type="hidden" name="carId" value="${toUpdate.carId}"></td>
								<td><input type="submit" value="Update"></td>
							</tr>
						</table>
					</form>
				</fieldset>
		</div>
	</div>
	
	Go Home-->
	<c:url var="homeUrl" value="/"></c:url>
	<a href="${homeUrl}">Click Here to Go Home</a>
</body>
</html>