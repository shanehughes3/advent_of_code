import java.util.*;

class Day25 {
    enum State {
	A,
	B,
	C,
	D,
	E,
	F
    };
    State currentState;
    int cursor;
    ArrayList<Boolean> tape = new ArrayList<Boolean>();
    
    public static void main(String[] args) {
	Day25 day = new Day25();
	day.run();
	System.out.printf("Part 1: %d\n", day.checksum());
    }

    public Day25() {
	cursor = 0;
	tape.add(false);
	currentState = State.A;
    }

    public void run() {
	for (int i = 0; i < 12586542; ++i) {
	    if (cursor < 0) {
		tape.add(0, false);
		cursor = 0;
	    } else if (cursor == tape.size()) {
		tape.add(false);
	    }
	    switch (currentState) {
	    case A:
		if (tape.get(cursor)) {
		    tape.set(cursor, false);
		    --cursor;
		    currentState = State.B;
		} else {
		    tape.set(cursor, true);
		    ++cursor;
		    currentState = State.B;
		}
		break;
	    case B:
		if (tape.get(cursor)) {
		    --cursor;
		} else {
		    ++cursor;
		    currentState = State.C;
		}
		break;
	    case C:
		if (tape.get(cursor)) {
		    tape.set(cursor, false);
		    --cursor;
		    currentState = State.A;
		} else {
		    tape.set(cursor, true);
		    ++cursor;
		    currentState = State.D;
		}
		break;
	    case D:
		if (tape.get(cursor)) {
		    --cursor;
		    currentState = State.F;
		} else {
		    tape.set(cursor, true);
		    --cursor;
		    currentState = State.E;
		}
		break;
	    case E:
		if (tape.get(cursor)) {
		    tape.set(cursor, false);
		    --cursor;
		    currentState = State.D;
		} else {
		    tape.set(cursor, true);
		    --cursor;
		    currentState = State.A;
		}
		break;
	    case F:
		if (tape.get(cursor)) {
		    --cursor;
		    currentState = State.E;
		} else {
		    tape.set(cursor, true);
		    ++cursor;
		    currentState = State.A;
		}
	    }
	}
    }

    public int checksum() {
	int total = 0;
	for (boolean b : tape) {
	    if (b) {
		++total;
	    }
	}
	return total;
    }
}
