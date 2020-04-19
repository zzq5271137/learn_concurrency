package _10_Immutable;

/*
 * 演示如何让一个对象具有不变性, 即使这个对象的属性中有引用类型的;
 */

import java.util.HashSet;
import java.util.Set;

// 这个类有一个引用类型的属性, 但是这个类并没有提供修改该属性的方法给外界, 所以这个类的对象具有不变性
class ImmutableClass {
    private final Set<String> students = new HashSet<>();  // 引用类型的属性

    public ImmutableClass() {
        students.add("学生1");
        students.add("学生2");
        students.add("学生3");
    }

    public boolean isStudent(String name) {
        return students.contains(name);
    }
}

public class ImmutableObjectDemo {
    public static void main(String[] args) {
    }
}
