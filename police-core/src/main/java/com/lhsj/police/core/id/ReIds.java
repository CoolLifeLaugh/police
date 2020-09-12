package com.lhsj.police.core.id;

import com.lhsj.police.core.exception.CommonException;

import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Supplier;

import static com.lhsj.police.core.constant.Constants.CODE_ERROR;

public class ReIds {

    public static UUID UUID() {
        return UUID.randomUUID();
    }

    /*
     * 返回使用ThreadLocalRandom的UUID，比默认的UUID性能更优
     */
    public static UUID fastUUID() {
        ThreadLocalRandom random = ThreadLocalRandom.current();
        return new UUID(random.nextLong(), random.nextLong());
    }

    private static final SnowflakeIdGenerator       SNOWFLAKE_ID_GENERATOR = new SnowflakeIdGenerator();
    private static final Supplier<RuntimeException> EXCEPTION_SUPPLIER     = () -> new CommonException(CODE_ERROR, "创建ID失败");

    static {
        SnowflakeIdGenerator.setWorkerId(1L);
    }

    public static Long snowflakeId() {
        return Optional.of(SNOWFLAKE_ID_GENERATOR)
                .map(SnowflakeIdGenerator::id)
                .map(Number::longValue)
                .orElseThrow(EXCEPTION_SUPPLIER);

    }
}
