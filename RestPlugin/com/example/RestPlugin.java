package com.example;



import com.remedy.arsys.plugincontainer.Definition;
import com.remedy.arsys.plugincontainer.DefinitionFactory;
import com.remedy.arsys.plugincontainer.DefinitionKey;
import com.remedy.arsys.plugincontainer.Plugin;
import com.remedy.arsys.plugincontainer.PluginContext;
import com.remedy.arsys.plugincontainer.PluginConfig;

import java.io.PrintWriter;
import java.io.InputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class RestPlugin implements Plugin {

    @Override
    public void processRequest(PluginContext pc) throws IOException {
        String path = pc.getRequest().getPathInfo();
        System.out.println("RestPlugin processRequest path: " + path);
    
        if (path != null && path.contains("params")) {
            pc.getResponse().setContentType("application/json");
            PrintWriter out = pc.getResponse().getWriter();
            out.print("{");
            out.print("\"name\": \"RestFetch\",");
            out.print("\"version\": \"1.0.0\",");
            out.print("\"type\": \"Visualizer\",");
            out.print("\"description\": \"REST Fetch test plugin\",");
            out.print("\"author\": \"AR Plugin\",");
            out.print("\"parameters\": [\"urlField\", \"targetField\"]");
            out.print("}");
            return;
        }
    
        // Standard rendering för DVF (iframe-innehåll)
        pc.getResponse().setContentType("text/html;charset=UTF-8");
        PrintWriter out = pc.getResponse().getWriter();
        out.println("<html><head><script>");
        out.println(pc.getPageService().getEventInfrastructureCode());
        out.println("function fetchAndSend(url) {");
        out.println(" EventDispatcher.sendEventToMidTier('FetchURL', url).then(js => eval(js));");
        out.println("}");
        out.println("</script></head><body>Waiting for input...</body></html>");
    }

    @Override
    public String handleEvent(PluginContext pc, String eventType, String eventData) {
        if (!"FetchURL".equals(eventType)) return "";
        String json = callExternalAPI(eventData);
        String escaped = json.replace("\"", "\\\\\"");
        return "EventDispatcher.sendEventToParent(\"RestResult\", \"" + escaped + "\");";
    }

    private String callExternalAPI(String url) {
        try (InputStream in = new URL(url).openStream()) {
            return new String(in.readAllBytes(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            return "{\"error\": \"" + e.getMessage().replace("\"", "\\\"") + "\"}";
        }
    }

    @Override
    public void cleanup() {}

    @Override
    public void init(PluginConfig config) {}

    @Override
    public DefinitionFactory getDefinitionFactory() {
        return new DefinitionFactory() {
            public Definition createFromString(PluginContext context, DefinitionKey key, String defJson) throws IOException {
                return null;
            }

            public Definition createFromStream(PluginContext context, DefinitionKey key, InputStream stream) throws IOException {
                return null;
            }
        };
    }
}
