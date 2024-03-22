const observer = new IntersectionObserver((entries) => {
  entries.forEach((entry) => {
    console.log(entry)
    if (entry.isIntersecting) { // if the element is visible
      entry.target.classList.add("show")
    } else {
      entry.target.classList.remove("show")
    }
  });
});

const hiddenElements = document.querySelectorAll('.hidden');
hiddenElements.forEach((element) => observer .observe(element));

