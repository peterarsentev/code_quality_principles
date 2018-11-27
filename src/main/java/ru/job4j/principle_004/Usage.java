package ru.job4j.principle_004;

/**
 * Usage of ExtResource.
 * @param <T> value.
 */
public class Usage<T> {

    /**
     * Execute actions with exception.
     * @param unary action.
     */
    void ex(UnaryEx unary) {
        try {
            unary.action();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Throw exception.
     * @param name name.
     * @param resource resources.
     * @throws Exception rethrow a possible exception.
     */
    public void readToFile(String name, ExtResource<T> resource) throws Exception {
        resource.read(name);
    }

    /**
     * Catch a possible exception.
     * @param value value.
     * @param resource resource.
     */
    public void writeToFile(T value, ExtResource<T> resource) {
        try {
            resource.write(value);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Read resource without exception.
     * @param name name.
     * @param resource resource.
     */
    public void read(String name, ExtResource<T> resource) {
        ex(() -> resource.read(name));
    }

    /**
     * Write resource without exception.
     * @param value value.
     * @param resource resources.
     */
    public void write(T value, ExtResource<T> resource) {
        ex(() -> resource.write(value));
    }


}
