package com.atiguigu.case1;

import org.apache.zookeeper.*;

import java.io.IOException;

/**
 * @author dyw
 * @date 2022-03-10  17:25
 */
public class DistributeServer {

    private String connectString = "192.168.233.130:2181,192.168.233.133:2181,192.168.233.134:2181";
    private int sessionTimeout = 200000;
    private ZooKeeper zooKeeper;

    public static void main(String[] args) throws IOException, InterruptedException, KeeperException {
        DistributeServer distributeServer = new DistributeServer();
        //获取zk连接
        distributeServer.getConnect();

        //2、注册服务器到zk集群
        distributeServer.regist(args[0]);
        //3、启动业务逻辑
        distributeServer.business();

    }

    private void business() throws InterruptedException {
        Thread.sleep(Integer.MAX_VALUE);
    }

    private void regist(String hostname) throws InterruptedException, KeeperException {
        String create = zooKeeper.create("/servers/"+hostname, hostname.getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
        System.out.println(hostname + "is online");
    }

    private void getConnect() throws IOException {
        zooKeeper = new ZooKeeper(connectString, sessionTimeout, new Watcher() {
            @Override
            public void process(WatchedEvent watchedEvent) {

            }
        });
    }
}
