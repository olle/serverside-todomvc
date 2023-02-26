#!/usr/bin/perl

package Request;

use utf8;
use Cwd;
use URI::Encode;

sub params() {
    my $uri = URI::Encode->new( { encode_reserved => 0 } );
    my $REQUEST_BODY;
    read( STDIN, $REQUEST_BODY, $ENV{'CONTENT_LENGTH'} );
    $REQUEST_BODY =~ s/\+/ /g;
    $REQUEST_BODY = $uri->decode($REQUEST_BODY);

    my @result;
    if ( $REQUEST_BODY =~ /&/ ) {
        foreach ( split /&/, $REQUEST_BODY ) {
            push( @result, split( /\=/, $_ ) );
        }
    }
    else {
        push( @result, split( /\=/, $REQUEST_BODY ) );
    }
    return @result;
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