// Static data with realistic, non-linear user distribution
const degreeData = {
  "B.Tech": {
    branches: [
      { name: "CSE", students: 120 },
      { name: "Mechanical", students: 80 },
      { name: "Electrical", students: 70 },
      { name: "Civil", students: 60 },
      { name: "IT", students: 90 },
    ],
    // Random realistic bar heights (not increasing)
    graph: [85, 70, 88, 92],
    line: [100, 140, 160, 180],
  },
  "M.Tech": {
    branches: [
      { name: "Computer Science", students: 40 },
      { name: "Thermal Engineering", students: 30 },
      { name: "Structural Engineering", students: 25 },
    ],
    graph: [45, 38, 50, 47],
    line: [40, 55, 60, 75],
  },
  "Pharmacy": {
    branches: [
      { name: "Pharmaceutics", students: 55 },
      { name: "Pharmacology", students: 50 },
      { name: "Pharmaceutical Chemistry", students: 45 },
    ],
    graph: [70, 65, 72, 68],
    line: [60, 70, 80, 85],
  },
  "MBA": {
    branches: [
      { name: "Marketing", students: 70 },
      { name: "Finance", students: 65 },
      { name: "HR", students: 60 },
    ],
    graph: [88, 75, 84, 90],
    line: [90, 100, 120, 130],
  },
  "BBA": {
    branches: [
      { name: "Business Administration", students: 80 },
      { name: "International Business", students: 40 },
    ],
    graph: [78, 64, 82, 76],
    line: [70, 80, 95, 100],
  },
};

// DOM elements
const degreeSelect = document.getElementById("degreeSelect");
const tableBody = document.querySelector("#courseTable tbody");
const selectedCourse = document.getElementById("selectedCourse");
const totalUsers = document.getElementById("totalUsers");

// Chart.js setup for bar chart (Users per semester)
const ctxBar = document.getElementById("userChart").getContext("2d");
const ctxLine = document.getElementById("enrollmentChart").getContext("2d");

let barChart = new Chart(ctxBar, {
  type: "bar",
  data: {
    labels: ["1st Sem", "2nd Sem", "3rd Sem", "4th Sem"],
    datasets: [{
      data: [0, 0, 0, 0],
      backgroundColor: "#0066FF",
      borderRadius: 4,
    }],
  },
  options: {
    responsive: true,
    plugins: { legend: { display: false } },
    scales: {
      y: {
        beginAtZero: true,
        title: { display: true, text: "No. of Users" },
        ticks: { stepSize: 20 },
      },
      x: { grid: { display: false } },
    },
  },
});

// Chart.js setup for line chart
let lineChart = new Chart(ctxLine, {
  type: "line",
  data: {
    labels: ["Q1", "Q2", "Q3", "Q4"],
    datasets: [{
      label: "Total Enrollments",
      data: [0, 0, 0, 0],
      borderColor: "#0066FF",
      backgroundColor: "rgba(0, 102, 255, 0.2)",
      borderWidth: 2,
      tension: 0.3,
      fill: true,
      pointRadius: 5,
    }],
  },
  options: {
    responsive: true,
    plugins: { legend: { display: false } },
    scales: { y: { beginAtZero: true }, x: { grid: { display: false } } },
  },
});

// Update dashboard dynamically
degreeSelect.addEventListener("change", function () {
  const selected = this.value;
  if (!selected) {
    tableBody.innerHTML = `<tr><td colspan="4" style="text-align:center;">Select a degree to view data</td></tr>`;
    selectedCourse.textContent = "—";
    totalUsers.textContent = "0";
    barChart.data.datasets[0].data = [0, 0, 0, 0];
    lineChart.data.datasets[0].data = [0, 0, 0, 0];
    barChart.update();
    lineChart.update();
    return;
  }

  selectedCourse.textContent = selected;

  const data = degreeData[selected];
  let total = 0;
  tableBody.innerHTML = "";

  data.branches.forEach((b) => {
    total += b.students;
    const row = `
      <tr>
        <td>${b.name}</td>
        <td>${b.students}</td>
        <td>Active</td>
        <td><a href="#" class="action-link">View</a></td>
      </tr>`;
    tableBody.insertAdjacentHTML("beforeend", row);
  });

  totalUsers.textContent = total;

  // ✅ Update charts dynamically
  barChart.data.datasets[0].data = data.graph;
  lineChart.data.datasets[0].data = data.line;
  barChart.update();
  lineChart.update();
});
