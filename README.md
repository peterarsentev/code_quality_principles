[travis-ci.org](http://www.travis-ci.org)<br/>
[![Build Status](https://travis-ci.org/peterarsentev/code_quality_principles.svg?branch=master)](https://travis-ci.org/peterarsentev/code_quality_principles)



Code quality principles.
========================

The project contains principles, which improve code quality.

Below, you can find list of principles. 
Each principles has examples of bad and good code snippets with explanations.

Contribute
----------

I will appreciate, if you share challenges code snippets or add other useful principles with examples.
If you have any questions, feel free to contact me. Skype : petrarsentev

Content
-------------------
1. Multiple return statements
2. Multiple if statements and switch anti-pattern
3. If-else-throw statements

#### 1. Multiple return statements

All methods must have only an one return statement. It should be at the end of method.

Bad code.

    int max(int left, int right) {
        if (left > right) {
            return left;
        } else {
            return right;
        }
    }
    
Good code.

    int max(int left, int right) {
        return left > right ? left : right;
    }
    
#### 2. Dispatch pattern instead of multiple if statements and switch anti-pattern.

Every time, when you see code like this below, replace it to dispatch pattern.

Multiple if statements.

    public boolean sent(final Message msg) {
        boolean rsl = false;
        if (msg.type() == Type.EMAIL) {
            // sent to email.
            rsl = true;
        } else if (msg.type() == Type.JABBER) {
            // sent to jabber.
            rsl = true;
        } else if (msg.type() == Type.TWITTER) {
            // sent to twitter.
            rsl = true;
        } else if (msg.type() == Type.ETC) {
            // sent to etc.
            rsl = true;
        }
        return rsl;
    }
    
Switch cases statements.

    public boolean sent(final Message msg) {
        final boolean rsl;
        switch (msg.type()) {
            case EMAIL:
                // sent to email.
                rsl = true;
                break;
            case JABBER:
                // sent to jabber.
                rsl = true;
                break;
            case TWITTER:
                // sent to twitter.
                rsl = true;
                break;
            default:
                // sent to etc.
                rsl = false;
                break;
        }
        return rsl;
    }
    
Dispatch pattern.

    import java.util.HashMap;
    import java.util.Map;
    import java.util.function.Function;

    /**
     * Dispatch pattern.
     *
     * @author Petr Arsentev (parsentev@yandex.ru)
     * @version $Id$
     * @since 0.1
     */
    public class DispatchPattern {

        /**
         * Contains destinations.
         */
        private final Map<Message.Type, Function<Message, Boolean>> dispatch = new HashMap<>();

        /**
         * Handle to email.
         * @return handle.
         */
        public Function<Message, Boolean> toEmail() {
            return msg -> {
                // sent to email.
                return true;
            };
        }

        /**
         * Handle to unknown.
         * @return handle.
         */
        public Function<Message, Boolean> toUnknown() {
            return msg -> {
                // sent to unknown.
                return false;
            };
        }

        /**
         * Init's dispatch.
         * @return current object.
         */
        public DispatchPattern init() {
            this.load(Message.Type.EMAIL, this.toEmail());
            this.load(Message.Type.UNKNOWN, this.toUnknown());
            return this;
        }
    
        /**
         * Load handlers for destinations.
         * @param type Message type.
         * @param handle Handler.
         */
        public void load(Message.Type type, Function<Message, Boolean> handle) {
            this.dispatch.put(type, handle);
        }
    
        /**
         * Sent message to dispatch.
         * @param msg message
         * @return true if it finds in a dispatch.
         */
        public boolean sent(final Message msg) {
            return this.dispatch.get(
                    msg.type()
            ).apply(msg);
        }
    }

The main benefit of dispatch pattern:
1. All codes are splitted on independent small methods.
2. Flexible extension.

Example.

Let's consider situation, when we need to calculate permission for person by age.

Here, We have non strict values, as it is on above example. 
It need to check a predict in order to execute a handler. 
It can be done only by loop, so such method works by O(n), not like example above (O(1)).
Actually, I don't know how to change this methods in order to it works by O(1). 
I will be glad to hear a such solution from you.


Permission table.

    < 14 - forbidden.
    >= 14 and < 18 - limit access.
    > 18 - free.

Full code.

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
         * Check access for person by age.
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
    
    import org.junit.Test;
    import static org.hamcrest.core.Is.is;
    import static org.junit.Assert.assertThat;
    
    /**
     * Test for person permission  by age.
     *
     * @author Petr Arsentev (parsentev@yandex.ru)
     * @version $Id$
     * @since 0.1
     */
    public class DispatchDiapasonTest {
    
        /**
         * Between 14 and 18.
         */
        @Test
        public void whenBetween14and18ThenLimited() {
           assertThat(
                   new DispatchDiapason().init().access(
                           () -> 16
                   ),
                   is(Person.Access.LIMIT)
           );
        }
    
        /**
         * Up 18 age.
         */
        @Test
        public void whenUp18AgeThenFree() {
            assertThat(
                    new DispatchDiapason().init().access(
                            () -> 21
                    ),
                    is(Person.Access.FREE)
            );
        }
    
        /**
         * Under 14 age.
         */
        @Test
        public void whenLess14ThenForbidden() {
            assertThat(
                    new DispatchDiapason().init().access(
                            () -> 10
                    ),
                    is(Person.Access.FORBIDDEN)
            );
        }
    }

### 3. If-else-throw statements.

Let's consider the follows code. We need to implement the iterator for even numbers.

    package ru.job4j.principle_003;

    import java.util.Iterator;
    
    public class EvenIt implements Iterable<Integer> {
        private final int[] data;
    
        public EvenIt(final int[] data) {
            this.data = data;
        }
    
        @Override
        public Iterator<Integer> iterator() {
    
            return new Iterator<Integer>() {
                private int point;
    
                @Override
                public boolean hasNext() {
                    return EvenIt.this.findEven(this.point) >= 0;
                }
    
                @Override
                public Integer next() {
                    if (this.hasNext()) {
                        this.point = EvenIt.this.findEven(this.point);
                        return EvenIt.this.data[this.point++];
                    } else {
                        throw new IllegalStateException("No such element.");
                    }
                }
        }
    
        private int findEven(final int start) {
            int rst = -1;
            for (int index = start; index != this.data.length; index++) {
                if (this.data[index] % 2 == 0) {
                    rst = index;
                    break;
                }
            }
            return rst;
        }
    }

Let's focus of this snippet.

    @Override
    public Integer next() {
        if (this.hasNext()) {
            this.point = EvenIt.this.findEven(this.point);
            return EvenIt.this.data[this.point++];
        } else {
            throw new IllegalStateException("No such element.");
        }
    }

This short code looks good, but it can be improved by removing else statement.

    if (!this.hasNext()) {
        throw new IllegalStateException("No such element.");
    } 
    this.point = EvenIt.this.findEven(this.point);
    return EvenIt.this.data[this.point++];
    
The main differents from this two snippets is splitting the validation part and main logic part.
Right now, we have two independents parts of code.

Let's consider more complex situation.

We need to validate the users input by few conditions.

We can do by this one:

    public class Credential {
        boolean hasAccess(final User user) {
            if (this.checkName(user)) {
                if (this.checkSurname(user)) {
                    if (this.checkBalance(user)) {
                        return true;
                    } else {
                        throw new IllegalStateException("Wrong balance.");
                    }
                } else {
                    throw new IllegalStateException("Wrong surname.");
                }
            } else {
                throw new IllegalStateException("Wrong name.");
            }
        }
    
        private boolean checkBalance(User user) {
            return false;
        }
    
        private boolean checkSurname(User user) {
            return false;
        }
    
        private boolean checkName(User user) {
            return false;
        }
    }
    
if we need to add more conditions, we should add more inner if else blocks.
This code will be difficult to read and maintenance. 

Let's make the refactoring.

First, split the validations part and logic part.

    boolean hasAccess(final User user) {
    if (!this.checkName(user)) {
        throw new IllegalStateException("Wrong name.");
    }
    if (!this.checkSurname(user)) {
        throw new IllegalStateException("Wrong surname.");
    }
    if (!this.checkBalance(user)) {
        throw new IllegalStateException("Wrong balance.");
    }
    return true;

Then, we need to replace multiple if statements to dispatch pattern.

    private final List<Consumer<User>> validates = Arrays.asList(   
            user -> {
                if (!this.checkName(user)) {
                    throw new IllegalStateException("Wrong name.");
                }
            },
            user -> {
                if (!this.checkSurname(user)) {
                    throw new IllegalStateException("Wrong surname.");
                }
            },
            user -> {
                if (!this.checkBalance(user)) {
                    throw new IllegalStateException("Wrong balance.");
                }
            }
    );

    boolean hasAccess(final User user) {
        this.validates.forEach(action -> action.accept(user));
        return true;
    }

       