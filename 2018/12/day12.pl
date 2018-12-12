use strict; use warnings;
open(my $input, "<", "input.txt");
my @lines = <$input>;
my $current;
my %map;
my $p1reps = 20;

if ($lines[0] =~ m/initial state: ([#.]+)/) {
    $current = $1;
} else {
    die "Bad initial match\n";
}
for my $i (2 .. $#lines) {
    if ($lines[$i] =~ m/([#.]{5}) => ([#.])/) {
        $map{$1} = $2;
    } else {
        die "Bad state line match\n";
    }
}

sub score {
    my ($input, $zero) = @_;
    my $total = 0;
    for my $i (0 .. length($input) - 1) {
        if (substr($input, $i, 1) eq "#") {
            $total += $i - $zero;
        }
    }
    return $total;
}

my $zero = 4;
$current = sprintf("%s%s%s", "."x$zero, $current, "."x$zero);

for my $t (1 .. 50000000000) {
    my $next = $current;
    for my $i (2 .. length($current) - 3) {
        my $context = substr($current, $i - 2, 5);
        substr($next, $i, 1, $map{$context});
    }

    my $lastzero = $zero;
    
    my $first = index($next, "#");
    if ($first < 4) {
        substr($next, 0, 0, "."x(4 - $first));
        $zero += 4 - $first;
    } elsif ($first > 4) {
        $next = substr($next, $first - 4);
        $zero -= $first - 4;
    }
    
    my $last = rindex($next, "#");
    my $len = length($next);
    if ($last < $len - 5) {
        $next = substr($next, 0, $last + 5);
    } elsif ($last > $len - 4) {
        substr($next, $len - 1, 0, "."x($last - ($len - 4)));
    }

    if ($current eq $next) {
        my $diff = $zero - $lastzero;
        $zero = $zero + ((50000000000 - $t) * $diff);
        print "Part 2: @{[score($next, $zero)]}\n";
        exit;
    }
    
    $current = $next;

    if ($t == $p1reps) {
        print "Part 1: @{[score($current, $zero)]}\n";
    }
}




