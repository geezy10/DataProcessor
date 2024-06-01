package at.fhooe.sail.task01;

import at.fhhgb.mc.opr.backblazedata.model.HardDisk;

import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;
import java.util.Vector;
import java.util.function.Function;
import java.util.function.Predicate;

public class DataProcessorManual {

    private Vector<HardDisk> hardDisks;

    public DataProcessorManual(Vector<HardDisk> hardDisks) {
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
    public Vector<HardDisk> filter(Predicate<HardDisk> predicate)  {
        Vector<HardDisk> vector = new Vector<>();
        for (HardDisk hd : hardDisks) {
            if (predicate.test(hd))
                vector.add(hd);

        }
        return vector;
    }

    // Returns the HardDisk with a specified maximum value
    public HardDisk max(Comparator<HardDisk> comparator) throws Exception {
        if (hardDisks.isEmpty())
            throw new Exception("No hardDisks found");
        HardDisk res = hardDisks.get(0);
        for (HardDisk hd : hardDisks) {
            if (comparator.compare(hd, res) > 0)
                res = hd;
        }
        return res;
    }

    // Returns the HardDisk with a specified minimum value
    public HardDisk min(Comparator<HardDisk> comparator) throws Exception {
        if (hardDisks.isEmpty())
            throw new Exception("No hardDisks found");
        HardDisk result = hardDisks.get(0);
        for (HardDisk hd : hardDisks) {
            if (comparator.compare(hd, result) < 0)
                result = hd;
        }
        return result;
    }

    // Returns a mean value specified by the function
    public double mean(Function<HardDisk, Long> function) throws Exception {
        if (hardDisks.isEmpty())
            throw new Exception("No hardDisks found");
        long sum = 0;
        for (HardDisk hd : hardDisks) {
            sum = sum + function.apply(hd);
        }
        return sum / hardDisks.size();
    }

    // Returns a median value specified by the function
    public long median(Comparator<HardDisk> sortingComparator, Function<HardDisk, Long> function) throws Exception {
        if (hardDisks.isEmpty())
            throw new Exception("No hardDisks found");
        long sum = 0;
        hardDisks.sort(sortingComparator);
        if (hardDisks.size() % 2 == 0) {
            sum = function.apply(hardDisks.get(hardDisks.size() / 2-1)) + function.apply(hardDisks.get(hardDisks.size() / 2));
            return sum / 2;
        } else {
            return function.apply(hardDisks.get(hardDisks.size() / 2));
        }


    }

    // Counts distinct values based on the given HardDisk-to-String mapping function
    public long countDistinctStrings(Function<HardDisk, String> function) {
        Set<String> string = new HashSet<>();
        for (HardDisk hd : hardDisks) {
            string.add(function.apply(hd));
        }
        return string.size();
    }
}
