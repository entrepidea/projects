import tkinter as tk
from datetime import datetime

# Create the main window
tab = tk.Tk()
tab.geometry("1500x750")

now = datetime.now()

# Initialize a counter variable
counter = now.strftime("%I:%M %p")

# Create a StringVar to hold the displayed text
display_var = tk.StringVar()
display_var.set(counter)

# Create a Label widget attached to the StringVar
label = tk.Label(tab, textvariable=display_var, font=("Arial", 20))
label.pack(padx=20, pady=20)


# Define the function to update the counter and display_var
def update_counter():
    global counter
    counter += 1  # Increment the counter
    display_var.set(counter)  # Update the displayed text

    # Schedule this function to run again after 1 second (1000 milliseconds)
    tab.after(1000, update_counter, *())


# Start the counter update loop
update_counter()

# Run the Tkinter main loop
tab.mainloop()
