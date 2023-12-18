# Getting Started

### Prepare
docker pull mysql:latest
docker run -d --name mysql-container -e MYSQL_ROOT_PASSWORD=mysql -e MYSQL_DATABASE=epam_db -e MYSQL_USER=mysql -e MYSQL_PASSWORD=mysql -p 3306:3306 mysql:latest

### Run
mvn clean test install spring-boot:run

