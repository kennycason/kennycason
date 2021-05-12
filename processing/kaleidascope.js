let width = 1000;
let height = 1000;

// Symmetry corresponding to the number of reflections. Change the number for different number of reflections
let symmetry = 4;
let angle = 360 / symmetry;
let saveButton, clearButton, mouseButton, keyboardButton;
let slider;

const COLORIZER_PARAMS = {
  f1: 0.3,
  f2: 0.3,
  f3: 0.3,
  p1: 0.0,
  p2: 2.0,
  p3: 4.0,
  center: 128,
  width: 127
}

function colorize(i) {
  const {f1, f2, f3, p1, p2, p3, width, center} = COLORIZER_PARAMS;
  const r = Math.floor(Math.sin(f1 * i + p1) * width + center)
  const g = Math.floor(Math.sin(f2 * i + p2) * width + center)
  const b = Math.floor(Math.sin(f3 * i + p3) * width + center)

  return {r: r, g: g, b: b};
}

function setup() {
  createCanvas(width, height);
  angleMode(DEGREES);
  background(15);

  // Creating the save button for the file
  saveButton = createButton('save');
  saveButton.mousePressed(saveFile);

  // Creating the clear screen button
  clearButton = createButton('clear');
  clearButton.mousePressed(clearScreen);

  // Creating the button for Full Screen
  fullscreenButton = createButton('Full Screen');
  fullscreenButton.mousePressed(screenFull);

  // Setting up the slider for the thickness of the brush
  brushSizeSlider = createButton('Brush Size Slider');
  sizeSlider = createSlider(1, 32, 16, 0.1);
}

// Save File Function
function saveFile() {
  save('design.jpg');
}

// Clear Screen function
function clearScreen() {
  background(15);
}

// Full Screen Function
function screenFull() {
  let fs = fullscreen();
  fullscreen(!fs);
}

function draw() {
  translate(width / 2, height / 2);

  if (mouseX > 0 && mouseX < width && mouseY > 0 && mouseY < height) {
    let mx = mouseX - width / 2;
    let my = mouseY - height / 2;
    let pmx = pmouseX - width / 2;
    let pmy = pmouseY - height / 2;

    if (mouseIsPressed) {
      for (let i = 0; i < symmetry; i++) {
        rotate(angle);
        let sw = sizeSlider.value();
        strokeWeight(sw);

        const {r, g, b} = colorize(new Date().getMilliseconds() / 100);
        //console.log(r + ', ' + g + ', ' + b);
        line(mx, my, pmx, pmy);
        stroke(r, g, b);
        push();
        scale(1, -1);
        line(mx, my, pmx, pmy);
        stroke(r, g, b);
        pop();
      }
    }
  }
}
