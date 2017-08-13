//: Playground - noun: a place where people can play



//README first:

//This playground is used to practice the swift language guide (https://developer.apple.com/library/content/documentation/Swift/Conceptual/Swift_Programming_Language/TheBasics.html#//apple_ref/doc/uid/TP40014097-CH5-ID309)
//each chapter is so indicated on the comments, useful notes are made from within the relevant code snippets.

//for example:
/**
 Control Flow
 **/
//useful notes w.r.t Control glow


//please NOTE that the above rules are not enforced till the section control flow. 
//Hence any code snippets before control flow are to be retrofitted.


import Cocoa


var i = 42

print(i)

var f:Float
f = 3.151693
print(f)

var varA = "Godzilla"
var varB = 1000.00

var str:String? = nil
if str != nil {
    print(str)
}else {
    print("str has nil value")
}



var myString:String?

myString = "Hello, Swift!"

if let yourstr = myString {
    print("yourstr has value \(yourstr)")
}
else{
    print("yourstr doesn't have a value")
}


let s = 42
print(s)
let pi:Float = 3.1415926
print(pi)

let 你好 = "你好世界"
print(你好)

var str2 = String("Hello world!")
print(str2!)
var stra = "hello world"
stra += "-------readers------"
print(stra)



var varA2   = 20
let constA = 100
var varC:Float = 20.0

var stringA = "\(varA2) times \(constA) is equal to \(varC * 100)"
print( stringA )

varA   = "Hello, Swift!"

print( "\(varA), length is \(varA.characters.count)" )


varA   = "Hello, Swift!"
var varD   = "Hello, World!"

if varA == varD {
    print( "\(varA) and \(varD) are equal" )
}else {
    print( "\(varA) and \(varD) are not equal" )
}


let char1:Character = "a"
print("value of char1 is \(char1)")

var ch:Character
for ch in "hello".characters {
print(ch)
}


var someInts = Array(repeating: 0, count: 3)
var anotherthreeints = Array(repeating: 4, count: 3)
var result  = someInts + anotherthreeints
print(result)


var shoppinglist : [String] = ["Eggs","Milk"]
print(shoppinglist)
print("the items number in the shoppinglist is \(shoppinglist.count)")
if shoppinglist.isEmpty {
    print("shopping list is empty")
}
else{
    print("shoppinglist is not empty")
}
shoppinglist.append("Flour")
shoppinglist += ["Baking powder"]
print(shoppinglist)
shoppinglist[1...3] = ["banana","apple"]
print(shoppinglist)
shoppinglist.insert("maple syrup", at: 0)
print(shoppinglist)

let maplesyrup = shoppinglist.remove(at: 0)
print(shoppinglist)
let apple = shoppinglist.removeLast()
print(shoppinglist)

for (index, value) in shoppinglist.enumerated(){
    print("\(index+1),\(value)")
}


var set = Set<Character>()
set.insert("a")
print(set)
set = []
print(set)
var favoriteGenres = ["Rockrolling","Classical","Pop"]
print(favoriteGenres)
favoriteGenres.insert("Jazz", at: 0)

print(favoriteGenres)

for genre in favoriteGenres.sorted(){
    print(genre)
}

let houseAnimals: Set = ["🐶", "🐱"]
let farmAnimals: Set = ["🐮", "🐔", "🐑", "🐶", "🐱"]
let cityAnimals: Set = ["🐦", "🐭"]
houseAnimals.isSubset(of: farmAnimals)
farmAnimals.isSuperset(of: houseAnimals)
farmAnimals.isDisjoint(with: cityAnimals)


var airports: [String:String] = ["xyz":"Toroton Pearson","DUB":"Dublin"]
print(airports.count)
airports["LHR"] = "London"
print(airports.count)

if let oldValue = airports.updateValue("Dublin airport", forKey: "DUB"){
    print("The old value of DUB is \(oldValue)")
}

if let oldValue = airports.removeValue(forKey: "DUB"){
    print("The removed airport is \"\(oldValue)\"")
}

for (code,airport) in airports {
    print("\(code):\(airport)")
}

let codes = [String](airports.keys)
print(codes)

let names = ["Anna", "Alex", "Brian", "Jack"]
for name in names{
    print(name)
}

for index in 1...5 {
    print("\(index) times 5 = \(index*5)")
}

let base = 3
var answer = 1
let power = 10

for _ in 1...power {
    answer *= base;
}
print ("\(base) to the \(power) is \(answer)")

let minutes = 60
for tickMark in 1..<minutes{
    print(tickMark)
}

let interval = 5
for tickMark in stride(from: 0, to: minutes, by: interval){
    print(tickMark, terminator: "\t")
}

let hour = 12
let hourInterval = 3
for tickMark in stride(from: 3, through:hour, by:hourInterval ){
    print(tickMark, terminator:"\t")
}


//array: interesting methods taking closure 
//filter
let bigNums: [Int] = [1,45,300,5,200].filter({$0>20})
print (bigNums)
//map
let strArr: [String] = [1,2,3].map({String($0)})
print(strArr)
//reduce
let sum = [1,2,3].reduce(0, +)
let minus = [1,2,3].reduce(10, -)

//String
let hello: String = "Hello"
var greet: String = hello + " there"
if let firstSpace = greet.characters.index(of: " "){
    greet.insert(contentsOf: " you".characters, at: firstSpace)
}


/**
 control flow
 **/

let newnames = ["Anna","Alex", "Brian", "Jack"]
for name in newnames {
    print("hello, \(name)!")
}

let numoflegs = ["spider":8, "ant":6, "cat":4]
for (animalName, legCount) in numoflegs{
    print("\(animalName) has \(legCount) legs!")
}

for index in 1...5 {
    print ("\(index) times 5 is \(index*5)")
}

let base1 = 3
let power1 = 10
var answer1 = 1
for _ in 1...power1 {
    answer1 *= base1
}
print ("\(base1) to the power of \(power1) is \(answer1)")

var temperature = 30
if temperature <= 32 {
    print("cold, consider wearing scarf")
}

let anotherChar : Character = "A"


//please be very careful that the switch statement in Swift is quite different from that in other languages in that
// NO fall through to next case in the abscent of "break" keyword.
switch anotherChar {
    case "a", "A":
    print("the letter is A")
default:
    print("The letter is not A")
}

//switch can use interval or tuple as well, this is a powerful feature not found in other languages
let somePnt = (1,1)
switch somePnt {
case (_,0):
    print("\(somePnt) is at x-axis")
case (0,0):
    print("\(somePnt) is at origin")
case (0, _):
    print("\(somePnt) is at y-axis")
case(-1...1,-1...1):
    print("\(somePnt) is inside the area")
default:
    print("\(somePnt) is outside the area")
}

//fall through
var intToDesc = 5
var desc = "The number \(intToDesc) is"

switch intToDesc {
case 2,3,5,7,11:
    desc += " primary number and also"
    fallthrough
default:
    desc += " an integer"
}
print (desc)

//guard
func greet (Person : [String: String]){
    guard let name = Person["name"] else{
        return
    }
    
    print ("hello, \(name)")
        
    guard let location  = Person["location"] else{
        print ("I hope the weather is nice near you")
        return
    }
    print ("I hope the weather is nice in \(location)")
    return
}

greet(Person: ["name": "John"])

greet(Person: ["name":"Emily", "location": "Jersey City"])


/**
 Functions
 **/
func greet(Person:String) -> String{
    let ret = "Hello " + Person + "!"
    return ret
}

print(greet(Person: "Anna"))
print(greet(Person: "Brian"))

func printAndCount(str: String) -> Int {
    print(str)
    return str.characters.count
}

func printWithoutCount(str:String){
    let _ = printAndCount(str: str)
}

printAndCount(str: "Hello world!")
printWithoutCount(str: "Hello, world!")

//function return multiple values - use tuple
func minMax(array : [Int]) -> (min : Int, max : Int) {
    var curMin = array[0]
    var curMax = array[0]
    //todo
    for val in array {
        if val < curMin {
            curMin = val
        }
        else if val > curMax{
            curMax = val
        }
    }
    return (curMin, curMax)
}

let bounds = minMax(array: [1,2,3,4,5])
print ("min is \(bounds.min), and max is \(bounds.max). ")

func greet2(person personName : String, from town : String) -> String {
    return "Hello, \(personName) from \(town) !"
}

print (greet2(person: "Bill", from: "Cupertino"))

func add(operand : Int, operand2 : Int) -> Int {
    return operand + operand2
}
print (add(operand: 2, operand2: 3))

//variadic
func arithmeticMean(_ numbers:Double...) -> Double {
    var total:Double = 0
    for number in numbers {
        total += number
    }
    return total / Double(numbers.count)
    
}

print (arithmeticMean(1,2,3,4,5,6))


//inout parameters
func swap(_ a : inout Int, _ b : inout Int){
    let temp = a
    a = b
    b = temp
}

var a1 : Int = 3
var b1 : Int = 4
swap(&a1,&b1)
print ("\(a1), \(b1)")


//function type
func addTwoInt(_ a : Int, _ b : Int ) -> Int {
    return a + b
}
func multipleTwoInt(_ a : Int, _ b: Int) -> Int {
    return a * b
}

var mathFunc : (Int, Int) -> Int  = addTwoInt

print (mathFunc(2,3))

mathFunc = multipleTwoInt

print (mathFunc(2,3))

//function type as parameters
func printMathFuncs(_ mathFunc : (Int, Int ) -> Int, _ a : Int, _ b : Int){
    print(mathFunc(a,b))
}

printMathFuncs(addTwoInt, 2, 3)
printMathFuncs(multipleTwoInt, 2, 3)


