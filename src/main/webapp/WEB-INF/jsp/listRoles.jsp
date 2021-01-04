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

<title>Sales Management application - Roles</title>

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
					<c:if test="${permission>0}">
						<!-- DataTales Example -->
						<div class="card shadow mb-4">
							<div class="card-header py-3">
								<div
									class="d-sm-flex align-items-center justify-content-between mb-4">
									<h4 class="h3 mb-0 text-gray-800">Roles</h4>
									<form action="/Roles/addRole">
										<c:if test="${permission==2}">
											<a href="/Roles/addRole" type="submit"
												class="d-none d-sm-inline-block btn btn-sm btn-success shadow-sm"><i
												class="fas fa-plus-square fa-sm text-white-50"></i> Add a
												role</a>
										</c:if>
									</form>
								</div>
								<div
									class="d-sm-flex align-items-center justify-content-between mb-4">
									<c:if test="${error.equals('no')}">
										<h5 class="text-danger">You cannot delete this item! you
											will lose a lot of informations</h5>
									</c:if>
									<c:if test="${error.equals('yes')}">
										<h5 class="text-success">Item deleted</h5>
									</c:if>
								</div>
							</div>
							<div class="card-body">
								<div class="table-responsive">
									<table class="table table-bordered" id="dataTable" width="100%"
										cellspacing="0">
										<thead>
											<tr>
												<th scope="col">User Role</th>
												<c:if test="${permission==2}">
													<th scope="col"></th>
													<th scope="col"></th>
												</c:if>
											</tr>
										</thead>
										<tbody>
											<c:forEach items="${roles}" var="role">
												<tr>
													<td>${role.roleUser}</td>
													<c:if test="${permission==2}">
														<td>
															<form action="/Roles/${role.id}/editRole">
																<button class="btn btn-primary btn-circle btn-sm"
																	type="submit">
																	<i class="fas fa-edit"></i>
																</button>
															</form>
														</td>
														<td>
															<form action="/Roles/${role.id}/delete">
																<button class="btn btn-danger btn-circle btn-sm"
																	type="submit">
																	<i class="fas fa-trash"></i>
																</button>
															</form>
														</td>
													</c:if>
												</tr>
											</c:forEach>
										</tbody>
									</table>
								</div>
							</div>
						</div>
					</c:if>
					<c:if test="${permission==0}">
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