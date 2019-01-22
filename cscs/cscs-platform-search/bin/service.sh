#!/bin/sh
error_exit ()
{
    echo "ERROR: $1 !!"
    exit 1
}

[ ! -e "$JAVA_HOME/bin/java" ] && JAVA_HOME=$HOME/jdk/java
[ ! -e "$JAVA_HOME/bin/java" ] && JAVA_HOME=/usr/java

export cmd=$1
export table=$2
export JAVA_HOME
export JAVA="$JAVA_HOME/bin/java"
export APP_HOME=`cd $(dirname $0)/..; pwd`
export DATA_HOME=${APP_HOME}/data
echo "app.home=${APP_HOME}"
export MAIN_CLASS="com.chinacscs.platform.search.StartUp"
export PID_FILE=$APP_HOME'/bin/.program.pid'

#--------------------------set class path jar package -----------------------
CLASSPATH=${APP_HOME}'/config'
SEARCH_JAR_PATH=(
        "$APP_HOME/lib"
        )

for jarpath in ${SEARCH_JAR_PATH[@]}; do
        for file in $jarpath/*.jar; do
                # check file is in classpath
                result=$(echo "$CLASSPATH" | grep "$file")
                if [[ "$result" == "" ]]; then
                        CLASSPATH=$CLASSPATH:$file;
                fi
        done
done

JAVA_OPT="${JAVA_OPT} -Dapp.home=${APP_HOME}"
JAVA_OPT="${JAVA_OPT} -classpath ${CLASSPATH}"
JAVA_OPT="${JAVA_OPT} ${MAIN_CLASS}"
JAVA_OPT="${JAVA_OPT} --app.home=${APP_HOME}"

if [ ! -d "${APP_HOME}/logs" ]; then
  mkdir ${APP_HOME}/logs
fi

if [ ! -d "${DATA_HOME}" ]; then
  mkdir ${DATA_HOME}
fi

#-Xdebug -Xrunjdwp:server=y,transport=dt_socket,address=8000,suspend=n
function start_program(){
        if [ -f $PID_FILE ]; then
      echo "program is running exit."
          exit 0
    fi
    echo -n "starting program ... "
                echo "$JAVA ${JAVA_OPT}" > ${APP_HOME}/nohup.out 2>&1 &
                nohup $JAVA ${JAVA_OPT} --app.home=${APP_HOME} >> ${APP_HOME}/nohup.out 2>&1 &
                echo "App is starting please you can check the ${APP_HOME}/nohup.out"
    if [ $? -eq 0 ]
    then
      if /bin/echo -n $! > "$PID_FILE"
      then
        #sleep 1
        echo STARTED
      else
        echo FAILED TO WRITE PID
        exit 1
      fi
    else
      echo PROGRAM DID NOT START
      exit 1
    fi
}

#-------------------------------------------------------
function stop_program(){
        #--------------------------- kill program start --------------------
        echo -n "Stopping program ... "
    if [ ! -f "$PID_FILE" ]
    then
      echo "no the program to stop (could not find file $PID_FILE)"
    else
        kill -15  $(cat "$PID_FILE")
      rm "$PID_FILE"
      echo STOPPED
    fi
}




ACTION=$1
case "$ACTION" in
  start)
        start_program
  ;;
  stop)
        stop_program
  ;;
  restart)
        stop_program
        start_program
  ;;
  check)
    echo "Checking arguments to $PROJECT_NAME: "
    echo "JAVA_HOME                     =  $JAVA_HOME"
    echo "PROJECT_HOME          =  $PROJECT_HOME"
    echo "LOG_FILE              =  $LOG_FILE"
    echo "MAIN_JAR              =  $MAIN_JAR"
    echo "MAIN_CLASS            =  $MAIN_CLASS"
    echo "JAVA_OPTIONS                  =  ${JAVA_OPTIONS[*]}"
    echo "SEARCH_JAR_PATH       =  ${SEARCH_JAR_PATH[*]}"
    echo "JAVA                          =  $JAVA"
    echo "CLASSES_PATH          =  $CLASSES_PATH"
    echo

    if [ -f $PID_FILE ];
    then
      echo "RUNNING PID =$(cat "$PID_FILE")"
      exit 0
    fi
    exit 1

    ;;
  *)
    usage
    ;;
esac
exit 0
