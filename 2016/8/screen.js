#!/usr/bin/env node
const InputList = require("../input-list");

class Screen {
    constructor(width, height) {
	this.screen = [];
	for (let i = 0; i < height; i++) {
	    this.screen.push(Array(width).fill(0));
	}
	this.width = width;
	this.height = height;
    }
    shiftRow(row, dist) {
	dist = dist % this.width;
	this.screen[row] = this.screen[row].slice(-dist)
			       .concat(this.screen[row].slice(0, -dist));
    }
    shiftCol(col, dist) {
	dist = dist % this.height;
	let temp = [];
	this.screen.forEach((row) => {
	    temp.push(row[col]);
	});
	temp = temp.slice(-dist).concat(temp.slice(0, -dist));
	this.screen.forEach((row, i) => {
	    row[col] = temp[i];
	});
    }
    drawRect(width, height) {
	for (let row = 0; row < height; row++) {
	    for (let col = 0; col < width; col++) {
		this.screen[row][col] = 1;
	    }
	}
    }
    drawOnScreen() {
	console.log("\x1B[2J");
	this.screen.forEach((line) => {
	    let outLine = line.map((d) => d ?
					String.fromCharCode(0x2588) : ' ');
	    console.log(outLine.join(""));
	});
    }
    get numberLit() {
	let total = 0;
	this.screen.forEach((row) => {
	    row.forEach((pixel) => {
		total += pixel;
	    });
	});
	return total;
    }
}

function changeScreen(input) {
    const LCD = new Screen(50, 6);
    input.forEach((line) => {
	const params = line.split(/[a-z =]+/).map((d) => parseInt(d, 10));
	if (line[1] === 'e') { // "rect..."
	    LCD.drawRect(params[1], params[2]);
	} else if (line[7] === 'r') { // "rotate row..."
	    LCD.shiftRow(params[1], params[2]);
	} else if (line[7] === 'c') { // "rotate col..."
	    LCD.shiftCol(params[1], params[2]);
	}
    });
    LCD.drawOnScreen();
    console.log("\nTotal: " + LCD.numberLit);
}

const List = new InputList(process.argv[2]);
List.on("loaded", () => changeScreen(List.inputList));
