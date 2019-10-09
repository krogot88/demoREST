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

function deleteWord(id) {
    $.ajax({
        url : '/word/'+id,
        datatype : 'json',
        type : "delete",
        contentType : "application/json",
        success : function() {
            var tableRow = document.getElementById(id);
            tableRow.getElementsByTagName("td")[0].innerHTML = "-";
            tableRow.getElementsByTagName("td")[1].innerHTML = "-";
            tableRow.getElementsByTagName("td")[2].innerHTML = "-";
            tableRow.getElementsByTagName("td")[3].innerHTML = "-";
            tableRow.getElementsByTagName("td")[4].innerHTML = "-";
        }
    });
}



function sendWord() {
    $.ajax({
        url : '/word',
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
                h2text.style.opacity = '1';
                setTimeout(function () {
                    h2text.style.opacity = '0';
                }, 2000);
            },
            409: function(responseObject, textStatus, jqXHR) {
                var h2text = document.querySelector('.response_status');
                h2text.textContent = 'already exist';
                h2text.style.opacity = '1';
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