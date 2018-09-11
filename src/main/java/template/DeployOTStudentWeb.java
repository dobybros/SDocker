package template;

import com.docker.utils.DeployServiceUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

public class DeployOTStudentWeb {
    public static void main(String[] args) throws Exception {
        String servicePath = TemplateConstants.PATH + "OTStudentWeb";//
        String dockerName = TemplateConstants.DOCKERNAME;
        String serviceName = "htmls";
        String gridfsHost = TemplateConstants.GRIDFSHOST;
//        String version = "1";
        String prefix = TemplateConstants.PREFIX;
        DeployServiceUtils.main(new String[]{"-x", prefix, "-p", servicePath, "-d", dockerName, "-s", serviceName, "-f", gridfsHost});

        HttpGet get = new HttpGet("http://localhost:10055/rest/htmls/redeploy");
        HttpClient httpClient = new DefaultHttpClient();
        HttpResponse response = httpClient.execute(get);
        System.out.println("status " + response.getStatusLine());
    }
}
