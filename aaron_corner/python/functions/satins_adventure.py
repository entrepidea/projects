import random
import time

hp = 0
atk = 0
guard = 0
hunger = 0
commands = """
Commands:

left - Go left when encountering a split path.

right - Go right when encountering a split path.

attack - Damage an enemy.

eat - Eat certain items.

stats - Show your statistics.
"""
print(f"{'\n' * 2}Welcome to Satin's Adventure!{'\n' * 2}")
time.sleep(1)
while True:
    name = input("\nEnter your nickname: ")
    while True:
        check = input("\nAre you sure? Type y for yes or n for no: ")
        if check.lower() in ['y', 'n']:
            break
        else:
            print("\nInvalid input.")
    if check.lower() == "y":
        break
print(f"{'\n' * 3}Your nickname is {name}.")
while True:
    character = input(
        "\nChoose a character: d for dwarf, g for gnome, k for knight, x for dragon, or type something else for a random character: ")
    while True:
        check_two = input("\nAre you sure? Type y for yes or n for no: ")
        if check_two.lower() in ['y', 'n']:
            break
        else:
            print("\nInvalid input.\n")
    if check_two.lower() == "y":
        break
if character == "d":
    character = "Dwarf"
elif character == "g":
    character = "Gnome"
elif character == "k":
    character = "Knight"
elif character == "x":
    character = "Dragon"
else:
    random_integer = random.randint(1, 4)
    if random_integer == 1:
        character = "Dwarf"
    elif random_integer == 2:
        character = "Gnome"
    elif random_integer == 3:
        character = "Knight"
    elif random_integer == 4:
        character = "Dragon"
if character == "Dwarf":
    hp = 6
    atk = 2
    guard = 2
    hunger = 50
elif character == "Gnome":
    hp = 6
    atk = 4
    guard = 1
    hunger = 50
elif character == "Knight":
    hp = 10
    atk = 4
    guard = 2
    hunger = 100
elif character == "Dragon":
    hp = 25
    atk = 6
    guard = 3
    hunger = 200
print(f"\nLets Start, {name} the {character}!")
time.sleep(1)
print(f"{'\n' * 2}Note: Type \"help\" to see the commands.")
time.sleep(1)
print(f"{'\n' * 2}You are spawned deep in Veilwood and see a split path ahead.")


def action(a, b, c, d):
    while True:
        option = input("\nWhat do you do?: ")
        if option == "help":
            print(commands)
            continue
        elif option == "stats":
            print(f"\nHealth: {hp}{'\n' * 2}Damage: {atk}{'\n' * 2}Hunger Points: {hunger}")
            continue
        elif option == "left" and a == 1:
            break
        elif option == "left" and a == 0:
            print("\nYou can't go left.")
        elif option == "right" and b == 1:
            break
        elif option == "right" and b == 0:
            print("\nYou can't go right.")
        elif option == "attack" and c == 1:
            break
        elif option == "attack" and c == 0:
            print("\nThere is nothing to attack.")
        elif option == "eat" and d == 1:
            break
        elif option == "eat" and d == 0:
            print("\nThere is nothing to eat.")
        else:
            print("\nInvalid input.")


action(1, 1, 0, 0)
