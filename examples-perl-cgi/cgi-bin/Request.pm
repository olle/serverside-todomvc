#!/usr/bin/perl

package Request;

use utf8;
use Cwd;
use URI::Encode;

sub params() {
    my $uri = URI::Encode->new( { encode_reserved => 0 } );
    my $REQUEST_BODY;
    read( STDIN, $REQUEST_BODY, $ENV{'CONTENT_LENGTH'} );

    # print STDERR "RAW_BODY: $REQUEST_BODY\n";
    $REQUEST_BODY =~ s/\+/ /g;
    $REQUEST_BODY = $uri->decode($REQUEST_BODY);

    # print STDERR "REQUEST_BODY: $REQUEST_BODY\n";
    # print STDERR "REQUEST_URI: $ENV{'REQUEST_URI'}\n";
    # print STDERR "QUERY_STRING: $ENV{'QUERY_STRING'}\n";
    return split( /\=/, $REQUEST_BODY );
}

sub response {
    print "Content-Type: text/html; charset=utf-8\n\n";
}

sub redirect {
    my ( $__, $path ) = @_;
    print "Status: 301 Moved Permanently\n";
    print "Location: $path\n\n";
}

1;