use strict;
use warnings;

open(my $input, "<", "input.txt");
my @lines = <$input>;
my @coords;
for my $i (0 .. $#lines) {
    $lines[$i] =~ m/(\d+), (\d+)/;
    $coords[$i][0] = $1;
    $coords[$i][1] = $2;
}

my $minx = 10000;
my $maxx = 0;
my $miny = 10000;
my $maxy = 0;
for my $coord (@coords) {
    if (@{$coord}[0] < $minx) {
        $minx = ${$coord}[0];
    }
    if (@{$coord}[0] > $maxx) {
        $maxx = ${$coord}[0];
    }
    if (@{$coord}[0] < $miny) {
        $miny = ${$coord}[1];
    }
    if (@{$coord}[1] > $maxy) {
        $maxy = ${$coord}[1];
    }
}

my @areas;
my $mindistsize = 0;

for my $x ($minx .. $maxx) {
    for my $y ($miny .. $maxy) {
        my $mindist = 10000;
        my $disttotal = 0;
        my $bestcoord;
        my $bestcnt;
        for my $i (0 .. $#coords) {
            my $dist = abs($coords[$i][0] - $x) + abs($coords[$i][1] - $y);
            $disttotal += $dist;
            if ($dist < $mindist) {
                $mindist = $dist;
                $bestcoord = $i;
                $bestcnt = 1;
            } elsif ($dist == $mindist) {
                $bestcnt++;
            }
        }
        if ($disttotal < 10000) {
            $mindistsize += 1;
        }
        if ($bestcnt == 1) {
            if ($x == $minx || $y == $miny || $x == $maxx || $y == $maxy) {
                $areas[$bestcoord] = -1;
            }
            if (!$areas[$bestcoord] || $areas[$bestcoord] != -1) {
                $areas[$bestcoord]++;
            }
        }
    }
}

my $maxarea = 0;
for my $area (@areas) {
    if ($area > $maxarea) {
        $maxarea = $area;
    }
}

print "Part 1: $maxarea\n";
print "Part 2: $mindistsize\n";
