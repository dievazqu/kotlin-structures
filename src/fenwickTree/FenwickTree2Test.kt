package fenwickTree

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class FenwickTree2Test {

    lateinit private var originalMatrix: Array<Array<Long>>
    lateinit private var fenwickTree: FenwickTree2
    private val N = 995
    private val M = 1005
    private val U = 100000
    private val MAX = 1000000L

    @BeforeEach
    fun `create Fenwick tree with random values`(){
        originalMatrix = TestUtils.buildRandomMatrix(N, M, MAX)
        fenwickTree = FenwickTree2(originalMatrix)
    }

    @Test
    fun `get(I, J) should return the (I, J) value`(){
        val I = (Math.random() * N).toInt()
        val J = (Math.random() * M).toInt()

        Assertions.assertEquals(originalMatrix[I][J], fenwickTree[I, J])
    }

    @Test
    fun `query(I0, J0, I1, J1) should return the sum of the elements from (I0,J0) to (I1-1, J1-1)`(){
        var (I0, I1) = TestUtils.sortedRandomPair(N)
        var (J0, J1) = TestUtils.sortedRandomPair(M)

        var sum = 0L
        for(i in I0 until I1){
            for(j in J0 until J1) {
                sum += originalMatrix[i][j]
            }
        }

        Assertions.assertEquals(sum, fenwickTree.query(I0, J0, I1, J1)) {
            "query($I0, $J0, $I1, $J1) failed"
        }
    }

    @Test
    fun query_stress_test(){
        (0..10_000).map { `query(I0, J0, I1, J1) should return the sum of the elements from (I0,J0) to (I1-1, J1-1)`() }
    }

    @Test
    fun `changing elements values using #set should not change other behaviour`() {
        for(i in 1..U){
            val posI = (Math.random() * N).toInt()
            val posJ = (Math.random() * M).toInt()
            val value = ((Math.random() - 0.5) * MAX).toLong()
            originalMatrix[posI][posJ] = value
            fenwickTree[posI, posJ] = value
        }

        `get(I, J) should return the (I, J) value`()
        query_stress_test()
    }

    @Test
    fun `changing elements values using #update should not change other behaviours`() {
        for(i in 1..U){
            val posI = (Math.random() * N).toInt()
            val posJ = (Math.random() * M).toInt()
            val diff = ((Math.random() - 0.5) * MAX).toLong()
            originalMatrix[posI][posJ] += diff
            fenwickTree.update(posI, posJ, diff)
        }

        `get(I, J) should return the (I, J) value`()
        query_stress_test()
    }




}