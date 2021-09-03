# adobe_assesment

Extension 1) 

Design:
I use Rest API and spring boot framework to create a GET endpoint for converting the number to roman representation.
My Project has following 4 modules:

Controller - exposes the GET endpoint
Service - maintains the main logic to calculate roman representation
Entity -  data model representing data, in this case number string and roman string.
Dao - contains DB/in memory datastore like cache. 

Request flows from client-> controller-> service-> entity-> dao. I have also
used a HashMap as in-memory datastore to cache values if user/clients sends request with same input. Once the service is deployed, cache gets refreshed.

Problem -
Reference for Roman representation

I use following wiki for Roman number representation
https://en.wikipedia.org/wiki/Roman_numerals

Steps to run the project:

Approach 1: Run service locally 

1. git clone and download project
2. mvn clean package
3. cd Adobe-AEM-Assessment
4. java -jar target/Adobe-AEM-Assessment-1.0-SNAPSHOT.jar

This should bring up service at http://localhost:8080

To test endpoint -
http://localhost:8080/romannumeral?query=1000

Approach 2 : Run service using DockerFile

I have added a docker file to build image based of openjdk11:alpine-jre. This can be used to build and run 
java based image to deploy our service.

Steps to run docker, after you successfully download project-
1. cd Adobe-AEM-Assessment
1. docker build -t <tag_name> .
2. docker run -d -p 8080:8080 -t <tag_name>

Extension 3)

Logging - I use java.util.logging.Logger package to print info-level logging. 
This can be easily extended to debug-level logging.

Metrics - I am using spring boot actuator to expose default springboot metrics like jvm, http request, etc.
Please refer application.yml file to check configuration to enable metrics. I also use micrometer registry to emit spring boot metrics
to influxDb V2. After you run service , you should be able to see available metrics at http://localhost:8080/actuator/metrics

Monitoring - InfluxDB v2 also provides visualization/dashboards to monitor metrics.

To see metrics in influxDb:

Option 1) - Install influxDB v2 locally (https://docs.influxdata.com/influxdb/v2.0/install/)

1. brew update
   
2. brew install influxdb

3. type `influxd` to start influx.
   
4. step 3) should bring up influxDB at http://localhost:8086/ 

5. Setup user and bucket in influx DB UI as follows:
   ```
   user = `root`
   password = `password`
   org name = `adobe_org`
   bucket = `adobe_db`
   token = <token_key> at step 6.
   ```

6. For influxDb v2, you need to set up token. Go to settings, token, generate token if not already present. Copy and paste it in step 5 above.
7. Once you complete influxDB installation and user setup, you start your service, you should be able to see measurements/tables populating in 
   your bucket 'adobe_db' in influxDB UI. 
8. To see http request metric flowing,select http request measurement from UI, hit endpoint GET /romannumeral?query={integer}, you should see data flowing.
9. Visualization/charts are also part of UI console.

Option 2) - Install influxDB v2 using docker image

1. docker run -d --name influx -p 8086:8086 influxdb

2. docker exec -it influx influx

3. step 2) should bring up influxDB at http://localhost:8086/

4. After this ,you can repeat steps 5) to 9) above from Option 1 above.

Scrennshots

InfluxDb V2 Token generation 
<img width="1426" alt="Screen Shot 2021-09-03 at 1 13 58 AM" src="https://user-images.githubusercontent.com/8934946/131984234-c4ba6e75-58ef-4067-b50c-e00e5e901546.png">

Spring boot metrics exposed through actuator on InfluxDB
<img width="1368" alt="Screen Shot 2021-09-03 at 1 14 21 AM" src="https://user-images.githubusercontent.com/8934946/131984376-5b5b034b-5774-4d71-99cc-7affbabc630a.png">

InfluxDB Bucket (adobe_db) data and dashboards :
<img width="1341" alt="Screen Shot 2021-09-03 at 1 17 20 AM" src="https://user-images.githubusercontent.com/8934946/131984289-ebb32e8e-a0ae-4684-b870-4a9fee0c9914.png">

SpringBoot actuator endpoint :
<img width="1362" alt="Screen Shot 2021-09-03 at 2 38 32 AM" src="https://user-images.githubusercontent.com/8934946/131985017-2b6afeb3-202a-410c-a5e0-881320d5b7e5.png">

Service Endpoint:

<img width="1211" alt="Screen Shot 2021-09-03 at 2 40 05 AM" src="https://user-images.githubusercontent.com/8934946/131985055-97e796b5-b093-435a-807b-9a0d10c64391.png">


Future Scope:

Dev Ops:

Currenlty my docker File only contains java based image to bring up service. It can be enhanced with adding influxdb config to run both service and influx db containers side by side.
Logging can be further enhanced to configure with ELK stack to further search logs on Kibbana.

Backend:

Currently I added unit test around service layer, we can add more unit tests around controller and dao layers as well. Also integration tests can be added once we have actual DB in place. 
Exception handling can be further enhanced to create more error specific messages. Currently I just handle 400 and all other errors are categorized as 500.
