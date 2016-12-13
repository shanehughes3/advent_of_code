#!/usr/bin/env node

// recusrive, exhaustive DFS, not very efficient, but works well for part 2

const input = 1358;
const mapSize = 100;
const map = [...Array(mapSize)].map(() => Array(mapSize));
const nextPossible = [
    {x: -1, y: 0},
    {x: 1, y: 0},
    {x: 0, y: 1},
    {x: 0, y: -1}
];

function checkSpace([x, y]) { // true if space, false if wall
    let sum = (x * x) + (3 * x) + (2 * x * y) + y + (y * y) + input;
    let bits = 0;
    while (sum > 0) {
	bits += sum % 2;
	sum >>= 1;
    }
    return !(bits % 2);
}

function moveNext([x, y], steps) {
    map[x][y] = steps;
    if (x === 31 && y === 39) {
	return steps;
    }
    let min = Number.MAX_SAFE_INTEGER;
    let test;
    for (let i = 0; i < 4; i++) {
	let nextX = x + nextPossible[i].x;
	let nextY = y + nextPossible[i].y;
	if (nextX === mapSize || nextY === mapSize ||
	    nextX < 0 || nextY < 0) {
	    continue;
	}
	if (map[nextX][nextY] < steps) {
	    continue;
	}
	if (checkSpace([nextX, nextY])) {
	    min = ((test = moveNext([nextX, nextY], steps + 1)) < min) ?
		  test : min;
	}
    }
    return min;
}

function countMap(max) {
    let total = 0;
    for (let x = 0; x < mapSize; x++) {
	for (let y = 0; y < mapSize; y++) {
	    if (map[x][y] <= max) {
		total++;
	    }
	}
    }
    return total;
}

console.log("Part 1: ", moveNext([1, 1], 0));
console.log("Part 2: ", countMap(50));
