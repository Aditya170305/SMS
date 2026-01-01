<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Let's Learn - Student Management System</title>
<link rel="icon" type="image/png" href="images/studylogo.png">
<link rel="stylesheet" href="css/style.css">
</head>
<body>
	<div class="container display">
        
        <!-- Left Section -->
        <div id="left">
            <!-- Role Selection -->
             <form action = "Login" method = "post">
            <div class="radio-class">
                <input type="radio" id="admin" name="user" value="admin" checked>
                <label for="admin">
                    <div class="icon-circle">
                        <img class="event1" src="images/username.svg" alt="admin icon">
                    </div>
                    Admin
                </label>

                <input type="radio" id="teacher" name="user" value="teacher">
                <label for="teacher">
                    <div class="icon-circle">
                        <img class="event2" src="images/username.svg" alt="teacher icon">
                    </div>
                    Employe
                </label>

                <input type="radio" id="student" name="user" value="student">
                <label for="student">
                    <div class="icon-circle">
                        <img class="event3" src="images/username.svg" alt="student icon">
                    </div>
                    Student
                </label>
            </div>

            <!-- Login Card -->
            <div class="login-form">
                <h2 style="text-align:center; color:#039BE5; margin-bottom:20px;">Log In</h2>

                <div class="card">
                    <label for="username">Username</label>
                    <img src="images/username.svg" alt="username icon">
                    <input type="text" name="username" id="username" placeholder="User name">

                    <label for="password">Password</label>
                    <img src="images/password.svg" alt="password icon">
                    <input type="password" name="password" id="password" placeholder="Password">
                </div>

                <div class="button-class">
                    <button class="btn">Log in</button>
                </div>
            </div>
            </form>
        </div>

        <!-- Right Section -->
        <div id="right">
            <img src="images/right.png" alt="Login Illustration">
        </div>
    </div>
</body>
</html>