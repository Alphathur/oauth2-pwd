# oauth2-pwd
Spring Boot App use Spring Security OAuth2.0 and password grant type.

## Usage
### Get jwt token
```bash
curl -XPOST 'http://192.168.137.1:8080/oa/token' -d'username=tony&password=123456'
```
output example
```json
{
    "access_token": "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJhdWQiOlsiYXV0aC1zZXJ2ZXIiXSwidXNlcl9uYW1lIjoidG9ueSIsInNjb3BlIjpbInJlYWQiLCJ3cml0ZSJdLCJleHQtaW5mbyI6eyJ1c2VySWQiOjV9LCJleHAiOjE2MDIwNjA2MzksImF1dGhvcml0aWVzIjpbIlJPT1QiXSwianRpIjoiZjU3NGM4Y2QtNzZiYi00NjQzLWIwMTYtNTA3NTVjNTgwN2Y0IiwiY2xpZW50X2lkIjoiY2xpZW50LXB3ZCJ9.dUWpSYyRLMhbJddtSRVRpgX5OiXZwSUJQaSVxOuY1VFYOavaVyC-ZTEwIe8uiJoMjceBgQ295u-HHeTNrLdMxT5stkCeQ4Te__GEhBt0dYaHBfwHcjao1ofTBSZ5pgKZj00-lP-WGIk0IFwBZKmtcIjVUUhpzAjGTl9whpNC4hd2Jb3K5Np_rsAD4ah22Ck8gRskMFBbAvyeRPSJVT52YTWtEbB17RFH-gFKC9t2lwQmUZvzHtpJChVGJl7Szrgc_ae14MDa55e5lVs2asLnCz-kVr3j4yzPuIpzV9cvG4KtHAts4-DtYfuKtPYzqql_O8bF__LSQOW52fK3r1iA2Q",
    "token_type": "bearer",
    "refresh_token": "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJhdWQiOlsiYXV0aC1zZXJ2ZXIiXSwidXNlcl9uYW1lIjoidG9ueSIsInNjb3BlIjpbInJlYWQiLCJ3cml0ZSJdLCJhdGkiOiJmNTc0YzhjZC03NmJiLTQ2NDMtYjAxNi01MDc1NWM1ODA3ZjQiLCJleHQtaW5mbyI6eyJ1c2VySWQiOjV9LCJleHAiOjE2MDIwNjA2MzksImF1dGhvcml0aWVzIjpbIlJPT1QiXSwianRpIjoiZDI4ZWZjOTEtODJkMS00MzQ0LWE5OGYtZmMyYzQ1OGI3MzMxIiwiY2xpZW50X2lkIjoiY2xpZW50LXB3ZCJ9.N57VvO1vxBVw-FyDyD8W5V4RgxLtjcVSAGQqaROm8-dZjfcsj0-gDsfrKBwfOF5YqWpjOwXf9iirRIEAPXJfSRmffnRa3movYOpUTi5rhzK0jG2KfAoZTxbXETFu8iF5xGiE98VLtYScowf5gzMkSQxkcdhNUXLQ8EXFsYEVIdFjpvf5EOGCvErCl8W5tgx8puaissrXbHjQkZLSv730OOIyea9-cEnj5OseLQELIPNkTT9pgw5PcaMaxcMeO-AZTRhEv3Ntjc3IxyE9PbPWr13LpaUwomDXbFOM9nxT3-Sjza7L3yAoqrWvSxFYtGJmctMdmvODf3dt63vqcyYUOw",
    "expires_in": 3599,
    "scope": "read write",
    "ext-info": {
        "userId": 5
    },
    "jti": "f574c8cd-76bb-4643-b016-50755c5807f4"
}
```

### Use access token in http request header
```bash
curl -H'Authorization:Bearer eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJhdWQiOlsiYXV0aC1zZXJ2ZXIiXSwidXNlcl9uYW1lIjoidG9ueSIsInNjb3BlIjpbInJlYWQiLCJ3cml0ZSJdLCJleHQtaW5mbyI6eyJ1c2VySWQiOjV9LCJleHAiOjE2MDIwNjA2MzksImF1dGhvcml0aWVzIjpbIlJPT1QiXSwianRpIjoiZjU3NGM4Y2QtNzZiYi00NjQzLWIwMTYtNTA3NTVjNTgwN2Y0IiwiY2xpZW50X2lkIjoiY2xpZW50LXB3ZCJ9.dUWpSYyRLMhbJddtSRVRpgX5OiXZwSUJQaSVxOuY1VFYOavaVyC-ZTEwIe8uiJoMjceBgQ295u-HHeTNrLdMxT5stkCeQ4Te__GEhBt0dYaHBfwHcjao1ofTBSZ5pgKZj00-lP-WGIk0IFwBZKmtcIjVUUhpzAjGTl9whpNC4hd2Jb3K5Np_rsAD4ah22Ck8gRskMFBbAvyeRPSJVT52YTWtEbB17RFH-gFKC9t2lwQmUZvzHtpJChVGJl7Szrgc_ae14MDa55e5lVs2asLnCz-kVr3j4yzPuIpzV9cvG4KtHAts4-DtYfuKtPYzqql_O8bF__LSQOW52fK3r1iA2Q' -XGET 'http://192.168.137.1:8080/user'
```
output example
```json
[
    {
        "id": 1,
        "username": "jerry",
        "email": "jerry@gmail.com",
        "userRole": "ADMIN",
        "createdAt": "2020-10-07T05:00:28.000+0000",
        "updatedAt": "2020-10-07T05:00:42.000+0000",
        "lastLoginAt": "2020-10-07T05:00:22.000+0000"
    },
    {
        "id": 2,
        "username": "jack",
        "email": "jack@gmail.com",
        "userRole": "DEVELOPER",
        "createdAt": "2020-10-07T05:09:57.000+0000",
        "updatedAt": "2020-10-07T05:10:18.000+0000",
        "lastLoginAt": "2020-10-07T05:10:08.000+0000"
    },
    {
        "id": 3,
        "username": "tom",
        "email": "tom@gmail.com",
        "userRole": "MEMBER",
        "createdAt": "2020-10-07T05:10:45.000+0000",
        "updatedAt": "2020-10-07T05:11:00.000+0000",
        "lastLoginAt": "2020-10-07T05:10:54.000+0000"
    },
    {
        "id": 4,
        "username": "dannie",
        "email": "dannie",
        "userRole": "GUEST",
        "createdAt": "2020-10-07T05:11:41.000+0000",
        "updatedAt": "2020-10-07T05:11:30.000+0000",
        "lastLoginAt": "2020-10-07T05:11:36.000+0000"
    },
    {
        "id": 5,
        "username": "tony",
        "email": "tony",
        "userRole": "ROOT",
        "createdAt": "2020-10-07T05:12:26.000+0000",
        "updatedAt": "2020-10-07T05:12:14.000+0000",
        "lastLoginAt": "2020-10-07T05:12:20.000+0000"
    }
]
```

## Run in docker
### Start mysql and execute sql script
```bash
docker network create mysql-network
docker run -d  -p 3306:3306 -e MYSQL_ROOT_PASSWORD=mysql520  -v /opt/data/mysql:/var/lib/mysql --name mariadb --net=mysql-network  --network-alias mysql  mariadb:10.3
docker cp user.sql mariadb:.
docker exec -it mariadb /bin/bash
mysql -hlocalhost -uroot -pmysql520
```
```bash
create database demo;
use demo;
source user.sql;
```
### Start spring boot application
```bash
mvn clean package -DskipTests
docker build -t oauth-pwd:1.0 .
docker run -d  -p8080:8080  --name oauth-pwd  --net=mysql-network   oauth-pwd:1.0
```
