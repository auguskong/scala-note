// 第五章核心内容 两个语言特性 generics and functions => functors and monads
/*
sealed trait IntList {
  def length: Int =
    this match {
      case End => 0
      case Pair(hd, tl) => 1 + tl.length
    }
  def double: IntList =
    this match {
      case End => End
      case Pair(hd, tl) => Pair(hd * 2, tl.double)
    }
  def product: Int =
    this match {
      case End => 1
      case Pair(hd, tl) => hd * tl.product
    }
  def sum: Int =
    this match {
      case End => 0
      case Pair(hd, tl) => hd + tl.sum
    }
}
case object End extends IntList
final case class Pair(head: Int, tail: IntList) extends IntList
*/

// 上面Code的两个问题
// 1. 只能存Int  解决方案: 泛型 generics abstract over types
// 2. a lot of repetition  e.g. case End => ..., case Pair() =>  解决方案: 函数functions abstract over methods

// 5.1 Generics

// 5.1.1 Pandora's Box
final case class Box[A](value: A) // 直接就将传入的参数作为内置的field了吗

Box(2)

res0.value

Box("hi")

res2.value

// We don’t care what type is stored in the box, but we want to make sure we preserve that type when we get the value out of the box.
// To do this we use a generic type.
// type 是由输入的参数决定的 [A] is called a type parameter

def generic[A](in: A): A = in
generic[String]("foo")
generic(1)

// case class 为什么有type 其本身不就是一种type吗?

// 5.1.2 Generic Algebraic Data Types


sealed trait Result[A]
case class Success[A](result: A) extends Result[A]
case class Failure[A](reason: String) extends Result[A]

sealed trait IntList
case object End extends IntList
final case class Pair(head: Int, tail: IntList) extends IntList

val userIdList = List(1, 2, 3)
case class User(id: Int, firstname: String, lastname: String)

val user1 = new User(1, "ab", "jj")
val userList = userIdList.map(id => new User(id, "ab", "jle"))

userList.map(u => println(u))