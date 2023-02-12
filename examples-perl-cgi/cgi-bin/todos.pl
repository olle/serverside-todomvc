#!/usr/bin/perl
use strict;
use warnings;
use utf8;
use Cwd;
use MIME::Base64;
use URI::Encode;
use Time::HiRes qw(gettimeofday);

my $uri = URI::Encode->new( { encode_reserved => 0 } );

my $REQUEST_BODY;
read(STDIN, $REQUEST_BODY, $ENV{'CONTENT_LENGTH'});
print STDERR "RAW_BODY: $REQUEST_BODY\n";
$REQUEST_BODY =~ s/\+/ /g;
$REQUEST_BODY = $uri->decode($REQUEST_BODY);
print STDERR "REQUEST_BODY: $REQUEST_BODY\n";
print STDERR "REQUEST_URI: $ENV{'REQUEST_URI'}\n";
print STDERR "QUERY_STRING: $ENV{'QUERY_STRING'}\n";
my ($key, $value) = split(/\=/, $REQUEST_BODY);
print STDERR "FORM_VALUE: $key --> $value\n";




my ($s, $ms) = gettimeofday();
my $output = encode_base64($value);

my $PWD = getcwd();
open(OUT, '>>', "$PWD/todos.dat") or die $!;
print OUT "$s$ms|$output";
close(OUT);

print "Status: 301 Moved Permanently\n";
print "Location: /\n\n";
