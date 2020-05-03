package _11_ConcurrentCollections._02_ConcurrentHashMap._01_MapIntro;

/*
 * 演示Map接口常用方法;
 */

import java.util.HashMap;
import java.util.Map;

public class MapDemo {
    public static void main(String[] args) {
        Map<String, Integer> map = new HashMap<>();
        map.put("k1", 127);
        map.put("k2", 345);
        System.out.println(map.isEmpty());
        System.out.println(map.keySet());
        System.out.println(map.get("k1"));
        System.out.println(map.size());
        System.out.println(map.containsKey("k1"));
    }
}
