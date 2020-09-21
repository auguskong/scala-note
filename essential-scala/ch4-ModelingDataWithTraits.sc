import java.util.Date
// Visitor example


// trait is the abstraction over classes
// two or more classes can be considered the same, and thus both implement the same operations
// traits allow us to express that multiple classes share a common super-type



// algebraic datatype

// structural recursion


// 4.1.1 An Example of Traits

// 未注册用户 和 已注册用户 用户是什么 用户共有的方法 和 独有的特殊的方法分别是什么 大部分共有的方法如何share, 如何找到共同点来抽取出来 这是OOD的核心思想?
// 为什么已经有了class的情况下还需要 trait呢？ 类的抽象如何理解 和对象的抽象是类有什么区别?  duplication duplication duplication 尽量避免duplication

// 不同的类也会share common type这些common type就是我们需要解决的


sealed trait Visitor {
  def id: String
  def createdAt: Date
}

case class Anonymous(id: String, createdAt: Date = new Date()) extends Visitor

case class User(id: String, screenName: String, createdAt: Date = new Date()) extends Visitor {
  def checkIfLikeThisGame(game: String): Boolean = {
    if (game == "civilization")
      true
    else
      false
  }
}

val user1 = new User("auguskong", "agujdke")

// 4.1.2 Traits Compared to Classes Trait和Class的区别
// 1. trait 没有构造器
// 2. trait 可以定义抽象方法 只有name 和 type signature 没有具体实现, 在class通过override来实现即可

// def 和 val的区别是什么?

// 4.1.4 Exercises

// 4.1.4.1 Cats, and More Cats
trait Feline {
  def colour: String
  def sound: String
}

case class Tiger (colour: String) extends Feline {
  def sound = "roar"
}

case class Panther (colour: String) extends Feline {
  def sound = "roar"
}

case class Cat (colour: String) extends Feline {
  def sound = "meow"
}

// 为什么这里不能直接定义maneSize Int? 如果将sound: String 放在了case class() 当中的话 就不能在case class 内部再重新定义了?
case class Lion (colour: String, maneSize: Int) extends Feline {
  val sound = "roar"
}

// trait 可以 extends trait
trait BigCat extends Feline {
  override val sound = "roar"
}

// 4.2.2.2 The Color and the Shape
sealed trait Color {
  def red: Int
  def green: Int
  def blue: Int

  def isLight = (red + green + blue).toDouble / 3.0 > 0.5
  def isDark = !isLight
}

// 这里作为subtype的三个颜色都是使用的case object 因为不需要使用到构造器
case object Red extends Color {
  val red = 210
  val blue = 0
  val green = 0
}

case object Yellow extends Color {
  val red = 255
  val blue = 255
  val green = 0
}

case object Pink extends Color {
  val red = 252
  val blue = 15
  val green = 200
}

// 4.1.4.2 Shaping Up With Traits
// 4.1.4.3 Shaping Up 2 (Da Streets)
// 4.2.2.1 Printing Shapes 添加sealed 关键字
// 4.2.2.2 The Color and the Shape 添加color类型
sealed trait Shape {
  def sides: Int
  def color: Color
  def perimeter: Double
  def area: Double
}

// 这里为什么是sealed ?
sealed trait Rectangular extends Shape {
  def width: Double
  def height: Double
  def color: Color
  override val sides = 4
  val perimeter = width * 2 + height * 2
  val area = width * height
}

// 不能写成color: Red 因为Red现在不是一种type了
// Color 作为一种Trait可以统一传入到一个function当中
case class Circle(radius: Double, color: Color) extends Shape {
  val sides = 0
  val perimeter = 2 * math.Pi * radius
  val area = math.Pi * radius * radius
}

case class Rectangle(width: Double, height: Double, color: Color) extends Rectangular

case class Square(size: Double, color: Color) extends Rectangular {
  val width = size
  val height = size
}

val rec = Rectangle(12, 3, Red)
rec.perimeter

// 4.2 This or That and Nothing Else: sealed traits => we must define all of its subtypes in the same file
// 如果能够保证所有继承trait的类都能在当前文件中写，就可用sealed trait
// 定义好了sealed trait 之后就可以让编译器在pattern matching过程中帮忙进行额外的检查，如果缺少了某一种类会报错


/**
// Warning: match may not be exhaustive. It would fail on the following input: Anonymouse(_, _) v match{ ... }
def missingCase(v: Visitor) =
  v match {
    case User(_, _, _) => "Got a user"
  }
*/

// 对于extends了sealed trait 的subtype 我们仍然可以在其他的文件中extend
// 但是如果标记为final了 那么subtype也没有办法在其他文件中extend

// Sealed Trait Pattern
sealed trait Visitor { /* ... */ }
final case class User(/* ... */) extends Visitor
final case class Anonymous(/* ... */) extends Visitor


// 4.2.2.1 Printing Shapes
// 4.2.2.2 添加打印color
object Draw {
  def apply(s: Shape): String = s match {
    case Circle(radius, color) =>
      s"A ${color} circle of radius ${radius}cm"
    case Rectangle(width, height, color) =>
      s"A ${color} rectangle of width ${width}cm and height ${height}cm"
    case Square(size, color) =>
      s"A ${color} square of size ${size}cm"
  }

  def apply(color: Color): String = color match {
    case Red    => "red"
    case Yellow => "yellow"
    case Pink   => "pink"
    case color  => if(color.isLight) "light" else "dark"
  }
}

// 4.2.2.4 A Short Division Exercise 通过使用type来添加限制
// 这个trait需要判断分子分母的数值吗?

sealed trait DivisionResult

case class Finite(value: Int) extends DivisionResult
case object Infinite extends DivisionResult

object divide {
  def apply(numerator: Int, denominator: Int): DivisionResult = {
    // Int / Int => Int
    if (denominator == 0) Infinite else Finite(numerator/denominator)
  }
}
divide(1, 0) match {
  case Finite(value) => s"It's finite: ${value}"
  case Infinite      => s"It's infinite"
}


// 4.3 Modeling Data With Traits

// Product Type Pattern
// A has a b(Type B) and c(Type C)
/*
case class A(b: B, c: C)

trait A {
  def b: B
  def c: C
}
 */

// 4.4 Sum Type Pattern
// A is a B or C   sealed trait / final case class pattern.
/*
sealed trait A
final case class B() extends A
final case class C() extends A
 */

// “is-a and” pattern
/*
trait B
trait C
trait A extends B with C
 */

// “has-a or” pattern
/*
trait A {
  def d: D
}

需要一个type D 作为中转 D is B or C
sealed trait D
final case class B() extends D
final case class C() extends D


A is a D or E, D has B, E has C
sealed trait A
final case class D(b: B) extends A
final case class E(c: C) extends A
 */

// 4.4.4.1 Stop on a Dime
sealed trait TrafficLight
case object Red extends TrafficLight
case object Yellow extends TrafficLight
case object Green extends TrafficLight


// 4.4.4.2 Calculator 如果有传入参数就必须用case class + 构造器了
sealed trait CalculationResult
case class SuccessResult(value: Int) extends CalculationResult
case class FailureResult(message: String) extends CalculationResult

// 4.4.4.3 Water, Water, Everywhere
sealed trait Source
case object Well extends Source
case object Spring extends Source
case object Tap extends Source
case class BottledWater(size: Int,  source: Source, carbonated: Boolean)

// 4.5 Working With Data


// Structural recursion is the precise opposite of the process of building an algebraic data type.
// 需要对于对封装好的class的内部进行拆分来得到我们需要的数据 A has B and C, 我们define了A must break it into B and C

// 4.5.1 Structural Recursion using Polymorphism (OO)
sealed trait A {
  def foo: String
}

final case class B() extends A {
  def foo: String =
    "It's B!"
}

final case class C() extends A {
  def foo: String =
    "It's C!"
}

// 4.5.2 Structural Recursion using Pattern Matching (FP)
/*
def f(a: A): F =
  a match {
    case A(b, c) => ???
  }

def f(a: A): F =
  a match {
    case B() => ???
    case C() => ???
  }
 */

// 4.5.3 A Complete Example


sealed trait Food
case object Antelope extends Food
case object TigerFood extends Food
case object Licorice extends Food
final case class CatFood(food: String) extends Food

sealed trait FelineV2 {
  def dinner: Food
}
final case class LionV2() extends FelineV2 {
  def dinner: Food =
    Antelope
}
final case class TigerV2() extends FelineV2 {
  def dinner: Food =
    TigerFood
}
final case class PantherV2() extends FelineV2 {
  def dinner: Food =
    Licorice
}
final case class CatV2(favouriteFood: String) extends FelineV2 {
  def dinner: Food = CatFood(favouriteFood)
}

// pattern match in base trait
sealed trait FelineV2 {
  def dinner: Food =
    this match {
      case LionV2() => Antelope
      case TigerV2() => TigerFood
      case PantherV2() => Licorice
      case CatV2(favouriteFood) => CatFood(favouriteFood)
    }
}

// pattern match in external object
object Dinner {
  def dinner(feline: FelineV2): Food =
    feline match {
      case LionV2() => Antelope
      case TigerV2() => TigerFood
      case PantherV2() => Licorice
      case CatV2(favouriteFood) => CatFood(favouriteFood)
    }
}
/***
 * Take home points
 */

/* 4.1 trait
trait TraitName {
  declarationOrExpression ...
}
case class Name(...) extends TraitName {
  ...
}
 */

/* 4.2 sealed trait
sealed trait TraitName { ... }
final case class Name(...) extends TraitName

main advantages of this pattern:
1. the compiler will warn if we miss a case in pattern matching; and
2. we can control extension points of sealed traits and thus make stronger guarantees about the behaviour of subtypes.
*/


/* 4.4
 product and sum types
*/

/*
 Q & A
 Pattern Match的对象必须是case class??
 */
