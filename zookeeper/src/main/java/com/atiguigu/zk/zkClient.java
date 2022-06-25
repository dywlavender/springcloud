package com.atiguigu.zk;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

/**
 * @author dyw
 * @date 2022-03-10  15:04
 */
public class zkClient {
    private String connectString = "192.168.233.130:2181,192.168.233.133:2181,192.168.233.134:2181";
    private int sessionTimeout = 200000;
    private ZooKeeper zkClient;

    @BeforeEach
    public void init() throws IOException {
        zkClient = new ZooKeeper(connectString, sessionTimeout, new Watcher() {
            @Override
            public void process(WatchedEvent watchedEvent) {
                System.out.println("-------watch----");
                System.out.println(Thread.currentThread().getName());
                List<String> children = null;
                try {
                    children = zkClient.getChildren("/", true);
                    for (String child : children) {
                        System.out.println("watch: "+ child);
                    }
                } catch (KeeperException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("-------------");
            }
        });
    }

    @Test
    public void create() throws InterruptedException, KeeperException {
        String nodeCreated = zkClient.create("/atguigu", "ss.avi".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);

    }

    @Test
    public void getChildren() throws InterruptedException, KeeperException {
        List<String> children = zkClient.getChildren("/", true);

        for (String child : children) {
            System.out.println("get: "+child);
        }
        Thread.sleep(Integer.MAX_VALUE);
    }

    @Test
    public void exist() throws InterruptedException, KeeperException {
        System.out.println("exist:"+Thread.currentThread().getName());
        Stat st = zkClient.exists("/atguigu", false);
        System.out.println(st==null?"not exist":"exist");
    }

}
