# Author: Kenny Cason
# Date:   Nov 9
# Read File line by line

$numArgs = $#ARGV + 1;

if($numArgs >= 1) {
   	$data_file = $ARGV[0];
	$start = $ARGV[1];
	$end = -1;
	if($numArgs >= 2) {
		$start = $ARGV[1];
	}
	if($numArgs >= 3) {
		$end = $ARGV[2];
	}
	open(DAT, $data_file) || die("Could not open file!");
	@raw_data=<DAT>;
	close(DAT);
	$i = 0;
	foreach $LINE_VAR (@raw_data) {
		if($i >= $start) {
 			if($end == -1) {
				print $LINE_VAR;
			} else {
				if($i <= $end) {
					print $LINE_VAR;
				}
			}
		}
		$i++;
	}	

}
