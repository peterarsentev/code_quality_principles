package ru.job4j.principle_002;

import java.util.LinkedHashMap;
import java.util.function.Function;

/**
 * Dispatch pattern for diapason key.
 *
 * @author Petr Arsentev (parsentev@yandex.ru)
 * @version $Id$
 * @since 0.1
 */
public class DispatchDiapason {
    /**
     * Dispatch.
     */
    private final LinkedHashMap<Function<Person, Boolean>, Function<Person, Person.Access>> dispatch = new LinkedHashMap<>();

    /**
     * Load initial handlers.
     * @return current object.
     */
    public DispatchDiapason init() {
        this.dispatch.put(
                person -> person.age() < 14,
                person -> Person.Access.FORBIDDEN
        );
        this.dispatch.put(
                person -> person.age() >= 14 && person.age() < 18,
                person -> Person.Access.LIMIT
        );
        this.dispatch.put(
                person -> person.age() >= 18,
                person -> Person.Access.FREE
        );
        return this;
    }

    /**
     * Load handler and predict.
     * @param predict Predict.
     * @param handle Handle.
     */
    public void load(Function<Person, Boolean> predict, Function<Person, Person.Access> handle) {
        this.dispatch.put(predict, handle);
    }

    /**
     * Chech access for person by age.
     * @param person Person
     * @return true if access are allowed
     */
    public Person.Access access(Person person) {
        for (Function<Person, Boolean> predict : this.dispatch.keySet()) {
            if (predict.apply(person)) {
                return this.dispatch.get(predict).apply(person);
            }
        }
        throw new IllegalStateException("Could not found a handle for person");
    }
}
