package test1908;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.exceptions.JedisConnectionException;
import redis.clients.jedis.exceptions.JedisDataException;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class DataValidation extends Thread {
//    private static final String host = "localhost";
//    private static final Integer port = 6379;
//    private static final String password = "test";

    public static void main(String[] args) {
        DataValidation thread = new DataValidation("Thread" + 1);
        thread.redisClusterTesr();
    }

    private void redisClusterTesr() {
        String host = "localhost";
        Integer port = 6379;
        String password = "test";
        String name = "Thread1";
        
        // 和redis服务器建立连接
        Jedis jedis = new Jedis(host, port); 
        try {
            jedis.auth(password);
        } catch (JedisConnectionException e) {
            System.err.println(name + "无法连接至目的主机！" + host + ":" + port);
            return;
        } catch (JedisDataException e) {
            System.err.println(name + "密码错误，登陆失败!" + e.getMessage());
            return;
        }
        System.out.println();
        
        // 获取存储的数据并输出
        long size = jedis.llen(name);
        List<String> list = new ArrayList<>();

        // 循环进行rpop，抛出每个
        for (int i = 0; i < size; i++) {
            try {
                String str = jedis.rpop(name);
                list.add(str);
                System.out.println(name+" 获取成功，ID："+i);
            } catch (JedisConnectionException e) {
                System.err.println(name + "连接已断开，正在准备重新连接");
                boolean result = false;
                for (int j = 0; j < 10; j++) {
                    jedis.close();
                    jedis = new Jedis(host, port);
                    try {
                        jedis.auth(password);
                        System.err.println(name + "第 /10次重新连接：连接成功");
                        i--;
                    } catch (JedisConnectionException e1) {
                        System.err.println(name + "第 /10次重新连接：连接失败");
                    }
                }
            }
        }
        System.out.println("finish,size: "+list.size());
        System.out.println(Arrays.toString(list.toArray()));
    }

    private DataValidation(String name) {
        super(name);
    }
}
