use strict; use warnings;
my $serial = 8199;
#my $serial = 18;

sub pw {
    my ($x, $y) = @_;
    return (int((((($x + 10) * $y) + $serial) * ($x + 10)) / 100) % 10) - 5;
}

my $maxp = 0;
my $maxx; my $maxy;
for my $startx (1 .. 298) {
    for my $starty (1 .. 298) {
	my $p = 0;
	for my $x ($startx .. $startx + 2) {
	    for my $y ($starty .. $starty + 2) {
		$p += pw($x, $y);
	    }
	}
	if ($p > $maxp) {
	    $maxp = $p;
	    $maxx = $startx;
	    $maxy = $starty;
	}
    }
}

print "Part 1: ($maxx,$maxy)\n";

my @grid;
for my $x (1 .. 300) {
    for my $y (1 .. 300) {
	$grid[$x][$y] = pw($x, $y);
    }
}
$maxp = 0;
$maxx = 0; $maxy = 0;
my $maxsize;
for my $size (1 .. 300) {
    print "Starting size $size\n";
    for my $startx (1 .. 301 - $size) {
	for my $starty (1 .. 301 - $size) {
	    my $p = 0;
	    for my $x ($startx .. $startx + $size - 1) {
		for my $y ($starty .. $starty + $size - 1) {
		    $p += $grid[$x]->[$y];
		}
	    }
	    if ($p > $maxp) {
		$maxp = $p;
		$maxx = $startx;
		$maxy = $starty;
		$maxsize = $size;
	    }
	}
    }
}

print "Part 2: ($maxx,$maxy,$maxsize)\n";


