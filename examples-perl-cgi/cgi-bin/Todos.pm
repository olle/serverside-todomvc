#!/usr/bin/perl

# -----------------------------------------------------------------------------
package Todo;

# -----------------------------------------------------------------------------
package Todos;

use strict;
use warnings;
use utf8;
use Cwd;
use MIME::Base64;
use Time::HiRes qw(gettimeofday);

my $PWD  = getcwd();
my $FILE = "$PWD/todos.dat";

sub __read {
    open( IN, '<', $FILE ) or die $!;
    chomp( my @inputLines = <IN> );
    close IN;
    return @inputLines;
}

sub __write {
    my (@outputLines) = @_;
    open( OUT, '>', $FILE ) or die $!;
    foreach (@outputLines) {
        print OUT "$_\n";
    }
    close(OUT);
}

sub active {
    my @allTodos = __read();
    my @activeTodos;
    foreach (@allTodos) {
        my ( $id, $status, $data ) = split( /\|/, $_ );
        next if ( $status == 1 );
        push( @activeTodos, $_ );
    }
    return @activeTodos;
}

sub completed {
    my @allTodos = __read();
    my @completedTodos;
    foreach (@allTodos) {
        my ( $id, $status, $data ) = split( /\|/, $_ );
        next if ( $status == 0 );
        push( @completedTodos, $_ );
    }
    return @completedTodos;
}

sub add {
    my ( $__, $text ) = @_;
    my ( $s, $ms )    = gettimeofday();
    my $id     = "$s$ms";
    my $status = 0;
    my $data   = encode_base64($text);
    open( OUT, '>>', $FILE ) or die $!;
    print OUT "$id|$status|$data";
    close(OUT);
}

sub __updateStatus {
    my ( $todoId, $newStatus ) = @_;
    my @oldTodos = __read();
    my @newTodos;
    foreach (@oldTodos) {
        my ( $id, $status, $data ) = split( /\|/, $_ );
        if ( $id == $todoId ) {
            push( @newTodos, "$id|$newStatus|$data" );
        }
        else {
            push( @newTodos, "$id|$status|$data" );
        }
    }
    __write(@newTodos);
}

sub complete {
    my ( $__, $todoId ) = @_;
    __updateStatus( $todoId, 1 );
}

sub activate {
    my ( $__, $todoId ) = @_;
    __updateStatus( $todoId, 0 );
}

sub delete {
    my ( $__, $todoId ) = @_;
    my @oldTodos = __read();
    my @newTodos;
    foreach (@oldTodos) {
        my ( $id, $status, $data ) = split( /\|/, $_ );
        next if ( $id == $todoId );
        push( @newTodos, "$id|$status|$data" );
    }
    __write(@newTodos);
}

1;