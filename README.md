The project is a small server application that provides weather data for the current day via REST API or a telegram bot.

In version 0.0.2, you can select cities such as:
Wellington, 
Auckland, 
Rotorua, 
Petone, 
Lower Hutt, 
Upper Hutt, 
Christchurch,
Novosibirsk.

To connect to the bot, you need to find it by the name `MyTestWeatherForecastBot`

Requests:

`/city` select a city from the list to receive weather and wind data for this city.

![cities.jpg](images%2Fcities.jpg)

`/weather` get data on temperature, humidity and UV index level for 8 am, 12 pm, 4 pm and 8 pm.

![weather.jpg](images%2Fweather.jpg)

`/wind` get data on wind direction and strength for 8 am, 12 pm, 4 pm and 8 pm.

![wind.jpg](images%2Fwind.jpg)