from pip import Image, ImageDraw, ImageFont
import textwrap

# Define the content of the rap with non-keywords highlighted
text = """
🔹 STRICTLY PYTHON 🔹
100% Real Python Terms — Rhymed & Refined
(*[words in brackets are NOT official Python keywords or built-ins]*)

True and open, not False, not None
try, except, finally: run
with as pass, raise class, def
nonlocal True and else is [left]

if not all or break, then wait
while you yield, and pass the [gate]
global is not, and or in
import from int, let scope [begin]

lambda async, await return
continue for loop, let values return
elif match and case is [tight]
assert that class and def is [right]

del class object, print or pass
from bool to bytes, we code with class
"""

# Set up the image
width, height = 1024, 1400
background_color = "white"
text_color = "black"
highlight_color = "red"

# Create a new image
image = Image.new("RGB", (width, height), background_color)
draw = ImageDraw.Draw(image)

# Load fonts
try:
    font = ImageFont.truetype("DejaVuSansMono.ttf", 28)
    title_font = ImageFont.truetype("DejaVuSansMono-Bold.ttf", 36)
except IOError:
    font = ImageFont.load_default()
    title_font = font

# Initial y position
y = 40

# Draw title
draw.text((40, y), "🔹 STRICTLY PYTHON 🔹", font=title_font, fill=text_color)
y += 50
draw.text((40, y), "100% Real Python Terms — Rhymed & Refined", font=font, fill=text_color)
y += 50
draw.text((40, y), "(*[words in brackets are NOT official Python keywords or built-ins]*)", font=font, fill=highlight_color)
y += 60

# Draw each line, highlighting non-keywords
for line in textwrap.dedent(text).strip().split("\n")[4:]:
    x = 40
    tokens = line.split(" ")
    for token in tokens:
        if token.startswith("[") and token.endswith("]"):
            clean_token = token[1:-1]
            draw.text((x, y), clean_token, font=font, fill=highlight_color)
            token_width = draw.textlength(clean_token + " ", font=font)
        else:
            draw.text((x, y), token, font=font, fill=text_color)
            token_width = draw.textlength(token + " ", font=font)
        x += token_width
    y += 40

# Save the image
image_path = "/mnt/data/strictly_python_poster.png"
image.save(image_path)

image_path