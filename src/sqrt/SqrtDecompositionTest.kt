package sqrt

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import rmq.SegmentTree

class SqrtDecompositionTest {

    lateinit private var originalArray: Array<Long>
    lateinit private var sqrt: SqrtDecomposition
    private val N = 100000
    private val U = 100000
    private val MAX = 1000000L

    @BeforeEach
    fun `create Sqrt Decomposition with random values`(){
        originalArray = TestUtils.buildRandomArray(N, MAX)
        sqrt = SqrtDecomposition(originalArray)
    }

    @Test
    fun `query(I, J) should return the sum of elements from I until J`(){
        var (I, J) = TestUtils.sortedRandomPair(N)
        var sum = 0L
        for(i in I until J){
            sum += originalArray[i]
        }

        Assertions.assertEquals(sum, sqrt.query(I, J)) {
            "query($I, $J) failed"
        }
    }

    @Test
    fun query_stress_test(){
        (0..10_000).map {`query(I, J) should return the sum of elements from I until J`()}
    }

    @Test
    fun `changing elements values using #set should not change other behaviour`() {
        for(i in 1..U){
            val pos = (Math.random() * N).toInt()
            val value = ((Math.random() - 0.5) * MAX).toLong()
            originalArray[pos] = value
            sqrt[pos] = value
        }

        query_stress_test()
    }




}