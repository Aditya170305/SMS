<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Teacher Attendance</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/AttendanceEmployeeStyle.css">
</head>
<body>
    <div class="display">
        <!-- Sidebar -->
        <nav class="dashboard">
            <ul>
                <li class="display">
                    <img src="${pageContext.request.contextPath}/images/home.svg" alt="home"> Dashboard
                </li>
                <li class="display">
                    <img src="${pageContext.request.contextPath}/images/assignment.svg" alt="courses"> Courses
                </li>
                <li class="display active">
                    <img src="${pageContext.request.contextPath}/images/attendance.svg" alt="attendance"> Attendance
                </li>
                <li class="display">
                    <img src="${pageContext.request.contextPath}/images/username.svg" alt="students"> Students
                </li>
                <li class="display">
                    <img src="${pageContext.request.contextPath}/images/user.svg" alt="profile"> Profile
                </li>
                <li class="display">
                    <img src="${pageContext.request.contextPath}/images/logout.svg" alt="logout"> Logout
                </li>
            </ul>
        </nav>

        <!-- Main Content -->
        <div class="left">
            <div class="circular"></div>
            <h1 class="heading">Attendance</h1>

            <!-- Filters -->
            <div class="attendance-filter-box">
                <div>
                    <label><strong>Select Semester:</strong></label>
                    <select class="select-box" id="semesterSelect">
                        <option value="">-- Select Semester --</option>
                        <c:forEach var="semester" items="${semesters}">
                            <option value="${semester}">${semester}</option>
                        </c:forEach>
                    </select>
                </div>

                <div>
                    <label><strong>Subject:</strong></label>
                    <select class="select-box" id="subjectSelect">
                        <option value="">-- Select Subject --</option>
                        <c:forEach var="subject" items="${subjects}">
                            <option value="${subject}">${subject}</option>
                        </c:forEach>
                    </select>
                </div>
                
                <div>
                    <label><strong>Date:</strong></label>
                    <input type="date" class="select-box" id="dateSelect" />
                </div>
            </div>

            <!-- Attendance Table -->
            <div class="events-card">
                <h2>Mark Attendance</h2>
                <table class="data-table">
                    <thead>
                        <tr>
                            <th>Roll No</th>
                            <th>Student Name</th>
                            <th>Mark Present (Checkbox)</th>
                            <th>Marked</th>
                        </tr>
                    </thead>
                    <tbody id="attendanceTableBody">
                        <tr>
                            <td colspan="4" style="text-align: center; padding: 30px; color: #999;">
                                Please select Semester and Subject to load students
                            </td>
                        </tr>
                    </tbody>
                </table>
                
                <!-- Save Button -->
                <div style="margin-top: 20px; text-align: center;">
                    <button id="saveAttendanceBtn" class="save-btn" disabled>
                        Save Attendance
                    </button>
                </div>
            </div>
        </div>
    </div>

    <script src="${pageContext.request.contextPath}/js/Employee.js"></script>
    <script src="${pageContext.request.contextPath}/js/AttendanceEmployee.js"></script>
    <script src="${pageContext.request.contextPath}/js/Employee.js"></script>
</body>
</html>