package net.alur;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.common.collect.Streams;
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
            Map<Integer, String> pathForJson = Streams
                    .zip(IntStream.range(1, path.size() + 1).mapToObj(i -> Integer.valueOf(i)), path.stream(),
                            (j, sq) -> Map.entry(j, sq.shorthand()))
                    .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

            PathMetadata pathinfo = new PathMetadata();
            pathinfo.path = pathForJson;
            pathinfo.pathlength = pathForJson.size();
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