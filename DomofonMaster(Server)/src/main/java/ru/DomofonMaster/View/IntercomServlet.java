package ru.DomofonMaster.View;

import com.google.gson.Gson;
import ru.DomofonMaster.Boundary.IntercomService;
import ru.DomofonMaster.Entities.IntercomEntity;

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

@WebServlet(urlPatterns = "/intercom")
public class IntercomServlet extends HttpServlet {
    private IntercomService IS;

    @Override
    public void init() throws ServletException {
        super.init();
        IS = new IntercomService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<IntercomEntity> intercoms = null;
        resp.setCharacterEncoding("UTF-8");

        PrintWriter out = resp.getWriter();
        try {
            intercoms = IS.GetIntercom();
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }


        assert intercoms != null;
        if(intercoms.isEmpty())
            intercoms = Collections.emptyList();

        Gson gson = new Gson();
        out.print(gson.toJson(intercoms));
        out.flush();
    }
}
