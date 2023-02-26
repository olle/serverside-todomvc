#!/usr/bin/perl

use strict;
use warnings;
use MIME::Base64;

use lib '.';
require Todos;
require Request;

Request->response();

foreach my $todo ( Todos->active() ) {
    my ( $id, $text, $editing ) =
      ( $todo->getId(), $todo->getText() );
    my $fragment;
    if ( $todo->isEditing() ) {
        $fragment = <<"END_FRAGMENT";
<li>
    <button name="complete" value="${id}" form="todo-item" title="Mark completed"></button>
    <form class="inline" method="post" action="cgi-bin/todos.pl">
    <input type="hidden" name="id" value="${id}" />
    <input name="update" value="${text}" autofocus required autocomplete="off" />
    </form>
</li>
END_FRAGMENT
    }
    else {
        $fragment = <<"END_FRAGMENT";
<li>
   <button name="complete" value="${id}" form="todo-item" title="Mark completed"></button>
   <button name="edit" value="${id}" form="todo-item" title="Click to edit">${text}</button>
   <button name="delete" value="${id}" form="todo-item" title="Delete todo item">&#x2715;</button>
</li> 
END_FRAGMENT
    }
    print "$fragment\n";
}

