<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<title>Firebase Push Admin</title>

	<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">

	<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM" crossorigin="anonymous"></script>

	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>


</head>
<body class="container">

	<h1 class="text-center">Firebase Messeging (Push Notification)</h1>

	<hr/>
	<br/>

	<div class="card" style="width: 100%">
		<div class="card-body">

			<p class="form-text" id="btnInfoToken">
				Click below button to get Google API token for one hour.
			</p>

			<input type="submit" id="btnToken" value="Enable Push Service" class="btn btn-outline-danger btn-sm" />

			<p id="token"></p>
			<p class="text-info" id="tokenCounter"></p>

		</div>
	</div>

	<br/>

<!-- Device token / topic -->
	<div class="row">
		<div class="col-sm-2 col-12">
			<label for="pushToken" class="form-label">Device Token / Topic:</label>
		</div>
		<div class="col-sm-1 col-12">
			<input type="radio" name="idDeviceToken" value="0" id="deviceToken" />
			<label for="deviceToken" class="form-text">Token</label><br/>
			<input type="radio" name="idDeviceToken" value="1" id="topic" checked />
			<label for="topic" class="form-text">Topic</label><br/>
		</div>

		<div class="col-sm-9 col-12">
			<input type="text" placeholder="Push Token or Topic" id="pushToken" value="global" class="form-control">
		</div>

		<div class="form-text">
			&emsp; From app you can get device token to send to a specific device. If you want to send a group of user then use topic which is subscribed from the app.
		</div>
	</div>

	<br/>

<!-- Push Type -->
	<div class="row">
		<div class="col-sm-2 col-12">
			<label for="pushType" class="form-label">Push Type::</label>
		</div>

		<div class="col-sm-10 col-12">
			<input type="text" placeholder="Push Type" id="pushType" value="general" class="form-control">
		</div>

		<div class="form-text">
			&emsp; App is handling what type of push you are sending. Ex: general, transaction, greetings. Can be empty if app is not handling it.
		</div>
	</div>

	<br/>
<!-- Push Title -->
	<div class="row">
		<div class="col-sm-2 col-12">
			<label for="pushTitle" class="form-label">Push Title:</label>
		</div>

		<div class="col-sm-10 col-12">
			<input type="text" placeholder="Push Title" id="pushTitle" class="form-control">
		</div>

		<div class="form-text">
			&emsp; Title will show as a push notificatin title.
		</div>
	</div>

	<br/>
<!-- Push Body -->
	<div class="row">
		<div class="col-sm-2 col-12">
			<label for="pushBody" class="form-label">Push Body:</label>
		</div>

		<div class="col-sm-10 col-12">
			<input type="text" placeholder="Push Body" id="pushBody" class="form-control">
		</div>

		<div class="form-text">
			&emsp; Body will show as a push detail text.
		</div>
	</div>

	<br/>
<!-- Push Image -->
	<div class="row">
		<div class="col-sm-2 col-12">
			<label for="pushImage" class="form-label">Push Image:</label>
		</div>

		<div class="col-sm-10 col-12">
			<input type="text" placeholder="Push Image" id="pushImage" class="form-control">
		</div>

		<div class="form-text">
			&emsp; Image will show in notification.
		</div>
	</div>

	<br/>
	<p id="pushResponse"></p>
	<br>

	<div id="errorMessageDiv" class="alert alert-info alert-dismissible fade show" >

		<strong>Alert!</strong> <span id="errorMessage"></span>
		<button id="btnAlert" type="button" class="btn-close"></button>
		
	</div>

	<br/>

	<input type="submit" id="btnSubmit" value="Send Push" class="btn btn-outline-info"/>

	<br/>
	<br/>
	<p id="pushRequestObject"></p>
	<p id="pushResponseObject"></p>


	<!-- My App / website content list -->

	<h3 class="text-center">You can choose an item to fill up push data</h3>
	<div class="list-group" id="myDataList">
		 <!-- <a href="#" class="list-group-item list-group-item-action flex-column align-items-start">

		 	<div class="d-flex w-100 justify-content-start">
			 	<img src="https://via.placeholder.com/100" alt="Content Image" class="img-fluid mr-3" style="width: 100px">
			 	<div class="ms-3">
			 		<h5 class="mb-1">Title of item 1</h5>
			 		<p class="mb-1">Subtitle of item 1</p>
			 	</div>
		 	</div>
		 	
		 </a> -->
	</div>


	<br/>
	<br/>
	<br/>
	<br/>


	<script type="text/javascript" src="push.js"></script>

</body>
</html>












