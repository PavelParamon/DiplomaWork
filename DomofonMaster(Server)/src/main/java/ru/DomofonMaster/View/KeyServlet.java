package ru.DomofonMaster.View;

import com.google.gson.Gson;
import ru.DomofonMaster.Boundary.KeyService;
import ru.DomofonMaster.Entities.KeyEntity;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@WebServlet(urlPatterns = "/keys")
public class KeyServlet extends HttpServlet {
    private KeyService KS;
    private KeyEntity KE;
    private PrintWriter out;

    @Override
    public void init() throws ServletException {
        super.init();
        KS = new KeyService();
        KE = new KeyEntity();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        out = resp.getWriter();
        Date date = new Date();
        boolean flag = false;
        String fk_str;
        long id_fk;
        if(req.getParameterNames().nextElement().equals("id_porch")){
            id_fk = Long.parseLong(req.getParameter("id_porch"));
            fk_str = "id_porch";
            KE.setPorch_id(id_fk);
        }
        else{
            id_fk = Long.parseLong(req.getParameter("id_intercom"));
            fk_str = "id_intercom";
            KE.setIntercom_id(id_fk);
        }
        String code = req.getParameter("code");
        //boolean type = Boolean.parseBoolean(req.getParameter("type"));
        String data_start = date.toString();

        KE.setCode(code);
        //KE.setType(type);
        KE.setData_start(data_start);

        try {
            KS.AddKey(KE, fk_str);
            flag = true;
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }

        Gson gson = new Gson();
        out.print(gson.toJson(flag));
        out.flush();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<KeyEntity> keys = null;
        req.setCharacterEncoding("UTF-8");
        out = resp.getWriter();
        int id_fk;
        if(req.getParameterNames().nextElement().equals("id_porch")){
            id_fk = Integer.parseInt(req.getParameter("id_porch"));
            try {
                keys = KS.GetKeysViaPorch(id_fk);
            } catch (SQLException | ClassNotFoundException throwables) {
                throwables.printStackTrace();
            }
        }
        else{
            id_fk = Integer.parseInt(req.getParameter("id_intercom"));
            try {
                keys = KS.GetKeysViaIntercom(id_fk);
            } catch (SQLException | ClassNotFoundException throwables) {
                throwables.printStackTrace();
            }
        }

        assert keys != null;
        if (keys.isEmpty())
            keys = Collections.emptyList();

        Gson gson = new Gson();
        out.print(gson.toJson(keys));
        out.flush();
    }

}
