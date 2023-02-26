#!/usr/bin/perl

use strict;
use warnings;
use MIME::Base64;

use lib '.';
require Todos;
require Request;

Request->response();

foreach my $todo ( Todos->completed() ) {
    my ( $id, $text ) = ( $todo->getId(), $todo->getText() );
    my $fragment = <<"END_FRAGMENT";
<li>
    <button name="revert" value="${id}" form="todo-item" title="Mark as active"></button>
    <span>${text}</span>
    <button name="delete" value="${id}" form="todo-item" title="Delete todo item">&#x2715;</button>
</li>
END_FRAGMENT
    print "$fragment\n";
}

