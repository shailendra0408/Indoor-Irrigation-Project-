# Indoor-Irrigation-Project-
Use case of IOT in Smart gardening. A simple code for Arduino and Android. 

This repo include Andorid App code as well as Ardunio code. Android app and Arduino communicate over 
HC-05 bluetooth module which use simple AT commands. This app is in the very intitial stage of development 
and we had not added any additional features apart from showing readings and turning a motor ON or OFF 
based on the commands received by the app on the arduino side.

Arduino uno measure moisture from the soil using soil moisture sensor which provide analog output. This 
analog output is measured by the aruino uno on analog pin A0 and readings are displayed on the 16X2 LCD. 
Along with that same readings are send to a android app using a HC-05 buetooth module. On the Android app, 
readings are shown with help of bar. User can turn on DC pump using android app. Commands are send from 
android app and received on the arduino, where they are processed and corresponding actions are executed. 
At present the only action is turning OFF or ON a dc pump which is done by toggling a pin and using a 5V relay
to control the pump

Android app is completely developed by Ravi Kumar and firmware and hardware was developed by Shailendra Singh. 
We have used open source hardware platform Arduino.  
 
