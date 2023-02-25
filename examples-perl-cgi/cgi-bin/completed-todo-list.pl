#!/usr/bin/perl

use strict;
use warnings;
use MIME::Base64;

use lib '.';
require Todos;
require Request;

Request->response();

my @todos = Todos->completed();
foreach (@todos) {

    # Each todo is a line separated by '|' for now.
    my ( $id, $status, $text ) = split( /\|/, $_ );
    my $todo = decode_base64($text);
    $todo =~ s/</&lt;/g;
    my $fragment = <<"END_FRAGMENT";
<li>
    <button name="revert" value="${id}" form="todo-item" title="Mark as active"></button>
    <span>${todo}</span>
    <button name="delete" value="${id}" form="todo-item" title="Delete todo item">&#x2715;</button>
</li>
END_FRAGMENT
    print "$fragment\n";
}

