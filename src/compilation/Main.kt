package compilation

import fenwickTree.FenwickTree
import utils.CompressionFactory

/**
 * Workspace for competitions:
 * Diego Vazquez
 */

fun main()
{
    val n = getInt()
    val arr = CompressionFactory.returnCompressedValues(getLongArray(n).toTypedArray())
    val presentNumbers = FenwickTree(n + 2)
    val lowerValueOfPairs = FenwickTree(n + 2)
    var result = 0L
    for (i in 0 until arr.size) {
        result += lowerValueOfPairs.query(arr[i] + 1, arr.size + 2)
        presentNumbers[arr[i]] = 1
        lowerValueOfPairs[arr[i]] = presentNumbers.query(arr[i] + 1, arr.size + 2)
    }
    println(result)
}
