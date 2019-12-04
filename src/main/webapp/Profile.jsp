<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
<link rel="stylesheet" href="HomePage.css">
<meta charset="UTF-8">
<title>My Profile Page</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
<link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/1.10.20/css/jquery.dataTables.css">
<script type="text/javascript" charset="utf8" src="https://cdn.datatables.net/1.10.20/js/jquery.dataTables.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-modal/0.9.1/jquery.modal.min.js"></script>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/jquery-modal/0.9.1/jquery.modal.min.css" />
</head>
<body onload="update()">
<form action = "schedule.jsp" id="formA"><button id="Schedule" style="float: right;">Schedule!</button></form>
<h2> <%= session.getAttribute("name") %> </h2>
<h3> <%= session.getAttribute("major") %> </h3>
<h4> <%= session.getAttribute("gradyear") %> </h4>
<div class="dropdown">
	<div class="form">
		<div class="text">
			<p id="table"></p>
		</div>
	</div>
</div>
<div id="demo"></div>
<script>
	var modal = "";
	var txt = "<table id='classes' class='display' border='1'><thead><tr><th>Class</th><th>Description</th><th>Details</th></tr></thead><tbody>";
	DATA = <%= session.getAttribute("user") %>;
	for(i in DATA.schedule) {
		for(j in DATA.schedule[i].classes) {
			var course = DATA.schedule[i].classes[j];
			txt += "<tr><td>" + course.deptcode + " " + course.coursecode + "</td><td>" + course.name + "</td><td><a href='#ex" + course.deptcode + course.coursecode + "' rel='modal:open'>View</a></td></tr>";
			modal += "<div id='ex" + course.deptcode + course.coursecode + "' class='modal'><p>" + course.description + "</p><a href='#' rel='modal:close'>Close</a></div>";
		}
	}
	txt += "</tbody></table>"    
	document.getElementById("table").innerHTML = txt;
	document.getElementById("demo").innerHTML = modal;
	$(document).ready(function() {
	    $('#classes').DataTable();
	} );
</script>

</body>
</html>