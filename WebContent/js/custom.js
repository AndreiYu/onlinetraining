

//hide alert messages when empty
$(document).ready(function () {
	  $(".alert").each(function(){
		  if(!$(this).text().trim().length){
			  $(".alert").hide();
		  }
	  })
});


//confirm password input
	var password = document.getElementById("password")
	, confirm_password = document.getElementById("confirm_password");

function validatePassword(){
	if(password.value != confirm_password.value) {
			confirm_password.setCustomValidity("Passwords don't match");
	} else {
	  confirm_password.setCustomValidity('');
	}
}
	password.onchange = validatePassword;
	confirm_password.onkeyup = validatePassword;

//alert on trying to upload unsupported file format
	var file = document.getElementById('avatar');

	file.onchange = function(e){
	    var ext = this.value.match(/\.([^\.]+)$/)[1];
	    var size = file.files[0].size/1024; // in KB
	    switch(ext)
	    {
	        case 'jpg': case 'jpeg': case 'gif': case 'png':
	        case 'JPG': case 'JPEG': case 'GIF': case 'PNG':
	            break;
	        default:
	            alert('Unsupported file format');
	            this.value='';
	    }
        if (size > 512) {
            alert('File size is more than 512Kb');
            this.value='';
        } else {

        }
	};	   
	
//submit form

		function frmsubmit(obj){
			  document.getElementById(obj).submit();
			  return false;
		}
	

		
		