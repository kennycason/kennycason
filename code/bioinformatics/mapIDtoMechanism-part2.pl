# Author: Kenny Cason
# Date:   Nov 9
# Read File line by line

use Data::Dumper;

$numArgs = $#ARGV + 1;
my %hashIDMap;
if($numArgs >= 1) {
   	$data_file1 = $ARGV[0];
        $data_file2 = $ARGV[1];
	open(DAT, $data_file1) || die("Could not open file!");
	@raw_data1=<DAT>;
	close(DAT);

	open(DAT, $data_file2) || die("Could not open file!");
	@raw_data2=<DAT>;
	close(DAT);
	$i = 0;
	foreach $LINE_VAR (@raw_data1) {
		@a = split('\t', $LINE_VAR);
		chomp @a;
		#print @a[0]."\n";
		foreach $LINE2_VAR (@raw_data2) {
			@a2 = split('\t', $LINE2_VAR);
			#print @a[1]."\t".@a2[0]."\n";
			if(@a[1] eq @a2[0]) {
				print @a[0]."\t".@a2[1]."\n";
   			}
		}
			
	}	

}
