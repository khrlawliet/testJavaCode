public final class Success<T> implements Try<T> {
private final T result;

    public Success(T result) {
        this.result = result;
    }

    @Override
    public T getResult() {
        return result;
    }

    @Override
    public Throwable getError() {
        throw new RuntimeException("Invalid invocation");
    }

}
