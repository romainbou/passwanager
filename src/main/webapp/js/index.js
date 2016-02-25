function timeConverter(UNIX_timestamp){
var a = new Date(UNIX_timestamp);
var months = ['Jan','Feb','Mar','Apr','May','Jun','Jul','Aug','Sep','Oct','Nov','Dec'];
var year = a.getFullYear();
var month = months[a.getMonth()];
var date = a.getDate();
var hour = a.getHours();
var min = a.getMinutes();
var sec = a.getSeconds();
var time = date + ' ' + month + ' ' + year + ' - ' + hour + ':' + min ;
return time;
}


$(document).ready(function() {

  $('body').hide();

  $.ajax({
    method: "GET",
    url: serverURL +"/folder",
    crossDomain: true,
    xhrFields: {
      withCredentials: true
   },
    dataType: 'json'
  })
  .success(function(dataReceived) {
    console.log(dataReceived);
    var elements = dataReceived["@items"];

    if(elements) {
      for (var i = 0; i < elements.length; i++) {
        var item = JSON.parse(elements[i]);

        var owner = item.owner.firstname + " " + item.owner.lastname;
        if(item.owner.email == sessionStorage.getItem("email")) {
          owner = "You";
        }

        var confidential = "Private";
        if(item.users["@items"].length > 1) {
          confidential = "Shared with: ";
          for (var i = 0; i < item.users["@items"].length; i++) {
            if(item.users["@items"][i].firstname != undefined && item.users["@items"][i].lastname != undefined && item.users["@items"][i].firstname != "" && item.users["@items"][i].lastname != "") {
              confidential += " " + item.users["@items"][i].firstname + " " + item.users["@items"][i].lastname + ",";
            }
          }

          if(confidential.slice(-1) == ',') {
            confidential= confidential.slice(0, - 1);
          }

        }



        var elem = $(`<div class="list-group-item">
        <div class="row-action-primary">
            <i class="material-icons">folder</i>
          </div>
          <div class="row-content">
            <div class="least-content" style="text-align:right">` + timeConverter(item.createdAt) + `<br><a href="#"><i class="material-icons">delete</i></a> </div>
            <h4 class="list-group-item-heading"><a href="folder.html?id=`+item.id+`">`+item.name+`</a></h4>

            <p class="list-group-item-text">Owner: `+ owner +` - `+ confidential +`</p>
          </div>
        </div>
        <div class="list-group-separator"></div>`);

        $('#folders-table').append(elem);
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
        $('#folderName').val("");
        $("#errors-block").hide();
        $("#share-with-list").html(`<h4>Share with...</h4>
        <label class="control-label" for="addon1">Email address or username used on Passwanager</label>
        <div class="input-group">
          <input type="text" id="shared1" class="form-control share-value">
          <span class="input-group-btn">
              <a href="javascript:void(0)" class="btn btn-primary btn-fab btn-fab-mini" id="add-share-button"><i class="material-icons">add</i></a>
          </span>
        </div>`);
        $(".modal").hide();

        window.location.replace("index.html");

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
