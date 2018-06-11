package sugarya._chapter07

import java.beans.PropertyChangeEvent
import java.beans.PropertyChangeListener
import java.beans.PropertyChangeSupport
import kotlin.properties.Delegates
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

fun main(args: Array<String>) {
//    testViewClickListener()

//    testlateinit()
//    testLazy()

//    testObserverField()
//    testDelegateFieldForKotlin()
//    testObserverFieldForKotlin()

//    testVetoableFieldForKotlin()

    testDelegateMap()
}

//*********委托类
interface OnClickListener{
    fun onClick()

    fun onLongClick()
}

class ViewClickDelegate : OnClickListener{

    override fun onClick(){
        println("ViewClickDelegate onClick")
    }

    override fun onLongClick() {
        println("ViewClickDelegate onLongClick")
    }

}

class View(val name: String, onClickListener: OnClickListener) : OnClickListener by onClickListener{

    override fun onLongClick() {
        println("$name onLongClick")
    }
}

fun testViewClickListener(){
    val view = View("View", ViewClickDelegate())
    view.onClick()
    view.onLongClick()
}


//*********委托属性
open class Food(val name: String) {

    override fun toString(): String {
        return "[$name]"
    }
}

//延迟初始化
class Container(val name: String) {

    lateinit var foodList: List<Food>

}

fun testlateinit() {
    val container = Container("碗")

    println("当前最新的食物是 辅食")

    container.foodList = arrayListOf(Food("辅食"))
    println("foodlist 更新为 ${container.foodList[0]}")

    println("\n当前最新的食物是 米饭")
    container.foodList = arrayListOf(Food("米饭"))
    println("foodlist 更新为 ${container.foodList[0]}")
}

//惰性初始化
class Container2(val name: String) {
    private var _foodList: List<Food>? = null
    val foodList: List<Food>
        get() {
            if (_foodList == null) {
                _foodList = arrayListOf(Food("米糊"))
            }
            return _foodList!!
        }
}

class Container3(val name: String) {
    private var _foodMap = hashMapOf<String, Food>()
    val food: Food
        get() {
            if (!_foodMap.containsKey("food")) {
                _foodMap["food"] = Food("米糊")
            }
            return _foodMap["food"]!!
        }
}

class Container4(val name: String) {
    val food: Food by lazy{
        Food("米糊")
    }
}

//指定锁
class Container5(val name: String) {
    val food: Food by lazy(Container5::class){
        Food("米糊")
    }
}

//默认 线程安全 SYNCHRONIZED
//PUBLICATION，同步锁不是必需的，允许多个线程同时执行
class Container6(val name: String) {
    val food: Food by lazy(LazyThreadSafetyMode.SYNCHRONIZED){
        Food("米糊")
    }
}

fun testLazy() {
    val container: Container4 = Container4("碗")
    println("${container.food}, ${container.food.hashCode()}")
}


data class Book(val name: String)

//Koltin观察者监听
class Shelf(val name: String, _book: Book) {

    private val propertyChange: PropertyChangeSupport = PropertyChangeSupport(this)
    var book: Book = _book
        set(value) {
            val oldBook = field
            field = value
            propertyChange.firePropertyChange("book", oldBook, value)
        }


    fun addBookChangeListener(propertyChangeListener: PropertyChangeListener) {
        propertyChange.addPropertyChangeListener("book", propertyChangeListener)
    }

    fun removeBookChangeListener(propertyChangeListener: PropertyChangeListener) {
        propertyChange.removePropertyChangeListener("book", propertyChangeListener)
    }
}

//进一步Kotlin封装
open class BasePropertyChange {

    val propertyChange = PropertyChangeSupport(this)

    protected fun addChangeListener(key: String, propertyChangeListener: PropertyChangeListener) {
        propertyChange.addPropertyChangeListener(key, propertyChangeListener)
    }

    protected fun removeChangeListener(key: String, propertyChangeListener: PropertyChangeListener) {
        propertyChange.removePropertyChangeListener(key, propertyChangeListener)
    }
}

class Shelf_2(val name: String, _book: Book) : BasePropertyChange() {

    var book: Book = _book
        set(value) {
            val oldBook = field
            field = value
            propertyChange.firePropertyChange("book", oldBook, value)
        }

    fun addBookChangeListener(propertyChangeListener: PropertyChangeListener) {
        addChangeListener("book", propertyChangeListener)
    }

    fun removeBookChangeListener(propertyChangeListener: PropertyChangeListener) {
        removeChangeListener("book", propertyChangeListener)
    }
}

class BookDelegate(_book: Book, val propertyChange: PropertyChangeSupport) {

    var field: Book = _book

    fun getValue(): Book = field

    fun setValue(value: Book) {
        val oldBook = field
        field = value
        propertyChange.firePropertyChange("book", oldBook, value)
    }
}

class Shelf2(val name: String, _book: Book) : BasePropertyChange() {

    val _bookDelegate: BookDelegate = BookDelegate(_book, propertyChange)
    var book: Book
        set(value) {
            _bookDelegate.setValue(value)
        }
        get() = _bookDelegate.getValue()

    fun addBookChangeListener(propertyChangeListener: PropertyChangeListener) {
        addChangeListener("book", propertyChangeListener)
    }

    fun removeBookChangeListener(propertyChangeListener: PropertyChangeListener) {
        removeChangeListener("book", propertyChangeListener)
    }
}

fun testObserverField() {
    val shelf = Shelf2("书架", Book("Think in java"))
    shelf.addBookChangeListener(object : PropertyChangeListener {
        override fun propertyChange(evt: PropertyChangeEvent?) {
            val oldBook = evt?.oldValue as Book
            val newBook = evt.newValue as Book

            println("old book = $oldBook , new book = $newBook")
        }
    })

    shelf.book = Book("Kotlin in action")
}


//通过委托属性机制 封装
class BookDelegate2(_book: Book, val propertyChange: PropertyChangeSupport) {

    var field: Book = _book

    operator fun getValue(shelf3: Shelf3, prop: KProperty<*>): Book = field

    operator fun setValue(shelf3: Shelf3, prop: KProperty<*>, newValue: Book) {
        val oldBook = field
        field = newValue
        propertyChange.firePropertyChange("book", oldBook, newValue)
    }
}

class Shelf3(val name: String, _book: Book) : BasePropertyChange() {

    var book: Book by BookDelegate2(_book, propertyChange)


    fun addBookChangeListener(propertyChangeListener: PropertyChangeListener) {
        addChangeListener("book", propertyChangeListener)
    }

    fun removeBookChangeListener(propertyChangeListener: PropertyChangeListener) {
        removeChangeListener("book", propertyChangeListener)
    }
}

//借助 ReadWriteProperty
class BookDelegate3(var field: Book, val propertyChange: PropertyChangeSupport) : ReadWriteProperty<Shelf3_1, Book> {

    override fun getValue(thisRef: Shelf3_1, property: KProperty<*>): Book {
        return field
    }

    override fun setValue(thisRef: Shelf3_1, property: KProperty<*>, value: Book) {
        val oldBook = field
        field = value
        propertyChange.firePropertyChange("book", oldBook, value)
    }
}

class Shelf3_1(val name: String, _book: Book) : BasePropertyChange() {

    var book: Book by BookDelegate3(_book, propertyChange)


    fun addBookChangeListener(propertyChangeListener: PropertyChangeListener) {
        addChangeListener("book", propertyChangeListener)
    }

    fun removeBookChangeListener(propertyChangeListener: PropertyChangeListener) {
        removeChangeListener("book", propertyChangeListener)
    }
}


fun testDelegateFieldForKotlin() {
    val shelf = Shelf3_1("书架", Book("Think in java"))

    shelf.addBookChangeListener(object : PropertyChangeListener {
        override fun propertyChange(evt: PropertyChangeEvent?) {
            val oldBook = evt?.oldValue as Book
            val newBook = evt?.newValue as Book

            println("Kotlin委托 old book is $oldBook, and new book is $newBook")
        }
    })

    shelf.book = Book("Kotlin in action!")
}

//通过Kotlin自带的Delegates.observable实现
class Shelf4(val name: String, _book: Book) {

    var book: Book by Delegates.observable(_book, {property, oldValue, newValue ->
        println("The old book's name is \"${oldValue.name}\", and the new book's name is \"${newValue.name}\"")
    })
}

fun testObserverFieldForKotlin(){
    val shelf = Shelf4("书架", Book("think in java"))
    shelf.book = Book("Kotlin in action")
}

/////实现Constrain属性
class Shelf5(val name: String, val book: Book ,_year: Int) {
    var year: Int by Delegates.vetoable(_year, {property, oldValue, newValue ->
        newValue <= 99
    })
}

fun testVetoableFieldForKotlin(){
    val shelf = Shelf5("书架", Book("think in java"), 0)
    shelf.year = 200
    println("current book is ${shelf.year}")

    shelf.year = 20
    println("current book is ${shelf.year}")
}

//by map delegation
class Fruit(name: String) : Food(name){

    private val attributeMap = HashMap<String, String>()

    val color: String by attributeMap

    val size: String by attributeMap

    fun setAttributeMap(name: String, value: String){
        attributeMap.put(name, value)
    }
}

fun testDelegateMap(){
    val fruit = Fruit("西瓜")

    fruit.setAttributeMap("color", "绿色")
    fruit.setAttributeMap("size", "2kg")

    println("color = ${fruit.color}, size = ${fruit.size}")
}