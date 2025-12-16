<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Students Records</title>
<link rel="stylesheet" href="css/StudentStyle.css">
<script src="https://cdnjs.cloudflare.com/ajax/libs/html2canvas/1.4.1/html2canvas.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/jspdf/2.5.1/jspdf.umd.min.js"></script>
</head>
<body>
    <div class="display">

        <nav class="dashboard">
            <ul>
                <li onclick="location.href='dashboardEmployee'"><img src="images/home.svg"> Dashboard</li>
                <li onclick="location.href='courses'"><img src="images/assignment.svg"> Courses</li>
                <li onclick="location.href='attendance1'"><img src="images/attendance.svg"> Attendance</li>
                <li class="active" onclick="location.href='students'"><img src="images/username.svg"> Students</li>
                <li onclick="location.href='profile'"><img src="images/user.svg"> Profile</li>
                <li onclick="location.href='logoutEmployee'"><img src="images/logout.svg">Logout</li>
            </ul>
        </nav>

        <div class="left">
            
            <h1 class="heading">Student Records & Performance</h1>

            <div class="filters">
                <select id="subjectSelect">
                    <option value="">-- Select Subject --</option>
                    <c:forEach var="subject" items="${subjects}">
                        <option value="${subject}">${subject}</option>
                    </c:forEach>
                </select>

                <select id="semesterSelect">
                    <option value="">-- Select Semester --</option>
                    <c:forEach var="semester" items="${semesters}">
                        <option value="${semester}">${semester}</option>
                    </c:forEach>
                </select>

                <button class="apply-btn" id="applyBtn">Apply</button>
                <button class="pdf-btn" onclick="exportPDF()">Export PDF</button>
                <button class="excel-btn" onclick="exportExcel()">Export Excel</button>
            </div>

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

                    <tbody id="tableBody">
                        <tr>
                            <td colspan="7" style="text-align:center; color:#888;">
                                Please select a Subject and Semester and click Apply.
                            </td>
                        </tr>
                    </tbody>
                </table>
            </div>
        </div>
    </div>

    <script src="js/Employee.js"></script>
    <script src="js/Student.js"></script> </body>
</html>