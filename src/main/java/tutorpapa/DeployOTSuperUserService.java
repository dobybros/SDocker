package tutorpapa;

import com.docker.utils.DeployServiceUtils;

public class DeployOTSuperUserService {
    public static void main(String[] args) throws Exception {
        String servicePath = TutorpapaConstants.PATH + "OTSuperUserService";
        String dockerName = TutorpapaConstants.DOCKERNAME;
        String serviceName = "otsuperuser";
        String gridfsHost = TutorpapaConstants.GRIDFSHOST;
        String version = "1";
        String prefix = TutorpapaConstants.PREFIX;
        DeployServiceUtils.main(new String[]{"-x", prefix, "-p", servicePath, "-d", dockerName, "-s", serviceName, "-f", gridfsHost, "-v", version});
    }
}
