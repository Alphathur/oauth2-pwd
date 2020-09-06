tag=$(date +"%Y%m%d-%H%M%S")
if [ $# -eq 1 ]; then
    tag=$1
fi
image=oauth-pwd:$tag
docker build -t $image .
echo "$image"