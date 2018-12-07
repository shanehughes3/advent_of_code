use strict;
use warnings;

open(my $input, "<", "input.txt");
my @lines = <$input>;
my %mapopen;
my %mapdeps;
my %allm;
my %notstart;
for my $lines (@lines) {
    $lines =~ m/Step (.) must be finished before step (.) can begin\./;
    $allm{$1} = 1;
    $allm{$2} = 1;
    $notstart{$2} = 1;
    $mapopen{$1} = [] unless defined $mapopen{$1};
    $mapdeps{$2} = [] unless defined $mapdeps{$2};
    push($mapopen{$1}, $2);
    push($mapdeps{$2}, $1);
}

my @next;
my @all = keys(%allm);
my @out;
for my $l (@all) {
    push(@next, $l) unless defined $notstart{$l};
}

while (@all) {
    @next = sort @next;
    my $now = shift @next;
    push(@out, $now);
    @all = grep(!/\Q$now\E/, @all);
    for my $new (@{$mapopen{$now}}) {
        my $depsresolved = 1;
        for my $dep (@{$mapdeps{$new}}) {
            $depsresolved = 0 unless grep(/\Q$dep\E/, @out);
        }
        push(@next, $new) unless
            !$depsresolved ||
            grep(/\Q$new\E/, @next) > 0 || 
            grep(/\Q$new\E/, @all) == 0;
    }
}

my $outstr = join('', @out);

print "Part 1: $outstr\n";



@all = keys(%allm);
@out = ();
@next = ();
for my $l (@all) {
    push(@next, $l) unless defined $notstart{$l};
}
my %active;
my $t = 0;

while (@all || keys %active) {
    for my $a (keys %active) {
        $active{$a}--;
        if ($active{$a} == 0) {
            delete $active{$a};
            push(@out, $a);
            for my $new (@{$mapopen{$a}}) {
                my $depsresolved = 1;
                for my $dep (@{$mapdeps{$new}}) {
                    $depsresolved = 0 unless grep(/\Q$dep\E/, @out);
                }
                push(@next, $new) unless
                    !$depsresolved ||
                    grep(/\Q$new\E/, @next) > 0 ||
                    grep(/\Q$new\E/, @all) == 0; 
            }
        }
    }
    @next = sort @next;
    while (@next && keys %active < 5) {
        my $now = shift @next;
        @all = grep(!/\Q$now\E/, @all);
        $active{$now} = 61 + (ord($now) - ord('A'));
    }
    print "$t: active ";
    for my $a (keys %active) {
        print "$a $active{$a} ";
    }
    print "\n";
    $t++ if (keys %active);
}

print "Part 2: $t\n";
