package com.atiguigu.case3;

import com.atiguigu.case2.Test;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.curator.retry.ExponentialBackoffRetry;

/**
 * @author dyw
 * @date 2022-03-10  22:52
 */
public class CuratorLockTest {

    public static void main(String[] args) {
        InterProcessMutex lock1 = new InterProcessMutex(getCuratorFramework(), "/locks");
        InterProcessMutex lock2 = new InterProcessMutex(getCuratorFramework(), "/locks");

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    lock1.acquire();
                    System.out.println("线程1 获取锁");
                    Thread.sleep(5000);
                    lock1.release();
                    System.out.println("线程1 释放锁");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    lock2.acquire();
                    System.out.println("线程2 获取锁");
                    Thread.sleep(5000);
                    lock2.release();
                    System.out.println("线程2 释放锁");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private static CuratorFramework getCuratorFramework() {
        ExponentialBackoffRetry policy = new ExponentialBackoffRetry(3000, 3);
        CuratorFramework client = CuratorFrameworkFactory.builder().connectString("192.168.233.130:2181,192.168.233.133:2181,192.168.233.134:2181")
                .connectionTimeoutMs(20000)
                .sessionTimeoutMs(20000)
                .retryPolicy(policy).build();
        client.start();
        System.out.println("zookeeper 启动");
        return  client;

    }

}
