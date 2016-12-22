#!/usr/bin/env node
const input = 3001330;
//const input = 8;

function next(current, group) {
    let next = current;
    do {
	next = (next + 1) % input;
	if (next == current) {
	    return current;
	}
    } while (group[next] === 0);
    
    return next;
}

function exchange() {
    let group = [...Array(input)].map(() => 1);
    let current = 0;
    let last;

    do {
	last = current;
	group[next(current, group)] = 0;
    } while ((current = next(current, group)) != last);
    
    return last + 1;
}

function secondExchange() {
    let group = [...Array(input)].map((d, i) => i + 1);
    let current = 0;

    while (group.length > 1) {
	console.log(current, group);
	let index = (current + Math.floor(group.length / 2)) % group.length;
	group = group.slice(0, index).concat(group.slice(index + 1));
	//group.splice((current + Math.floor(group.length / 2)) % group.length,
	//	     1);
	current++;
    }

    return group[0];
}
console.log(exchange());
console.log(secondExchange());
