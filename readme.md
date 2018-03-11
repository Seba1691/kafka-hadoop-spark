Kafka+Hadoop+Spark
===================


This document describes the steps to configure and make work Kafka, Hadoop and Spark

----------

Kafka
-------------

You need to have Apache Kafka 0.8.2.1 installed. Here is a link with the steps needed:
https://www.digitalocean.com/community/tutorials/how-to-install-apache-kafka-on-ubuntu-14-04

Once you have Kafka working and started you can run the Kafka Producer by going to the folder "IOT-Platform/platform/services/kafka-hadoop-spark/GpsKafkaProducer" and executing "mvn jetty:run"

> **Note:**

> - You can verify if the producer is running executing a kafka consumer with the following command:
>  ~/kafka/bin/kafka-console-consumer.sh --zookeeper localhost:2181 --topic gps-event --from-beginning
>  If everything is working well you should be able to see something logged when you send a POST to the endpoint localhost:8080/gps with a valid JSON

Hadoop
-------------

To run the consumer you need to have Apache Hadoop installed. 
For that you can follow those steps:

**Configure Master:**

1- Create a user hduser
>- $ sudo addgroup hadoop
>- $ sudo adduser --ingroup hadoop hduser
>- $ su hduser

2- Copy the folder Hadoop_Master into your file system (hduser must have all peritonitis over this folder)

3- Copy the following into ~/.bashrc 
```
# Set JAVA_HOME (we will also configure JAVA_HOME directly for Hadoop later on)
export JAVA_HOME=/usr/java/jdk1.7.0_79

# Set Hadoop-related environment variables
export HADOOP_HOME=/home/hduser/hadoop

# Some convenient aliases and functions for running Hadoop-related commands
unalias fs &> /dev/null
alias fs="hadoop fs"
unalias hls &> /dev/null
alias hls="fs -ls"

# If you have LZO compression enabled in your Hadoop cluster and
# compress job outputs with LZOP (not covered in this tutorial):
# Conveniently inspect an LZOP compressed file from the command
# line; run via:
#
# $ lzohead /hdfs/path/to/lzop/compressed/file.lzo
#
# Requires installed 'lzop' command.
#
lzohead () {
    hadoop fs -cat $1 | lzop -dc | head -1000 | less
}

# Add Hadoop bin/ directory to PATH
export PATH=$PATH:$HADOOP_HOME/bin

export PATH=$PATH:$JAVA_HOME/bin

export HADOOP_MAPRED_HOME=$HADOOP_HOME
export HADOOP_COMMON_HOME=$HADOOP_HOME
export HADOOP_HDFS_HOME=$HADOOP_HOME
export YARN_HOME=$HADOOP_HOME
export HADOOP_COMMON_LIB_NATIVE_DIR=$HADOOP_HOME/lib/native
export PATH=$PATH:$HADOOP_HOME/sbin:$HADOOP_HOME/bin
export HADOOP_INSTALL=$HADOOP_HOME

export HADOOP_CONF_DIR=$HADOOP_HOME/etc/hadoop

```

and then execute:

>- $ . ~/.bashrc

4- Edit /etc/hosts and add the ip as master and the ip of the salve as slave:
```
127.0.0.1       localhost
10.100.10.2     master
10.100.10.12    slave
```
> **Note:**

> - If you want to add more slaves you need to edit this hosts file and  $HADOOP_CONF_DIR/slaves

5- Finally you need to establish a ssh communication between every node:
```
hduser@ubuntu:~$ ssh-keygen -t rsa -P ""
Generating public/private rsa key pair.
Enter file in which to save the key (/home/hduser/.ssh/id_rsa):
Created directory '/home/hduser/.ssh'.
Your identification has been saved in /home/hduser/.ssh/id_rsa.
Your public key has been saved in /home/hduser/.ssh/id_rsa.pub.
The key fingerprint is:
9b:82:ea:58:b4:e0:35:d7:ff:19:66:a6:ef:ae:0e:d2 hduser@ubuntu
The key's randomart image is:
[...snipp...]
hduser@ubuntu:~$ cat $HOME/.ssh/id_rsa.pub >> $HOME/.ssh/authorized_keys
hduser@ubuntu:~$ ssh localhost
The authenticity of host 'localhost (::1)' can't be established.
RSA key fingerprint is d7:87:25:47:ae:02:00:eb:1d:75:4f:bb:44:f9:36:26.
Are you sure you want to continue connecting (yes/no)? yes
Warning: Permanently added 'localhost' (RSA) to the list of known hosts.
Linux ubuntu 2.6.32-22-generic #33-Ubuntu SMP Wed Apr 28 13:27:30 UTC 2010 i686 GNU/Linux
Ubuntu 10.04 LTS
[...snipp...]
hduser@ubuntu:~$
```

**Configure Slave:**

Repeat the same step as for configuring master, but in step 2 copy the folder called Hadoop_Slave

**Create Namenode and Datanodes and HDFS:**

Once you have both Slave and Master reconfigured execute in master: 

>- $ hdfs namenode -format 

To start hdfs execute:

>- $ start-dfs.sh;start-yarn.sh;jps

This command should start hdfs in all the nodes.

If you go to http://localhost:50070/ you should see as many datanodes actives as slaves you had configured.

In the same way you open http://localhost:8088/ you should also see as many nodes as you had configured.

Spark
-------------

1 - Download Apache Spark spark-1.6.1-bin-hadoop2.6 
2- Once you have downloaded Spark you can run the consumer executing:

>- /spark-1.6.1-bin-hadoop2.6/bin/spark-submit --class consumer.SparkConsumer --master yarn --deploy-mode client /home/youruser/workspace/IOT-Platform/platform/services/kafka-hadoop-spark/GpsKafkaSparkConsumer/target/kafkaSparkConsumer-0.0.1-SNAPSHOT-jar-with-dependencies.jar


