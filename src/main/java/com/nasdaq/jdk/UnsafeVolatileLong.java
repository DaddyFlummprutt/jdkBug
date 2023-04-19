package com.nasdaq.jdk;

import java.nio.ByteBuffer;
import java.util.concurrent.ThreadLocalRandom;

import sun.misc.Unsafe;


public class UnsafeVolatileLong
{
    private static final int OFFSET =28;

    public static void main(String[] args) throws ReflectiveOperationException
    {
        var f = Unsafe.class.getDeclaredField("theUnsafe");
        f.setAccessible(true);
        var u = (Unsafe)f.get(null);
        byte[] bs = new byte[OFFSET*2];
        ThreadLocalRandom.current().nextBytes(bs);
        ByteBuffer bbd = ByteBuffer.allocateDirect(bs.length);
        bbd.put(bs);
        var val = u.getLongVolatile(bbd, OFFSET);
        System.out.println(val);
    }
}
