import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import org.bson.Document;
import org.springframework.core.io.ClassPathResource;

import java.io.InputStream;
import java.util.Properties;

public class SDockerMain {

    /**
     * 日志记录器
     */

    private static final String defaultPort = "10055";
//    private static final String defaultPort = "10002";
    private static final String MAX_THREADS = "1024";
    private static final String WAR_PATH = "C:\\Users\\lulia\\work\\work_new\\sdocker\\SDocker\\war";

    public static void main(String[] args) {
//		docker.main.Main.main(new String[]{"-t", MAX_THREADS, "-p", defaultPort});
        Properties properties = new Properties();
        try {
            InputStream inS = new ClassPathResource("database.properties").getInputStream();
            properties.load(inS);
        }catch (Exception e){

        }

        String[] host = ((String)properties.get("database.host")).split("://")[1].split(":");
        MongoClient client = new MongoClient(host[0], Integer.parseInt(host[1]));
        MongoCollection collection = client.getDatabase("dockerdb").getCollection("dockers");
        Document query = new Document();
        query.put("ip", "192.168.1.196");
        query.put("serverType", "docker_tc");
        collection.deleteOne(query);
        chat.main.Main.main(new String[]{"-t", MAX_THREADS, "-p", defaultPort, "-w", WAR_PATH});
    }
}

