#!/usr/bin/env node

const fs = require("fs"),
      readline = require("readline");

class TriangleList {
    constructor(file) {
	this.lines = [];
	const rl = readline.createInterface({
	    input: fs.createReadStream(file)
	});
	rl.on("line", (line) => {
	    const nums = line.trim().split(/\s+/);
	    this.lines.push(nums);
	});
	rl.on("close", () => {
	    this.countLineTriangles();
	    this.countVerticalTriangles();
	});
    }

    countLineTriangles() {
	let total = 0;
	this.lines.forEach((line) => {
	    total += this.checkThree(line[0], line[1], line[2]);
	});
	console.log("Line: ", total);
    }

    countVerticalTriangles() {
	let total = 0;
	for (let i = 0; i < this.lines.length; i += 3) {
	    for (let j = 0; j < 3; j++) {
		total += this.checkThree(
		    this.lines[i][j], this.lines[i+1][j], this.lines[i+2][j]);
	    }
	}
	console.log("Vertical: ", total);
    }
    
    checkThree(a, b, c) {
	const sum = +a + +b + +c;
	const max = Math.max(+a, Math.max(+b, +c));
	return (max < sum - max) ? 1 : 0;
    }
}

const list = new TriangleList(process.argv[2]);
