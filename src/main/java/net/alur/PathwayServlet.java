package net.alur;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

/**
 * Servlet implementation class PathwayServlet
 */
@WebServlet(name = "PathwayServlet", urlPatterns = { "/PathwayServlet" })
public class PathwayServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final IPathwayComputer ipc = new PathwayComputer();

    /**
     * @see HttpServlet#HttpServlet()
     */
    public PathwayServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    @SuppressWarnings("unused")
    private class PathMetadata {
        Map<Integer, String> path;
        int pathlength;
        int numpaths;
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String from = request.getParameter("from");
        String to = request.getParameter("to");

        try {
            Gson jsonHelper = new Gson();
            List<BoardSquare> path = ipc.computePathway(BoardSquare.at(from), BoardSquare.at(to));
            Map<Integer, String> pathSimplified = new HashMap<>();
            int i = 0;
            for (BoardSquare square : path) {
                pathSimplified.put(++i, square.shorthand());
            }

            PathMetadata pathinfo = new PathMetadata();
            pathinfo.path = pathSimplified;
            pathinfo.pathlength = pathSimplified.size();
            pathinfo.numpaths = NumPathsMatrix.getNumPaths(pathinfo.pathlength - 1, from, to);

            response.getWriter().append(jsonHelper.toJson(pathinfo));
        } catch (Exception e) {
            log("from " + from + " to " + to, e);
            response.getWriter().append("{}");
        }
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // TODO Auto-generated method stub
        doGet(request, response);
    }
}