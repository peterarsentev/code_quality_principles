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
                if (!this.hasNext()) {
                    throw new IllegalStateException("No such element.");
                }
                this.point = EvenIt.this.findEven(this.point);
                return EvenIt.this.data[this.point++];
            }
        };
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
