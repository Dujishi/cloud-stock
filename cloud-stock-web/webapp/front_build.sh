#!/usr/bin/env bash

remote="/res/$2"
resources="./front"

echo "chmod -R 777 $resources"
chmod -R 777 "$resources"
echo "cd $resources"
cd "$resources"
echo "rm -rf node_modules"
rm -rf node_modules
echo "unzip node_modules -d ./node_modules"
unzip node_modules -d ./node_modules

pwd=`pwd`

if [ "$1" = "prod" ]
then
    echo "npm run prod"
    npm run prod
    echo "python ../upyun_upload.py ddyc-store $pwd/dist static $remote"
    python "../upyun_upload.py" ddyc-store "$pwd/dist" static "$remote";
else
    echo "npm run int"
    npm run int
    echo "python ../upyun_upload_test.py ddyc-store-test $pwd/dist static $remote"
    python "../upyun_upload_test.py" ddyc-store-test "$pwd/dist" static "$remote";
fi

echo "rm -rf node_modules"
rm -rf node_modules