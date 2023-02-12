#!/usr/bin/perl
use strict;
use warnings;
use utf8;
# use Cwd;
#use MIME::Base64;
use URI::Encode;
my $uri = URI::Encode->new( { encode_reserved => 0 } );

my $REQUEST_BODY;
read(STDIN, $REQUEST_BODY, $ENV{'CONTENT_LENGTH'});
$REQUEST_BODY = $uri->decode($REQUEST_BODY);
print STDERR "REQUEST_BODY: $REQUEST_BODY\n";
print STDERR "REQUEST_URI: $ENV{'REQUEST_URI'}\n";
print STDERR "QUERY_STRING: $ENV{'QUERY_STRING'}\n";

# my $PWD = getcwd();
# print STDERR "PWD: $PWD\n";
# foreach my $key (keys %ENV) {
#     print STDERR "$key --> $ENV{$key}\n";
# }

print "Status: 301 Moved Permanently\n";
print "Location: /\n\n";
