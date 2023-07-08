local key = KEYS[1]
local new = tonumber(ARGV[1])
local current = tonumber(redis.call('GET', key))
if (current == false) or (new < current) then
    redis.call('SET', key, new)
end
return true

