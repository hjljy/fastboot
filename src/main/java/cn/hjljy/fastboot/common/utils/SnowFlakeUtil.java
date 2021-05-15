package cn.hjljy.fastboot.common.utils;

/**
 * @author 海加尔金鹰
 * @apiNote snowflake算法工具包
 * @since 2020/11/9 21:20
 */
public class SnowFlakeUtil {
    /**
     * 起始的时间戳
     */
    private final static long START_STMP = 1480166465631L;

    /**
     * 每一部分占用的位数
     * SEQUENCE_BIT  序列号占用的位数
     * MACHINE_BIT  机器标识占用的位数
     * DATACENTER_BIT 数据中心占用的位数
     */
    private final static long SEQUENCE_BIT = 12;
    private final static long MACHINE_BIT = 5;
    private final static long DATACENTER_BIT = 5;

    /**
     * 每一部分的最大值
     */
    private final static long MAX_DATACENTER_NUM = 31L;
    private final static long MAX_MACHINE_NUM = 31L;
    private final static long MAX_SEQUENCE = 4095L;

    /**
     * 每一部分向左的位移
     */
    private final static long MACHINE_LEFT = SEQUENCE_BIT;
    private final static long DATACENTER_LEFT = SEQUENCE_BIT + MACHINE_BIT;
    private final static long TIMESTMP_LEFT = DATACENTER_LEFT + DATACENTER_BIT;

    /**
     * 数据中心
     */
    private final long datacenterId;
    /**
     * 数据中心
     */
    private final long machineId;
    /**
     * 序列号
     */
    private long sequence = 0L;
    /**
     * 上一次时间戳
     */
    private long lastStmp = -1L;

    /**
     * @param datacenterId 数据中心
     * @param machineId    机器标识
     */
    public SnowFlakeUtil(long datacenterId, long machineId) {
        if (datacenterId > MAX_DATACENTER_NUM || datacenterId < 0) {
            throw new IllegalArgumentException("datacenterId can't be greater than MAX_DATACENTER_NUM or less than 0");
        }
        if (machineId > MAX_MACHINE_NUM || machineId < 0) {
            throw new IllegalArgumentException("machineId can't be greater than MAX_MACHINE_NUM or less than 0");
        }
        this.datacenterId = datacenterId;
        this.machineId = machineId;
    }

    /**
     * 产生下一个ID
     *
     * @return ID
     */
    public synchronized long nextId() {
        long currStmp = getNewstmp();
        if (currStmp < lastStmp) {
            throw new RuntimeException("Clock moved backwards.  Refusing to generate id");
        }

        if (currStmp == lastStmp) {
            //相同毫秒内，序列号自增
            sequence = (sequence + 1) & MAX_SEQUENCE;
            //同一毫秒的序列数已经达到最大
            if (sequence == 0L) {
                currStmp = getNextMill();
            }
        } else {
            //不同毫秒内，序列号置为0
            sequence = 0L;
        }

        lastStmp = currStmp;

        return (currStmp - START_STMP) << TIMESTMP_LEFT
                | datacenterId << DATACENTER_LEFT
                | machineId << MACHINE_LEFT
                | sequence;
    }

    private long getNextMill() {
        long mill = getNewstmp();
        while (mill <= lastStmp) {
            mill = getNewstmp();
        }
        return mill;
    }

    private long getNewstmp() {
        return System.currentTimeMillis();
    }

    public static Long createId() {
        SnowFlakeUtil snowFlake = new SnowFlakeUtil(1, 2);
        return snowFlake.nextId();
    }

    public static Long OrderNum() {
        SnowFlakeUtil snowFlake = new SnowFlakeUtil(5, 7);
        return snowFlake.nextId();
    }

    public static void main(String[] args) {
        //如果分布式部署项目，这里的参数建议设置独立的
        SnowFlakeUtil snowFlake = new SnowFlakeUtil(2, 3);
        int n = 12;
        for (int i = 0; i < (1 << n); i++) {
            System.out.println(snowFlake.nextId());
        }

    }
}
