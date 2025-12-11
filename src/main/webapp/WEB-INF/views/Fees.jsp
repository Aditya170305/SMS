<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/FeesStyle.css">
<script src="https://cdnjs.cloudflare.com/ajax/libs/jspdf/2.5.1/jspdf.umd.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/jspdf-autotable/3.5.28/jspdf.plugin.autotable.min.js"></script>
<title>Fees Details</title>
</head>
<body>
	 <div style="gap: 10px;" class="display">
        
        <!-- Sidebar -->
        <nav class="dashboard">
            <ul>
                <li class="display">
                    <img src="${pageContext.request.contextPath}/images/home.svg" alt="home image">
                    Dashboard
                </li>
                <li class="display">
                    <img src="${pageContext.request.contextPath}/images/calendar.svg" alt="calendar image">
                    Class Schedule
                </li>
                <li class="display">
                    <img src="${pageContext.request.contextPath}/images/attendance.svg" alt="attendance image">
                    Attendance
                </li>
                <li class="display">
                    <img width="24px" src="${pageContext.request.contextPath}/images/book.svg" alt="book image">
                    Academics
                </li>
                <li class="display" style="background-color:#e8f2ff; border-left-color:#0066FF; color:#0066FF">
                    <img width="24px" src="${pageContext.request.contextPath}/images/fees.svg" alt="fees image">
                    Fees
                </li>
                <li class="display">
                    <img width="24px" src="${pageContext.request.contextPath}/images/profile.svg" alt="profile image">
                    Profile
                </li>
                <li class="display">
                    <img src="${pageContext.request.contextPath}/images/logout.svg" alt="logout image">
                    Logout
                </li>
            </ul>
        </nav>

        <!-- Right Section -->
        <div class="left fees-container">
            
            <h1 class="fees-heading">Fees Paid</h1>

            <!-- Select Course -->
            <div class="dropdown-container">
                <label>Select Course</label>
                <select id="courseSelect">
                    <c:forEach var="course" items="${courses}">
                        <option value="${course}">${course}</option>
                    </c:forEach>
                </select>
            </div>

            <!-- Summary Cards -->
            <div class="fees-summary">
                <div class="fee-card white-card">
                    <h2>Total Academic</h2>
                    <p id="totalAcademic">
                        <c:if test="${summary != null}">
                            ₹ <fmt:formatNumber value="${summary.totalAcademic}" pattern="#,##0.00"/>
                        </c:if>
                        <c:if test="${summary == null}">
                            ₹ 0.00
                        </c:if>
                    </p>
                </div>

                <div class="fee-card white-card">
                    <h2>Paid Fees</h2>
                    <p id="paidFees">
                        <c:if test="${summary != null}">
                            ₹ <fmt:formatNumber value="${summary.totalPaid}" pattern="#,##0.00"/>
                        </c:if>
                        <c:if test="${summary == null}">
                            ₹ 0.00
                        </c:if>
                    </p>
                </div>

                <div class="fee-card white-card">
                    <h2>Balance Fees</h2>
                    <p id="balanceFees">
                        <c:if test="${summary != null}">
                            ₹ <fmt:formatNumber value="${summary.totalBalance}" pattern="#,##0.00"/>
                        </c:if>
                        <c:if test="${summary == null}">
                            ₹ 0.00
                        </c:if>
                    </p>
                </div>
            </div>

            <!-- Receipt + Status Layout -->
            <div class="fees-bottom-section">

                <!-- Left: Receipt Download -->
                <div class="receipt-download white-card">
                    <h3>Download Receipt</h3>

                    <label>Select Semester</label>
                    <select class="receipt-select" id="semesterSelect">
                        <option value="">-- Select Semester --</option>
                        <c:forEach var="fee" items="${fees}">
                            <option value="${fee.semester}">${fee.semester}</option>
                        </c:forEach>
                    </select>

                    <button class="download-btn" onclick="generatePDF()">Download PDF</button>
                </div>

                <!-- Right: Fees Status Table -->
                <div class="fees-status white-card">
                    <h3>Fees Completion Status</h3>

                    <table class="status-table">
                        <thead>
                            <tr>
                                <th>Semester</th>
                                <th>Total</th>
                                <th>Paid</th>
                                <th>Balance</th>
                                <th>Status</th>
                            </tr>
                        </thead>
                        <tbody id="feeTableBody">
                            <c:forEach var="fee" items="${fees}">
                                <tr>
                                    <td>${fee.semester}</td>
                                    <td>₹ <fmt:formatNumber value="${fee.totalFees}" pattern="#,##0"/></td>
                                    <td>₹ <fmt:formatNumber value="${fee.paidFees}" pattern="#,##0"/></td>
                                    <td>₹ <fmt:formatNumber value="${fee.balanceFees}" pattern="#,##0"/></td>
                                    <td class="${fee.status == 'Paid' ? 'paid' : 'pending'}">${fee.status}</td>
                                </tr>
                            </c:forEach>
                            <c:if test="${empty fees}">
                                <tr>
                                    <td colspan="5" style="text-align: center;">No fee records found</td>
                                </tr>
                            </c:if>
                        </tbody>
                    </table>
                </div>

            </div>

        </div>

    </div>

    <!-- JavaScript for Dynamic Fees -->
    <script>
        document.addEventListener('DOMContentLoaded', function() {
            console.log("Fees page JavaScript loaded!");
            
            const courseSelect = document.getElementById("courseSelect");
            const semesterSelect = document.getElementById("semesterSelect");
            
            // When course changes, fetch new data
            courseSelect.addEventListener("change", async function() {
                const selectedCourse = this.value;
                console.log("Course changed to:", selectedCourse);
                
                try {
                    // Fetch summary
                    const summaryResponse = await fetch("/fees/summary?course=" + encodeURIComponent(selectedCourse));
                    const summary = await summaryResponse.json();
                    
                    // Update summary cards
                    document.getElementById("totalAcademic").textContent = "₹ " + formatNumber(summary.totalAcademic);
                    document.getElementById("paidFees").textContent = "₹ " + formatNumber(summary.totalPaid);
                    document.getElementById("balanceFees").textContent = "₹ " + formatNumber(summary.totalBalance);
                    
                    // Fetch fees list
                    const feesResponse = await fetch("/fees/bycourse?course=" + encodeURIComponent(selectedCourse));
                    const fees = await feesResponse.json();
                    
                    // Update table
                    updateFeeTable(fees);
                    
                    // Update semester dropdown
                    updateSemesterDropdown(fees);
                    
                    console.log("Fee data updated successfully!");
                    
                } catch (error) {
                    console.error("Error fetching fee data:", error);
                    alert("Error loading fee data. Please try again.");
                }
            });
        });
        
        // Format number with commas
        function formatNumber(num) {
            return new Intl.NumberFormat('en-IN', {
                minimumFractionDigits: 2,
                maximumFractionDigits: 2
            }).format(num);
        }
        
        // Update fee table
        function updateFeeTable(fees) {
            const tbody = document.getElementById("feeTableBody");
            tbody.innerHTML = "";
            
            if (!fees || fees.length === 0) {
                tbody.innerHTML = '<tr><td colspan="5" style="text-align: center;">No fee records found</td></tr>';
                return;
            }
            
            fees.forEach(function(fee) {
                const row = document.createElement("tr");
                const statusClass = fee.status === "Paid" ? "paid" : "pending";
                
                row.innerHTML = `
                    <td>\${fee.semester}</td>
                    <td>₹ \${formatNumber(fee.totalFees)}</td>
                    <td>₹ \${formatNumber(fee.paidFees)}</td>
                    <td>₹ \${formatNumber(fee.balanceFees)}</td>
                    <td class="\${statusClass}">\${fee.status}</td>
                `;
                
                tbody.appendChild(row);
            });
        }
        
        // Update semester dropdown
        function updateSemesterDropdown(fees) {
            const select = document.getElementById("semesterSelect");
            select.innerHTML = '<option value="">-- Select Semester --</option>';
            
            fees.forEach(function(fee) {
                const option = document.createElement("option");
                option.value = fee.semester;
                option.textContent = fee.semester;
                select.appendChild(option);
            });
        }
        
        // Generate PDF Receipt
        async function generatePDF() {
            const semester = document.getElementById("semesterSelect").value;
            const course = document.getElementById("courseSelect").value;
            
            if (!semester) {
                alert("Please select a semester first!");
                return;
            }
            
            try {
                // Fetch receipt data from backend
                const response = await fetch(`/fees/receipt?semester=\${encodeURIComponent(semester)}&course=\${encodeURIComponent(course)}`);
                const feeData = await response.json();
                
                if (!feeData) {
                    alert("No fee data found for selected semester!");
                    return;
                }
                
                const { jsPDF } = window.jspdf;
                const doc = new jsPDF();

                // Header
                doc.setFontSize(20);
                doc.text("XYZ College of Engineering", 10, 15);
                
                doc.setFontSize(12);
                doc.text("Official Fee Receipt", 10, 25);

                doc.setFontSize(10);
                doc.text("Receipt No: RCPT-" + Math.floor(Math.random() * 90000 + 10000), 150, 15);
                doc.text("Date: " + (feeData.paymentDate || new Date().toLocaleDateString()), 150, 22);

                // Student Details
                doc.setFontSize(13);
                doc.text("Student Information", 10, 40);

                let studentDetails = [
                    ["Student Name", "John Doe"],
                    ["Enrollment No", "CSE2021A001"],
                    ["Course", feeData.course],
                    ["Semester", feeData.semester]
                ];

                doc.autoTable({
                    startY: 45,
                    head: [["Field", "Details"]],
                    body: studentDetails,
                    theme: "grid",
                    styles: { fontSize: 11 }
                });

                // Fee Details
                doc.setFontSize(13);
                doc.text("Fee Breakdown", 10, doc.lastAutoTable.finalY + 15);

                // Calculate breakdown (example breakdown - adjust as needed)
                const tuitionFees = feeData.totalFees * 0.90;
                const examFees = feeData.totalFees * 0.05;
                const libraryFees = feeData.totalFees * 0.03;
                const otherCharges = feeData.totalFees * 0.02;

                let feeBreakdown = [
                    ["Tuition Fees", "₹ " + formatNumber(tuitionFees)],
                    ["Exam Fees", "₹ " + formatNumber(examFees)],
                    ["Library Fees", "₹ " + formatNumber(libraryFees)],
                    ["Other Charges", "₹ " + formatNumber(otherCharges)]
                ];

                doc.autoTable({
                    startY: doc.lastAutoTable.finalY + 20,
                    head: [["Fee Type", "Amount"]],
                    body: feeBreakdown,
                    theme: "grid",
                    styles: { fontSize: 11 }
                });

                // Summary
                doc.setFontSize(13);
                doc.text("Payment Summary", 10, doc.lastAutoTable.finalY + 20);

                let summary = [
                    ["Total Fees", "₹ " + formatNumber(feeData.totalFees)],
                    ["Paid Fees", "₹ " + formatNumber(feeData.paidFees)],
                    ["Balance", "₹ " + formatNumber(feeData.balanceFees)],
                    ["Status", feeData.status]
                ];

                doc.autoTable({
                    startY: doc.lastAutoTable.finalY + 25,
                    head: [["Description", "Amount"]],
                    body: summary,
                    theme: "grid",
                    styles: { fontSize: 11 }
                });

                // Signature
                doc.setFontSize(12);
                doc.text("Authorized Signature", 140, doc.lastAutoTable.finalY + 30);
                doc.line(140, doc.lastAutoTable.finalY + 32, 190, doc.lastAutoTable.finalY + 32);

                // Save PDF
                doc.save(semester.replace(/\s+/g, '_') + "_Fees_Receipt.pdf");
                
                console.log("PDF generated successfully!");
                
            } catch (error) {
                console.error("Error generating PDF:", error);
                alert("Error generating receipt. Please try again.");
            }
        }
    </script>
    <script type="text/javascript" src="js/StudentDashboard.js"></script>
</body>
</html>