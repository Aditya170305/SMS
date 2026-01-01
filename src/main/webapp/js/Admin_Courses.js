document.addEventListener("DOMContentLoaded", () => {
    
    // ====================================================
    // 1. SIDEBAR NAVIGATION
    // ====================================================
    const links = document.querySelectorAll(".dashboard ul li");
    links.forEach(li => {
        li.addEventListener("click", () => {
            const page = li.getAttribute("data-page");
            if (page) window.location.href = page;
        });
    });

    // ====================================================
    // 2. DOM ELEMENTS
    // ====================================================
    // Main Page Dropdowns
    const degreeSelect = document.getElementById("degreeSelect");
    const yearSelect   = document.getElementById("yearSelect");
    const semSelect    = document.getElementById("semSelect");

    // Stats Display Elements
    const totalSpan = document.getElementById("totalStudents");
    const activeP   = document.getElementById("activeStudents");
    const inactiveP = document.getElementById("inactiveStudents");

    // Modal Elements
    const modal = document.getElementById("courseModal");
    const addBtn = document.querySelector(".add-btn");
    const closeBtn = document.getElementById("closeModalBtn");
    
    // Modal Form Elements
    const modalForm = document.getElementById("addCourseForm");
    const modalYear = document.getElementById("modalYear");
    const modalSemester = document.getElementById("modalSemester");
    const modalStudentName = document.getElementById("modalStudentName"); // New Name Field

    // ====================================================
    // 3. DYNAMIC SEMESTER LOGIC (Main Page & Modal)
    // ====================================================
    
    // Function to update semester options based on year
    function updateSemesters(yearDropdown, semDropdown) {
        const year = yearDropdown.value;
        let options = "";

        if (year === "Year 1") {
            options = `<option value="Semester 1">Semester 1</option><option value="Semester 2">Semester 2</option>`;
        } else if (year === "Year 2") {
            options = `<option value="Semester 3">Semester 3</option><option value="Semester 4">Semester 4</option>`;
        } else if (year === "Year 3") {
            options = `<option value="Semester 5">Semester 5</option><option value="Semester 6">Semester 6</option>`;
        } else if (year === "Year 4") {
            options = `<option value="Semester 7">Semester 7</option><option value="Semester 8">Semester 8</option>`;
        }
        semDropdown.innerHTML = options;
    }

    // Listener for Main Page Year Change
    yearSelect.addEventListener("change", () => {
        updateSemesters(yearSelect, semSelect);
        fetchRealStats(); // Fetch new stats immediately
    });

    // Listener for Modal Year Change
    modalYear.addEventListener("change", () => {
        updateSemesters(modalYear, modalSemester);
    });

    // ====================================================
    // 4. FETCH REAL STATS FROM DATABASE
    // ====================================================
    function fetchRealStats() {
        const degree = degreeSelect.value;
        const semester = semSelect.value; 

        // UI Feedback: Show loading...
        if(totalSpan) totalSpan.innerText = "...";
        if(activeP) activeP.innerText = "Loading...";
        if(inactiveP) inactiveP.innerText = "";

        fetch(`/admin/courses/stats?degree=${encodeURIComponent(degree)}&semester=${encodeURIComponent(semester)}`)
            .then(response => {
                if (!response.ok) throw new Error("Network response was not ok");
                return response.json();
            })
            .then(data => {
                // Update HTML with Real DB Data
                totalSpan.innerText = data.totalStudents;
                activeP.innerText = `Active Students: ${data.activeStudents}`;
                inactiveP.innerText = `Inactive Students: ${data.inactiveStudents}`;
            })
            .catch(error => {
                console.error("Error fetching stats:", error);
                totalSpan.innerText = "0";
                activeP.innerText = "Active Students: 0";
                inactiveP.innerText = "Inactive Students: 0";
            });
    }

    // Trigger fetch on dropdown changes
    degreeSelect.addEventListener("change", fetchRealStats);
    semSelect.addEventListener("change", fetchRealStats);

    // Initial Fetch on Load
    fetchRealStats();

    // ====================================================
    // 5. MODAL OPEN/CLOSE LOGIC
    // ====================================================
    addBtn.addEventListener("click", () => modal.style.display = "flex");
    
    closeBtn.addEventListener("click", () => modal.style.display = "none");
    
    window.addEventListener("click", (e) => {
        if (e.target === modal) modal.style.display = "none";
    });

    // ====================================================
    // 6. HANDLE ADD COURSE SUBMISSION (With Student Name)
    // ====================================================
    modalForm.addEventListener("submit", (e) => {
        e.preventDefault(); // STOP PAGE RELOAD

        // Gather Data
        const studentName = modalStudentName.value;
        const degree = document.getElementById("modalDegree").value;
        const year = document.getElementById("modalYear").value;
        const semester = document.getElementById("modalSemester").value;

        console.log(`Adding Student: ${studentName} to ${degree} - ${semester}`);

        // Call Backend API
        fetch(`/admin/courses/add`, {
            method: "POST",
            headers: { "Content-Type": "application/x-www-form-urlencoded" },
            body: `studentName=${encodeURIComponent(studentName)}&degree=${encodeURIComponent(degree)}&year=${encodeURIComponent(year)}&semester=${encodeURIComponent(semester)}`
        })
        .then(response => response.text())
        .then(result => {
            if (result === "success") {
                alert(`Successfully enrolled ${studentName} in ${degree}!`);
                modal.style.display = "none"; // Close Modal
                
                // Clear Name Input
                modalStudentName.value = "";

                // Update Main Page Dropdowns to match what was just added
                degreeSelect.value = degree;
                yearSelect.value = year;
                
                // Trigger updates to refresh the view
                updateSemesters(yearSelect, semSelect);
                semSelect.value = semester;
                fetchRealStats(); // Refresh numbers
            } else {
                alert("Failed to add student. Please try again.");
            }
        })
        .catch(error => console.error("Error adding course:", error));
    });

});