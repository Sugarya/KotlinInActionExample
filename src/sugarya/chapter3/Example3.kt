
@file:JvmName("Utils")

package sugarya.chapter3

import sugarya.chapter4.wheel


fun main(args: Array<String>) {
//    testTripleQuote()
    testExtendFunction()
    wheel
}

fun testTripleQuote(){
    val msg = """
                   _ooOoo_
                  o8888888o
                  88" . "88
                  (| -_- |)
                  O\  =  /O
               ____/`---'\____
             .'  \\|     |//  `.
            /  \\|||  :  |||//  \
           /  _||||| -:- |||||-  \
           |   | \\\  -  /// |   |
           | \_|  ''\---/''  |   |
           \  .-\__  `-`  ___/-. /
         ___`. .'  /--.--\  `. . __
      ."" '<  `.___\_<|>_/___.'  >'"".
     | | :  `- \`.;`\ _ /`;.`/ - ` : | |
     \  \ `-.   \_ __\ /__ _/   .-` /  /
======`-.____`-.___\_____/___.-`____.-'======
                   `=---='
^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
             佛祖保佑       永无BUG
    """
    println(msg)
}

fun testArrayAndList(){
    val list = listOf<String>("a") //Array.asList(T...)
    val map = mapOf<String, Number>("a" to 1)
    val set = setOf<String>("a")

    val list2 = arrayListOf<String>("a")
    val filterList = list2.filter {
        it.startsWith("http://")
    }

    val map2 = hashMapOf<String, Number>("a" to 1)
    val map3 = linkedMapOf<String, Number>("a" to 1)
    val map4 = sortedMapOf<String, Number>("a" to 1)

    val set2 = hashSetOf<String>("a")
    val set3 = linkedSetOf<String>("a")
    val set4 = sortedSetOf<String>("a")

    val mutableList = mutableListOf<String>("a", "b")
    val mutableSet = mutableSetOf<String>("a")
    val mutableMap = mutableMapOf<String, Number>("a" to 1)

    val listOfNotNull = listOfNotNull(1, null, 2, null)


    mutableList.add("c")

    testList(list2)
    testList(mutableList)

    //创建 数组操作

    val intArrayOf = intArrayOf(1, 2, 3)
    val arrayOfNulls = arrayOfNulls<Int>(4)

//    val arrayOf = arrayOf(Computer())

    val array4 = Array<Int>(5){
        it + 1
    }


}

fun testList(list: MutableList<String>){

}

enum class LANGUAGE{
    ENGLISH
}

class Coder(val name: String){

    infix fun speaks(language: LANGUAGE): Coder{
        return this
    }

    infix fun speaks(language: String): Coder{
        return this
    }

    infix fun on(season: String){

    }

    fun Computer.writeCode(){

    }

}

fun testInfix(){
    val coder = Coder("Oliver")
    coder.speaks(LANGUAGE.ENGLISH)

    coder speaks English on Summer

    12 shl 2
}

val English: String = "english"

var English2: String = ""

const val Summer: String = "Summer"

fun Computer.startUp(){
    println("$name start up")
}

fun testExtendFunction(){
    val computer = Computer("惠普电脑")
    computer.startUp()

    Computer("")

}