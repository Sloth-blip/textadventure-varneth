import { computed } from "vue"

export function useMapView(gameState) {
  const mapNodes = computed(() => gameState.value?.map?.nodes ?? [])
  const mapEdges = computed(() => gameState.value?.map?.edges ?? [])

  const mapMetrics = computed(() => {
    if (!mapNodes.value.length) {
      return {
        minX: 0,
        minY: 0,
        cellSize: 110,
        padding: 50,
        width: 240,
        height: 180
      }
    }

    const xs = mapNodes.value.map(node => node.x)
    const ys = mapNodes.value.map(node => node.y)

    const minX = Math.min(...xs)
    const maxX = Math.max(...xs)
    const minY = Math.min(...ys)
    const maxY = Math.max(...ys)

    const cellSize = 110
    const padding = 50

    return {
      minX,
      minY,
      cellSize,
      padding,
      width: (maxX - minX) * cellSize + padding * 2,
      height: (maxY - minY) * cellSize + padding * 2
    }
  })

  function svgX(node) {
    return mapMetrics.value.padding + (node.x - mapMetrics.value.minX) * mapMetrics.value.cellSize
  }

  function svgY(node) {
    return mapMetrics.value.padding + (node.y - mapMetrics.value.minY) * mapMetrics.value.cellSize
  }

  function findNode(roomId) {
    return mapNodes.value.find(node => node.roomId === roomId)
  }

  const renderedNodes = computed(() =>
    mapNodes.value.map(node => ({
      ...node,
      svgX: svgX(node),
      svgY: svgY(node),
      displayLabel: node.visited || node.current ? node.label : "?"
    }))
  )

  const renderedEdges = computed(() =>
    mapEdges.value
      .map(edge => {
        const from = findNode(edge.fromRoomId)
        const to = findNode(edge.toRoomId)

        if (!from || !to) return null

        return {
          ...edge,
          x1: svgX(from),
          y1: svgY(from),
          x2: svgX(to),
          y2: svgY(to)
        }
      })
      .filter(Boolean)
  )

  return {
    mapNodes,
    mapEdges,
    mapMetrics,
    renderedNodes,
    renderedEdges
  }
}