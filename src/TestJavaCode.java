import java.sql.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.function.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class TestJavaCode {
    public static void main(String[] args) {
        List<Integer> s = Arrays.asList(1, 3, 2, 6, 1, 2);
        divisibleSumPairs(6, 3, s);
//        birthday(s, 4, 2);
//        TestSample testing = new TestSample();
//        testing.sample();
//        System.out.println(testBiFunc.apply(2, "demo"));
//        System.out.println(testFunc.apply(2));
//        testCons.accept("kervin");
//        System.out.println(testSupp.get());
//        System.out.println(testPrd.test(3));

        Integer[] numbers = {1, 2, 3, 4, 5, 6, 7, 8, 9};

        checkNumber(numbers, (x) -> x % 2 == 0);
        System.out.println("-----------------");
        checkNumber(numbers, (x) -> x % 2 != 0);

        var firstNames = List.of("John", "Jasmin", "Mark");

        firstNames.stream()
                .map(TestJavaCode::getTitle)
                .map(result -> result.map(String::toUpperCase))
                .map(result -> switch (result) {
                    case Success success -> success.getResult();
                    case Failure error -> error.getError();
                })
                .forEach(System.out::println);
    }

    // ar = [1, 3, 2, 6, 1, 2], n = 6, k = 3
    public static int divisibleSumPairs(int n, int k, List<Integer> ar) {
        // Write your code here
        var res = IntStream.range(0, n)
                .boxed()
                .map(e -> IntStream.range(e+1, n)
                        .boxed()
                        .filter(el -> (ar.get(el) + ar.get(e)) % k == 0 )
                        .count())
                .mapToInt(Long::intValue)
                .sum();

        System.out.println("res2 = " + res);
        return  res;
    }


    // s = [2,2,1,3,2], d=4, m=2
    public static int birthday(List<Integer> s, int d, int m) {
        List<List<Integer>> subList = IntStream
                .rangeClosed(0, s.size() - m)
                .mapToObj(i -> s.subList(i, i + m))
                .collect(Collectors.toList());
        return getCount(d, subList).intValue();
    }

    private static Long getCount(int d, List<List<Integer>> subList) {
        return subList.stream()
                .filter(e -> e.stream().mapToInt(Integer::intValue).sum() == d)
                .count();
    }

    public static void checkNumber(Integer[] numbers, Predicate<Integer> predicate) {
        ArrayList<Integer> integers = new ArrayList<>();
        Arrays.stream(numbers).filter(predicate).forEach(integers::add);
        integers.forEach(System.out::println);
    }

    public static Try<String> getTitle(String title) {
        return Try.of(() -> checkTitle(title));
    }

    public static String checkTitle(String title) throws Exception {
        if (title.startsWith("J")) {
            throw new Exception("Invalid Title");
        }
        return title;
    }

    static BiFunction<Integer, String, String> testBiFunc = (Integer i, String str) -> str.concat(i.toString());
    static Function<Integer, Integer> testFunc = (Integer i) -> i * i;
    static Consumer<String> testCons = (String str) -> System.out.println(str.concat(str.toUpperCase()));
    static Supplier<Integer> testSupp = () -> 3 * 3;
    static Predicate<Integer> testPrd = (Integer b) -> b.equals(3);

}


@FunctionalInterface
interface TestDemo {
    void sample();

    boolean equals(Object obj);

    String toString();

    default void testDef() {
    }

    ;
}


class TestSample implements TestDemo {
    @Override
    public void sample() {
        System.out.println("testing");
    }
}





