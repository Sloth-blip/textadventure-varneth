<script setup>
defineProps({
  renderedNodes: {
    type: Array,
    required: true
  },
  renderedEdges: {
    type: Array,
    required: true
  },
  mapMetrics: {
    type: Object,
    required: true
  }
})
</script>

<template>
  <section class="panel map-column">
    <h2 class="panel-title">Map</h2>
    <div class="panel-content map-column-content">
      <div v-if="renderedNodes.length === 0" class="map-empty">
        Noch nichts entdeckt.
      </div>

      <div v-else class="map-scroll">
        <svg
          class="map-svg"
          :viewBox="`0 0 ${mapMetrics.width} ${mapMetrics.height}`"
          :style="{
            width: `${mapMetrics.width}px`,
            height: `${mapMetrics.height}px`
          }"
        >
          <line
            v-for="edge in renderedEdges"
            :key="`${edge.fromRoomId}-${edge.toRoomId}`"
            class="map-edge"
            :x1="edge.x1"
            :y1="edge.y1"
            :x2="edge.x2"
            :y2="edge.y2"
          />

          <g
            v-for="node in renderedNodes"
            :key="node.roomId"
            class="map-node"
            :class="{
              current: node.current,
              visited: node.visited,
              discovered: node.discovered && !node.visited
            }"
            :transform="`translate(${node.svgX}, ${node.svgY})`"
          >
            <circle r="14" />
            <text y="32" text-anchor="middle">{{ node.displayLabel }}</text>
          </g>
        </svg>
      </div>
    </div>
  </section>
</template>

<style scoped>
.map-column {
  grid-column: 1;
  grid-row: 1 / 3;
  display: flex;
  flex-direction: column;
  min-height: 0;
}

.map-column-content {
  flex: 1;
  display: flex;
  min-height: 0;
}

.map-empty {
  color: #9aa3bd;
  padding: 8px 0;
}

.map-scroll {
  width: 100%;
  height: 100%;
  overflow: auto;
  min-height: 0;
  border: 1px solid rgba(154, 90, 18, 0.25);
  background:
    radial-gradient(circle at center, rgba(214, 123, 22, 0.04), transparent 55%),
    rgba(7, 10, 18, 0.7);
}

.map-svg {
  display: block;
}

.map-edge {
  stroke: rgba(214, 123, 22, 0.45);
  stroke-width: 2;
  stroke-linecap: round;
}

.map-node circle {
  fill: #182033;
  stroke: rgba(154, 90, 18, 0.55);
  stroke-width: 1.5;
}

.map-node text {
  fill: #d8dceb;
  font-size: 13px;
  font-family: Consolas, "Courier New", monospace;
}

.map-node.visited circle {
  fill: #24314d;
}

.map-node.discovered circle {
  fill: #101521;
  stroke-dasharray: 2 2;
}

.map-node.current circle {
  fill: #d67b16;
  stroke: #f0c08b;
  stroke-width: 3;
}

.map-node.current text {
  fill: #fff4e7;
  font-weight: bold;
}

@media (max-width: 1100px) {
  .map-column {
    grid-column: auto;
    grid-row: auto;
    min-height: 420px;
  }

  .map-scroll {
    min-height: 340px;
  }
}
</style>