#!/usr/bin/perl

use strict;
use warnings;

use lib '.';
require Todos;
require Request;

my ( $action, $id ) = Request->params();

if ( $action =~ /complete/ ) {
    Todos->complete($id);
}
elsif ( $action =~ /delete/ ) {
    Todos->delete($id);
}
elsif ( $action =~ /revert/ ) {
    Todos->activate($id);
}
elsif ( $action =~ /edit/ ) {
    Todos->edit($id);
}

Request->redirect("/");
