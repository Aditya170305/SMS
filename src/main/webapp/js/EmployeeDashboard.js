// Wait for DOM to load
document.addEventListener('DOMContentLoaded', function() {
    console.log("Teacher Dashboard JavaScript loaded!");
    
    // Fetch and render assignments
    fetchTeacherAssignments();
    
    // Fetch dashboard stats
    fetchDashboardStats();
    
    // Initialize chart
    initializeGradeChart();
});

// Fetch teacher's assignments from backend
async function fetchTeacherAssignments() {
    try {
        const response = await fetch('/teacher/assignments');
        
        if (!response.ok) {
            throw new Error('Failed to fetch assignments: ' + response.status);
        }
        
        const assignments = await response.json();
        console.log('Fetched assignments:', assignments);
        
        renderAssignmentsTable(assignments);
        
    } catch (error) {
        console.error('Error fetching assignments:', error);
        const tableBody = document.getElementById("assignmentTableBody");
        tableBody.innerHTML = '<tr><td colspan="5" style="text-align:center;color:red;">Error loading assignments</td></tr>';
    }
}

// Render assignments table
function renderAssignmentsTable(assignments) {
    const tableBody = document.getElementById("assignmentTableBody");
    tableBody.innerHTML = "";
    
    if (assignments.length === 0) {
        tableBody.innerHTML = '<tr><td colspan="5" style="text-align:center;">No assignments found</td></tr>';
        return;
    }
    
    assignments.forEach(a => {
        const row = document.createElement("tr");
        
        // ✅ FIXED: Removed backslashes before $
        row.innerHTML = `
            <td>${a.assignmentName}</td>
            <td>${a.subjectName || a.subjectCode}</td>
            <td>${a.submissions}</td>
            <td>${a.status}</td>
            <td><a href="#" class="view-assignment" data-id="${a.id}" style="color:#0066FF;text-decoration:none;">View</a></td>
        `;
        
        tableBody.appendChild(row);
    });
    
    // Attach event listeners to View links
    document.querySelectorAll('.view-assignment').forEach(link => {
        link.addEventListener('click', async function(e) {
            e.preventDefault();
            const assignmentId = this.getAttribute('data-id');
            await viewAssignmentDetails(assignmentId);
        });
    });
}

// View assignment details in new tab
async function viewAssignmentDetails(assignmentId) {
    try {
        console.log('Fetching assignment details for ID:', assignmentId);
        
        const response = await fetch('/teacher/assignment/details?id=' + assignmentId);
        
        if (!response.ok) {
            throw new Error('Failed to fetch assignment details');
        }
        
        const assignment = await response.json();
        console.log('Assignment details:', assignment);
        
        openAssignmentInNewTab(assignment);
        
    } catch (error) {
        console.error('Error fetching assignment details:', error);
        alert('Error loading assignment details');
    }
}

// Open assignment in new tab (same as student view)
function openAssignmentInNewTab(assignment) {
    // ✅ FIXED: Removed backslashes before $ in HTML string
    const html = `<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>${assignment.assignmentName}</title>
  <style>
    body {
      font-family: Arial, sans-serif;
      max-width: 900px;
      margin: 40px auto;
      padding: 20px;
      background: #f5f5f5;
      color: #333;
    }
    .container {
      background: white;
      padding: 30px;
      border-radius: 10px;
      box-shadow: 0 2px 10px rgba(0,0,0,0.1);
    }
    h1 {
      color: #0066ff;
      border-bottom: 3px solid #0066ff;
      padding-bottom: 10px;
      margin-bottom: 20px;
    }
    .info-section {
      background: #f8f9fa;
      padding: 15px;
      border-radius: 8px;
      margin: 20px 0;
      border-left: 4px solid #0066ff;
    }
    .info-row {
      display: flex;
      margin: 10px 0;
    }
    .info-label {
      font-weight: bold;
      min-width: 150px;
      color: #555;
    }
    .info-value {
      color: #333;
    }
    .status-active {
      color: #0b6623;
      font-weight: bold;
    }
    .status-closed {
      color: #b30000;
      font-weight: bold;
    }
    .content-section {
      margin-top: 30px;
      line-height: 1.8;
    }
    .content-section h2 {
      color: #333;
      margin-top: 25px;
      border-bottom: 2px solid #eee;
      padding-bottom: 8px;
    }
    .assignment-content {
      background: #fafbfc;
      padding: 20px;
      border-radius: 8px;
      border: 1px solid #e1e4e8;
      margin: 15px 0;
    }
    .instructions {
      background: #fff3cd;
      padding: 15px;
      border-radius: 8px;
      border-left: 4px solid #ffc107;
      margin: 15px 0;
    }
    @media print {
      body { background: white; }
      .container { box-shadow: none; }
    }
  </style>
</head>
<body>
  <div class="container">
    <h1>${assignment.assignmentName}</h1>
    
    <div class="info-section">
      <div class="info-row">
        <span class="info-label">Subject Code:</span>
        <span class="info-value">${assignment.subjectCode}</span>
      </div>
      <div class="info-row">
        <span class="info-label">Type:</span>
        <span class="info-value">${assignment.subjectType}</span>
      </div>
      <div class="info-row">
        <span class="info-label">Status:</span>
        <span class="info-value ${assignment.status === 'Submitted' ? 'status-active' : 'status-closed'}">
          ${assignment.status}
        </span>
      </div>
      <div class="info-row">
        <span class="info-label">Due Date:</span>
        <span class="info-value">${assignment.dueDate || 'Not specified'}</span>
      </div>
    </div>

    <div class="content-section">
      <h2>Assignment Questions</h2>
      <div class="assignment-content">
        ${getAssignmentQuestions(assignment)}
      </div>

      <h2>Instructions for Students</h2>
      <div class="instructions">
        <ul>
          <li>Read the assignment requirements carefully</li>
          <li>Complete all sections as specified</li>
          <li>Ensure your answers are well-documented and clear</li>
          <li>Submit before the due date</li>
          <li>Follow the submission guidelines provided in class</li>
        </ul>
      </div>
    </div>
  </div>
</body>
</html>`;
  
    const newWindow = window.open('', '_blank');
    newWindow.document.write(html);
    newWindow.document.close();
}

// Generate assignment questions HTML
function getAssignmentQuestions(assignment) {
    if (!assignment.questions || assignment.questions.length === 0) {
        return '<p>No questions available for this assignment yet.</p>';
    }
    
    let html = '<p style="margin-bottom: 20px;">Answer the following questions. Provide detailed explanations with examples where necessary.</p>';
    html += '<div style="line-height: 2;">';
    
    assignment.questions.forEach((q) => {
        html += `<p><strong>Q${q.questionNumber}.</strong> ${q.questionText}`;
        if (q.marks) {
            html += ` <span style="color: #666; font-size: 14px;">[${q.marks} marks]</span>`;
        }
        html += '</p>';
    });
    
    html += '</div>';
    return html;
}

// Fetch dashboard statistics
async function fetchDashboardStats() {
    try {
        const response = await fetch('/teacher/stats');
        
        if (!response.ok) {
            throw new Error('Failed to fetch stats');
        }
        
        const stats = await response.json();
        console.log('Dashboard stats:', stats);
        
        // Update dashboard cards
        document.getElementById('totalCourses').textContent = stats.totalCourses || 5;
        document.getElementById('totalStudents').textContent = stats.totalStudents || 40;
        document.getElementById('averageGrade').textContent = (stats.averageGrade || 79) + '%';
        
    } catch (error) {
        console.error('Error fetching stats:', error);
    }
}

// Initialize grade chart
function initializeGradeChart() {
    const ctx = document.getElementById("gradeChart").getContext("2d");

    new Chart(ctx, {
        type: "bar",
        data: {
            labels: ["Jan", "Feb", "Mar", "Apr"],
            datasets: [{
                label: "Average Grade (%)",
                data: [45, 50, 60, 65],
                backgroundColor: "#0066FF",
                borderRadius: 5
            }]
        },
        options: {
            responsive: true,
            scales: {
                y: {
                    beginAtZero: true,
                    max: 100,
                    ticks: {
                        stepSize: 20
                    }
                }
            },
            plugins: {
                legend: { display: false }
            }
        }
    });
}