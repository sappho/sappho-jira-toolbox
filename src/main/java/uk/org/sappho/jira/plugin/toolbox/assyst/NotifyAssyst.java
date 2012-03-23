package uk.org.sappho.jira.plugin.toolbox.assyst;

import com.atlassian.jira.ComponentManager;
import com.atlassian.jira.issue.Issue;
import com.opensymphony.module.propertyset.PropertySet;
import com.opensymphony.workflow.FunctionProvider;
import com.opensymphony.workflow.WorkflowException;

import java.util.Date;
import java.util.Map;

public class NotifyAssyst implements FunctionProvider {

    private static final String ACTION = "action";
    private static final String EVENT = "event";

    @Override
    public void execute(Map vars, Map args, PropertySet properties) throws WorkflowException {
        try {
            String baseUrl = (String) args.get(AssystParamFactory.BASEURL);
            String customFieldName = (String) args.get(AssystParamFactory.IDFIELDNAME);
            String host = (String) args.get(AssystParamFactory.HOST);
            String share = (String) args.get(AssystParamFactory.SHARE);
            Issue issue = (Issue) vars.get("issue");
            Object assystId = ComponentManager.getInstance().getCustomFieldManager().
                    getCustomFieldObjectByName(customFieldName).getValue(issue);
            if (assystId != null) {
                // if so then we report to Assyst as an action
                Output output = new Output(new Date(), (String) assystId, issue.getString("key"), baseUrl,
                        host, share);
                output.write(ACTION, output.getFilename(ACTION));
                output.write(EVENT, output.getFilename(EVENT));
            }
        } catch (Throwable throwable) {
            throw new WorkflowException("Unable to notify Assyst!", throwable);
        }
    }
}
