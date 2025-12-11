// Teacher ID (in real app, get from session)
const TEACHER_ID = "TEACHER001";

// Wait for DOM to load
document.addEventListener('DOMContentLoaded', function() {
  console.log("Teacher Courses JavaScript loaded!");
  fetchAndRenderSubjects();
});

// Fetch subjects from backend
async function fetchAndRenderSubjects() {
  try {
    const response = await fetch('/teacher/courses/subjects');
    if (!response.ok) {
      throw new Error('Failed to fetch subjects: ' + response.status);
    }
    
    const subjects = await response.json();
    console.log('Fetched subjects:', subjects);
    
    renderCoursesTable(subjects);
    
  } catch (error) {
    console.error('Error fetching subjects:', error);
    alert('Error loading subjects. Please refresh the page.');
  }
}

// Render courses table
function renderCoursesTable(subjects) {
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
    
    // Assignments upload button
    const assignmentsCell = document.createElement("td");
    assignmentsCell.innerHTML = `<div class="cell-wrap"><button class="btn-view btn-upload-assign">Upload</button></div>`;
    
    // Syllabus upload button
    const syllabusCell = document.createElement("td");
    syllabusCell.innerHTML = `<div class="cell-wrap"><button class="btn-view btn-upload-syll">Upload</button></div>`;
    
    // Notes upload button
    const notesCell = document.createElement("td");
    notesCell.innerHTML = `<div class="cell-wrap"><button class="btn-view btn-upload-notes">Upload</button></div>`;
    
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
  
  wireButtons();
}

// Wire button event handlers
function wireButtons() {
  // Assignments upload buttons
  document.querySelectorAll(".btn-upload-assign").forEach(btn => {
    btn.onclick = async (e) => {
      const tr = e.target.closest("tr");
      const code = tr.dataset.code;
      const title = tr.dataset.title;
      const type = tr.dataset.type;
      await showAssignmentsModal(code, title, type);
    };
  });
  
  // Syllabus upload buttons
  document.querySelectorAll(".btn-upload-syll").forEach(btn => {
    btn.onclick = async (e) => {
      const tr = e.target.closest("tr");
      const code = tr.dataset.code;
      const title = tr.dataset.title;
      const type = tr.dataset.type;
      await showSyllabusModal(code, title, type);
    };
  });
  
  // Notes upload buttons
  document.querySelectorAll(".btn-upload-notes").forEach(btn => {
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
    const response = await fetch(`/teacher/courses/assignments?subjectCode=${encodeURIComponent(code)}&subjectType=${encodeURIComponent(type)}`);
    
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
    
    assignments.forEach((a) => {
      const tr = document.createElement("tr");
      
      // Assignment name
      const nameTd = document.createElement("td");
      nameTd.textContent = a.assignmentName;
      
      // Template button
      const templateTd = document.createElement("td");
      templateTd.style.textAlign = "center";
      const templateBtn = document.createElement("button");
      templateBtn.className = "btn-open";
      templateBtn.textContent = "Template";
      templateBtn.disabled = a.teacherUploaded;
      templateBtn.style.cursor = a.teacherUploaded ? "not-allowed" : "pointer";
      templateBtn.style.opacity = a.teacherUploaded ? "0.6" : "1";
      templateBtn.addEventListener("click", () => {
        if (!templateBtn.disabled) {
          openAssignmentTemplate(a);
        }
      });
      templateTd.appendChild(templateBtn);
      
      // Upload/Uploaded button
      const uploadTd = document.createElement("td");
      uploadTd.style.textAlign = "center";
      const uploadBtn = document.createElement("button");
      uploadBtn.className = "btn-open";
      uploadBtn.textContent = a.teacherUploaded ? "Uploaded" : "Upload";
      uploadBtn.disabled = a.teacherUploaded || !a.questions || a.questions.length === 0;
      uploadBtn.style.backgroundColor = a.teacherUploaded ? "#28a745" : "#0066ff";
      uploadBtn.style.cursor = uploadBtn.disabled ? "not-allowed" : "pointer";
      uploadBtn.style.opacity = uploadBtn.disabled ? "0.6" : "1";
      
      uploadBtn.addEventListener("click", async () => {
        if (!uploadBtn.disabled) {
          await uploadAssignment(a.id, uploadBtn, templateBtn);
        }
      });
      uploadTd.appendChild(uploadBtn);
      
      tr.appendChild(nameTd);
      tr.appendChild(templateTd);
      tr.appendChild(uploadTd);
      assignmentsTbody.appendChild(tr);
    });
    
  } catch (error) {
    console.error('Error fetching assignments:', error);
    assignmentsTbody.innerHTML = '<tr><td colspan="3" style="text-align:center;color:red;">Error loading assignments</td></tr>';
  }
}

// Open assignment template in new tab
function openAssignmentTemplate(assignment) {
  const html = `<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>${assignment.assignmentName} - Template</title>
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
  </style>
</head>
<body>
  <div class="container">
    <h1>${assignment.assignmentName} - Template</h1>
    
    <div class="info-section">
      <p><strong>Subject Code:</strong> ${assignment.subjectCode}</p>
      <p><strong>Type:</strong> ${assignment.subjectType}</p>
      <p><strong>Due Date:</strong> ${assignment.dueDate || 'Not specified'}</p>
    </div>

    <div class="content-section">
      <h2>Assignment Questions</h2>
      <div class="assignment-content">
        ${getAssignmentQuestionsTemplate(assignment)}
      </div>
    </div>
  </div>
</body>
</html>`;
  
  const newWindow = window.open('', '_blank');
  newWindow.document.write(html);
  newWindow.document.close();
}

// Generate assignment questions template
function getAssignmentQuestionsTemplate(assignment) {
  if (!assignment.questions || assignment.questions.length === 0) {
    return '<p>No questions available for this assignment yet.</p>';
  }
  
  if (assignment.subjectType === 'Theory') {
    let html = '<p style="margin-bottom: 20px;"><strong>Theory Assignment Questions:</strong></p>';
    html += '<div style="line-height: 2;">';
    
    assignment.questions.forEach((q) => {
      html += `<p><strong>Q${q.questionNumber}.</strong> ${q.questionText}`;
      if (q.marks) {
        html += ` <span style="color: #666;">[${q.marks} marks]</span>`;
      }
      html += '</p>';
    });
    
    html += '</div>';
    return html;
    
  } else {
    const question = assignment.questions[0];
    
    let html = '<p style="margin-bottom: 20px;"><strong>Lab Assignment Task:</strong></p>';
    html += '<div style="background: #f0f7ff; padding: 20px; border-radius: 8px;">';
    html += `<p style="margin-bottom: 15px;"><strong>Objective:</strong> ${question.questionText}</p>`;
    
    if (question.marks) {
      html += `<p style="margin-top: 15px;"><strong>Total Marks:</strong> ${question.marks}</p>`;
    }
    
    html += '</div>';
    return html;
  }
}

// Upload assignment
async function uploadAssignment(assignmentId, uploadBtn, templateBtn) {
  try {
    uploadBtn.disabled = true;
    uploadBtn.textContent = "Uploading...";
    
    const response = await fetch('/teacher/courses/upload-assignment', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/x-www-form-urlencoded',
      },
      body: `assignmentId=${assignmentId}&teacherId=${TEACHER_ID}`
    });
    
    const result = await response.json();
    
    if (result.success) {
      uploadBtn.textContent = "Uploaded";
      uploadBtn.style.backgroundColor = "#28a745";
      templateBtn.disabled = true;
      alert("Assignment uploaded successfully!");
    } else {
      uploadBtn.disabled = false;
      uploadBtn.textContent = "Upload";
      alert("Failed to upload assignment: " + result.message);
    }
    
  } catch (error) {
    console.error('Error uploading assignment:', error);
    uploadBtn.disabled = false;
    uploadBtn.textContent = "Upload";
    alert("Error uploading assignment. Please try again.");
  }
}

// Show syllabus modal (similar to student view, but with upload functionality)
async function showSyllabusModal(code, title, type) {
  modalTitle.textContent = `${code} — ${title} (${type}) — Syllabus Upload`;
  assignmentsView.classList.add("hidden");
  docView.classList.remove("hidden");
  docContent.innerHTML = '<p style="text-align:center;">Loading...</p>';
  
  openModal();
  
  try {
    // Check if already uploaded
    const checkResponse = await fetch(`/teacher/courses/check-syllabus?subjectCode=${encodeURIComponent(code)}&subjectType=${encodeURIComponent(type)}`);
    const checkResult = await checkResponse.json();
    
    // Fetch syllabus content
    const response = await fetch(`/teacher/courses/syllabus?subjectCode=${encodeURIComponent(code)}&subjectType=${encodeURIComponent(type)}`);
    const syllabus = await response.json();
    
    let html = `<div style="font-weight:700;margin-bottom:16px;font-size:18px;">${code} — ${type} Syllabus Template</div>`;
    
    if (syllabus.length === 0) {
      html += '<p>No syllabus template available for this subject.</p>';
    } else {
      html += '<div style="background:#f9f9f9;padding:20px;border-radius:8px;margin:15px 0;">';
      html += '<ol style="margin-left:20px;line-height:1.8;">';
      
      syllabus.forEach((module) => {
        html += `<li><strong>Module ${module.moduleNumber}: ${module.moduleTitle}</strong><br>`;
        html += `<span style="color:#555;">${module.moduleContent}</span></li>`;
      });
      
      html += '</ol></div>';
    }
    
    // Upload button
    if (checkResult.uploaded) {
      html += '<button class="btn-view" disabled style="background:#28a745;margin-top:16px;">Uploaded</button>';
    } else {
      html += '<button class="btn-view" id="uploadSyllabusBtn" style="margin-top:16px;">Upload Syllabus</button>';
    }
    
    docContent.innerHTML = html;
    
    // Wire upload button
    if (!checkResult.uploaded) {
      document.getElementById("uploadSyllabusBtn").onclick = () => {
        uploadSyllabus(code, type);
      };
    }
    
  } catch (error) {
    console.error('Error fetching syllabus:', error);
    docContent.innerHTML = '<p style="color:red;">Error loading syllabus template</p>';
  }
}

// Upload syllabus
async function uploadSyllabus(subjectCode, subjectType) {
  const fileInput = document.createElement('input');
  fileInput.type = 'file';
  fileInput.accept = '.pdf,.doc,.docx';
  
  fileInput.onchange = async () => {
    const file = fileInput.files[0];
    if (!file) return;
    
    try {
      const response = await fetch('/teacher/courses/upload-syllabus', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/x-www-form-urlencoded',
        },
        body: `subjectCode=${encodeURIComponent(subjectCode)}&subjectType=${encodeURIComponent(subjectType)}&fileName=${encodeURIComponent(file.name)}&teacherId=${TEACHER_ID}`
      });
      
      const result = await response.json();
      
      if (result.success) {
        alert("Syllabus uploaded successfully!");
        closeModal();
      } else {
        alert("Failed to upload syllabus: " + result.message);
      }
      
    } catch (error) {
      console.error('Error uploading syllabus:', error);
      alert("Error uploading syllabus. Please try again.");
    }
  };
  
  fileInput.click();
}

// Show notes modal (similar to syllabus)
async function showNotesModal(code, title, type) {
  modalTitle.textContent = `${code} — ${title} (${type}) — Notes Upload`;
  assignmentsView.classList.add("hidden");
  docView.classList.remove("hidden");
  docContent.innerHTML = '<p style="text-align:center;">Loading...</p>';
  
  openModal();
  
  try {
    // Check if already uploaded
    const checkResponse = await fetch(`/teacher/courses/check-notes?subjectCode=${encodeURIComponent(code)}&subjectType=${encodeURIComponent(type)}`);
    const checkResult = await checkResponse.json();
    
    // Fetch notes content
    const response = await fetch(`/teacher/courses/notes?subjectCode=${encodeURIComponent(code)}&subjectType=${encodeURIComponent(type)}`);
    const notes = await response.json();
    
    let html = `<div style="font-weight:700;margin-bottom:16px;font-size:18px;">${code} — ${type} Notes Template</div>`;
    
    if (notes.length === 0) {
      html += '<p>No notes template available for this subject.</p>';
    } else {
      html += '<div style="background:#f9f9f9;padding:20px;border-radius:8px;margin:15px 0;">';
      html += '<ul style="margin-left:20px;line-height:1.8;">';
      
      notes.forEach((note) => {
        html += `<li><strong>${note.noteTitle}</strong><br>`;
        if (note.noteContent) {
          html += `<span style="color:#555;">${note.noteContent}</span>`;
        }
        html += '</li>';
      });
      
      html += '</ul></div>';
    }
    
    // Upload button
    if (checkResult.uploaded) {
      html += '<button class="btn-view" disabled style="background:#28a745;margin-top:16px;">Uploaded</button>';
    } else {
      html += '<button class="btn-view" id="uploadNotesBtn" style="margin-top:16px;">Upload Notes</button>';
    }
    
    docContent.innerHTML = html;
    
    // Wire upload button
    if (!checkResult.uploaded) {
      document.getElementById("uploadNotesBtn").onclick = () => {
        uploadNotes(code, type);
      };
    }
    
  } catch (error) {
    console.error('Error fetching notes:', error);
    docContent.innerHTML = '<p style="color:red;">Error loading notes template</p>';
  }
}

// Upload notes
async function uploadNotes(subjectCode, subjectType) {
  const fileInput = document.createElement('input');
  fileInput.type = 'file';
  fileInput.accept = '.pdf,.doc,.docx,.ppt,.pptx';
  
  fileInput.onchange = async () => {
    const file = fileInput.files[0];
    if (!file) return;
    
    try {
      const response = await fetch('/teacher/courses/upload-notes', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/x-www-form-urlencoded',
        },
        body: `subjectCode=${encodeURIComponent(subjectCode)}&subjectType=${encodeURIComponent(subjectType)}&fileName=${encodeURIComponent(file.name)}&teacherId=${TEACHER_ID}`
      });
      
      const result = await response.json();
      
      if (result.success) {
        alert("Notes uploaded successfully!");
        closeModal();
      } else {
        alert("Failed to upload notes: " + result.message);
      }
      
    } catch (error) {
      console.error('Error uploading notes:', error);
      alert("Error uploading notes. Please try again.");
    }
  };
  
  fileInput.click();
}