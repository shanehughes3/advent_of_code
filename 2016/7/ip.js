#!/usr/bin/env node

const InputList = require("../input-list");

function checkForABBA(input) {
    if (!input) {
	return false;
    }
    for (let i = 1; i < input.length - 2; i++) {
	if (input[i] === input[i + 1] &&
	    input[i] !== input[i - 1] &&
	    input[i - 1] === input[i + 2])
	    {
		return true;
	    }
    }
    return false;
}

function checkForABA(input) {
    if (!input) return [];
    let output = [];
    for (let i = 0; i < input.length - 2; i++) {
	if (input[i] === input[i + 2] &&
	    input[i] !== input[i + 1]) {
	   output.push(input.slice(i, i + 2)); 
	}
    }
    return output;
}

function checkABALineArrays(outside, inside) {
    for (let i = 0; i < outside.length; i++) {
	const found = inside.find((elem) => {
	    return (elem[0] === outside[i][1] &&
		    elem[1] === outside[i][0]);
	});
	if (found) return 1;
    }
    return 0;
}

function checkLine(line) {
    let lineABBAPresent = false;
    let lineABBACancelPresent = false;
    let ABAOutsideList = [];
    let ABAInsideList = [];
    line = line.split(/[\[\]]/);
    
    for (let i = 0; i < line.length; i++) {
	const groupABBAPresent = checkForABBA(line[i]);
	const groupABAList = checkForABA(line[i]);
	if (i % 2 === 0) { // outside brackets
	    lineABBAPresent = groupABBAPresent || lineABBAPresent;
	    ABAOutsideList = ABAOutsideList.concat(groupABAList);
	} else { // inside brackets
	    lineABBACancelPresent = groupABBAPresent || lineABBACancelPresent;
	    ABAInsideList = ABAInsideList.concat(groupABAList);
	}
    }
    const ABBA = (lineABBAPresent && !lineABBACancelPresent) ? 2 : 0;
    const ABA = checkABALineArrays(ABAOutsideList, ABAInsideList);
    
    return ABBA + ABA;
}

function parseList(list) {
    let totalABBA = 0;
    let totalABA = 0;
    list.forEach((line) => {
	const result = checkLine(line);
	totalABBA += (result > 1) ? 1 : 0;
	totalABA += (result % 2 === 1) ? 1 : 0;
    });
    console.log(`TLS: ${totalABBA}, SSL: ${totalABA}`);
}

const List = new InputList(process.argv[2]);
List.on("loaded", () => parseList(List.inputList));
