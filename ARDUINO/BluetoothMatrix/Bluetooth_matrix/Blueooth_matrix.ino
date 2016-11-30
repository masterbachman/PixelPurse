#include "FastLED.h"

// How many leds in your strip?
#define NUM_LEDS 64

// For led chips like Neopixels, which have a data line, ground, and power, you just
// need to define DATA_PIN.  For led chipsets that are SPI based (four wires - data, clock,
// ground, and power), like the LPD8806 define both DATA_PIN and CLOCK_PIN
#define DATA_PIN 9
#define CLOCK_PIN 13

int state = 0;
int flag = 0;

// Define the array of leds
CRGB leds[NUM_LEDS];

void setup() {
  	  FastLED.addLeds<NEOPIXEL, DATA_PIN>(leds, NUM_LEDS);
      Serial.begin(9600);
}
void clearLeds(){
  for (int i = 0; i < 64; ++i)
  {
    leds[i] = CRGB::Black;
  }
}

void drawHeart(){
  clearLeds();
  leds[1] = leds[2] = leds[5] = leds[59] = leds[60] = leds[6] = CRGB:Red;
  for (int i = 8; i < 40; ++i)
  {
    leds[i] = CRGB::Red;
  }
  for (int i = 41; i < 47; ++i)
  {
    leds[i] = CRGB::Red;
  }
  for (int i = 50; i < 55; ++i)
  {
    leds[i] = CRGB::Red;
  }
}

void loop() {
 if(Serial.available() > 0){
   state = Serial.read();
   flag=0;
 }
//   leds[i] = CRGB::White;
  if (state == '1') {
    drawHeart();
    FastLED.show();
    if(flag == 0){
      Serial.println("PATTERN 1 SELECTED.");
      flag = 1;
    }
  }


  else if (state == '2') {
    clearLeds();
    for (int i = 0; i < sizeof(leds)/sizeof(leds[0]); ++i)
    {
      if (i % 3 == 0)
      {
        leds[i] = CRGB::White;
      }
      else{leds[i] = CRGB::Red;}
    }

  FastLED.show();
  if(flag == 0){
  Serial.println("PATTERN 2 SELECTED.");
  flag = 1;
  }
  }

  else if (state == '3') {
    clearLeds();
    for (int i = 0; i < sizeof(leds)/sizeof(leds[0]); ++i)
    {
      if (i % 2 == 0)
      {
        leds[i] = CRGB::ForestGreen;
      }
      else{leds[i] = CRGB::Red;}
    }

  FastLED.show();
  else if(flag == 0){
  Serial.println("PATTERN 3 SELECTED.");
  flag = 1;
  }
  }

  else if (state == '4') {
    clearLeds();
    FastLED.show();
    if(flag == 0){
    Serial.println("PATTERN 4 SELECTED.");
    flag = 1;
    }
  }

  else if (state == '5') {
    clearLeds();
    for (int i = 0; i < sizeof(leds)/sizeof(leds[0]); ++i)
    {
      if (i % 3 == 0)
      {
        leds[i] = CRGB::White;
      }
      else{leds[i] = CRGB::CadetBlue;}
    }

  FastLED.show();
  if(flag == 0){
  Serial.println("PATTERN 5 SELECTED.");
  flag = 1;
  }
  }

  else if (state == 'BEGIN') {
    clearLeds();
    for (int i = 0; i < NUM_LEDS; i++) {
      leds[i] = CRGB::Green;
    }
    FastLED.show();
    delay(100);
    clearLeds();
    FastLED.show();
    delay(100);
    for (int i = 0; i < NUM_LEDS; i++) {
      leds[i] = CRGB::Green;
    }
    FastLED.show();
    if(flag == 0){
    Serial.println("LED: OFF");
    flag = 1;
    }
  }

  else if (state == '0') {
    clearLeds();
    FastLED.show();
    if(flag == 0){
    Serial.println("LED: OFF");
    flag = 1;
    }
  }

 }
