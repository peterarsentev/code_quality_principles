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
4. Don't use exceptions. Exceptions make look your code ugly.
5. Check-then-act statements

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
        boolean hasAccess(final User login) {
            if (this.checkName(login)) {
                if (this.checkSurname(login)) {
                    if (this.checkBalance(login)) {
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
    
        private boolean checkBalance(User login) {
            return false;
        }
    
        private boolean checkSurname(User login) {
            return false;
        }
    
        private boolean checkName(User login) {
            return false;
        }
    }
    
if we need to add more conditions, we should add more inner if else blocks.
This code will be difficult to read and maintenance. 

Let's make the refactoring.

First, split the validations part and logic part.

    boolean hasAccess(final User login) {
    if (!this.checkName(login)) {
        throw new IllegalStateException("Wrong name.");
    }
    if (!this.checkSurname(login)) {
        throw new IllegalStateException("Wrong surname.");
    }
    if (!this.checkBalance(login)) {
        throw new IllegalStateException("Wrong balance.");
    }
    return true;

Then, we need to replace multiple if statements to dispatch pattern.

    private final List<Consumer<User>> validates = Arrays.asList(   
            login -> {
                if (!this.checkName(login)) {
                    throw new IllegalStateException("Wrong name.");
                }
            },
            login -> {
                if (!this.checkSurname(login)) {
                    throw new IllegalStateException("Wrong surname.");
                }
            },
            login -> {
                if (!this.checkBalance(login)) {
                    throw new IllegalStateException("Wrong balance.");
                }
            }
    );

    boolean hasAccess(final User login) {
        this.validates.forEach(action -> action.accept(login));
        return true;
    }

 ### 4. Don't use exceptions. Exceptions make look your code ugly.

Frequently, my students ask me question about exception. Why do we need it or how to use it?
If you asked me such question, before I have written about this principle I would tell you follows:
Exception is used when you need to notify clients about unordinary situation in code. 
Generally, We just rethrow this exception or print it to console. We don't try to handle it.

If we had had such behavior in real life it would be ridiculous. Let's consider you need a taxi. 
When the taxi comes, a driver tells you that you need to take repair tools, 
because a car may break on road and you have to fix it. It is the same situation, 
when you use code, which throw exceptions.

Now I would like to propagate a new idea about exception.

Let's consider the follow common interface.
 
     public interface ExtResource<T> {
     
         T read(String name) throws Exception;
     
         void write(T value) throws Exception;
     } 
  
Such interface allows you to read and write a resource to external system. 
Each method may throw exception and you need to handle it.
We use try-catch statements for handling exception. 
Such code start to look ugly.

    public void writeToFile(T value, ExtResource<T> resource) {
        try {
            resource.write(value);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }           
    
This is a primitive sample. Look at more real example:

    public void update(User user) {
        try (final PreparedStatement ps = this.source.getConnection()
                .prepareStatement("update users set login=? where id=?")) {
            ps.setString(1, user.getLogin());
            ps.setInt(2, user.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
You start to make you code in copy-past style.

My idea is to get rid of any exceptions by lambla expression.

Let's create function interface with can do unary operation.

    public interface UnaryEx {
       
        void action() throws Exception;
    }

Now we need to create method, which catch exception and print it to console. 

    void ex(UnaryEx unary) {
        try {
            unary.action();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
Now, we can rewrite about code without try-catch statement.

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
    
How you can see, we don't have copy/past code now. 

Look at sample with JDBC method.

    public void update(User model) {
        this.db(
                "update users set login=? where id=?",
                List.of(model.getLogin(), model.getId()),
                ps -> {
                    ps.executeUpdate();
                }
        );
    }

This code is clean and clear.    

 ### 5. Check-and-act statements.
 
 Let's consider situation, when we try to find a element in collection then do somethings with it.
 
     public void addUser(User user) {
         if (!users.containsKey(user)) {
             users.putIfAbsent(user, new ArrayList<>());
         }
     }
 
     public Account findByRequisite(String passport, String requisite) {
         User user = findByPassport(passport);
         if (user != null) {
             for (Account account : users.get(user)) {
                 if (account.getRequisite().equals(requisite)) {
                     return account;
                 }
             }
         }
         return null;
     }
 
In this situation, we must check that an element is not null then do an action.
I can describe by a template
    
    Element el = find(...);
    if (el != null) { 
        // do an action with the element.
    }
   
Any condition statements decrease readability of you code.

I offer replace such construction check-then-act to two party: check-throw and act.

Let's look how I refactored it.

Situation with void method.

    public void addUser(User user) {
        if (users.containsKey(user)) {
            return;
        }
        users.put(user, new ArrayList<>());
    }
   
Situation with not-void method.

    public Account findByRequisite(String passport, String requisite) {
        User user = findByPassport(passport);
        if (user == null) {
            return null;
        }
        Account rsl = null;
        for (Account account : users.get(user)) {
            if (account.getRequisite().equals(requisite)) {
                rsl = account;
                break;
            }
        }
        return rsl;
    }
 
### How to handle null?

1.	return null.

    public User findByPassport(String passport) {
        for (User user : users.keySet()) {
            if (user.getPassport().equals(passport)) {
                return user;
            }
        }
        return null;
    }

Simple and obvious. Not safe for client.

2.	Throw checked-exception

    public User findByPassportIfNullThrow(String passport) throws NotFoundUserException {
        for (User user : users.keySet()) {
            if (user.getPassport().equals(passport)) {
                return user;
            }
        }
        throw new NotFoundUserException("User with passport " + passport + "not found");
    }

Simple and obvious. Overcoding in client side.

    try {
        var user = bank.findByPassportIfNullThrow("123");
        System.out.println(user.getUsername());
    } catch (NotFoundUserException e) {
        System.out.println("User not found.");
    }

4.	Throw Runtime-exception.

    public User findByPassportIfNullRuntime(String passport) {
        for (User user : users.keySet()) {
            if (user.getPassport().equals(passport)) {
                return user;
            }
        }
        throw new NullPointerException("User with passport " + passport + "not found");
    }

Simple and obvious. Not safe for client.

5.	Use Optional.

    public Optional<User> findByPassportIfNullOptional(String passport) {
        for (User user : users.keySet()) {
            if (user.getPassport().equals(passport)) {
                return Optional.of(user);
            }
        }
        return Optional.empty();
    }

Best choice. Lack in performance.

6.	Annotation @Nullable

    @Nullable
    public User findByPassportCheckByCompile(String passport) {
        for (User user : users.keySet()) {
            if (user.getPassport().equals(passport)) {
                return user;
            }
        }
        return null;
    }

Elegant. Overconfig needed. 

