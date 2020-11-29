#!/bin/bash

#edgegallery/edget-tester
cd ./tester
chmod +x ./build.sh
./build.sh
cd ..

#Build back end: edgegallery/edget-be
cd ./backend
chmod +x ./build.sh
./build.sh
cd ..

#Build Test case:#edgegallery/testcase-feature1
cd ./testcases
chmod +x ./build.sh
./build.sh
cd ..

