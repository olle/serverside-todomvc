#!/usr/bin/perl

use strict;
use warnings;
use MIME::Base64;

use lib '.';
require Todos;
require Request;

Request->response();

my ( $__, $___, $isEditing ) = Todos->metadata();
my $autofocus = $isEditing ? "" : "autofocus";

my $fragment = <<"END_FRAGMENT";
<form action="cgi-bin/todos.pl" method="post">
    <label for="todo">Todo</label>
    <input placeholder="What needs to be done?" ${autofocus} required autocomplete="off" name="todo" id="todo" />
</form>
END_FRAGMENT
print "$fragment\n";

