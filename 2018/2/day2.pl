use strict;
use warnings;

open(my $input, "<", "input.txt")
    or die "Can't open input.txt: $!\n";
my @lines = <$input>;
my $twos = 0;
my $threes = 0;

for my $lines (@lines) {
    $lines =~ s/\n//;
}

for my $line (@lines) {
    my %hash;
    for my $char (split //, $line) {
	$hash{$char}++;
    }
    my $two = my $three = 0;
    for my $val (values %hash) {
	if ($val == 2) {
	    $two++;
	} elsif ($val == 3) {
	    $three++;
	}
    }
    if ($two > 0) {
	$twos++;
    }
    if ($three > 0) {
	$threes++;
    }
}

my $output = $twos * $threes;
print "Part 1 $output\n";

for my $line_o (@lines) {
    for my $line_i (@lines) {
	if (linediff($line_o, $line_i) == 1) {
	    printsimlines($line_o, $line_i);
	    exit();
	}
    }
}

sub linediff {
    my ($line1, $line2) = @_;
    my @line1chars = split(//, $line1);
    my @line2chars = split(//, $line2);
    my $ndiffs = 0;
    for my $i (0 .. $#line1chars) {
	if ($line1chars[$i] ne $line2chars[$i]) {
	    $ndiffs++;
	}
    }
    return $ndiffs;
}

sub printsimlines {
    my ($line1, $line2) = @_;
    my @line1chars = split(//, $line1);
    my @line2chars = split(//, $line2);
    print "Part 2: ";
    for my $i (0 .. $#line1chars) {
	if ($line1chars[$i] eq $line2chars[$i]) {
	    print "$line1chars[$i]";
	}
    }
    print "\n";
}
