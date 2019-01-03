package shared;

import com.docker.utils.DeployServiceUtils;
import tuition.TuitionConstants;

public class DeployResourceWeb {
    public static void main(String[] args) throws Exception {
        String servicePath = SharedConstants.PATH + "ResourceWeb";
        String dockerName = SharedConstants.DOCKERNAME;
        String serviceName = "resource";
        String gridfsHost = TuitionConstants.GRIDFSHOST;
        String version = "1";
        String prefix = TuitionConstants.PREFIX;
        DeployServiceUtils.main(new String[]{"-x", prefix, "-p", servicePath, "-d", dockerName, "-s", serviceName, "-f", gridfsHost, "-v", version});
    }
}
