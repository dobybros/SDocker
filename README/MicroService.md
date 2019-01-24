## 微服务搭建过程
1. 创建一个maven工程，名为TestService，在pom.xml将SDocker引入。
2. 在SDocker中配置Mongodb地址（这个Mongodb是用来发布服务用的），配置dockerName和发布服务的程序。
3. 在TestService的源目录下创建一个config.properties配置文件，此文件中需要配置Mongodb地址(这个Mongodb是此服务程序用到的),并且配置的属性可以通过@ConfigProperty(key = "....")注入到的被Bean标记的类的属性中。
4. 在SdDocker中实现发布程序,将TestService发布到Mongo中。
```
public interface TestServiceConstants {
    String PATH = "D:\\NewAc\\SharedServices\\";
    String LIB = "D:\\NewAc\\SharedServices\\SharedCore";
    //pay
    String DOCKERNAME = "test";
    String GRIDFSHOST = "mongodb://localhost:27017";
    String PREFIX = "scripts";
}

public class TestService {
    public static void main(String[] args) throws Exception {
        String servicePath = TestServiceConstants.PATH + "TestService";
        String dockerName = TestServiceConstants.DOCKERNAME;
        String libPath = TestServiceConstants.LIB;
        String serviceName = "testService";
        String gridfsHost = TestServiceConstants.GRIDFSHOST;
        String version = "1";
        String prefix = TestServiceConstants.PREFIX;
        DeployServiceUtils.main(new String[]{"-x", prefix, "-p", servicePath, "-d", dockerName, "-s", serviceName, "-f", gridfsHost, "-v", version, "-l", libPath});
    }
}
```
上述为发布程序，需要调运DeployServiceUtils.main()进行发布，需要参数如下:
- servicePath : 指定要发布的工程路径
- dockerName : 此工程打包到mongo中后，会被相同name的Docker拉取下来，解析并且运行。
- libPath ： 有时，发布的工程需要用到其它工程。这个配置指定公共库的存放位置，这样在发布时可以将依赖的工程一并打包到mongo中。
- serviceName : 指定服务名，在远程服务调用时可以指定调用哪个service。@ServiceBean(name = "需要调运的serviceName")。远程调用时，底层的实现是通过serviceName, className, methodName定位到需要远程调用的方法。
- version : 指定服务的版本号，同一服务可以有多个版本，在远程调用时可以指定调用哪个版本的service。
- prefix : 指定前缀

5. 在SDocker工程中/resources/props/server_params.properties配置SDocker的service.type,这样在启动sdocker时会从mongo中拉取dockerName与service.type相同的service，并在SDocker中运行。
## Annotation
SDocker实现了微服务化，对使用者而言，只需要RemoteService与ServiceBean注解即可。
被标注为RemoteService的类在服务启动时会被扫描并实例化，把它当做一个远程服务，这样就可以对它远程调用了。

6. 启动SDocker，SDocker拉取TestService,这样TestService就部署到了SDocker上。
#### RemoteService
标注为RemoteSevice的类可以被远程service调用
#### ServiceBean
标注为ServiceBean的属性可以被RemoteService标注的远程Bean注入。
> sample
```
远程Bean

interface Service1{
    def sayHi()
}

@RemoteService
class Service1 implements Service1{
   def sayHi(){
       println "hi"
   } 
}

注入远程Bean
@Bean 或 @ControllerMapping 标注
class Service2{
    @ServiceBean(name="testService")
    private Service1 service1 //Service1只是一个接口
    def invoke(){
        service1.sayHi()
    }
}
```
这样，即使他们不在同一个进程，也可以进行依赖注入，并且远程调用, name 属性指定需要调用的远程服务的服务名。 远程调用的底层实现为RMI。


### 分布式任务调度(ScheduledTaskService)
> 此分布式任务调度系统是作为一个独立的服务，它启动后会周期性从数据库中拿任务。拿到任务后把任务加入到定时任务中，并且定时执行。任务的执行实质上是一个远程服务调用的过程，任务提供方需要调运ScheduledTaskService提供的远程服务来添加任务，并且指明回调接口（服务名，类名，方法名），当
到达任务执行时间时，任务调度系统会通过服务名，类名，方法名找到远程服务接口，进行远程调用。

> 特性
- 可以把多个任务平均分配到多个ScheduleTaskService节点， 解决单点压力。
- 在ScheduleTaskService节点宕机的情况下，自动将此节点准备执行和执行未完成的任务转移到其它节点上执行。
- 在部署过程中添加新任务，如果新任务id在数据库中存在并且新任务数据发生变化，这样就可覆盖数据库中的旧任务，并且再下一次执行时调用新的任务。
（支持周期任务和一次性任务，一次性任务必须是未执行完成才可生效）

> 功能
- 支持一次性任务， 周期性任务。
- 可以取消未执行的任务和准备执行的任务。
- 可以设置执行任务失败时的重试次数与重试时间间隔。

> 此系统依赖于zookeeper,需要zookeeper对TaskService进行故障检测。需要在ScheduledTaskService工程下对config.properties进行配置
- zk.host : 指定zookeeper。
- zk.root.path ： 指定临时节点在zk中存放的位置
- zk.timeout ： 指定zookeeper会话时长 /ms

> 使用
1. 创建ScheduleTask对象 <br/>
    参数 :
- notify 需要回调的serviceName
- scheduledTime 调度时间 /毫秒
- allowedExpireSeconds 允许过期时长,默认半小时 /毫秒
- relatedId 与业务关联的id，服务端自定义
- period 周期性任务时间间隔，此字段为空时是一次性任务
- deleteOnEnded 任务执行完成后是否从数据库删除任务
- className, methodName, version 需要回调接口所在类名，方法名，所在版本号 /1
- tryTimes 周期性任务调用失败重试次数 /3次
- tryPeriod 周期性任务调用失败重试时间间隔 /毫秒

2.回调实现方法： <br/>
（1）继承ScheduledTaskReturner类，重写returnScheduledTask方法，并且提供notify参数 <br/>
（2）提供notify,className,methodName参数。

3.调运ScheduleTaskService 远程服务接口，通过addTask即可完成任务的添加。

4.调运ScheduleTaskService cancelTaskById或者cancelTaskByRelatedId方法完成任务取消。

> 案例
```
创建需要回调的远程服务(假设此服务为callback)
@RemoteService
class Callback{
    def test(){
        println "task start"
    }
}

调用ScheduledTaskService服务添加任务
@RequestMapping(uri = "@PREFIX/scheduledtask/addTask", method = GroovyServlet.GET)
def addTask(HttpServletRequest request, HttpServletResponse response){
    ScheduledTask scheduledTask = new ScheduledTask()
    scheduledTask.setId("HELLO_WORLD") //任务id,尽量指定，否则在分布式下会添加多个重复任务
    scheduledTask.setScheduledTime(System.currentTimeMillis()) //任务执行时间
    scheduledTask.setClassName("Callback")
    scheduledTask.setMethodName("test") //需要回调的方法
    scheduledTask.setInfo("hello  world") 
    scheduledTask.setNotify("callback") // 需要回调的服务名
    scheduledTask.setRelatedId("reid") // 与业务相关的id，可以不填
    scheduledTask.setTryPeriod(3) //执行失败重试时间间隔
    scheduledTask.setTryTimes(3) //执行失败重试次数
    scheduledTaskService.addTask(scheduledTask)
}
```
几秒钟后，会回调Callback类test方法。

## tcc分布式事务(DistributeTransactionService)
> DistributeTransactionService是一个分布式事务，它是一个独立的服务，它可以将多个远程服务组合成一个完整的事务，在执行时依次调用远程服务。它分为try, confirm, cancel三个阶段， confirm阶段可有可无,try阶段与cancel阶段要具有一一对应的关系。try阶段进行预留资源，confirm阶段确认资源被消费，cancel阶段在try阶段出错时，对预留的资源进行释放或者还原。如果在confirm或者cancel阶段出错，默认会重试三次，如果还是不成功，则标记此次事务为失败，这时需要人工干预恢复。
被标记为失败的事务会在DistributeTransactionService宕机，或者重启时会在上一次执行失败的地方继续执行。

> 特性
- 多个服务组成原子操作，要么执行成功，要么执行失败
- 服务宕机后不会影响事务执行
- 充分利用集群的特性，支持高并发

> 涉及的注解
1. @Transaction(id = "test_abnormal2", order = 1, type = TCC.TRY)  指定为try阶段第一步，order从1开始。
2. @Transaction(id = "test_abnormal2", order = 1, type = TCC.CONFIRM) 指定为confirm阶段第一步。
3. @Transaction(id = "test_abnormal2", order = 1, type = TCC.CANCEL) 指定为cancel阶段第一步，与try第一步对应。
4. @Summary(id = "test_abnormal2", maxTry = 1, maxConfirm = 1, maxCancel = 1) 指定事务每个阶段的执行步骤。
5. @TransactionResultNotify(id = "test_abnormal2") 事务最终执行结果异步回调 （因为可能会进行重试阶段）

如果注解标记完整，则可调用DistributeTransactionService服务的 TransactionService execute方法执行事务，传入的info参数
    由业务具体指定，在执行事务时会被带到事务标记的每个方法中。
    
 注意：
1. Summary所指的每个阶段的步骤数必须与Transaction中实现的数量相同，否则标记为不完整事务
2. Transaction中每个阶段的order必须连续递增，不能有断层，否则标记为不完整事务
3. @TransactionResultNotify 可有可无
4. 由于分布式事务可能会造成对某个方法的重复调用，所以在业务层要实现幂等。（重复调用几率很低）

> 此系统依赖于zookeeper,需要zookeeper对TaskService进行故障检测。需要在ScheduledTaskService工程下对config.properties进行配置
- zk.host : 指定zookeeper。
- zk.root.path ： 指定临时节点在zk中存放的位置
- zk.timeout ： 指定zookeeper会话时长 /ms

> 事例
```
@RemoteService
class NormalDTService {
    private static final TAG = NormalDTService.class.simpleName

    @Summary(id = "test_normal0", maxTry = 2, maxConfirm = 3, maxCancel = 2)
    @Transaction(id = "test_normal0", order = 1, type = TCC.TRY)
    public def try1(String transactionId, def info) throws CoreException {
        println ("************try1**************")
    }

    @Transaction(id = "test_normal0", order = 2, type = TCC.TRY)
    void try2(String transactionId, def info)
            throws CoreException {
        println ("************try2**************")
    }

    @Transaction(id = "test_normal0", order = 1, type = TCC.CONFIRM)
    void confirm1(String transactionId, def info)
            throws CoreException {
        println ("************confirm1**************")
    }

    @Transaction(id = "test_normal0", order = 2, type = TCC.CONFIRM)
    void confirm2(String transactionId, def info) throws CoreException {
        println ("************confirm2**************")
    }

    @Transaction(id = "test_normal0", order = 3, type = TCC.CONFIRM)
    void confirm3(String transactionId, def info) throws CoreException {
        println ("************confirm3**************")
    }

    @Transaction(id = "test_normal0", order = 1, type = TCC.CANCEL)
    void cancel1(String transactionId, def info)
            throws CoreException {
        println ("************cancel1**************")
    }

    @Transaction(id = "test_normal0", order = 2, type = TCC.CANCEL)
    void cancel2(String transactionId, def info)
            throws CoreException {
        println ("************cancel2**************")
    }

    @TransactionResultNotify(id = "test_normal0")
    void error(def transactionResult)
            throws CoreException {
        println ("************this is async callback**************")
    }
}

@Bean
class Test{
    @ServiceBean(name = "distributetransaction")
    private TransactionService transactionService
    def invoke(){
        transactionService.execute("test_normal0", "hello world")
    }
}
```
execute函数第一个参数为事务id（被相同id标记的方法组合成同一事务），第二个参数为业务需要传递到事务每个阶段的需要处理的信息。<br/>

执行结果
```
************try1**************
************try1**************
************confirm1**************
************confirm2**************
************confirm3**************
************this is async callback**************
```
通过@TransactionResultNotify(id = "test_normal0") 标记的方法接受的transactionResult，可以获取事务执行结果。
