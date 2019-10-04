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
                $('.response_status').text("saved");
            },
        }
    });
}

function setVisible() {
    document.getElementById("translate").hidden = false;
}