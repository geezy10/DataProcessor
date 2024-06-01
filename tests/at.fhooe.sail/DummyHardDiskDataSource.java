package at.fhooe.sail;

import at.fhhgb.mc.opr.backblazedata.loaders.HardDiskDataSource;
import at.fhhgb.mc.opr.backblazedata.model.HardDisk;
import at.fhhgb.mc.opr.backblazedata.model.SMARTValue;

import java.util.Date;
import java.util.LinkedList;
import java.util.Vector;

public class DummyHardDiskDataSource implements HardDiskDataSource {

    private Vector<HardDisk> disks;
    private int index = 0;

    public DummyHardDiskDataSource() {
            this.disks = new Vector<>();

            LinkedList<SMARTValue> smart = new LinkedList<>();
            smart.add(new SMARTValue(1, 2L, 1L));
            smart.add(new SMARTValue(2, 3L, 1L));
            disks.add(new HardDisk(new Date(), "1", "2", 5678L, true, smart));

            smart = new LinkedList<>();
            smart.add(new SMARTValue(3, 3L, 1L));
            smart.add(new SMARTValue(4, 4L, 1L));
            smart.add(new SMARTValue(5, 5L, 1L));
            disks.add(new HardDisk(new Date(), "2", "3", 8678L, true, smart));

            smart = new LinkedList<>();
            smart.add(new SMARTValue(3, 4L, 1L));
            smart.add(new SMARTValue(4, 5L, 1L));
            smart.add(new SMARTValue(5, 6L, 1L));
            smart.add(new SMARTValue(6, 7L, 1L));
            disks.add(new HardDisk(new Date(), "3", "4", 9678L, true, smart));

            smart = new LinkedList<>();
            smart.add(new SMARTValue(3, 5L, 1L));
            disks.add(new HardDisk(new Date(), "4", "5", 1678L, false, smart));

            smart = new LinkedList<>();
            smart.add(new SMARTValue(3, 6L, 1L));
            smart.add(new SMARTValue(4, 7L, 1L));
            smart.add(new SMARTValue(5, 8L, 1L));
            disks.add(new HardDisk(new Date(), "5", "6", 2678L, false, smart));
        }

    @Override
    public HardDisk next() {
        if (index < disks.size()) {
            return disks.get(index++);
        } else {
            return null;
        }
    }
}