package ru.job4j.principle_002;

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
