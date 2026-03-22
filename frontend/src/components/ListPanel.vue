<script setup>
defineProps({
  title: {
    type: String,
    required: true
  },
  items: {
    type: Array,
    required: true
  },
  emptyText: {
    type: String,
    required: true
  }
})
</script>

<template>
  <section class="panel side-panel">
    <h2 class="panel-title">{{ title }}</h2>
    <div class="panel-content scroll-panel">
      <p v-if="items.length === 0" class="muted-text">{{ emptyText }}</p>
      <ul v-else class="plain-list">
        <li v-for="item in items" :key="item">{{ item }}</li>
      </ul>
    </div>
  </section>
</template>

<style scoped>
.side-panel {
  display: flex;
  flex-direction: column;
  min-height: 0;
}

.scroll-panel {
  flex: 1;
  overflow: auto;
  min-height: 0;
}

.muted-text {
  margin: 0;
  color: #93a0c2;
}
</style>