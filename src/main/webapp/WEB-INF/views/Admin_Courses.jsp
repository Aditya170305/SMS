<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Courses | Admin</title>
    <link rel="stylesheet" href="css/AdminCoursesStyle.css" />
</head>
<body>
    <div class="display">

    <nav class="dashboard">
        <ul>
            <li class="sidebar-item">
                <img src="images/home.svg"> Dashboard
            </li>
            <li class="sidebar-item active">
                <img width="24px" src="images/book.svg"> Courses
            </li>
            <li class="sidebar-item">
                <img src="images/requests.svg"> Requests
            </li>
            <li class="sidebar-item">
                <img width="24px" src="images/username.svg"> Users
            </li>
            <li class="sidebar-item">
                <img src="images/user.svg"> Profile
            </li>
            <li class="sidebar-item">
                <img src="images/logout.svg"> Logout
            </li>
        </ul>
    </nav>

    <div class="left">
        <h1 class="heading">Courses</h1>

        <div class="course-options">

            <label><strong>Select Degree:</strong></label>
            <select id="degreeSelect">
                <option value="MBA">MBA</option>
                <option value="BBA">BBA</option>
                <option value="M.Tech">M.Tech</option>
                <option value="B.Tech">B.Tech</option>
            </select>

            <br><br>

            <label><strong>Select Year:</strong></label>
            <select id="yearSelect">
                <option value="Year 1">Year 1</option>
                <option value="Year 2">Year 2</option>
                <option value="Year 3">Year 3</option>
                <option value="Year 4">Year 4</option>
            </select>

            <br><br>

            <label><strong>Select Semester:</strong></label>
            <select id="semSelect">
                <option value="Semester 1">Semester 1</option>
                <option value="Semester 2">Semester 2</option>
            </select>

            <div class="stats-box">
                <h3>Total Students: <b id="totalStudents">0</b></h3>
                <p id="activeStudents">Active Students: 0</p>
                <p id="inactiveStudents">Inactive Students: 0</p>
            </div>

            <button class="add-btn">+ Add Course</button>

        </div>
    </div>

</div>

<div class="modal" id="courseModal">
    <div class="modal-content" style="padding: 30px;">
        <span id="closeModalBtn" class="close-btn">&times;</span>
        
        <h2 style="color: #0066FF; text-align: center; margin-bottom: 20px;">Add New Course</h2>
        
        <form id="addCourseForm">
        	<div style="margin-bottom: 15px;">
                <label style="display:block; font-weight:bold; margin-bottom:5px;">Student Name</label>
                <input type="text" id="modalStudentName" placeholder="Enter student name" required 
                       style="width:100%; padding:10px; border:1px solid #ccc; border-radius:5px;">
            </div>
            <div style="margin-bottom: 15px;">
                <label style="display:block; font-weight:bold; margin-bottom:5px;">Select Degree</label>
                <select id="modalDegree" style="width:100%; padding:10px; border:1px solid #ccc; border-radius:5px;">
                    <option value="B.Tech">B.Tech</option>
                    <option value="M.Tech">M.Tech</option>
                    <option value="MBA">MBA</option>
                    <option value="BBA">BBA</option>
                </select>
            </div>

            <div style="margin-bottom: 15px;">
                <label style="display:block; font-weight:bold; margin-bottom:5px;">Select Year</label>
                <select id="modalYear" style="width:100%; padding:10px; border:1px solid #ccc; border-radius:5px;">
                    <option value="Year 1">Year 1</option>
                    <option value="Year 2">Year 2</option>
                    <option value="Year 3">Year 3</option>
                    <option value="Year 4">Year 4</option>
                </select>
            </div>

            <div style="margin-bottom: 20px;">
                <label style="display:block; font-weight:bold; margin-bottom:5px;">Select Semester</label>
                <select id="modalSemester" style="width:100%; padding:10px; border:1px solid #ccc; border-radius:5px;">
                    <option value="Semester 1">Semester 1</option>
                    <option value="Semester 2">Semester 2</option>
                </select>
            </div>

            <button type="submit" style="width:100%; padding:12px; background:#0066FF; color:white; border:none; border-radius:5px; font-weight:bold; cursor:pointer;">
                Submit & Add
            </button>
        </form>
    </div>
</div>

<script src="js/Admin_Courses.js"></script>
<script type="text/javascript" src="js/Admin.js"></script>
</body>
</html>