<!-- Logout Modal-->
<div class="modal fade" id="logoutModal" tabindex="-1" role="dialog"
	aria-labelledby="exampleModalLabel" aria-hidden="true">
	<div class="modal-dialog" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<h5 class="modal-title" id="exampleModalLabel">Ready to Leave?</h5>
				<button class="close" type="button" data-dismiss="modal"
					aria-label="Close">
					<span aria-hidden="true">x</span>
				</button>
			</div>
			<div class="modal-body">Select "Logout" below if you are ready
				to end your current session.</div>
			<div class="modal-footer">
				<form action="/disconnect">
					<input class="btn btn-primary" name="token" id="token"
						type="hidden" value="${token}">
					<button class="btn btn-secondary" type="button"
						data-dismiss="modal">Cancel</button>
					<input class="btn btn-primary" type="submit" value="out">
				</form>
			</div>
		</div>
	</div>
</div>
<!-- End of Logout Modal-->

