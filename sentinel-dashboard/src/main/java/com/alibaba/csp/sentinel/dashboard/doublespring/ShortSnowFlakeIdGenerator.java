package com.alibaba.csp.sentinel.dashboard.doublespring;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

public class ShortSnowFlakeIdGenerator implements IdGenerator<Long> {


    private final Logger logger = LoggerFactory.getLogger(ShortSnowFlakeIdGenerator.class);

    private static ShortSnowFlakeIdGenerator shortSnowFlakeIdGenerator;
    /**
     * 初始偏移时间戳
     */
    private final long OFFSET = 1546300800L;

    /**
     * 机器id (0~15 保留 16~31作为备份机器)
     */
    private final long WORKER_ID;
    /**
     * 机器id所占位数 (5bit, 支持最大机器数 2^5 = 32)
     */
    private static final long WORKER_ID_BITS = 5L;
    /**
     * 自增序列所占位数 (16bit, 支持最大每秒生成 2^16 = ‭65536‬)
     */
    private final long SEQUENCE_ID_BITS = 16L;
    /**
     * 机器id偏移位数
     */
    private final long WORKER_SHIFT_BITS = SEQUENCE_ID_BITS;
    /**
     * 自增序列偏移位数
     */
    private final long OFFSET_SHIFT_BITS = SEQUENCE_ID_BITS + WORKER_ID_BITS;
    /**
     * 机器标识最大值 (2^5 / 2 - 1 = 15)
     */
    private static final long WORKER_ID_MAX = ((1 << WORKER_ID_BITS) - 1) >> 1;
    /**
     * 备份机器ID开始位置 (2^5 / 2 = 16)
     */
    private final long BACK_WORKER_ID_BEGIN = (1 << WORKER_ID_BITS) >> 1;
    /**
     * 自增序列最大值 (2^16 - 1 = ‭65535)
     */
    private final long SEQUENCE_MAX = (1 << SEQUENCE_ID_BITS) - 1;
    /**
     * 发生时间回拨时容忍的最大回拨时间 (秒)
     */
    private final long BACK_TIME_MAX = 1L;

    /**
     * 上次生成ID的时间戳 (秒)
     */
    private long lastTimestamp = 0L;
    /**
     * 当前秒内序列 (2^16)
     */
    private long sequence = 0L;
    /**
     * 备份机器上次生成ID的时间戳 (秒)
     */
    private long lastTimestampBak = 0L;
    /**
     * 备份机器当前秒内序列 (2^16)
     */
    private long sequenceBak = 0L;

    {

    }

    /**
     * 私有构造函数禁止外部访问
     */
    private ShortSnowFlakeIdGenerator(Integer workerId) {
        // 初始化机器ID
        // 伪代码: 由你的配置文件获取节点ID
        if (workerId < 0 || workerId > WORKER_ID_MAX) {
            throw new IllegalArgumentException(String.format("workerId范围: 0 ~ %d 目前: %d", WORKER_ID_MAX, workerId));
        }
        WORKER_ID = workerId;

    }

    public static ShortSnowFlakeIdGenerator instance() {

        Long no = System.currentTimeMillis() % WORKER_ID_MAX;
        int workerId = no.intValue();

        Objects.requireNonNull(workerId);

        if (shortSnowFlakeIdGenerator == null) {
            synchronized (ShortSnowFlakeIdGenerator.class) {
                if (shortSnowFlakeIdGenerator == null) {
                    shortSnowFlakeIdGenerator = new ShortSnowFlakeIdGenerator(workerId);
                }
            }
        }
        return shortSnowFlakeIdGenerator;
    }

    /**
     * 获取自增序列
     *
     * @return long
     */
    @Override
    public Long nextId() {
        return nextId(System.currentTimeMillis() / 1000);
    }

    /**
     * 主机器自增序列
     *
     * @param timestamp 当前Unix时间戳
     * @return long
     */
    private synchronized long nextId(long timestamp) {
        // 时钟回拨检查
        if (timestamp < lastTimestamp) {
            // 发生时钟回拨
            logger.warn("时钟回拨, 启用备份机器ID: now: [{}] last: [{}]", timestamp, lastTimestamp);
            return nextIdBackup(timestamp);
        }

        // 开始下一秒
        if (timestamp != lastTimestamp) {
            lastTimestamp = timestamp;
            sequence = 0L;
        }
        if (0L == (++sequence & SEQUENCE_MAX)) {
            // 秒内序列用尽
//            log.warn("秒内[{}]序列用尽, 启用备份机器ID序列", timestamp);
            sequence--;
            return nextIdBackup(timestamp);
        }

        return ((timestamp - OFFSET) << OFFSET_SHIFT_BITS) | (WORKER_ID << WORKER_SHIFT_BITS) | sequence;
    }

    /**
     * 备份机器自增序列
     *
     * @param timestamp timestamp 当前Unix时间戳
     * @return long
     */
    private long nextIdBackup(long timestamp) {
        if (timestamp < lastTimestampBak) {
            if (lastTimestampBak - System.currentTimeMillis() / 1000 <= BACK_TIME_MAX) {
                timestamp = lastTimestampBak;
            } else {
                throw new RuntimeException(String.format("时钟回拨: now: [%d] last: [%d]", timestamp, lastTimestampBak));
            }
        }

        if (timestamp != lastTimestampBak) {
            lastTimestampBak = timestamp;
            sequenceBak = 0L;
        }

        if (0L == (++sequenceBak & SEQUENCE_MAX)) {
            // 秒内序列用尽
            //logger.warn("秒内[{}]序列用尽, 备份机器ID借取下一秒序列", timestamp);
            return nextIdBackup(timestamp + 1);
        }

        return ((timestamp - OFFSET) << OFFSET_SHIFT_BITS) | ((WORKER_ID ^ BACK_WORKER_ID_BEGIN) << WORKER_SHIFT_BITS) | sequenceBak;
    }

    public static void main(String[] args) {
        ShortSnowFlakeIdGenerator instance = ShortSnowFlakeIdGenerator.instance();

        for (int i = 0; i < 100; i++) {
            System.out.println(instance.nextId());
        }
    }

}