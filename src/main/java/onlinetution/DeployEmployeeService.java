package onlinetution;

import com.docker.utils.DeployServiceUtils;

public class DeployEmployeeService {
    public static void main(String[] args) throws Exception {
        String servicePath = OnlineTuitionConstants.PATH + "OTEmployeeService";
        String dockerName = OnlineTuitionConstants.DOCKERNAME;
        String serviceName = "otemployee";
        String gridfsHost = OnlineTuitionConstants.GRIDFSHOST;
        String version = "1";
        String prefix = OnlineTuitionConstants.PREFIX;
        DeployServiceUtils.main(new String[]{"-x", prefix, "-p", servicePath, "-d", dockerName, "-s", serviceName, "-f", gridfsHost, "-v", version});
    }
}
