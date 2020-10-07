# oauth2-pwd
Spring Boot App use Spring Security OAuth2.0 and password grant type.

## Usage

## Run in docker
### Start mysql
```bash
docker run -d  -p 3306:3306 -e MYSQL_ROOT_PASSWORD=mysql520  -v /opt/data/mysql:/var/lib/mysql --name mariadb --net=mysql-network  --network-alias mysql  mariadb:10.3
```
### Start spring boot application
```bash
mvn clean package -DskipTests
```
```bash
docker build -t oauth-pwd:1.0 .
```
```bash
docker run -d  -p8080:8083  --name oauth-pwd  --net=mysql-network   oauth-pwd:1.0
``` 