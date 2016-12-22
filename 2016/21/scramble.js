#!/usr/bin/env node
const InputList = require("../input-list");
const inputPassword = "abcdefgh";

function swapPosition(line, current) {
    let next = current.split("");
    next[+line[2]] = current[+line[5]];
    next[+line[5]] = current[+line[2]];
    return next.join("");
}

function swapLetter(line, current) {
    const re = new RegExp(line[2], "g");
    let next = current.replace(re, line[5]);
    let index;
    let last = 0;
    while ((index = current.slice(last).indexOf(line[5])) > -1) {
	next = next.slice(0, index + last) + line[2] +
	       next.slice(index + last + 1);
	last = index + last + 1;
    }
    return next;
}

function rotateLeft(line, current) {
    return current.slice(+line[2]) + current.slice(0, +line[2]);
}

function rotateRight(line, current) {
    return current.slice(-line[2]) + current.slice(0, -line[2]);
}

function rotateBased(line, current) {
    let index = current.indexOf(line[6]);
    if (index === -1) {
	return current;
    }
    index += (index > 3) ? 2 : 1;
    index = index % current.length;
    return current.slice(-index) + current.slice(0, -index);
}

function reversePos(line, current) {
    let start = parseInt(line[2], 10);
    let end = parseInt(line[4], 10);
    return current.slice(0, start) +
	   current.slice(start, end + 1).split("").reverse().join("") +
	   current.slice(end + 1);
}

function movePos(line, current) {
    let indexFrom = parseInt(line[2], 10);
    let indexTo = parseInt(line[5], 10);
    let next = current.split("");
    next.splice(indexFrom, 1);
    next.splice(indexTo, 0, current[indexFrom]);
    return next.join("");
}

const operations = {
    "swap position": swapPosition,
    "swap letter": swapLetter,
    "rotate left": rotateLeft,
    "rotate right": rotateRight,
    "rotate based": rotateBased,
    "reverse positions": reversePos,
    "move position": movePos
};

function run(input) {
    let current = inputPassword;
    for (let i = 0; i < input.length; i++) {
	let line = input[i].split(" ");
	current = (operations[`${line[0]} ${line[1]}`])(line, current);
    }
    console.log(current);
}

const Input = new InputList(process.argv[2]);
Input.on("loaded", () => run(Input.inputList));
