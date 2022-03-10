package dp.examples

import compilation.MOD
import dp.MemoizedRecursion
import utils.TestUtils.Companion.time

fun main() {
    var N = 1e7.toInt()
    var arr = IntArray(5) { i -> i}
    var (a, b, c, d) = arr
    println("$a $b $c $d")
    println(time {
        MemoizedRecursion.runBottomUpRecursion(N, ::fibonacci)
    })
}

fun fibonacci(i: Int, mem: (Int) -> (Long)): Long {
    if (i<0) {
        return 0
    }
    if (i<2) {
        return 1
    }
    return (mem(i-1) + mem(i-2)) % MOD
}