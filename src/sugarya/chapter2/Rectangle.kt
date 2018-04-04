package sugarya.chapter2

class Rectangle(val width: Int, val height: Int){
    val isSquare get() = width == height
    var square = false
        get() {
            return width == height
        }
    set(value) {
        field
    }
}