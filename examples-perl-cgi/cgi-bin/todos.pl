#!/usr/bin/perl

use strict;
use warnings;

use lib '.';
require Request;
require Todos;

my ( $__, $todo ) = Request->params();
Todos->add($todo);

Request->redirect("/");
