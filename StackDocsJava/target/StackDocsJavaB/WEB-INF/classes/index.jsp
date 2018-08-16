<%@ taglib prefix="java" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>

<head>
    <link rel="stylesheet" href="Styles/styles.css">
    <script src="Scripts/script.js"></script>
    <title>Stack Docs JAVA</title>
</head>

<body class="bg-light">

    <div class="container">
        <div class="py-5 text-center">
            <img src="Images/logo.png" alt="" class="image">
            <h2>Stack Docs JAVA</h2>
        </div>

        <form id="myForm">
            <div class="row">
                <div class="col-md-4 order-md-1 mb-4">

                    <h4 class="d-flex justify-content-between align-items-center mb-3">
                        <span class="text-muted">Pasirinkite kalbą</span>
                    </h4>

                    <select class="form-control custom-select d-block w-100" id="language">

                        <option value="">
                            "Pasirinkimas..."
                        </option>

                        <java:forEach var="tags" items="${doctags}">

                            <option value="${tags[0]}">
                                    ${tags[1]}
                            </option>

                        </java:forEach>

                    </select>

                    <button type="submit" class="btn btn-light">Paieška</button>

                </div>


                <div class="col-md-8 order-md-2">

                    <h4 class="mb-3"> Paieška pagal frazę</h4>

                    <div class="row">
                        <div class="col-md-12 mb-3">
                            <input class="form-control" id="search-field" type="text" placeholder="Įveskite frazę">
                        </div>
                    </div>

                </div>
            </div>
        </form>

        <table class="table table-bordered shadow-lg p-3 mb-5 bg-white rounded">

            <tbody>

            <java:forEach var="topic" items="${topicList}">
                <tr id="${topic[0]}" onclick="GetTopicInfo(${topic[0]})">
                    <td>${topic[1]}</td>
                </tr>
            </java:forEach>

            </tbody>

        </table>

        <nav aria-label="Puslapiu navigacija">

            <ul class="pagination justify-content-center">

                <li class="page-item" id="previous" onclick="changePage(this.id)">
                    <a class="page-link text-dark">Atgal</a>
                </li>

                <li class="page-item" id="page-number">
                    <a class="page-link text-dark"><%= request.getParameter("page") != null ? request.getParameter("page") : 1%></a>
                </li>

                <li class="page-item" id="next" onclick="changePage(this.id)">
                    <a class="page-link text-dark">Pirmyn</a>
                </li>

            </ul>
        </nav>
    </div>

</body>
</html>
