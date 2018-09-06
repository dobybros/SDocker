package payment;

import com.docker.utils.DeployServiceUtils;
import discovery.DiscoveryConstants;

public class DeployPaymentService {
    public static void main(String[] args) throws Exception {
        String servicePath = PaymentConstants.PATH + "PaymentService";
        String dockerName = PaymentConstants.DOCKERNAME;
        String serviceName = "paymentService";
        String gridfsHost = PaymentConstants.GRIDFSHOST;
        String version = "1";
        String prefix = PaymentConstants.PREFIX;
        DeployServiceUtils.main(new String[]{"-x", prefix, "-p", servicePath, "-d", dockerName, "-s", serviceName, "-f", gridfsHost, "-v", version});
    }
}
