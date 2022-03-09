package fenwickTree

import utils.TestUtils.Companion.getRandomArray
import utils.TestUtils.Companion.getRandomNumber
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import utils.TestUtils.Companion.getRandomRange

class FenwickTreeTest {

    lateinit private var originalArray: Array<Long>
    lateinit private var fenwickTree: FenwickTree
    private val N = 10e6.toInt()
    private val MAX = 10e18.toLong()
    private val timesTested = 10e3.toInt()

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

    @Test
    fun queryCheck() {
        val (l, r) = getRandomRange(0, N, 10000)

        var expectedSum = 0L
        for(i in l until r){
            expectedSum += originalArray[i]
        }

        Assertions.assertEquals(expectedSum, fenwickTree.query(l, r)) {
            "query($l, $r) failed"
        }
    }

    fun setAndQueryCheck() {
        var (I, J) = getRandomRange(0, N);
        var pos = getRandomNumber(I, J)
        val diff = getRandomNumber(-MAX, MAX)

        var queryBefore = fenwickTree.query(I, J)
        fenwickTree[pos] += diff
        var queryAfter = fenwickTree.query(I, J)

        Assertions.assertEquals(queryBefore + diff, queryAfter) {
            "query($I, $J) failed"
        }
    }

    @Test
    fun `query results must be consistent for random ranges`(){
        (0..timesTested).map{ queryCheck() }
    }

    @Test
    fun `changing elements values using #set should change query results`() {
        (0..timesTested).map{ setAndQueryCheck() }
    }
}