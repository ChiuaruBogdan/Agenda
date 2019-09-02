package org.fasttrackit.web;

import org.fasttrackit.config.ObjectMapperConfiguration;
import org.fasttrackit.domain.Agenda;
import org.fasttrackit.service.AgendaService;
import org.fasttrackit.transfer.SaveAgendaRequest;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/agenda")
public class AgendaServlet extends HttpServlet {

    private AgendaService agendaService = new AgendaService();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        SaveAgendaRequest request =
                ObjectMapperConfiguration.getObjectMapper().readValue(req.getReader(), SaveAgendaRequest.class);

        try {
            agendaService.createTodoItem(request);
        } catch (SQLException | ClassNotFoundException e) {
            resp.sendError(201, "internal server error: " + e.getMessage());
        }

    }


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String findQuery = req.getQueryString();
        try {
            List<Agenda> todoItems = agendaService.getTodoItem();
            String responseJson = ObjectMapperConfiguration.getObjectMapper().writeValueAsString(todoItems);

            resp.getWriter().print(responseJson);
            resp.getWriter().flush();
            resp.getWriter().close();


            List<Agenda> findtodoItems = agendaService.findToDoItem(findQuery);
            String queryJson = ObjectMapperConfiguration.getObjectMapper().writeValueAsString(findtodoItems);

            resp.getWriter().print(queryJson);
            resp.getWriter().flush();
            resp.getWriter().close();

        } catch (SQLException | ClassNotFoundException e) {
            resp.sendError(200, "internal server error: " + e.getMessage());
        }

    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");

        try {
            agendaService.deleteToDoItem(Long.parseLong(id));
        } catch (SQLException | ClassNotFoundException e) {
            resp.sendError(405, "internal server error: " + e.getMessage());
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");
        SaveAgendaRequest request = ObjectMapperConfiguration.getObjectMapper()
                .readValue(req.getReader(), SaveAgendaRequest.class);

        try {
            agendaService.updateToDoItem(Long.parseLong(id), request);
        } catch (SQLException | ClassNotFoundException e) {
            resp.sendError(405, "internal server error: " + e.getMessage());
        }
    }


    protected void Query2(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String findQuery = req.getQueryString();

        try {
            List<Agenda> findtodoItems = agendaService.findToDoItem(findQuery);
            String queryJson = ObjectMapperConfiguration.getObjectMapper().writeValueAsString(findtodoItems);

            resp.getWriter().print(queryJson);
            resp.getWriter().flush();
            resp.getWriter().close();
        } catch (SQLException | ClassNotFoundException e) {
            resp.sendError(200, "internal server error: " + e.getMessage());
        }

    }


}