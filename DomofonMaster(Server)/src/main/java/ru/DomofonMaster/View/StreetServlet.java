package ru.DomofonMaster.View;

import com.google.gson.Gson;
import ru.DomofonMaster.Boundary.StreetService;
import ru.DomofonMaster.Entities.CityEntity;
import ru.DomofonMaster.Entities.StreetEntity;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

@WebServlet(urlPatterns = "/streets")
public class StreetServlet extends HttpServlet {
    private StreetService SS;

    @Override
    public void init() throws ServletException {
        super.init();
        SS = new StreetService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<StreetEntity> streets = null;
        resp.setCharacterEncoding("UTF-8");
        req.setCharacterEncoding("UTF-8");

        int id_city = Integer.parseInt(req.getParameter("id_city"));
        PrintWriter out = resp.getWriter();
        try {
            streets = SS.GetStreets(id_city);
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }


        assert streets != null;
        if(streets.isEmpty())
            streets = Collections.emptyList();

        Gson gson = new Gson();
        out.print(gson.toJson(streets));
        out.flush();
    }
}
