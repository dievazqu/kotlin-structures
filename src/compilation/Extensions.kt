package compilation
import java.util.*

/**
 * Hardcoded Extensions
 */


// Uncomment to parse graphs
//fun getGraph(size: Int): Graph {
//    var g = Graph(size)
//    (1..size-1).forEach {
//        var i = getInt()
//        var j = getInt()
//        var w = getLong()
//        g.addEdge(i, j, w)
//        g.addEdge(j, i, w)
//    }
//    return g
//}

var s: Scanner = Scanner(System.`in`)
fun getInt(): Int = s.nextInt()
fun getLong(): Long = s.nextLong()
fun getString(): String = s.next()
fun getIntArray(size: Int): IntArray {
    val ans = IntArray(size)
    for (i in 0 until size)
        ans[i] = getInt()
    return ans
}
fun getLongArray(size: Int): LongArray {
    val ans = LongArray(size)
    for (i in 0 until size)
        ans[i] = getLong()
    return ans
}

fun <T, C: Comparable<C>> Iterable<T>.findMax(map: (T) -> C): Pair<Int, T>? {
    var maxx: C? = null
    var maxIdx = -1
    var maxValue: T? = null
    this.forEachIndexed { idx, t ->
        var comparedValue = map(t)
        if (maxx == null || maxx!! < comparedValue) {
            maxx = comparedValue
            maxIdx = idx
            maxValue = t
        }
    }

    if (maxValue == null) {
        return null
    } else {
        return  Pair(maxIdx, maxValue!!)
    }
}