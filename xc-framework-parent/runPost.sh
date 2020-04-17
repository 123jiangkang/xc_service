#! /bin/sh

image=`sudo docker images | grep -w $1 | head -n 1 | awk '{print $3}'`

#启动容器
sudo docker run -itd --name $1 -p 31002:31002 $image

if [ $? -eq 0 ];then
      echo "$1 容器已启动"
fi