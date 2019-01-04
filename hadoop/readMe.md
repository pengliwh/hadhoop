## Configuration and start hdfs
### Configuration
- etc/hadoop/core-site.xml
```
<configuration>
    <property>
        <name>fs.defaultFS</name>
        <value>hdfs://localhost:9000</value>
    </property>
</configuration>
```

- etc/hadoop/hdfs-site.xml
```
<configuration>
    <property>
        <name>dfs.replication</name>
        <value>1</value>
    </property>
</configuration>
```

- Setup passphraseless ssh
```
  $ ssh-keygen -t rsa -P '' -f ~/.ssh/id_rsa
  $ cat ~/.ssh/id_rsa.pub >> ~/.ssh/authorized_keys
  $ chmod 0600 ~/.ssh/authorized_keys
```

### Execution
- Format the filesystem
```
bin/hdfs namenode -format
```

- Start NameNode daemon and DataNode daemon
```
sbin/start-dfs.sh
```

- 问题
每次重启启动hadoop时，DataNode就会失效，那是因为默认的tmp文件每次重新开机会被清空，与此同时namenode的格式化信息就会丢失
需要修改core-site.xml， 加入一下信息
```
    <property>
        <name>hadoop.tmp.dir</name>
        <value>/home/chjzh/hadoop_tmp</value>
        <description>A base for other temporary directories.</description>
    </property>
```
新建需要用到的目录，并赋予修改的权限

- NameNode web UI
```
http://192.168.116.129:50070
```

- stop hdfs
```
sbin/stop-dfs.sh
```

## hadoop shell
- doc
```
https://www.cnblogs.com/lzfhope/p/6952869.html
http://hadoop.apache.org/docs/r1.0.4/cn/hdfs_shell.html
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
- etc/hadoop/mapred-site.xml
```
<configuration>
    <property>
        <name>mapreduce.framework.name</name>
        <value>yarn</value>
    </property>
</configuration>
```

- etc/hadoop/yarn-site.xml
```
<configuration>
    <property>
        <name>yarn.nodemanager.aux-services</name>
        <value>mapreduce_shuffle</value>
    </property>
</configuration>
```
- Start ResourceManager daemon and NodeManager daemon
```
sbin/start-yarn.sh
```

- ResourceManager Web UI
```
http://localhost:8088/
```

- stop the daemons
```
sbin/stop-yarn.sh
```
