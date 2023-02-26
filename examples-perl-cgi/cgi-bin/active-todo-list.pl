#!/usr/bin/perl

use strict;
use warnings;
use MIME::Base64;

use lib '.';
require Todos;
require Request;

Request->response();

foreach my $todo ( Todos->active() ) {
    my ( $id, $text ) = ( $todo->getId(), $todo->getText() );
    my $fragment = <<"END_FRAGMENT";
<li>
   <button name="complete" value="${id}" form="todo-item" title="Mark completed"></button>
   <button name="edit" value="${id}" form="todo-item" title="Click to edit">${text}</button>
   <button name="delete" value="${id}" form="todo-item" title="Delete todo item">&#x2715;</button>
</li> 
END_FRAGMENT
    print "$fragment\n";
}

#   <li> <!-- TODO: If the todo item is being edited use this:  -->
#     <button name="complete" value="{todo-id}" form="todo-item" title="Mark completed"></button>
#     <form class="inline" method="post" action="todos/{todo-id}">
#       <input type="hidden" name="id" value="{todo-id}" />
#       <input name="update" value="{todo-text}" autofocus required autocomplete="off" />
#     </form>
#   </li>

