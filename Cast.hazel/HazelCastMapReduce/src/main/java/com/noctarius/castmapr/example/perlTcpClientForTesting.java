/*#!/usr/bin/perl

use IO::Socket::INET;
$| = 1;
print "please enter port no for the serve ";
my $portno=<>;

print "please enter query as";

print "please enter in order (one or more queries attributes) 1. <file name>" ."\n"." 2. <file permissions> "."\n"."3. <file size> "."\n"."4.<last modified time>";

my $socket = new IO::Socket::INET (
                PeerHost => '127.0.0.1',
                PeerPort => $portno,
                Proto => 'tcp',
                );
die "cannot connect to the server $!\n" unless $socket;
print "connected to the server\n";
my $cond=1;
my $filename=<>;
my $perm=<>;
my $size=<>;
my $time=<>;
chomp $filename;
chomp  $perm;
chomp $size;
$sendThisItem= "filename"." " . $filename . " " ."perm"." ". $perm . " "."size"." " . $size . " "."time"." " .$time;
print"sending ". $sendThisItem;

$size=$socket->send($sendThisItem);

print"size = ". $size;
my $response = "";
$socket->recv($response, 10240);
$response =~ s/\s/\n/g;
sort $response ;
print "received response: $response\n";

*/