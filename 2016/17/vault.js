#!/usr/bin/env node
const crypto = require("crypto");
const input = "lpvhkcbi";

const direction = { // array represents: relevant hash char, dx, dy
    "U": [0, -1, 0],
    "D": [1, 1, 0],
    "L": [2, 0, -1],
    "R": [3, 0, 1]
};
let worst = 0;

function checkBounds(x, y, [pos, dx, dy]) {
    if (x + dx < 4 && x + dx > -1 &&
	y + dy < 4 && y + dy > -1) {
	return true;
    } else {
	return false;
    }
}

function next(string, [x, y], steps) {
    if (x === 3 && y === 3) {
	return string;
    }
    let hash = crypto.createHash("md5");
    hash.update(string);
    const output = hash.digest("hex");
    let best;
    let current;
    
    for (d in direction) {
	if (output[direction[d][0]] >= 'b' && checkBounds(x, y, direction[d])) {
	    current = next(string + d,
			   [x + direction[d][1], y + direction[d][2]],
			   steps + 1);
	    if (!best && current) {
		best = current;
	    } else if (current) {
		best = (current.length < best.length) ? current : best;
	    }
	    if (current && current.length > worst) {
		worst = current.length;
	    }
	}
    }

    return best || null;
}

console.log(next(input, [0, 0], 0).slice(input.length));
console.log(worst - input.length);
