<%@ taglib prefix="java" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link rel="stylesheet" href="Styles/styles.css">
    <script src="Scripts/script.js"></script>
    <title>Topics</title>
</head>
<body>
<div class="header">
    <h2>${topic[0]}</h2>
</div>

<div class="container2">

    <div class="mainbody">
        <ul>
            <li>
                <h3>Introduction</h3>
                <p>${topic[1]}</p>
            </li>
            <li>
                <h3>Syntax</h3>
                <p>${topic[2]}</p>
            </li>
            <li>
                <h3>Parameters</h3>
                <p>${topic[3]}</p>
            </li>
            <li>
                <h3>Remarks</h3>
                <p>${topic[4]}</p>
            </li>
        </ul>
    </div>


    <br>
    <div class="row justify-content-center">
        <!-- Button trigger modal -->
        <button class="col-auto btn btn-info" type="button" data-toggle="modal" data-target=".bd-example-modal-lg">
            Example
        </button>
        <div class="col-sm-2 offset-sm-2"></div>
        <div class="col-sm-2 offset-sm-2"></div>
        <button class="col-auto btn btn-secondary" onclick="window.history.back();">Back</button>

    </div>
    <br>

</div>


<%--Modal--%>
<div class="modal fade bd-example-modal-lg" tabindex="-1" role="dialog" aria-labelledby="myLargeModalLabel"
     aria-hidden="true">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="exampleModalLabel">${topic[1]}</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <ul>
                    <li>
                        <java:forEach var="ex" items="${examples}">
                            <p>${ex[0]}</p>
                        </java:forEach>
                    </li>
                </ul>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
            </div>
        </div>
    </div>
</div>


</body>
</html>
