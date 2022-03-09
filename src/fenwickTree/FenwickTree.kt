package fenwickTree

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
