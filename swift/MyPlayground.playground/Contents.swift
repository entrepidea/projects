//: Playground - noun: a place where people can play

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

