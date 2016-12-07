const EventEmitter = require("events"),
      readline = require("readline"),
      fs = require("fs");

class InputList extends EventEmitter {
    constructor(infile) {
	super();
	this.list = [];
	const rl = readline.createInterface({
	    input: fs.createReadStream(infile)
	});
	rl.on("line", (line) => {
	    this.list.push(line.trim());
	});
	rl.on("close", () => {
	    this.emit("loaded");
	});
    }

    get inputList() {
	return this.list.slice();
    }
}

module.exports = InputList;
