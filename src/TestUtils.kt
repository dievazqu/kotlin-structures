class TestUtils{

    data class Result(val a:Int, val b:Int)

    companion object {

        fun getRandomNumber(min: Int, max: Int): Int {
            return getRandomNumber(min.toLong(), max.toLong()).toInt()
        }

        fun getRandomNumber(min: Long, max: Long): Long {
            if (max < min) {
                throw IllegalArgumentException("max cant be lower than min")
            }
            return (Math.random() * (max - min)).toLong() + min
        }

        fun getRandomSortedPair(n:Int): Result {
            var I = getRandomNumber(0, n)
            var J = getRandomNumber(0, n)
            if (J<I){
                return Result(J, I)
            } else {
                return Result(I, J)
            }
        }

        fun getRandomArray(n:Int, max:Long): Array<Long>{
            return (1..n).map { ((Math.random() - 0.5) * max).toLong() }.toTypedArray()
        }

        fun getRandomMatrix(n: Int, m:Int, max:Long): Array<Array<Long>>{
            return (1..n).map { getRandomArray(m, max) }.toTypedArray()
        }

        fun time(action: ()->Unit):Long {
            val started = System.currentTimeMillis()
            action.invoke()
            return System.currentTimeMillis() - started
        }
    }
}

