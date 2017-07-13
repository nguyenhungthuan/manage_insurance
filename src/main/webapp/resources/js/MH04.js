var getUrl = window.location;
var baseUrl = getUrl .protocol + "//" + getUrl.host;

$(function() {
	window.localStorage.clear();
	var companyID = $('#companyID').val();
	if(companyID != 0) {
		$('input:radio[name="radioCom"][value="existedCom"]').attr("checked","checked");
		$('#existedCom').css("display", "inline");
		$("#newComTbl").css("display", "none");
	} else {
		$('input:radio[name="radioCom"][value="newCom"]').attr("checked","checked");
		$('#existedCom').css("display", "inline");
		$("#newComTbl").css("display", "none");		
	}
	loadData();
	
	$('input:radio[name="radioCom"]').change(loadData);
})

function loadData() {
	var radio = $('input:radio[name="radioCom"]:checked');
	if(radio.val() == "existedCom") {
		$("#newComTbl").css("display", "none");
        loadCBB();
	} else {
		$("#companyInternalID").val(0);
    	$("#newComTbl").css("display", "inline");
    	$("#existedCom").css("display", "none");
    }
}

function processName() {
	var fullName = $('#fullName').val();
	if(fullName.trim()) {
		$.ajax({
			type :  "GET",
			contentType : "application/json",
			url : baseUrl+"/processName.do",
			data : {
				fullName : fullName
			},
			dataType : 'text',
			success : function(json) {
				$("#fullName").val(json);
			},
			error: function(ts) { alert(ts.responseText) }
		})
	}
}

function loadCBB() {
	var comID = $('#companyID').val();
	$.ajax({
		type :  "GET",
		contentType : "application/json",
		url : baseUrl+"/loadCBB.do",
		dataType : 'json',
		success : function(json) {
			$('#existedCom').css("display", "inline");
			var select = $('#companyInternalID');
			select.find('option').remove();
			$.each(json, function(key, value) {
				$('<option>').val(parseInt(value.companyInternalId)).text(value.companyName).appendTo(select);
			})
			select.find('option[value='+parseInt(comID)+']').attr("selected",true);	
			loadComInfor();
		},
		error: function(ts) { alert(ts.responseText) }
	})
}

function loadComInfor() {
	var id = $('#companyInternalID').val();	
	$.ajax({
		type :  "GET",
		contentType : "application/json",
		url : baseUrl+"/loadComInfor.do",
		data : {
			companyID : id
		},
		dataType : 'json',
		success : function(json) {
			$("#comName").text(json.companyName);
			$("#addr").text(json.address);			
			$("#tel").text(json.tel);
			$("#email").text(json.email);
			$("#email").attr("href", "mailto:"+json.email);
		},
		error: function(ts) { alert(ts.responseText) }
	})
}
