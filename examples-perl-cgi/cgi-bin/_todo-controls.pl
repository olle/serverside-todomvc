#!/usr/bin/perl

use strict;
use warnings;
use MIME::Base64;

use lib '.';
require Todos;
require Request;

Request->response();

my ( $activeCount, $completedCount, $isEditing, $hideCompleted ) =
  Todos->metadata();

my $FILTER_BUTTON =
  $hideCompleted
  ? '<button name="show" value="completed" title="Show completed todo items">Show</button>'
  : '<button name="hide" value="completed" title="Hide completed todo items">Hide</button>';

my $fragment = <<"END_FRAGMENT";
<h1>Todos <small title="${activeCount} Active items">${activeCount}</small></h1>
<form action="cgi-bin/controls.pl" method="post">
    <button name="clear" value="completed" title="Clear ${completedCount} completed">${completedCount} Completed • Clear</button>
    ${FILTER_BUTTON}
</form>
END_FRAGMENT
print "$fragment\n";

