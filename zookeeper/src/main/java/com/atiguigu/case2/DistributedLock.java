package com.atiguigu.case2;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * @author dyw
 * @date 2022-03-10  21:58
 */
public class DistributedLock {
    private String connectString = "192.168.233.130:2181,192.168.233.133:2181,192.168.233.134:2181";
    private int sessionTimeout = 200000;
    private ZooKeeper zooKeeper;
    private CountDownLatch countDownLatch = new CountDownLatch(1);
    private CountDownLatch waitLath = new CountDownLatch(1);
    private String waitPath;
    private String currentNodes;

    public DistributedLock() throws IOException, InterruptedException, KeeperException {
        zooKeeper = new ZooKeeper(connectString, sessionTimeout, new Watcher() {
            @Override
            public void process(WatchedEvent watchedEvent) {
                if (watchedEvent.getState() == Event.KeeperState.SyncConnected){
                    countDownLatch.countDown();
                }
                if (watchedEvent.getType() == Event.EventType.NodeDeleted && watchedEvent.getPath().equals(waitPath)){
                    waitLath.countDown();
                }
            }
        });
        countDownLatch.await();

        Stat s = zooKeeper.exists("/locks", false);
        if (s==null){
            zooKeeper.create("/locks","locks".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE,CreateMode.PERSISTENT);
        }

    }

    public void zkLock(){
        //创建对应的零时带序号节点
        try {
            currentNodes = zooKeeper.create("/locks/" + "seq-", null, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
            List<String> children = zooKeeper.getChildren("/locks", false);
            if (children.size() == 1){
                return;
            }else{
                Collections.sort(children);
                String substring = currentNodes.substring("/locks/".length());
                int index = children.indexOf(substring);
                if(index==-1){
                    System.out.println("数据异常");
                }else if (index == 0){
                    return;
                }else{
                    waitPath = "/locks/" + children.get(index -1 );
                    zooKeeper.getData(waitPath,true,null);
                    waitLath.await();
                    return;
                }
            }
        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //判断创建的节点是否是最小的序号节点，如果是获取锁，如果不是，监听前一个节点
    }
    public void unzkLock(){
        //删除节点
        try {
            zooKeeper.delete(currentNodes,-1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (KeeperException e) {
            e.printStackTrace();
        }
    }
}
