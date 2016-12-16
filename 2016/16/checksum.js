#!/usr/bin/env node

const input = "11110010111001001";
const outputLength = parseInt(process.argv[2], 10);

function fill(input) {
    debugger;
    let current = input.slice();
    while (current.length <= outputLength) {
	let buffer = [];
	const reverse = current.split("").reverse();
	buffer.push("0");
	const next = reverse.map((d) => (d === "1") ? "0" : "1");
	buffer = buffer.concat(next);
	current += buffer.join("");
    }
    return current;
}

function getSum(input) {
    input = input.slice(0, outputLength);
    while (input.length % 2 === 0) {
	let output = [];
	let next;
	for (let i = 0; i < input.length; i += 2) {
	    next = (input[i] === input[i + 1]) ? "1" : "0";
	    output.push(next);
	}
	input = output.join("");
    }
    return input;
}

const disk = fill(input);
console.log(getSum(disk));
