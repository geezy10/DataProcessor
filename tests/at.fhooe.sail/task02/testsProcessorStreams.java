package at.fhooe.sail.task02;


import at.fhhgb.mc.opr.backblazedata.model.HardDisk;
import at.fhooe.sail.DummyHardDiskDataSource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Vector;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class testsProcessorStreams {

    private DataProcessorStreams processor;
    private DummyHardDiskDataSource dataSource;

    @BeforeEach
    public void setUp() {
        dataSource = new DummyHardDiskDataSource();
        Vector<HardDisk> hardDisks = new Vector<>();
        HardDisk disk;
        while ((disk = dataSource.next()) != null) {
            hardDisks.add(disk);
        }
        processor = new DataProcessorStreams(hardDisks);
    }

    @Test
    public void testSort() {
        processor.sort((hdd1, hdd2) -> Long.compare(hdd1.getCapacityInBytes(), hdd2.getCapacityInBytes()));
        Vector<HardDisk> sortedHardDisks = processor.filter(hdd -> true);
        assertEquals(1678L, sortedHardDisks.firstElement().getCapacityInBytes());
        assertEquals(9678L, sortedHardDisks.lastElement().getCapacityInBytes());
    }


    @Test
    public void testCount() {
        assertEquals(5, processor.count());
    }

    @Test
    public void testFilter() {
        assertEquals(3, processor.filter(hdd -> hdd.isFailing()).size());
    }

    @Test
    public void testMax() {
        HardDisk maxBytes = processor
                .max((hdd1, hdd2) -> Long.compare(hdd1.getCapacityInBytes(), hdd2.getCapacityInBytes()));
        assertEquals(9678L, maxBytes.getCapacityInBytes());
    }

    @Test
    public void testMin() {
        HardDisk minBytes = processor
                .min((hdd1, hdd2) -> Long.compare(hdd1.getCapacityInBytes(), hdd2.getCapacityInBytes()));
        assertEquals(1678L, minBytes.getCapacityInBytes());
    }

    @Test
    public void testMean() {
        double meanBytes = processor.mean(hdd -> hdd.getCapacityInBytes());
        assertEquals(5678.0, meanBytes, 0.1);
    }

    @Test
    public void testMedian() {
        long medianBytes = processor.median(
                (hdd1, hdd2) -> Long.compare(hdd1.getCapacityInBytes(), hdd2.getCapacityInBytes()),
                hdd -> hdd.getCapacityInBytes());
        assertEquals(5678L, medianBytes);
    }

    @Test
    public void testCountDistinctStrings() {
        long serialCount = processor.countDistinctStrings(hdd -> hdd.getModel());
        assertEquals(5, serialCount);
    }

}
