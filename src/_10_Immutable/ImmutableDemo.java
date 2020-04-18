package _10_Immutable;

/*
 * 不可变对象的要求: 所有属性都由final修饰, 表示这个对象真真正正的不可被修改, 增加字段也不行;
 *
 * 演示不可变对象(具有不变性的对象), 演示无法修改这个对象的属性, 只能读取;
 */

class Person {
    final int age = 18;
    final String name = "ZzqGo";
}

public class ImmutableDemo {
    public static void main(String[] args) {
        Person person = new Person();
        System.out.println(person.name);
        System.out.println(person.age);

        // 会报错, 连编译都过不了, 无法修改
        // person.age = 20;
        // person.name = "ZzqGo2";
    }
}
