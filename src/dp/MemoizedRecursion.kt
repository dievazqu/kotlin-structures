package dp

import compilation.MemFunction //compiler-ignore-line

/**
 * Be careful with Stack overflows, try bottom-up strategies.
 */
class MemoizedRecursion<I, O>(val fn: MemFunction<I, O>) {

    private val mem = HashMap<I, O>()

    fun solve(i: I): O {
        val cachedValue = mem[i]
        if (cachedValue != null) {
            return cachedValue
        }
        var result = fn(i, ::solve)
        mem[i]=result
        return result
    }

    companion object {
        fun <A> runBottomUpRecursion(n: Int, fn: MemFunction<Int, A>): A {
            var mem = MemoizedRecursion(fn)
            for (i in 0 until n) {
                mem.solve(i)
            }
            return mem.solve((n))
        }
    }
}

