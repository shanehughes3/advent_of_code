use strict;
use warnings;

open(my $input, "<", "input.txt")
    or die "Could not open input.txt: $!\n";
my @lines = sort <$input>;

my @guards_time;
my @guards_total;
for (my $i = 0; $i < $#lines;) {
    if ($lines[$i] =~ m/\[1518-\d\d-\d\d \d\d:\d\d\] Guard #(\d+)/) {
	my $guard = $1;
	while ($lines[++$i] && $lines[$i] =~ m/\[1518-\d\d-\d\d \d\d:(\d\d)\] falls asleep/) {
	    my $start = $1;
	    if ($lines[++$i] && $lines[$i] =~ m/\[1518-\d\d-\d\d \d\d:(\d\d)\] wakes up/) {
		$guards_total[$guard] += $1 - $start;
		for my $m ($start .. $1 - 1) {
		    $guards_time[$guard][$m]++;
		}
	    } else {
		die "Bad line expecting wake: $lines[$i]\n";
	    }
	}
    } else {
	die "Bad line: $lines[$i]:\n\n$lines[$i - 1]$lines[$i]$lines[$i + 1]\n";
    }
}

my $max = 0;
my $max_guard;
for my $g (0 .. $#guards_total) {
    if ($guards_total[$g] && $guards_total[$g] > $max) {
	$max = $guards_total[$g];
	$max_guard = $g;
    }
}

$max = 0;
my $max_min;
for my $m (0 .. 59) {
    if ($guards_time[$max_guard][$m] && $guards_time[$max_guard][$m] > $max) {
	$max = $guards_time[$max_guard][$m];
	$max_min = $m;
    }
}

print "Part 1: @{[$max_min * $max_guard]}\n";

$max = 0;
for my $g (0 .. $#guards_time) {
    if (!$guards_time[$g]) {
	next;
    }
    for my $m (0 .. 59) {
	if ($guards_time[$g][$m] && $guards_time[$g][$m] > $max) {
	    $max = $guards_time[$g][$m];
	    $max_guard = $g;
	    $max_min = $m;
	}
    }
}

print "Part 2: @{[$max_min * $max_guard]}\n";
