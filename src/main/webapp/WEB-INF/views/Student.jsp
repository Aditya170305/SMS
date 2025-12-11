<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Students</title>
<link rel="stylesheet" href="css/StudentStyle.css">
<script src="https://cdnjs.cloudflare.com/ajax/libs/html2canvas/1.4.1/html2canvas.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/jspdf/2.5.1/jspdf.umd.min.js"></script>
</head>
<body>
	<div class="display">

        <!-- Sidebar -->
        <nav class="dashboard">
            <ul>
                <li><img src="home.svg"> Dashboard</li>
                <li><img src="assignment.svg"> Courses</li>
                <li><img src="attendance.svg"> Attendance</li>
                <li class="active"><img src="username.svg"> Students</li>
                <li><img src="user.svg"> Profile</li>
            </ul>
        </nav>

        <!-- Main Content -->
        <div class="left">
            
            <h1 class="heading">Student Records & Performance</h1>

            <!-- Filters -->
            <div class="filters">
                <select id="department">
                    <option value="CSE">CSE</option>
                    <option value="ECE">ECE</option>
                    <option value="EE">EE</option>
                </select>

                <select id="semester">
                    <option>5th Semester</option>
                    <option>4th Semester</option>
                    <option>3rd Semester</option>
                </select>

                <button class="apply-btn">Apply</button>
                <button class="pdf-btn">Export PDF</button>
                <button class="excel-btn">Export Excel</button>
            </div>

            <!-- Table -->
            <div class="student-card">
                <table id="studentTable">
                    <thead>
                        <tr>
                            <th>Roll No</th>
                            <th>Name</th>
                            <th>Department</th>
                            <th>Semester</th>
                            <th>Test Score</th>
                            <th>Attendance %</th>
                            <th>Status</th>
                        </tr>
                    </thead>

                    <tbody>
                        <tr>
                            <td>21CSE01</td>
                            <td>Rahul Sharma</td>
                            <td>CSE</td>
                            <td>5</td>
                            <td>82</td>
                            <td>91%</td>
                            <td>Good</td>
                        </tr>
                        <tr>
                            <td>21CSE02</td>
                            <td>Priya Sinha</td>
                            <td>CSE</td>
                            <td>5</td>
                            <td>76</td>
                            <td>85%</td>
                            <td>Average</td>
                        </tr>
                        <tr>
                            <td>21CSE03</td>
                            <td>Aman Verma</td>
                            <td>CSE</td>
                            <td>5</td>
                            <td>64</td>
                            <td>70%</td>
                            <td>Needs Improvement</td>
                        </tr>
                    </tbody>
                </table>
            </div>

        </div>

    </div>

    <script src="js/Employee.js"></script>
    <script type="text/javascript" src="Student.js"></script>
</body>
</body>
</html>