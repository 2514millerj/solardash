/*
 * Voltage Sensor Variables
*/
const int analogIn = A0;
int mVperAmp = 66; // use 100 for 20A Module and 66 for 30A Module
int RawValue= 0;
int ACSoffset = -2486; //was 2500
double Voltage = 0;
double Amps = 0;

/*
 * Current Sensor Variables
 */
int analogInput = A1;
float vout = 0.0;
float vin = 0.0;
float R1 = 3000.0; //  
float R2 = 1500.0; // 
int value = 0;

/*
 * Temperature Sensor Variables
 */
const int temperaturePin = 10;

void setup(){
 Serial.begin(9600);
}

void loop(){
 RawValue = 0; //reset value
 String data = "";

 /*
  * Pull data from current sensor
  */
 for (int x = 0; x< 64;x++) // 64 analogue readings for averaging
 {
    RawValue = RawValue + analogRead(analogIn); // add each A/D reading to a total
 }
 Voltage = ((RawValue /64)/ 1024.0) * 5000; // Gets you mV
 Amps = ((Voltage - ACSoffset) / mVperAmp);
 
 //Serial.print("Raw Value = " ); // shows pre-scaled value
 //Serial.print(RawValue /64);

 String current_data = "{current_sensor: {voltage: " + String(Voltage) + ", amps: " + String(Amps) + "}}";
 
 //Serial.print("\t mV = "); // shows the voltage measured
 //Serial.print(Voltage,3); // the '3' after voltage allows you to display 3 digits after decimal point
 //Serial.print("\t Amps = "); // shows the voltage measured
 //Serial.println(Amps,3); // the '3' after voltage allows you to display 3 digits after decimal point

 /*
  * Pull data from voltage sensor
  */
  value = analogRead(analogInput);
  vout = (value * 5.0) / 1024.0; // see text
  vin = vout / (R2/(R1+R2));
  //Serial.print("INPUT V= ");
  //Serial.print(vin,2);

  String voltage_data = "{voltage_sensor: {voltage: " + String(vin) + "}}";

  /*
   * Pull data from temperature Sensor
   */
   //getTemperature(temperaturePin);
   String temperature_data = "{temperature_sensor: {degreesC: 24, degreesF: 72}}";

 data = "[" + current_data + "," + voltage_data + "," + temperature_data + "]";
 Serial.print(data);
 delay(2500);
 
}

float getTemperature(int pin)
{
  float voltage, degreesC, degreesF;

  voltage = (analogRead(pin) * 0.004882814);

  degreesC = (voltage - 0.5) * 100.0;

  degreesF = degreesC * (9.0/5.0) + 32.0;

  Serial.print("voltage: ");
  Serial.print(voltage);
  Serial.print("  deg C: ");
  Serial.print(degreesC);
  Serial.print("  deg F: ");
  Serial.println(degreesF);

  // These statements will print lines of data like this:
  // "voltage: 0.73 deg C: 22.75 deg F: 72.96"
}

