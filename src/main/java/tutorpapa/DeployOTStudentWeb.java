package tutorpapa;

import com.docker.utils.DeployServiceUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

public class DeployOTStudentWeb {
    public static void main(String[] args) throws Exception {
        String servicePath = TutorpapaConstants.PATH + "OTStudentWeb";
        String dockerName = TutorpapaConstants.DOCKERNAME;
        String serviceName = "student";
        String gridfsHost = TutorpapaConstants.GRIDFSHOST;
        String version = "1";
        String prefix = TutorpapaConstants.PREFIX;
        DeployServiceUtils.main(new String[]{"-x", prefix, "-p", servicePath, "-d", dockerName, "-s", serviceName, "-f", gridfsHost, "-v", version});

        HttpGet get = new HttpGet("http://www.tutorpapa.com/redeploy");
        HttpClient httpClient = new DefaultHttpClient();
        HttpResponse response = httpClient.execute(get);
        System.out.println("status " + response.getStatusLine());
    }
}
