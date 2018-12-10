use strict; use warnings;

my $nplayers = 493;
my $lastmarble = 71863;

my @circle = (0);
my @players;
my $current = 0;
my $currentplayer = 0;
my @queue;

sub getmax {
    my $max = 0;
    for my $elf (0 .. $#players) {
	$max = $players[$elf] if $players[$elf] && $players[$elf] > $max;
    }
    return $max;
}

sub parse_queue {
    my $qi = 0;
    my $ci = 0;
    my $i = 0;
    my @out;
    while ($i < @circle + @queue) {
	if ($queue[$qi] && $i == $queue[$qi]->[0]) {
	    $out[$i++] = $queue[$qi++]->[1];
	} else {
	    $out[$i++] = $circle[$ci++];
	}
    }
    @queue = ();
    if (@circle < 100) {
	print join ',', @circle;
	print "\n";
    }
    @circle = @out;
}

for my $next (1 .. $lastmarble * 100) {
    if ($next % 23 == 0) {
	parse_queue();
	$players[$currentplayer] += $next;
	my $removedi = ($current + @circle - 7) % @circle;
	my $removed = splice(@circle, $removedi, 1);
	$current = $removedi % @circle;
	$players[$currentplayer] += $removed;
    } else {
	my $nextpos = $current + 2;
	if ($nextpos > @circle) {
	    parse_queue();
	    $nextpos = $nextpos % @circle;
	}
	my @tmp = ($nextpos + @queue, $next);
	$queue[@queue] = \@tmp;
#	splice(@circle, $nextpos, 0, $next);
	$current = $nextpos - 1;
    }
    $currentplayer = ++$currentplayer % $nplayers;
    if ($next == $lastmarble) {
	print "Part 1: @{[getmax()]}\n";
    }
    if ($next % $lastmarble == 0) {
	print "@{[$next / $lastmarble]}%\n";
    }
}

print "Part 2: @{[getmax()]}\n";




