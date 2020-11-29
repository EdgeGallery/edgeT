docker volume create results
docker run -d --name edget-tester --mount source=results,target=/opt/ocomp/data --network=host  edgegallery/edget-feature2-testcase:latest
docker run -d --name edget-controller --mount source=results,target=/opt/vtp/data,readonly  --network=host edgegallery/edget-be:latest
