<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Student Dashboard — Academics</title>
<link rel="stylesheet" href="css/AcademicsStyle.css" />
</head>
<body>
	<div class="display">
    <!-- Sidebar (kept same style) -->
    <nav class="dashboard">
      <ul>
        <li class="display"><img src="images/home.svg" alt="home"> Dashboard</li>
        <li class="display"><img src="images/calendar.svg" alt="calendar"> Class Schedule</li>
        <li class="display"><img src="images/attendance.svg" alt="attendance"> Attendance</li>
        <li class="display active"><img src="images/book.svg" alt="academics"> Academics</li>
        <li class="display"><img src="images/fees.svg" alt="fees"> Fees</li>
        <li class="display"><img src="images/profile.svg" alt="profile"> Profile</li>
        <li class="display"><img src="images/logout.svg" alt="logout"> Logout</li>
      </ul>
    </nav>

    <!-- Right side: Academics -->
    <div class="left">
      <h1 class="heading">Academics</h1>

      <div class="academics-container">
        <div class="academics-card">
          <div class="table-header">
            <h2>Subjects &amp; Syllabus</h2>
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
            <tbody>
              <!-- JS will populate rows -->
            </tbody>
          </table>
        </div>
      </div>
    </div>
  </div>

  <!-- Assignments / Syllabus / Notes Modal (shared) -->
  <div id="modalOverlay" class="modal hidden">
    <div class="modal-content large">
      <span class="close" id="modalClose">&times;</span>
      <h2 id="modalTitle">Modal Title</h2>

      <!-- Assignments view -->
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

      <!-- Syllabus / Notes view (simple content area) -->
      <div id="docView" class="modal-section hidden">
        <div class="doc-content" id="docContent">
          <!-- JS fills content -->
        </div>
      </div>
    </div>
  </div>

  <script src="js/Academics.js"></script>
  <script src="js/StudentDashboard.js"></script>
</body>
</html>