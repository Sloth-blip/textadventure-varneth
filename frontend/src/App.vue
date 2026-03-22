<script setup>
import { computed } from "vue"
import TopBar from "./components/TopBar.vue"
import MapPanel from "./components/MapPanel.vue"
import CharacterStatsPanel from "./components/CharacterStatsPanel.vue"
import ContextPanel from "./components/ContextPanel.vue"
import ActionMenu from "./components/ActionMenu.vue"
import DialogNotebookPanel from "./components/DialogNotebookPanel.vue"
import ListPanel from "./components/ListPanel.vue"
import DevOverlay from "./components/DevOverlay.vue"
import { useGameApi } from "./composables/useGameApi"
import { useMapView } from "./composables/useMapView"

const {
  gameState,
  loading,
  error,
  sendIntent
} = useGameApi("http://localhost:8080")

const isDialogue = computed(() => gameState.value?.mode === "DIALOGUE")

const dialogueActionId = computed(() => {
  if (!gameState.value?.actions?.length) return null
  const found = gameState.value.actions.find(action => action.id === "NEXT_DIALOG")
  return found?.id ?? null
})

const dialogLogEntries = computed(() =>
  Object.entries(gameState.value?.dialogNotebook ?? {})
)

const {
  mapMetrics,
  renderedNodes,
  renderedEdges
} = useMapView(gameState)
</script>

<template>
  <div class="app-shell">
    <div class="page-column">
      <div class="game-frame">
        <TopBar />

        <div v-if="loading" class="info-box">Lade...</div>
        <div v-else-if="error" class="info-box error-box">{{ error }}</div>

        <template v-else-if="gameState">
          <div class="main-layout">
            <MapPanel
              :rendered-nodes="renderedNodes"
              :rendered-edges="renderedEdges"
              :map-metrics="mapMetrics"
            />

            <CharacterStatsPanel
              :player-status="gameState.playerStatus"
            />

            <ContextPanel
              :mode="gameState.mode"
              :context-text="gameState.contextText"
              :is-dialogue="isDialogue"
            />

            <aside class="side-stack">
              <DialogNotebookPanel :entries="dialogLogEntries" />

              <ListPanel
                title="Inventory"
                :items="gameState.inventory"
                empty-text="Leer."
              />

              <ListPanel
                title="Quests"
                :items="gameState.quests"
                empty-text="Keine aktiven Quests."
              />
            </aside>
          </div>

          <ActionMenu
            :actions="gameState.actions"
            :dialogue-action-id="dialogueActionId"
            @select="sendIntent"
          />
        </template>
      </div>

      <DevOverlay
        v-if="gameState"
        :logs="gameState.devLog"
      />
    </div>
  </div>
</template>

<style>
body {
  margin: 0;
  background:
    radial-gradient(circle at top, #101628 0%, #06080f 45%),
    #06080f;
  color: #f5f7ff;
  font-family: Consolas, "Courier New", monospace;
}

* {
  box-sizing: border-box;
}

#app {
  min-height: 100vh;
}

.app-shell {
  min-height: 100vh;
  display: flex;
  justify-content: center;
  align-items: flex-start;
  padding: 20px;
}

.page-column {
  width: min(1280px, 100%);
  display: flex;
  flex-direction: column;
  gap: 18px;
}

.game-frame {
  width: 100%;
  height: calc(100vh - 40px);
  display: grid;
  grid-template-rows: auto minmax(0, 1fr) auto;
  gap: 18px;
  background: linear-gradient(180deg, #05070d 0%, #06080f 100%);
  border: 1px solid #9a5a12;
  border-radius: 18px;
  padding: 18px;
  box-shadow: 0 0 28px rgba(214, 123, 22, 0.08);
  overflow: hidden;
}

.panel {
  border: 1px solid rgba(154, 90, 18, 0.45);
  background: linear-gradient(180deg, #070a12 0%, #0a0d16 100%);
  min-width: 0;
  min-height: 0;
}

.panel-title {
  margin: 0;
  padding: 12px;
  text-align: center;
  border-bottom: 1px solid rgba(154, 90, 18, 0.45);
}

.panel-content {
  padding: 16px;
  min-height: 0;
}

.info-box {
  padding: 14px;
  border: 1px solid rgba(154, 90, 18, 0.45);
}

.error-box {
  color: #ff9b9b;
}

.main-layout {
  display: grid;
  grid-template-columns: 280px minmax(0, 1fr) 250px;
  grid-template-rows: auto minmax(0, 1fr);
  gap: 14px;
  min-height: 0;
}

.side-stack {
  grid-column: 3;
  grid-row: 1 / 3;
  display: grid;
  grid-template-rows: minmax(0, 1fr) 180px 180px;
  gap: 14px;
  min-height: 0;
}

.plain-list {
  margin: 0;
  padding-left: 18px;
}

.plain-list li + li {
  margin-top: 8px;
}

.muted-text {
  margin: 0;
  color: #93a0c2;
}

@media (max-width: 1100px) {
  .game-frame {
    height: auto;
    min-height: calc(100vh - 40px);
    grid-template-rows: auto auto auto;
    overflow: visible;
  }

  .main-layout {
    grid-template-columns: 1fr;
    grid-template-rows: auto;
  }

  .side-stack {
    grid-column: auto;
    grid-row: auto;
    grid-template-rows: auto;
  }
}
</style>