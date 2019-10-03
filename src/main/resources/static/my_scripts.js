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
function myFunction2() {
    $.ajax({
        url : '/saveword',
        datatype : 'json',
        type : "get",
        contentType : "application/json",
        success : function(data) {
            $('.greeting-id').append(data.name);
            $('.greeting-content').append(data.translate);
        }
    });
}

function setVisible() {
    document.getElementById("translate").hidden = false;
}