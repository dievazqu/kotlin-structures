package compilation

import dp.MemoizedRecursion

/**
 * Workspace for competitions:
 * Diego Vazquez
 */

var MAX = 10000
var ZEROS = IntArray(20)
var N: Int = -1
var A: Int = -1
var B: Int = -1
var goal: IntArray = ZEROS

fun main() {
    runCases {
        N = getInt()
        A = getInt()
        B = getInt()
        goal = getIntArray(N)
        var found = false
        var mem = MemoizedRecursion(::dpp)
        for(i in 1 .. MAX) {
            if(checkGoal(mem.solve(i))) {
                println(i)
                found=true
                break
            }
        }
        if (!found) {
            println("IMPOSSIBLE")
        }
    }
}

fun checkGoal(dp: IntArray): Boolean {
    for(i in 0 until N) {
        if (dp[i] < goal[i]) {
            return false
        }
    }
    return true
}

fun dpp(n: Int, mem: Memory<Int, IntArray>): IntArray {
    if (n <= 0) {
        return ZEROS
    }
    if (n <= 0) {
        return ZEROS
    }
    if (n<=N && (n <= B || goal[n-1]>0)) {
        val currentState = IntArray(N)
        currentState[n-1] = 1
        return currentState
    }

    var leftChild = mem(n - B)
    var rightChild = mem(n - A)
    var currentState = sumArray(leftChild, rightChild)

//    println("State before removing exceding: ${Arrays.toString(currentState)}")
    for(i in N-1 downTo 0) {
        if (currentState[i] > goal[i]) {
            var extras = currentState[i] - goal[i]
//            println("Exceded in $i for $extras")
            var left = mem(i+1-B)
            var right = mem(i+1-A)
            var explosionResult = sumArrayAndMultiply(left, right, extras)
            currentState[i] -= extras
            currentState = sumArray(currentState, explosionResult)
//            println("Adding after explosion: ${Arrays.toString(explosionResult)}")
        }
    }
//    println("Returned state for $n: ${Arrays.toString(currentState)}")
    return currentState
}

fun sumArrayAndMultiply(l: IntArray, r: IntArray, factor: Int): IntArray {
    var ans = IntArray(N)
    for(i in 0 until N) {
        ans[i] = (l[i] + r[i]) * factor
    }
    return ans
}

fun sumArray(l: IntArray, r: IntArray): IntArray {
    var ans = IntArray(N)
    for(i in 0 until N) {
        ans[i] = l[i] + r[i]
    }
    return ans
}
