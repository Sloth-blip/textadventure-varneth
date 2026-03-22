<script setup>
defineProps({
  logs: {
    type: Array,
    required: true
  }
})
</script>

<template>
  <section class="panel external-log-panel">
    <h2 class="panel-title">Logs</h2>
    <div class="panel-content external-log-content">
      <p v-if="logs.length === 0" class="muted-text">Keine Logs vorhanden.</p>
      <ul v-else class="plain-list">
        <li v-for="entry in logs" :key="entry">{{ entry }}</li>
      </ul>
    </div>
  </section>
</template>

<style scoped>
.external-log-panel {
  width: 100%;
  display: flex;
  flex-direction: column;
}

.external-log-content {
  max-height: 320px;
  overflow: auto;
}

.muted-text {
  margin: 0;
  color: #93a0c2;
}
</style>