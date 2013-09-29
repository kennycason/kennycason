# Author: Kenny Cason
# Date:   Nov 9
# Step 2 Calc Fold Change

$numArgs = $#ARGV + 1;

if($numArgs >= 1) {
   	$data_file = $ARGV[0];
	open(DAT, $data_file) || die("Could not open file!");
	@raw_data=<DAT>;
	close(DAT);
	
	$fileOut = 0;
	if($numArgs >= 2) {
		open FILEOUT, ">$ARGV[1]" or die "unable to open $file $!";
		$fileOut = 1;	
	}

	$sumRoots = 0.0;
	$nRoots = 0;
	$sumShoots = 0.0;
	$nShoots = 0;
	foreach $LINE_VAR (@raw_data) {
		@a = split(',', $LINE_VAR);
		# exp 1
		if(@a[1] != "NOUSE") { 
			$nRoots++;
			$sumRoots += @a[1];	
		}
 		# exp 2
		if(@a[2] != "NOUSE") { 
			$nShoots++;
			$sumShoots += @a[2];	
		}
	}	

	$meanRoots = $sumRoots/$nRoots;
	$meanShoots = $sumShoots/$nShoots;
	
	$stdDevRoots = 0;
	$stdDevShoots = 0;
	foreach $LINE_VAR (@raw_data) {
		@a = split(',', $LINE_VAR);
		# exp 1
		if(@a[1] != "NOUSE") { 
			$stdDevRoots += ((@a[1] - $meanRoots) ** 2);	
		}
 		# exp 2
		if(@a[2] != "NOUSE") { 
			$stdDevShoots += ((@a[2] - $meanShoots) ** 2);	
		}
	}
	$stdDevRoots = sqrt ($stdDevRoots / ($nRoots - 1));
	$stdDevShoots = sqrt ($stdDevShoots / ($nShoots - 1));

	print "STD DEVS = (roots) ".$stdDevRoots.", (shoots)".$stdDevShoots."\n";
	
	$twoStdDevRoots = $stdDevRoots * 2;
	$twoStdDevShoots = $stdDevShoots * 2;
	
	$numRootsAbove2StdDev = 0;
	$numShootsAbove2StdDev = 0;
	$numRootsBelow2StdDev = 0;
	$numShootsBelow2StdDev = 0;
	foreach $LINE_VAR (@raw_data) {
		@a = split(',', $LINE_VAR);
		# exp 1
		if(@a[1] != "NOUSE") { 
			if(@a[1] > $meanRoots + $twoStdDevRoots) {
				print FILEOUT @a[0]."\n";
				$numRootsAbove2StdDev++;
			}
			if(@a[1] < $meanRoots - $twoStdDevRoots) {
				$numRootsBelow2StdDev++;
			} 		
		}
 		# exp 2
		if(@a[2] != "NOUSE") { 
			if(@a[2] > $meanShoots + $twoStdDevShoots) {
				print FILEOUT @a[0]."\n";
				$numShootsAbove2StdDev++;
			}	
			if(@a[2] < $meanShoots - $twoStdDevShoots) {
				$numShootsBelow2StdDev++;
			} 
		}
	}	
	print "Num Roots Fold w/ Fold Change > 2 Std. Dev. ". $numRootsAbove2StdDev."\n".
		"Num Shoots Fold w/ Fold Change > 2 Std. Dev. ". $numShootsAbove2StdDev."\n".
		"Num Roots Fold w/ Fold Change < 2 Std. Dev. ". $numRootsBelow2StdDev."\n".
		"Num Shoots Fold w/ Fold Change < 2 Std. Dev. ". $numShootsBelow2StdDev."\n".
		"Out of: Max Roots = ".$nRoots." and Max Shoots = ".$nShoots."\n";


}
