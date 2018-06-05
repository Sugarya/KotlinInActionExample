package sugarya.chapter6

fun main(args: Array<String>) {
//    testNoNullClient()
//    testAnyAndUnit()
//    testNullType()
    testAsClient()
}


//Kotlin基本类型和转化

fun testBasicType(){

    //Kotlin层面上不区分基本数据类型和包装类型，转成Java时有不同
    val bb: Byte = 127
    val boolean = false
    val aa: Char = '+'

    var i: Int = 21_4748_3647 //10位
    var l: Long = 922_3372_0368_5477_5807L  //19位 //不存在小写的l


    val f: Float = 123f
    val f2: Float = 124F

    val d: Double = 12.5

    val a16 = 0x123ef3
    val b16 = 0XcCCef3

    val c2 = 0b1010
    val cc2 = 0B11001

    val b: Byte = 1
    val eas = b + 1L //Long + Byte => Long , eas为Long类型;

    //小范围不能自动转成大范围数据
    val index: Int = 11
//    val dd: Double = index


    fun foo(l: Long) {
        println(l)
    }
    foo(44) //单独写44可能是byte，int long，这里智能推断44会被认为是long

    //字符串转基本数据类型
    val p = "44".toInt()
    val p2 = "44".toLong()
    val p3 = "44".toFloatOrNull()

    //Kotlin的异常处理，1. 不存在方法上throws  2.不区分受检异常和运行时异常，统统按运行时异常处理
    try {
        val p4 = "44".toDouble()
    }catch (e: NumberFormatException){
        e.printStackTrace()
    }

    val translate = l.toInt().toLong().toFloat().toDouble().toInt()
}


//Any Unit

fun testAnyAndUnit(){
    val any: Any = testBasicType()

    val a: Unit = testBasicType()
    println("hashcode = ${a.hashCode()}, toString = ${a.toString()}")
}

// Nothing
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

////////////空安全调用 ?.

open class Company(val name: String, val address: String?)

fun testNullType() {
    val company = Company("京东", null)

    val length = company.address?.length
    println("length = $length")
}

//Elvis运算法 ?:
fun testElivis(){
    val company = Company("京东", null)

    val length = company.address?.length?:0
    println("length = $length")
}

fun testElvis2(list: List<Company?>?, defaultAddress: String?) {
    val companyList = list ?: throw IllegalArgumentException("null list")

    val defaultAdd = defaultAddress ?: ""
    val address = companyList[0]?.address?.toUpperCase() ?: defaultAdd //多重空安全调用和Elvis联合使用
    println("address = $address")
}

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
fun testNoNull(s: String?): String {
    val length = s!!.length
    println("$s's length is $length")
    return s!!.toUpperCase()
}

fun testNoNullClient() {
    val result = testNoNull("Kotlin")
    println("result = $result")
}

//对 List<Company> 惰性初始化
class BookManage() {
    private var _companyList: List<Company>? = null

    val companyList: List<Company>
        get() {
            if (_companyList == null) {
                _companyList = arrayListOf(Company("JD", "Beijing"))
            }
            return _companyList!!
        }
}

//let函数  把可空类型 转成 非空类型
fun getTheBigCompanyInTheWorld(): Company = Company("JD", "Beijing")

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


//可空类型的扩展函数

fun CharSequence?.isNullOrEmpty(): Boolean {
    return this == null || this.length == 0
}

fun CharSequence?.theLength(): Int =
        if(this == null){
            0
        }else{
            this.length
        }

fun testNullExtend() {
    val length = getTheBigCompanyInTheWorld().address.theLength()
    println("length = $length")
}

//特殊情况：泛型参数是可空类型
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
