package _06_ThreadLocal._01_HowToUse;

/*
 * 演示ThreadLocal使用场景二:
 * 每个线程内需要保存全局的变量(例如在拦截器中获取用户信息或请求信息), 可以让不同的方法直接使用, 避免传递参数的麻烦;;
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
