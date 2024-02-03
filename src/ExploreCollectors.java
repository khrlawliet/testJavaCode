import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static java.util.stream.Collectors.*;

public class ExploreCollectors {

    public static List<Person> createPeople() {
        return List.of(
                new Person("Jasmin", 20),
                new Person("Jasmin", 22),
                new Person("Bob", 20),
                new Person("Paula", 32),
                new Person("Paul", 32),
                new Person("Jack", 3),
                new Person("Jack", 72),
                new Person("Jill", 11)
        );
    }

    public static void main(String[] args) {
        System.out.println(new Persona("KErvin", "Bal"));
        System.out.println(testCase(10));
        System.out.println(testCase("Kervin"));
    }


    //Java 15
    public static String testString(String str) {
        var msg = """
    hello
    """;
        return msg;
    }

    // Java 14
 record Persona(String firstname, String Lastname) {}

    // Java 17
  public static String testCase(Object input) {
        return switch (input){
            case Integer i -> "this is integer";
            case String str -> "this is string " + input;
            default -> "whatever";

        };
  }

    private static void collectStreamFunction() {
        //example#1 - reduce
        //total Age via reduce
        //takes the collection reduces to a single value
        //reduce converts a Stream to something concrete
        //sout = 212
        System.out.println(createPeople().stream()
                .map(Person::getAge)
                .reduce(0, Integer::sum));

        //example#2 - collect
        //sout = [PAULA, PAUL, JACK]
        System.out.println(createPeople().stream()
                .filter(person -> person.getAge() > 30)
                .map(Person::getName)
                .map(String::toUpperCase)
                .collect(toList())); // collect is reduce

        //example#3 - toMap
        //Map name as key and age as value
        //sout = {Bob=20, Jasmin=20, Bill=72, Paula=32, Jack=3, Jill=11, Jas=22, Paul=32}
        System.out.println(createPeople().stream()
                .collect(toMap(Person::getName, Person::getAge)));

        //example#4 - toUnmodifiableList
        //create immutable collection of list from Stream
        //sout - [20, 22, 20, 32, 32, 3, 72, 11]
        List<Integer> ages = createPeople()
                .stream()
                .map(Person::getAge)
                .collect(toUnmodifiableList());

        System.out.println(ages);
        //example#5 - joining
        //Create a comma separated the names in uppercase
        // of people older than 30
        // return String
        // sout - PAULA, PAUL, JACK
        System.out.println(createPeople()
                .stream().filter(person -> person.getAge() > 30)
                .map(Person::getName)
                .map(String::toUpperCase)
                .collect(joining(", ")));

        //example#6 - partitioning
        // returns Map<Boolean, List<R>>
        //sout - {false=[Person@6e8dacdf, Person@7a79be86],
        // true=[Person@34ce8af7, Person@b684286, Person@880ec60,
        // Person@3f3afe78, Person@7f63425a, Person@36d64342]}
        System.out.println(createPeople().stream()
                .collect(partitioningBy(person -> person.getAge() % 2 == 0)));
        //sout - {false=[Jack, Jill], true=[Jasmin, Jasmin, Bob, Paula, Paul, Jack]}
        System.out.println(createPeople().stream()
                .collect(partitioningBy(person -> person.getAge() % 2 == 0,
                        mapping(Person::getName, toList()))));

        //example#7 - groupingBy single arg
        //group the people based on their name
        //return Map<T, List<R>>
        //sout - {Bob=[Person@3b6eb2ec], Jasmin=[Person@1e643faf, Person@6e8dacdf],
        // Jill=[Person@7a79be86], Jack=[Person@34ce8af7,
        // Person@b684286], Paula=[Person@880ec60], Paul=[Person@3f3afe78]}
        Map<String, List<Person>> byName = createPeople().stream()
                .collect(groupingBy(Person::getName));

        System.out.println(byName);

        //example#8 - groupingBy with mapping
        //groupingBy(Function<T,R> ==> Collector
        //groupingBy(Function<T,R>, Collector>
        //map middle of reduce/collect
        //use of Collectors.mapping
        //mapping = <Function<T,R>, Collector>
        //return Map<T, List<R>>
        //sout - {Bob=[20], Jasmin=[20, 22], Jill=[11], Jack=[3, 72], Paula=[32], Paul=[32]}
        System.out.println(createPeople()
                .stream()
                .collect(groupingBy(Person::getName, mapping(Person::getAge, toList()))));

        //example#9 - groupingBy with counting
        //count frequency of name
        //return Map<T, R>
        //sout - {Bob=1, Jasmin=2, Jill=1, Jack=2, Paula=1, Paul=1}
        System.out.println(createPeople()
                .stream()
                .collect(groupingBy(Person::getName, counting())));

        //example#10 - groupingBy with counting(collectingAndThen)
        //convert return type Long to Integer
        //groupingBy and mapping = (Function, Collector)
        //collectingAndThen(Collector, Function)
        //return Map<T, R>
        //sout - {Bob=1, Jasmin=2, Jill=1, Jack=2, Paula=1, Paul=1}
        System.out.println(createPeople()
                .stream()
                .collect(groupingBy(Person::getName,
                        collectingAndThen(counting(), Long::intValue))));

        //example#11 - maxBy get by Name
        //use Comparison
        //use of collectingAndThen
        //get max age by name
        //sout - Jack
        var res = createPeople().stream()
                .collect(
                        collectingAndThen(maxBy(Comparator.comparing(Person::getAge)),
                                person -> person.map(Person::getName).orElse("")
                        ));

        System.out.println(res);

        //example#12 - filtering
        //filtering of middle of collect
        //return Map<T, List<R>>
        //function inside mapping
        //sout - {32=[Paula], 3=[], 20=[Jasmin], 22=[Jasmin], 72=[], 11=[]}
        System.out.println(createPeople().stream()
                .collect(groupingBy(Person::getAge,
                        mapping(Person::getName,
                                filtering(name -> name.length() > 4,
                                        toList())))));

        //example#13 - flatmapping
        //one-to-many map flattening function
        //flattening the Stream<T>
        //sout - [J, a, s, m, i, n, J, a, s, m, i, n, B, o, b, P, a, u, l, a, P, a, u, l, J, a, c, k, J, a, c, k, J, i, l, l]
        System.out.println(createPeople()
                .stream().map(Person::getName)
                .flatMap(name -> Stream.of(name.split("")))
                .collect(toList()));

        //example#14 - groupingBy with mapping and flatMapping
        //groupingBy<Function, Collector.mapping<Function, Collector.flatMapping<Function, Collector.toSet>>>
        //sout - {32=[P, A, U, L], 3=[A, C, J, K], 20=[A, B, S, I, J, M, N, O], 22=[A, S, I, J, M, N], 72=[A, C, J, K], 11=[I, J, L]}
        System.out.println(createPeople()
                .stream()
                .collect(groupingBy(Person::getAge, //Function
                        mapping(person -> person.getName().toUpperCase(), //Collector
                                flatMapping(name -> Stream.of(name.split("")), //Collector
                                        toSet()))))); //Collector


        List<Integer> ints = List.of(1, 2, 3, 2, 1, 1, 3, 3, 4, 5, 5, 4, 4, 4, 4);

        System.out.println(
                ints.stream()
                        .collect(groupingBy(Integer::intValue, counting()))
                        .entrySet()
                        .stream().max(Map.Entry.comparingByValue())
        );

        System.out.println("total occurrence " +
                ints.stream()
                        .collect(groupingBy(Integer::intValue, counting()))
                        .entrySet()
                        .stream().max(Map.Entry.comparingByValue())
                        .map(Map.Entry::getValue)
                        .orElse(0L)
        );

        System.out.println("ans " +
                ints.stream()
                        .collect(collectingAndThen(groupingBy(Integer::intValue, counting()),
                                map -> map.entrySet()
                                        .stream()
                                        .max(Map.Entry.comparingByValue())
                                        .map(Map.Entry::getKey)
                                        .orElse(0)

                        ))
        );                                

    }

    private void notes() {
        //Note#1
        //Object-Oriented Programming: Polymorphism

        //Functional Composition + Lazy evaluation
        //Lazy evaluation requires purity of functions

        //Pure function returns the same result any number of times
        //we call it with the same input - idempotency

        //Pure function dont have side-effect:
        //1. Pure function do not change anything
        //2. Pure function do not depend on anything that may possibly change
        //-------------------------------------------------------------------

        //Collector<T, A, R>
        // T - Type
        // A - Accumulator
        // R - Return
        //--------------------------------------------------------------------

        //reduce - reduce, collect, sum
        //grouping mapping - (Function, Collector)
        //collectingAndThen - (Collector, Function)
        //teeing - (Collector, Collector, operation(BiFunction))

        //Stream
        //      .map(Function<T, R>) ===> Stream<R>
        //      .flatMap(Function<T,Iterator<R>> ===> Stream<R>
        // Iterator = Stream
        //---------------------------------------------------------------------

        //toList, toSet, toMap
        //toUnmodifiableList, toUnmodifiableSet, toUnmodifiableMap
        //-------------------------------------------------------------------------

        //collect(Collector)
        //groupingBy(Function<T,R>)
        //groupingBy(Function<T,R>, Collector)
        //groupingBy(Function<T,R>, Collector)
        //mapping(Function<T,R>, Collector)
        //collectingAndThen(Collector, Function<T,R>)
        //teeing(Collector, Collector, Collector)

    }


}
