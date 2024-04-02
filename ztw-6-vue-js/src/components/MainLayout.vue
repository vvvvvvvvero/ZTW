<script>
export default {
  name: "MainLayout"
}
</script>

<template>
  <div id="main-layout">
    <header>
    </header>
    <main> <!-- Główna zawartość -->
      <router-view/>
    </main>
    <footer> <!-- Stopka -->
      <!-- Komponenty stopki -->
    </footer>
  </div>
</template>

<style scoped>

</style>