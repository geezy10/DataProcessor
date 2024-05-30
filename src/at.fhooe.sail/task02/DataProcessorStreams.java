package at.fhooe.sail.task02;

import at.fhhgb.mc.opr.backblazedata.model.HardDisk;

import java.util.Comparator;
import java.util.Vector;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class DataProcessorStreams {

    private Vector<HardDisk> hardDisks;

    public DataProcessorStreams(Vector<HardDisk> hardDisks) {
        this.hardDisks = hardDisks;
    }

    // Can use Vector.sort internally, sorts the internal Vector
    public void sort(Comparator<HardDisk> comparator) {
        hardDisks.sort(comparator);
    }

    // Returns size of the internal vector
    public long count() {
        return hardDisks.size();
    }

    // Returns a Vector of HardDisks filtered by predicate
    public Vector<HardDisk> filter(Predicate<HardDisk> predicate) {
        return hardDisks.stream().filter(predicate).collect(Collectors.toCollection(Vector::new));
    }

    // Returns the HardDisk with a specified maximum value
    public HardDisk max(Comparator<HardDisk> comparator) {
        return hardDisks.stream().max(comparator).get();
    }

    // Returns the HardDisk with a specified minimum value
    public HardDisk min(Comparator<HardDisk> comparator) {
        return hardDisks.stream().min(comparator).get();

    }

    // Returns a mean value specified by the function
    public double mean(Function<HardDisk, Long> function) {
        return hardDisks.stream().map(function).mapToLong(Long::longValue).average().getAsDouble();
    }

    // Returns a median value specified by the function
    public long median(Comparator<HardDisk> sortingComparator, Function<HardDisk, Long> function) {
        sort(sortingComparator);
        return function.apply(hardDisks.get(hardDisks.size() / 2));

    }

    // Counts distinct values based on the given HardDisk-to-String mapping function
    public long countDistinctStrings(Function<HardDisk, String> function) {
        return hardDisks.stream().map(function).distinct().count();
    }
}
