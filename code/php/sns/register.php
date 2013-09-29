<!DOCTYPE HTML PUBLIC '-//W3C//DTD HTML 4.01//EN'
            'http://www.w3.org/TR/html4/strict.dtd'>

<?php
// require 'imageverification.php';
 $error_msg = $_REQUEST['error_msg'];


echo "
<html lang='en'>
<head>
	<title>SNS Registration</title>
	<meta http-equiv='content-type' content='text/html; charset=utf-8'>
	<meta name='author' content='kenny cason'>
	<meta name='keywords' content=''>
	<meta name='description' content=''>
	<link rel='stylesheet' type='text/css' href='css/layout.css'>
</head>
<body>
	<h2>SNS Registration</h2>
	<form action='confirmregistration.php'>
		<p>Please complete the form below.<req>{$error_msg}</req> </p>
		<fieldset class='information'>
			<legend>Name</legend>
			<div>
				<label for='fName'><req>*</req>First Name</label> <input type='text' id='fName' name='fName'>
			</div>
			<div>
				<label for='lName'><req>*</req>Last Name</label> <input type='text' id='lName' name='lName'>
			</div>
		</fieldset>
		<fieldset class='information'>
			<legend>Email</legend>
			<div>
				<label for='Email'><req>*</req>Email</label> <input type='text' id='Email' name='Email'>
			</div>
			<div>
				<label for='Email2'><req>*</req>Retype Email</label> <input type='text' id='Email2' name='Email2'>
			</div>
		</fieldset>
		<fieldset class='information'>
			<legend>Password</legend>
			<div>
				<label for='Password'><req>*</req>Password</label> <input type='password' id='Password' name='Password'>
			</div>
			<div>
				<label for='Password2'><req>*</req>Retype Password</label> <input type='password' id='Password2' name='Password2'>
			</div>
		</fieldset>
		<fieldset class='information'>
			<legend>User Details</legend>
			<div class='radio'>
				<fieldset>
					<legend><span>Gender</span></legend>
					<div>
						<input type='radio' id='male' name='gender' value='Male'> <label for='male'>Male</label>
					</div>
					<div>
						<input type='radio' id='female' name='gender' value='Female'> <label for='female'>Female</label>
					</div>
				</fieldset>
			</div>
		
					<div>
						<label for='dob_year'>Year</label>
						<select id='dob_year' name='dob_year'>
							<option value='0000'>0000</option><option value='1931'>1931</option><option value='1932'>1932</option><option value='1933'>1933</option><option value='1934'>1934</option><option value='1935'>1935</option><option value='1936'>1936</option><option value='1937'>1937</option><option value='1938'>1938</option><option value='1939'>1939</option><option value='1940'>1940</option><option value='1941'>1941</option><option value='1942'>1942</option><option value='1943'>1943</option><option value='1944'>1944</option><option value='1945'>1945</option><option value='1946'>1946</option><option value='1947'>1947</option><option value='1948'>1948</option><option value='1949'>1949</option><option value='1950'>1950</option><option value='1951'>1951</option><option value='1952'>1952</option><option value='1953'>1953</option><option value='1954'>1954</option><option value='1955'>1955</option><option value='1956'>1956</option><option value='1957'>1957</option><option value='1958'>1958</option><option value='1959'>1959</option><option value='1960'>1960</option><option value='1961'>1961</option><option value='1962'>1962</option><option value='1963'>1963</option><option value='1964'>1964</option><option value='1965'>1965</option><option value='1966'>1966</option><option value='1967'>1967</option><option value='1968'>1968</option><option value='1969'>1969</option><option value='1970'>1970</option><option value='1971'>1971</option><option value='1972'>1972</option><option value='1973'>1973</option><option value='1974'>1974</option><option value='1975'>1975</option><option value='1976'>1976</option><option value='1977'>1977</option><option value='1978'>1978</option><option value='1979'>1979</option><option value='1980'>1980</option><option value='1981'>1981</option><option value='1982'>1982</option><option value='1983'>1983</option><option value='1984'>1984</option><option value='1985'>1985</option><option value='1986'>1986</option><option value='1987'>1987</option><option value='1988'>1988</option><option value='1989'>1989</option><option value='1990'>1990</option><option value='1991'>1991</option><option value='1992'>1992</option><option value='1993'>1993</option><option value='1994'>1994</option><option value='1995'>1995</option><option value='1996'>1996</option><option value='1997'>1997</option><option value='1998'>1998</option><option value='1999'>1999</option><option value='2000'>2000</option><option value='2001'>2001</option><option value='2002'>2002</option><option value='2003'>2003</option><option value='2004'>2004</option><option value='2005'>2005</option><option value='2006'>2006</option><option value='2007'>2007</option><option value='2008'>2008</option>
						</select>
					</div>
					<div>
						<label for='dob_month'>Month</label>
						<select id='dob_month' name='dob_month'>
							<option value='1'>January</option><option value='2'>February</option><option value='3'>March</option><option value='4'>April</option><option value='5'>May</option><option value='6'>June</option><option value='7'>July</option><option value='8'>August</option><option value='9'>September</option><option value='10'>October</option><option value='11'>November</option><option value='12'>December</option>

						</select>
					</div>
					<div>
						<label for='dob_day'>Day</label>
						<select id='dob_day' name='dob_day'>
							<option value='1'>1</option><option value='2'>2</option><option value='3'>3</option><option value='4'>4</option><option value='5'>5</option><option value='6'>6</option><option value='7'>7</option><option value='8'>8</option><option value='9'>9</option><option value='10'>10</option><option value='11'>11</option><option value='12'>12</option><option value='13'>13</option><option value='14'>14</option><option value='15'>15</option><option value='16'>16</option><option value='17'>17</option><option value='18'>18</option><option value='19'>19</option><option value='20'>20</option><option value='21'>21</option><option value='22'>22</option><option value='23'>23</option><option value='24'>24</option><option value='25'>25</option><option value='26'>26</option><option value='27'>27</option><option value='28'>28</option><option value='29'>29</option><option value='30'>30</option><option value='31'>31</option>
						</select>
  					</div>
 		</fieldset>
		<div><button type='submit' id='submit'>Register</button></div>
	</form>
</body></html>";


/*
                        <div>
                        <img src='imageverification.php' title='Verify' />
			</div>
*/
?>




