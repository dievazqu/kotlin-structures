package sqrt

class SqrtDecomposition(val N:Int){

    private val K = Math.floor(Math.sqrt(N.toDouble())).toInt()
    private var values = Array<Long>(N) { 0 }
    private var buckets = Array<Long>(K + 1) { 0 }

    constructor(values: Array<Long>) : this(values.size){
        values.forEachIndexed(::set)
    }

    operator fun set(idx: Int, value: Long){
        buckets[idx/K] += value - values[idx]
        values[idx] = value
    }

    fun query(i:Int, j:Int): Long{
        var n = i
        var sum = 0L
        while(n < j){
            if(n%K == 0 && n+K <= j){
                sum += buckets[n/K]
                n += K
            }else{
                sum += values[n]
                n += 1
            }
        }
        return sum
    }
}
