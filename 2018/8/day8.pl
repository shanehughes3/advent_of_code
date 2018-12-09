use strict; use warnings;

open (my $input, "<", "input.txt");
my $str = <$input>;

my $skip = 0;
sub getn {
    $str =~ m/^(?:\d+ ){\Q$skip\E}(\d+)/;
    ++$skip;
    return $1;
}
sub node {
    my $nchild = getn();
    my $nmetadata = getn();
    my $sum = 0;
    for (1 .. $nchild) {
	$sum += node();
    }
    for (1 .. $nmetadata) {
	$sum += getn();
    }
    return $sum;
}

print "Part 1: @{[node()]}\n";

$skip = 0;
sub p2node {
    my $nchild = getn();
    my $nmetadata = getn();
    my @children;
    my $sum = 0;
    for my $i (0 .. $nchild - 1) {
	$children[$i] = p2node();
    }
    if ($nchild > 0) {
	for (1 .. $nmetadata) {
	    my $i = getn();
	    if ($children[$i - 1]) {
		$sum += $children[$i - 1];
	    }
	}
    } else {
	for (1 .. $nmetadata) {
	    $sum += getn();
	}
    }
    return $sum;
}

print "Part 2: @{[p2node()]}\n";
