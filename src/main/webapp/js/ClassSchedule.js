document.addEventListener('DOMContentLoaded', function() {
    const dayButtons = document.querySelectorAll('.day');
    const scheduleContainer = document.getElementById('scheduleContainer');
    
    let fullScheduleData = []; // Store the fetched data

    // 1. Fetch data from Server
    fetch('/student/schedule/data')
        .then(response => response.json())
        .then(data => {
            fullScheduleData = data;
            console.log("Schedule loaded:", data.length, "items");
            
            // Render Monday by default
            renderSchedule('Mon');
        })
        .catch(error => {
            console.error('Error fetching schedule:', error);
            scheduleContainer.innerHTML = '<p style="text-align:center; padding:20px; color:red;">Failed to load schedule.</p>';
        });

    // 2. Function to render schedule for a specific day
    function renderSchedule(day) {
        scheduleContainer.innerHTML = '';
        
        // Filter data for the selected day
        const daySchedule = fullScheduleData.filter(item => item.day === day);
        
        if(daySchedule.length === 0) {
            scheduleContainer.innerHTML = '<p style="text-align:center; padding:20px; color:#666;">No classes scheduled for ' + day + '.</p>';
            return;
        }

        daySchedule.forEach(item => {
            const card = document.createElement('div');
            card.classList.add('schedule-card');

            // Add class for styling (Lab vs Theory)
            if (item.lab) {
                card.classList.add('lab');
            } else {
                card.classList.add('theory');
            }

            card.innerHTML = `
                <div class="period-header">${item.period}</div>
                <p class="time">${item.time}</p>
                <p class="label">Subject Name</p>
                <p class="subject">${item.subject}</p>
                <p class="label">Faculty Name</p>
                <p class="faculty">${item.faculty}</p>
            `;
            scheduleContainer.appendChild(card);
        });
    }

    // 3. Handle Day Tabs
    dayButtons.forEach(button => {
        button.addEventListener('click', () => {
            // Remove active class from all
            dayButtons.forEach(btn => btn.classList.remove('active'));
            // Add to clicked
            button.classList.add('active');
            
            // Get day code (Mon, Tue...)
            const selectedDay = button.getAttribute('data-day');
            renderSchedule(selectedDay);
        });
    });
});