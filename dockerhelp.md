#Docker help
To build an image:
```commandline
docker build -f Dockerfile -t tag path
```
eg.
```commandline
docker build -f Dockerfile -t tag .
```
To run the image
```commandline
docker run tag
```