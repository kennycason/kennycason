---
title: Mailer - Send HTML Mail / Attachments - PHP
author: Kenny Cason
tags: php
---

This is a sample class to send mail using PHP. It also demonstrates how to attach images.


```php
<?php

class Mail {
    var $parts;
    var $to;
    var $cc;
    var $bcc;
    var $from;
    var $headers;
    var $subject;
    var $body;
    var $html;
    var $host;
    var $port;

    function __construct() {
        $this->parts = array();
        $this->to = "";
        $this->cc = "";
        $this->bcc = "";
        $this->from = "";
        $this->subject = "";
        $this->body = "";
        $this->headers = "";
        $this->html = false;
    }

    function addAttachment($message,$name = "",$ctype = "application/octet-stream") {
        $this->parts[] = array(
                    "ctype" => $ctype,
                    "message" => $message,
                    "encode" => "base64",
                    "name" => $name
                    );            
    }

    function buildMessage($part) {
        $message = $part["message"];
        $message = chunk_split(base64_encode($message));
        $encoding = "base64";
        return "Content-Type: ".$part["ctype"].($part["name"]? ";name = \"".$part["name"]."\"" : "").
               "\nContent-Transfer-Encoding: $encoding\n\n$message\n";
    }

    function buildMultipart() {
        $boundry = "HKC".md5(uniqid(time()));
        $multipart = "Content-Type: multipart/mixed; boundary =  \"$boundry\"\n\n";
        $multipart .= "This is a MIME encoded message.\n\n--$boundry";
        for($i = sizeof($this->parts)-1; $i >= 0; $i--) {
            $multipart .= "\n".$this->buildMessage($this->parts[$i])."--$boundry";
        }
        return $multipart .= "--\n";
   }

    function getMail($complete = true) {
        $mime = "";
        if(!empty($this->from)) {
            $mime .= "From: ".$this->from."\n";
        }
        if(!empty($this->headers)) {
            $mime .= $this->headers."\n";
        }
        if($complete) {
            if(!empty($this->cc)) {
                $mime .= "Cc: ".$this->cc."\n";
            }
            if(!empty($this->bcc)) {
                $mime .= "Bcc: ".$this->bcc."\n";
            }
            if(!empty($this->subject)) {
                $mime .= "Subject: ".$this->subject."\n";
            }
        }     
        if(!empty($this->body)) {
            $this->addAttachment($this->body,"",($this->html? "text/html":"text/plain"));
        }
        $mime .=  "MIME-Version: 1.0\n".$this->buildMultipart();
        return $mime;
    }

    function send() {
        if(!empty($this->cc)) {
            $mime = $this->getMail(true);
        } else {
            $mime = $this->getMail(false);
        }
        if(!empty($this->host)) {
             ini_set("SMTP",$this->host);
        }
        return mail($this->to,$this->subject,$this->body,$mime);
    }
}
```
Usage
```php
<?php
$fp = fopen("somepicture.jpg","r");
$data = fread($fp,filesize("somepicture.jpg"));
fclose($fp);

$mail = new Mail();
$mail->from = "me@somewhere.com";
$mail->to = "you@somewhere.com";
$mail->subject = "welcome";
$mail->body = "<b>How's it going?</b>";
$mail->html = true;
$mail->addAttachment($data,"somepicture.jpg" ,"image/jpeg" );
$mail->send();

```
