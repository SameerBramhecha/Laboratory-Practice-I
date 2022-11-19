import adafruit_dht
import time
import board
import RPi.GPIO as GPIO

dhtDevice = adafruit_dht.DHT22(board.D4)
GPIO.setup(18,GPIO.IN) #Temparature Sensor
GPIO.setup(4,GPIO.OUT) #LED out pin no

while True:
    try:
        temperature = dhtDevice.temperature
        humidity = dhtDevice.humidity
        print('Temp={0:0.1f}*C Humidity={1:0.1f}%'.format(temperature,humidity))
        if temperature > 25:
            GPIO.output(4,True)
            time.sleep(1.0)
        GPIO.output(4,False)
    except RuntimeError as error:
        print(error.args[0])
        time.sleep(2.0)
        continue
    except Exception as error :
        dhtDevice.exit()
        raise error
    time.sleep(5.0)
