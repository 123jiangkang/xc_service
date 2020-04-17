#! /bin/sh

dockerPs=`sudo docker ps | grep -w $1 | head -n 1 | awk '{print $1}'`
if [ "$dockerPs" != "" ];then
   sudo docker stop $dockerPs
   if [ $? -eq 0 ];then
      echo "$dockerPs 容器已停止"
   fi
fi

dockerPsAll=`sudo docker ps -a | grep -w $1 | head -n 1 | awk '{print $1}'`
if [ "$dockerPsAll" != "" ];then
   sudo docker rm $dockerPsAll
   if [ $? -eq 0 ];then
      echo "$dockerPsAll 容器已删除"
   fi
fi

image=`sudo docker images | grep -w $1 | head -n 1 | awk '{print $3}'`
if [ "$image" != "" ] ;then
   sudo docker rmi $image
   if [ $? -eq 0 ];then
      echo "$image 镜像已删除"
   fi
fi
