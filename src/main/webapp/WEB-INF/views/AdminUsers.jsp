<%@ page language="java" contentType="text/html; charset=UTF-8"

pageEncoding="UTF-8"%>

<!DOCTYPE html>

<html>

<head>

<meta charset="UTF-8" />

<meta name="viewport" content="width=device-width, initial-scale=1.0" />

<title>Users | Admin</title>

<link rel="stylesheet" href="css/AdminUsersStyle.css" />

</head>

<body>

<div class="display">



<nav class="dashboard">

<ul>

<li><img src="images/home.svg"> Dashboard</li>

<li><img width="24" src="images/book.svg"> Courses</li>

<li><img src="images/requests.svg"> Requests</li>

<li class="active"><img width="24" src="images/username.svg"> Users</li>

<li><img width="24" src="images/user.svg"> Profile</li>

<li><img width="24" src="images/logout.svg"> Logout</li>

</ul>

</nav>



<div class="left">

<h1 class="heading">Users</h1>



<div style="display: flex; gap: 20px; margin-bottom: 25px; align-items:center; flex-wrap: wrap;">



<div>

<label><strong>Select Degree:</strong></label><br>

<select id="degreeSelect">

<option value="">-- Select --</option>

<option value="B.Tech">B.Tech</option>

<option value="M.Tech">M.Tech</option>

<option value="MBA">MBA</option>

<option value="BBA">BBA</option>

</select>

</div>



<div>

<label><strong>Select Branch:</strong></label><br>

<select id="branchSelect">

<option value="">-- Select --</option>

<option value="CSE">CSE</option>

<option value="ECE">ECE</option>

<option value="EE">Electrical</option>

<option value="ME">Mechanical</option>

<option value="Finance">Finance (MBA)</option>

<option value="Marketing">Marketing (MBA)</option>

</select>

</div>



<div>

<label><strong>Select Year:</strong></label><br>

<select id="yearSelect">

<option value="">-- Select --</option>

<option value="1">1st Year</option>

<option value="2">2nd Year</option>

<option value="3">3rd Year</option>

<option value="4">4th Year</option>

</select>

</div>



<div>

<label><strong>Select Semester:</strong></label><br>

<select id="semSelect">

<option value="">-- Select --</option>

<option value="1">Semester 1</option>

<option value="2">Semester 2</option>

<option value="3">Semester 3</option>

<option value="4">Semester 4</option>

<option value="5">Semester 5</option>

<option value="6">Semester 6</option>

<option value="7">Semester 7</option>

<option value="8">Semester 8</option>

</select>

</div>



</div>



<div class="courses-card">



<table id="studentTable">

<thead>

<tr>

<th>Student Name</th>

<th>Roll No</th>

<th>Email</th>

<th>Status</th>

</tr>

</thead>



<tbody>

<tr>

<td colspan="4" style="text-align:center;">

Select Degree, Branch, Year & Semester to view students

</td>

</tr>

</tbody>

</table>



<button id="addStudentBtn"

style="margin-top: 15px; width: 180px; background:#0066FF;

color:white; padding:10px; border:none; border-radius:8px;

cursor:pointer;">

+ Add Student

</button>



</div>

</div>



</div>



<div class="modal" id="studentModal">

<div class="modal-content">

<span id="closeModalBtn" class="close-btn">&times;</span>


<h2 style="text-align:center; color:#0066FF; margin-bottom:25px;">Add New Student</h2>


<form id="addStudentForm">

<label class="modal-label">Student Name</label>

<input type="text" id="modalName" class="modal-input" placeholder="Enter full name" required>



<label class="modal-label">Select Degree</label>

<select id="modalDegree" class="modal-select" required>

<option value="B.Tech">B.Tech</option>

<option value="M.Tech">M.Tech</option>

<option value="MBA">MBA</option>

<option value="BBA">BBA</option>

</select>



<label class="modal-label">Select Branch</label>

<select id="modalBranch" class="modal-select" required>

<option value="CSE">CSE</option>

<option value="ECE">ECE</option>

<option value="ME">Mechanical</option>

<option value="Finance">Finance</option>

<option value="Marketing">Marketing</option>

<option value="General">General (BBA)</option>

<option value="Data Science">Data Science</option>

</select>



<label class="modal-label">Select Year</label>

<select id="modalYear" class="modal-select" required>

<option value="1">1st Year</option>

<option value="2">2nd Year</option>

<option value="3">3rd Year</option>

<option value="4">4th Year</option>

</select>



<label class="modal-label">Select Semester</label>

<select id="modalSemester" class="modal-select" required>

<option value="1">Semester 1</option>

<option value="2">Semester 2</option>

</select>



<button type="submit" class="modal-submit-btn">Submit & Add Student</button>

</form>

</div>

</div>

<script src="js/AdminUsers.js"></script>

<script type="text/javascript" src="js/Admin.js"></script>

</body>
</html>