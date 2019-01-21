## Annotation
#### TimerTask
TimerTask注解在类上，被注解的类需要实现main方法，这样，在服务启动后，会创建一个新线程周期性调用main方法。
```
@TimerTask(period = 6000L, key = "helloHandler")
class TestHandler{
    public void main(){
        println "hello world"
    }
}
```
以上案例可以在服务启动后每隔6s执行一次main方法，并打印出"hello world"。每一个TimerTask必须有唯一的key。
#### RedeployMain
RedeployMain注解在类上，它与TimerTask类似， TimerTask是周期性调用，而RedeployMain只有在服务启动后调用一次。被注解的类要实现main方法，还要实现shutdown方法。main方法是在服务启动后调用，而shutdown方法是服务停止是调用，一般在shutdown方法中释放该服务打开的资源。
#### ConfigProperty
ConfigProperty 标注在属性上，可以把工程下config.properties中的某个字段赋值到被ConfigProperty标注的属性上。
> sample
```
config.properties文件内容为:
zk.host=localhost:1281
```
```
@Bean
class Test{
    @ConfigProperty(name = "zk.host")
    private String zkHost
}
```
使用到@ConfigProperty的类需要被@Bean注解，这样ConfigProperty才会生效。

