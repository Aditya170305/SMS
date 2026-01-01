document.addEventListener("DOMContentLoaded", () => {
    const editBtn = document.getElementById('editBtn');
    const cancelBtn = document.getElementById('cancelBtn');
    const formActions = document.getElementById('formActions');
    const profileForm = document.getElementById('profileForm');

    // Toggle Edit Mode
    editBtn.addEventListener('click', () => {
        const inputs = profileForm.querySelectorAll('input:not([name="employeeCode"])'); // Emp Code stays read-only
        inputs.forEach(input => input.removeAttribute('readonly'));
        
        editBtn.style.display = 'none';
        formActions.style.display = 'flex';
    });

    // Cancel Edit
    cancelBtn.addEventListener('click', () => {
        location.reload(); // Simplest way to reset data
    });

    // Handle Save
    profileForm.addEventListener('submit', async (e) => {
        e.preventDefault();
        
        if(!confirm("Save changes to profile?")) return;

        const formData = new FormData(profileForm);
        const params = new URLSearchParams();
        for (const [key, value] of formData.entries()) {
            params.append(key, value);
        }

        try {
            const response = await fetch('/admin/profile/update', {
                method: 'POST',
                headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
                body: params
            });
            const result = await response.json();
            
            if (result.success) {
                alert("Profile Updated Successfully!");
                location.reload();
            } else {
                alert("Update failed.");
            }
        } catch (error) {
            console.error("Error:", error);
            alert("An error occurred.");
        }
    });
});