# Author: Kenny Cason
# Date:   Nov 9
# Read File line by line

use Data::Dumper;

$numArgs = $#ARGV + 1;
my %hashIDMap;
if($numArgs >= 1) {
   	$data_file1 = $ARGV[0];
	open(DAT, $data_file1) || die("Could not open file!");
	@raw_data1=<DAT>;
	close(DAT);

	foreach $LINE_VAR (@raw_data1) {
		@a = split(',', $LINE_VAR);
		@a2 = split(';', @a[1]);
		chomp @a2;
		for($i = 0; $i <  @a2; $i++) {
			print @a[0]."\t".@a2[$i]."\n";
    
		}
	}	

}
