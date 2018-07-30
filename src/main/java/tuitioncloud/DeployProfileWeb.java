package tuitioncloud;

import com.docker.utils.DeployServiceUtils;
import onlinetuition.OnlineTuitionConstants;

public class DeployProfileWeb {
    public static void main(String[] args) throws Exception {
        String servicePath = TuitionCloudConstants.PATH + "ProfileWeb";//
        String dockerName = TuitionCloudConstants.DOCKERNAME;
        String serviceName = "acuprofile";
        String gridfsHost = TuitionCloudConstants.GRIDFSHOST;
//        String version = "1";
        String prefix = TuitionCloudConstants.PREFIX;
        DeployServiceUtils.main(new String[]{"-x", prefix, "-p", servicePath, "-d", dockerName, "-s", serviceName, "-f", gridfsHost});

//        HttpGet get = new HttpGet("http://localhost:10055/rest/htmls/redeploy");
//        HttpClient httpClient = new DefaultHttpClient();
//        HttpResponse response = httpClient.execute(get);
//        System.out.println("status " + response.getStatusLine());
    }
}
