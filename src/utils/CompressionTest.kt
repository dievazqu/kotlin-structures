package utils

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class CompressionTest {

    lateinit private var originalArray: Array<Long>

    private val N = 10000
    private val MAX = 100000000000L

    @BeforeEach
    fun `create array with random values`(){
        originalArray = TestUtils.getRandomArray(N, MAX)
    }

    @Test
    fun `returnCompressedValues return expected array`(){
        var returnedArray = CompressionFactory.returnCompressedValues(originalArray)

        checkOrderIsPreserved(originalArray, returnedArray)
    }

    fun checkOrderIsPreserved(originalArray: Array<Long>, comparedArray: List<Int>) {
        for (i in 0 until N) {
            for (j in 0 until N) {
                Assertions.assertEquals(
                    originalArray[i].compareTo(originalArray[j]),
                    comparedArray[i].compareTo(comparedArray[j]))
            }
        }
    }

    @Test
    fun `Compression behaves as expected`(){
        var compression = CompressionFactory.Compression(originalArray)

        checkValueAndOrderIsPreserved(originalArray, compression)
    }

    fun checkValueAndOrderIsPreserved(originalArray: Array<Long>, compression: CompressionFactory.Compression) {
        for (i in 0 until N) {
            Assertions.assertEquals(
                originalArray[i],
                compression.uncompress(compression.compress(originalArray[i])))


            for (j in 0 until N) {
                val ci = compression.compress(originalArray[i])
                val cj = compression.compress(originalArray[j])

                Assertions.assertEquals(
                    originalArray[i].compareTo(originalArray[j]),
                    ci.compareTo(cj))
            }
        }
    }

    @Test
    fun `It handles 10^6 elements in less than a couple of seconds`() {
        var array = TestUtils.getRandomArray(1000000, MAX)
        var time = TestUtils.time {
            CompressionFactory.returnCompressedValues(array)
        }
        Assertions.assertTrue(time < 5000)
    }
}