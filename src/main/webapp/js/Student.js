document.addEventListener('DOMContentLoaded', function() {
    
    const applyBtn = document.getElementById("applyBtn");
    
    // Add event listener to Apply button
    applyBtn.addEventListener("click", loadStudents);

    async function loadStudents() {
        const subject = document.getElementById("subjectSelect").value;
        const semester = document.getElementById("semesterSelect").value;
        const tableBody = document.getElementById("tableBody");

        // Validation
        if (!subject || !semester) {
            alert("Please select both Subject and Semester.");
            return;
        }

        // Show loading state
        tableBody.innerHTML = `<tr><td colspan="7" style="text-align:center;">Loading data...</td></tr>`;

        try {
            // Fetch data from new Controller Endpoint
            const response = await fetch(`/teacher/students/performance?subject=${encodeURIComponent(subject)}&semester=${encodeURIComponent(semester)}`);
            
            if (!response.ok) {
                throw new Error("Network response was not ok");
            }

            const data = await response.json();

            // Clear table
            tableBody.innerHTML = "";

            if (data.length === 0) {
                tableBody.innerHTML = `<tr><td colspan="7" style="text-align:center;">No students found for this selection.</td></tr>`;
                return;
            }

            // Populate Table
            data.forEach(std => {
                let statusColor = "green";
                if(std.status === "Average") statusColor = "orange";
                if(std.status === "Needs Improvement") statusColor = "red";

                const row = `
                    <tr>
                        <td>${std.rollNo}</td>
                        <td>${std.name}</td>
                        <td>${std.department}</td>
                        <td>${std.semester}</td>
                        <td>${std.testScore}</td>
                        <td>${std.attendancePercentage}%</td>
                        <td style="color:${statusColor}; font-weight:bold;">${std.status}</td>
                    </tr>
                `;
                tableBody.innerHTML += row;
            });

        } catch (error) {
            console.error("Error:", error);
            tableBody.innerHTML = `<tr><td colspan="7" style="text-align:center; color:red;">Error fetching data.</td></tr>`;
        }
    }
});

// ----------------------------------------------------
// Export Table to PDF (Kept from your original code)
// ----------------------------------------------------
window.exportPDF = async function() {
    const element = document.getElementById("studentTable");
    // Basic check to ensure table has data
    if(element.rows.length <= 2) {
        alert("No data to export");
        return;
    }

    const canvas = await html2canvas(element);
    const imgData = canvas.toDataURL("image/png");

    const { jsPDF } = window.jspdf; 
    const pdf = new jsPDF("p", "mm", "a4");
    
    let width = pdf.internal.pageSize.getWidth();
    let height = (canvas.height * width) / canvas.width;

    pdf.addImage(imgData, "PNG", 0, 0, width, height);
    pdf.save("Students_Report.pdf");
}

// ----------------------------------------------------
// Export Table to Excel (CSV) (Kept from your original code)
// ----------------------------------------------------
window.exportExcel = function() {
    const table = document.getElementById("studentTable");
    
    if(table.rows.length <= 2) { // Header + Loading/Empty row
        alert("No data to export");
        return;
    }

    let csv = "";
    for (let row of table.rows) {
        let rowData = [];
        for (let cell of row.cells) {
            rowData.push(cell.innerText);
        }
        csv += rowData.join(",") + "\n";
    }

    const blob = new Blob([csv], { type: "text/csv" });
    const link = document.createElement("a");
    link.href = URL.createObjectURL(blob);
    link.download = "Students_Report.csv";
    link.click();
}