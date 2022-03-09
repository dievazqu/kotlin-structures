package utils

/**
 * Utility service to map Long into Int conserving the order.
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
