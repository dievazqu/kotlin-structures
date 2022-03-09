package fenwickTree

import TestUtils.Companion.getRandomArray
import TestUtils.Companion.getRandomNumber
import TestUtils.Companion.getRandomSortedPair
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class FenwickTreeTest {

    lateinit private var originalArray: Array<Long>
    lateinit private var fenwickTree: FenwickTree
    private val N = 10e6.toInt()
    private val MAX = 10e18.toLong()

    @BeforeEach
    fun `create Fenwick tree with random values`(){
        originalArray = getRandomArray(N, MAX)
        fenwickTree = FenwickTree(originalArray)
    }

    @Test
    fun `get(I) should return the I-th elem`(){
        val I = getRandomNumber(0, N)

        Assertions.assertEquals(originalArray[I], fenwickTree[I])
    }

    fun queryCheck() {
        var (I, J) = getRandomSortedPair(N)

        var expectedSum = 0L
        for(i in I until J){
            expectedSum += originalArray[i]
        }

        Assertions.assertEquals(expectedSum, fenwickTree.query(I, J)) {
            "query($I, $J) failed"
        }
    }

    fun setAndQueryCheck() {
        var (I, J) = getRandomSortedPair(N)
        var pos = getRandomNumber(I, J)
        val value = getRandomNumber(-MAX, MAX)

        var currentValue = fenwickTree[pos]
        var queryBefore = fenwickTree.query(I, J)
        fenwickTree[pos] = value
        var queryAfter = fenwickTree.query(I, J)

        Assertions.assertEquals(queryBefore + value - currentValue, queryAfter) {
            "query($I, $J) failed"
        }
    }

    @Test
    fun `query results must be consistent for random ranges`(){
        var Q = 10e3.toInt()
        (0..Q).map{ queryCheck() }
    }

    @Test
    fun `changing elements values using #set should not change other behaviour`() {
        var U = 10e6.toInt()
        (0..U).map{ setAndQueryCheck() }
    }
}