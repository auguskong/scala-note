/*
Function
Anonymous Functions
Higher-Order-Functions and Curries
map, flatMap, filter and for-comprehensions
Collections
  Sequences
  Tuples and Maps
  Options
Handling Failure
Pattern Matching
 */


val list = List(1, 2, 3)

println(list.head)
println(list.tail)

// map
list.map(_ + 1)
print(list)
list.map(_ => 2)
print(list)

// filter
println(list.filter(_ % 2 == 0))

// flatMap
val toPair = (x: Int) => List(x, x+1)
println(list.flatMap(toPair))

val numbers = List(1,2,3)
val chars = List("sb", "cs", "opt")
val colors = List("black", "white")

val f = numbers.filterNot(_ < 2)
println(chars.head)
val l = List(List(1,2), List(3,4)).flatten
val res = chars.flatMap(c => "" + c + c)

val combinations = numbers.flatMap(n => List(1))
val combinations = numbers.map(n => chars.map(c => "" + c + n))

val combinations = numbers.map(n => chars.flatMap(c => "" + c + n))
val combinations = numbers.flatMap(n => chars.flatMap(c => "" + c + n))
val combinations = numbers.flatMap(n => chars.map(c => "" + c + n))
val combinations = numbers.flatMap(n => chars.flatMap(c => colors.map(color => "" + c + n + "-" + color)))
val combinations = numbers.flatMap(n => chars.map(c => colors.map(color => "" + c + n + "-" + color)))
val cl = colors.map(color => "" + "sb" + 1 + "-" + color)
val cl2 = chars.map(c => cl)
val cl3 = numbers.map(n => cl2)


val forCombinations = for {
  n <- numbers
  c <- chars
  color <- colors
} yield "" + c + n + "-" + color
println(forCombinations)

// Option

val someOption: Option[Int] = Some(56)
val noneOption: Option[Int] = None

