/*
Method Notations
Scala Objects
Inheritance: Abstract Classes and Traits
Generics
Anonymous Classes
Case Classes
Exceptions
Packaging and Imports
 */


/************* Objects *************/
class Person {

}

object Person {
  val N_EYES = 2
  def name: String = "Ben"
}

val mary = new Person
val john = new Person


println(mary == john)

val p1 = Person
val p2 = Person

println(p1 == p2)

// Generics

class MyList[A] {
  // use the type A
}

val listOfIntegers = new MyList[Int]
val listOfStrings = new MyList[String]
val listOfBoolean = new MyList[Boolean]

// generic methods


// List[A] List[+A] List[-A] List[B >: A] List[A <: Animal]