<script setup>
defineProps({
  actions: {
    type: Array,
    required: true
  },
  dialogueActionId: {
    type: String,
    default: null
  }
})

const emit = defineEmits(["select"])
</script>

<template>
  <section class="panel wide-actions">
    <h2 class="panel-title">Aktionen</h2>
    <div class="panel-content wide-actions-content">
      <div class="action-list-wide">
        <button
          v-for="action in actions"
          :key="action.id"
          class="action-button"
          :class="{ 'dialogue-action': action.id === dialogueActionId }"
          @click="emit('select', action.id)"
        >
          {{ action.label }}
        </button>
      </div>
    </div>
  </section>
</template>

<style scoped>
.wide-actions {
  display: flex;
  flex-direction: column;
}

.wide-actions-content {
  display: flex;
  justify-content: center;
  align-items: flex-start;
}

.action-list-wide {
  width: min(760px, 100%);
  display: flex;
  flex-direction: column;
  gap: 10px;
}

.action-button {
  width: 100%;
  text-align: left;
  background: transparent;
  border: 1px solid rgba(154, 90, 18, 0.25);
  color: #d8dceb;
  padding: 12px 14px;
  cursor: pointer;
  font-family: inherit;
  white-space: pre-wrap;
}

.action-button:hover {
  border-color: #d67b16;
  color: #f5f7ff;
}

.dialogue-action {
  border-color: rgba(214, 123, 22, 0.7);
  color: #f2c38d;
  box-shadow: inset 0 0 0 1px rgba(214, 123, 22, 0.18);
}

.dialogue-action:hover {
  border-color: #d67b16;
  color: #fff3e3;
  background: rgba(214, 123, 22, 0.08);
}

@media (max-width: 1100px) {
  .wide-actions-content {
    justify-content: stretch;
  }

  .action-list-wide {
    width: 100%;
  }
}
</style>