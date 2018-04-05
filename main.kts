// Explore a simple class

println("UW Homework: Simple Kotlin")

// write a "whenFn" that takes an arg of type "Any" and returns a String
fun whenFn (arg: Any) : String {
    when (arg) {
        "Hello" -> return "world"
        0 -> return "zero"
        1 -> return "one"
        in 2..10 -> return "low number"
        is Int -> return "a number"
        is String -> return "Say what?"
        else -> return "I don't understand"
    }
}

// write an "add" function that takes two Ints, returns an Int, and adds the values
fun add (x: Int, y: Int) : Int {
    return x + y;
}

// write a "sub" function that takes two Ints, returns an Int, and subtracts the values
fun sub (x: Int, y: Int) : Int {
    return x - y;
}

// write a "mathOp" function that takes two Ints and a function (that takes two Ints and returns an Int), returns an Int, and applies the passed-in-function to the arguments
fun mathOp (x: Int, y: Int, f : (Int, Int) -> Int) : Int {
    return f(x, y);
}
// write a class "Person" with first name, last name and age
class Person (f: String, l: String, a: Int) {
    var firstName : String
    var lastName : String
    var age: Int
    
    val debugString : String
        get() = "[Person firstName:" + this.firstName + " lastName:" + this.lastName + " age:" + this.age + "]";

    init {
        firstName = f
        lastName = l
        age = a
    }

    fun equals(p : Person) : Boolean {
        return this.firstName == p.firstName && this.lastName == p.lastName && this.age == p.age;
    }

    override fun hashCode() : Int {
        return this.firstName.hashCode() + this.lastName.hashCode() * 31;
    }
}

// write a class "Money"
class Money (a: Int, c : String){
    var amount : Int
    var currency : String

    init {
        if (a >= 0){
            amount = a;
        } else throw AssertionError("Amount < 0");

        if (c == "USD" || c == "CAN" || c == "EUR" || c == "GBP"){ 
            currency = c;
        } else throw AssertionError("Invalid Currency");
    }

    fun convert (cur : String) : Money {
        var temp  : Int = 0;

        when (this.currency) {
            "USD" -> temp = amount
            "EUR" -> temp = (amount * 10) / 15;
            "CAN" -> temp = (amount * 12) / 15
            "GBP" -> temp = (amount * 10) / 5
        }

        when (cur) {
            "EUR" -> temp = (temp * 15) / 10
            "CAN" -> temp = (temp * 15) / 12
            "GBP" -> temp = (temp * 5) / 10
        }

        return Money(temp, cur);
    }

    operator fun plus(x : Money) : Money {
        return Money(this.amount + x.convert(this.currency).amount, this.currency);
    }
}

// ============ DO NOT EDIT BELOW THIS LINE =============

print("When tests: ")
val when_tests = listOf(
    "Hello" to "world",
    "Howdy" to "Say what?",
    "Bonjour" to "Say what?",
    0 to "zero",
    1 to "one",
    5 to "low number",
    9 to "low number",
    17.0 to "I don't understand"
)
for ((k,v) in when_tests) {
    print(if (whenFn(k) == v) "." else "!")
}
println("")

print("Add tests: ")
val add_tests = listOf(
    Pair(0, 0) to 0,
    Pair(1, 2) to 3,
    Pair(-2, 2) to 0,
    Pair(123, 456) to 579
)
for ( (k,v) in add_tests) {
    print(if (add(k.first, k.second) == v) "." else "!")
}
println("")

print("Sub tests: ")
val sub_tests = listOf(
    Pair(0, 0) to 0,
    Pair(2, 1) to 1,
    Pair(-2, 2) to -4,
    Pair(456, 123) to 333
)
for ( (k,v) in sub_tests) {
    print(if (sub(k.first, k.second) == v) "." else "!")
}
println("")

print("Op tests: ")
print(if (mathOp(2, 2, { l,r -> l+r} ) == 4) "." else "!")
print(if (mathOp(2, 2, ::add ) == 4) "." else "!")
print(if (mathOp(2, 2, ::sub ) == 0) "." else "!")
print(if (mathOp(2, 2, { l,r -> l*r} ) == 4) "." else "!")
println("")


print("Person tests: ")
val p1 = Person("Ted", "Neward", 47)
print(if (p1.firstName == "Ted") "." else "!")
p1.age = 48
print(if (p1.debugString == "[Person firstName:Ted lastName:Neward age:48]") "." else "!")
println("")

print("Money tests: ")
val tenUSD = Money(10, "USD")
val twelveUSD = Money(12, "USD")
val fiveGBP = Money(5, "GBP")
val fifteenEUR = Money(15, "EUR")
val fifteenCAN = Money(15, "CAN")
val convert_tests = listOf(
    Pair(tenUSD, tenUSD),
    Pair(tenUSD, fiveGBP),
    Pair(tenUSD, fifteenEUR),
    Pair(twelveUSD, fifteenCAN),
    Pair(fiveGBP, tenUSD),
    Pair(fiveGBP, fifteenEUR)
)
for ( (from,to) in convert_tests) {
    print(if (from.convert(to.currency).amount == to.amount) "." else "!")
}
val moneyadd_tests = listOf(
    Pair(tenUSD, tenUSD) to Money(20, "USD"),
    Pair(tenUSD, fiveGBP) to Money(20, "USD"),
    Pair(fiveGBP, tenUSD) to Money(10, "GBP")
)
for ( (pair, result) in moneyadd_tests) {
    print(if ((pair.first + pair.second).amount == result.amount &&
              (pair.first + pair.second).currency == result.currency) "." else "!")
}
println("")
