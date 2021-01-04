<!-- Sidebar -->
<ul
	class="navbar-nav bg-gradient-primary sidebar sidebar-dark accordion"
	id="accordionSidebar">

	<!-- Sidebar - Brand -->
	<form action="/index">
		<a
			class="sidebar-brand d-flex align-items-center justify-content-center"
			href="/index" type="submit">
			<div class="sidebar-brand-icon rotate-n-15">
				<i class="fas fa-laugh-wink"></i>
			</div>
			<div class="sidebar-brand-text mx-3">
				Sales Management <sup>2020</sup>
			</div>
		</a>
	</form>
	<!-- Divider -->
	<hr class="sidebar-divider my-0">

	<!-- Nav Item - Dashboard -->
	<form action="/index">
		<li class="nav-item active"><a class="nav-link" type="submit"
			href="/index"> <i class="fas fa-fw fa-tachometer-alt"></i> <span>Dashboard</span>
		</a></li>
	</form>
	<c:if test="${listPrivileges.contains('category')}">
		<!-- Divider -->
		<hr class="sidebar-divider d-none d-md-block">
		<!-- Nav Item - Categories -->
		<li class="nav-item">
			<form action="/Categories">
				<a class="nav-link" type="submit" href="/Categories"> <i
					class="fas fa-fw fa-table"></i> <span>Categories</span></a>
			</form>
		</li>
	</c:if>
	<c:if test="${listPrivileges.contains('product')}">
		<!-- Divider -->
		<hr class="sidebar-divider d-none d-md-block">
		<!-- Nav Item - Products -->
		<li class="nav-item">
			<form action="/Products">
				<a class="nav-link" type="submit" href="/Products"> <i
					class="fas fa-fw fa-table"></i> <span>Products</span></a>
			</form>
		</li>
	</c:if>
	<c:if test="${listPrivileges.contains('customer')}">
		<!-- Divider -->
		<hr class="sidebar-divider d-none d-md-block">
		<!-- Nav Item - Customers -->
		<li class="nav-item">
			<form action="/Customers">
				<a class="nav-link" type="submit" href="/Customers"> <i
					class="fas fa-fw fa-users"></i> <span>Customers</span></a>
			</form>
		</li>
	</c:if>
	<c:if test="${listPrivileges.contains('role')}">
		<!-- Divider -->
		<hr class="sidebar-divider d-none d-md-block">
		<!-- Nav Item - Customers -->
		<li class="nav-item">
			<form action="/Roles">
				<a class="nav-link" type="submit" href="/Roles"> <i
					class="fas fa-fw fa-users"></i> <span>Roles</span></a>
			</form>
		</li>
	</c:if>
	<c:if test="${listPrivileges.contains('user')}">
		<!-- Divider -->
		<hr class="sidebar-divider d-none d-md-block">
		<!-- Nav Item - Customers -->
		<li class="nav-item">
			<form action="/Users">
				<a class="nav-link" type="submit" href="/Users"> <i
					class="fas fa-fw fa-users"></i> <span>Users</span></a>
			</form>
		</li>
	</c:if>
	<c:if test="${listPrivileges.contains('privilege')}">
		<hr class="sidebar-divider d-none d-md-block">
		<!-- Nav Item - Customers -->
		<li class="nav-item">
			<form action="Privileges">
				<a class="nav-link" type="submit" href="/Privileges"> <i
					class="fas fa-fw fa-users"></i> <span>Privileges</span></a>
			</form>
		</li>
	</c:if>
	<c:if test="${listPrivileges.contains('authorization')}">
		<hr class="sidebar-divider d-none d-md-block">
		<!-- Nav Item - Customers -->
		<li class="nav-item">
			<form action="Authorizations">
				<a class="nav-link" type="submit" href="/Authorizations"> <i
					class="fas fa-fw fa-users"></i> <span>Authorizations</span></a>
			</form>
		</li>
	</c:if>
	<!-- Divider -->
	<hr class="sidebar-divider d-none d-md-block">
	<!-- Sidebar Toggler (Sidebar) -->
	<div class="text-center d-none d-md-inline">
		<button class="rounded-circle border-0" id="sidebarToggle"></button>
	</div>

</ul>
<!-- End of Sidebar -->
