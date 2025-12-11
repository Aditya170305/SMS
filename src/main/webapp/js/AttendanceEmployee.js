// Wait for DOM to load
document.addEventListener('DOMContentLoaded', function() {
    console.log("AttendanceEmployee.js loaded!");
    
    const semesterSelect = document.getElementById("semesterSelect");
    const subjectSelect = document.getElementById("subjectSelect");
    const dateSelect = document.getElementById("dateSelect");
    const tableBody = document.getElementById("attendanceTableBody");
    const saveBtn = document.getElementById("saveAttendanceBtn");
    
    let attendanceData = []; // Store student data
    const teacherId = "TEACHER001"; // In real app, get from session
    
    // Set today's date as default
    const today = new Date().toISOString().split('T')[0];
    dateSelect.value = today;
    
    // Load students when semester and subject are selected
    semesterSelect.addEventListener("change", loadStudents);
    subjectSelect.addEventListener("change", loadStudents);
    
    // Save attendance button
    saveBtn.addEventListener("click", saveAttendance);
    
    // Function to load students
    async function loadStudents() {
        const semester = semesterSelect.value;
        const subject = subjectSelect.value;
        
        if (!semester || !subject) {
            tableBody.innerHTML = '<tr><td colspan="4" style="text-align: center; padding: 30px; color: #999;">Please select both Semester and Subject</td></tr>';
            saveBtn.disabled = true;
            return;
        }
        
        console.log("Loading students for:", semester, subject);
        tableBody.innerHTML = '<tr><td colspan="4" style="text-align: center; padding: 30px;">Loading students...</td></tr>';
        
        try {
            const response = await fetch(`/teacher/attendance/students?semester=${encodeURIComponent(semester)}&subject=${encodeURIComponent(subject)}`);
            
            if (!response.ok) {
                throw new Error("Failed to fetch students");
            }
            
            const students = await response.json();
            console.log("Students loaded:", students.length);
            
            if (students.length === 0) {
                tableBody.innerHTML = '<tr><td colspan="4" style="text-align: center; padding: 30px; color: #999;">No students found for this semester</td></tr>';
                saveBtn.disabled = true;
                return;
            }
            
            // Store student data
            attendanceData = students.map(student => ({
                rollNo: student.rollNo,
                name: student.name,
                present: null // null = not marked, true = present, false = absent
            }));
            
            // Check if attendance already exists for today
            await checkExistingAttendance();
            
            // Render table
            renderTable();
            saveBtn.disabled = false;
            
        } catch (error) {
            console.error("Error loading students:", error);
            tableBody.innerHTML = '<tr><td colspan="4" style="text-align: center; padding: 30px; color: red;">Error loading students. Please try again.</td></tr>';
            saveBtn.disabled = true;
        }
    }
    
    // Check if attendance already exists for selected date
    async function checkExistingAttendance() {
        const date = dateSelect.value;
        const semester = semesterSelect.value;
        const subject = subjectSelect.value;
        
        if (!date || !semester || !subject) return;
        
        try {
            const response = await fetch(`/teacher/attendance/getbydate?date=${date}&semester=${encodeURIComponent(semester)}&subject=${encodeURIComponent(subject)}`);
            
            if (response.ok) {
                const existingAttendance = await response.json();
                console.log("Existing attendance found:", existingAttendance.length);
                
                // Update attendanceData with existing records
                existingAttendance.forEach(record => {
                    const student = attendanceData.find(s => s.rollNo === record.rollNo);
                    if (student) {
                        student.present = record.status === "Present";
                    }
                });
            }
        } catch (error) {
            console.error("Error checking existing attendance:", error);
        }
    }
    
    // Render the attendance table
    function renderTable() {
        tableBody.innerHTML = "";
        
        attendanceData.forEach((student, index) => {
            const row = document.createElement("tr");
            
            row.innerHTML = `
                <td>${student.rollNo}</td>
                <td>${student.name}</td>
                <td>
                    <input type="checkbox" 
                           class="attendance-checkbox"
                           data-index="${index}"
                           ${student.present === true ? "checked" : ""}>
                </td>
                <td id="status-${index}">
                    ${getStatusText(student.present)}
                </td>
            `;
            
            tableBody.appendChild(row);
        });
        
        attachCheckboxListeners();
    }
    
    // Get status text HTML
    function getStatusText(present) {
        if (present === true) return '<span class="present-text">Present</span>';
        if (present === false) return '<span class="absent-text">Absent</span>';
        return '<span class="not-marked">-</span>';
    }
    
    // Attach checkbox event listeners
    function attachCheckboxListeners() {
        const checkboxes = document.querySelectorAll(".attendance-checkbox");
        
        checkboxes.forEach(checkbox => {
            checkbox.addEventListener("change", function() {
                const index = parseInt(this.getAttribute("data-index"));
                
                // Update data
                attendanceData[index].present = this.checked;
                
                // Update UI instantly
                const statusCell = document.getElementById(`status-${index}`);
                statusCell.innerHTML = getStatusText(this.checked);
            });
        });
    }
    
    // Save attendance to database
    async function saveAttendance() {
        const semester = semesterSelect.value;
        const subject = subjectSelect.value;
        const date = dateSelect.value;
        
        if (!semester || !subject || !date) {
            alert("Please select Semester, Subject, and Date");
            return;
        }
        
        // Check if all students are marked
        const unmarked = attendanceData.filter(s => s.present === null);
        if (unmarked.length > 0) {
            const confirmSave = confirm(`${unmarked.length} student(s) are not marked. They will be marked as Absent. Continue?`);
            if (!confirmSave) return;
        }
        
        // Prepare data for API
        const payload = {
            teacherId: teacherId,
            date: date,
            semester: semester,
            subject: subject,
            attendance: attendanceData.map(student => ({
                rollNo: student.rollNo,
                name: student.name,
                status: student.present === true ? "Present" : "Absent"
            }))
        };
        
        console.log("Saving attendance:", payload);
        saveBtn.disabled = true;
        saveBtn.textContent = "Saving...";
        
        try {
            const response = await fetch("/teacher/attendance/save", {
                method: "POST",
                headers: {
                    "Content-Type": "application/json"
                },
                body: JSON.stringify(payload)
            });
            
            const result = await response.json();
            
            if (result.success) {
                alert("✅ " + result.message);
                console.log("Attendance saved successfully!");
            } else {
                alert("❌ " + result.message);
                console.error("Failed to save attendance");
            }
            
        } catch (error) {
            console.error("Error saving attendance:", error);
            alert("❌ Error saving attendance. Please try again.");
        } finally {
            saveBtn.disabled = false;
            saveBtn.textContent = "Save Attendance";
        }
    }
    
    // Date change listener
    dateSelect.addEventListener("change", async function() {
        if (attendanceData.length > 0) {
            await checkExistingAttendance();
            renderTable();
        }
    });
    
    console.log("AttendanceEmployee.js initialized successfully!");
});