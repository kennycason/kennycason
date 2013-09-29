# Author: Kenny Cason

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
			if(@a[0] eq @a2[0]) {
				print @a2[1];
   			}
		}
			
	}	

}
