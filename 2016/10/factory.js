#!/usr/bin/env node
const InputList = require("../input-list");

class Bot {
    constructor(number, factory) {
	this.factory = factory;
	this.holding = [];
	this.number = number;
	this.instruction = null;
    }
    give(value) {
	this.holding.push(value);
	if (this.holding.length == 2 && this.instruction) {
	    this.pass();
	} else if (this.holding.length > 2) {
	    console.log(`Error: bot ${this.number}, holding ${this.holding}`);
	}
    }
    setInstruction(passLow, passHigh) {
	this.instruction = [passLow, passHigh];
	if (this.holding.length == 2) {
	    this.pass();
	}
    }
    pass() {
	this.holding.sort((a, b) => a - b);
	if (this.holding[0] === 17 && this.holding[1] === 61) {
	    console.log(`PART 1: bot ${this.number}`);
	}
	this.holding.forEach((d, i) => {
	    if (this.instruction[i].bot) {
		this.factory.giveTo(this.holding[i],
				    this.instruction[i].number);
	    } else {
		this.factory.output(this.holding[i],
				    this.instruction[i].number);
	    }
	});
	this.holding = [];
    }

}

class Output {
    constructor(number) {
	this.holds = [];
	this.number = number;
    }
    add(value) {
	this.holds.push(value);
    }
    getHolds() {
	return this.holds;
    }
}

class Factory {
    constructor() {
	this.bots = [];
	this.outputs = [];
    }
    giveTo(value, botNumber) {
	this.checkNewBot(botNumber);
	this.bots[botNumber].give(value);
    }
    output(value, number) {
	if (this.outputs[number] === undefined) {
	    this.outputs[number] = new Output(number);
	}
	this.outputs[number].add(value);
    }
    checkNewBot(botNumber) {
	if (this.bots[botNumber] === undefined) {
	    this.bots[botNumber] = new Bot(botNumber, this);
	}
    }
    setInstruction(botNumber, passLow, passHigh) {
	this.checkNewBot(botNumber);
	this.bots[botNumber].setInstruction(passLow, passHigh);
    }
    getOutput(number) {
	return this.outputs[number].getHolds();
    }
}

function startFactory(input) {
    const factory = new Factory();
    part2 = 1;
    input.forEach((line) => {
	const numbers = line.split(/[a-z ]+/).map((d) => parseInt(d, 10));
	if (line[0] === 'v') {
	    factory.giveTo(numbers[1], numbers[2]);
	} else {
	    const params = line.split(" ");
	    const passLow = {
		bot: (params[5][0] === 'b') ? true : false,
		number: numbers[2]
	    };
	    const passHigh = {
		bot: (params[10][0] === 'b') ? true : false,
		number: numbers[3]
	    };
	    factory.setInstruction(numbers[1], passLow, passHigh);
	}
    });
    [0, 1, 2].forEach((number) => {
	part2 *= factory.getOutput(number)[0];
    });
    console.log(`PART 2: ${part2}`);
}

const Input = new InputList(process.argv[2]);
Input.on("loaded", () => startFactory(Input.inputList));
