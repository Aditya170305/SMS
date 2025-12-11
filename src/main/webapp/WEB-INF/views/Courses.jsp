<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Teacher Dashboard — Courses</title>
<link rel="stylesheet" href="css/CoursesStyle.css" />
</head>
<body>
	<div class="display">
    
    <!-- Sidebar -->
    <nav class="dashboard">
      <ul>
        <li class="display">
          <img src="images/home.svg" alt="home"> Dashboard
        </li>

        <!-- COURSES ACTIVE -->
        <li class="display active">
          <img src="images/assignment.svg" alt="courses"> Courses
        </li>

        <li class="display">
          <img src="images/attendance.svg" alt="assignments"> Attendance
        </li>
        <li class="display">
          <img src="images/username.svg" alt="students"> Students
        </li>
        <li class="display">
          <img src="images/user.svg" alt="settings"> Profile
        </li>
        <li class="display">
          <img src="images/logout.svg" alt="settings"> Profile
        </li>
      </ul>
    </nav>

    <!-- Right Side -->
    <div class="left">
      <h1 class="heading">Courses</h1>

      <div class="academics-container">
        <div class="academics-card">
          <div class="table-header">
            <h2>Subjects & Syllabus</h2>
          </div>

          <table class="academics-table" id="academicsTable">
            <thead>
              <tr>
                <th>Subject</th>
                <th>Subject Code</th>
                <th>Assignments</th>
                <th>Syllabus</th>
                <th>Notes</th>
              </tr>
            </thead>
            <tbody></tbody>
          </table>

        </div>
      </div>
    </div>
  </div>

  <!-- MODAL -->
  <div id="modalOverlay" class="modal hidden">
    <div class="modal-content large">
      <span class="close" id="modalClose">&times;</span>
      <h2 id="modalTitle">Modal Title</h2>

      <!-- Assignments -->
      <div id="assignmentsView" class="modal-section hidden">
        <table class="assign-table">
          <thead>
            <tr>
              <th>Assignment</th>
              <th>Open</th>
              <th>Status</th>
            </tr>
          </thead>
          <tbody id="assignmentsTbody"></tbody>
        </table>
      </div>

      <!-- Syllabus / Notes -->
      <div id="docView" class="modal-section hidden">
        <div class="doc-content" id="docContent"></div>
      </div>

    </div>
  </div>

  <script src="js/Courses.js"></script>
  <script src="js/Employee.js"></script>
</body>
</html>