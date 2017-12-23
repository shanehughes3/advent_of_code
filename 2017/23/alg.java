import java.util.*;

class Alg {
    
    public static void main(String[] args) {
	Alg alg = new Alg();
	alg.go();
    }

    public Alg() {

    }

    public void go() {
	long a = 1;
	long b = 106700;
	long c = 123700;
	long d = 0;
	long e = 0;
	long f = 0;
	long g = 0;
	long h = 0;
	while (true) { // 1000 times
	    f = 1;
	    d = 2;
	    outer:
	    do { // b - 2 times
		e = 2;
		do { 
		    g = d;
		    g *= e;
		    g -= b;
		    if (g == 0) {
			f = 0;
			break outer;
		    }
		    ++e;
		    g = e;
		    g -= b;
		} while (g != 0);
		++d;
		g = d;
		g -= b;
	    } while (g != 0);
	    if (f == 0) {
		++h;
	    }
	    g = b;
	    g -= c;
	    if (g == 0) {
		break;
	    }
	    b += 17;
	}
	System.out.println(h);
    }
}
