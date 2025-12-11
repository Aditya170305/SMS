<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Class Schedule - Student Dashboard</title>
<link rel="stylesheet" href="css/ClassScheduleStyle.css">
</head>
<body>
	 <div class="display">
        <!-- Sidebar -->
        <nav class="dashboard">
            <ul>
                <li class="display">
                    <img src="images/home.svg" alt="home image">
                    Dashboard
                </li>
                <li class="display active">
                    <img src="images/calendar.svg" alt="calendar image">
                    Class Schedule
                </li>
                <li class="display">
                    <img src="images/attendance.svg" alt="attendance image">
                    Attendance
                </li>
                <li class="display">
                    <img src="images/book.svg" alt="book image">
                    Academics
                </li>
                <li class="display">
                    <img src="images/fees.svg" alt="fees image">
                    Fees
                </li>
                <li class="display">
                    <img src="images/profile.svg" alt="profile image">
                    Profile
                </li>
                <li class="display">
                    <img src="images/logout.svg" alt="logout image">
                    Logout
                </li>
            </ul>
        </nav>

        <!-- Right Content -->
        <div class="left">
            <h1 class="heading">Class Schedule</h1>

            <!-- ✅ Centered Day Tabs -->
            <div class="day-tabs">
                <button class="day active" data-day="Mon">Mon</button>
                <button class="day" data-day="Tue">Tue</button>
                <button class="day" data-day="Wed">Wed</button>
                <button class="day" data-day="Thu">Thu</button>
                <button class="day" data-day="Fri">Fri</button>
                <button class="day" data-day="Sat">Sat</button>
            </div>

            <!-- Schedule Cards (Dynamic Area) -->
            <div id="scheduleContainer" class="schedule-container">
                <!-- JS will inject schedule cards here -->
            </div>
        </div>
    </div>
    <script src="js/ClassSchedule.js"></script>
    <script type="text/javascript" src="js/StudentDashboard.js"></script>p
</body>
</html>