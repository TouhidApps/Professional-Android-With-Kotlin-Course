<?php

$url = "https://touhidapps.com/api/demo/jsondemoapi.php?option=2";
$header = array(
	"Content-Type: application/json"
); 

$ch = curl_init();

curl_setopt($ch, CURLOPT_URL, $url);
curl_setopt($ch, CURLOPT_POST, 0);
curl_setopt($ch, CURLOPT_HTTPHEADER, $header);
// curl_setopt($ch, CURLOPT_POSTFIELDS, http_build_query($postFields));
curl_setopt($ch, CURLOPT_RETURNTRANSFER, true); // get string result
curl_setopt($ch, CURLOPT_SSL_VERIFYPEER, true);

$response = curl_exec($ch);
if (curl_errno($ch)) {
	throw new Exception("cURL Error: " . curl_error($ch));
}

curl_close($ch);

echo $response;





?>