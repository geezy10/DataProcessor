package at.fhooe.sail.task01;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Vector;

import at.fhooe.sail.DummyHardDiskDataSource;
import at.fhooe.sail.task02.DataProcessorStreams;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import at.fhhgb.mc.opr.backblazedata.model.HardDisk;


public class testsProcessorManual {

    private DataProcessorManual processor;
    private DummyHardDiskDataSource dataSource;
    private DataProcessorManual processor2;

    @BeforeEach
    public void setUp() {
        dataSource = new DummyHardDiskDataSource();
        Vector<HardDisk> hardDisks = new Vector<>();
        HardDisk disk;
        while ((disk = dataSource.next()) != null) {
            hardDisks.add(disk);
        }Vector<HardDisk> hardDisks2 = (Vector<HardDisk>) hardDisks.clone();
		hardDisks2.remove(0);

		processor = new DataProcessorManual(hardDisks);
		processor2 = new DataProcessorManual(hardDisks2);
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
    public void testMax() throws Exception {
        HardDisk maxBytes = processor
                .max((hdd1, hdd2) -> Long.compare(hdd1.getCapacityInBytes(), hdd2.getCapacityInBytes()));
        assertEquals(9678L, maxBytes.getCapacityInBytes());
    }

    @Test
    public void testMin() throws Exception {
        HardDisk minBytes = processor
                .min((hdd1, hdd2) -> Long.compare(hdd1.getCapacityInBytes(), hdd2.getCapacityInBytes()));
        assertEquals(1678L, minBytes.getCapacityInBytes());
    }

    @Test
    public void testMean() throws Exception {
        double meanBytes = processor.mean(hdd -> hdd.getCapacityInBytes());
        assertEquals(5678.0, meanBytes, 0.1);
    }

    @Test
    public void testMedian() throws Exception {
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


    @Test
    public void testNoHardDisksException() {
        // Create an empty vector
        Vector<HardDisk> hardDisks = new Vector<>();
        DataProcessorManual processor = new DataProcessorManual(hardDisks);
        assertThrows(Exception.class, () -> processor.max((hdd1, hdd2) -> Long.compare(hdd1.getCapacityInBytes(), hdd2.getCapacityInBytes())));
        assertThrows(Exception.class, () -> processor.min((hdd1, hdd2) -> Long.compare(hdd1.getCapacityInBytes(), hdd2.getCapacityInBytes())));
        assertThrows(Exception.class, () -> processor.mean(hdd -> hdd.getCapacityInBytes()));
        assertThrows(Exception.class, () -> processor.median((hdd1, hdd2) -> Long.compare(hdd1.getCapacityInBytes(), hdd2.getCapacityInBytes()), hdd -> hdd.getCapacityInBytes()));
    }

    @Test
    public void testMedianEven() throws Exception {
        long medianBytes = processor2.median(
                (hdd1, hdd2) -> Long.compare(hdd1.getCapacityInBytes(), hdd2.getCapacityInBytes()),
                hdd -> hdd.getCapacityInBytes());
        assertEquals(5678L, medianBytes);
    }
}



