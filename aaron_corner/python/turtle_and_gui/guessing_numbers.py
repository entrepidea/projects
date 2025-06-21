import turtle, time, pygame, random

pygame.init()
pygame.mixer.init()
pygame.mixer.music.load("C:/Users/Jon/Downloads/Pixel Peeker Polka - slower.mp3")
pygame.mixer.music.play(loops=-1)
screen = turtle.Screen()


def create_turtle():
    random_int = random.randint(0, 1)
    t = turtle.Turtle()
    t.shape("turtle")
    t.color((random_int, random_int, random_int))
    t.penup()
    t.speed(0)
    return t


def teleport_turtles():
    x_min, x_max = -390, 390
    y_min, y_max = -290, 290

    for t in turtles:
        x = random.randint(x_min, x_max)
        y = random.randint(y_min, y_max)
        t.goto(x, y)


    screen.ontimer(teleport_turtles, 1000)


for i in range(100):
    create_turtle()
for i in range(100):
    screen.bgcolor((i / 100, i / 100, i / 100))
    time.sleep(0.01)
time.sleep(0.5)
screen.bgcolor(0, 0.8, 1)
text = turtle.Turtle()
text.hideturtle()
text.speed(0)
text.penup()
text.goto(-600, 300)
text.write("Guessing Numbers!", font=("Trebuchet MS", 100, "bold"))
text.goto(-150, 225)
text.write("By QazGuy", font=("Trebuchet MS", 50, "bold"))

turtle.done()
