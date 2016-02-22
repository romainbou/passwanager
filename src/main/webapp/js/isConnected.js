$(document).ready(function() {

  if(sessionStorage.getItem("email") == undefined) {
    window.location.replace("signin.html");
  }
  else {
    $("#display-email-address").html(sessionStorage.getItem("email"));
  }

});
