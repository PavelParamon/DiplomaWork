package ru.DomofonMaster.View;

import com.google.gson.Gson;
import ru.DomofonMaster.Boundary.CityService;
import ru.DomofonMaster.Entities.CityEntity;

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

@WebServlet(urlPatterns = "/city")
public class CityServlet extends HttpServlet {
    private CityService CS;

    @Override
    public void init() throws ServletException {
        super.init();
        CS = new CityService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<CityEntity> cities = null;
        resp.setCharacterEncoding("UTF-8");

        PrintWriter out = resp.getWriter();
        try {
            cities = CS.GetCity();
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }


        assert cities != null;
        if(cities.isEmpty())
            cities = Collections.emptyList();

        Gson gson = new Gson();
        out.print(gson.toJson(cities));
        out.flush();
    }
}
