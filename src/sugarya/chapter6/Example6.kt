package sugarya.chapter6

fun main(args: Array<String>) {
    testNoNullClient()
}


//Kotlin基本类型和转化

fun testBasicType() {
    val bb: Byte = 127
    val boolean = false
    val aa: Char = 'a'

    var i: Int = 21_4748_3647 //10位
    var l: Long = 922_3372_0368_5477_5807L  //19位 //不存在小写的l


    val f: Float = 123f
    val f2: Float = 124F

    val a16 = 0x123ef3
    val b16 = 0XcCCef3

    val c2 = 0b1010
    val cc2 = 0B11001

    val b: Byte = 1

    val eas = b + 1L //Long + Byte => Long , eas为Long类型


    fun foo(l: Long) {
        println(l)
    }

    foo(44) //单独写44可能是byte，int long，这里智能推断44会被认为是long

    //字符串转基本数据类型
    val p = "44".toInt()
    val p2 = "44".toLong()
    val p3 = "44".toFloatOrNull()
    val p4 = "44".toDouble()

    val translate = l.toInt().toLong().toFloat().toDouble().toInt()
}


//Any Unit Nothing

fun getStrinLenght(s: String?): Int {
    if (s == null) {
        throw IllegalArgumentException()
    } else {
        return s.length
    }
}

fun fail(): Nothing = throw IllegalArgumentException()

fun getStrinLenght2(s: String?): Int =
        if (s == null) {
            fail()
        } else {
            s.length
        }

fun testAnyAndNothing(o: Any?): View {
    val filterO = o ?: fail()

    return when (filterO) {
        is View -> {
            View("View")
        }
        is TextView -> {
            TextView("TextView", "")
        }
        else -> {
            fail()
        }
    }
}

//空安全调用
open class Company(val name: String, val address: String?)

fun testNullType() {
    val company = Company("京东", null)

    val length = company.address?.length
    println("length = $length")
}

//Elvis运算法
fun testElvis(list: List<Company?>?, defaultAddress: String?) {
    val defaultAdd = defaultAddress ?: ""
    val length = defaultAddress?.length ?: 0 //.?为空调用，表达式返回null，遇到了？：
    println("defaultAdd = $defaultAdd, length = $length")

    val companyList = list ?: throw IllegalArgumentException("null list")

    val address = companyList[0]?.address?.toUpperCase() ?: defaultAddress //多重空安全调用和Elvis联合使用
    println("address = $address")
}

fun getStrinLenght3(s: String?): Int = s?.length ?: fail()

//安全转换
open class View(val name: String)

class TextView(name: String, val text: String) : View(name) //后面跟着括号，表明调用的是View的主构造函数，没有括号，则表示之后会使用次构造函数

fun testAsClient() {
    val company = Company("JD", "亦庄")
    testAs(company)
    testAs2(company)

    val tv = TextView("TextView", "I am a message content")
    testAs2(tv)
}

fun testAs(o: Any) {
    val tv = o as TextView
    println("name = ${tv.name}")
}

fun testAs2(o: Any) {
    val tv = o as? TextView ?: TextView("Default TextView", "")
    println("The content of ${tv.name} is = ${tv.text}")
}


//非空断言
fun testNoNullClient() {
    val result = testNoNull("Kotlin")
    println("result = $result")
}

fun testNoNull(s: String?): String {
    val length = s!!.length
    println("$s's length is $length")
    return s!!.toUpperCase()
}

//let函数

fun testLet() {
    val length = getTheBigCompanyInTheWorld().address?.length
    val upperCase = getTheBigCompanyInTheWorld().address?.toUpperCase()
    val letter = getTheBigCompanyInTheWorld().address?.get(0)
    println("address's length is $length , uppercase  = $upperCase, letter = $letter")
}

fun testLet2() {
    getTheBigCompanyInTheWorld().address?.let {
        val upperCase = it.toUpperCase()
        val length = it.length
        val letter = it[0]
        println("address's length is $length , uppercase  = $upperCase, letter = $letter")
    }
}

fun getTheBigCompanyInTheWorld(): Company = Company("JD", "Beijing")


//可空类型的扩展函数

fun CharSequence?.isNullOrEmpty(): Boolean {
    return this == null || this.length == 0
}

fun testNullExtend() {
    val company = Company("JD", null)

    val nullOrEmpty = company.address.isNullOrEmpty()
}

//泛型参数是可空类型

fun testGenericityClient() {
    myPrintln(null)

    printlnStr("")

    val s: String? = ""
    printlnStr2(s)
}

fun printlnStr(s: String) {
    println(s)
}

fun printlnStr2(s: String?) {
    val result = s ?: ""
    println(result)
}

fun <T> myPrintln(t: T) {
    println(t.toString())
}