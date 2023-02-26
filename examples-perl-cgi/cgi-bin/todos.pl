#!/usr/bin/perl

use strict;
use warnings;

use lib '.';
require Request;
require Todos;

my ( $key, $value, $__, $update ) = Request->params();

if ( $key =~ /todo/ ) {
    Todos->add($value);
}
elsif ( $key =~ /id/ ) {
    Todos->update( $value, $update );
}

Request->redirect("/");
