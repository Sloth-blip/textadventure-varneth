<script setup>
defineProps({
  playerStatus: {
    type: Object,
    required: true
  }
})
</script>

<template>
  <section class="panel character-strip">
    <h2 class="panel-title">Characterstats</h2>
    <div class="panel-content">
      <div class="stat-row">
        <div class="stat-chip">
          <span class="stat-label">Level</span>
          <span class="stat-value">{{ playerStatus.level }}</span>
        </div>

        <div class="stat-chip">
          <span class="stat-label">HP</span>
          <span class="stat-value">
            {{ playerStatus.currentHp }} / {{ playerStatus.maxHp }}
          </span>
        </div>

        <div class="stat-chip">
          <span class="stat-label">MP</span>
          <span class="stat-value">
            {{ playerStatus.currentRessource }} / {{ playerStatus.maxRessource }}
          </span>
        </div>

        <div class="stat-chip">
          <span class="stat-label">XP</span>
          <span class="stat-value">
            {{ playerStatus.xp }} / {{ playerStatus.nextXpThreshold }}
          </span>
        </div>
      </div>

      <div v-if="playerStatus.skills.length > 0" class="skill-row">
        <span class="skill-label">Skills</span>
        <div class="skill-tags">
          <span
            v-for="skill in playerStatus.skills"
            :key="skill"
            class="skill-tag"
          >
            {{ skill }}
          </span>
        </div>
      </div>
    </div>
  </section>
</template>

<style scoped>
.character-strip {
  grid-column: 2;
  grid-row: 1;
}

.stat-row {
  display: grid;
  grid-template-columns: repeat(4, minmax(0, 1fr));
  gap: 10px;
}

.stat-chip {
  display: flex;
  flex-direction: column;
  gap: 6px;
  padding: 10px 12px;
  border: 1px solid rgba(154, 90, 18, 0.25);
  background: rgba(214, 123, 22, 0.04);
  min-height: 72px;
  justify-content: center;
}

.stat-label {
  color: #b8bfd6;
  font-size: 0.8rem;
  text-transform: uppercase;
  letter-spacing: 0.08em;
}

.stat-value {
  color: #f5f7ff;
  font-size: 1rem;
}

.skill-row {
  margin-top: 14px;
  display: flex;
  gap: 10px;
  align-items: flex-start;
}

.skill-label {
  color: #b8bfd6;
  min-width: 58px;
  padding-top: 6px;
}

.skill-tags {
  display: flex;
  flex-wrap: wrap;
  gap: 8px;
}

.skill-tag {
  padding: 6px 10px;
  border: 1px solid rgba(154, 90, 18, 0.3);
  background: rgba(214, 123, 22, 0.06);
  color: #f2c38d;
}

@media (max-width: 1100px) {
  .character-strip {
    grid-column: auto;
    grid-row: auto;
  }

  .stat-row {
    grid-template-columns: repeat(2, minmax(0, 1fr));
  }
}
</style>