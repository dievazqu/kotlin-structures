package utils

class TestUtils{

    data class Result(val a:Int, val b:Int)

    companion object {

        fun getRandomNum(min: Long, max: Long): Long {
            return Math.random().toLong() * (max - min) + min
        }

        fun sortedRandomPair(n:Int, distinct: Boolean=false): Result {
            var I = (Math.random() * n).toInt()
            var J = (Math.random() * n).toInt()
            if (J<I){
                val aux = I
                I = J
                J = aux
            }
            if (distinct){
                if (I==0){
                    J+=1
                }else{
                    I-=1
                }
            }
            return Result(I, J)
        }

        fun buildRandomArray(n:Int, max:Long): Array<Long>{
            return (1..n).map { ((Math.random() - 0.5) * max).toLong() }.toTypedArray()
        }

        fun buildRandomMatrix(n: Int, m:Int, max:Long): Array<Array<Long>>{
            return (1..n).map { buildRandomArray(m, max) }.toTypedArray()
        }

        fun time(action: ()->Unit):Long {
            val started = System.currentTimeMillis()
            action.invoke()
            return System.currentTimeMillis() - started
        }
    }
}

