package notification;

import com.docker.utils.DeployServiceUtils;
import discovery.DiscoveryConstants;

public class DeployNotificationService {
    public static void main(String[] args) throws Exception {
        String servicePath = NotificationConstants.PATH + "NotificationService";
        String dockerName = NotificationConstants.DOCKERNAME;
        String serviceName = "notification";
        String gridfsHost = NotificationConstants.GRIDFSHOST;
        String version = "1";
        String prefix = NotificationConstants.PREFIX;
        DeployServiceUtils.main(new String[]{"-x", prefix, "-p", servicePath, "-d", dockerName, "-s", serviceName, "-f", gridfsHost, "-v", version});
    }
}
