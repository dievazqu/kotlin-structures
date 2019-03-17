import java.io.File
import java.util.*

fun main(args: Array<String>) {

    val input = Scanner(File("input.txt"))
    val answer = StringBuffer()

    val T = input.nextInt()
    for(i in 1..T){
        val N = input.nextInt()
        val M = input.nextInt()
        answer.appendln("$N $M")
        for(n in 1..N){
            val str = input.next()
            answer.appendln(str)
        }
        for(m in 1..M){
            val str = input.next()
            answer.appendln(str)
        }

    }
    File("output.txt").writeText(answer.toString())
    
}

