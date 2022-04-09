package _06_ThreadLocal._01_HowToUse;

/*
 * 演示ThreadLocal使用场景二:
 * 每个线程内需要保存全局的变量(例如在拦截器中获取用户信息或请求信息), 可以让不同的方法直接使用, 避免传递参数的麻烦;
 *
 * 此种场景有一个非常经典的案例，就是Spring中的事务管理。
 * 假设m1()方法使用了事务管理，m1()里面调用了m2()和m3()方法，那么必须要求m1()、m2()、m3()三个方法使用的数据库连接是同一个，
 * 这里就使用到了ThreadLocal，就是把从数据库连接池里拿出的一个数据库连接放到ThreadLocal里，然后各个方法从ThreadLocal里拿出来用，就是同一个数据库连接，
 * 并且保证不同线程之间的数据库连接不会相互影响。
 */

class User {
    String name;

    public User(String name) {
        this.name = name;
    }
}

class UserContextHolder {
    private static ThreadLocal<User> userHolder = new ThreadLocal<>();

    public static User get() {
        return userHolder.get();
    }

    public static void set(User user) {
        userHolder.set(user);
    }

    public static void remove() {
        userHolder.remove();
    }
}

// 接收请求, 生成user对象的service, 对应到实际场景可以是过滤器或者拦截器
class Service1 {
    public void process(String username) {
        User user = new User(username);
        UserContextHolder.set(user);
        System.out.println(Thread.currentThread().getName() + "在Service1中接收到用户(" + user.name + ")的请求, 存入ThreadLocal");
        new Service2().process();
    }
}

class Service2 {
    public void process() {
        User user = UserContextHolder.get();
        System.out.println(Thread.currentThread().getName() + "在Service2中处理用户(" + user.name + ")的请求...");
        new Service3().process();
    }
}

class Service3 {
    public void process() {
        User user = UserContextHolder.get();
        System.out.println(Thread.currentThread().getName() + "在Service3中处理用户(" + user.name + ")的请求...");

        /*
         * 假设这个Service3是整个业务流程中最后调用ThreadLocal的地方,
         * 所以这里我们应该主动调用remove()方法, 以防止内存泄漏;
         * 详见ThreadLocalTips.java
         *
         * 在实际开发中, 我们如果是用拦截器的方法获取到用户信息并存入ThreadLocal, 那同样应该用拦截器的方法,
         * 在这个线程退出之前拦截住它, 并且把ThreadLocal中刚才保存的用户信息清除掉;
         */
        UserContextHolder.remove();
    }
}

public class ThreadLocalNormalUsage06 {
    public static void main(String[] args) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                new Service1().process("毛毛的大脚");
            }
        }).start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                new Service1().process("深海的星星");
            }
        }).start();
    }
}
