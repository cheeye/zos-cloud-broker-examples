#/bin/bash

# Requires the following ENV variables
# - DOCKER_USERNAME
# - DOCKER_PASSWORD


# Ensure directory is set to project root path
cd $(dirname $0)/..

export IMAGE_NAME=zos-broker-instance-dashboard
export REPO=ivandov/jdbc-viewer-vue

echo "Testing Docker Hub credentials"
echo "$DOCKER_PASSWORD" | docker login -u "$DOCKER_USERNAME" --password-stdin
echo "Docker Hub credentials are working"

if [ "$CI" == "true" ]; then
  echo "Running CI specific push steps"

  # Set Docker tag to match git branch
  export TAG=`if [ "$TRAVIS_BRANCH" == "master" ]; then echo "cicd"; else echo $TRAVIS_BRANCH ; fi`
  
  echo "Docker tag set to $TAG"
else
  export TAG=local
fi

echo "Building Docker image"
docker build -t $REPO .
docker tag $REPO $REPO:$TAG
docker push $REPO:$TAG