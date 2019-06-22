from turtle import *

Sam = Turtle()

#Let the turtle know it will be coloring in your shape and choose the color.
Sam.begin_fill()
Sam.color("red")

#Position the turtle to start drawing.
Sam.left(40)

#This is the first half of the heart.
Sam.forward(130)
Sam.left(30)
Sam.forward(40)
Sam.left(40)
Sam.forward(40)
Sam.left(40)
Sam.forward(30)
Sam.left(30)
Sam.forward(30)
Sam.left(40)
Sam.forward(50)

#Position the turtle to start drawing the second half of the heart.
Sam.right(80)

#This second half of the heart (It's just the first half backwards)!

Sam.forward(50)
Sam.left(40)
Sam.forward(30)
Sam.left(30)
Sam.forward(30)
Sam.left(40)
Sam.forward(40)
Sam.left(40)
Sam.forward(40)
Sam.left(30)
Sam.forward(130)

#Tell the turtle to color in the shape.
Sam.end_fill()

#Type a fun message!
Sam.write("HAPPY VALENTINE'S DAY!")