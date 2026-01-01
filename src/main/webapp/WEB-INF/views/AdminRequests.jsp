<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Requests | Admin</title>
<link rel="stylesheet" href="css/AdminRequestsStyle.css" />
</head>
<body>
    <div class="display">

        <nav class="dashboard">
            <ul>
                <li onclick="location.href='/adminDashboard'"><img src="images/home.svg"> Dashboard</li>
                <li onclick="location.href='/adminCourses'"><img src="images/book.svg"> Courses</li>
                <li class="active"><img src="images/requests.svg"> Requests</li>
                <li onclick="location.href='/users'"><img src="images/username.svg"> Users</li>
                <li onclick="location.href='/adminProfile'"><img src="images/user.svg"> Profile</li>
                <li><img src="images/logout.svg"> Logout</li>
            </ul>
        </nav>

        <div class="left">
            <h1 class="heading">Student Requests</h1>

            <div class="tabs-container">
                <button class="tab-btn active" onclick="filterRequests('Pending', this)">Pending</button>
                <button class="tab-btn" onclick="filterRequests('Approved', this)">Approved</button>
                <button class="tab-btn" onclick="filterRequests('Rejected', this)">Rejected</button>
                <button class="tab-btn" onclick="filterRequests('All', this)">All History</button>
            </div>

            <div class="table-card">
                <table id="requestsTable">
                    <thead>
                        <tr>
                            <th>Student</th>
                            <th>Type</th>
                            <th>Subject</th>
                            <th>Date</th>
                            <th>Status</th>
                            <th>Action</th>
                        </tr>
                    </thead>
                    <tbody id="tableBody">
                        <tr><td colspan="6" style="text-align:center;">Loading requests...</td></tr>
                    </tbody>
                </table>
            </div>
        </div>
    </div>

    <script src="js/AdminRequests.js"></script>
</body>
</html>