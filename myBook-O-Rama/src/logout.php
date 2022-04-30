<?php  

require_once('book_sc_fns.php');
session_start();
$old_user = $_SESSION['admin_user'];
unset($_SESSION['admin_user']);
session_destroy();

do_html_header("Logging Out");

if(!empty($old_user)) {
	echo "<p>Logged out.</p>";
	do_html_url("login.php", "Login");
} else {
	echo "<p>You were not logged in, and so have not been logged out.</p>";
	do_html_url("login.php", "Login");
}

do_html_footer();

?>