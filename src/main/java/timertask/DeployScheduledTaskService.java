package timertask;

import com.docker.utils.DeployServiceUtils;
import notification.NotificationConstants;

public class DeployScheduledTaskService {
    public static void main(String[] args) throws Exception {
        String servicePath = TimerTaskConstants.PATH + "ScheduledTaskService";
        String dockerName = TimerTaskConstants.DOCKERNAME;
        String serviceName = "scheduledtask";
        String gridfsHost = TimerTaskConstants.GRIDFSHOST;
        String version = "1";
        String prefix = TimerTaskConstants.PREFIX;
        DeployServiceUtils.main(new String[]{"-x", prefix, "-p", servicePath, "-d", dockerName, "-s", serviceName, "-f", gridfsHost, "-v", version});
    }

}
