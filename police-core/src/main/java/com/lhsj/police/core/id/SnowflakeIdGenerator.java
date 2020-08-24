package com.lhsj.police.core.id;

import com.lhsj.police.core.time.ReClocks;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Calendar;
import java.util.Date;

import static com.lhsj.police.core.base.Validate.isTrue;
import static com.lhsj.police.core.time.ReDateFormats.formatDateTimeMillis;

/**
 * copy from io.shardingsphere.core.keygen.DefaultKeyGenerator
 *
 * <p>
 * Default distributed primary key generator.
 *
 * <p>
 * Use snowflake algorithm. Length is 64 bit.
 * </p>
 *
 * <pre>
 * 1bit   sign bit.
 * 41bits timestamp offset from 2016.11.01(Sharding-Sphere distributed primary key published data) to now.
 * 10bits worker process id.
 * 12bits auto increment offset in one mills
 * </pre>
 *
 * <p>
 * Call @{@code DefaultKeyGenerator.setWorkerId} to set.
 * </p>
 *
 * @author gaohongtao
 */
public final class SnowflakeIdGenerator implements IdGenerator {

    private static Logger logger = LoggerFactory.getLogger(SnowflakeIdGenerator.class);

    public static final long EPOCH;

    private static final long SEQUENCE_BITS = 12L;

    private static final long WORKER_ID_BITS = 10L;

    private static final long SEQUENCE_MASK = (1 << SEQUENCE_BITS) - 1;

    private static final long WORKER_ID_LEFT_SHIFT_BITS = SEQUENCE_BITS;

    private static final long TIMESTAMP_LEFT_SHIFT_BITS = WORKER_ID_LEFT_SHIFT_BITS + WORKER_ID_BITS;

    private static final long WORKER_ID_MAX_VALUE = 1L << WORKER_ID_BITS;

    private static long workerId;

    static {
        Calendar calendar = Calendar.getInstance();
        calendar.set(2016, Calendar.NOVEMBER, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        EPOCH = calendar.getTimeInMillis();
    }

    private long sequence;

    private long lastTime;

    /**
     * Set work process id.
     *
     * @param workerId work process id
     */
    public static void setWorkerId(final long workerId) {
        isTrue(workerId >= 0L && workerId < WORKER_ID_MAX_VALUE);

        SnowflakeIdGenerator.workerId = workerId;
    }

    /**
     * Generate key.
     *
     * @return key type is @{@link Long}.
     */
    public synchronized Number id() {
        long currentMillis = ReClocks.currentTimeMillis();

        isTrue(lastTime <= currentMillis, "Clock is moving backwards, last time is {} milliseconds, current time is {} milliseconds", lastTime, currentMillis);

        if (lastTime == currentMillis) {
            if (0L == (sequence = (sequence + 1) & SEQUENCE_MASK)) {
                currentMillis = waitUntilNextTime(currentMillis);
            }
        } else {
            sequence = 0;
        }

        lastTime = currentMillis;

        if (logger.isDebugEnabled()) {
            logger.debug("id generate param, date {}, workerId {}, sequence {}", formatDateTimeMillis(new Date(lastTime)), workerId, sequence);
        }

        return ((currentMillis - EPOCH) << TIMESTAMP_LEFT_SHIFT_BITS) | (workerId << WORKER_ID_LEFT_SHIFT_BITS) | sequence;
    }

    private long waitUntilNextTime(final long lastTime) {
        long time = ReClocks.currentTimeMillis();

        while (time <= lastTime) {
            time = ReClocks.currentTimeMillis();
        }

        return time;
    }

}
