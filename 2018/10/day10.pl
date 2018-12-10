use warnings; use strict;

open(my $input, "<", "input.txt");
my @lines = <$input>;

my @pointstarts;

for my $line (@lines) {
    if ($line =~ m/position=< ?(-?\d+),  ?(-?\d+)> velocity=< ?(-?\d+),  ?(-?\d+)>/) {
	my @tmp = ($1, $2, $3, $4);
	push(@pointstarts, \@tmp);
    } else {
	print "Bad line read: $line\n";
    }
}

sub getbounds {
    my ($t) = @_;
    my $minx = 100000;
    my $maxx = -100000;
    my $miny = 100000;
    my $maxy = -100000;
    for my $point (@pointstarts) {
	my $x = $point->[0] + ($t * $point->[2]);
	my $y = $point->[1] + ($t * $point->[3]);
	$minx = $x if ($x < $minx);
	$maxx = $x if ($x > $maxx);
	$miny = $y if ($y < $miny);
	$maxy = $y if ($y > $maxy);
    }
    return ($minx, $maxx, $miny, $maxy);

}

sub printmsg {
    my ($t) = @_;
    my @plane;
    my ($minx, $maxx, $miny, $maxy) = getbounds($t);
    for my $x ($minx .. $maxx) {
	for my $y ($miny .. $maxy) {
	    $plane[$x - $minx][$y - $miny] = '.';
	}
    }
    for my $point (@pointstarts) {
	my $x = $point->[0] + ($t * $point->[2]) - $minx;
	my $y = $point->[1] + ($t * $point->[3]) - $miny;
	$plane[$x][$y] = '#'
    }
    for my $x ($minx .. $maxx) {
	for my $y ($miny .. $maxy) {
	    print $plane[$x - $minx][$y - $miny];
	}
	print "\n";
    }
}

my $t = 0;
my ($minx, $maxx, $miny, $maxy) = getbounds(0);
my $mindiff = ($maxx - $minx) + ($maxy - $miny);
while (1) {
    ($minx, $maxx, $miny, $maxy) = getbounds(++$t);
    my $diff = ($maxx - $minx) + ($maxy - $miny);
    if ($diff > $mindiff) {
	--$t;
	print "Part 1:\n";
	printmsg($t);
	print "Part 2: $t seconds\n";
	last;
    } else {
	$mindiff = $diff;
    }
}
