let width = 48 * 16;
let height = 48 * 16;
let stepLength = width / 16;
let speed = Math.random() * 100;
let iteration = 0;
let maxIterations = 16 * 16 * 5;

const COLORIZER_PARAMS = {
  f1: 0.3,
  f2: 0.4,
  f3: 0.3,
  p1: 0.0,
  p2: 2.0,
  p3: 4.0,
  center: width / 2, // 128
  width: width  // 127
}

const RANDOM_COLORIZER_PARAMS = {
  f1: Math.random(),
  f2: Math.random(),
  f3: Math.random(),
  p1: Math.random() * 4,
  p2: Math.random() * 4,
  p3: Math.random() * 4,
  center: 128,
  width: 127
}

function colorize(i) {
  const {f1, f2, f3, p1, p2, p3, width, center} = RANDOM_COLORIZER_PARAMS;
  const r = Math.floor(Math.sin(f1 * i + p1) * width + center)
  const g = Math.floor(Math.cos(f2 * i + p2) * width + center)
  const b = Math.floor(Math.tan(f3 * i + p3) * width + center)

  return {r: r, g: g, b: b};
}

// TURTLE STUFF:
let x, y; // the current position of the turtle
let currentangle = 0; // which way the turtle is pointing
let step = stepLength; // how much the turtle moves with each 'F'
let angle = 90; // how much the turtle turns with a '-' or '+'

// LINDENMAYER STUFF (L-SYSTEMS)
let thestring = 'A'; // "axiom" or start of the string
let numloops = 5; // how many iterations to pre-compute
let therules = []; // array for rules
therules[0] = ['A', '-BF+AFA+FB-']; // first rule
therules[1] = ['B', '+AF-BFB-FA+']; // second rule

let whereinstring = 0; // where in the L-system are we?

function setup() {
  createCanvas(width, height);
  background(255);
  stroke(0, 0, 0, 255);

  // start the x and y position at lower-left corner
  x = stepLength / 2;
  y = height - 1 - stepLength / 2;

  // COMPUTE THE L-SYSTEM
  for (let i = 0; i < numloops; i++) {
    thestring = lindenmayer(thestring);
  }

  console.log("configurations: " + JSON.stringify(RANDOM_COLORIZER_PARAMS));
}

function draw() {
  if (iteration >= maxIterations) {
    return;
  }
  iteration++;

  // draw the current character in the string:
  drawIt(thestring[whereinstring]);

  // increment the point for where we're reading the string.
  // wrap around at the end.
  whereinstring++;
  if (whereinstring > thestring.length-1) whereinstring = 0;

}

// interpret an L-system
function lindenmayer(s) {
  let outputstring = ''; // start a blank output string

  // iterate through 'therules' looking for symbol matches:
  for (let i = 0; i < s.length; i++) {
    let ismatch = 0; // by default, no match
    for (let j = 0; j < therules.length; j++) {
      if (s[i] == therules[j][0])  {
        outputstring += therules[j][1]; // write substitution
        ismatch = 1; // we have a match, so don't copy over symbol
        break; // get outta this for() loop
      }
    }
    // if nothing matches, just copy the symbol over.
    if (ismatch == 0) outputstring+= s[i];
  }

  return outputstring; // send out the modified string
}

// this is a custom function that draws turtle commands
function drawIt(k) {
  if (k=='F') { // draw forward
    // polar to cartesian based on step and currentangle:
    let x1 = x + step*cos(radians(currentangle));
    let y1 = y + step*sin(radians(currentangle));
    // line(x, y, x1, y1); // connect the old and the new

    // update the turtle's position:
    x = x1;
    y = y1;
  } else if (k == '+') {
    currentangle += angle; // turn left
  } else if (k == '-') {
    currentangle -= angle; // turn right
  }

  // give me some random color values:
  // let r = random(128, 255);
  // let g = random(0, 192);
  // let b = random(0, 50);
  const {r, g, b} = colorize(new Date().getMilliseconds() / speed);
  let a = random(25, 100);

  // pick a gaussian (D&D) distribution for the radius:
  const maxRadius = stepLength;
  let radius = 0;
  radius += random(0, maxRadius);
  radius += random(0, maxRadius);
  radius += random(0, maxRadius);
  radius = radius / 3;

  // draw the stuff:
  fill(r, g, b, a);
  //ellipse(x, y, radius, radius);
  //rect(x - radius / 2, 1 + y - radius / 2, radius, radius);
  rect(x - stepLength / 2 - radius / 2, y - stepLength / 2 + 1 - radius / 2, radius, radius);
  // polygon(x - radius / 2, 1 + y - radius / 2, radius / 3, 5);
}

//const TWO_PI = Math.PI * 2;
function polygon(x, y, radius, npoints) {
  let angle = TWO_PI / npoints;
  beginShape();
  for (let a = 0; a < TWO_PI; a += angle) {
    let sx = x + Math.cos(a) * radius;
    let sy = y + Math.sin(a) * radius;
    vertex(sx, sy);
  }
  endShape(CLOSE);
}
