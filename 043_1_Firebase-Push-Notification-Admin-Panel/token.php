<?php


$clientFile = getcwd() . '/jsonKeys/google_api_client.json';

$serviceAccount = json_decode(file_get_contents($clientFile), true);


$tokenUri = $serviceAccount['token_uri'];
$scope = 'https://www.googleapis.com/auth/firebase.messaging';

$assertionPayload = [
	"iss" => $serviceAccount['client_email'],
	"scope" => $scope,
	"aud" => $tokenUri,
	"exp" => time() + 3600, // 1 hour
	"iat" => time()
];


function base64url_encode($data) {

	$base64 = base64_encode($data);
	$base64url = strtr($base64, '+/', '-_');
	$base64url = rtrim($base64url, '=');

	return $base64url;

}

$header = base64url_encode(json_encode(["alg" => "RS256", "typ" => "JWT"]));
$payload = base64url_encode(json_encode($assertionPayload));

$signature = '';

openssl_sign($header . '.' . $payload, $signature, $serviceAccount['private_key'], 'sha256');

$jwt = $header . '.' . $payload . '.' . base64url_encode($signature);

$postFields = [

	'grant_type' => 'urn:ietf:params:oauth:grant-type:jwt-bearer',
	'assertion' => $jwt

];

$ch = curl_init();

curl_setopt($ch, CURLOPT_URL, $tokenUri);
curl_setopt($ch, CURLOPT_POST, 1);
curl_setopt($ch, CURLOPT_POSTFIELDS, http_build_query($postFields));
curl_setopt($ch, CURLOPT_RETURNTRANSFER, true); // get string result
curl_setopt($ch, CURLOPT_SSL_VERIFYPEER, true);

$response = curl_exec($ch);
if (curl_errno($ch)) {
	throw new Exception("cURL Error: " . curl_error($ch));
}

curl_close($ch);

$responseData = json_decode($response, true);
if (isset($responseData['access_token'])) {
	$accessToken = $responseData['access_token'];
	echo $accessToken;
} else {
	echo "Failed to get access token. Response was: " . $response;
}





?>