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
		@a = split(',', $LINE_VAR);
       		print @a[0].",";
		if(@a[2] == "P" && @a[4] == "P") { # should be averaged
			print ((@a[1] + @a[3])/2);
			print ",";
		} elsif(@a[2] == "A" && @a[4] == "A") { # should not be used
			print "NOUSE,";
		} else { # the data with P should be used
			if(@a[2] == "P") {
				print @a[1];print ",";
			} else {
				print @a[3];print ",";
			}
		}   
		if(@a[6] == "P" && @a[8] == "P") { # should be averaged
			print ((@a[5] + @a[7])/2);print ",";
		} elsif(@a[6] == "A" && @a[8] == "A") { # should not be used
			print "NOUSE,";
		} else { # the data with P should be used
			if(@a[6] == "P") {
				print @a[5];print ",";
			} else {
				print @a[7];print ",";
			}
		}   
		if(@a[10] == "P" && @a[12] == "P") { # should be averaged
			print ((@a[9] + @a[11])/2);print ",";
		} elsif(@a[10] == "A" && @a[12] == "A") { # should not be used
			print "NOUSE,";
		} else { # the data with P should be used
			if(@a[10] == "P") {
				print @a[9];print ",";
			} else {
				print @a[11];print ",";
			}
		}    
		if(@a[14] == "P" && @a[16] == "P") { # should be averaged
			print ((@a[13] + @a[15])/2);
		} elsif(@a[14] == "A" && @a[16] == "A") { # should not be used
			print "NOUSE";
		} else { # the data with P should be used
			if(@a[14] == "P") {
				print @a[13];
			} else {
				print @a[15];
			}
		}
		print "\n";        
	}	
}
