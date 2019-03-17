package rmq

class SparseTable<T>(val values: Array<T>, val op: (T, T) -> T) {

    private val length = values.size
    private val maxPot = 32 - Integer.numberOfLeadingZeros(length) // floor(lg(length)) + 1
    private val arr: Array<MutableList<T>> = buildingArray()

    private fun buildingArray(): Array<MutableList<T>> {
        val arr = Array<MutableList<T>>(maxPot) {
            mutableListOf()
        }

        for (i in 0 until length) {
            arr[0].add(values[i])
        }

        for (k in 1 until maxPot) {
            val shift = 1.shl(k-1)
            for (i in 0 until length - shift.shl(1) ) {
                arr[k].add(op.invoke(arr[k-1][i], arr[k-1][i+shift]))
            }
        }
        return arr
    }

    // [i, j)
    fun query(i:Int, j:Int): T{
        val k = 31 - Integer.numberOfLeadingZeros(j-i)
        val shift = 1.shl(k)
        return op.invoke(arr[k][i], arr[k][j-shift])
    }
}