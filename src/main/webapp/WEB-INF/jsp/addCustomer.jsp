<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">

<head>

<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">
<meta name="description" content="">
<meta name="author" content="">

<title>Sales Management application - Add a customer</title>

<!-- Custom fonts for this template -->
<link href="/vendor/fontawesome-free/css/all.min.css" rel="stylesheet"
	type="text/css">
<link
	href="https://fonts.googleapis.com/css?family=Nunito:200,200i,300,300i,400,400i,600,600i,700,700i,800,800i,900,900i"
	rel="stylesheet">

<!-- Custom styles for this template -->
<link href="/css/sb-admin-2.min.css" rel="stylesheet">

<!-- Custom styles for this page -->
<link href="/vendor/datatables/dataTables.bootstrap4.min.css"
	rel="stylesheet">

</head>

<body id="page-top">

	<!-- Page Wrapper -->
	<div id="wrapper">

		<!-- Sidebar -->
		<%@ include file="includes/sidebar.jsp"%>
		<!-- End of Sidebar -->

		<!-- Content Wrapper -->
		<div id="content-wrapper" class="d-flex flex-column">

			<!-- Main Content -->
			<div id="content">

				<!-- Topbar -->
				<%@ include file="includes/topbar.jsp"%>
				<!-- End of Topbar -->

				<!-- Begin Page Content -->
				<div class="container-fluid">
					<c:if test="${permission==2}">
						<!-- DataTales Example -->
						<div class="card shadow mb-4">
							<div class="card-body">
								<form action="/Customers/addCustomer/add" method="post">
									<fieldset>
										<!-- Form Name -->
										<legend>Add a new customer</legend>
										<c:if test="${customer==null}">
											<!-- Text input-->
											<div class="form-group">
												<label class="col-md-12 control-label" for="name">Name</label>
												<div class="col-md-12">
													<input id="name" name="name" type="text"
														placeholder="name of customer"
														class="form-control input-md" required="required">
												</div>
											</div>
											<!-- Text input-->
											<div class="form-group">
												<label class="col-md-12 control-label" for="lastName">Last
													name</label>
												<div class="col-md-12">
													<input id="lastName" name="lastName" type="text"
														placeholder="last name of customer"
														class="form-control input-md" required="required">
												</div>
											</div>
											<!-- Text input-->
											<div class="form-group">
												<label class="col-md-12 control-label" for="phoneNumber">Phone
													number</label>
												<div class="col-md-12">
													<input id="phoneNumber" name="phoneNumber" type="text"
														placeholder="Phone number of customer"
														class="form-control input-md-12" required="required">
												</div>
											</div>
											<!-- Text input-->
											<div class="form-group">
												<label class="col-md-12 control-label" for="address">Address</label>
												<div class="col-md-12">
													<input id="address" name="address" type="text"
														placeholder="Address of customer"
														class="form-control input-md-12" required="required">
												</div>
											</div>
										</c:if>
										<c:if test="${customer!=null}">
											<!-- Text input-->
											<div class="form-group">
												<label class="col-md-12 control-label" for="name">Name</label>
												<div class="col-md-12">
													<input id="name" name="name" type="text"
														placeholder="name of customer"
														class="form-control input-md-12" required="required"
														value="${customer.name}">

													<div class="form-group">
														<c:forEach items="${errors}" var="error">
															<c:if
																test="${error.key.substring(0, error.key.length() - 1)=='name'}">
																<span class="help-block text-danger">${error.value}</span>
																<br>
															</c:if>
														</c:forEach>
													</div>
												</div>
											</div>
											<!-- Text input-->
											<div class="form-group">
												<label class="col-md-12 control-label" for="lastName">Last
													name</label>
												<div class="col-md-12">
													<input id="lastName" name="lastName" type="text"
														placeholder="last name of customer"
														class="form-control input-md-12" required="required"
														value="${customer.lastName}">
													<div class="form-group">
														<c:forEach items="${errors}" var="error">
															<c:if
																test="${error.key.substring(0, error.key.length() - 1)=='lastName'}">
																<span class="help-block text-danger">${error.value}</span>
																<br>
															</c:if>
														</c:forEach>
													</div>
												</div>
											</div>
											<!-- Text input-->
											<div class="form-group">
												<label class="col-md-12 control-label" for="phoneNumber">Phone
													number</label>
												<div class="col-md-12">
													<input id="phoneNumber" name="phoneNumber" type="text"
														placeholder="Phone number of customer"
														class="form-control input-md-12" required="required"
														value="${customer.phoneNumber}">
													<div class="form-group">
														<c:forEach items="${errors}" var="error">
															<c:if
																test="${error.key.substring(0, error.key.length() - 1)=='phoneNumber'}">
																<span class="help-block text-danger">${error.value}</span>
																<br>
															</c:if>
														</c:forEach>
													</div>
												</div>
											</div>
											<!-- Text input-->
											<div class="form-group">
												<label class="col-md-12 control-label" for="address">Address</label>
												<div class="col-md-12">
													<input id="address" name="address" type="text"
														placeholder="Address of customer"
														class="form-control input-md-12" required="required"
														value="${customer.address}">
													<div class="form-group">
														<c:forEach items="${errors}" var="error">
															<c:if
																test="${error.key.substring(0, error.key.length() - 1)=='address'}">
																<span class="help-block text-danger">${error.value}</span>
																<br>
															</c:if>
														</c:forEach>
													</div>
												</div>
											</div>
										</c:if>
										<!-- Button (Double) -->
										<div class="form-group">
											<div class="col-md-12">
												<button id="button1id" name="button1id"
													class="btn btn-success" type="submit">Add</button>
												<a id="button2id" class="btn btn-danger" href="/Customers">Cancel</a>
											</div>
										</div>
									</fieldset>
								</form>
							</div>
						</div>
					</c:if>
					<c:if test="${permission<2}">
						<%@ include file="includes/404.jsp"%>
					</c:if>
				</div>
				<!-- /.container-fluid -->

			</div>
			<!-- End of Main Content -->

			<!-- Footer -->
			<%@ include file="includes/footer.html"%>
			<!-- End of Footer -->

		</div>
		<!-- End of Content Wrapper -->

	</div>
	<!-- End of Page Wrapper -->

	<!-- Scroll to Top Button-->
	<a class="scroll-to-top rounded" href="#page-top"> <i
		class="fas fa-angle-up"></i>
	</a>

	<!-- Logout Modal-->
	<%@ include file="includes/modal.jsp"%>
	<!-- End of Logout Modal-->

	<!-- Bootstrap core JavaScript-->
	<script src="/vendor/jquery/jquery.min.js"></script>
	<script src="/vendor/bootstrap/js/bootstrap.bundle.min.js"></script>

	<!-- Core plugin JavaScript-->
	<script src="/vendor/jquery-easing/jquery.easing.min.js"></script>

	<!-- Custom scripts for all pages-->
	<script src="/js/sb-admin-2.min.js"></script>

	<!-- Page level plugins -->
	<script src="/vendor/datatables/jquery.dataTables.min.js"></script>
	<script src="/vendor/datatables/dataTables.bootstrap4.min.js"></script>

	<!-- Page level custom scripts -->
	<script src="/js/demo/datatables-demo.js"></script>

</body>

</html>