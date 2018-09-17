package im;

import com.docker.utils.DeployServiceUtils;
import tuition.TuitionConstants;

public class DeployGWTuitionRoomService {
    public static void main(String[] args) throws Exception {
        String servicePath = IMConstants.PATH + "GWTuitionRoomService";
        String dockerName = "gateway";
        String serviceName = "gwtuitionroom";
        String gridfsHost = IMConstants.GRIDFSHOST;
        String version = "2";
        String prefix = IMConstants.PREFIX;
        DeployServiceUtils.main(new String[]{"-x", prefix, "-p", servicePath, "-d", dockerName, "-s", serviceName, "-f", gridfsHost, "-v", version});
    }
}
