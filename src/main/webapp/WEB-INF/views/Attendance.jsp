<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Attendance Dashboard - Student Dashboard</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/StudentDashboard-AttendanceStyle.css" />
<!-- Add jsPDF and jsPDF-AutoTable for PDF export -->
<script src="https://cdnjs.cloudflare.com/ajax/libs/jspdf/2.5.1/jspdf.umd.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/jspdf-autotable/3.5.31/jspdf.plugin.autotable.min.js"></script>
<!-- Add SheetJS for Excel export -->
<script src="https://cdnjs.cloudflare.com/ajax/libs/xlsx/0.18.5/xlsx.full.min.js"></script>
</head>
<body>
<div class="display">
<!-- Sidebar -->
<nav class="dashboard">
<ul>
<li class="display">
<img src="${pageContext.request.contextPath}/images/home.svg" alt="home image" /> Dashboard
</li>
<li class="display">
<img src="${pageContext.request.contextPath}/images/calendar.svg" alt="calendar image" /> Class Schedule
</li>
<li class="display active">
<img src="${pageContext.request.contextPath}/images/attendance.svg" alt="attendance image" /> Attendance
</li>
<li class="display">
<img src="${pageContext.request.contextPath}/images/book.svg" alt="book image" /> Academics
</li>
<li class="display">
<img src="${pageContext.request.contextPath}/images/fees.svg" alt="fees image" /> Fees
</li>
<li class="display">
<img src="${pageContext.request.contextPath}/images/profile.svg" alt="profile image" /> Profile
</li>
<li class="display">
<img src="${pageContext.request.contextPath}/images/logout.svg" alt="logout image" /> Logout
</li>
</ul>
</nav>

<!-- Main Section -->
<div class="left">
<h1 class="heading">Attendance Overview</h1>

<div class="attendance-section">
<!-- Theory -->
<div class="column">
<h2>Theory Classes</h2>

<c:set var="theorySubjects" value="${['BT-301 – Data Structure', 'BT-302 – Discrete Structure', 'BT-303 – Theory of Computation', 'BT-304 – Digital Systems', 'BT-305 – Object Oriented Programming']}" />
<c:forEach var="subject" items="${theorySubjects}">
    <c:choose>
        <c:when test="${percentages[subject] != null}">
            <c:set var="percentageValue" value="${percentages[subject].percentage}" />
        </c:when>
        <c:otherwise>
            <c:set var="percentageValue" value="0" />
        </c:otherwise>
    </c:choose>
    
    <div class="attendance-card" data-subject="${subject}">
        <h3>${subject}</h3>
        <input type="range" min="0" max="100" value="${percentageValue}" class="slider" style="--value:${percentageValue}%;" readonly />
        <div class="attendance-footer">
            <p>
                <c:choose>
                    <c:when test="${percentages[subject] != null}">
                        <fmt:formatNumber value="${percentageValue}" maxFractionDigits="1" />% Attendance
                    </c:when>
                    <c:otherwise>
                        No Data
                    </c:otherwise>
                </c:choose>
            </p>
            <button class="view-btn-small" data-subject="${subject}">View</button>
        </div>
    </div>
</c:forEach>
</div>

<!-- Lab -->
<div class="column">
<h2>Lab Classes</h2>

<c:set var="labSubjects" value="${['BT-301 (Lab) – Data Structure Lab', 'BT-302 (Lab) – Discrete Structure Lab', 'BT-304 (Lab) – Digital Systems Lab', 'BT-305 (Lab) – OOP Lab']}" />
<c:forEach var="subject" items="${labSubjects}">
    <c:choose>
        <c:when test="${percentages[subject] != null}">
            <c:set var="percentageValue" value="${percentages[subject].percentage}" />
        </c:when>
        <c:otherwise>
            <c:set var="percentageValue" value="0" />
        </c:otherwise>
    </c:choose>
    
    <div class="attendance-card" data-subject="${subject}">
        <h3>${subject}</h3>
        <input type="range" min="0" max="100" value="${percentageValue}" class="slider" style="--value:${percentageValue}%;" readonly />
        <div class="attendance-footer">
            <p>
                <c:choose>
                    <c:when test="${percentages[subject] != null}">
                        <fmt:formatNumber value="${percentageValue}" maxFractionDigits="1" />% Attendance
                    </c:when>
                    <c:otherwise>
                        No Data
                    </c:otherwise>
                </c:choose>
            </p>
            <button class="view-btn-small" data-subject="${subject}">View</button>
        </div>
    </div>
</c:forEach>
</div>
</div>
</div>
</div>

<!-- Modal Popup -->
<div id="attendanceModal" class="modal hidden">
<div class="modal-content">
<span class="close">&times;</span>
<h2 id="modalSubjectTitle">Attendance History</h2>

<div class="date-filter">
<label>From: <input type="date" id="fromDate"></label>
<label>To: <input type="date" id="toDate"></label>
<button id="filterBtn">Filter</button>
<button id="resetBtn" class="reset-btn" disabled>Reset</button>
</div>

<!-- Export Buttons -->
<div class="export-buttons" style="margin: 15px 0; text-align: right;">
<button id="exportPdfBtn" class="export-btn" style="background: #dc3545; color: white; padding: 8px 16px; border: none; border-radius: 4px; cursor: pointer; margin-right: 10px;">
📄 Export to PDF
</button>
<button id="exportExcelBtn" class="export-btn" style="background: #28a745; color: white; padding: 8px 16px; border: none; border-radius: 4px; cursor: pointer;">
📊 Export to Excel
</button>
</div>

<div class="summary">
<p><strong>Total Classes:</strong> <span id="totalClasses">0</span></p>
<p><strong>Days Present:</strong> <span id="daysPresent">0</span></p>
<p><strong>Days Absent:</strong> <span id="daysAbsent">0</span></p>
</div>

<table id="attendanceTable">
<thead>
<tr>
<th>Date</th>
<th>Status</th>
</tr>
</thead>
<tbody id="attendanceTableBody"></tbody>
</table>
</div>
</div>

<!-- Embedded JavaScript for Attendance -->
<script>
// Wait for DOM to load before attaching event listeners
document.addEventListener('DOMContentLoaded', function() {
    console.log("JavaScript loaded successfully!");
    
    const modal = document.getElementById("attendanceModal");
    const closeBtn = document.querySelector(".close");
    const tableBody = document.getElementById("attendanceTableBody");
    const totalClasses = document.getElementById("totalClasses");
    const daysPresent = document.getElementById("daysPresent");
    const daysAbsent = document.getElementById("daysAbsent");
    const modalTitle = document.getElementById("modalSubjectTitle");
    const filterBtn = document.getElementById("filterBtn");
    const resetBtn = document.getElementById("resetBtn");
    const fromDateInput = document.getElementById("fromDate");
    const toDateInput = document.getElementById("toDate");
    const exportPdfBtn = document.getElementById("exportPdfBtn");
    const exportExcelBtn = document.getElementById("exportExcelBtn");

    let currentSubject = "";
    let allAttendanceData = [];
    let displayedData = []; // Data currently shown in table

    console.log("View buttons found:", document.querySelectorAll(".view-btn-small").length);

    // Show modal per subject and fetch data
    document.querySelectorAll(".view-btn-small").forEach(function(btn) {
        btn.addEventListener("click", async function(e) {
            e.preventDefault();
            
            console.log("View button clicked!");
            
            const subject = btn.getAttribute("data-subject");
            console.log("Subject:", subject);
            
            currentSubject = subject;
            modalTitle.textContent = subject + " - Attendance History";
            
            // Reset filters
            fromDateInput.value = "";
            toDateInput.value = "";
            resetBtn.disabled = true;
            
            // Show loading message
            tableBody.innerHTML = '<tr><td colspan="2" style="text-align: center;">Loading...</td></tr>';
            
            // Show modal
            modal.classList.remove("hidden");
            console.log("Modal opened");
            
            // Fetch attendance data from backend
            await fetchAttendanceData(subject);
        });
    });

    // Fetch attendance data from backend
    async function fetchAttendanceData(subject) {
        try {
            const url = "/attendance/data?subject=" + encodeURIComponent(subject);
            console.log('Fetching from URL:', url);
            
            const response = await fetch(url);
            console.log('Response status:', response.status);
            
            if (!response.ok) {
                throw new Error("HTTP error! status: " + response.status);
            }
            
            const data = await response.json();
            console.log('Fetched data:', data);
            console.log('Data length:', data.length);
            
            allAttendanceData = data;
            
            // Render table with all data
            renderTable(allAttendanceData);
            
        } catch (error) {
            console.error('ERROR fetching attendance data:', error);
            tableBody.innerHTML = '<tr><td colspan="2" style="text-align: center; color: red;">Error loading data: ' + error.message + '</td></tr>';
            updateSummary(0, 0, 0);
        }
    }

    // Close modal
    closeBtn.addEventListener("click", function() {
        modal.classList.add("hidden");
    });
    
    modal.addEventListener("click", function(e) {
        if (e.target === modal) {
            modal.classList.add("hidden");
        }
    });

    // Filter between dates
    filterBtn.addEventListener("click", function() {
        const from = fromDateInput.value ? new Date(fromDateInput.value) : null;
        const to = toDateInput.value ? new Date(toDateInput.value) : null;

        const filtered = allAttendanceData.filter(function(r) {
            const d = new Date(r.date);
            if (from && d < from) return false;
            if (to && d > to) return false;
            return true;
        });

        renderTable(filtered);

        if (from || to) {
            resetBtn.disabled = false;
        }
    });

    // Reset button
    resetBtn.addEventListener("click", function() {
        fromDateInput.value = "";
        toDateInput.value = "";
        renderTable(allAttendanceData);
        resetBtn.disabled = true;
    });

    // Render Table Function
    function renderTable(data) {
        console.log("Rendering table with", data.length, "records");
        
        tableBody.innerHTML = "";
        displayedData = data; // Store for export
        
        if (!data || data.length === 0) {
            tableBody.innerHTML = '<tr><td colspan="2" style="text-align: center;">No attendance records found</td></tr>';
            updateSummary(0, 0, 0);
            return;
        }
        
        for (var i = 0; i < data.length; i++) {
            var r = data[i];
            var row = document.createElement("tr");
            var statusClass = r.status === "Present" ? "present" : "absent";
            
            row.innerHTML = '<td>' + r.date + '</td><td class="' + statusClass + '">' + r.status + '</td>';
            tableBody.appendChild(row);
        }

        var presentCount = data.filter(function(r) { return r.status === "Present"; }).length;
        var absentCount = data.filter(function(r) { return r.status === "Absent"; }).length;

        updateSummary(data.length, presentCount, absentCount);
    }

    // Update summary section
    function updateSummary(total, present, absent) {
        totalClasses.textContent = total;
        daysPresent.textContent = present;
        daysAbsent.textContent = absent || (total - present);
    }
    
    // Export to PDF
    exportPdfBtn.addEventListener("click", function() {
        if (displayedData.length === 0) {
            alert("No data to export!");
            return;
        }
        
        try {
            const { jsPDF } = window.jspdf;
            const doc = new jsPDF();
            
            // Add title
            doc.setFontSize(16);
            doc.text(currentSubject + " - Attendance Report", 14, 20);
            
            // Add summary
            doc.setFontSize(12);
            doc.text("Total Classes: " + totalClasses.textContent, 14, 30);
            doc.text("Days Present: " + daysPresent.textContent, 14, 37);
            doc.text("Days Absent: " + daysAbsent.textContent, 14, 44);
            
            // Add table
            const tableData = displayedData.map(function(r) {
                return [r.date, r.status];
            });
            
            doc.autoTable({
                head: [['Date', 'Status']],
                body: tableData,
                startY: 50,
                theme: 'grid',
                headStyles: { fillColor: [66, 139, 202] }
            });
            
            // Save the PDF
            doc.save(currentSubject.replace(/[^a-zA-Z0-9]/g, '_') + '_Attendance.pdf');
            console.log("PDF exported successfully!");
        } catch (error) {
            console.error("Error exporting PDF:", error);
            alert("Error exporting PDF. Check console for details.");
        }
    });
    
    // Export to Excel
    exportExcelBtn.addEventListener("click", function() {
        if (displayedData.length === 0) {
            alert("No data to export!");
            return;
        }
        
        try {
            // Prepare data for Excel
            const excelData = [
                [currentSubject + " - Attendance Report"],
                [],
                ["Total Classes", totalClasses.textContent],
                ["Days Present", daysPresent.textContent],
                ["Days Absent", daysAbsent.textContent],
                [],
                ["Date", "Status"]
            ];
            
            // Add attendance records
            displayedData.forEach(function(r) {
                excelData.push([r.date, r.status]);
            });
            
            // Create workbook and worksheet
            const wb = XLSX.utils.book_new();
            const ws = XLSX.utils.aoa_to_sheet(excelData);
            
            // Set column widths
            ws['!cols'] = [{ wch: 15 }, { wch: 15 }];
            
            // Add worksheet to workbook
            XLSX.utils.book_append_sheet(wb, ws, "Attendance");
            
            // Save the Excel file
            XLSX.writeFile(wb, currentSubject.replace(/[^a-zA-Z0-9]/g, '_') + '_Attendance.xlsx');
            console.log("Excel exported successfully!");
        } catch (error) {
            console.error("Error exporting Excel:", error);
            alert("Error exporting Excel. Check console for details.");
        }
    });
    
    console.log("All event listeners attached successfully!");
});
</script>
	<script type="text/javascript" src="js/StudentDashboard.js"></script>
</body>
</html>