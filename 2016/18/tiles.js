#!/usr/bin/env node
const input = "...^^^^^..^...^...^^^^^^...^.^^^.^.^.^^.^^^.....^.^^^...^^^^^^.....^.^^...^^^^^...^.^^^.^^......^^^^";
const depth = 400000;

function populate(start) {
    const width = start.length;
    let floor = [];
    floor[0] = start;

    for (let row = 1; row < depth; row++) {
	floor[row] = "";
	for (let tile = 0; tile < width; tile++) {
	    let prev;
	    if (tile === 0) {
		prev = "." + floor[row - 1].slice(0, 2);
	    } else if (tile === width - 1) {
		prev = floor[row - 1].slice(-2) + ".";
	    } else {
		prev = floor[row - 1].slice(tile - 1, tile + 2);
	    }

	    switch (prev) {
		case "^^.":
		case ".^^":
		case "^..":
		case "..^":
		    floor[row] += "^";
		    break;
		default:
		    floor[row] += ".";
	    }
	}
    }
    return floor;
}

function count(floor) {
    let total = 0;
    floor.forEach((row) => {
	total += (row.match(/\./g) || []).length;
    });
    return total;
}

function calculate() {
    const floor = populate(input);
    return count(floor);
}

console.log(calculate());
