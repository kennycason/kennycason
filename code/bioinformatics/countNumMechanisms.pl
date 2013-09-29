# Author: Kenny Cason

use Data::Dumper;

$numArgs = $#ARGV + 1;
my %hashIDMap;
if($numArgs >= 1) {
   	$data_file1 = $ARGV[0];
	open(DAT, $data_file1) || die("Could not open file!");
	@raw_data1=<DAT>;
	close(DAT);
	my %hash;
	foreach $LINE_VAR (@raw_data1) {
	#	print $LINE_VAR;
		if ($hash{$LINE_VAR} != 0) {
			$hash{$LINE_VAR}++;
			#print "EXISTS";
		} else {
			#print "PUSH";
			push @{$hash},$LINE_VAR;
			$hash{$LINE_VAR} = 1;
		}
		#print $LINE_VAR;
	}	

	print Dumper( \%hash);

}
