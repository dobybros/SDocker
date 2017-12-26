package im;

import com.docker.utils.DeployServiceUtils;
import template.TemplateConstants;

public class DeployIMMonitorService {
    public static void main(String[] args) throws Exception {
        String servicePath = TemplateConstants.PATH + "IMMonitorService";//
        String dockerName = TemplateConstants.DOCKERNAME;
        String serviceName = "immonitor";
        String gridfsHost = TemplateConstants.GRIDFSHOST;
//        String version = "1";
        String prefix = TemplateConstants.PREFIX;
        DeployServiceUtils.main(new String[]{"-x", prefix, "-p", servicePath, "-d", dockerName, "-s", serviceName, "-f", gridfsHost});
    }
}
