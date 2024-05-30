package at.fhooe.sail.task01;

import at.fhhgb.mc.opr.backblazedata.loaders.HardDiskDataSource;
import at.fhhgb.mc.opr.backblazedata.loaders.LiveHardDiskDataSource;
import at.fhhgb.mc.opr.backblazedata.model.HardDisk;

import java.util.Comparator;
import java.util.Vector;

public class ManualMain {
    public static void main(String[] args) throws Exception {
        HardDiskDataSource ds = new LiveHardDiskDataSource();
        Vector<HardDisk> hardDisks = new Vector<>();
        HardDisk disk;

        while ((disk = ds.next()) != null) {
            hardDisks.add(disk);
        }
        DataProcessorManual processor = new DataProcessorManual(hardDisks);

        long hddCount = processor.count();
        System.out.println("Number of hard disks: " + hddCount);

        long failingCount = processor.filter(hdd -> hdd.isFailing()).size();
        System.out.println("Number of failing hard disks: " + failingCount);

        Comparator<HardDisk> comparator = (hdd1, hdd2) -> {
            if (hdd1.getCapacityInBytes() > hdd2.getCapacityInBytes()) {
                return 1;
            } else if (hdd1.getCapacityInBytes() < hdd2.getCapacityInBytes()) {
                return -1;
            } else {
                return 0;
            }
        };

        long largestDiskBytes = processor.max(comparator).getCapacityInBytes();
        System.out.println("Largest disk capacity: " + largestDiskBytes + " bytes");

        long smallestDiskBytes = processor.min(comparator).getCapacityInBytes();
        System.out.println("Smallest disk capacity: " + smallestDiskBytes + " bytes");

        double meanDiskBytes = processor.mean(HardDisk::getCapacityInBytes);
        System.out.println("Mean disk capacity: " + meanDiskBytes + " bytes");

        long medianDiskBytes = processor.median(comparator, HardDisk::getCapacityInBytes);
        System.out.println("Median disk capacity: " + medianDiskBytes + " bytes");

        //countDistinctStrings
        long distinctStrings = processor.countDistinctStrings(HardDisk::getModel);
        System.out.println("Number of distinct models: " + distinctStrings);

        long distinctSerials = processor.median(comparator, hdd -> (long) hdd.getSmartValues().size());
        System.out.println("Median Number of SmartValues: " + distinctSerials);

        //
    }
}
