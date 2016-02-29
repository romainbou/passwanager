function getURLParameter(name) {
  return decodeURIComponent((new RegExp('[?|&]' + name + '=' + '([^&;]+?)(&|#|;|$)').exec(location.search)||[,""])[1].replace(/\+/g, '%20'))||null;
}

var clickToDisplay = function() {
  $(this).parent().hide();
  $(this).parent().siblings(".to-copy").hide();
  $(this).parent().siblings(".to-hide").show();
  $(this).parent().siblings(".value").html("");
}

var clickToHide = function() {
  $(this).parent(".to-hide").hide();
  $(this).parent().siblings('.value').html("");
  $(this).parent().siblings('.to-display').show();
  $(this).parent().siblings('.to-copy').show();
};

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

      var folder = JSON.parse(dataReceived.folder[0]);

      if(dataReceived.entries) {
        var entries = JSON.parse(dataReceived.entries[0])["@items"];

        var owner = folder.owner.firstname + " " + folder.owner.lastname;
        if(folder.owner.email == sessionStorage.getItem("email")) {
          owner = "You";
        }

        $('#title-folder').html(folder.name);

        for (var i = 0; i < entries.length; i++) {
          var item = JSON.parse(entries[i]);

          var elem = $(`<tr>
            <td>`+ item.id +`</td>
            <td>`+ item.title +`</td>
            <td>`+ item.username +`</td>
            <td>
              <span class="value"></span>
              <span class="to-display"><a href="javascript:void(0)" class="click-to-display">Click to display</a></span>
              <span class="to-copy"> ou <a href="javascript:void(0)" class="click-to-copy" data-toggle=snackbar data-content="Your password has been copied to the clipboard...">click to copy</a></span>
              <span class="to-hide"> <a href="javascript:void(0)" class="hide-value">Hide the password</a></span>
            </td>
            <td>`+ item.url +`</td>
            <td>`+ item.notes +`</td>
            <td></td>
            <td><a href="#"><i class="material-icons">delete</i></a></td>
          </tr>`);

          $('.click-to-display', elem).bind("click", clickToDisplay);

          $('.hide-value', elem).bind("click", clickToHide);


          $('#entries-table').append(elem);
        }
      }
    }
  })
  .error(function(data) {
    sessionStorage.clear();
    window.location.replace("signin.html");
  })
  .complete(function() {
    $('body').show();
  });





  $('#form-add-new-entry').submit(function() {
    console.log("eee");

    $('#button-add-new-entry').attr("disabled", true);

    var title = $('#title').val();
    var username = $('#username').val();
    var password = $('#password').val();
    var verifyPassword = $('#verifypassword').val();
    var url = $('#url').val();
    var notes = $('#notes').val();

    var errors = [];

    if(title == undefined || title == "") {
      errors.push("Title is invalid");
    }

    if(username == undefined || username == "") {
      errors.push("Username is invalid");
    }

    if(password == undefined || password == "") {
      errors.push("Password is invalid");
    }

    if(verifyPassword == undefined || verifyPassword == "") {
      errors.push("Verify password is invalid");
    }

    if(password != verifyPassword) {
      errors.push("Password and verify password are different");
    }

    if(errors.length > 0) {
      $("#errors-list").html("");
      for (var i = 0; i < errors.length; i++) {
        $("#errors-list").append($('<li>'+errors[i]+'</li>'));
      }
      $("#errors-block").show();
      $('#button-add-new-entry').attr("disabled", false);
    }
    else {

      var data = {
        'folder' : id_params,
        'title' : title,
        'username' : username,
        'url' : url,
        'notes' : notes,
        'values' : password
      }

      $.ajax({
        method: "POST",
        url: serverURL +"/entry",
        crossDomain: true,
        xhrFields: {
          withCredentials: true
        },
        data: data,
        dataType: 'json'
      })
      .success(function(dataReceived) {

        console.log(dataReceived);

      })
      .error(function(data) {
        console.log(data);
        $("#errors-list").html("");
        $("#errors-list").append($('<li>'+data.responseJSON.error+'</li>'));
        $("#errors-block").show();
      })
      .complete(function() {
        $('#button-add-new-entry').attr("disabled", false);
      });

    }


    return false;
  });




});
