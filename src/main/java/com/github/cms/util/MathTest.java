package com.github.cms.util;

import java.util.Scanner;

/**
 * @author zhangmingyang
 * @date 2021/10/21 11:53
 */
public class MathTest {
    public static void main(String[] args) {
        System.out.println("请输入一个整数:");
        Scanner input = new Scanner(System.in);
        int num = input.nextInt();
        comput(num);
    }

    private static void comput(int num) {
        if (num % 10 == 0) {
            for (int i = 0; i <= num / 10; i++) {
                if (num % 5 == 0) {
                    for (int j = 0; j <= num / 5; j++) {
                        if (num % 1 == 0) {
                            for (int k = 0; k <= num / 1; k++) {
                                if (10 * i + 5 * j + k == num) {
                                    System.out.println(String.format("a:%d,b:%d,c:%d", i, j, k));
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
