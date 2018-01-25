package ru.job4j.principle_003;

import java.util.Iterator;

/**
 * Iterator for even numbers.
 */
public class EvenIt implements Iterable<Integer> {
    /**
     * Array of numbers.
     */
    private final int[] data;

    /**
     * Default constructor.
     * @param data numbers.
     */
    public EvenIt(final int[] data) {
        this.data = data;
    }

    /**
     * ${@inheritDoc}.
     */
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
                if (!this.hasNext()) {
                    throw new IllegalStateException("No such element.");
                }
                this.point = EvenIt.this.findEven(this.point);
                return EvenIt.this.data[this.point++];
            }
        };
    }

    /**
     * Find event by positions.
     * @param start position of searching.
     * @return return the new position.
     */
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
