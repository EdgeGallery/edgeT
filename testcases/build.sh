for f in `ls -l | grep ^d | awk '{print $9}'`; do
  cd $f
  chmod +x ./build.sh
  ./build.sh
  cd ..
done

