package com.createthread;

import java.util.Arrays;
import java.util.List;

/**
 * Java8+ 并行计算
 */
public class Method8 {

    public static void main(String[] args) {
        List<Integer> list = Arrays.asList(1, 2, 3, 4, 5);
        // 串行，打印结果为12345
        list.stream().forEach(System.out::print);
        System.out.println();
        // 并行，打印结果随机，比如35214
        list.parallelStream().forEach(System.out::print);
    }
}
