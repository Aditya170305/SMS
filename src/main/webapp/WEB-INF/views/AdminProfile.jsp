<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Admin Profile</title>
<link rel="stylesheet" href="css/AdminProfileStyle.css" />
</head>
<body>
    <div class="main-wrapper">
        <nav class="dashboard">
            <ul>
                <li onclick="location.href='/adminDashboard'"><img src="images/home.svg"> Dashboard</li>
                <li onclick="location.href='/adminCourses'"><img src="images/book.svg"> Courses</li>
                <li><img src="images/requests.svg"> Requests</li>
                <li onclick="location.href='/users'"><img src="images/username.svg"> Users</li>
                <li class="active"><img src="images/user.svg"> Profile</li>
                <li><img src="images/logout.svg"> Logout</li>
            </ul>
        </nav>

        <div class="main-content">
            <div class="profile-header">
                <h1>Admin Profile</h1>
                <button id="editBtn" class="btn-edit">Edit Profile</button>
            </div>

            <form id="profileForm" class="profile-form">
                
                <div class="section-card">
                    <h2 class="section-title">Personal Details</h2>
                    <div class="form-grid">
                        <div class="form-group"><label>First Name</label><input type="text" name="firstName" value="${profile.firstName}" readonly /></div>
                        <div class="form-group"><label>Middle Name</label><input type="text" name="middleName" value="${profile.middleName}" readonly /></div>
                        <div class="form-group"><label>Last Name</label><input type="text" name="lastName" value="${profile.lastName}" readonly /></div>
                        <div class="form-group"><label>Gender</label><input type="text" name="gender" value="${profile.gender}" readonly /></div>
                        <div class="form-group"><label>Date of Birth</label><input type="date" name="dateOfBirth" value="${profile.dateOfBirth}" readonly /></div>
                        <div class="form-group"><label>Blood Group</label><input type="text" name="bloodGroup" value="${profile.bloodGroup}" readonly /></div>
                    </div>
                </div>

                <div class="section-card">
                    <h2 class="section-title">Professional Details</h2>
                    <div class="form-grid">
                        <div class="form-group"><label>Employee Code</label><input type="text" name="employeeCode" value="${profile.employeeCode}" readonly style="background:#e9ecef;" /></div>
                        <div class="form-group"><label>Department</label><input type="text" name="department" value="${profile.department}" readonly /></div>
                        <div class="form-group"><label>Designation</label><input type="text" name="designation" value="${profile.designation}" readonly /></div>
                        <div class="form-group"><label>Joining Date</label><input type="date" name="joiningDate" value="${profile.joiningDate}" readonly /></div>
                    </div>
                </div>

                <div class="section-card">
                    <h2 class="section-title">Contact Information</h2>
                    <div class="form-grid">
                        <div class="form-group"><label>Mobile Number</label><input type="tel" name="mobile" value="${profile.mobile}" readonly /></div>
                        <div class="form-group form-group-full"><label>Email Address</label><input type="email" name="email" value="${profile.email}" readonly /></div>
                        <div class="form-group form-group-full"><label>Address</label><input type="text" name="address" value="${profile.address}" readonly /></div>
                        <div class="form-group"><label>City</label><input type="text" name="city" value="${profile.city}" readonly /></div>
                        <div class="form-group"><label>State</label><input type="text" name="state" value="${profile.state}" readonly /></div>
                        <div class="form-group"><label>Pincode</label><input type="text" name="pincode" value="${profile.pincode}" readonly /></div>
                    </div>
                </div>

                <div class="section-card">
                    <h2 class="section-title">Bank & Document Details</h2>
                    <div class="form-grid">
                        <div class="form-group"><label>Bank Name</label><input type="text" name="bankName" value="${profile.bankName}" readonly /></div>
                        <div class="form-group"><label>Account Number</label><input type="text" name="accountNumber" value="${profile.accountNumber}" readonly /></div>
                        <div class="form-group"><label>IFSC Code</label><input type="text" name="ifscCode" value="${profile.ifscCode}" readonly /></div>
                        <div class="form-group"><label>PAN Number</label><input type="text" name="panNumber" value="${profile.panNumber}" readonly /></div>
                        <div class="form-group"><label>Aadhar Number</label><input type="text" name="aadharNumber" value="${profile.aadharNumber}" readonly /></div>
                    </div>
                </div>

                <div class="form-actions" id="formActions" style="display:none;">
                    <button type="submit" class="btn-save">Save Changes</button>
                    <button type="button" id="cancelBtn" class="btn-cancel">Cancel</button>
                </div>
            </form>
        </div>
    </div>
    <script src="js/AdminProfile.js"></script>
    <script type="text/javascript" src="js/Admin.js"></script>
</body>
</html>