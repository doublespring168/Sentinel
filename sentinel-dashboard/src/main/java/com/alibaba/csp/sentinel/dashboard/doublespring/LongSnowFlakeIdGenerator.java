package com.alibaba.csp.sentinel.dashboard.doublespring;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LongSnowFlakeIdGenerator implements IdGenerator<Long> {

    private final Logger logger = LoggerFactory.getLogger(LongSnowFlakeIdGenerator.class);

    private final long startEpoch = 1546300800000L;
    private final long workerIdBits = 5L;
    private final long datacenterIdBits = 5L;
    private final long maxWorkerId = ~(-1L << workerIdBits);
    private final long maxDatacenterId = ~(-1L << datacenterIdBits);
    private final long sequenceBits = 12L;

    private final long workerIdShift = sequenceBits;
    private final long datacenterIdShift = sequenceBits + workerIdBits;
    private final long timestampLeftShift = sequenceBits + workerIdBits + datacenterIdBits;
    private final long sequenceMask = ~(-1L << sequenceBits);
    private final long datacenterId;
    private final long workerId;
    private long lastTimestamp = -1L;
    private long sequence = 0L;

    public LongSnowFlakeIdGenerator(long datacenterId, long workerId) {
        if (datacenterId > maxDatacenterId || datacenterId < 0) {
            throw new IllegalArgumentException(String.format("datacenter Id can't be greater than %d or less than 0", maxDatacenterId));
        }
        if (workerId > maxWorkerId || workerId < 0) {
            throw new IllegalArgumentException(String.format("worker Id can't be greater than %d or less than 0", maxWorkerId));
        }
        this.datacenterId = datacenterId;
        this.workerId = workerId;
    }


    @Override
    public synchronized Long nextId() {
        long timestamp = now();

        if (timestamp < lastTimestamp) {
            logger.error("clock is moving backwards.  Rejecting requests until {}.", lastTimestamp);
            throw new IllegalStateException(String.format("Clock moved backwards.  Refusing to generate id for %d milliseconds", lastTimestamp - timestamp));
        }

        if (lastTimestamp == timestamp) {
            sequence = (sequence + 1) & sequenceMask;
            if (sequence == 0) {
                timestamp = tilNextMillis();
            }
        } else {
            sequence = 0;
        }

        lastTimestamp = timestamp;

        return ((timestamp - startEpoch) << timestampLeftShift) |
                (datacenterId << datacenterIdShift) |
                (workerId << workerIdShift) |
                sequence;
    }

    private long tilNextMillis() {
        long timestamp = now();
        while (timestamp <= lastTimestamp) {
            timestamp = now();
        }
        return timestamp;
    }

    private long now() {
        return System.currentTimeMillis() / 100000;
    }


    public static void main(String[] args) {
        Integer seed = 9014 % 31;
        LongSnowFlakeIdGenerator longSnowFlakeIdGenerator = new LongSnowFlakeIdGenerator(seed, seed);

        for (int i=0;i<100;i++){
            System.out.println(longSnowFlakeIdGenerator.nextId());
        }
    }

}
