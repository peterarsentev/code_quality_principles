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

List of principles.
-------------------

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

The main benefit of distpatch pattern:
1. All codes are splitted on independent small methods.
2. Flexible extension.