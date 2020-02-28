import java.util.HashSet;
import java.util.Set;

/**
 * Test
 *
 * @author LuoQuan
 * @date 2020/2/28 11:48
 */
public class Test {
    public static void main(String[] args) {
        System.out.println(16 >> 2);
        Set<String> set = new HashSet<>();
        set.add("123");
        set.add("456");
        set.add("789");
        set.stream().map(item->{return "数据是"+item;}).forEach(System.out::println);
    }
}
