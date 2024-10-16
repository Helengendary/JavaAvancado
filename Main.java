import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Main {

public static void main(String[] args) {

    ArrayList<Integer> list = new ArrayList<>();

    for (int i = 0; i < 6; i++) {
        list.add(i);
    }

    List<Integer> result = list.stream()
        .map(n -> n * n)
        .filter(n -> n% 2 ==1)
        .sorted((n, m) -> n - m)
        .limit(2)
        .collect(Collectors.toList());

    for (Integer value : result) {
        System.out.println(value);
    }
}
}