package Controllers;

import Models.DTO.ExampleDTO;
import Models.DTO.TopicsDTO;
import Services.DIContainer;
import Services.IHigherService;
import Services.ObjectConverterToString;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "ServletTopics", urlPatterns = {"/topics"})
public class ServletTopics extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String topicID = request.getParameter("topicId");
        if (topicID == null) {
            response.sendRedirect("http://localhost:8080/index.jsp");
        } else {
            IHigherService higher = DIContainer.getInjector().getInstance(IHigherService.class);
            TopicsDTO dto = higher.getTopicById(topicID);
            ExampleDTO exDto = higher.getExamplesByTopicsId(topicID);
            if (dto.success && exDto.success) {
                request.setAttribute("topic", ObjectConverterToString.objectToStringMap(dto.list.get(0)));
                request.setAttribute("examples", ObjectConverterToString.listOfObjectsToStringList(exDto.list));
                request.getRequestDispatcher("topics.jsp").forward(request, response);
            } else {
                response.sendRedirect("http://localhost:8080/index.jsp");
            }
        }
    }
}

