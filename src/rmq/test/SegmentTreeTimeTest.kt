package rmq.test

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import rmq.SegmentTree

class SegmentTreeTimeTest {

    lateinit private var originalArray: Array<Long>
    lateinit private var rmq: SegmentTree<Long>
    private val N = 100000
    private val MAX = 1000000L

    @BeforeEach
    fun `create Segment Tree with random values`(){
        originalArray = TestUtils.getRandomArray(N, MAX)
        rmq = SegmentTree<Long>(originalArray) { a, b -> a + b }
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