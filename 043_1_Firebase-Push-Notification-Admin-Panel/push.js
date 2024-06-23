hideAlert();
var myApiToken = "";

$('#btnToken').click(function() {
	hideAlert();
	loadApiToken();
});

$('#btnSubmit').click(function() {
	hideAlert();
	sendPush();
});

$('#btnAlert').click(function() { // close alert button
	hideAlert();
});



function showAlert(msg) {
	$('#errorMessage').html(msg);
	$('#errorMessageDiv').removeAttr("hidden", '');
}

function hideAlert() {
	$('#errorMessage').html('');
	$('#errorMessageDiv').attr("hidden", '');
}


function loadApiToken() {

	$.ajax({
		url: 'token.php',
		type: 'GET',
		success: function(msg) {
			console.log("Success..." + msg);

			myApiToken = msg;
			$('#token').html("API Token Generated: " + msg.substring(0, 20) + "...");
			$('#btnInfoToken').attr("hidden", "");
			$('#btnToken').attr("hidden", "");

			startCountDown();

		}
	});

} // loadApiToken



function makeTokenEmpty() {

	myApiToken = '';
	$("#token").html('');
	$("#tokenCounter").html('');
	$("#btnToken").removeAttr("hidden", "");
	$("#btnInfoToken").removeAttr("hidden", "");
	
}


function startCountDown() {
	var timer2 = "59:01";
	var interval = setInterval(function() {

	  var timer = timer2.split(':');
	  //by parsing integer, I avoid all extra string processing
	  var minutes = parseInt(timer[0], 10);
	  var seconds = parseInt(timer[1], 10);
	  --seconds;
	  minutes = (seconds < 0) ? --minutes : minutes;
	  if (minutes < 0) clearInterval(interval);
	  seconds = (seconds < 0) ? 59 : seconds;
	  seconds = (seconds < 10) ? '0' + seconds : seconds;
	  //minutes = (minutes < 10) ?  minutes : minutes;
	  $('#tokenCounter').html(minutes + ':' + seconds);
	  timer2 = minutes + ':' + seconds;

	  if (minutes < 0) {
	  	makeTokenEmpty();
	  }

	}, 1000);
} // startCountDown


function sendPush() {
	
	if (myApiToken == '') {
		showAlert("Please generate google API token first (Press enable button)");
		return;
	}


	var isDeviceToken = $("#deviceToken").prop('checked');
	var tokenOrTopic  = $("#pushToken").val().trim();
	var pushType      = $("#pushType").val().trim();
	var pushTitle     = $("#pushTitle").val().trim();
	var pushBody      = $("#pushBody").val().trim();
	var pushImage     = $("#pushImage").val().trim();


	if (!tokenOrTopic) {
		showAlert("Please enter token or topic");
		return;
	}

	if (!pushTitle) {
		showAlert("Please enter a title");
		return;
	}

	if (!pushBody) {
		showAlert("Please enter a body");
		return;
	}


	const dataPayload = {
		apiToken    : myApiToken,
		deviceToken : isDeviceToken ? tokenOrTopic : "/topics/" + tokenOrTopic,
		pushType    : pushType,
		pushTitle   : pushTitle,
		pushBody    : pushBody,
		pushImage   : pushImage,
	}

	const jsonStrNewLine = JSON.stringify(dataPayload, null, "\t");
	$("#pushRequestObject").html("Request JSON: \n<pre>" + jsonStrNewLine + "</pre>");

	$.ajax({

		url: 'push.php',
		type: 'POST',
		data: dataPayload,
		success: function(data) {
			$("#pushResponseObject").html("Response: <br/> <pre>" + data + "</pre>");

			try {

				var jsonObject = JSON.parse(data);
				if (jsonObject.error && jsonObject.error.status && jsonObject.error.status === "UNAUTHENTICATED") {
					showAlert("Authenticate error, Reload the page and try again.");
				}

				if (jsonObject.name) {
					showAlert("Push send success ✅ " + jsonObject.name);
				} else {

					if (jsonObject.errorMessage) {
						showAlert(jsonObject.errorMessage);
					} else {
						showAlert("Something went wrong! Please see the response data!");
					}

				}

			} catch(e) {
				console.log("Error parsing JSON: ", e);
			}


		},
		error: function (xhr, status, error) {
			$("#pushResponseObject").html("Response:<br/>Something went wrong: (Please check: Server Key/Token/Topic) " + error);
			$("pushResponseObject").css("background-color", "red");
			console.log(error);
		}

	});

} // sendPush

function setPushDataToUI(pushType, title, body, imgUrl) {

	$('#pushType').val(pushType);
	$('#pushTitle').val(title);
	$('#pushBody').val(body);
	$('#pushImage').val(imgUrl);

	hideAlert();
	$('#pushRequestObject').html('');
	$('#pushResponseObject').html('');

}

function loadAppContent() {

	$.ajax({

		url: 'loadAppData.php',
		type: 'GET',
		dataType: 'json',
		success: function(data) {
			
			let d = JSON.stringify(data, null, "\t");
			var fullListHtml = "";

			for (var i = 0; i < data.myJsonObject.length; i++) {
				
				var mName = data.myJsonObject[i].name;
				var mDetails = data.myJsonObject[i].details;
				var mImgUrl = data.myJsonObject[i].imgData.baseUrl + data.myJsonObject[i].imgData.fileName;

				var myDataRow = '<a href="#" onClick="setPushDataToUI(\'MY_CONTENT\', \'' + mName + '\', \'' + mDetails + '\', \'' + mImgUrl + '\')" class="list-group-item list-group-item-action flex-column align-items-start">';

					myDataRow += '<div class="d-flex w-100 justify-content-start">';

					myDataRow += '<img src="' + mImgUrl + '" alt="' + mName + '" class="img-fluid mr-3" style="width: 100px">';

					myDataRow += '<div class="ms-3">';

					myDataRow += '<h5 class="mb-1">' + mName + '</h5>';
					myDataRow += '<p class="mb-1">' + mDetails + '</p>';
					myDataRow += '</div>';
					myDataRow += '</div>';

				myDataRow += '</a>';

				fullListHtml += myDataRow;

			}

			$('#myDataList').html(fullListHtml);

		},
		error: function (xhr, status, error) {
			console.log(error);
		}

	});
	
} // loadAppContent

loadAppContent();

