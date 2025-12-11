const dayButtons = document.querySelectorAll('.day');
const scheduleContainer = document.getElementById('scheduleContainer');

// === Final Balanced Timetable (6 lectures per day) ===
const schedules = {
    Mon: [
        { period: 'L1', time: '9:00 AM - 9:55 AM', subject: 'BT-301 – Data Structure (Theory)', faculty: 'Prof. Rakesh Yadav' },
        { period: 'L2', time: '10:00 AM - 10:55 AM', subject: 'BT-302 – Discrete Structure (Theory)', faculty: 'Prof. Neha Gupta' },
        { period: 'L3', time: '11:00 AM - 11:55 AM', subject: 'BT-303 – Theory of Computation (Theory)', faculty: 'Prof. Deepak Jain' },
        { period: 'L4', time: '12:00 PM - 12:55 PM', subject: 'BT-304 – Digital Systems (Theory)', faculty: 'Prof. Rakesh Sharma' },
        { period: 'L5', time: '1:45 PM - 2:40 PM', subject: 'BT-305 – Object Oriented Programming (Theory)', faculty: 'Prof. Rajiv Mehta' },
        { period: 'L6', time: '2:45 PM - 3:40 PM', subject: 'BT-301 – Data Structure (Lab)', faculty: 'Prof. Rakesh Yadav' }
    ],
    Tue: [
        { period: 'L1', time: '9:00 AM - 9:55 AM', subject: 'BT-304 – Digital Systems (Theory)', faculty: 'Prof. Rakesh Sharma' },
        { period: 'L2', time: '10:00 AM - 10:55 AM', subject: 'BT-305 – Object Oriented Programming (Theory)', faculty: 'Prof. Rajiv Mehta' },
        { period: 'L3', time: '11:00 AM - 11:55 AM', subject: 'BT-302 – Discrete Structure (Theory)', faculty: 'Prof. Neha Gupta' },
        { period: 'L4', time: '12:00 PM - 12:55 PM', subject: 'BT-303 – Theory of Computation (Theory)', faculty: 'Prof. Deepak Jain' },
        { period: 'L5', time: '1:45 PM - 2:40 PM', subject: 'BT-301 – Data Structure (Theory)', faculty: 'Prof. Rakesh Yadav' },
        { period: 'L6', time: '2:45 PM - 3:40 PM', subject: 'BT-304 – Digital Systems (Lab)', faculty: 'Prof. Rakesh Sharma' }
    ],
    Wed: [
        { period: 'L1', time: '9:00 AM - 9:55 AM', subject: 'BT-302 – Discrete Structure (Theory)', faculty: 'Prof. Neha Gupta' },
        { period: 'L2', time: '10:00 AM - 10:55 AM', subject: 'BT-303 – Theory of Computation (Theory)', faculty: 'Prof. Deepak Jain' },
        { period: 'L3', time: '11:00 AM - 11:55 AM', subject: 'BT-304 – Digital Systems (Theory)', faculty: 'Prof. Rakesh Sharma' },
        { period: 'L4', time: '12:00 PM - 12:55 PM', subject: 'BT-305 – Object Oriented Programming (Theory)', faculty: 'Prof. Rajiv Mehta' },
        { period: 'L5', time: '1:45 PM - 2:40 PM', subject: 'BT-301 – Data Structure (Theory)', faculty: 'Prof. Rakesh Yadav' },
        { period: 'L6', time: '2:45 PM - 3:40 PM', subject: 'BT-305 – Object Oriented Programming (Lab)', faculty: 'Prof. Rajiv Mehta' }
    ],
    Thu: [
        { period: 'L1', time: '9:00 AM - 9:55 AM', subject: 'BT-303 – Theory of Computation (Theory)', faculty: 'Prof. Deepak Jain' },
        { period: 'L2', time: '10:00 AM - 10:55 AM', subject: 'BT-302 – Discrete Structure (Theory)', faculty: 'Prof. Neha Gupta' },
        { period: 'L3', time: '11:00 AM - 11:55 AM', subject: 'BT-301 – Data Structure (Theory)', faculty: 'Prof. Rakesh Yadav' },
        { period: 'L4', time: '12:00 PM - 12:55 PM', subject: 'BT-305 – Object Oriented Programming (Theory)', faculty: 'Prof. Rajiv Mehta' },
        { period: 'L5', time: '1:45 PM - 2:40 PM', subject: 'BT-304 – Digital Systems (Theory)', faculty: 'Prof. Rakesh Sharma' },
        { period: 'L6', time: '2:45 PM - 3:40 PM', subject: 'BT-303 – Theory of Computation (Lab)', faculty: 'Prof. Deepak Jain' }
    ],
    Fri: [
        { period: 'L1', time: '9:00 AM - 9:55 AM', subject: 'BT-305 – Object Oriented Programming (Theory)', faculty: 'Prof. Rajiv Mehta' },
        { period: 'L2', time: '10:00 AM - 10:55 AM', subject: 'BT-301 – Data Structure (Theory)', faculty: 'Prof. Rakesh Yadav' },
        { period: 'L3', time: '11:00 AM - 11:55 AM', subject: 'BT-302 – Discrete Structure (Theory)', faculty: 'Prof. Neha Gupta' },
        { period: 'L4', time: '12:00 PM - 12:55 PM', subject: 'BT-304 – Digital Systems (Theory)', faculty: 'Prof. Rakesh Sharma' },
        { period: 'L5', time: '1:45 PM - 2:40 PM', subject: 'BT-303 – Theory of Computation (Theory)', faculty: 'Prof. Deepak Jain' },
        { period: 'L6', time: '2:45 PM - 3:40 PM', subject: 'BT-301 – Data Structure (Lab)', faculty: 'Prof. Rakesh Yadav' }
    ],
    Sat: [
        { period: 'L1', time: '9:00 AM - 9:55 AM', subject: 'BT-301 – Data Structure (Theory)', faculty: 'Prof. Rakesh Yadav' },
        { period: 'L2', time: '10:00 AM - 10:55 AM', subject: 'BT-304 – Digital Systems (Theory)', faculty: 'Prof. Rakesh Sharma' },
        { period: 'L3', time: '11:00 AM - 11:55 AM', subject: 'BT-302 – Discrete Structure (Theory)', faculty: 'Prof. Neha Gupta' },
        { period: 'L4', time: '12:00 PM - 12:55 PM', subject: 'BT-303 – Theory of Computation (Theory)', faculty: 'Prof. Deepak Jain' },
        { period: 'L5', time: '1:45 PM - 2:40 PM', subject: 'BT-305 – Object Oriented Programming (Theory)', faculty: 'Prof. Rajiv Mehta' },
        { period: 'L6', time: '2:45 PM - 3:40 PM', subject: 'BT-304 – Digital Systems (Lab)', faculty: 'Prof. Rakesh Sharma' }
    ]
};

// === Function to render schedule ===
function renderSchedule(day) {
    scheduleContainer.innerHTML = '';
    schedules[day].forEach(item => {
        const card = document.createElement('div');
        card.classList.add('schedule-card');

        if (item.subject.includes('(Lab)')) {
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

// Default day = Monday
renderSchedule('Mon');

// Handle day tab switching
dayButtons.forEach(button => {
    button.addEventListener('click', () => {
        dayButtons.forEach(btn => btn.classList.remove('active'));
        button.classList.add('active');
        const selectedDay = button.textContent.trim();
        renderSchedule(selectedDay);
    });
});
