package sugarya.chapter4


fun main(args: Array<String>) {
    
}

private fun testClass(){
    val person = Person("Kotlin")
    val person2 = Person2(gender = 1, age = 10, name = "Kotlin")

}

private fun testFactory(){
    CooperCarFactory.newCooperCar("Mini Cooper")
    CooperCarFactory.newCooperCar("Mini Cooper")
}

