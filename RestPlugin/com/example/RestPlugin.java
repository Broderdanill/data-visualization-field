import com.remedy.arsys.plugincontainer.Plugin;
import com.remedy.arsys.plugincontainer.PluginContext;

import java.io.PrintWriter;
import java.io.InputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class RestPlugin implements Plugin {
    @Override
    public void processRequest(PluginContext pc) throws IOException {
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
    String json = callExternalAPI(eventData); // Implement your HTTP call
    String escaped = json.replace("\"", "\\\\\"");
    return "EventDispatcher.sendEventToParent(\"RestResult\", \"" + escaped + "\");";
    }
    private String callExternalAPI(String url) {
    try (InputStream in = new URL(url).openStream()) {
    return new String(in.readAllBytes(), StandardCharsets.UTF_8);
    } catch (IOException e) {
    return "{\"error\": \"" + e.getMessage() + "\"}";
    }}
    }
    @Override
    public void cleanup() {
        // Inget att rensa, men krävs av interfacet
    }