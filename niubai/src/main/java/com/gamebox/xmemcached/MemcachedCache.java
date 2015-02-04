/*
 * Copyright 2013-2014 gamebox. All rights reserved.
 * Support: http://www.gamebox.com
 * Team: WG DEV
 * Project:niubai
 * Package:com.gamebox.xmemcached
 * File:MemcachedCache.java
 * Date:2014年10月8日
 */
package com.gamebox.xmemcached;

import java.util.Set;

import net.rubyeye.xmemcached.MemcachedClient;

import org.springframework.cache.Cache;
import org.springframework.cache.support.SimpleValueWrapper;


/**
 * @author wuji
 *
 */
public class MemcachedCache implements Cache
{
    private final String name;
    private final MemcachedClient memcachedClient;
    private final MemCache memCache;
    
    public MemcachedCache(String name, int expire, MemcachedClient memcachedClient)
    {
        this.name = name;
        this.memcachedClient = memcachedClient; 
        this.memCache = new MemCache(name, expire, memcachedClient);
    }

    @Override
    public void clear()
    {
        memCache.clear();
    }

    @Override
    public void evict(Object key)
    {
        memCache.delete(key.toString());
    }

    @Override
    public ValueWrapper get(Object key)
    {
        Set<String> keySet = memCache.getKeySet();
        keySet.add(key.toString());
        ValueWrapper wrapper = null;
        Object value = memCache.get(key.toString());
        if (value != null)
        {
            wrapper = new SimpleValueWrapper(value);
        }
        return wrapper;
    }

    @Override
    public String getName()
    {
        return this.name;
    }

    @Override
    public MemcachedClient getNativeCache()
    {
        return this.memcachedClient;
    }

    @Override
    public void put(Object key, Object value)
    {
        memCache.put(key.toString(), value);
    }
}