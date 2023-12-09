
$(document).ready(function(){
	
	var langValue = $.cookie("lang");
	if( langValue == "" || langValue == undefined ){
		langValue = "eng";
//		langValue = langValue.toUpperCase();
		$.cookie("lang",langValue, {expired: 365, path:"/"});
	}else{
		langValue = langValue.toLowerCase();
	}
	$("#langCd").val(langValue);
	
	$("#langCd").on("change",function(){
		var pickLang = $("#langCd option:selected").val();
//		pickLang = pickLang.toUpperCase();
		$.cookie("lang", pickLang, {expired: 365, path:"/"});
		
		window.location.replace("/");
	})
});
