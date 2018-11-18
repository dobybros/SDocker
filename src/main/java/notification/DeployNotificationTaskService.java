package notification;

import com.docker.utils.DeployServiceUtils;

public class DeployNotificationTaskService {
    public static void main(String[] args) throws Exception {
        String servicePath = NotificationConstants.PATH + "NotificationTaskService";
        String dockerName = NotificationConstants.DOCKERNAME;
        String serviceName = "notificationtask";
        String gridfsHost = NotificationConstants.GRIDFSHOST;
        String version = "1";
        String prefix = NotificationConstants.PREFIX;
        DeployServiceUtils.main(new String[]{"-x", prefix, "-p", servicePath, "-d", dockerName, "-s", serviceName, "-f", gridfsHost, "-v", version});
    }
}
