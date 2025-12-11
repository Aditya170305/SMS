// Wait for DOM to load
document.addEventListener('DOMContentLoaded', function() {
  console.log("Academics JavaScript loaded successfully!");
  
  // Fetch and render subjects on page load
  fetchAndRenderSubjects();
});

// Fetch subjects from backend
async function fetchAndRenderSubjects() {
  try {
    const response = await fetch('/academics/subjects');
    if (!response.ok) {
      throw new Error('Failed to fetch subjects: ' + response.status);
    }
    
    const subjects = await response.json();
    console.log('Fetched subjects:', subjects);
    
    renderAcademicsTable(subjects);
    
  } catch (error) {
    console.error('Error fetching subjects:', error);
    alert('Error loading subjects. Please refresh the page.');
  }
}

// Render subjects table
function renderAcademicsTable(subjects) {
  const tableBody = document.querySelector("#academicsTable tbody");
  tableBody.innerHTML = "";
  
  subjects.forEach((s) => {
    const tr = document.createElement("tr");

    // Subject name column
    const subjectCell = document.createElement("td");
    subjectCell.innerHTML = `<div style="font-weight:600">${s.subjectName}</div><div class="subcode">${s.subjectType}</div>`;

    // Subject code column
    const codeCell = document.createElement("td");
    codeCell.innerHTML = `<div class="subcode">${s.subjectCode}</div>`;

    // Assignments button column
    const assignmentsCell = document.createElement("td");
    assignmentsCell.innerHTML = `<div class="cell-wrap"><button class="btn-view btn-assign">View</button></div>`;

    // Syllabus button column
    const syllabusCell = document.createElement("td");
    syllabusCell.innerHTML = `<div class="cell-wrap"><button class="btn-view btn-syllabus">View</button></div>`;

    // Notes button column
    const notesCell = document.createElement("td");
    notesCell.innerHTML = `<div class="cell-wrap"><button class="btn-view btn-notes">View</button></div>`;

    // Attach data attributes
    tr.dataset.code = s.subjectCode;
    tr.dataset.title = s.subjectName;
    tr.dataset.type = s.subjectType;

    tr.appendChild(subjectCell);
    tr.appendChild(codeCell);
    tr.appendChild(assignmentsCell);
    tr.appendChild(syllabusCell);
    tr.appendChild(notesCell);

    tableBody.appendChild(tr);
  });

  // Wire up button event handlers
  wireButtons();
}

// Wire button event handlers
function wireButtons() {
  // Assignments buttons
  document.querySelectorAll(".btn-assign").forEach(btn => {
    btn.onclick = async (e) => {
      const tr = e.target.closest("tr");
      const code = tr.dataset.code;
      const title = tr.dataset.title;
      const type = tr.dataset.type;
      await showAssignmentsModal(code, title, type);
    };
  });

  // Syllabus buttons
  document.querySelectorAll(".btn-syllabus").forEach(btn => {
    btn.onclick = async (e) => {
      const tr = e.target.closest("tr");
      const code = tr.dataset.code;
      const title = tr.dataset.title;
      const type = tr.dataset.type;
      await showSyllabusModal(code, title, type);
    };
  });

  // Notes buttons
  document.querySelectorAll(".btn-notes").forEach(btn => {
    btn.onclick = async (e) => {
      const tr = e.target.closest("tr");
      const code = tr.dataset.code;
      const title = tr.dataset.title;
      const type = tr.dataset.type;
      await showNotesModal(code, title, type);
    };
  });
}

// Modal elements
const modalOverlay = document.getElementById("modalOverlay");
const modalTitle = document.getElementById("modalTitle");
const assignmentsView = document.getElementById("assignmentsView");
const assignmentsTbody = document.getElementById("assignmentsTbody");
const docView = document.getElementById("docView");
const docContent = document.getElementById("docContent");

// Open modal
function openModal() {
  modalOverlay.classList.remove("hidden");
}

// Close modal
function closeModal() {
  modalOverlay.classList.add("hidden");
  assignmentsView.classList.add("hidden");
  docView.classList.add("hidden");
  assignmentsTbody.innerHTML = "";
  docContent.innerHTML = "";
}

// Close button handler
document.getElementById("modalClose")?.addEventListener("click", closeModal);

// Close on background click
modalOverlay.addEventListener("click", (e) => {
  if (e.target === modalOverlay) closeModal();
});

// Show assignments modal
async function showAssignmentsModal(code, title, type) {
  modalTitle.textContent = `${code} — ${title} (${type}) — Assignments`;
  assignmentsView.classList.remove("hidden");
  docView.classList.add("hidden");
  assignmentsTbody.innerHTML = '<tr><td colspan="3" style="text-align:center;">Loading...</td></tr>';
  
  openModal();
  
  try {
    const response = await fetch(`/academics/assignments?subjectCode=${encodeURIComponent(code)}&subjectType=${encodeURIComponent(type)}`);
    
    if (!response.ok) {
      throw new Error('Failed to fetch assignments: ' + response.status);
    }
    
    const assignments = await response.json();
    console.log('Fetched assignments:', assignments);
    
    assignmentsTbody.innerHTML = "";
    
    if (assignments.length === 0) {
      assignmentsTbody.innerHTML = '<tr><td colspan="3" style="text-align:center;">No assignments found</td></tr>';
      return;
    }
    
    // Filter to show only uploaded assignments
    const uploadedAssignments = assignments.filter(a => a.teacherUploaded === true);
    
    if (uploadedAssignments.length === 0) {
      assignmentsTbody.innerHTML = '<tr><td colspan="3" style="text-align:center;">No assignments uploaded by teacher yet</td></tr>';
      return;
    }
    
    uploadedAssignments.forEach((a) => {
      const tr = document.createElement("tr");
      
      // Assignment name
      const nameTd = document.createElement("td");
      nameTd.textContent = a.assignmentName;
      
      // Open button
      const openTd = document.createElement("td");
      const openBtn = document.createElement("button");
      openBtn.className = "btn-open";
      openBtn.textContent = "Open";
      openBtn.addEventListener("click", () => {
        openAssignmentInNewTab(a);
      });
      openTd.appendChild(openBtn);
      
      // Status
      const statusTd = document.createElement("td");
      statusTd.innerHTML = (a.status === "Submitted")
        ? `<span class="badge-present">${a.status}</span>`
        : `<span class="badge-missing">${a.status}</span>`;
      
      tr.appendChild(nameTd);
      tr.appendChild(openTd);
      tr.appendChild(statusTd);
      assignmentsTbody.appendChild(tr);
    });
    
  } catch (error) {
    console.error('Error fetching assignments:', error);
    assignmentsTbody.innerHTML = '<tr><td colspan="3" style="text-align:center;color:red;">Error loading assignments</td></tr>';
  }
}

// Show syllabus modal
async function showSyllabusModal(code, title, type) {
  modalTitle.textContent = `${code} — ${title} (${type}) — Syllabus`;
  assignmentsView.classList.add("hidden");
  docView.classList.remove("hidden");
  docContent.innerHTML = '<p style="text-align:center;">Loading...</p>';
  
  openModal();
  
  try {
    const response = await fetch(`/academics/syllabus?subjectCode=${encodeURIComponent(code)}&subjectType=${encodeURIComponent(type)}`);
    
    if (!response.ok) {
      throw new Error('Failed to fetch syllabus: ' + response.status);
    }
    
    const syllabus = await response.json();
    console.log('Fetched syllabus:', syllabus);
    
    if (syllabus.length === 0) {
      docContent.innerHTML = '<p>No syllabus information available for this subject.</p>';
      return;
    }
    
    let html = `<div style="font-weight:700;margin-bottom:12px;font-size:16px;">${code} — ${type} Syllabus</div><ol style="margin-left:20px;line-height:1.8;">`;
    
    syllabus.forEach((module) => {
      html += `<li><strong>Module ${module.moduleNumber}: ${module.moduleTitle}</strong><br>`;
      html += `<span style="color:#555;">${module.moduleContent}</span></li>`;
    });
    
    html += '</ol><p style="margin-top:16px;color:#666;">Download full PDF from LMS or contact your instructor for detailed syllabus.</p>';
    
    docContent.innerHTML = html;
    
  } catch (error) {
    console.error('Error fetching syllabus:', error);
    docContent.innerHTML = '<p style="color:red;">Error loading syllabus</p>';
  }
}

// Function to open assignment in new tab
function openAssignmentInNewTab(assignment) {
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
    .status-submitted {
      color: #0b6623;
      font-weight: bold;
    }
    .status-not-submitted {
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
    .footer {
      margin-top: 30px;
      padding-top: 20px;
      border-top: 1px solid #ddd;
      color: #666;
      font-size: 14px;
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
        <span class="info-value ${assignment.status === 'Submitted' ? 'status-submitted' : 'status-not-submitted'}">
          ${assignment.status}
        </span>
      </div>
      <div class="info-row">
        <span class="info-label">Due Date:</span>
        <span class="info-value">${assignment.dueDate || 'Not specified'}</span>
      </div>
      ${assignment.submissionDate ? `
      <div class="info-row">
        <span class="info-label">Submission Date:</span>
        <span class="info-value">${assignment.submissionDate}</span>
      </div>
      ` : ''}
    </div>

    <div class="content-section">
      <h2>Assignment Description</h2>
      <div class="assignment-content">
        ${getAssignmentQuestions(assignment)}
      </div>

      <h2>Instructions</h2>
      <div class="instructions">
        <ul>
          <li>Read the assignment requirements carefully</li>
          <li>Complete all sections as specified</li>
          <li>Ensure your code/answers are well-documented</li>
          <li>Submit before the due date</li>
          <li>Follow the submission guidelines provided in class</li>
        </ul>
      </div>

      <h2>Objectives</h2>
      <div class="assignment-content">
        <ul>
          <li>Understand and apply the core concepts</li>
          <li>Demonstrate problem-solving skills</li>
          <li>Write clean, efficient, and well-documented code/solutions</li>
          <li>Test and validate your implementation</li>
        </ul>
      </div>

      <h2>Submission Guidelines</h2>
      <div class="assignment-content">
        <p><strong>What to Submit:</strong></p>
        <ul>
          <li>Complete source code (if applicable)</li>
          <li>Documentation and comments</li>
          <li>Test cases and results</li>
          <li>Assignment report (if required)</li>
        </ul>
        <p><strong>How to Submit:</strong></p>
        <ul>
          <li>Upload to the course LMS portal</li>
          <li>Follow the naming convention: RollNumber_AssignmentName</li>
          <li>Ensure all files are included in the submission</li>
        </ul>
      </div>

      ${assignment.status === 'Not Submitted' ? `
      <div class="instructions" style="background: #f8d7da; border-left-color: #dc3545;">
        <p style="color: #721c24; margin: 0;"><strong>⚠️ This assignment has not been submitted yet!</strong></p>
        <p style="color: #721c24; margin: 5px 0 0 0;">Please complete and submit before the due date to avoid penalties.</p>
      </div>
      ` : `
      <div class="instructions" style="background: #d4edda; border-left-color: #28a745;">
        <p style="color: #155724; margin: 0;"><strong>✓ This assignment has been submitted successfully!</strong></p>
      </div>
      `}
    </div>

    <div class="footer">
      <p>For questions or clarifications, please contact your instructor or lab assistant.</p>
      <p>Assignment opened: ${new Date().toLocaleString()}</p>
    </div>
  </div>
</body>
</html>`;
  
  const newWindow = window.open('', '_blank');
  newWindow.document.write(html);
  newWindow.document.close();
}

// Generate assignment questions based on type
function getAssignmentQuestions(assignment) {
  if (!assignment.questions || assignment.questions.length === 0) {
    return '<p>No questions available for this assignment yet.</p>';
  }
  
  if (assignment.subjectType === 'Theory') {
    // Theory assignments - show questions from database
    let html = '<p style="margin-bottom: 20px;">Answer the following questions. Each question carries equal marks. Provide detailed explanations with examples where necessary.</p>';
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
    
  } else {
    // Lab assignments - show practical implementation question from database
    const question = assignment.questions[0]; // Lab typically has 1 main question
    
    let html = '<p style="margin-bottom: 20px;"><strong>Practical Implementation Task:</strong></p>';
    html += '<div style="background: #f0f7ff; padding: 20px; border-radius: 8px; border-left: 4px solid #0066ff;">';
    html += `<p style="margin-bottom: 15px;"><strong>Objective:</strong> ${question.questionText}</p>`;
    
    html += '<p style="margin-bottom: 10px;"><strong>Task Requirements:</strong></p>';
    html += '<ol style="margin-left: 20px; line-height: 1.8;">';
    html += '<li>Write a complete program implementing the above objective with all necessary functions and operations.</li>';
    html += '<li>Include proper input validation and error handling mechanisms in your implementation.</li>';
    html += '<li>Test your program with at least 5 different test cases, including edge cases and boundary conditions.</li>';
    html += '<li>Add detailed comments in your code explaining the logic and functionality of each major section.</li>';
    html += '<li>Create a test report documenting your test cases, expected outputs, actual outputs, and any observations.</li>';
    html += '</ol>';
    
    html += '<p style="margin-top: 15px;"><strong>Deliverables:</strong></p>';
    html += '<ul style="margin-left: 20px; line-height: 1.8;">';
    html += '<li>Complete source code (.c, .cpp, .java, or .py file)</li>';
    html += '<li>Test report with screenshots of program execution</li>';
    html += '<li>Brief documentation explaining your approach and design decisions</li>';
    html += '</ul>';
    
    if (question.marks) {
      html += `<p style="margin-top: 15px;"><strong>Total Marks:</strong> ${question.marks}</p>`;
    }
    
    html += '</div>';
    return html;
  }
}

// Extract topic from assignment name (kept for backward compatibility if needed)
function getTopicFromAssignmentName(assignmentName) {
  const topic = assignmentName.replace(/^(Assignment|Lab)\s+\d+:\s*/i, '');
  return topic;
}

// Show notes modal
async function showNotesModal(code, title, type) {
  modalTitle.textContent = `${code} — ${title} (${type}) — Notes`;
  assignmentsView.classList.add("hidden");
  docView.classList.remove("hidden");
  docContent.innerHTML = '<p style="text-align:center;">Loading...</p>';
  
  openModal();
  
  try {
    const response = await fetch(`/academics/notes?subjectCode=${encodeURIComponent(code)}&subjectType=${encodeURIComponent(type)}`);
    
    if (!response.ok) {
      throw new Error('Failed to fetch notes: ' + response.status);
    }
    
    const notes = await response.json();
    console.log('Fetched notes:', notes);
    
    if (notes.length === 0) {
      docContent.innerHTML = '<p>No notes available for this subject yet.</p>';
      return;
    }
    
    let html = `<div style="font-weight:700;margin-bottom:12px;font-size:16px;">${code} — ${type} Notes</div><ul style="margin-left:20px;line-height:1.8;">`;
    
    notes.forEach((note) => {
      html += `<li><strong>${note.noteTitle}</strong><br>`;
      if (note.noteContent) {
        html += `<span style="color:#555;">${note.noteContent}</span><br>`;
      }
      if (note.noteUrl) {
        html += `<a href="${note.noteUrl}" target="_blank" style="color:#0066ff;">View Resource</a><br>`;
      }
      if (note.uploadedDate) {
        html += `<small style="color:#999;">Uploaded: ${note.uploadedDate}</small>`;
      }
      html += '</li>';
    });
    
    html += '</ul><p style="margin-top:16px;color:#666;">Access additional notes and resources in the course portal.</p>';
    
    docContent.innerHTML = html;
    
  } catch (error) {
    console.error('Error fetching notes:', error);
    docContent.innerHTML = '<p style="color:red;">Error loading notes</p>';
  }
}