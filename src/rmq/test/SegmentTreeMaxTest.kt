package rmq.test

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import rmq.SegmentTree
import utils.TestUtils

class SegmentTreeMaxTest {

    lateinit private var originalArray: Array<Long>
    lateinit private var rmq: SegmentTree<Long>
    private val N = 100000
    private val U = 100000
    private val MAX = 1000000L

    @BeforeEach
    fun `create Segment Tree with random values`(){
        originalArray = TestUtils.getRandomArray(N, MAX)
        rmq = SegmentTree<Long>(originalArray) { a, b -> Math.max(a,b) }
    }

    @Test
    fun `query(I, J) should return the maximum of elements from I to J`(){
        var (I, J) = TestUtils.getRandomRange(0, N)
        var max = Long.MIN_VALUE
        for(i in I..J){
            max = Math.max(max, originalArray[i])
        }

        Assertions.assertEquals(max, rmq.query(I, J)) {
            "query($I, $J) failed"
        }
    }

    @Test
    fun query_stress_test(){
        (0..10_000).map {`query(I, J) should return the maximum of elements from I to J`()}
    }

    @Test
    fun `changing elements values using #set should not change other behaviour`() {
        for(i in 1..U){
            val pos = (Math.random() * N).toInt()
            val value = ((Math.random() - 0.5) * MAX).toLong()
            originalArray[pos] = value
            rmq[pos] = value
        }

        query_stress_test()
    }

    @Test
    fun `changing elements values using #efficientAction + #set should not change other behaviour`() {
        rmq.efficientAction {
            for (i in 1..U) {
                val pos = (Math.random() * N).toInt()
                val value = ((Math.random() - 0.5) * MAX).toLong()
                originalArray[pos] = value
                rmq[pos] = value
            }
        }

        query_stress_test()
    }

    @Test
    fun `updating in efficient actions is more efficient`(){
        val positions = (1..N).map { (Math.random() * N).toInt() }
        val values = (1..N).map { ((Math.random() - 0.5) * MAX).toLong() }

        val normalUpdateTime = TestUtils.time {
            for (i in 0 until N) {
                rmq[positions[i]] = values[i]
            }
        }

        val efficientUpdateTime = TestUtils.time {
            rmq.efficientAction {
                for (i in 0 until N) {
                    rmq[positions[i]] = values[i]
                }
            }
        }

        Assertions.assertTrue(normalUpdateTime > efficientUpdateTime)
    }

}