package sugarya.chapter2

import java.io.FileInputStream

fun main(args: Array<String>) {
//    test()

    circle()
}

private fun test() {
    val message = "Kotlin" // final String message = "Kotlin";
    var count = 0

    val msg: String = "Kotlin" //value
    var count2: Int = 0  // variant

    val p = Person("Kotlin")
    p.name
    /*
        Q1 var与val什么区别？
        val = 加了final变量
     */

    val languageList = arrayListOf<String>("Kotlin", "Java")
    languageList.add("Go")

    /*
        字符串模板 java web EL表达式
     */

    println("message = " + message)

    println("message = $message")
    println("count = ${count}")
    println("language list size = ${languageList.size}")
    println("\$: ${count2}")

    /*
       函数
     */

    fun max(a: Int, b: Int): Int {
        if (a > b) {
            return a
        }
        return b
    }

    fun max2(a: Int, b: Int) = if (a > b) a else b

    /*
        Q2 表达式和语句有什么区别？
        1. 返回值  2. 包含 ， 并列

        表达式：if ,when， try
     */

    /*
     when
     */
    fun filter(a: Int) {
        when (a) {
            0 -> println("0")
            1 -> println("1")
            in 2..10 -> println("in 2..10")
            !in 11..20 -> println("! in 11..20")
            Integer.parseInt("21") -> println("21")
            22, 23 -> println("22 , 23")
            else -> println("else")
        }
    }

    fun filter2(x: Any) {
        when (x) {
            is Int -> println("Int")
            is String -> println("String")
            is Person -> print("JavaPerson")
        }
    }


    fun filter3(x: Any) =
            when (x) {
                is Int -> {
                    val a = 3
                    "Int"
                }
                is String -> {
                    val b = ""
                    "String"
                }
                else -> {
                    "else"
                }
            }

    fun filter4(x: Any) =
            when {
                x is Int -> "Int"
                x is String -> "String"
                else -> "else"
            }


}

/*
       循环 和 迭代
     */
fun circle() {
    for (i in 0..5) {
        println("i : $i")
    }

    for(j in 0 until 5){
        println("j : $j")
    }

    for(k in 5 downTo 0){
        println("k : $k")
    }


    val languageList = arrayListOf<String>("Kotlin", "Java", "Go")
    for((index, value) in languageList.withIndex()){
        println("index = $index, value = $value")
    }

    val languageMap = hashMapOf<String, String>("j" to "Java", "k" to "Kotlin", "g" to "Go")
    for((key, v) in languageMap){
        println("$key : map value = $v")
    }

    /*
       异常try
     */
    fun testRuntimeException(){
        val i = Integer.parseInt("!")
    }

    fun testCheckedException(){
        val fis = FileInputStream("/temp")
        throw IllegalArgumentException("")
    }

    /*
     * 小结：1）不区分受检异常和非受检异常, 不强制处理异常
     * 2） throws
     * Throwable
     * Error, Exception
     * 运行时异常RuntimeException,， 受检异常 NotFineFileException
     */

    fun readNumber(){
        val a = try {
            val c = ""
            Integer.parseInt("!")
        }catch (e: Exception){
            e.printStackTrace()
            0
        }

        println("a = $a")
    }

    /*
        枚举
     */


}

enum class Season{
    SPRING,
    SUMMER,
    AUTUMN,
    WINTER
}

enum class Color(val r: Int, val g: Int, val b: Int){
    RED(255, 0, 0),
    GREEN(0, 255, 0),
    BLUE(0,0,255);

    fun rgb() = r * 255 * 255 + g * 255 + b
}

/*
    类和属性 自定义访问器
    属性 = 字段 + 访问器
 */