#!/usr/bin/env node

/* outputs all valid room names with total valid numbers at end
 *
 * pipe into grep to find target room
 */

const fs = require("fs"),
      readline = require("readline"),
      EventEmitter = require("events");

class Room {
    static isValid(lineStr) {
	const line = lineStr.split('-');
	let roomNumber, checksum, dump;
	[roomNumber, checksum, dump] = line.pop().split(/[\[\]]/);

	// sort letters in single string
	const letters = line.join("").split("").sort().join("");

	// split letter repeats into array elements ["aaaaa", "bbb"]
	let letter = 0, start = 0;
	let sortedLetters = [];
	while(letters[letter]) {
	    if (letters[letter] === letters[letter + 1]) {
		letter++;
	    } else {
		sortedLetters.push(letters.slice(start, letter + 1));
		letter++;
		start = letter;
	    }
	}

	// order array by number of each letter and alphabetical
	let orderedLetters = sortedLetters.sort((a, b) => {
	    if (b.length == a.length) {
		return (a[0] < b[0]) ? -1 : 1;
	    } else {
		return b.length - a.length;
	    }
	});

	// check
	for (let i = 0; i < 5; i++) {
	    if (orderedLetters[i][0] !== checksum[i]) {
		return 0;
	    }
	}

	console.log(Room.decryptName(line.join(" "), roomNumber), roomNumber);
	return +roomNumber;
    }

    static decryptName(lineStr, number) {
	const shift = number % 26;
	let output = "";
	for (let i = 0; i < lineStr.length; i++) {
	    if (lineStr[i] === ' ') {
		output += ' ';
	    } else {
		let code = lineStr.charCodeAt(i);
		code += shift;
		output += (String.fromCharCode(code) > 'z') ?
			  String.fromCharCode(code - 26) :
			  String.fromCharCode(code);
	    }
	}
	return output;
    }
}

class RoomList extends EventEmitter {
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

    findValidRooms() {
	let total = 0;
	this.list.forEach((line) => {
	    total += Room.isValid(line);
	});
	console.log("Total: ", total);
    }
}

const list = new RoomList(process.argv[2]);
list.on("loaded", list.findValidRooms);
