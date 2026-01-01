document.addEventListener('DOMContentLoaded', function() {
    
    // DOM Elements
    const degreeSelect = document.getElementById("degreeSelect");
    const tableBody = document.querySelector("#courseTable tbody");
    const selectedCourse = document.getElementById("selectedCourse");
    const totalUsers = document.getElementById("totalUsers");

    // --- 1. Initialize Bar Chart (Semester Distribution) ---
    const ctxBar = document.getElementById("userChart").getContext("2d");
    let barChart = new Chart(ctxBar, {
        type: "bar",
        data: {
            labels: ["1st Sem", "2nd Sem", "3rd Sem", "4th Sem", "5th Sem", "6th Sem", "7th Sem", "8th Sem"],
            datasets: [{
                data: [0, 0, 0, 0, 0, 0, 0, 0], // Default Empty Data
                backgroundColor: "#0066FF",
                borderRadius: 4,
            }],
        },
        options: {
            responsive: true,
            maintainAspectRatio: false,
            plugins: { legend: { display: false } },
            scales: {
                y: { 
                    beginAtZero: true, 
                    title: { display: true, text: "No. of Students" },
                    ticks: { precision: 0 } 
                },
                x: { grid: { display: false } },
            },
        },
    });

    // --- 2. Initialize Line Chart (Enrollment Trends) ---
    const ctxLine = document.getElementById("enrollmentChart").getContext("2d");
    let lineChart = new Chart(ctxLine, {
        type: "line",
        data: {
            labels: ["Q1 (Jan-Mar)", "Q2 (Apr-Jun)", "Q3 (Jul-Sep)", "Q4 (Oct-Dec)"],
            datasets: [{
                label: "New Enrollments",
                data: [0, 0, 0, 0], // Default Empty Data
                borderColor: "#0066FF",
                backgroundColor: "rgba(0, 102, 255, 0.2)",
                borderWidth: 2,
                tension: 0.3,
                fill: true,
                pointRadius: 5,
            }],
        },
        options: {
            responsive: true,
            maintainAspectRatio: false,
            plugins: { legend: { display: true } },
            scales: { 
                y: { beginAtZero: true, ticks: { precision: 0 } }, 
                x: { grid: { display: false } } 
            },
        },
    });

    // --- 3. Dropdown Change Event Listener ---
    degreeSelect.addEventListener("change", function () {
        const selected = this.value;
        
        if (!selected) {
            resetDashboard();
            return;
        }

        console.log("Fetching data for:", selected);

        // Call Java Controller
        fetch(`/admin/dashboard/data?degree=${encodeURIComponent(selected)}`)
            .then(response => {
                if (!response.ok) throw new Error("Network response was not ok");
                return response.json();
            })
            .then(data => {
                updateDashboard(selected, data);
            })
            .catch(error => {
                console.error('Error fetching admin data:', error);
                tableBody.innerHTML = `<tr><td colspan="4" style="text-align:center;color:red;">Error loading data</td></tr>`;
            });
    });

    // --- 4. Update UI Function ---
    function updateDashboard(degree, data) {
        // Update Cards
        selectedCourse.textContent = degree;
        totalUsers.textContent = data.totalUsers;

        // Update Table
        tableBody.innerHTML = "";
        if(!data.branchStats || data.branchStats.length === 0) {
            tableBody.innerHTML = `<tr><td colspan="4" style="text-align:center;">No students found for this degree</td></tr>`;
        } else {
            data.branchStats.forEach(stat => {
                const row = `
                <tr>
                    <td>${stat.branch}</td>
                    <td>${stat.count}</td>
                    <td><span style="color:green;font-weight:bold;">Active</span></td>
                    <td><a href="#" class="action-link" style="color:#0066FF;text-decoration:none;">View</a></td>
                </tr>`;
                tableBody.insertAdjacentHTML("beforeend", row);
            });
        }

        // Update Charts
        barChart.data.datasets[0].data = data.semesterCounts;
        barChart.update();

        lineChart.data.datasets[0].data = data.enrollmentTrends;
        lineChart.update();
    }

    // --- 5. Reset Function ---
    function resetDashboard() {
        tableBody.innerHTML = `<tr><td colspan="4" style="text-align:center;">Select a degree to view data</td></tr>`;
        selectedCourse.textContent = "—";
        totalUsers.textContent = "0";
        barChart.data.datasets[0].data = [0, 0, 0, 0, 0, 0, 0, 0];
        lineChart.data.datasets[0].data = [0, 0, 0, 0];
        barChart.update();
        lineChart.update();
    }
});