package ru.DomofonMaster.View;

import com.google.gson.Gson;
import ru.DomofonMaster.Boundary.HouseService;
import ru.DomofonMaster.Boundary.PorchService;
import ru.DomofonMaster.Entities.HouseEntity;
import ru.DomofonMaster.Entities.PorchEntity;

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

@WebServlet(urlPatterns = "/porches")
public class PorchServlet extends HttpServlet {

    private PorchService PS;

    @Override
    public void init() throws ServletException {
        super.init();
        PS = new PorchService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<PorchEntity> porches = null;
        resp.setCharacterEncoding("UTF-8");
        req.setCharacterEncoding("UTF-8");

        int id_house = Integer.parseInt(req.getParameter("id_house"));
        PrintWriter out = resp.getWriter();
        try {
            porches = PS.GetPorches(id_house);
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }


        assert porches != null;
        if (porches.isEmpty())
            porches = Collections.emptyList();

        Gson gson = new Gson();
        out.print(gson.toJson(porches));
        out.flush();
    }
}
