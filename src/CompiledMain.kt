import java.util.*


/**
 * Workspace for competitions:
 * Diego Vazquez
 */

fun main()
{
    val n = getInt()
    val arr = CompressionFactory.returnCompressedValues(getLongArray(n).toTypedArray())
    val presentNumbers = FenwickTree(n + 2)
    val lowerValueOfPairs = FenwickTree(n + 2)
    var result = 0L
    for (i in 0 until arr.size) {
        result += lowerValueOfPairs.query(arr[i] + 1, arr.size + 2)
        presentNumbers[arr[i]] = 1
        lowerValueOfPairs[arr[i]] = presentNumbers.query(arr[i] + 1, arr.size + 2)
    }
    println(result)
}

/**
 * Hardcoded Extensions
 */

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

/**
 * Utility service to map Long into Into conserving the order.
 */
class CompressionFactory {

    companion object {
        fun returnCompressedValues(arr: Array<Long>): List<Int> {
            var compression = Compression(arr)
            return arr.map { v -> compression.compress(v) }
        }
    }

    class Compression(arr: Array<Long>) {

        private var map: MutableMap<Long, Int> = HashMap()
        private var umap: MutableMap<Int, Long> = HashMap()

        init {
            val sortedArray = arr.sortedArray()
            sortedArray.forEachIndexed { index, value ->
                map.put(value, index)
                umap.put(index, value)
            }
        }

        fun compress(n: Long): Int = map[n]!!
        fun uncompress(n: Int): Long = umap[n]!!
    }
}

/**
 * Fenwick Tree implementation that supports:
 * * Updating in O(ln(n))
 * * Querying the sum in any range in O(ln(n))
 *
 * Spacial Complexity: O(n)
 */
class FenwickTree(val N: Int) {

    constructor(currentArray: Array<Long>) : this(currentArray.size) {
        for(i in currentArray.indices){
            this[i] = currentArray[i]
        }
    }

    private var acumValues: LongArray = LongArray(N + 1)
    private var values: LongArray = LongArray(N + 1)

    // O(1)
    operator fun get(n: Int): Long {
        return values[n + 1]
    }

    /**
     * [l,r)
     */
    fun query(l: Int, r: Int): Long {
        if (r < l) throw IllegalArgumentException("right value can't be lower that left one")
        return query(r) - query(l)
    }

    /**
     * [0,n)
     */
    private fun query(n: Int): Long {
        var n = n
        var sum: Long = 0
        while (n > 0) {
            sum += acumValues[n - 1]
            n -= Integer.lowestOneBit(n)
        }
        return sum
    }

    operator fun set(n: Int, value: Long) {
        val diff = value - values[n + 1]
        update(n, diff)
    }

    private fun update(n: Int, value: Long) {
        var n = n + 1
        values[n] += value
        while (n <= acumValues.size) {
            acumValues[n - 1] += value
            n += Integer.lowestOneBit(n)
        }
    }
}
