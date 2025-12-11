document.querySelectorAll(".dashboard ul li").forEach((item) => {
    item.addEventListener("click", () => {
        let text = item.innerText.trim();
        switch (text) {
            case "Dashboard":
                window.location.href = "dashboard";
                break;
            case "Class Schedule":
                window.location.href = "class-schedule";
               break;
            case "Attendance":
                window.location.href = "attendance";
                break;
            case "Academics":
                window.location.href = "academics";
                break;
            case "Fees":
                window.location.href = "fees";
                break;
			case "Profile":
				window.location.href = "profileStudent";
				break;
            case "Logout":
                window.location.href = "logout";
                break;
       }
    });
});
// document.getElementById(".second").addEventListener("onclick" , () => {
//     location.href = "StudentDashboard-ClassSchedule.html"
// })