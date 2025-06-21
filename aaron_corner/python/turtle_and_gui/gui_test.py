import tkinter as tk

def on_button_click():
    print("Hello World!")

# Create the main window
root = tk.Tk()
root.title("Greeting")
root.configure(bg="lightblue")  # Set the window's background color

screen_width = root.winfo_screenwidth()
screen_height = root.winfo_screenheight()

center_x = (screen_width - 1500) // 2
center_y = (screen_height - 1000) // 2

root.geometry(f"1500x1000+{center_x}+{center_y}")

# Create a label widget
label = tk.Label(root, text="Hello World!", width=10, height=1, font=("Helvetica", 64))
label.pack(pady=30)  # Add some vertical padding

# Create a button widget
button = tk.Button(root, text="Say Hi!", command=on_button_click, bg="green", fg="white")
button.pack(pady=300)

# Start the GUI event loop
root.mainloop()
