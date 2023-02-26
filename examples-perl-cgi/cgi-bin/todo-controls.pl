#!/usr/bin/perl

use strict;
use warnings;
use MIME::Base64;

use lib '.';
require Todos;
require Request;

Request->response();

my ( $activeCount, $completedCount ) = Todos->metadata();
my $fragment = <<"END_FRAGMENT";
<h1>Todos <small title="${activeCount} Active items">${activeCount}</small></h1>
<form action="controls" method="post">
    <button name="clear" value="completed" title="Clear ${completedCount} completed">${completedCount} Completed • Clear</button>
    <!--
        TODO: Render the hide/show button depending on the current state of
            such a filter, to either show completed items or not.
    -->
    <button name="hide" value="completed" title="Hide completed todo items">Hide</button>
    <button name="show" value="completed" title="Show completed todo items">Show</button>
</form>
END_FRAGMENT
print "$fragment\n";

