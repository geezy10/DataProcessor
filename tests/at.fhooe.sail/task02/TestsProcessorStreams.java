package at.fhooe.sail.task02;


import at.fhhgb.mc.opr.backblazedata.model.HardDisk;
import at.fhooe.sail.DummyHardDiskDataSource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Vector;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestsProcessorStreams {

        private DataProcessorStream processor;
        private DataProcessorStream processor2;
        private DummyHardDiskDataSource dataSource;

        @BeforeEach
        public void setUp() {
            dataSource = new DummyHardDiskDataSource();
            Vector<HardDisk> hardDisks = new Vector<>();
            HardDisk disk;
            while ((disk = dataSource.next()) != null) {
                hardDisks.add(disk);
            }

            Vector<HardDisk> hardDisks2 = (Vector<HardDisk>) hardDisks.clone();
            hardDisks2.remove(0);

            processor = new DataProcessorStream(hardDisks);
            processor2 = new DataProcessorStream(hardDisks2);
        }

        @Test
        public void testSort() {

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
            assertEquals(8678L, medianBytes);

            long medianBytes2 = processor2.median(
                    (hdd3, hdd4) -> Long.compare(hdd3.getCapacityInBytes(), hdd4.getCapacityInBytes()),
                    hdd5 -> hdd5.getCapacityInBytes());
            assertEquals(9178L, medianBytes2);
        }

        @Test
        public void testCountDistinctStrings() {
            long serialCount = processor.countDistinctStrings(hdd -> hdd.getModel());
            assertEquals(5, serialCount);
        }

    }
