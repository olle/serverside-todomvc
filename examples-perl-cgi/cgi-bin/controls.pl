#!/usr/bin/perl

use strict;
use warnings;

use lib '.';
require Todos;
require Request;

my ( $action, $id ) = Request->params();
print STDERR "######### HANDLING $action --> $id\n";

if ( $action =~ /clear/ ) {
    Todos->clearCompleted();
}
elsif ( $action =~ /hide/ ) {
    Todos->hideCompleted();
}
elsif ( $action =~ /show/ ) {
    Todos->showCompleted();
}

Request->redirect("/");
