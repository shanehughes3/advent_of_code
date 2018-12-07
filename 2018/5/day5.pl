use strict;
use warnings;

open(my $input, "<", "input.txt")
    or die "Can't open input.txt: $!\n";
my $polymer = <$input>;
$polymer =~ s/\n//;
my $polymerorig = $polymer;

sub react {
    my ($polymer) = @_;
    my $len = length $polymer;
    my $i = 0;
    while ($i < $len) {
        if (uc(substr($polymer, $i, 1)) eq uc(substr($polymer, $i + 1, 1)) && 
            substr($polymer, $i, 1) ne substr($polymer, $i + 1, 1)) {
            substr($polymer, $i, 2, "");
            $len -= 2;
            if ($i > 0) {
                --$i;
            }
        } else {
            ++$i;
        }
    }
    return $len;
}

my $p1len = react $polymer;
print "Part 1: $p1len\n";

my $min = length $polymerorig;
for my $c ('a' .. 'z') {
    my $uc = uc $c;
    $polymer = $polymerorig;
    $polymer =~ s/[\Q$c$uc\E]//g;
    my $thislen = react $polymer;
    if ($thislen < $min) {
        $min = $thislen;
    }
}
print "Part 2: $min\n";
