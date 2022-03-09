package timeComparison

import TestUtils
import fenwickTree.FenwickTree
import rmq.SegmentTree
import sqrt.SqrtDecomposition

class DynamicSum(){

    data class Query(val i:Int, val j:Int)
    data class Update(val i:Int, val v:Long)

    fun compare(n:Int, max:Long, q:Int, u:Int){
        val values = TestUtils.getRandomArray(n, max)
        val queries = (1..q).map {
            // TODO: FIX (a, a) pairs
            val (i, j) = TestUtils.getRandomSortedPair(n)
            Query(i, j)
        }
        val updates = (1..u).map {
            val i = (Math.random() * n).toInt()
            val v = ((Math.random() - 0.5) * max).toLong()
            Update(i, v)
        }
        compare(values, queries, updates)
    }

    private fun compare(values: Array<Long>, queries:List<Query>, updates:List<Update>) {
        val fenwickTime = TestUtils.time {
            val fenwickTree = FenwickTree(values)
            queries.forEach { fenwickTree.query(it.i, it.j) }
            updates.forEach { fenwickTree[it.i] = it.v }
        }

        val segmentTime = TestUtils.time {
            val segment = SegmentTree(values) { a, b -> a + b }
            queries.forEach { segment.query(it.i, it.j) }
            updates.forEach { segment[it.i] = it.v }
        }

        val sqrtTime = TestUtils.time {
            val sqrt = SqrtDecomposition(values)
            queries.forEach { sqrt.query(it.i, it.j) }
            updates.forEach { sqrt[it.i] = it.v }
        }


        println("FenwickTree: $fenwickTime")
        println("SegmentTree: $segmentTime")
        println("SqrtDecomposition: $sqrtTime")
    }
}

fun main(args: Array<String>) {
    val comp = DynamicSum()
    comp.compare(1_000_000, 100_000_000L, 1_000_000, 1_000_000)
    println()
    comp.compare(1_000_000, 100_000_000L, 100_000, 10_000_000)
}



