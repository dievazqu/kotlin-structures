package utils

class TestUtils{

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

        fun getRandomRange(min: Int, max: Int, maxRange: Int? = null): Pair<Int, Int> {
            val (I, J) = getRandomRange(min.toLong(), max.toLong(), maxRange?.toLong())
            return Pair(I.toInt(), J.toInt())
        }

        /**
         * Return a range with different value in [$min, $max] and the range size is lower than $maxRange
         */
        fun getRandomRange(min: Long, max: Long, maxRange: Long? = null): Pair<Long, Long> {
            if (maxRange != null && maxRange <= 2) {
                throw IllegalArgumentException("maxRange must be greater than 2")
            }
            if (max <= min) {
                throw IllegalArgumentException("min must be lower than max")
            }
            var a = getRandomNumber(min, max - 1)
            var b = getRandomNumber(min, max - 1)
            var minn = Math.min(a, b)
            var maxx = Math.max(a, b) + 1

            if (maxRange != null && maxRange < maxx - minn) {
                maxx = minn + maxRange
            }
            return Pair(minn, maxx)
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

