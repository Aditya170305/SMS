// Edit Profile Button
const editBtn = document.getElementById('editBtn');
const cancelBtn = document.getElementById('cancelBtn');
const formActions = document.getElementById('formActions');
const profileForm = document.getElementById('profileForm');

let isEditing = false;

// Enable/Disable form fields
function toggleEditMode(enable) {
    const inputs = profileForm.querySelectorAll('input:not([name="employeeCode"])');
    
    inputs.forEach(input => {
        if (enable) {
            input.removeAttribute('readonly');
        } else {
            input.setAttribute('readonly', true);
        }
    });
    
    isEditing = enable;
    
    if (enable) {
        editBtn.textContent = 'Editing...';
        editBtn.style.background = '#6c757d';
        editBtn.disabled = true;
        formActions.style.display = 'flex';
    } else {
        editBtn.textContent = 'Edit Profile';
        editBtn.style.background = '#0066ff';
        editBtn.disabled = false;
        formActions.style.display = 'none';
    }
}

// Edit Button Click
editBtn.addEventListener('click', () => {
    toggleEditMode(true);
});

// Cancel Button Click
cancelBtn.addEventListener('click', () => {
    if (confirm('Are you sure you want to cancel? All unsaved changes will be lost.')) {
        location.reload();
    }
});

// Form Submit
profileForm.addEventListener('submit', async (e) => {
    e.preventDefault();
    
    if (!confirm('Are you sure you want to save these changes?')) {
        return;
    }
    
    // Get form data
    const formData = new FormData(profileForm);
    
    // Convert to URL encoded format
    const params = new URLSearchParams();
    for (const [key, value] of formData.entries()) {
        params.append(key, value);
    }
    
    try {
        // Show loading
        const saveBtn = document.querySelector('.btn-save');
        const originalText = saveBtn.textContent;
        saveBtn.textContent = 'Saving...';
        saveBtn.disabled = true;
        
        const response = await fetch('/teacher/profile/update', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded',
            },
            body: params.toString()
        });
        
        const result = await response.json();
        
        if (result.success) {
            // Show success message
            showMessage('Profile updated successfully!', 'success');
            
            // Disable edit mode
            toggleEditMode(false);
            
            // Reload page after 1 second
            setTimeout(() => {
                location.reload();
            }, 1000);
        } else {
            showMessage('Failed to update profile: ' + result.message, 'error');
            saveBtn.textContent = originalText;
            saveBtn.disabled = false;
        }
        
    } catch (error) {
        console.error('Error updating profile:', error);
        showMessage('An error occurred while updating profile', 'error');
        
        const saveBtn = document.querySelector('.btn-save');
        saveBtn.textContent = 'Save Changes';
        saveBtn.disabled = false;
    }
});

// Show message function
function showMessage(text, type) {
    // Remove any existing messages
    const existingMsg = document.querySelector('.message');
    if (existingMsg) {
        existingMsg.remove();
    }
    
    // Create new message
    const message = document.createElement('div');
    message.className = `message ${type}`;
    message.textContent = text;
    
    // Insert at top of main content
    const mainContent = document.querySelector('.main-content');
    mainContent.insertBefore(message, mainContent.firstChild);
    
    // Auto remove after 5 seconds
    setTimeout(() => {
        message.remove();
    }, 5000);
}

// Input validation
profileForm.addEventListener('input', (e) => {
    const input = e.target;
    
    // Email validation
    if (input.name === 'email') {
        const emailPattern = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
        if (!emailPattern.test(input.value)) {
            input.style.borderColor = '#dc3545';
        } else {
            input.style.borderColor = '#0066ff';
        }
    }
    
    // Mobile validation
    if (input.name === 'mobile') {
        const mobilePattern = /^[0-9]{10}$/;
        if (!mobilePattern.test(input.value)) {
            input.style.borderColor = '#dc3545';
        } else {
            input.style.borderColor = '#0066ff';
        }
    }
    
    // Pincode validation
    if (input.name === 'pincode') {
        const pincodePattern = /^[0-9]{6}$/;
        if (!pincodePattern.test(input.value)) {
            input.style.borderColor = '#dc3545';
        } else {
            input.style.borderColor = '#0066ff';
        }
    }
});

// Prevent form submission on Enter key (except in textarea)
profileForm.addEventListener('keydown', (e) => {
    if (e.key === 'Enter' && e.target.tagName !== 'TEXTAREA') {
        e.preventDefault();
    }
});

// Warn user before leaving if editing
window.addEventListener('beforeunload', (e) => {
    if (isEditing) {
        e.preventDefault();
        e.returnValue = '';
    }
});