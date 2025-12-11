<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Teacher Dashboard</title>
<link rel="stylesheet" href="css/EmployeeDashboardStyle.css">
</head>
<body>
	 <div class="display">
        <!-- Sidebar -->
        <nav class="dashboard">
            <ul>
                <li class="display">
                    <img src="images/home.svg" alt="home"> Dashboard
                </li>
                <li class="display">
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
                    <img src="images/logout.svg" alt="logout image">
                    Logout
                </li>
            </ul>
        </nav>

        <!-- Main Content -->
        <div class="left">
            <h1 class="heading">Welcome, Teacher!</h1>
            <div class="circular"></div>

            <!-- Top Cards -->
            <div class="cards-container">
                <div class="card">
                    <h2 class="card-value" id="totalCourses">5</h2>
                    <p class="card-label">Courses</p>
                </div>
                <div class="card">
                    <h2 class="card-value" id="totalStudents">40</h2>
                    <p class="card-label">Students</p>
                </div>
                <div class="card">
                    <h2 class="card-value" id="averageGrade">79%</h2>
                    <p class="card-label">Average Grade</p>
                </div>
            </div>

            <!-- Bottom Section -->
            <div class="bottom-container">
                <!-- Assignments Section -->
                <div class="events-card">
                    <h2>Assignments Overview</h2>
                    <table class="data-table">
                        <thead>
                            <tr>
                                <th>Assignment</th>
                                <th>Course</th>
                                <th>Submissions</th>
                                <th>Status</th>
                                <th>Action</th>
                            </tr>
                        </thead>
                        <tbody id="assignmentTableBody">
                            <!-- JS fills data -->
                        </tbody>
                    </table>
                </div>

                <!-- Chart Section -->
                <div class="attendance-progress-section">
                    <h2 class="section-title">Average Grades by Month</h2>
                    <canvas id="gradeChart"></canvas>
                </div>
            </div>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
    <script src="js/EmployeeDashboard.js"></script>
    <script src="js/Employee.js"></script>
</body>
</html>