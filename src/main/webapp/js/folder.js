function getURLParameter(name) {
  return decodeURIComponent((new RegExp('[?|&]' + name + '=' + '([^&;]+?)(&|#|;|$)').exec(location.search)||[,""])[1].replace(/\+/g, '%20'))||null;
}

$(document).ready(function() {

  $('body').hide();

  var id_params = getURLParameter("id");

  if(id_params == undefined || !$.isNumeric(id_params)) {
    window.location.replace("index.html");
  }

  $.ajax({
    method: "GET",
    url: serverURL +"/folder",
    data: {"id" : id_params},
    crossDomain: true,
    xhrFields: {
      withCredentials: true
   },
    dataType: 'json'
  })
  .success(function(dataReceived) {
    console.log(dataReceived);

    if(dataReceived) {

      var owner = dataReceived.owner.firstname + " " + dataReceived.owner.lastname;
      if(dataReceived.owner.email == sessionStorage.getItem("email")) {
        owner = "You";
      }

      $('#title-folder').html(dataReceived.name);

      var elem = $(`<tr>
        <td>2</td>
        <td>FTP</td>
        <td>parkki@bouye.fr</td>
        <td>
          <span class="value"></span>
          <span class="to-display"><a href="javascript:void(0)" class="click-to-display">Click to display</a></span>
          <span class="to-copy"> ou <a href="javascript:void(0)" class="click-to-copy" data-toggle=snackbar data-content="Your password has been copied to the clipboard...">click to copy</a></span>
          <span class="to-hide"> <a href="javascript:void(0)" class="hide-value">Hide the password</a></span>
        </td>
        <td></td>
        <td></td>
        <td>Thibault Itart-Longueville</td>
        <td><a href="#"><i class="material-icons">delete</i></a></td>
      </tr>`);

      $('#folders-table').append(elem);
    }
  })
  .error(function(data) {
    sessionStorage.clear();
    window.location.replace("signin.html");
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
