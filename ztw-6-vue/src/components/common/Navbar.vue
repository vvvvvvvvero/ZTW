<script>
export default {
  name: "Navbar"
}
</script>

<template>
  <nav>
    <router-link to="/authors">Authors</router-link>
    <router-link to="/books">Books</router-link>
    <router-link to="/landings">Landings</router-link>
  </nav>
</template>

<style scoped>
nav {
  display: flex;
  justify-content: center; /* Center items horizontally */
  background-color: #333; /* Dark background */
  padding: 0.8em 0; /* Vertical padding */
}

nav a {
  color: #fff; /* White text color for links */
  text-decoration: none;
  padding: 0.5em 1.5em; /* Padding around links */
  margin: 0 1em; /* Space between links */
  border-radius: 4px; /* Rounded corners for links */
  transition: background-color 0.3s, color 0.3s; /* Smooth transition for hover effects */
}

nav a:hover, nav a.router-link-exact-active {
  background-color: #4CAF50; /* Change background on hover and when active */
  color: #fff; /* White text on hover and when active */
}
</style>