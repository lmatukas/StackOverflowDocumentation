window.addEventListener("load", function () {
    var script = document.createElement("script");
    script.src = "https://maps.googleapis.com/maps/api/js?callback=myMap"
    script.async = true;
    document.getElementsByTagName("script")[0].parentNode.appendChild(script);
});

window.addEventListener("load", function () {
    var script = document.createElement("script");
    script.src = "https://code.jquery.com/jquery-3.3.1.slim.min.js"
    script.integrity = "sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo"
    script.crossOrigin = "anonymous"
    script.async = true;
    document.getElementsByTagName("script")[1].parentNode.appendChild(script);
});

window.addEventListener("load", function () {
    var script = document.createElement("script");
    script.src = "https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.0/umd/popper.min.js"
    script.integrity = "sha384-cs/chFZiN24E4KMATLdqdvsezGxaGsi4hLGOzlXwp5UZB1LY//20VyM2taTB4QvJ"
    script.crossOrigin = "anonymous"
    script.async = true;
    document.getElementsByTagName("script")[2].parentNode.appendChild(script);
});

window.addEventListener("load", function () {
    var script = document.createElement("script");
    script.src = "https://stackpath.bootstrapcdn.com/bootstrap/4.1.0/js/bootstrap.min.js"
    script.integrity = "sha384-uefMccjFJAIv6A+rW+L4AHf99KvxDjWSu1z9VI8SKNVmz4sk7buKt/6v9KI65qnm"
    script.crossOrigin = "anonymous"
    script.async = true;
    document.getElementsByTagName("script")[3].parentNode.appendChild(script);

});

window.addEventListener("load", function () {
    // Access the form element...
    var form = document.getElementById("myForm");
    // ...and take over its submit event.
    form.addEventListener("submit", function (event) {
        event.preventDefault();
        Search();
    });
});


function Search() {
    var language = document.getElementById("language").value;
    var searchField = document.getElementById("search-field").value;
    location.href = "/servletindex?language=" + language + "&search=" + searchField;
}


function GetTopicInfo(number) {
    location.href = "/topics?topicId=" + number;
}


function changePage(id) {
    var language = getParam("language");
    var search = getParam("search");
    var pageNum = parseInt(document.getElementById("page-number").firstElementChild.text);
    var trRows = document.getElementsByTagName("tr");
    if (trRows.length !== 0) {
        var topicId = trRows[0].id;
        var next;
        if (id === "next") {
            next = true;
            pageNum++;
            location.href = "/servletindex?language=" + language + "&search=" + search + "&after=" + next +
                "&topicid=" + topicId + "&page=" + pageNum;
        } else if (id === "previous" && pageNum > 1) {
            next = false;
            pageNum--;
            location.href = "/servletindex?language=" + language + "&search=" + search + "&after=" + next +
                "&topicid=" + topicId + "&page=" + pageNum;
        }
    }
}


function getParam(parameter) {
    var urlParams = new URLSearchParams(window.location.search);
    if (urlParams.has(parameter)) {
        return urlParams.get(parameter);
    } else {
        return -1;
    }
}
