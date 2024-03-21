
const ctx = document.getElementById("myChart").getContext("2d");
const myChart = new Chart(ctx, {
  type: "bar",
  data: {
    labels: ["Paryż", "Londyn", "Rzym", "Barcelona", "Praga"],
    datasets: [
      {
        label: "Liczba podróżnych w 2023 roku",
        data: [789, 1013, 678, 776, 344],
        backgroundColor: ["rgb(54, 162, 235)"],
        borderWidth: 1,
      },
    ],
  },
  options: {
    scales: {
      y: {
        beginAtZero: true,
      },
    },
  },
});

document
  .getElementById("submit-button")
  .addEventListener("click", function (event) {
    const button = this;
    button.textContent = "Sending...";
    button.disabled = true;

    setTimeout(function () {
      button.textContent = "Sent!";
      button.disabled = false;
    }, 2000);
  });