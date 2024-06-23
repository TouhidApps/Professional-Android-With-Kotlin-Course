<?php


$clientFile = getcwd() . '/jsonKeys/google_api_client.json';

$serviceAccount = json_decode(file_get_contents($clientFile), true);


$projectName    = $serviceAccount['project_id'];
$apiToken       = $_POST['apiToken'];
$deviceToken    = $_POST['deviceToken'];
$pushType       = $_POST['pushType'];
$pushTitle      = $_POST['pushTitle'];
$pushBody       = $_POST['pushBody'];
$pushImage      = $_POST['pushImage'];


$payloadData = array('message' => array(

	'token' => $deviceToken,
	// 'notification' => array(
	// 	'title' => $pushTitle,
	// 	'body' => $pushBody,
	// 	'image' => $pushImage
	// ),
	'data' => array(
		'pushType' => $pushType,
		'pushTitle' => $pushTitle,
		'pushBody' => $pushBody,
		'pushImage' => $pushImage
	)

));

$payload = json_encode($payloadData);

$payloadSize = strlen($payload);

if ($payloadSize > 4096) {
	$msg = '{"errorMessage": "Payload (Push Data) size is over the 4KB limit! Please reduce the title or body or both text"}';
	echo $msg;
	die();
}


$url = "https://fcm.googleapis.com/v1/projects/" . $projectName . "/messages:send";

$headers = array(

	'Authorization: Bearer ' . $apiToken,
	'Content-Type: application/json'

);


$ch = curl_init();

curl_setopt($ch, CURLOPT_URL, $url);
curl_setopt($ch, CURLOPT_POST, 1);
curl_setopt($ch, CURLOPT_HTTPHEADER, $headers);
curl_setopt($ch, CURLOPT_POSTFIELDS, $payload);
curl_setopt($ch, CURLOPT_RETURNTRANSFER, true); // get string result
curl_setopt($ch, CURLOPT_SSL_VERIFYPEER, true);

$response = curl_exec($ch);
if (curl_errno($ch)) {
	throw new Exception("cURL Error: " . curl_error($ch));
}

curl_close($ch);


echo $response;






?>