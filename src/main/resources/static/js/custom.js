$(function() {
	$('#letter').on('input', function() {
		this.value = this.value.replace(/[^a-zA-Z@]/g, ''); //<-- replace all other than given set of values
	});
});