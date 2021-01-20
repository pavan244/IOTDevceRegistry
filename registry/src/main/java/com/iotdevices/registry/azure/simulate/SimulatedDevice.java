// Copyright (c) Microsoft. All rights reserved.
// Licensed under the MIT license. See LICENSE file in the project root for full license information.

// This application uses the Azure IoT Hub device SDK for Java
// For samples see: https://github.com/Azure/azure-iot-sdk-java/tree/master/device/iot-device-samples

package com.iotdevices.registry.azure.simulate;

import com.microsoft.azure.sdk.iot.device.*;
import com.google.gson.Gson;

import java.io.*;
import java.net.URISyntaxException;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;

public class SimulatedDevice {
  // The device connection string to authenticate the device with your IoT hub.
  // Using the Azure CLI:
  // az iot hub device-identity show-connection-string --hub-name {YourIoTHubName} --device-id MyJavaDevice --output table
  private String connString = "HostName=TMPOC.azure-devices.net;DeviceId=MyDevice2;SharedAccessKey=BSAN0+ENBDTaGkQRFYtdML58Q8JGTv9Aq41fjbh+hgw="
  		+ "";

  // Using the MQTT protocol to connect to IoT Hub
  private static IotHubClientProtocol protocol = IotHubClientProtocol.MQTT_WS;
  private static DeviceClient client;
  private String messageStr="";
  // Specify the telemetry to send to your IoT hub.
  private static class TelemetryDataPoint {
     

    
    // Serialize object to JSON format.
    public String serialize() {
      Gson gson = new Gson();
      return gson.toJson(this);
    }
  }

  // Print the acknowledgement received from IoT Hub for the telemetry message sent.
  private static class EventCallback implements IotHubEventCallback {
    public void execute(IotHubStatusCode status, Object context) {
      System.out.println("IoT Hub responded to message with status: " + status.toString());

      if (context != null) {
        synchronized (context) {
          context.notify();
        }
      }
    }
  }

  private  class MessageSender implements Runnable {
    public void run() {
      try {
        // Initialize the simulated telemetry.
        double minTemperature = 20;
        double minHumidity = 60;
        Random rand = new Random();

        while (true) {
          // Simulate telemetry.
         
          Message msg = new Message(messageStr);

          // Add a custom application property to the message.
          // An IoT hub can filter on these properties without access to the message body.

          System.out.println("Sending message: " + messageStr);

          Object lockobj = new Object();

          // Send the message.
          EventCallback callback = new EventCallback();
          client.sendEventAsync(msg, callback, lockobj);

          synchronized (lockobj) {
            lockobj.wait();
          }
          Thread.sleep(1000);
        }
      } catch (InterruptedException e) {
        System.out.println("Finished.");
      }
    }
  }

  public  void run(String message) throws IOException, URISyntaxException {

    // Connect to the IoT hub.
    client = new DeviceClient(connString, protocol);
    client.open();
    this.messageStr=message ;
    // Create new thread and start sending messages 
   
    MessageSender sender = new MessageSender();
    ExecutorService executor = Executors.newFixedThreadPool(1);
    executor.execute(sender);

    // Stop the application.
    
  }
  
  
  public void closeMessaging()throws IOException, URISyntaxException
  {
	  ExecutorService executor = Executors.newFixedThreadPool(1);
	    executor.shutdownNow();
	    client.closeNow();
	  
  }
  
  
  
}
