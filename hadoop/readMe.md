## hadoop shell

- hadoop shell
```
https://www.cnblogs.com/lzfhope/p/6952869.html
http://hadoop.apache.org/docs/r1.0.4/cn/hdfs_shell.html
```

- start namenode daemon and datanode daemon
```
sbin/start-dfs.sh
```

- namenode web UI
```
http://192.168.116.129:50070
```

- stop
```
 sbin/stop-dfs.sh
```

- hadoop shell
```
创建目录
bin/hdfs dfs -mkdir /user
bin/hadoop fs -mkdir /user
删除目录或者文件
./bin/hadoop fs -rm -r -skipTrash /user/root/input
获取hadoop文件到本地
bin/hdfs dfs -get output output
上传本地文件到hadoop
bin/hdfs dfs -put etc/hadoop input
input没有以/开头，所以文件将会上传到/user/<username>目录下， username为当前执行shell脚本的用户名

列出目录文件
bin/hadoop fs -ls /user
```

## yarn
ResourceManager Web UI
```
http://localhost:8088/
```