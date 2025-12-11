// -------------------------
// Dummy Student Database
// -------------------------
const studentDB = {
    "CSE": {
        5: [
            { roll: "21CSE01", name: "Rahul Sharma", score: 82, att: 91, status: "Good" },
            { roll: "21CSE02", name: "Priya Sinha", score: 76, att: 85, status: "Average" },
            { roll: "21CSE03", name: "Aman Verma", score: 64, att: 70, status: "Needs Improvement" }
        ],
        4: [
            { roll: "21CSE11", name: "Suman Das", score: 73, att: 88, status: "Good" }
        ],
        3: [
            { roll: "21CSE21", name: "Rohit Jain", score: 67, att: 78, status: "Average" }
        ]
    },

    "ECE": {
        5: [
            { roll: "21ECE01", name: "Tina Roy", score: 80, att: 90, status: "Good" }
        ],
        4: [
            { roll: "21ECE11", name: "Shiv Kumar", score: 63, att: 72, status: "Average" }
        ],
        3: [
            { roll: "21ECE21", name: "Anjali Singh", score: 59, att: 69, status: "Needs Improvement" }
        ]
    },

    "EE": {
        5: [
            { roll: "21EE01", name: "Harsh Patel", score: 77, att: 84, status: "Good" }
        ],
        4: [
            { roll: "21EE11", name: "Mayank Sharma", score: 62, att: 75, status: "Average" }
        ],
        3: [
            { roll: "21EE21", name: "Ravi Verma", score: 55, att: 68, status: "Needs Improvement" }
        ]
    }
};

// ----------------------------------------------------
// Function to Update Table Based on Dropdown Selection
// ----------------------------------------------------
function loadStudents() {
    let dept = document.getElementById("department").value;
    let semText = document.getElementById("semester").value;

    // Extract numeric semester: "5th Semester" → 5
    let sem = parseInt(semText);

    const tableBody = document.querySelector("#studentTable tbody");
    tableBody.innerHTML = "";

    if (studentDB[dept] && studentDB[dept][sem]) {
        studentDB[dept][sem].forEach(std => {
            tableBody.innerHTML += `
                <tr>
                    <td>${std.roll}</td>
                    <td>${std.name}</td>
                    <td>${dept}</td>
                    <td>${sem}</td>
                    <td>${std.score}</td>
                    <td>${std.att}%</td>
                    <td>${std.status}</td>
                </tr>
            `;
        });
    } else {
        tableBody.innerHTML = `
            <tr>
                <td colspan="7" style="text-align:center; padding:20px;">
                    No Records Found
                </td>
            </tr>
        `;
    }
}

// ----------------------------------------------------
// Export Table to PDF
// ----------------------------------------------------
async function exportPDF() {
    const element = document.getElementById("studentTable");

    const canvas = await html2canvas(element);
    const imgData = canvas.toDataURL("image/png");

    const pdf = new jsPDF("p", "mm", "a4");
    let width = pdf.internal.pageSize.getWidth();
    let height = (canvas.height * width) / canvas.width;

    pdf.addImage(imgData, "PNG", 0, 0, width, height);
    pdf.save("Students_Report.pdf");
}

// ----------------------------------------------------
// Export Table to Excel (CSV)
// ----------------------------------------------------
function exportExcel() {
    const table = document.getElementById("studentTable");
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

// ----------------------------------------------------
// Event Listeners
// ----------------------------------------------------
document.querySelector(".apply-btn").addEventListener("click", loadStudents);
document.querySelector(".pdf-btn").addEventListener("click", exportPDF);
document.querySelector(".excel-btn").addEventListener("click", exportExcel);

// Load default data (CSE 5th Semester)
loadStudents();
