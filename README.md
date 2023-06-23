## Name
InPress Weather

## Description
This is a RESTful API for communication and expose endpoints for retrieving weather information and integrates with the weather data provider OpenWeatherMap to fetch real-time weather data.  
This is a Coding assignment which is assigned by InPress to evaluate the candidate (Askar Ghandchi) to likely employment.  
The project employs three types of container: discovery (one node), gateway (one node) and weather (n nodes).
The docker-compose is set to create 3 nodes of weather microservice.  
The gateway microservice listens on port 8080 and dispatches requests to the weather containers; indeed it plays the load-baLancer role.

## Installation
This guideline assumes docker and docker-compose have been already installed.  
After cloning or downloading the source code, do the following steps:
1. Open a terminal and change directory to the main directory of the project where the file docker-compose.yml exists.
2. Issue the following commands to build required docker images. These commands require internet connection to download some docker images and plenty of maven dependencies; so it is needed to be patient to fulfill the operations
```
   docker build -t inpress/discovery:1.0.0 discovery
   docker build -t inpress/gateway:1.0.0 gateway
   docker build -t inpress/weather:1.0.0 weather
```
3. After successfully completing previous commands, execute one of the following command to start the project
```
docker-compose up -d
docker-compose up
```
4. Wait for a minute or so to complete the startup operations
5. The usage of API is explained in the next section
6. For shutting down the project, run the following command in the project main directory
```
docker-compose down
```
## Usage
The internet connection is required to fetch weather data from openweathermap.org.  
There are three REST services as follows: 
1. Current Weather
2. Forecast
3. Air Pollution

The Current Weather service uses the GET verb and can be called like the following URL:
```
http://localhost:8080/weather/current/Tehran
```
The name of intended city can be cite at the end of the URL.  

The Forecast service uses the GET verb and can be called like the following URL:
```
http://localhost:8080/weather/forecast/Paris
```
The name of intended city can be cite at the end of the URL.  

The Air Pollution service uses the POST verb and can be called using a POST request:

URL:
```
http://localhost:8080/weather/air-pollution
```
Body sample:
```
{
    "city": "Brussels",
    "start-date": "2023-06-20",
    "end-date": "2023-06-23"
}
```
The body should be in JSON format.  

The API has been tested on macOS Ventura 13.4.1

## Author
Askar Ghandchi