#!/usr/bin/env node
const InputList = require("../input-list");

function compute(input, register) {
    const length = input.length;
    let lineNum = 0;
    while (lineNum < length) {
	const command = input[lineNum].split(" ");
	switch (command[0]) {
	    case "cpy":
		register[command[2]] = (isNaN(parseInt(command[1], 10))) ?
				       register[command[1]] :
				       parseInt(command[1], 10);
		break;
	    case "inc":
		register[command[1]]++;
		break;
	    case "dec":
		register[command[1]]--;
		break;
	    case "jnz":
		lineNum += (register[command[1]] !== 0) ?
			   +command[2] - 1 : 0;
		break;
	}
	lineNum++;
    }
    return register.a;
}

function start(input) {
    let register1 = {
	a: 0,
	b: 0,
	c: 0,
	d: 0
    };
    let register2 = {
	a: 0,
	b: 0,
	c: 1,
	d: 0
    };
    console.log("Part 1: " + compute(input, register1));
    console.log("Part 2: " + compute(input, register2));
}

const Input = new InputList(process.argv[2]);
Input.on("loaded", () => start(Input.inputList));
