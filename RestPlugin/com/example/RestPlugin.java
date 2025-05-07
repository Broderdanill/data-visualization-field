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
import java.io.ByteArrayInputStream;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public class RestPlugin implements Plugin {

    @Override
    public void processRequest(PluginContext pc) throws IOException {
        String path = pc.getRequest().getPathInfo();

        if ("/params".equals(path)) {
            pc.getResponse().setContentType("application/json");
            PrintWriter out = pc.getResponse().getWriter();
            out.print("{ \"name\": \"RestPlugin\", \"version\": \"1.0.0\" }");
            return;
        }

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
        if (!eventType.equals("FetchURL")) return "";
        String json = callExternalAPI(eventData);
        String escaped = json.replace("\"", "\\\\\"");
        return "EventDispatcher.sendEventToParent(\"RestResult\", \"" + escaped + "\");";
    }

    private String callExternalAPI(String url) {
        try (InputStream in = new URL(url).openStream()) {
            return new String(in.readAllBytes(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            return "{\"error\": \"" + e.getMessage() + "\"}";
        }
    }

    @Override
    public void cleanup() {}

    @Override
    public void init(PluginConfig config) {}

    @Override
    public DefinitionFactory getDefinitionFactory() {
        return new DummyFactory();
    }

    // Inkluderad intern klass
    private static class DummyFactory implements DefinitionFactory {
        @Override
        public Definition createFromString(PluginContext context, DefinitionKey key, String defJson) {
            return null;
        }

        @Override
        public Definition createFromStream(PluginContext context, DefinitionKey key, InputStream stream) {
            return null;
        }

        @Override
        public String serializeToString(PluginContext context, DefinitionKey key, Definition def) {
            return "";
        }

        @Override
        public InputStream serializeToStream(PluginContext context, DefinitionKey key, Definition def) {
            return new ByteArrayInputStream(new byte[0]);
        }

        @Override
        public Map getSkinDefinition() {
            return null;
        }

        @Override
        public Map getInstanceDefinition() {
            return null;
        }
    }
}
