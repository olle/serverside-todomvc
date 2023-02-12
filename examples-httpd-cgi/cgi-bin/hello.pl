#!/usr/bin/perl
use strict;
use warnings;
use utf8;

print "Content-Type: text/plain; charset=utf-8\n\n";

foreach my $key (keys %ENV) {
    print "$key --> $ENV{$key}<br />";
}
