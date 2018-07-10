$(document).ready(function () {
    $("#search-form").submit(function (event) {
        //stop submit the form, we will post it manually.
        event.preventDefault();
        query();
    });
});

$(document).ready(function () {
    $("#download-form").submit(function (event) {
        //stop submit the form, we will post it manually.
        event.preventDefault();
        download();
    });
});

function query() {
    var search = {}
    var tbody = window.document.getElementById("tbody-result");
    search["contractCode"] = $("#contractCode").val();
    search["upload1"] = $("#upload1").val();
    search["upload2"] = $("#upload2").val();

    $("#btn-search").prop("disabled", true);
    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: "/query/contract",
        data: JSON.stringify(search),
        dataType: 'json',
        cache: false,
        timeout: 600000,
        success: function (data) {
            console.log("SUCCESS : ", data);

            var str = "";
            for (i in data) {
                var date = new Date(data[i].uploadDate);
                var date_str = date.toDateString() + " " + date.toTimeString();
                str += "<tr>" +
                    "<td>" + data[i].contractCode + "</td>" +
                    // "<td>" + data[i].uploadDate + "</td>" +
                    "<td>" + date_str + "</td>" +
                    "<td>" + data[i].bucketName + "</td>" +
                    "<td>" + data[i].objectKey + "</td>" +
                    "</tr>";
            }
            tbody.innerHTML = str;
            $("#btn-search").prop("disabled", false);
        },
        error: function (e) {
            console.log("ERROR : ", e);
            $("#btn-search").prop("disabled", false);
        }
    });
}

// function download() {
//     var search = {}
//
//     $("#btn-download").prop("disabled", true);
//     $.ajax({
//         type: "POST",
//         contentType: "application/json",
//         url: "/download",
//         data: JSON.stringify(search),
//         dataType: 'json',
//         cache: false,
//         timeout: 600000,
//         success: function (data) {
//             console.log("SUCCESS : ", data);
//             $("#btn-download").prop("disabled", false);
//         },
//         error: function (e) {
//             console.log("ERROR : ", e);
//             $("#btn-download").prop("disabled", false);
//         }
//     });
// }