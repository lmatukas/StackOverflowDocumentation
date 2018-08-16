package Controllers;

import Models.BL.DocTag;
import Models.BL.Topic;
import Models.URLSettingsModel;
import Services.DropDown;
import Services.ObjectConverterToString;
import Services.Pagination;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "ServletIndex", urlPatterns = {"/servletindex"})
public class ServletIndex extends HttpServlet {


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int paramSize = request.getParameterMap().size();
        System.out.println(paramSize);
        if (paramSize > 0) {
            String language = request.getParameter("language");
            String search = request.getParameter("search");
            String page = request.getParameter("page");
            String topic = request.getParameter("topicid");
            boolean after = request.getParameter("after") != null && Boolean.parseBoolean(request.getParameter("after"));
            int pageNum = page != null ? Integer.parseInt(page) : 1;
            System.out.println(language + " || " + search + " || " + page + " || " + topic);
            Pagination pagination = new Pagination();
            URLSettingsModel url = new URLSettingsModel(topic, language, search, after);
            List<Topic> topicList = pagination.getList(url);
            request.setAttribute("topicList", ObjectConverterToString.listOfObjectsToStringList(topicList));
        }
        DropDown dropDown = new DropDown();
        List<DocTag> docList = dropDown.getList();
        request.setAttribute("doctags", ObjectConverterToString.listOfObjectsToStringList(docList));
        request.getRequestDispatcher("index.jsp").forward(request, response);
    }
}