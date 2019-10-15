function myFunction() {
    document.getElementById("translate").hidden = true;

    $.ajax({
        url: "/word/random",
        datatype: "json",
        type: "get",
        contentType: "application/json",
        success: function (data) {
            $(".greeting-id").text(data.name);
            $(".greeting-content").text(data.translate);
        }
    });
}

function deleteWord(id) {
    $.ajax({
        url: "/word/" + id,
        datatype: "json",
        type: "delete",
        contentType: "application/json",
        success: function () {
            var tableRow = document.getElementById(id);
            tableRow.getElementsByTagName("td")[0].innerHTML = "-";
            tableRow.getElementsByTagName("td")[1].innerHTML = "-";
            tableRow.getElementsByTagName("td")[2].innerHTML = "-";
            tableRow.getElementsByTagName("td")[3].innerHTML = "-";
            tableRow.getElementsByTagName("td")[4].innerHTML = "-";
        }
    });
}

function editWord(id) {
    var tableRow = document.getElementById(id);
    var name = tableRow.getElementsByTagName("td")[1].textContent;
    var tran = tableRow.getElementsByTagName("td")[2].textContent;
    tableRow.getElementsByTagName("td")[1].innerHTML = "<input class='input_name' type='text'" +
        " id='name" + id + "' value='" + name + "' size='24'>";
    tableRow.getElementsByTagName("td")[2].innerHTML = "<input class='input_tran' type='text'" +
        " id='tran" + id + "' value='" + tran + "' size='24'>";

    tableRow.getElementsByTagName("td")[3].innerHTML =
        "<button class='button21' onclick='submitWord(" + id + ");'>Y</button>" +
        "<button class='button22' onclick=\"cancelWord(" + id + ",'" + name + "','" + tran + "');\">N</button>";
}

function submitWord(id) {
    $.ajax({
        url: "/word/" + id,
        datatype: "json",
        type: "put",
        contentType: "application/json; charset=utf-8",
        data: JSON.stringify({
            "name": $(".input_name").val(),
            "translate": $(".input_tran").val()
        }),
        success: function (data) {
            var tableRow = document.getElementById(id);
            tableRow.getElementsByTagName("td")[1].innerHTML = data.name;
            tableRow.getElementsByTagName("td")[2].innerHTML = data.translate;
            tableRow.getElementsByTagName("td")[3].innerHTML =
                "<button class='button2' onclick='editWord(" + id + ");'>E</button>";
        }
    });
}

function cancelWord(id, name, tran) {
    var tableRow = document.getElementById(id);
    tableRow.getElementsByTagName("td")[1].innerHTML = name;
    tableRow.getElementsByTagName("td")[2].innerHTML = tran;
    tableRow.getElementsByTagName("td")[3].innerHTML =
        "<button class='button2' onclick='editWord(" + id + ");'>E</button>";
}

function sendWord() {
    $(".response_status_name").text("");
    $(".response_status_translate").text("");

    $.ajax({
        url: "/word",
        type: "post",
        datatype: "json",
        contentType: "application/json; charset=utf-8",
        data: JSON.stringify({
            "id": null,
            "name": $("#text1").val(),
            "translate": $("#text2").val()
        }),
        success: function (data) {
            document.getElementById("text1").value = "";
            document.getElementById("text2").value = "";
        },
        statusCode: {
            201: function (responseObject, textStatus, jqXHR) {
                var h2text = document.querySelector(".response_status");
                h2text.textContent = "saved";
                h2text.style.opacity = "1";
                setTimeout(function () {
                    h2text.style.opacity = "0";
                }, 2000);
            },
            409: function (responseObject, textStatus, jqXHR) {
                var h2text = document.querySelector(".response_status");
                h2text.textContent = "already exist";
                h2text.style.opacity = "1";
                setTimeout(function () {
                    h2text.style.opacity = "0";
                }, 2000);
            },
            400: function (responseObject, textStatus, jqXHR) {
                var h2text = document.querySelector(".response_status");
                h2text.textContent = "error";
                h2text.style.opacity = "1";
                setTimeout(function () {
                    h2text.style.opacity = "0";
                }, 2000);
                $(".response_status_name").text(responseObject.responseJSON.name);
                $(".response_status_translate").text(responseObject.responseJSON.translate);
            },
        }
    });
}

function setVisible() {
    document.getElementById("translate").hidden = false;
}

window.onload = function () {
    var request = new XMLHttpRequest();

    request.onreadystatechange = function () {
        if (request.readyState == 4) {
            var iptext = document.querySelector(".ip_address");
            if (iptext !== null) {
                iptext.innerHTML = request.responseText;
            }
        }
    }

    request.open("GET", "/getip");
    request.send();
}