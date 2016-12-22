#!/usr/bin/env node
const InputList = require("../input-list");

class List {
    constructor(input) {
	this.list = [];
	input.forEach((line) => {
	    line = line.split("-").map((d) => parseInt(d, 10));
	    this.list.push(line);
	});
	this.list = this.list.sort((a, b) => a[0] - b[0]);
	this.list = this.reduceList();
    }

    reduceList(list) {
	for (let i = 0; i < this.list.length - 1; i++) {
	    if (this.list[i][1] + 1 >= this.list[i + 1][0]) {
		this.list[i][1] = (this.list[i][1] > this.list[i + 1][1]) ?
			     this.list[i][1] : this.list[i + 1][1];
		this.list.splice(i + 1, 1);
		i--;
	    }
	}
	return this.list;
    }

    get lowest() {
	return this.list[0][1] + 1;
    }

    get count() {
	let total = 0;
	for (let i = 1; i < this.list.length; i++) {
	    total += this.list[i][0] - this.list[i - 1][1] - 1;
	}
	return total;
    }
}

function run(input) {
    const IPs = new List(input);
    console.log(IPs.lowest);
    console.log(IPs.count);
}

const Input = new InputList(process.argv[2]);
Input.on("loaded", () => run(Input.inputList));
