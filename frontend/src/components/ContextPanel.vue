<script setup>
defineProps({
  mode: {
    type: String,
    required: true
  },
  contextText: {
    type: String,
    required: true
  },
  isDialogue: {
    type: Boolean,
    required: true
  }
})
</script>

<template>
  <section
    class="panel context-main"
    :class="{ 'dialogue-panel': isDialogue }"
  >
    <h2 class="panel-title">
      {{ isDialogue ? "Dialog" : "Kontext / Dialog" }}
    </h2>

    <div class="panel-content context-main-content">
      <div class="context-meta">
        <p class="mode-line">
          <strong>Mode:</strong> {{ mode }}
        </p>

        <span v-if="isDialogue" class="dialogue-badge">
          Gespräch aktiv
        </span>
      </div>

      <p
        class="context-text"
        :class="{ 'dialogue-text': isDialogue }"
      >
        {{ contextText }}
      </p>
    </div>
  </section>
</template>

<style scoped>
.context-main {
  grid-column: 2;
  grid-row: 2;
  min-height: 0;
  display: flex;
  flex-direction: column;
}

.context-main-content {
  flex: 1;
  overflow: auto;
  min-height: 0;
}

.context-meta {
  display: flex;
  justify-content: space-between;
  align-items: center;
  gap: 12px;
  margin-bottom: 14px;
}

.mode-line {
  margin: 0;
  color: #b8bfd6;
}

.context-text {
  margin: 0;
  white-space: pre-wrap;
  line-height: 1.6;
}

.dialogue-panel {
  border-color: rgba(214, 123, 22, 0.8);
  box-shadow: inset 0 0 0 1px rgba(214, 123, 22, 0.18);
}

.dialogue-badge {
  display: inline-block;
  padding: 4px 10px;
  border: 1px solid rgba(214, 123, 22, 0.55);
  color: #f2c38d;
  font-size: 0.78rem;
  letter-spacing: 0.08em;
  text-transform: uppercase;
  background: rgba(214, 123, 22, 0.08);
}

.dialogue-text {
  padding: 14px 16px;
  border-left: 3px solid rgba(214, 123, 22, 0.75);
  background: rgba(214, 123, 22, 0.05);
  color: #f3e7d6;
}

@media (max-width: 1100px) {
  .context-main {
    grid-column: auto;
    grid-row: auto;
  }
}
</style>