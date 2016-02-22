$(document).ready(function() {

  $('body').hide();

  $.ajax({
    method: "GET",
    url: serverURL +"/folder",
    crossDomain: true,
    xhrFields: {
      withCredentials: true
   },
    // data: {session_id: sessionStorage.getItem("session_id")},
    dataType: 'json'
  })
  .success(function(dataReceived) {
    console.log(dataReceived);

  })
  .error(function(data) {
    console.log(data);
  })
  .complete(function() {
    $('body').show();
  });





  $('#form-add-new-folder').submit(function() {

    $('#button-add-new-folder').attr("disabled", true);

    var folderName = $('#folderName').val();

    var errors = [];

    if(folderName == undefined || folderName == "") {
      errors.push("Folder name is invalid");
    }

    if(errors.length > 0) {
      $("#errors-list").html("");
      for (var i = 0; i < errors.length; i++) {
        $("#errors-list").append($('<li>'+errors[i]+'</li>'));
      }
      $("#errors-block").show();
      $('#button-add-new-folder').attr("disabled", false);
    }
    else {
      var colaborators = "";
      for (var i = 1; i <= $('.share-value').length; i++) {
        if($("#shared"+i).val() != "") {
          colaborators += $("#shared"+i).val() + ",";
        }
      }
      if(colaborators.slice(-1) == ',') {
        colaborators= colaborators.slice(0, - 1);
      }

      var data = {
        'session_id': sessionStorage.getItem("session_id"),
        'name' : folderName,
        'colaborators' : colaborators
      }

      $.ajax({
        method: "POST",
        url: serverURL +"/folder",
        crossDomain: true,
        xhrFields: {
          withCredentials: true
        },
        data: data,
        dataType: 'json'
      })
      .success(function(dataReceived) {

        console.log(dataReceived);

        // sessionStorage.setItem("session_id",dataReceived.session_id);
        // sessionStorage.setItem("email",data.email);
        //
        // window.location.replace("index.html");

      })
      .error(function(data) {
        console.log(data);
        $("#errors-list").html("");
        $("#errors-list").append($('<li>'+data.responseJSON.error+'</li>'));
        $("#errors-block").show();
      })
      .complete(function() {
        $('#button-add-new-folder').attr("disabled", false);
      });

    }


    return false;
  });




});
