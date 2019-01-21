## Annotation
#### Bean
添加了Bean注解的类会在服务启动时被实例化，这样就可以在需要用到此类的类中进行注入。Bean还可以添加在属性上，这样就可以把实例化后的类注入到属性中。
> sample
```
@Bean
class Test1{
    def sayHi(){
        println("hi")
    }
}
```
创建了类Test1,并且标注为一个Bean。
```
@Bean
class Test2{
    @Bean
    private Test1 test1
    def invoke(){
        test1.sayHi()
    }
}
```
类Test2通过@Bean将Test1注入到属性test1,这样test1的引用就指向了被实例化的Test1。
上述是通过类型自动注入，如果有多个相同类型的Bean,可以指定Bean 的 name属性， 这样就可以根据 name选择性注入。
被Bean标注的类只能在当前进程中调用，如果需要在不同进程间调用，需要用到ServiceBean。
