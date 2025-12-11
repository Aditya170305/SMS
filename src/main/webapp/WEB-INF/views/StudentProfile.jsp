<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Teacher Profile</title>
<link rel="stylesheet" href="css/StudentProfileStyle.css" />
</head>
<body>
    <div class="main-wrapper">
        <nav class="dashboard">
            <ul>
                <li class="nav-item" onclick="location.href='dashboard.jsp'">
                    <img src="images/home.svg" alt="dashboard"> Dashboard
                </li>
                <li class="nav-item">
                    <img src="images/calendar.svg" alt="courses"> Class Schedule
                </li>
                <li class="nav-item">
                    <img src="images/attendance.svg" alt="attendance"> Attendance
                </li>
                <li class="nav-item">
                    <img src="images/book.svg" alt="students"> Academics
                </li>
                <li class="nav-item">
                    <img src="images/fees.svg" alt="profile"> Fees
                </li>
                <li class="nav-item active">
                    <img src="images/profile.svg" alt="logout"> Profile
                </li>
                <li class="nav-item">
                    <img src="images/logout.svg" alt="logout"> Logout
                </li>
            </ul>
        </nav>

        <div class="main-content">
            <div class="profile-header">
                <h1>Teacher Profile</h1>
                <button id="editBtn" class="btn-edit">Edit Profile</button>
            </div>

            <form id="profileForm" class="profile-form">
                <div class="section-card">
                    <h2 class="section-title">Personal Details</h2>
                    <div class="form-grid">
                        <div class="form-group">
                            <label>Title</label>
                            <input type="text" name="title" value="${profile.title}" readonly />
                        </div>
                        <div class="form-group">
                            <label>Enrollment No</label>
                            <input type="text" name="enrollmentNo" value="${profile.enrollmentNo}" readonly />
                        </div>
                        <div class="form-group">
                            <label>First Name</label>
                            <input type="text" name="firstName" value="${profile.firstName}" readonly />
                        </div>
                        <div class="form-group">
                            <label>Middle Name</label>
                            <input type="text" name="middleName" value="${profile.middleName}" readonly />
                        </div>
                        <div class="form-group">
                            <label>Last Name</label>
                            <input type="text" name="lastName" value="${profile.lastName}" readonly />
                        </div>
                        <div class="form-group">
                            <label>Gender</label>
                            <input type="text" name="gender" value="${profile.gender}" readonly />
                        </div>
                        <div class="form-group">
                            <label>Date of Birth</label>
                            <input type="date" name="dateOfBirth" value="${profile.dateOfBirth}" readonly />
                        </div>
                        <div class="form-group">
                            <label>Place of Birth</label>
                            <input type="text" name="placeOfBirth" value="${profile.placeOfBirth}" readonly />
                        </div>
                        <div class="form-group">
                            <label>Blood Group</label>
                            <input type="text" name="bloodGroup" value="${profile.bloodGroup}" readonly />
                        </div>
                        <div class="form-group">
                            <label>Father's Name</label>
                            <input type="text" name="fatherName" value="${profile.fatherName}" readonly />
                        </div>
                        <div class="form-group">
                            <label>Mother's Name</label>
                            <input type="text" name="motherName" value="${profile.motherName}" readonly />
                        </div>
                    </div>
                </div>

                <div class="section-card">
                    <h2 class="section-title">Contact Information</h2>
                    <div class="form-grid">
                        <div class="form-group">
                            <label>Mobile Number</label>
                            <input type="tel" name="mobile" value="${profile.mobile}" readonly />
                        </div>
                        <div class="form-group">
                            <label>Phone Number</label>
                            <input type="tel" name="phone" value="${profile.phone}" readonly />
                        </div>
                        <div class="form-group form-group-full">
                            <label>Email Address</label>
                            <input type="email" name="email" value="${profile.email}" readonly />
                        </div>
                        <div class="form-group form-group-full">
                            <label>Address Line 1</label>
                            <input type="text" name="addressLine1" value="${profile.addressLine1}" readonly />
                        </div>
                        <div class="form-group form-group-full">
                            <label>Address Line 2</label>
                            <input type="text" name="addressLine2" value="${profile.addressLine2}" readonly />
                        </div>
                        <div class="form-group">
                            <label>City</label>
                            <input type="text" name="city" value="${profile.city}" readonly />
                        </div>
                        <div class="form-group">
                            <label>State</label>
                            <input type="text" name="state" value="${profile.state}" readonly />
                        </div>
                        <div class="form-group">
                            <label>Country</label>
                            <input type="text" name="country" value="${profile.country}" readonly />
                        </div>
                        <div class="form-group">
                            <label>Pincode</label>
                            <input type="text" name="pincode" value="${profile.pincode}" readonly />
                        </div>
                    </div>
                </div>

                <div class="section-card">
                    <h2 class="section-title">Bank & Document Details</h2>
                    <div class="form-grid">
                        <div class="form-group">
                            <label>Bank Name</label>
                            <input type="text" value="${profile.bankName}" readonly />
                        </div>
                        <div class="form-group">
                            <label>Account Number</label>
                            <input type="text" value="${profile.accountNumber}" readonly />
                        </div>
                        <div class="form-group">
                            <label>IFSC Code</label>
                            <input type="text" value="${profile.ifscCode}" readonly />
                        </div>
                        <div class="form-group">
                            <label>PAN Number</label>
                            <input type="text" value="${profile.panNumber}" readonly />
                        </div>
                        <div class="form-group">
                            <label>Aadhar Number</label>
                            <input type="text" value="${profile.aadharNumber}" readonly />
                        </div>
                        <div class="form-group">
                            <label>&nbsp;</label>
                        </div>
                    </div>
                </div>

                <div class="form-actions" id="formActions" style="display:none;">
                    <button type="submit" class="btn-save">Save Changes</button>
                    <button type="button" id="cancelBtn" class="btn-cancel">Cancel</button>
                </div>
            </form>
        </div>
    </div>

    <script src="js/StudentProfile.js"></script>
    <script type="text/javascript" src="js/StudentDashboard.js"></script>
</body>
</html>