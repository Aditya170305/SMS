document.querySelectorAll(".dashboard ul li").forEach((item) => {
    item.addEventListener("click", () => {
        let text = item.innerText.trim();

        switch (text) {
            case "Dashboard":
                 window.location.href = "adminDashboard";
                 break;
            case "Courses":
                window.location.href = "adminCourses";
                break;
            case "Requests":
                window.location.href = "requests";
                break;
            case "Users":
                window.location.href = "users";
                break;
            case "Profile":
                 window.location.href = "adminProfile";
                 break;
            case "Logout":
                window.location.href = "logoutAdmin";
                break;
        }
    });
});
// document.getElementById(".second").addEventListener("onclick" , () => {
//     location.href = "StudentDashboard-ClassSchedule.html"
// })