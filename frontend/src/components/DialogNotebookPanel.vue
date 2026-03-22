<script setup>
import { ref } from "vue"

defineProps({
  entries: {
    type: Array,
    required: true
  }
})

const openEntries = ref({})

function toggle(source) {
  openEntries.value = {
    ...openEntries.value,
    [source]: !openEntries.value[source]
  }
}

function isOpen(source) {
  return !!openEntries.value[source]
}
</script>

<template>
  <section class="panel side-panel">
    <h2 class="panel-title">DialogLog</h2>
    <div class="panel-content scroll-panel">
      <p v-if="entries.length === 0" class="muted-text">
        Noch keine Dialognotizen.
      </p>

      <div v-else class="dialog-log-book">
        <div
          v-for="[source, chunks] in entries"
          :key="source"
          class="dialog-log-entry"
        >
          <button
            class="dialog-log-toggle"
            @click="toggle(source)"
          >
            <span>{{ source }}</span>
            <span class="dialog-log-arrow">
              {{ isOpen(source) ? "▾" : "▸" }}
            </span>
          </button>

          <ul
            v-if="isOpen(source)"
            class="plain-list dialog-log-list"
          >
            <li
              v-for="(chunk, index) in chunks"
              :key="`${source}-${index}`"
            >
              {{ chunk }}
            </li>
          </ul>
        </div>
      </div>
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

.dialog-log-book {
  display: flex;
  flex-direction: column;
  gap: 10px;
}

.dialog-log-entry {
  border-bottom: 1px solid rgba(154, 90, 18, 0.18);
  padding-bottom: 8px;
}

.dialog-log-entry:last-child {
  border-bottom: none;
  padding-bottom: 0;
}

.dialog-log-toggle {
  width: 100%;
  display: flex;
  justify-content: space-between;
  align-items: center;
  gap: 10px;
  background: linear-gradient(180deg, #0c1019 0%, #090d15 100%);
  border: 1px solid rgba(154, 90, 18, 0.35);
  color: #f2c38d;
  padding: 10px 12px;
  font-family: Consolas, "Courier New", monospace;
  font-size: 0.95rem;
  cursor: pointer;
  text-align: left;
}

.dialog-log-toggle:hover {
  border-color: #d67b16;
  color: #fff3e3;
  background: rgba(214, 123, 22, 0.08);
}

.dialog-log-arrow {
  color: #d8dceb;
  flex-shrink: 0;
}

.dialog-log-list {
  margin-top: 10px;
}
</style>