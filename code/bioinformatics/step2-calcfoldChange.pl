# Author: Kenny Cason
# Date:   Nov 9
# Step 2 Calc Fold Change

$numArgs = $#ARGV + 1;

if($numArgs >= 1) {
   	$data_file = $ARGV[0];
	open(DAT, $data_file) || die("Could not open file!");
	@raw_data=<DAT>;
	close(DAT);
	
	
	foreach $LINE_VAR (@raw_data) {
		
		@a = split(',', $LINE_VAR);
       		print @a[0].",";
		# exp 1
		if(@a[1] != "NOUSE" && @a[2] != "NOUSE") { # if data is usable, take Log of ratio
			print log (@a[2] / @a[1]);
			print ",";
		} else {
			print "NOUSE,";
		}
 		# exp 2
		if(@a[3] != "NOUSE" && @a[4] != "NOUSE") { # if data is usable, take Log of ratio
			print log (@a[4] / @a[3]);
		} else {
			print "NOUSE";
		}
       		print "\n";
	}	
}
