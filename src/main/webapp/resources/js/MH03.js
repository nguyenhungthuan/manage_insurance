var getUrl = window.location;
var baseUrl = getUrl .protocol + "//" + getUrl.host + "/" + getUrl.pathname.split('/')[1];
$(function(){	
	window.localStorage.clear();
	$('#delete').click(function() {
		var msg = $("#msg_delete").val();
    	var result = confirm(msg);
    	if(result){
    		return true;
    	}	
    	return false;
    })
    
    $('#update').click(function(){
    	var userID = $('#userInternalID').val();
    	$('#viewMain').attr("action",baseUrl+"/Update/"+userID);
    	$('#viewMain').attr("method","POST");
    	$('#viewMain').submit();
    })
});