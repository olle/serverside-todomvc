#!/usr/bin/perl
use strict;
use warnings;
use utf8;
use Cwd;
use MIME::Base64;

print "Content-Type: text/plain; charset=utf-8\n\n";

my $PWD = getcwd();
open(FH, '<', "$PWD/todos.dat") or die $!;
while(<FH>){
   my ($id,$value) = split(/\|/, $_);
   my $todo = decode_base64($value);
   $todo =~ s/</&lt;/g;
   my $fragment = <<"END_FRAGMENT";
<li>
   <button name="complete" value="${id}" form="todo-item" title="Mark completed"></button>
   <button name="edit" value="${id}" form="todo-item" title="Click to edit">${todo}</button>
   <button name="delete" value="${id}" form="todo-item" title="Delete todo item">&#x2715;</button>
</li> 
END_FRAGMENT
   print "$fragment\n";
}
close(FH);
