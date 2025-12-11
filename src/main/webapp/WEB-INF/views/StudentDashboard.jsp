<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isELIgnored = "false"%>
<!DOCTYPE html>
<html>
<head>
 	<meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="css/StudentDashboardStyle.css">
	<title>Student Dashboard</title>
</head>
<body> 
	 <div style="gap: 10px;" class="display">
        <nav class="dashboard">
            <ul>
                <li class="display">
                    <img src="images/home.svg" alt="home image">
                    Dashboard
                </li>
                <li class="display">
                    <img src="images/calendar.svg" alt="calendar image">
                    Class Schedule
                </li>
                <li class="display">
                    <img src="images/attendance.svg" alt="attendance image">
                    Attendance
                </li>
                <li class="display">
                    <img width="24px" src="images/book.svg" alt="book image">
                    Academics
                </li>
                <li class="display">
                    <img width="24px" src="images/fees.svg" alt="fees image">
                    Fees
                </li>
                <li class="display">
                    <img width="24px" src="images/profile.svg" alt="profile image">
                    Profile
                </li>
                <li class="display">
                    <img src="images/logout.svg" alt="logout image">
                    Logout
                </li>
            </ul>
        </nav>

        <div class="left">
            <h1 class="heading">Welcome, Student!</h1>
            <div class="student-info">
                <p>Student Roll-No : 0187CS231016</p>
                <p>Batch : 2023-2027</p>
            </div>
            <div class="circular"></div>

            <!-- Cards Container -->
            <div class="cards-container">
                <div class="card">
                    <h2 class="card-value">92%</h2>
                    <p class="card-label">Attendance</p>
                </div>
                <div class="card">
                    <h2 class="card-value">8.4</h2>
                    <p class="card-label">CGPA</p>
                </div>

                <!-- ✅ Next Class Card -->
                <div class="card card-next-class">
                    <h2 class="card-heading">Next Class</h2>
                    <p class="card-content class-info">
                        <span class="subject">DSA</span> – <span class="professor">Professor Sharma</span>
                    </p>
                    <p class="card-content class-time">
                        <img src="images/clock.svg" alt="clock icon">
                        10:00 AM - 11:00 AM
                    </p>
                </div>
            </div>

            <!-- Bottom Container for Attendance and Events -->
            <div class="bottom-container">
                <!-- Events and Announcements Card -->
                <div class="events-card">
                    <h2>📢 Upcoming Events:</h2>
                    <ul>
                        <li>Guest Lecture: AI in Real Life – 25 Oct</li>
                        <li>Hackathon Registration Closes – 30 Oct</li>
                    </ul>
                    <h2>📅 Announcements:</h2>
                    <ul>
                        <li>Mid-term Exams start 1 Nov</li>
                        <li>Library Renewal Deadline – 28 Oct</li>
                    </ul>
                </div>

                <!-- Attendance Progress Section -->
                <div class="attendance-progress-section">
                    <h2 class="section-title">Semester Attendance</h2>
                    <div class="chart-container">
                        <div class="chart-y-axis">
                            <span>100%</span>
                            <span>80%</span>
                            <span>60%</span>
                            <span>40%</span>
                            <span>20%</span>
                            <span>0%</span>
                        </div>
                        <div class="chart-bars">
                            <div class="bar-wrapper">
                                <div class="bar" style="height: 85%;" data-value="85%"></div>
                                <span class="bar-label">1st Sem</span>
                            </div>
                            <div class="bar-wrapper">
                                <div class="bar" style="height: 78%;" data-value="78%"></div>
                                <span class="bar-label">2nd Sem</span>
                            </div>
                            <div class="bar-wrapper">
                                <div class="bar" style="height: 88%;" data-value="88%"></div>
                                <span class="bar-label">3rd Sem</span>
                            </div>
                            <div class="bar-wrapper">
                                <div class="bar" style="height: 92%;" data-value="92%"></div>
                                <span class="bar-label">4th Sem</span>
                            </div>
                        </div>
                    </div>
                </div>

            </div>
        </div>
    </div>
    <script type="text/javascript" src="js/StudentDashboard.js"></script>
</body>
</html>