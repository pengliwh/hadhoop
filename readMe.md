
## hadoop官网
http://hadoop.apache.org/

## Hive
```
hive
use cscs_sandbox;
show tables;
 desc tmp_htable_compy_basicinfo_yx;
 show create table tmp_htable_compy_basicinfo_yx;
```

- create hive table
```
CREATE TABLE IF NOT EXISTS cscs_sandbox.employee ( eid int, name String,
 salary String, destination String)
 COMMENT 'Employee details'
 ROW FORMAT DELIMITED
 FIELDS TERMINATED BY '\t'
 LINES TERMINATED BY '\n'
 STORED AS TEXTFILE;
```
