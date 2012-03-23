package uk.org.sappho.jira.plugin.toolbox.assyst;

import com.atlassian.jira.plugin.workflow.AbstractWorkflowPluginFactory;
import com.atlassian.jira.plugin.workflow.WorkflowPluginFunctionFactory;
import com.opensymphony.workflow.loader.AbstractDescriptor;
import com.opensymphony.workflow.loader.FunctionDescriptor;

import java.util.Arrays;
import java.util.Map;

public class AssystParamFactory extends AbstractWorkflowPluginFactory
        implements WorkflowPluginFunctionFactory {

    public static final String BASEURL = "baseUrl";
    public static final String IDFIELDNAME = "customFieldName";
    public static final String HOST = "host";
    public static final String SHARE = "share";

    @Override
    protected void getVelocityParamsForInput(Map<String, Object> params) {
        params.put(BASEURL, "");
        params.put(IDFIELDNAME, "");
        params.put(HOST, "");
        params.put(SHARE, "");
    }

    @Override
    protected void getVelocityParamsForEdit(Map<String, Object> params, AbstractDescriptor descriptor) {
        getVelocityParamsForView(params, descriptor);
    }

    @Override
    protected void getVelocityParamsForView(Map<String, Object> params, AbstractDescriptor descriptor) {
        params.put(BASEURL, getValue(descriptor, BASEURL));
        params.put(IDFIELDNAME, getValue(descriptor, IDFIELDNAME));
        params.put(HOST, getValue(descriptor, HOST));
        params.put(SHARE, getValue(descriptor, SHARE));
    }

    @Override
    public Map<String, ?> getDescriptorParams(Map<String, Object> params) {
        return extractMultipleParams(params, Arrays.asList(BASEURL, IDFIELDNAME, HOST, SHARE));
    }

    private String getValue(AbstractDescriptor descriptor, String key) {
        String value = null;
        if (descriptor instanceof FunctionDescriptor)
            value = (String) ((FunctionDescriptor) descriptor).getArgs().get(key);
        return value != null ? value.trim() : "";
    }
}
