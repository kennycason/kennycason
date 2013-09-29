# Author: Kenny Cason
# Date:   Nov 9
# Read File line by line

$numArgs = $#ARGV + 1;

if($numArgs >= 1) {
   	$data_file = $ARGV[0];
	open(DAT, $data_file) || die("Could not open file!");
	@raw_data=<DAT>;
	close(DAT);
	foreach $LINE_VAR (@raw_data) {
		@a = split('\t', $LINE_VAR);
      #		print @a[0]."\t".@a[4]."\t".@a[5]."\n";
       		print @a[0].",".@a[4]."\n";         
	}	
}
