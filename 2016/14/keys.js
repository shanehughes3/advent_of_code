#!/usr/bin/env node
const crypto = require("crypto");

function findKeys(input, stretch) {
    const re = /([\w])\1{2}\1*/g;
    let keys = [];
    let queue = [...Array(16)].map(() => []);
    let index = 0;
    const limit = (stretch) ? 2017 : 1;

    
    while (keys.length < 100) {
	let output;
	for (let i = 0; i < limit; i++) {
	    let hash = crypto.createHash("md5");	
	    hash.update(output || input + index);
	    output = hash.digest("hex");
	}
	let matches = output.match(re);
	if (matches) {
	    let three = matches[0];
	    let fives = matches.filter((d) => d.length > 4);
	    if (fives) {
		fives.forEach((match) => {
		    let number = parseInt(match[0], 16);
		    if (queue[number].length > 0) {
			queue[number] = queue[number].filter((d) => {
			    return d > index - 1001;
			});
			keys = keys.concat(queue[number]);
			queue[number].length = 0;
		    }
		});
	    }
	    if (three) {
		queue[parseInt(three[0], 16)].push(index);
	    }
	}
	index++;
    }
    keys.sort((a, b) => a - b);
    console.log(keys[63]);
}

findKeys(process.argv[2], false);
findKeys(process.argv[2], true);
