#!/usr/bin/perl

use strict;
use warnings;

use lib '.';
require Todos;
require Request;

my ( $action, $id ) = Request->params();
print STDERR "######### HANDLING $action --> $id\n";

if ( $action =~ /clear/ ) {
    Todos->clear();
}

Request->redirect("/");
