#!/bin/bash
#
#chkconfig: 2345 99 36
##################

username=`whoami`
if [ "$username"x = "root1"x ];then
        echo "Can not start the service using user: ${username}!!!"
        exit 255
fi


SERVER_NAME=Tanya
SHUTDOWN_WAIT=5
SERVER_TYPE=prod
APP_PATH=/root/tanya


ARGUMENTS1=-Dspring.profiles.active=${SERVER_TYPE}
ARGUMENTS2=-Dserver.port=8080
JAR=`ls -lt "${APP_PATH}"/*.jar | awk 'NR==1 {print $9}'`


get_pid(){
    echo `ps aux | grep "${JAR}" | grep -v grep | awk '{ print $2 }'`
}
start(){
        pid=$(get_pid)
        if [ -n "$pid" ]
        then
                echo "$SERVER_NAME is already running pid : $pid"
        else
                echo "Starting $SERVER_NAME..."
                echo "${APP_PATH}"
                echo "${ARGUMENTS1}"
                echo "${JAR}"
                echo "java -Xms512m -Xmx1024m -XX:-HeapDumpOnOutOfMemoryError -XX:HeapDumpPath="${APP_PATH}" "${ARGUMENTS1}" "${ARGUMENTS2}"  -jar "${JAR}" > $APP_PATH/spring.log &"
                nohup java -Xms512m -Xmx1024m -XX:-HeapDumpOnOutOfMemoryError -XX:HeapDumpPath="${APP_PATH}" "${ARGUMENTS1}" "${ARGUMENTS2}"  -jar "${JAR}" >> $APP_PATH/spring.log &
                echo "$SERVER_NAME started..."
        fi
        return 0
}

stop(){
        pid=$(get_pid)
        if [ -n "$pid" ]
        then
                echo "$SERVER_NAME($pid) stoping ..."
                   let kwait=$SHUTDOWN_WAIT
                        count=0;
                        until [ `ps -p $pid | grep -c $pid` = '0' ] || [ $count -gt $kwait ]
                                do

                                        sleep 1
                                        let remainTime=kwait-$count;
                                        let count=$count+1;
                                        echo -n -e "\nwaiting for processes to exit (pid: $pid) $remainTime \n";
                                done

                        if [ $count -gt $kwait ]; then
                                kill -9 $pid
                                echo "$SERVER_NAME($pid) stopped ..."
                        fi
        else
                echo "$SERVER_NAME is not running ..."
        fi
        return 0

}

status(){
        pid=$(get_pid)
        if [ -n "$pid" ]
        then
               echo "${SERVER_NAME} pid : $pid"
        else
               echo "${SERVER_NAME} is not running"
        fi
        return 0
}

case $1 in
status)
        status
        ;;
start)
        start
        status
        ;;
stop)
        stop
        ;;
restart)
        stop
        start
        status
        ;;
*)

echo $"Usage : $0 {start|stop|restart|status}"
exit 1
esac
