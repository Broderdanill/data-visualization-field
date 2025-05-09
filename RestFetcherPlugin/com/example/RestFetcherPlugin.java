package com.example;

import com.remedy.arsys.plugincontainer.*;

import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class RestFetcherPlugin implements Plugin {

    @Override
    public void processRequest(PluginContext pc) throws IOException {
        String path = pc.getRequest().getPathInfo();
        System.out.println(">> RestFetcherPlugin.processRequest path = " + path);

        if ("/params".equals(path)) {
            pc.getResponse().setContentType("application/json");
            pc.getResponse().setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
            pc.getResponse().setHeader("Pragma", "no-cache");
            pc.getResponse().setHeader("Expires", "0");
            pc.getResponse().setHeader("Content-Disposition", "inline");

            PrintWriter out = pc.getResponse().getWriter();
            out.print("{ \"name\": \"RestFetcher\", \"version\": \"1.0.16\", \"type\": \"Visualizer\", " +
                    "\"description\": \"Plugin that fetches REST data\", \"author\": \"AR Plugin\", " +
                    "\"parameters\": [\"urlField\", \"targetField\"] }");
            out.flush();
            return;
        }

        // Default HTML rendering (iframe)
        pc.getResponse().setContentType("text/html;charset=UTF-8");
        pc.getResponse().setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
        pc.getResponse().setHeader("Pragma", "no-cache");
        pc.getResponse().setHeader("Expires", "0");
        pc.getResponse().setHeader("Content-Disposition", "inline");

        PrintWriter out = pc.getResponse().getWriter();

        out.println("<!DOCTYPE html>");
        out.println("<html><head><meta charset='UTF-8'>");
        out.println("<script type='text/javascript'>");
        out.println(pc.getPageService().getEventInfrastructureCode());
        out.println("function init() {");
        out.println("  if (typeof EventDispatcher === 'undefined') {");
        out.println("    console.warn('EventDispatcher not ready. Retrying...');");
        out.println("    setTimeout(init, 200); return;");
        out.println("  }");
        out.println("  var origin = document.referrer.split('/').slice(0,3).join('/');");
        out.println("  console.log('[RestFetcher] Setting parent origin:', origin);");
        out.println("  EventDispatcher.setParentOrigin(origin);");
        out.println("  EventDispatcher.subscribe('TriggerFetch', function(url) {");
        out.println("    console.log('[RestFetcher] TriggerFetch received:', url);");
        out.println("    EventDispatcher.sendEventToMidTier('FetchURL', url).then(js => {");
        out.println("      try { eval(js); } catch(e) { console.error('Eval error:', e); }");
        out.println("    });");
        out.println("  });");
        out.println("}");
        out.println("window.addEventListener('load', init);");
        out.println("</script>");
        out.println("</head><body>");
        out.println("<div>RestFetcher is loaded and ready.</div>");
        out.println("</body></html>");
        out.flush();
    }

    @Override
    public String handleEvent(PluginContext pc, String eventType, String eventData) {
        System.out.println(">> handleEvent: " + eventType + " with data: " + eventData);

        if (!"FetchURL".equals(eventType)) return "";

        String json = callExternalAPI(eventData);
        String escaped = json
                .replace("\\", "\\\\")
                .replace("\"", "\\\"")
                .replace("\n", "")
                .replace("\r", "");

        return "EventDispatcher.sendEventToParent(\"RestResult\", \"" + escaped + "\");";
    }

    private String callExternalAPI(String url) {
        try (InputStream in = new URL(url).openStream()) {
            return new String(in.readAllBytes(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            return "{\"error\": \"" + e.getMessage().replace("\"", "'") + "\"}";
        }
    }

    @Override public void cleanup() {}
    @Override public void init(PluginConfig config) {}

    @Override
    public DefinitionFactory getDefinitionFactory() {
        return new DefinitionFactory() {
            @Override
            public Definition createFromString(PluginContext context, DefinitionKey key, String defJson) throws IOException {
                return null;
            }

            @Override
            public Definition createFromStream(PluginContext context, DefinitionKey key, InputStream stream) throws IOException {
                return null;
            }
        };
    }
}
