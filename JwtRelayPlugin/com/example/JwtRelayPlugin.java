package com.example;

import com.remedy.arsys.plugincontainer.Plugin;
import com.remedy.arsys.plugincontainer.PluginContext;
import com.remedy.arsys.plugincontainer.PluginConfig;
import com.remedy.arsys.plugincontainer.DefinitionFactory;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

public class JwtRelayPlugin implements Plugin {

    @Override
    public void processRequest(PluginContext pc) throws IOException {
        String jwt = pc.getRequest().getParameter("jwt");
        String targetUrl = pc.getRequest().getParameter("url");

        pc.getResponse().setContentType("application/json");
        pc.getResponse().setHeader("Cache-Control", "no-cache");
        PrintWriter out = pc.getResponse().getWriter();

        if (jwt == null || targetUrl == null) {
            pc.getResponse().setStatus(400);
            out.print("{\"error\": \"Missing jwt or url\"}");
            return;
        }

        try {
            HttpURLConnection conn = (HttpURLConnection) new URL(targetUrl).openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("AR-JWT", jwt);
            conn.setRequestProperty("Accept", "application/json");

            int code = conn.getResponseCode();
            InputStream in = (code >= 200 && code < 300)
                    ? conn.getInputStream()
                    : conn.getErrorStream();

            String body = new String(in.readAllBytes());
            pc.getResponse().setStatus(code);
            out.print(body);

        } catch (Exception e) {
            pc.getResponse().setStatus(500);
            out.print("{\"error\": \"" + e.getMessage().replace("\"", "'") + "\"}");
        }
    }

    @Override
    public void init(PluginConfig config) {}
    
    @Override
    public void cleanup() {}
    
    @Override
    public String handleEvent(PluginContext pc, String eventType, String eventData) {
        return "";
    }
    
    @Override
    public DefinitionFactory getDefinitionFactory() {
        return null;
    }
}
