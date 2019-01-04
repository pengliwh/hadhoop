
## hive官方手册
http://hbase.apache.org/book.html#quickstart

## Running on a single-node, standalone instance of HBase
## Pseudo-Distributed Local Install
- Download
```
https://www.apache.org/dyn/closer.lua/hbase/
```

- Extract the downloaded file, and change to the newly-created directory
```
$ tar xzvf hbase-3.0.0-SNAPSHOT-bin.tar.gz
$ cd hbase-3.0.0-SNAPSHOT/
```

- set JAVA_HOME
```
vi /etc/profile
export JAVA_HOME=/usr/java/jdk1.8.0_191
source /etc/profile
设置好JAVA_HOME以后，如果启动还是报找不到JAVA_HOME异常
需要另外再手动设置conf/hbase-env.sh的JAVA_HOME
```

- Edit conf/hbase-site.xml
```
<configuration>
  <property>
    <name>hbase.rootdir</name>
    <value>file:///home/testuser/hbase</value>
  </property>
  <property>
    <name>hbase.zookeeper.property.dataDir</name>
    <value>/home/testuser/zookeeper</value>
  </property>
  <property>
    <name>hbase.unsafe.stream.capability.enforce</name>
    <value>false</value>
  </property>
</configuration>
```
OR
```
<configuration>
  <property>
    <name>hbase.rootdir</name>
    <value>hdfs://localhost:9000/hbase</value>
  </property>
  <property>
    <name>hbase.zookeeper.property.dataDir</name>
    <value>/home/testuser/zookeeper</value>
  </property>
  <property>
    <name>hbase.unsafe.stream.capability.enforce</name>
    <value>true</value>
  </property>
  <property>
    <name>hbase.cluster.distributed</name>
    <value>true</value>
  </property>
</configuration>
```

- start hbase
```
bin/start-hbase.sh 
```

- HBase Web UI
```
http://localhost:16010
```

- Stop HBase
```
./bin/stop-hbase.sh
```

### Start and stop a backup HBase Master (HMaster) server

```
./bin/local-master-backup.sh start 2 3 5
cat /tmp/hbase-testuser-1-master.pid |xargs kill -9
 .bin/local-regionservers.sh start 2 3 4 5
  .bin/local-regionservers.sh stop 3
```

## Advanced - Fully Distributed
```
http://hbase.apache.org/book.html#quickstart
2.4. Advanced - Fully Distributed
```

## Quickstart with Hbase
### Hbase shell
```
./bin/hbase shell
create 'test', 'cf'
list 'test'
describe 'test' | desc 'test'
show create table 'test'
put 'test', 'row1', 'cf:a', 'value1'
put 'test', 'row2', 'cf:b', 'value2'
put 'test', 'row3', 'cf:c', 'value3'
scan 'test'
get 'test', 'row1'
disable 'test'
enable 'test'
drop 'test'
quit
```
