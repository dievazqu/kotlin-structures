package graph

import java.util.*

class Graph(N: Int) {


    data class Edge(val node: Int, val w: Long)

    private val edges: Array<MutableList<Edge>> = Array(N + 1) { i -> LinkedList<Edge>() }

    fun addEdge(i: Int, j: Int, w: Long = 0) {
        edges[i].add(Edge(j, w))
    }

    // Tested on a problem
    fun <T> treeDfs(
        startNode: Int,
        baseCaseGenerator: (Int) -> T,
        mergeResultsFn: (T?, T, Edge) -> T,
        mergeResultsInRootFn: (T?, T, Edge) -> T = mergeResultsFn): T {

        var result: T? = null
        edges[startNode].forEach { edge ->
            val childResult = dfs(edge.node, startNode, baseCaseGenerator, mergeResultsFn)
            result = mergeResultsInRootFn(result, childResult, edge)
        }
        if (result == null) {
            return baseCaseGenerator(startNode)
        } else {
            return result!!
        }
    }

    private fun <T> dfs(currentNode: Int, parentNode: Int, baseCaseGenerator: (Int) -> T, mergeResultsFn: (T?, T, Edge) -> T): T {
        var result: T? = null
        edges[currentNode].forEach { edge ->
            if (edge.node != parentNode) {
                val childResult = dfs(edge.node, currentNode, baseCaseGenerator, mergeResultsFn)
                result = mergeResultsFn(result, childResult, edge)
            }
        }
        if (result == null) {
            return baseCaseGenerator(currentNode)
        } else {
            return result!!
        }
    }

}