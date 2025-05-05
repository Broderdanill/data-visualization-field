package com.example.dvm;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class PongPlugin {

    public void processRequest(HttpServletRequest req, HttpServletResponse res) throws IOException {
        res.setContentType("application/json;charset=UTF-8");
        res.getWriter().write("{\"message\": \"pong from DVM\"}");
    }
}
