package fenwickTree

class FenwickTree2D(val N: Int, val M: Int) {

    constructor(currentArray: Array<Array<Long>>) : this(currentArray.size, currentArray[0].size) {
        for(i in currentArray.indices){
            for(j in currentArray[0].indices){
                update(i, j, currentArray[i][j])
            }
        }
    }

    private var acumValues: Array<LongArray> = Array(N + 1) { LongArray(M + 1) }
    private var values: Array<LongArray> = Array(N + 1) { LongArray(M + 1) }

    operator fun get(n: Int, m: Int): Long {
        return values[n + 1][m + 1]
    }

    /**
     * [(n0, m0), (n1, m1))
     */
    fun query(n0: Int, m0: Int, n1: Int, m1: Int): Long {
        if (n1 < n0 || m1 < m0) throw IllegalArgumentException("right value can't be lower that left one")
        return query(n1, m1) - query(n0, m1) - query(n1, m0) + query(n0, m0)
    }

    /**
     * [(0,0), (n,m))
     */
    private fun query(n: Int, m: Int): Long {
        var res = 0L
        var i = n
        while (i > 0){
            var j = m
            while (j > 0) {
                res += acumValues[i][j]
                j -= Integer.lowestOneBit(j)
            }
            i -= Integer.lowestOneBit(i)
        }
        return res
    }

    fun update(x: Int, y: Int, value: Long) {
        var i = x + 1
        values[x + 1][y + 1] += value
        while (i < acumValues.size) {
            var j = y + 1
            while (j < acumValues[0].size) {
                acumValues[i][j] += value
                j += Integer.lowestOneBit(j)
            }
            i += Integer.lowestOneBit(i)
        }
    }

    operator fun set(n: Int, m:Int, value: Long) {
        val diff = value - values[n + 1][m + 1]
        update(n, m, diff)
    }
}

