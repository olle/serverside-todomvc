#!/usr/bin/perl

# -----------------------------------------------------------------------------
package Todo;

use strict;
use warnings;
use utf8;
use Cwd;
use MIME::Base64;
use Time::HiRes qw(gettimeofday);

sub new {
    my ( $class, $id, $status, $editing, $text ) = @_;
    my $self = {
        _id      => $id,
        _status  => $status,
        _text    => $text,
        _editing => $editing,
    };
    bless $self, $class;
    return $self;
}

sub create {
    my ( $__, $text ) = @_;
    my ( $s,  $ms )   = gettimeofday();
    return new Todo( "$s$ms", 0, 0, $text );
}

sub getId {
    my $self = shift;
    return $self->{_id};
}

sub getText {
    my $self = shift;
    my $text = $self->{_text};
    $text =~ s/</&lt;/g;
    return $text;
}

sub isCompleted {
    my $self = shift;
    return $self->{_status} == 1;
}

sub isActive {
    my $self = shift;
    return $self->{_status} == 0;
}

sub setStatus {
    my ( $self, $status ) = @_;
    $self->{_status} = $status;
}

sub setText {
    my ( $self, $text ) = @_;
    $self->{_text}    = $text;
    $self->{_editing} = 0;
}

sub setEditing {
    my $self = shift;
    $self->{_editing} = 1;
}

sub isEditing {
    my $self = shift;
    return $self->{_editing} == 1;
}

sub toLine() {
    my $self = shift;
    my $data = encode_base64( $self->{_text} );
    return "$self->{_id}|$self->{_status}|$self->{_editing}|$data";
}

sub parseLine {
    my ( $__, $line ) = @_;
    my ( $id, $status, $editing, $data ) = split( /\|/, $line );
    my $text = decode_base64($data);
    return new Todo( $id, $status, $editing, $text );
}

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
    my @todos;
    while (<IN>) {
        push( @todos, Todo->parseLine($_) );
    }
    close IN;
    return @todos;
}

sub __write {
    my (@todos) = @_;
    open( OUT, '>', $FILE ) or die $!;
    foreach my $todo (@todos) {
        print OUT $todo->toLine();
    }
    close(OUT);
}

sub __writeOne {
    my ($todo) = @_;
    open( OUT, '>>', $FILE ) or die $!;
    print OUT $todo->toLine();
    close(OUT);
}

sub active {
    my @allTodos = __read();
    my @activeTodos;
    foreach my $todo (@allTodos) {
        next if ( $todo->isCompleted() );
        push( @activeTodos, $todo );
    }
    return @activeTodos;
}

sub completed {
    my @allTodos = __read();
    my @completedTodos;
    foreach my $todo (@allTodos) {
        next if ( $todo->isActive() );
        push( @completedTodos, $todo );
    }
    return @completedTodos;
}

sub add {
    my ( $__, $text ) = @_;
    my $todo = Todo->create($text);
    __writeOne($todo);
}

sub __updateStatus {
    my ( $todoId, $newStatus ) = @_;
    my @oldTodos = __read();
    my @newTodos;
    foreach my $todo (@oldTodos) {
        if ( $todo->getId() == $todoId ) {
            $todo->setStatus($newStatus);
        }
        push( @newTodos, $todo );
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
    foreach my $todo (@oldTodos) {
        next if ( $todo->getId() == $todoId );
        push( @newTodos, $todo );
    }
    __write(@newTodos);
}

sub clear {
    my @oldTodos = __read();
    my @newTodos;
    foreach my $todo (@oldTodos) {
        next if ( $todo->isCompleted() );
        push( @newTodos, $todo );
    }
    __write(@newTodos);
}

sub edit {
    my ( $__, $todoId ) = @_;
    my @oldTodos = __read();
    my @newTodos;
    foreach my $todo (@oldTodos) {
        if ( $todo->getId() == $todoId ) {
            $todo->setEditing();
        }
        push( @newTodos, $todo );
    }
    __write(@newTodos);
}

sub update {
    my ( $__, $todoId, $text ) = @_;
    my @oldTodos = __read();
    my @newTodos;
    foreach my $todo (@oldTodos) {
        if ( $todo->getId() == $todoId ) {
            $todo->setText($text);
        }
        push( @newTodos, $todo );
    }
    __write(@newTodos);
}

sub metadata {
    my @todos = __read();
    my ( $activeCount, $completedCount, $isEditing ) = ( 0, 0, 0 );
    foreach my $todo (@todos) {
        if ( $todo->isEditing() ) {
            $isEditing = 1;
        }
        if ( $todo->isCompleted() ) {
            $completedCount++;
        }
        else {
            $activeCount++;
        }
    }
    return ( $activeCount, $completedCount, $isEditing );
}

1;