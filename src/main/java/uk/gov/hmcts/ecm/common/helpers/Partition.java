package uk.gov.hmcts.ecm.common.helpers;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;

public final class Partition<T> extends AbstractList<List<T>> {

    private final transient List<T> list;
    private final transient int chunkSize;
    private static final String ERROR_MESSAGE = "Index %s is out of the list range <0,%s>";

    public Partition(List<T> list, int chunkSize) {
        this.list = new ArrayList<>(list);
        this.chunkSize = chunkSize;
    }

    public static <T> Partition<T> ofSize(List<T> list, int chunkSize) {
        return new Partition<>(list, chunkSize);
    }

    @Override
    public List<T> get(int index) {
        int start = index * chunkSize;
        int end = Math.min(start + chunkSize, list.size());
        if (start > end) {
            throw new IndexOutOfBoundsException(String.format(ERROR_MESSAGE, index, (size() - 1)));
        }
        return new ArrayList<>(list.subList(start, end));
    }

    @Override
    public int size() {
        return (int) Math.ceil((double) list.size() / (double) chunkSize);
    }
}
