var serverURL = "http://localhost:8080";

$(document).ready(function() {
  $.material.init();


  $("#add-folder-modal").click(function() {
    $(".modal").show();
  });

  $(".close-modal").click(function() {
    $(".modal").hide();
  });

  $("#add-share-button").bind('click', function() {
    var nbValue = $('.share-value').size() +1;
    var add = $('<input type="text" id="shared'+ nbValue +'" class="form-control share-value" style="max-width:488px">');
    $("#share-with-list").append(add);
  });

  $('.click-to-display').click(function() {
    $(this).parent().hide();
    $(this).parent().siblings(".to-copy").hide();
    $(this).parent().siblings(".to-hide").show();
    $(this).parent().siblings(".value").html("");
  });

  $('.hide-value').click(function() {
    $(this).parent(".to-hide").hide();
    $(this).parent().siblings('.value').html("");
    $(this).parent().siblings('.to-display').show();
    $(this).parent().siblings('.to-copy').show();
  });


  $("#button-public-key-copy").click(function(event) {
    var copyTextarea =  $('#textarea-public-key');
    copyTextarea.select();

    try {
      var successful = document.execCommand('copy');
      var msg = successful ? 'successful' : 'unsuccessful';
      console.log(msg);
    } catch (err) {
      console.log("copy error");
    }
  });

  $("#button-private-key-copy").click(function(event) {
    var copyTextarea =  $('#textarea-private-key');
    copyTextarea.select();

    try {
      var successful = document.execCommand('copy');
      var msg = successful ? 'successful' : 'unsuccessful';
      console.log(msg);
    } catch (err) {
      console.log("copy error");
    }
  });

  $("#button-logout").click(function() {
    sessionStorage.clear();
    window.location.replace("signin.html");
  });

  function validateEmail(email) {
    var re = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
    return re.test(email);
  }




  // EVENTS


  $('#form-sign-up').submit(function() {
    $('#button-sign-up').attr("disabled", true);

    var email = $('#email').val();
    var firstname = $('#firstname').val();
    var lastname = $('#lastname').val();
    var username = $('#username').val();
    var password = $('#password').val();
    var verifyPassword = $('#verify-password').val();

    var errors = [];

    if(email == undefined || email == "" || !validateEmail(email)) {
      errors.push("Email address is invalid");
    }

    if(firstname == undefined || firstname == "") {
      errors.push("Firstname is invalid");
    }

    if(lastname == undefined || lastname == "") {
      errors.push("Lastname is invalid");
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
      $('#button-sign-up').attr("disabled", false);
    }
    else {

      var rsa = forge.pki.rsa;

      // generate an RSA key pair synchronously
      var keypair = rsa.generateKeyPair({bits: 2048, e: 0x10001});

      function fix (str) {
        return str.replace(/\r/g, '') + '\n'
      }
      keypair = {
         public: fix(forge.pki.publicKeyToRSAPublicKeyPem(keypair.publicKey, 72)),
         private: fix(forge.pki.privateKeyToPem(keypair.privateKey, 72))
      };

      var data = {
        'email' : email,
        'firstname' : firstname,
        'lastname' : lastname,
        'username' : username,
        'password' : password,
        'public_key' : keypair.public
      }

      $.ajax({
        method: "POST",
        url: serverURL +"/user/signup",
        data: data,
        dataType: 'json'
      })
      .success(function(dataReceived) {
        sessionStorage.setItem("email",data.email);

        $('#login-box').hide();
        $('#keys-box').show();

        $("#textarea-public-key").val(keypair.public);
        $("#textarea-private-key").val(keypair.private);

      })
      .error(function(data) {
        $("#errors-list").html("");
        $("#errors-list").append($('<li>'+data.responseJSON.error+'</li>'));
        $("#errors-block").show();
      })
      .complete(function() {
        $('#button-sign-up').attr("disabled", false);
      });
    }

    return false;
  });






  $('#form-sign-in').submit(function() {

    $('#button-sign-in').attr("disabled", true);

    var email = $('#email').val();
    var password = $('#password').val();

    var errors = [];

    if(email == undefined || email == "" || !validateEmail(email)) {
      errors.push("Email address is invalid");
    }

    if(password == undefined || password == "") {
      errors.push("Password is invalid");
    }

    if(errors.length > 0) {
      $("#errors-list").html("");
      for (var i = 0; i < errors.length; i++) {
        $("#errors-list").append($('<li>'+errors[i]+'</li>'));
      }
      $("#errors-block").show();
      $('#button-sign-in').attr("disabled", false);
    }
    else {
      var data = {
        'email' : email,
        'password' : password
      }

      $.ajax({
        method: "POST",
        url: serverURL +"/user/signin",
        data: data,
        crossDomain: true,
        xhrFields: {
          withCredentials: true
        },
        dataType: 'json'
      })
      .success(function(dataReceived) {
        sessionStorage.setItem("email",data.email);

        window.location.replace("index.html");

      })
      .error(function(data) {
        console.log(data);
        $("#errors-list").html("");
        $("#errors-list").append($('<li>'+data.responseJSON.error+'</li>'));
        $("#errors-block").show();
      })
      .complete(function() {
        $('#button-sign-in').attr("disabled", false);
      });
    }

    return false;
  });

});
