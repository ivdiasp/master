<?php
 $to = "iwangulenko@googlemail.com ";
 $subject = "Hi!";
$x = $REMOTE_ADDR;
 if (mail($to, $subject, $x)) {
   echo("<p>Message successfully sent!</p>");
  } else {
   echo("<p>Message delivery failed...</p>");
  }
 ?>