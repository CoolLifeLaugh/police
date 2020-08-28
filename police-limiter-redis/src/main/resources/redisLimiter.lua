--获取KEY
local key = KEYS[1]
local permits = tonumber(ARGV[1])

local val = redis.call('incrby', key, permits)
local ttl = redis.call('ttl', key)

--获取ARGV内的参数并打印
local interval = tonumber(ARGV[2])
local threshold = tonumber(ARGV[3])

-- 如果val = 1，补偿expire操作
if val == 1 then
    redis.call('expire', key, interval)
else
    -- 如果val != 1，说明不是第一次incrby，继续校验ttl，如果失效时间异常，重新补偿expire操作
    if ttl == -1 then
        redis.call('expire', key, interval)
    end
end

-- 校验incrby操作后的返回值，超过阈值，则返回0，表示失败
if val > threshold then
    --redis.log(redis.LOG_NOTICE, "fail, val " .. val .. ", ttl " .. ttl .. ", threshold " .. threshold);
    return 0
end

--redis.log(redis.LOG_NOTICE, "success, val " .. val .. ", ttl " .. ttl .. ", threshold " .. threshold);
return permits