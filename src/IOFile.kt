import java.util.*

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

fun main()
{
    val n = getInt()
    val arr = CompressionFactory.returnCompressedValues(getLongArray(n).toTypedArray())
    val presentNumbers = SegmentTree(LongArray(arr.size + 2).toTypedArray()) { a, b -> a + b }
    val lowerValueOfPairs = SegmentTree(LongArray(arr.size + 2).toTypedArray()) { a, b -> a + b }
    var result = 0L
    for (i in 0 until arr.size) {
        result += lowerValueOfPairs.query(arr[i] + 1, arr.size + 2)
        presentNumbers.set(arr[i], 1)
        lowerValueOfPairs.set(arr[i], presentNumbers.query(arr[i] + 1, arr.size + 2))
    }
    println(result)
}

class SegmentTree<T>(M:Int, val op:(T, T)->T) {

    private var maxCap: Int = maxCapFrom(M)
    private var data: Array<T?> = arrayOfNulls<Any>(maxCap * 2) as Array<T?>
    private var refresh = true

    constructor(values: Array<T>, op: (T, T)->T):this(values.size, op) {
        efficientAction {
            for (i in values.indices) {
                set(i, values[i])
            }
        }
    }

    private fun nullableOp(a: T?, b:T?):T?{
        if(a == null) return b
        if(b == null) return a
        return op.invoke(a, b)
    }

    private fun maxCapFrom(n: Int): Int{
        val k = 32 - Integer.numberOfLeadingZeros(n)
        return 1.shl(k)
    }

    private fun idx(i:Int):Int = maxCap - 1 + i

    fun efficientAction(action: ()->Unit){
        refresh = false
        action.invoke()
        refresh = true
        refresh()
    }

    fun remove(idx: Int) {
        val idx = idx(idx)
        data[idx] =  null
        if(refresh) refreshFromIndex(idx)
    }

    operator fun get(idx: Int): T? {
        return data[idx(idx)]
    }

    operator fun set(idx: Int, elem: T){
        val idx = idx(idx)
        data[idx] = elem
        if (refresh) refreshFromIndex(idx)
    }

    private fun refreshFromIndex(idx: Int) {
        var i = idx
        while (i != 0) {
            i = (i - 1) / 2
            this.data[i] = nullableOp(this.data[2 * i + 1], this.data[2 * i + 2])
        }
    }

    private fun refresh() {
        for (i in maxCap - 2 downTo 0) {
            this.data[i] = nullableOp(this.data[2 * i + 1], this.data[2 * i + 2])
        }
    }

    // [l, r]
    fun query(left: Int, right: Int): T {
        if (left > right || left < 0 || right >= data.size)
            throw IllegalArgumentException()
        val ans = queryRec(0, 0, maxCap - 1, left, right)
        return ans ?: throw IllegalStateException("SegmentTree is empty")
    }

    private fun queryRec(idx: Int, li: Int, ri: Int, lo: Int, ro: Int): T? {
        if (lo <= li && ri <= ro)
            return data[idx]
        if (ri < lo || li > ro)
            return null
        val mid = (ri + li) / 2
        return nullableOp(
            queryRec(idx * 2 + 1, li, mid, lo, ro),
            queryRec(idx * 2 + 2, mid + 1, ri, lo, ro))
    }

    fun fullString(): String{
        var str = ""
        str += data[0]
        for (i in 1 until data.size) {
            str += ", " + data[i]
        }
        return "[$str]"
    }

    override fun toString(): String {
        var str = ""
        str += data[maxCap - 1]
        for (i in 1 until maxCap) {
            str += ", " + data[maxCap - 1 + i]
        }
        return "[$str]"
    }

}

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
