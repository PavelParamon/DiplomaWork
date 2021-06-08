package ru.DomofonMaster.View;

import com.google.gson.Gson;
import ru.DomofonMaster.Boundary.HouseService;
import ru.DomofonMaster.Entities.HouseEntity;

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

@WebServlet(urlPatterns = "/houses")
public class HouseServlet extends HttpServlet {
    private HouseService HS;

    @Override
    public void init() throws ServletException {
        super.init();
        HS = new HouseService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<HouseEntity> houses = null;
        resp.setCharacterEncoding("UTF-8");
        req.setCharacterEncoding("UTF-8");

        int id_street = Integer.parseInt(req.getParameter("id_street"));
        PrintWriter out = resp.getWriter();
        try {
            houses = HS.GetHouses(id_street);
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }


        assert houses != null;
        if (houses.isEmpty())
            houses = Collections.emptyList();

        Gson gson = new Gson();
        out.print(gson.toJson(houses));
        out.flush();
    }
}
