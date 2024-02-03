public final class Failure<T> implements Try<T> {
    private final Throwable throwable;

    public Failure(Throwable error) {
        this.throwable = error;
    }

    @Override
    public T getResult() {
        throw new RuntimeException("Invalid invocation");
    }

    @Override
    public Throwable getError() {
        return throwable;
    }
}
