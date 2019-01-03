package tuition.getway;

import com.docker.utils.DeployServiceUtils;
import im.IMConstants;
import tuition.TuitionConstants;

public class DeployGWTuitionRoomService {
    public static void main(String[] args) throws Exception {
        String servicePath = TuitionConstants.PATH + "GWTuitionRoomService";
        String dockerName = "gateway";
        String serviceName = "gwtuitionroom";
        String gridfsHost = IMConstants.GRIDFSHOST;
        String version = "1";
        String prefix = IMConstants.PREFIX;
        DeployServiceUtils.main(new String[]{"-x", prefix, "-p", servicePath, "-d", dockerName, "-s", serviceName, "-f", gridfsHost, "-v", version});
    }
}
