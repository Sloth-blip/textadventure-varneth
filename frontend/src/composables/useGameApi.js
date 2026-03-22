import { onMounted, ref } from "vue"

export function useGameApi(apiBase) {
  const sessionId = ref(null)
  const gameState = ref(null)
  const loading = ref(false)
  const error = ref("")

  async function fetchState() {
    loading.value = true
    error.value = ""

    try {
      const headers = {}

      if (sessionId.value) {
        headers["X-Session-Id"] = sessionId.value
      }

      const response = await fetch(`${apiBase}/api/state`, {
        method: "GET",
        headers
      })

      if (!response.ok) {
        throw new Error(`State request failed: ${response.status}`)
      }

      const newSessionId = response.headers.get("X-Session-Id")
      if (newSessionId) {
        sessionId.value = newSessionId
      }

      gameState.value = await response.json()
    } catch (err) {
      error.value = err.message
    } finally {
      loading.value = false
    }
  }

  async function sendIntent(actionId) {
    if (!sessionId.value) {
      error.value = "Keine Session-ID vorhanden."
      return
    }

    loading.value = true
    error.value = ""

    try {
      const response = await fetch(`${apiBase}/api/intent`, {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
          "X-Session-Id": sessionId.value
        },
        body: JSON.stringify({
          type: "SELECT_ACTION",
          value: actionId
        })
      })

      if (!response.ok) {
        throw new Error(`Intent request failed: ${response.status}`)
      }

      const newSessionId = response.headers.get("X-Session-Id")
      if (newSessionId) {
        sessionId.value = newSessionId
      }

      gameState.value = await response.json()
    } catch (err) {
      error.value = err.message
    } finally {
      loading.value = false
    }
  }

  onMounted(() => {
    fetchState()
  })

  return {
    sessionId,
    gameState,
    loading,
    error,
    fetchState,
    sendIntent
  }
}