
## hadoop官网
http://hadoop.apache.org/

## Hive
https://www.yiibai.com/hive/hive_create_table.html
```
hive
use cscs_sandbox;
show tables;
desc tmp_htable_compy_basicinfo_yx;
show create table tmp_htable_compy_basicinfo_yx;
show partitions employee;
```

- create hive table
```
CREATE [TEMPORARY] [EXTERNAL] TABLE [IF NOT EXISTS] [db_name.] table_name

[(col_name data_type [COMMENT col_comment], ...)]
[COMMENT table_comment]
[ROW FORMAT row_format]
[STORED AS file_format]
```
```
CREATE TABLE IF NOT EXISTS cscs_sandbox.employee ( eid int, name String,
 salary String, destination String)
 COMMENT 'Employee details'
 ROW FORMAT DELIMITED
 FIELDS TERMINATED BY '\t'
 LINES TERMINATED BY '\n'
 STORED AS TEXTFILE;
```

- load data into hive table
```
LOAD DATA [LOCAL] INPATH 'filepath' [OVERWRITE] INTO TABLE tablename 
[PARTITION (partcol1=val1, partcol2=val2 ...)]
```
```
 LOAD DATA LOCAL INPATH '/home/lipeng/sample.txt'
 OVERWRITE INTO TABLE cscs_sandbox.employee;
```

- 修改表
```
ALTER TABLE name RENAME TO new_name
ALTER TABLE name ADD COLUMNS (col_spec[, col_spec ...])
ALTER TABLE name DROP [COLUMN] column_name
ALTER TABLE name CHANGE column_name new_name new_type
ALTER TABLE name REPLACE COLUMNS (col_spec[, col_spec ...])

ALTER TABLE employee RENAME TO emp;
ALTER TABLE employee CHANGE name ename String;
ALTER TABLE employee ADD COLUMNS (dept STRING COMMENT 'Department name');
ALTER TABLE employee REPLACE COLUMNS (eid INT empid Int, ename STRING name String);
```

- 分区
```
ALTER TABLE table_name ADD
  partition_spec [ LOCATION 'location1' ]
  partition_spec [ LOCATION 'location2' ] ...

partition_spec:
  : PARTITION (partition_col = partition_col_value,
        partition_col = partiton_col_value, ...)
```
```
 ALTER TABLE page_view ADD
    PARTITION (dt='2008-08-08', country='us')
      location '/path/to/us/part080808'
    PARTITION (dt='2008-08-09', country='us')
      location '/path/to/us/part080809';
```
```
ALTER TABLE table_name DROP
    partition_spec, partition_spec,...
```

- 删除表
```
DROP TABLE [IF EXISTS] table_name;
DROP TABLE IF EXISTS employee;
```

## Hbase
```
hbase shell
scan 'portal:company_base'
```
