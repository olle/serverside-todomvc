#!/usr/bin/perl
use strict;
use warnings;
use utf8;
use Cwd;

my $PWD = getcwd();

print "Content-Type: text/plain; charset=utf-8\n\n";

print "PWD: $PWD<br />";
foreach my $key (keys %ENV) {
    print "$key --> $ENV{$key}<br />";
}

my $filename = "$PWD/todos.dat";

open(FH, '<', $filename) or die $!;
while(<FH>){
   print "$_<br />";
}
close(FH);
