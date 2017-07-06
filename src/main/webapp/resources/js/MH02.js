var getUrl = window.location;
var baseUrl = getUrl .protocol + "//" + getUrl.host;
var mainMH02;

function actionMH02(attribute, value) {
	disableTB();
	$('#'+attribute).val(value);
	if(attribute == 'sortType') {
		attribute = 'sort';
	} else if(attribute == 'currentPage') {
		attribute = 'page';
	}
	mainMH02.find('input[name="ssKey"]').attr("disabled", true);
	mainMH02.attr("action",baseUrl+"/ListUser/"+attribute+".do");
	mainMH02.submit();
}
$(function(){	
	mainMH02 = $('#listMain');	
	var backConst = $('#backconst').val();
	if(getUrl.pathname.includes(backConst)) {
		saveValTB();
	}
    $('#selectCom').on('change', function () {
    	window.localStorage.clear();
    	mainMH02.find(':input[name!="companyInternalID"]').attr("disabled", true);
    	mainMH02.attr("action",baseUrl+"/ListUser/change.do");
    	mainMH02.submit();
    });
    
    
    $('#register').click(function() {
    	mainMH02.find('input[name!="ssKey"]').attr("disabled", true);
    	mainMH02.attr("action",baseUrl+"/Register.do");
    	mainMH02.attr("method","POST");
		mainMH02.submit();
	})
	
	$('#btnSearch').click(function() {
		mainMH02.attr("action",baseUrl+"/ListUser/search.do");
		$('#ssKey').attr("disabled", true);
		saveValTB();
	})
           
});

function downloadCSV() {
	mainMH02.find("input[type='text']").each(function() {
		$("input[name = "+$(this).attr("name")+"]").val(window.localStorage[$(this).attr("name")]);
	})
	mainMH02.attr("action",baseUrl+"/downloadCSV.do");
	mainMH02.submit();
}

function saveValTB(){
	mainMH02.find("input[type='text']").each(function() {
		window.localStorage[$(this).attr("name")] = $("input[name = "+$(this).attr("name")+"]").val();
	})
}

function disableTB() {
	mainMH02.find("input[type='text']").each(function() {
		$("input[name = "+$(this).attr("name")+"]").val(window.localStorage[$(this).attr("name")]);
		if($(this).val() == '') {
			$(this).attr("disabled", true);
		}
	})
}