document.addEventListener("DOMContentLoaded", () => {
    // Load Pending requests by default
    fetchRequests('Pending');
});

// 1. FILTER REQUESTS (Tab Click)
window.filterRequests = (status, btnElement) => {
    // Update active tab UI
    document.querySelectorAll('.tab-btn').forEach(btn => btn.classList.remove('active'));
    if(btnElement) btnElement.classList.add('active');

    fetchRequests(status);
};

// 2. FETCH DATA FROM SERVER
function fetchRequests(status) {
    const tableBody = document.getElementById("tableBody");
    tableBody.innerHTML = `<tr><td colspan="6" style="text-align:center;">Loading...</td></tr>`;

    fetch(`/admin/requests/list?status=${status}`)
        .then(res => res.json())
        .then(data => {
            if (data.length === 0) {
                tableBody.innerHTML = `<tr><td colspan="6" style="text-align:center;">No ${status} requests found.</td></tr>`;
                return;
            }

            tableBody.innerHTML = data.map(req => `
                <tr>
                    <td>
                        <strong>${req.studentName}</strong><br>
                        <span style="font-size:12px; color:#888;">${req.enrollmentNo}</span>
                    </td>
                    <td>${req.requestType}</td>
                    <td>
                        <strong>${req.subject}</strong><br>
                        <span style="font-size:13px; color:#666;">${req.description}</span>
                    </td>
                    <td>${req.requestDate}</td>
                    <td><span class="badge badge-${req.status}">${req.status}</span></td>
                    <td>
                        ${req.status === 'Pending' ? `
                            <button class="btn-action btn-approve" onclick="updateStatus(${req.requestId}, 'Approved')">Approve</button>
                            <button class="btn-action btn-reject" onclick="updateStatus(${req.requestId}, 'Rejected')">Reject</button>
                        ` : '<span style="color:#aaa;">-</span>'}
                    </td>
                </tr>
            `).join("");
        })
        .catch(err => console.error(err));
}

// 3. HANDLE APPROVE / REJECT
window.updateStatus = (id, newStatus) => {
    if(!confirm(`Are you sure you want to mark this request as ${newStatus}?`)) return;

    fetch(`/admin/requests/update?id=${id}&status=${newStatus}`, { method: 'POST' })
        .then(res => res.text())
        .then(result => {
            if(result === 'success') {
                // Refresh the current list
                const activeStatus = document.querySelector('.tab-btn.active').innerText;
                // If we are in 'Pending' tab, removing the item is better UX than reloading
                fetchRequests(activeStatus === 'All History' ? 'All' : activeStatus);
            } else {
                alert("Failed to update status.");
            }
        });
};