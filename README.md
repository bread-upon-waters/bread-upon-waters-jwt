# Spring Boot Security

**Forms of Authentication :**
- **Form Base Auth** (On valid credentials, it uses cookie sessionid (Expiry time 30 min))
![form_base_auth](https://user-images.githubusercontent.com/8968908/101985316-f4347300-3c8f-11eb-9585-2260797fa9c5.PNG)
 
Override default login Form
![overrride_form_base_auth](https://user-images.githubusercontent.com/8968908/101985966-b3d6f400-3c93-11eb-9385-f32c60e5b6be.PNG)
 
- **Basic Auth** (Username & Password is send as Base64 with every request)
![basic_auth](https://user-images.githubusercontent.com/8968908/101938466-08269900-3bec-11eb-9a65-f08d828bd6e6.PNG)

* JSON Web Token (On valid credentials, it web token is sent with every request)
*Learn about jwt here** ...[JSON Web Token](https://jwt.io/)



## Cross-site request forgery 
**Learn about csrf here** ...[Cross-site request forgery](https://www.imperva.com/learn/application-security/csrf-cross-site-request-forgery/)
![csrf](https://user-images.githubusercontent.com/8968908/101984897-fea13d80-3c8c-11eb-9174-8b71ed3d9a9c.PNG)
