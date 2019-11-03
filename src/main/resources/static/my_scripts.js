var globalWordGameDTOTranslates;
var isComplicateOn = true;

function myFunction() {
    var translates = document.getElementsByClassName("greeting-content");
    for (var i = 0; i < translates.length; i++) {
        setColorNone(translates.item(i).id);
        document.getElementById(i).setAttribute("onClick","checkAnswer(id)");
    }

    $.ajax({
        url: "/word/random/game/4",
        datatype: "json",
        type: "get",
        contentType: "application/json",
        success: function (data) {
            globalWordGameDTOTranslates = data.translates;
            $(".greeting-id").text(data.name);
            printTranslates();
        }
    });
}

function setVisible() {
    if(isComplicateOn){
        isComplicateOn = false;
        document.getElementById("complicate-button").innerHTML = partlyAnswers;
    } else {
        isComplicateOn = true;
        document.getElementById("complicate-button").innerHTML = fullAnswers;
    }
    printTranslates();
}

function printTranslates() {
    $("#0").text(gaps(globalWordGameDTOTranslates[0]));
    $("#1").text(gaps(globalWordGameDTOTranslates[1]));
    $("#2").text(gaps(globalWordGameDTOTranslates[2]));
    $("#3").text(gaps(globalWordGameDTOTranslates[3]));
}

function gaps(translate) {
    if (isComplicateOn) {
        return translate.replace(/(.{2})./g, '$1 ');
    }
    return translate;
}

function checkAnswer(id) {
    $.ajax({
        url: "/word/game",
        datatype: "json",
        type: "post",
        contentType: "application/json; charset=utf-8",
        data: JSON.stringify({
            "name": $(".greeting-id").text(),
            "translate": globalWordGameDTOTranslates[id],
        }),
        success: function (data) {
            var complicatedRealTranslate = gaps(data.translate);
            document.getElementById(id).textContent = globalWordGameDTOTranslates[id];
            if (document.getElementById(id).textContent == data.translate) {
                setGreen(id);
            } else {
                setRed(id);
                var translates = document.getElementsByClassName("greeting-content");
                for (var i = 0; i < translates.length; i++) {
                    if (document.getElementById(i).textContent == complicatedRealTranslate) {
                        document.getElementById(i).textContent = globalWordGameDTOTranslates[i];
                        setGreen(translates.item(i).id);
                    }
                }
            }
            for (var i = 0; i < globalWordGameDTOTranslates.length; i++) {
                document.getElementById(i).setAttribute("onclick", "");
            }
        }
    });
}

function setGreen(id) {
    var text = document.getElementById(id);
    text.style.backgroundColor = "#8edb82";
}

function setRed(id) {
    var text = document.getElementById(id);
    text.style.backgroundColor = "#DB8E92";
}

function setColorNone(id) {
    var text = document.getElementById(id);
    text.style.backgroundColor = "";
}

function deleteWord(id) {
    $.ajax({
        url: "/word/id/" + id,
        datatype: "json",
        type: "delete",
        contentType: "application/json",
        success: function () {
            var tableRow = document.querySelector("[word-id=\"" + id + "\"]");
            tableRow.getElementsByTagName("td")[0].innerHTML = "-";
            tableRow.getElementsByTagName("td")[1].innerHTML = "-";
            tableRow.getElementsByTagName("td")[2].innerHTML = "-";
            tableRow.getElementsByTagName("td")[3].innerHTML = "-";
            tableRow.getElementsByTagName("td")[4].innerHTML = "-";
        }
    });
}

function editWord(id) {
    var tableRow = document.querySelector("[word-id=\"" + id + "\"]");
    var name = tableRow.getElementsByTagName("td")[1].textContent;
    var tran = tableRow.getElementsByTagName("td")[2].textContent;
    tableRow.getElementsByTagName("td")[1].innerHTML = "<input class='input_name' type='text'" +
        " value='" + name + "' size='24'>";
    tableRow.getElementsByTagName("td")[2].innerHTML = "<input class='input_tran' type='text'" +
        " value='" + tran + "' size='24'>";

    tableRow.getElementsByTagName("td")[3].innerHTML =
        "<button class='button21' onclick='submitWord(" + id + ");'>" + textYES + "</button>" +
        "<button class='button22' onclick=\"cancelWord(" + id + ",'" + name + "','" + tran + "');\">" + textNO + "</button>";
}

function submitWord(id) {
    $.ajax({
        url: "/word/id/" + id,
        datatype: "json",
        type: "put",
        contentType: "application/json; charset=utf-8",
        data: JSON.stringify({
            "name": $(".input_name").val(),
            "translate": $(".input_tran").val()
        }),
        success: function (data) {
            var tableRow = document.querySelector("[word-id=\"" + id + "\"]");
            tableRow.getElementsByTagName("td")[1].innerHTML = data.name;
            tableRow.getElementsByTagName("td")[2].innerHTML = data.translate;
            tableRow.getElementsByTagName("td")[3].innerHTML =
                "<button class='button2' onclick='editWord(" + id + ");'>" + textEDIT + "</button>";
        }
    });
}

function cancelWord(id, name, tran) {
    var tableRow = document.querySelector("[word-id=\"" + id + "\"]");
    tableRow.getElementsByTagName("td")[1].innerHTML = name;
    tableRow.getElementsByTagName("td")[2].innerHTML = tran;
    tableRow.getElementsByTagName("td")[3].innerHTML =
        "<button class='button2' onclick='editWord(" + id + ");'>" + textEDIT + "</button>";
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