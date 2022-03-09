package timeComparison

import TestUtils
import rmq.SegmentTree
import rmq.SparseTable

class StaticMax(){

    data class Query(val i:Int, val j:Int)

    fun compare(n:Int, max:Long, q:Int){
        val values = TestUtils.getRandomArray(n, max)
        val queries = (1..q).map {
            // TODO: FIX (a, a) pairs
            val (i, j) = TestUtils.getRandomSortedPair(n)
            Query(i, j)
        }
        compare(values, queries)
    }

    private fun compare(values: Array<Long>, queries:List<Query>) {
        val sparseTime = TestUtils.time {
            val sparse = SparseTable(values) { a, b -> Math.max(a, b) }
            queries.forEach { sparse.query(it.i, it.j) }
        }

        val segmentTime = TestUtils.time {
            val segment = SegmentTree(values) { a, b -> Math.max(a, b) }
            queries.forEach { segment.query(it.i, it.j) }
        }

        println("SparseTable: $sparseTime")
        println("SegmentTree: $segmentTime")
    }
}

fun main(args: Array<String>) {
    val comp = StaticMax()
    comp.compare(1_000_000, 100_000_000L, 1_000_000)
    println()
    comp.compare(100_000, 100_000_000L, 10_000_000)
}



