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

    let currentSubject = "";
    let allAttendanceData = [];

    console.log("View buttons found:", document.querySelectorAll(".view-btn-small").length);

    // Show modal per subject and fetch data
    document.querySelectorAll(".view-btn-small").forEach((btn) => {
        btn.addEventListener("click", async (e) => {
            e.preventDefault(); // Prevent any default button behavior
            
            console.log("View button clicked!");
            
            const subject = btn.getAttribute("data-subject");
            console.log("Subject:", subject);
            
            currentSubject = subject;
            modalTitle.textContent = `${subject} - Attendance History`;
            
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
            const url = `/attendance/data?subject=${encodeURIComponent(subject)}`;
            console.log('Fetching from URL:', url);
            
            const response = await fetch(url);
            console.log('Response status:', response.status);
            console.log('Response OK:', response.ok);
            
            if (!response.ok) {
                throw new Error(`HTTP error! status: ${response.status}`);
            }
            
            const data = await response.json();
            console.log('Fetched data:', data);
            console.log('Data type:', typeof data);
            console.log('Is array:', Array.isArray(data));
            console.log('Data length:', data.length);
            
            allAttendanceData = data;
            
            // Render table with all data
            renderTable(allAttendanceData);
            
        } catch (error) {
            console.error('ERROR fetching attendance data:', error);
            console.error('Error name:', error.name);
            console.error('Error message:', error.message);
            tableBody.innerHTML = `<tr><td colspan="2" style="text-align: center; color: red;">Error loading data: ${error.message}</td></tr>`;
            updateSummary(0, 0, 0);
        }
    }

    // Close modal
    closeBtn.addEventListener("click", () => {
        console.log("Close button clicked");
        modal.classList.add("hidden");
    });
    
    modal.addEventListener("click", (e) => {
        if (e.target === modal) {
            console.log("Modal background clicked");
            modal.classList.add("hidden");
        }
    });

    // Filter between dates
    filterBtn.addEventListener("click", () => {
        console.log("Filter button clicked");
        
        const from = fromDateInput.value ? new Date(fromDateInput.value) : null;
        const to = toDateInput.value ? new Date(toDateInput.value) : null;

        console.log("Filter from:", from);
        console.log("Filter to:", to);

        const filtered = allAttendanceData.filter(r => {
            const d = new Date(r.date);
            if (from && d < from) return false;
            if (to && d > to) return false;
            return true;
        });

        console.log("Filtered records:", filtered.length);
        renderTable(filtered);

        // Enable reset if filters applied
        if (from || to) {
            resetBtn.disabled = false;
        }
    });

    // Reset button (show all records)
    resetBtn.addEventListener("click", () => {
        console.log("Reset button clicked");
        fromDateInput.value = "";
        toDateInput.value = "";
        renderTable(allAttendanceData);
        resetBtn.disabled = true;
    });

    // Render Table Function
    function renderTable(data) {
        console.log("Rendering table with", data.length, "records");
        
        tableBody.innerHTML = "";
        
        if (!data || data.length === 0) {
            console.log("No data to display");
            tableBody.innerHTML = '<tr><td colspan="2" style="text-align: center;">No attendance records found</td></tr>';
            updateSummary(0, 0, 0);
            return;
        }
        
        data.forEach((r, index) => {
            console.log(`Row ${index}:`, r);
            
            const row = document.createElement("tr");
            const statusClass = r.status === "Present" ? "present" : "absent";
            
            row.innerHTML = `
                <td>${r.date}</td>
                <td class="${statusClass}">${r.status}</td>
            `;
            tableBody.appendChild(row);
        });

        const presentCount = data.filter(r => r.status === "Present").length;
        const absentCount = data.filter(r => r.status === "Absent").length;

        console.log("Present:", presentCount, "Absent:", absentCount);
        updateSummary(data.length, presentCount, absentCount);
    }

    // Update summary section
    function updateSummary(total, present, absent) {
        console.log("Updating summary - Total:", total, "Present:", present, "Absent:", absent);
        totalClasses.textContent = total;
        daysPresent.textContent = present;
        daysAbsent.textContent = absent || (total - present);
    }
    
    console.log("All event listeners attached successfully!");
});