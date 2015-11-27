/*
 * Initial Arduino code by shailendra singh 
 *
 *
 */
#include <LiquidCrystal.h>
#include <stdio.h>
#include <SoftwareSerial.h>
#define DATABUFFERSIZE 80

LiquidCrystal lcd(12, 11, 5, 4, 3, 2);
int sensorPin = A0; 
int sensorValue = 0;
const int ledPin = 8;
char dataBuffer[DATABUFFERSIZE+1]; 
char startChar = '$';
char endChar = '@';

boolean storeString = false;
SoftwareSerial BTSerial(9,10); // RX | TX




void setup(){
    // set up the LCD's number of columns and rows: 
    lcd.begin(16, 2);
    // Print a message to the LCD.
    lcd.print("Silverline Design Inc");
    delay(3000);
    pinMode(ledPin,OUTPUT);
    digitalWrite(ledPin,LOW);
    Serial.begin(9600);
    BTSerial.begin(9600);
    delay(2000);
  }
 
    boolean getSoftwareSerialstring(){
    static byte dataBufferIndex = 0;
  
    while(BTSerial.available()>0){
    char incomingByte = BTSerial.read();
        if(incomingByte ==startChar){
            dataBufferIndex = 0;
            storeString =true;
              }
        if(storeString){
            if(dataBufferIndex == DATABUFFERSIZE){
            dataBufferIndex = 0;
            break;
                    }
        if(incomingByte==endChar){
            dataBuffer[dataBufferIndex] = 0;
            return true;
            }
        else{
            dataBuffer[dataBufferIndex++]=incomingByte;
            dataBuffer[dataBufferIndex] = 0;
            }
      }
        else{
      }
  }
        return false;
}


void loop(){
    if(getSoftwareSerialstring()){
        Serial.println(dataBuffer);
            if(strstr(dataBuffer,"ON")){
                Serial.println("Received ON"); //this is also fro debugging
                digitalWrite(ledPin,HIGH); //if we wil connect any relay circuit 5V In pin to PIN13, AC appliance conected to it will blink
                }
    else if(strstr(dataBuffer,"OFF")){
          Serial.println("Received OFF");
          digitalWrite(ledPin,LOW);
        }
    else if(strstr(dataBuffer,"Read Sensor")){
          sensorValue = analogRead(sensorPin);
          lcd.setCursor(0, 1);
          lcd.print("Moisture=");
          BTSerial.print("Moisture=");
          BTSerial.print(sensorValue);
          BTSerial.print("&");
          lcd.setCursor(9,1);
          lcd.print(sensorValue);
          delay(1000);
        }

        }
    }


  
  
  
  
  
  
     
   
    
  

 


