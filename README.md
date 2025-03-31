Tehnologije: 
-> Java: Verzija 17
-> Spring Boot: Verzija 3.4.4
-> Spring Web
-> Spring Data JPA
-> Spring Security (s JWT autentifikacijom i autorizacijom)
-> Angular: Verzija 15+ (Angular CLI)
-> PostgreSQL: Verzija 14
-> Maven: Upravljanje ovisnostima i buildanje backend aplikacije
-> Docker & Docker Compose: Izgradnja i pokretanje containera za backend, frontend i bazu podataka

Alati: 
-> Spring Tool Suite 4.4.29.1
-> VS code
-> Dbeaver 
-> Postman 

Docker: 
-> Docker & Docker Compose

Pokretanje dockera: 
1. git clone https://github.com/do774/flaster.git
2. Ući u projekt -> cd flaster
3. Pokretanje dockera -> sudo docker compose up --build

Ako sve prođe ok, trebali bi vidjeti nešto kao :
db-1        | 2025-03-31 22:03:33.743 UTC [1] LOG:  database system is ready to accept connections
....
frontend-1  | /docker-entrypoint.sh: Configuration complete; ready for start up
....
backend-1   | 2025-03-31T22:03:39.238Z  INFO 1 --- [blog] [           main] com.flaster.blog.BlogApplication         : Started BlogApplication in 4.949 seconds (process running for 5.405)

Linkovi:
-> backend  : http://localhost:4200
-> frontend : http://localhost:8080

Pokretanje bez dockera: 
1. Nakon clone-a , u flaster/Backend/blog  -> mvn clean install , da se u /targetu iskreira .jar 
2. Pokrenuti u flaster/Backend/blog/target -> java -jar target/blog-0.0.1-SNAPSHOT.jar
3. U flaster/blog-front/ -> npm start (ako ne proradi iz prve, onda npm install prije, radi ovisnosti)
4. Na kraju -> npm run build -- --configuration production

ILI 
1. Ako se koristi neki editor kao Eclipse, importati flaster/Backend/blog projekt i pokrenuti kao java ili spring app.(Ako vec imamo svoj tomcat, onda treba dodati app tamo provo prije pokretanja tomcata).
2. U VS Code npr. dodati flaster/Frontend/blog-front (ako je instaliran npm, node i @angular/cli) i samo ng serve za pokretanje.
3. Koristiti postgresql kao sto je definirano u application.properties za uspjesnu konekciju.
