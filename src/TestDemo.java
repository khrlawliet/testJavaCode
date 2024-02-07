@FunctionalInterface
interface TestDemo {
    void sample();

    boolean equals(Object obj);

    String toString();

    default void testDef() {
    }

    ;
}
