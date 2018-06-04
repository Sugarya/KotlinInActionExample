@file:JvmName("MyUtils")

package sugarya.chapter4

open class Animal(_name: String) {

    open val name: String

    init {
        println("init")
        this.name = _name
    }

    fun eat(food: String) {
        println("animal is eating $food")
    }

    fun run() {
        println("animal is running")
    }
}

class Animal2(val name: String) {

    init {
        println("init")
    }

    fun eat(food: String) {
        println("animal is eating $food")
    }

    fun run() {
        println("animal is running")
    }
}

class Animal3(val name: String = "Kotlin") {

    init {
        println("init")
    }

    fun eat(food: String) {
        println("animal is eating $food")
    }

    fun run() {
        println("animal is running")
    }
}


open class Person(name: String, val gender: Int, val age: Int) : Animal(name) {

    constructor(name: String) : this(name, 0, 1) {
        println("constructor name = $name, gender = $gender, age = $age")
    }

    constructor(name: String, gender: Int) : this(name, gender, 1) {
        println("constructor name = $name, gender = $gender, age = $age")
    }

    open fun work() {
        println("$name is working")
    }
}

class Person2(name: String = "default", val gender: Int = 0, val age: Int = 1) : Animal(name) {


}

open class Person3(name: String, val gender: Int, val age: Int) : Animal(name) {

    constructor(age: Int) : this("default", 0, age) {
        println("constructor age = $age")
        println("特有的初始化逻辑")
    }

    constructor(name: String, gender: Int) : this(name, gender, 1) {
        println("constructor name = $name, gender = $gender")
        println("特有的初始化逻辑2")
    }

}


open class Colder : Person {

    var language: String = "Java"
        get() {
            return field
        }
        set(value) {
            field = value
        }

    var jobYear: Int
        private set

    constructor(name: String, jobYear: Int) : super(name) {
        this.jobYear = jobYear
    }


    fun switchYear(jobYear: Int) {
        this.jobYear = jobYear
    }


    private fun coding() {
        println("$name is coding with $language")
    }

    override fun work() {
        super.work()
        coding()
    }

}


//**************Kotlin自带的特殊类, 数据类，

data class Point(val x: Int, val y: Int)


open class Car(val name: String) {

    var mileage = 0

    class Board {

        fun setup() {
            println("底盘正常")
        }
    }

    class Wheel(val size: Int) {

        fun setup() {
            println("{$size}轮子正常")
        }
    }

    class Body {

        fun show() {
            println("车壳正常")
        }
    }

    inner class Engine(val fuel: Fuel) {

        fun run(mileage: Int) {
            val result: Int = when (fuel) {
                is Oil -> {
                    fuel.calucate()
                    1
                }
                is Gas -> {
                    println("使用天然气，发动机开始工作")
                    fuel.calucateGas()
                    1
                }
                is Electric -> {
                    fuel.calucateElectric()
                    3
                }
            }
            this@Car.mileage = this@Car.mileage + mileage
        }
    }

    fun work(mileage: Int) {
        Board().setup()
        Wheel(4).setup()
        Body().show()
        Engine(Oil()).run(mileage)
    }

}

sealed class Fuel

class Oil : Fuel() {

    fun calucate(): Float {
        println("使用石油，发动机开始工作")
        return 0.5f
    }
}

class Gas : Fuel() {
    fun calucateGas(): Float {
        return 0.6f
    }
}

class Electric : Fuel() {
    fun calucateElectric(): Float {
        return 0.8f
    }
}


//Object 对象声明，伴生对象，对象表达式

object NameComparator : Comparator<String> {

    override fun compare(o1: String, o2: String): Int {
        return o1.compareTo(o2, true)
    }
}


enum class Color {
    Red, Blue, Black
}

class CooperCar(name: String, val color: Color = Color.Black) : Car(name)

const val wheel: Int = 4

class CooperCarFactory {

    companion object {

        fun newCooperCar(name: String): CooperCar {

            return CooperCar(name)
        }

        fun newRedCooperCar(name: String): CooperCar {
            return CooperCar(name, Color.Red)
        }
    }

}

interface IClick {

    val clickName: String

    fun onClick()

    fun showClickNameLength(): Int {
        return clickName.length
    }
}

class View {

    fun addClick(iClick: IClick) {
        iClick.onClick()
    }
}

fun countClick(view: View) {
    var clickCount = 0

    val iClick: IClick = object : IClick {
        override val clickName: String
            get() = "View"

        override fun onClick() {
            clickCount++
        }
    }


    iClick.showClickNameLength()

    view.addClick(iClick)

}


