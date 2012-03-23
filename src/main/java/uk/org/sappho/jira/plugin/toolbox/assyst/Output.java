package uk.org.sappho.jira.plugin.toolbox.assyst;

import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;

import java.io.FileWriter;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class Output {

    private static final Configuration freemarkerConfig = new Configuration();
    private static final HashMap templateData = new HashMap();
    private Date dateStamp;       
    private String jiraId;
    private String host;
    private String share;

    public Output(Date dateStamp, String assystId, String jiraId, String baseUrl, String host, String share) {
        this.dateStamp = dateStamp;
        this.jiraId = jiraId;
        this.host = host;
        this.share = share;
        freemarkerConfig.setClassForTemplateLoading(Output.class, "/uk/org/sappho/jira/plugin/toolbox/assyst");
        freemarkerConfig.setObjectWrapper(new DefaultObjectWrapper());
        templateData.put("baseUrl", baseUrl);
        templateData.put("assystId", assystId);
        templateData.put("jiraId", jiraId);
        templateData.put("date", dateStamp);
    }

    // get the full filename to use for a message
    public String getFilename(String templateName) throws UnknownHostException {
        return "\\\\" + InetAddress.getByName(host).getCanonicalHostName() + "\\" + share + "\\" +
                jiraId + "-" + templateName + "-" + new SimpleDateFormat("yyyyMMddHHmmss").format(dateStamp) + ".imp";
    }

    public void write(String filename, String templateName) throws Exception {
        Template template = freemarkerConfig.getTemplate(templateName + ".template");
        FileWriter out = new FileWriter(filename);
        template.process(templateData, out);
        out.close();
    }
}
