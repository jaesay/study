jQuery(document).ready(function($) {
    $(".clickable-row").click(function() {
        window.location = $(this).data("href");
    });
    
    $('.needs-validation').submit(function(event) {
		if(this.checkValidity() === false) {
			event.preventDefault();
			event.stopPropagation();
		}
		this.classList.add('was-validated');
	});
});