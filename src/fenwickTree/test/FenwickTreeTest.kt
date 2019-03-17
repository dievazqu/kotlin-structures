package fenwickTree.test

import fenwickTree.FenwickTree
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class FenwickTreeTest {

    lateinit private var originalArray: Array<Long>
    lateinit private var fenwickTree: FenwickTree
    private val U = 100000
    private val N = 100000
    private val MAX = 1000000L

    @BeforeEach
    fun `create Fenwick tree with random values`(){
        originalArray = TestUtils.buildRandomArray(N, MAX)
        fenwickTree = FenwickTree(originalArray)
    }

    @Test
    fun `get(I) should return the I-th elem`(){
        val I = (Math.random() * N).toInt()

        Assertions.assertEquals(originalArray[I], fenwickTree[I])
    }

    @Test
    fun `query(I, J) should return the sum of elements from I until J`(){
        var (I, J) = TestUtils.sortedRandomPair(N)

        var sum = 0L
        for(i in I until J){
            sum += originalArray[i]
        }

        Assertions.assertEquals(sum, fenwickTree.query(I, J)) {
            "query($I, $J) failed"
        }
    }

    @Test
    fun query_stress_test(){
        (0..10_000).map{ `query(I, J) should return the sum of elements from I until J`() }
    }

    @Test
    fun `changing elements values using #set should not change other behaviour`() {
        for(i in 1..U){
            val pos = (Math.random() * N).toInt()
            val value = ((Math.random() - 0.5) * MAX).toLong()
            originalArray[pos] = value
            fenwickTree[pos] = value
        }

        `get(I) should return the I-th elem`()
        query_stress_test()
    }

    @Test
    fun `changing elements values using #update should not change other behaviours`() {
        for(i in 1..U){
            val pos = (Math.random() * N).toInt()
            val diff = ((Math.random() - 0.5) * MAX).toLong()
            originalArray[pos] += diff
            fenwickTree.update(pos, diff)
        }

        `get(I) should return the I-th elem`()
        query_stress_test()
    }




}