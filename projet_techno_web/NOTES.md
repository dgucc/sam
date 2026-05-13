

```sh

# Install podman
$ sudo apt-get install podman

# Alias podman as docker
$ echo "# replace docker with podman" >> ~/.bashrc
$ echo "alias docker=podman" >> ~/.bashrc
source ~/.bashrc

# Run mysql container
$ podman run -d \
--name mysql \
-p 3306:3306 \
-e MYSQL_ROOT_PASSWORD=admin \
-v docker-mysql:/var/lib/mysql \
docker.io/library/mysql:8.4


# Open mysql terminal
$ podman exec -it mysql mysql -u root -p admin

mysql> show databases;

```


```sh
$ docker start mysql

```