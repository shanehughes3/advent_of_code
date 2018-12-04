use strict;
use warnings;

open(my $input, "<", "input.txt")
    or die "Can't open input.txt: $!";
my @lines = <$input>;

my $p1freq = 0;

for my $line (@lines) {
    $p1freq += $line;
}

print "Part 1: $p1freq\n";

my $currfreq = 0;
my $p2freq;
my %seenfreqs;
my $i = 0;

$seenfreqs{0} = 1;

while (!defined $p2freq) {
    $currfreq += $lines[$i];
    if (defined $seenfreqs{$currfreq}) {
	$p2freq = $currfreq;
    } else {
	$seenfreqs{$currfreq} = 1;
	$i = ++$i % @lines;
    }
}

print "Part 2: $p2freq\n";
