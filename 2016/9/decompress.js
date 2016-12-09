#!/usr/bin/env node
const InputList = require("../input-list");

function decompress(input) {
    let lastIndex = 0;
    let match;
    let totalAdd = 0;

    while ((match = input.slice(lastIndex).match(/\([0-9]+x[0-9]+\)/))) {
	params = match[0].split(/[()x]/);
	totalAdd += +params[1] * (+params[2] - 1) - match[0].length;
	lastIndex = lastIndex + match.index + match[0].length + +params[1];
    }

    console.log(input.length + totalAdd);
}

const Input = new InputList(process.argv[2]);
Input.on("loaded", () => decompress(Input.inputList[0]));
