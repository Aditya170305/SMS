document.querySelectorAll(".dashboard ul li").forEach((item) => {
    item.addEventListener("click", () => {
        let text = item.innerText.trim();

        switch (text) {
            case "Dashboard":
                 window.location.href = "dashboardEmployee";
                 break;
            case "Courses":
                window.location.href = "courses";
                break;
            case "Attendance":
                window.location.href = "attendance1";
                break;
            case "Students":
                window.location.href = "students";
                break;
             case "Profile":
                 window.location.href = "profile";
                 break;
            case "Logout":
                window.location.href = "logoutEmployee";
                break;
        }
    });
});
// document.getElementById(".second").addEventListener("onclick" , () => {
//     location.href = "StudentDashboard-ClassSchedule.html"
// })