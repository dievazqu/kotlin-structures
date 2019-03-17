class TestUtils{

    data class Result(val a:Int, val b:Int)

    companion object {

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
            val arr = LongArray(n)
            for(i in 0 until n){
                arr[i] = ((Math.random() - 0.5) * max).toLong()
            }
            return arr.toTypedArray()
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

