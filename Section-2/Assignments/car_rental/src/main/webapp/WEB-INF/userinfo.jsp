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
			<tr>
				<td>Address</td>
				<td>${user.address}</td>
			</tr>
			<tr>
				<td>Age</td>
				<td>${user.age}</td>
			</tr>
			<tr>
				<td>Wallet</td>
				<td>${user.wallet}</td>
			</tr>
		</table>
		
		<fieldset style="width: fit-content;">
					<legend>Update My Details</legend>
					<c:url value="/user/updatePage" var="updatePage" />
					<form action="${updatePage}" method="post">
						<input type="hidden" name="id" value="${user.userId}" />
						<input type="submit" value="Update My Details" />
					</form>
		</fieldset>
		
		<fieldset style="width: fit-content;">
					<legend>Delete My Account</legend>
					<c:url value="/user/delete" var="deletePage" />
					<form action="${deletePage}" method="post">
						<input type="hidden" name="userId" value="${user.userId}" />
						<input type="submit" value="Delete My Account" />
					</form>
		</fieldset>
	</c:if>
	<div id="next" class="modalDialog">
		<div>
		<c:url var="infoUrl" value="/user/searchById"></c:url>
		<form action="${infoUrl}" method="post">
			<input type="hidden" name="userid" value="${redirectedUser.userId}" />
			<input type="submit" class="close" value="X" />
		</form>
		<c:if test="${not empty redirectedUser}">
		<h3 style="color:green;">${success}</h3>
		<table>
			<tr>
				<td>Name</td>
				<td>${redirectedUser.userName}</td>
			</tr>
			<tr>
				<td>Phone No</td>
				<td>${redirectedUser.phone}</td>
			</tr>
			<tr>
				<td>Address</td>
				<td>${redirectedUser.address}</td>
			</tr>
			<tr>
				<td>Age</td>
				<td>${redirectedUser.age}</td>
			</tr>
			<tr>
				<td>Wallet</td>
				<td>${redirectedUser.wallet}</td>
			</tr>
		</table>
		</c:if>
		</div>
	</div>
	
	<div id="update" class="modalDialog">
		<div>
		<c:url var="infoUrl" value="/user/searchById"></c:url>
		<form action="${infoUrl}" method="post">
			<input type="hidden" name="userid" value="${toUpdate.userId}" />
			<input type="submit" class="close" value="X" />
		</form>
		<fieldset style="width: fit-content;">
					<legend>User Update</legend>
					<c:url value="/user/update" var="updateUrl" />
					<form action="${updateUrl}" method="post">
						<table>
							<tr>
								<td>Name</td>
								<td><input type="text" name="userName"
									value="${toUpdate.userName}"></td>
							</tr>
							<tr>
								<td>Phone No</td>
								<td><input type="text" name="phone"
									value="${toUpdate.phone}"></td>
							</tr>
							<tr>
								<td>Address</td>
								<td><input type="text" name="address"
									value="${toUpdate.address}"></td>
							</tr>
							<tr>
								<td>Age</td>
								<td><input type="text" name="age"
									value="${toUpdate.age}"></td>
							</tr>
							<tr>
								<td>Wallet</td>
								<td><input type="text" name="wallet"
									value="${toUpdate.wallet}"></td>
							</tr>
							<tr>
								<td><input type="hidden" name="userId" value="${toUpdate.userId}"></td>
								<td><input type="submit" value="Update"></td>
							</tr>
						</table>
					</form>
				</fieldset>
		</div>
	</div>
	
	<br/>
	<br/>
	
	<fieldset style="width: fit-content;">
					<legend>Car Selection</legend>
					<c:url value="/user/carselection" var="selectionUrl" />
					<form action="${selectionUrl}" method="post">
						<table>
							<tr>
							<td>
							<select name="carId">
							<c:forEach var="car" items="${carList}">
							<option value="${car.carId}">${car.licensePlate}</option>
							</c:forEach>
							</select>
							
							</td>
							</tr>
							<tr>
								<td><input type="hidden" name="userId" value="${user.userId}"></td>
								<td><input type="submit" value="Select"></td>
							</tr>
						</table>
					</form>
				</fieldset>
	
	<br/>
	<br/>
	Go Home-->
	<c:url var="homeUrl" value="/"></c:url>
	<a href="${homeUrl}">Click Here to Go Home</a> 
</body>
</html>