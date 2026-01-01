<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Admin Dashboard</title>
<link rel="stylesheet" href="css/AdminDashboardStyle.css" />
<script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
</head>
<body>
    <div class="display">
    <nav class="dashboard">
      <ul>
        <li class="active"><img src="images/home.svg" alt=""> Dashboard</li>
        <li><img width="24px" src="images/book.svg" alt=""> Courses</li>
        <li><img src="images/requests.svg" alt=""> Requests</li>
        <li><img width="24px" src="images/username.svg" alt=""> Users</li>
        <li><img src="images/user.svg" alt=""> Profile</li>
        <li><img src="images/logout.svg" alt="">Logout</li>
      </ul>
    </nav>

    <div class="left">
      <h1 class="heading">Welcome, Admin!</h1>

      <div class="cards-container">
        <div class="card">
          <h2 class="card-value" id="totalDegrees">5</h2>
          <p class="card-label">Total Degrees</p>
        </div>
        <div class="card">
          <h2 class="card-value" id="selectedCourse">—</h2>
          <p class="card-label">Course Type</p>
        </div>
        <div class="card">
          <h2 class="card-value" id="totalUsers">0</h2>
          <p class="card-label">Total Users</p>
        </div>
      </div>

      <div class="bottom-container">
        <div class="courses-card">
          <h2>Courses</h2>
          <div class="dropdown-section">
            <label for="degreeSelect"><strong>Select Degree:</strong></label>
            <select id="degreeSelect">
              <option value="">-- Select Degree --</option>
              <option value="B.Tech">B.Tech</option>
              <option value="M.Tech">M.Tech</option>
              <option value="Pharmacy">Pharmacy</option>
              <option value="MBA">MBA</option>
              <option value="BBA">BBA</option>
            </select>
          </div>

          <table id="courseTable">
            <thead>
              <tr>
                <th>Branch / Specialization</th>
                <th>Enrolled Students</th>
                <th>Status</th>
                <th>Action</th>
              </tr>
            </thead>
            <tbody>
              <tr><td colspan="4" style="text-align:center;">Select a degree to view data</td></tr>
            </tbody>
          </table>
        </div>

        <div class="chart-card">
          <h2>Users Distribution</h2>
          <div class="chart-container">
            <canvas id="userChart"></canvas>
          </div>

          <h2 class="line-title">Total Enrollments Over Time</h2>
          <div class="chart-container">
            <canvas id="enrollmentChart"></canvas>
          </div>
        </div>
      </div>
    </div>
  </div>

  <script src="js/AdminDashboard.js"></script>
  <script type="text/javascript" src="js/Admin.js"></script>
</body>
</html>