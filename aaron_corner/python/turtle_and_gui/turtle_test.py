import turtle

turtle.colormode(255)

screen = turtle.Screen()
line = turtle.Turtle()
line.speed(0)
line.goto(500, 800)
line.right(90)
line.fd(1600)
leo = turtle.Turtle()
leo.color("blue")
leo.pensize(3)
leo.right(10)
leo.forward(100)

raph = turtle.Turtle()
raph.speed(0)
raph.color("red")
raph.pensize(3)
raph.left(100)
raph.forward(100)

don = turtle.Turtle()
don.speed(0)
don.color("purple")
don.speed(1)

mikey = turtle.Turtle()
mikey.color("orange")
mikey.speed(1)

for _ in range(4):
    don.forward(100)
    don.left(90)

for _ in range(3):
    mikey.forward(100)
    mikey.left(120)
race1 = turtle.Turtle()
race2 = turtle.Turtle()
race3 = turtle.Turtle()
race4 = turtle.Turtle()
race1.goto(-700, 500)
race2.goto(-700, 250)
race3.goto(-700, -250)
race4.goto(-700, -500)
race1.color("red")
race2.color("orange")
race3.color("blue")
race4.color("purple")
race1.pensize(3)
race2.pensize(3)
race3.pensize(3)
race4.pensize(3)
import random

while race1.xcor() <= 500 and race2.xcor() <= 500 and race3.xcor() <= 500 and race4.xcor() <= 500:
    speed = random.randint(-5, 20)
    race1.fd(speed)
    speed = random.randint(-5, 20)
    race2.fd(speed)
    speed = random.randint(-5, 20)
    race3.fd(speed)
    speed = random.randint(-5, 20)
    race4.fd(speed)
if race1.xcor() >= 500:
    screen.bgcolor(255,50,50)
elif race2.xcor() >= 500:
    screen.bgcolor(255,150,50)
elif race3.xcor() >= 500:
    screen.bgcolor(50,50,255)
elif race4.xcor() >= 500:
    screen.bgcolor(255,50,255)
turtle.done()
