#!/usr/bin/env node
const InputList = require("../input-list");

function decompress(input, multiplier) {
    let lastIndex = 0;
    let match;
    let totalAdd = 0;
    let params;
    let decompressMatched = false;

    while ((match = input.slice(lastIndex).match(/\([0-9]+x[0-9]+\)/))) {
	decompressMatched = true;
	params = match[0].split(/[()x]/).map((d) => parseInt(d, 10));
	totalAdd += match.index * multiplier;
	const decompStr = input.slice(lastIndex + match.index + match[0].length,
				      lastIndex + match.index +
				      match[0].length + params[1]);
	totalAdd += decompress(decompStr, params[2] * multiplier);
	lastIndex = lastIndex + match.index + match[0].length + params[1];
    }
    
    return (decompressMatched) ? totalAdd : input.length * multiplier;
}

function start(input) {
    console.log(decompress(input, 1, 1));
}

const Input = new InputList(process.argv[2]);
Input.on("loaded", () => start(Input.inputList[0]));
