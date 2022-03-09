package rmq.test

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import rmq.SparseTable

class SparseTableTest {

    lateinit private var originalArray: Array<Long>
    lateinit private var sparse: SparseTable<Long>
    private val N = 100000
    private val MAX = 1000000L

    @BeforeEach
    fun `create Sparse Table with random values`(){
        originalArray = TestUtils.getRandomArray(N, MAX)
        sparse = SparseTable<Long>(originalArray) { a, b -> Math.max(a,b)}
    }

    @Test
    fun `query(I, J) should return the maximum of elements from I until J`(){
        // TODO: FIX (a, a) pairs
        var (I, J) = TestUtils.getRandomSortedPair(N-1)
        var max = Long.MIN_VALUE
        for(i in I until J){
            max = Math.max(max, originalArray[i])
        }

        Assertions.assertEquals(max, sparse.query(I, J)) {
            "query($I, $J) failed"
        }
    }

    @Test
    fun query_stress_test(){
        (0..100_000).map {`query(I, J) should return the maximum of elements from I until J`()}
    }


}