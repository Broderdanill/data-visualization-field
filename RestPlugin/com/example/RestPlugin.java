package com.example;

import com.remedy.arsys.plugincontainer.*;
import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class RestPlugin implements Plugin {

    @Override
    public void processRequest(PluginContext pc) throws IOException {
        String path = pc.getRequest().getPathInfo();

        if ("/params".equals(path)) {
            pc.getResponse().setContentType("application/json");
            PrintWriter out = pc.getResponse().getWriter();
            out.print("{ \"name\": \"RestFetch\", \"version\": \"1.0.0\", \"type\": \"Visualizer\", " +
                      "\"description\": \"REST Fetch test plugin\", \"author\": \"AR Plugin\", " +
                      "\"parameters\": [\"urlField\", \"targetField\"] }");
            return;
        }

        // Main visualizer rendering
        pc.getResponse().setContentType("text/html;charset=UTF-8");
        PrintWriter out = pc.getResponse().getWriter();

        out.println("<html><head>");
        out.println("<script type='text/javascript'>");
        out.println(pc.getPageService().getEventInfrastructureCode());
        out.println("EventDispatcher.setParentOrigin(window.location.origin);");
        out.println("function fetchAndSend(url) {");
        out.println("  EventDispatcher.sendEventToMidTier('FetchURL', url).then(js => eval(js));");
        out.println("}");
        out.println("</script>");
        out.println("</head><body>");
        out.println("<div>Waiting for REST fetch trigger...</div>");
        out.println("</body></html>");        
    }

    @Override
    public String handleEvent(PluginContext pc, String eventType, String eventData) {
        if (!"FetchURL".equals(eventType)) return "";
        String json = callExternalAPI(eventData);
        String escaped = json.replace("\"", "\\\\\"").replace("\n", "").replace("\r", "");
        return "EventDispatcher.sendEventToParent(\"RestResult\", \"" + escaped + "\");";
    }

    private String callExternalAPI(String url) {
        try (InputStream in = new URL(url).openStream()) {
            return new String(in.readAllBytes(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            return "{\"error\": \"" + e.getMessage().replace("\"", "'") + "\"}";
        }
    }

    @Override
    public void cleanup() {
        // Nothing to clean up
    }

    @Override
    public void init(PluginConfig config) {
        // No initialization needed
    }

    @Override
    public DefinitionFactory getDefinitionFactory() {
        return new DefinitionFactory() {
            @Override
            public Definition createFromString(PluginContext context, DefinitionKey key, String defJson) {
                return null;
            }

            @Override
            public Definition createFromStream(PluginContext context, DefinitionKey key, InputStream stream) {
                return null;
            }
        };
    }
}
