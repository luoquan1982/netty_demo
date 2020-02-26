package com.luoquan.nio;

import java.nio.IntBuffer;

/**
 * BassicBuffer
 *
 * @author LuoQuan
 * @date 2020/2/26 17:53
 */
public class BasicBuffer {
    public static void main(String[] args) {
        //举例说明buffer的使用(简单说明)
        //创建一个buffer,大小为5,即可以存放5个int的buffer
        IntBuffer intBuffer = IntBuffer.allocate(5);

        //向buffer中存储数据
        for (int i = 0; i < intBuffer.capacity(); i++) {
            intBuffer.put(i * 2);
        }

        //如何从buffer读取数据
        //将buffer转换,读写切换(重要,读取前一定要先切换)
        intBuffer.flip();

        while (intBuffer.hasRemaining()){
            System.out.print(intBuffer.get() + " ");
        }
    }
}
