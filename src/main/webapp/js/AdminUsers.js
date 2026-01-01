document.addEventListener("DOMContentLoaded", () => {
    
    // --- MAIN PAGE ELEMENTS ---
    const degreeSel = document.getElementById("degreeSelect");
    const branchSel = document.getElementById("branchSelect");
    const yearSel = document.getElementById("yearSelect");
    const semSel = document.getElementById("semSelect");
    const tableBody = document.querySelector("#studentTable tbody");
    const addStudentBtn = document.getElementById("addStudentBtn");

    // --- MODAL ELEMENTS ---
    const modal = document.getElementById("studentModal");
    const closeModalBtn = document.getElementById("closeModalBtn");
    const modalForm = document.getElementById("addStudentForm");
    // Modal Inputs
    const modalName = document.getElementById("modalName");
    const modalDegree = document.getElementById("modalDegree");
    const modalBranch = document.getElementById("modalBranch");
    const modalYear = document.getElementById("modalYear");
    const modalSemester = document.getElementById("modalSemester");

    // ====================================================
    // 1. LOAD STUDENTS FUNCTION (Main Page)
    // ====================================================
    function loadStudents() {
        const degree = degreeSel.value;
        const branch = branchSel.value;
        const year = yearSel.value;
        const semester = semSel.value;

        if (!degree || !branch || !year || !semester) {
            tableBody.innerHTML = `<tr><td colspan="4" style="text-align:center; color:#666;">Please select Degree, Branch, Year and Semester</td></tr>`;
            return;
        }

        tableBody.innerHTML = `<tr><td colspan="4" style="text-align:center;">Loading data...</td></tr>`;

        fetch(`/admin/users/list?degree=${encodeURIComponent(degree)}&branch=${encodeURIComponent(branch)}&semester=${encodeURIComponent(semester)}`)
            .then(response => response.json())
            .then(data => {
                if (!data || data.length === 0) {
                    tableBody.innerHTML = `<tr><td colspan="4" style="text-align:center; color: red;">No students found for this combination</td></tr>`;
                    return;
                }
                tableBody.innerHTML = data.map(st => `
                    <tr>
                        <td>${st.name}</td>
                        <td>${st.rollNo}</td>
                        <td>${st.email}</td>
                        <td><span style="color:${st.status === 'Active'?'green':'red'};font-weight:bold;">${st.status}</span></td>
                    </tr>
                `).join("");
            })
            .catch(error => {
                console.error("Error:", error);
                tableBody.innerHTML = `<tr><td colspan="4" style="text-align:center; color: red;">Error loading data</td></tr>`;
            });
    }

    // Main Page Event Listeners
    degreeSel.onchange = loadStudents;
    branchSel.onchange = loadStudents;
    yearSel.onchange = loadStudents;
    semSel.onchange = loadStudents;


    // ====================================================
    // 2. MODAL LOGIC (Open/Close & Dynamic Semesters)
    // ====================================================
    addStudentBtn.onclick = () => modal.style.display = "flex";
    closeModalBtn.onclick = () => modal.style.display = "none";
    window.onclick = (e) => { if (e.target === modal) modal.style.display = "none"; };

    // Dynamic Semester Options for Modal
    modalYear.onchange = () => {
        const y = modalYear.value;
        let opts = "";
        if(y==="1") opts=`<option value="1">Semester 1</option><option value="2">Semester 2</option>`;
        else if(y==="2") opts=`<option value="3">Semester 3</option><option value="4">Semester 4</option>`;
        else if(y==="3") opts=`<option value="5">Semester 5</option><option value="6">Semester 6</option>`;
        else if(y==="4") opts=`<option value="7">Semester 7</option><option value="8">Semester 8</option>`;
        modalSemester.innerHTML = opts;
    };

    // ====================================================
    // 3. HANDLE FORM SUBMISSION (AJAX)
    // ====================================================
    modalForm.onsubmit = (e) => {
        e.preventDefault(); // Stop page reload

        const name = modalName.value;
        const degree = modalDegree.value;
        const branch = modalBranch.value;
        const year = modalYear.value;
        const semester = modalSemester.value;

        // Call Backend API
        fetch(`/admin/users/add`, {
            method: "POST",
            headers: { "Content-Type": "application/x-www-form-urlencoded" },
            body: `name=${encodeURIComponent(name)}&degree=${encodeURIComponent(degree)}&branch=${encodeURIComponent(branch)}&year=${encodeURIComponent(year)}&semester=${encodeURIComponent(semester)}`
        })
        .then(response => response.text())
        .then(result => {
            if (result === "success") {
                alert("Student added successfully!");
                modal.style.display = "none";
                modalForm.reset(); // Clear form
                // Set main dropdowns to match added student and reload table
                degreeSel.value = degree;
                branchSel.value = branch;
                yearSel.value = year;
                semSel.value = semester;
                loadStudents(); 
            } else {
                alert("Failed to add student. Please try again.");
            }
        })
        .catch(err => console.error("Error adding student:", err));
    };
});