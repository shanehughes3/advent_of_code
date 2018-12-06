use strict;
use warnings;

open(my $input, "<", "input.txt")
    or die "Can't open input.txt: $!\n";
my @lines = <$input>;

my @rows;

for my $line (@lines) {
    if ($line =~ m/\#(\d+) @ (\d+),(\d+): (\d+)x(\d+)/) {
	for my $x ($2 .. ($2 + $4 - 1)) {
	    for my $y ($3 .. ($3 + $5 - 1)) {
		$rows[$x][$y]++;
	    }
	}
    } else {
	die "Bad match: $line\n";
    }
}

my $count = 0;

for my $row (@rows) {
    for my $el (@{$row}) {
	if ($el && $el >= 2) {
	    $count++;
	} 
    }
}
print "Part 1: $count\n";

for my $line (@lines) {
    if ($line =~ m/\#(\d+) @ (\d+),(\d+): (\d+)x(\d+)/) {
	my $lap = 0;
	for my $x ($2 .. ($2 + $4 - 1)) {
	    for my $y ($3 .. ($3 + $5 - 1)) {
		if ($rows[$x][$y] > 1) {
		    $lap++;
		}
	    }
	}
	if ($lap == 0) {
	    print "Part 2: $1\n";
	}
	$lap = 0;
    }
}
