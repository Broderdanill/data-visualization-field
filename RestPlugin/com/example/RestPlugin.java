package com.example;

import com.remedy.arsys.plugincontainer.*;

import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class RestPlugin implements Plugin {

    @Override
    public void processRequest(PluginContext pc) throws IOException {
        String path = pc.getRequest().getPathInfo();
        System.out.println(">> RestPlugin.processRequest path = " + path);

        if (path != null && path.contains("/params")) {
            pc.getResponse().setContentType("application/json");
            pc.getResponse().setHeader("Cache-Control", "no-cache");
            pc.getResponse().setHeader("Content-Disposition", "inline");
            PrintWriter out = pc.getResponse().getWriter();
            out.print("{ \"name\": \"RestFetch\", \"version\": \"1.0.3\", \"type\": \"Visualizer\", " +
                    "\"description\": \"REST Fetch test plugin\", \"author\": \"AR Plugin\", " +
                    "\"parameters\": [\"urlField\", \"targetField\"] }");
            return;
        }

        // Main visualizer rendering
        pc.getResponse().setContentType("text/html;charset=UTF-8");
        pc.getResponse().setHeader("Cache-Control", "no-cache");
        pc.getResponse().setHeader("Content-Disposition", "inline");

        PrintWriter out = pc.getResponse().getWriter();

        out.println("<html><head>");
        out.println("<script type='text/javascript' src='/arsys/resources/scripts/eventdispatcher.js'></script>");
        out.println("<script type='text/javascript'>");

        out.println("function tryInitDispatcher() {");
        out.println("  if (typeof EventDispatcher === 'undefined') {");
        out.println("    console.warn('EventDispatcher not yet loaded. Retrying...');");
        out.println("    setTimeout(tryInitDispatcher, 100);");
        out.println("    return;");
        out.println("  }");

        // Sätt parent-origin från referer
        out.println("  var origin = document.referrer.split('/').slice(0, 3).join('/');");
        out.println("  console.log('Setting parent origin to', origin);");
        out.println("  EventDispatcher.setParentOrigin(origin);");

        out.println("  EventDispatcher.subscribe('TriggerFetch', function(url) {");
        out.println("    console.log('TriggerFetch received with URL:', url);");
        out.println("    EventDispatcher.sendEventToMidTier('FetchURL', url).then(js => {");
        out.println("      console.log('JS from midtier:', js);");
        out.println("      try { eval(js); } catch(e) {");
        out.println("        document.body.innerHTML += '<pre style=\"color:red\">' + e + '</pre>'; ");
        out.println("        console.error('Eval error:', e);");
        out.println("      }");
        out.println("    });");
        out.println("  });");
        out.println("}");

        out.println("tryInitDispatcher();");
        out.println("</script>");
        out.println("</head><body>");
        out.println("<div id='status'>Waiting for REST fetch trigger...</div>");
        out.println("</body></html>");
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
            String err = "{\"error\": \"" + e.getMessage().replace("\"", "'") + "\"}";
            System.err.println(">> REST fetch error: " + err);
            return err;
        }
    }

    @Override
    public void cleanup() {}

    @Override
    public void init(PluginConfig config) {}

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
