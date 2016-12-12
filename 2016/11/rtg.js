#!/usr/bin/env node

// runs both parts in 2m17s
/// map = [[chip, generator]]
let map1 = [[0, 0], [2, 1], [2, 1], [2, 1], [2, 1]];
let map2 = [[0, 0], [0, 0], [0, 0], [2, 1], [2, 1], [2, 1], [2, 1]];
    
let test;

function checkMap(map) {
    const length = map.length;
    for (let i = 0; i < length; i++) {
	if (map[i][0] !== map[i][1]) {
	    for (let j = 0; j < length; j++) {
		if (map[j][1] === map[i][0]) {
		    return false;
		}
	    }
	}
    }
    return true;
}

function checkHistory(floor, map, steps) {
    let key = JSON.stringify(map.concat(floor));
    if (history.hasOwnProperty(key)) {
	if (history[key] <= steps) {
	    return false;
	}
    }
    return true;
}

function checkFinished(map) {
    const length = map.length;
    for (let i = 0; i < length; i++) {
	if (map[i][0] !== 3 || map[i][1] !== 3) {
	    return false;
	}
    }
    return true;
}

function next(inElevator, floor, direction, map, steps) {
    let currentMin = 100;
    floor += direction;
    for (let i = 0; i < inElevator.length; i++) {
	map[inElevator[i][0]][inElevator[i][1]] = floor;
    }
    map = map.sort();
    if (!checkMap(map)) {
	return 100;
    }
    if (!checkHistory(floor, map, steps)) {
	return 100;
    }
    if (checkFinished(map)) {
	return steps;
    }
    if (steps > 70) {
	return 100;
    }
    history[JSON.stringify(map.concat(floor))] = steps;

    const mapLen = map.length;
    for (let elem1 = 0; elem1 < mapLen; elem1++) {
	for (let elem1Type = 0; elem1Type < 2; elem1Type++) {
	    
	    if (map[elem1][elem1Type] === floor) {
		if (floor < 3) {
		    currentMin =
			((test = next([[elem1, elem1Type]], floor, 1,
				       JSON.parse(JSON.stringify(map)),
				       steps + 1))
			 < currentMin) ? test : currentMin;
		}
		if (floor > 0) {
		    currentMin =
			((test = next([[elem1, elem1Type]], floor, -1,
				       JSON.parse(JSON.stringify(map)),
				       steps + 1))
			 < currentMin) ? test : currentMin;
		}
		    
		for (let elem2 = elem1; elem2 < mapLen; elem2++) {
		    for (let elem2Type = 0; elem2Type < 2; elem2Type++) {
			
			if (map[elem2][elem2Type] === floor &&
			    ((elem2Type === elem1Type && elem2 !== elem1) ||
			     (elem2Type !== elem1Type && elem2 === elem1))) {
			    
			    if (floor < 3) {
				currentMin =
				    ((test = next([[elem1, elem1Type],
						   [elem2, elem2Type]], floor, 1,
						  JSON.parse(JSON.stringify(map)),
						  steps + 1))
				     < currentMin) ? test : currentMin;
			    }
			    if (floor > 0) {
				currentMin =
				    ((test = next([[elem1, elem1Type],
						   [elem2, elem2Type]], floor, -1,
						  JSON.parse(JSON.stringify(map)),
						  steps + 1))
				     < currentMin) ? test : currentMin;
			    }
			}
		    }
		}
	    }
	}
    }

    return currentMin;
    
}
     
let history = {};
history[JSON.stringify(map1.concat(0))] = 0;
console.log("OUTPUT: " + next([[0,0],[0,1]], 0, 1, JSON.parse(JSON.stringify(map1)), 1));

history = {};
history[JSON.stringify(map2.concat(0))] = 0;
console.log("OUTPUT 2: " + next([[0,0],[0,1]], 0, 1, JSON.parse(JSON.stringify(map2)), 1));
