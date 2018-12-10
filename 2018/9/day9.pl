use strict; use warnings;

my $nplayers = 493;
my $lastmarble = 71863;

my @circle = (0, 1);
my @players;
my $current = 1;
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
	    push(@out, $queue[$qi++]->[1]);
	} else {
	    push(@out, $circle[$ci++]);
	}
	++$i;
    }
    @queue = ();
    @circle = @out;
}

for my $next (2 .. $lastmarble * 100) {
    if ($next % 23 == 0) {
	$current += @queue;
	parse_queue();
	$players[$currentplayer] += $next;
	my $removedi = ($current + @circle - 7) % @circle;
	my $removed = splice(@circle, $removedi, 1);
	$current = $removedi % @circle;
	$players[$currentplayer] += $removed;
    } else {
	my $nextpos = $current + 2;
	if ($nextpos > @circle) {
	    $nextpos += @queue;
	    parse_queue();
	    $nextpos = $nextpos % @circle;
	}
	my @tmp = ($nextpos + @queue, $next);
	$queue[@queue] = \@tmp;
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




