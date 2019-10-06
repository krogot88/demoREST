function myFunction() {
    document.getElementById("translate").hidden = true;

    $.ajax({
        url : '/getword',
        datatype : 'json',
        type : "get",
        contentType : "application/json",
        success : function(data) {
            $('.greeting-id').text(data.name);
            $('.greeting-content').text(data.translate);
        }
    });
}

function sendWord() {
    $.ajax({
        url : '/postword',
        type : "post",
        datatype : 'json',
        contentType : "application/json; charset=utf-8",
        data : JSON.stringify({
            "id" : null,
            "name" : $('#text1').val(),
            "translate" : $('#text2').val()
        }),
        success : function(data) {
            document.getElementById("text1").value = "";
            document.getElementById("text2").value = "";
        },
        statusCode: {
            201: function(responseObject, textStatus, jqXHR) {
                var h2text = document.querySelector('.response_status');
                h2text.textContent = 'saved';
                setTimeout(function () {
                    h2text.style.opacity = '0';
                }, 2000);
            },
        }
    });
}

function setVisible() {
    document.getElementById("translate").hidden = false;
}

window.onload = function () {
    var request = new XMLHttpRequest();

    request.onreadystatechange = function() {
        if(request.readyState == 4) {
            var iptext = document.querySelector('.ip_address');
            if (iptext!== null) {
                iptext.innerHTML = request.responseText;
            }
        }
    }

    request.open('GET','/getip');
    request.send();
}