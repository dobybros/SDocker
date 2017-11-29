package template;

import com.docker.utils.DeployServiceUtils;
import im.IMConstants;

public class DeployHtmlsService {
    public static void main(String[] args) throws Exception {
        String servicePath = TemplateConstants.PATH + "HtmlsService";//
        String dockerName = TemplateConstants.DOCKERNAME;
        String serviceName = "htmls";
        String gridfsHost = TemplateConstants.GRIDFSHOST;
//        String version = "1";
        String prefix = TemplateConstants.PREFIX;
        DeployServiceUtils.main(new String[]{"-x", prefix, "-p", servicePath, "-d", dockerName, "-s", serviceName, "-f", gridfsHost});
    }
}
