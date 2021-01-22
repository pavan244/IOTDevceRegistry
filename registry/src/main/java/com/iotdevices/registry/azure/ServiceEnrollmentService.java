package com.iotdevices.registry.azure;

import static org.apache.commons.codec.binary.Base64.encodeBase64;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.iotdevices.registry.config.IotEntityConfig;
import com.iotdevices.registry.service.DeviceInfo;
import com.microsoft.azure.sdk.iot.device.DeviceClient;
import com.microsoft.azure.sdk.iot.device.IotHubClientProtocol;
import com.microsoft.azure.sdk.iot.device.IotHubEventCallback;
import com.microsoft.azure.sdk.iot.device.IotHubStatusCode;
import com.microsoft.azure.sdk.iot.provisioning.device.ProvisioningDeviceClient;
import com.microsoft.azure.sdk.iot.provisioning.device.ProvisioningDeviceClientRegistrationCallback;
import com.microsoft.azure.sdk.iot.provisioning.device.ProvisioningDeviceClientRegistrationResult;
import com.microsoft.azure.sdk.iot.provisioning.device.ProvisioningDeviceClientStatus;
import com.microsoft.azure.sdk.iot.provisioning.device.ProvisioningDeviceClientTransportProtocol;
import com.microsoft.azure.sdk.iot.provisioning.security.SecurityProviderTpm;
import com.microsoft.azure.sdk.iot.provisioning.security.exceptions.SecurityProviderException;
import com.microsoft.azure.sdk.iot.provisioning.security.hsm.SecurityProviderTPMEmulator;
import com.microsoft.azure.sdk.iot.provisioning.service.ProvisioningServiceClient;
import com.microsoft.azure.sdk.iot.provisioning.service.Query;
import com.microsoft.azure.sdk.iot.provisioning.service.configs.Attestation;
import com.microsoft.azure.sdk.iot.provisioning.service.configs.IndividualEnrollment;
import com.microsoft.azure.sdk.iot.provisioning.service.configs.ProvisioningStatus;
import com.microsoft.azure.sdk.iot.provisioning.service.configs.QueryResult;
import com.microsoft.azure.sdk.iot.provisioning.service.configs.QuerySpecification;
import com.microsoft.azure.sdk.iot.provisioning.service.configs.QuerySpecificationBuilder;
import com.microsoft.azure.sdk.iot.provisioning.service.configs.TpmAttestation;
import com.microsoft.azure.sdk.iot.provisioning.service.exceptions.ProvisioningServiceClientException;



@Component
public class ServiceEnrollmentService {

	// TPM provisioning starts 
	  
	@Autowired
	private IotEntityConfig iotconfig;
	
	  ProvisioningDeviceClient provisioningDeviceClient;
	  
	  
	static class TPMProvisioningStatus
    {
        ProvisioningDeviceClientRegistrationResult provisioningDeviceClientRegistrationInfoClient = new ProvisioningDeviceClientRegistrationResult();
        Exception exception;
    }

    static class ProvisioningDeviceClientRegistrationCallbackImpl implements ProvisioningDeviceClientRegistrationCallback
    {
        @Override
        public void run(ProvisioningDeviceClientRegistrationResult provisioningDeviceClientRegistrationResult, Exception exception, Object context)
        {
            if (context instanceof TPMProvisioningStatus)
            {
            	TPMProvisioningStatus status = (TPMProvisioningStatus) context;
                status.provisioningDeviceClientRegistrationInfoClient = provisioningDeviceClientRegistrationResult;
                status.exception = exception;
            }
            else
            {
                System.out.println("Received unknown context");
            }
        }
    }

    private static class IotHubEventCallbackImpl implements IotHubEventCallback
    {
        @Override
        public void execute(IotHubStatusCode responseStatus, Object callbackContext)
        {
            System.out.println("Message received! Response status: " + responseStatus);
        }
    }
	
    private static final String SCOPE_ID = "0ne001FB6A7";
    private static final String GLOBAL_ENDPOINT = "global.azure-devices-provisioning.net";
    private static final ProvisioningDeviceClientTransportProtocol PROVISIONING_DEVICE_CLIENT_TRANSPORT_PROTOCOL = ProvisioningDeviceClientTransportProtocol.HTTPS;
    private static final int MAX_TIME_TO_WAIT_FOR_REGISTRATION = 10000; 
   
 // TPM provisioning ends 
	
	
	
	
	
	
	//HostName=TMPOCDPS.azure-devices-provisioning.net;SharedAccessKeyName=provisioningserviceowner;SharedAccessKey=+/2ffE09ySRBQ7cQwLx4MbSMyEV8F6dW+cD3oPFnxuQ=
	private static final String PROVISIONING_CONNECTION_STRING = "HostName=TMPOCDPS.azure-devices-provisioning.net;SharedAccessKeyName=provisioningserviceowner;SharedAccessKey=+/2ffE09ySRBQ7cQwLx4MbSMyEV8F6dW+cD3oPFnxuQ=";


  //  private  String TPM_ENDORSEMENT_KEY = "AToAAQALAAMAsgAgg3GXZ0SEs/gakMyNRqXXJP1S124GUgtk8qHaGzMUaaoABgCAAEMAEAgAAAAAAAEAoj2EAcnJbfXnxh/0s6FfuDkaD7Vh9A0GFz3odfVVgqJoLGNi+THnjr1fy5vnKQ7np0sBFE64qbQE5WFCU0Weqi+iLSSxAJjwiRgB8nuC336Ya/pLr6ZYGPbi7vhvRaimlK0rgVBIyRuB7e/GvG9b2Fyt0XYNG6dKmrrKE4MJtVjkoS/KpKCA6Km86+DvtH21k+E6mZ3I6mtEeiVdDsAAKgM6ghuZBYagEsLm7QZRe5hjjnmUtN9kxdthHen9HbxHmmMayUfE70PRZ2t4WXnuAN0JN7K5vkhySgLIyQCTDXCw3OVKI7k1svrKAD77WW3+gQ4qmR+oHqe8q1f4MJh1bw=="; 
    		
    // Optional parameters
    private static final String IOTHUB_HOST_NAME = "TMPOC.azure-devices.net";
    private static final ProvisioningStatus PROVISIONING_STATUS = ProvisioningStatus.ENABLED;
    private ProvisioningServiceClient provisioningServiceClient =
            ProvisioningServiceClient.createFromConnectionString(PROVISIONING_CONNECTION_STRING);
    
   
    
    
    public DeviceInfo createEnrollment(DeviceInfo deviceInfo,SecurityProviderTPMEmulatorMyImpl securityProviderImpl) throws ProvisioningServiceClientException, SecurityProviderException
    {
    	System.out.println("Starting sample...");
    
    
        // *********************************** Create a Provisioning Service Client ************************************
    
    	securityProviderImpl.setRegistrationId(deviceInfo.getId());
    
        // ******************************** Create a new individualEnrollment config **********************************
        System.out.println("\nCreate a new individualEnrollment...");
        String s = new String(encodeBase64(securityProviderImpl.getEndorsementKey()));
        System.out.println(s);
        Attestation attestation = new TpmAttestation(s);
        
        IndividualEnrollment individualEnrollment =
                new IndividualEnrollment(
                		securityProviderImpl.getRegistrationId(),
                         attestation);

        // The following parameters are optional. Remove it if you don't need.
        individualEnrollment.setDeviceIdFinal(deviceInfo.getName());
        individualEnrollment.setIotHubHostNameFinal(IOTHUB_HOST_NAME);
        individualEnrollment.setProvisioningStatusFinal(PROVISIONING_STATUS);
        
        
  
        // ************************************ Create the individualEnrollment *************************************
        System.out.println("\nAdd new individualEnrollment...");
        IndividualEnrollment individualEnrollmentResult =  provisioningServiceClient.createOrUpdateIndividualEnrollment(individualEnrollment);
        System.out.println("\nIndividualEnrollment created with success...");
        System.out.println(individualEnrollmentResult);
        TPMProvisioningStatus provisioningStatus = new TPMProvisioningStatus();
   
      
        try {
        	if( this.provisioningDeviceClient==null)
        	{
    	        this.provisioningDeviceClient = ProvisioningDeviceClient.create(GLOBAL_ENDPOINT, SCOPE_ID, PROVISIONING_DEVICE_CLIENT_TRANSPORT_PROTOCOL, securityProviderImpl);
    	    }
        	provisioningDeviceClient.registerDevice(new ProvisioningDeviceClientRegistrationCallbackImpl(), provisioningStatus);
           while (provisioningStatus.provisioningDeviceClientRegistrationInfoClient.getProvisioningDeviceClientStatus() != ProvisioningDeviceClientStatus.PROVISIONING_DEVICE_STATUS_ASSIGNED)
           {
               if (provisioningStatus.provisioningDeviceClientRegistrationInfoClient.getProvisioningDeviceClientStatus() == ProvisioningDeviceClientStatus.PROVISIONING_DEVICE_STATUS_ERROR ||
                       provisioningStatus.provisioningDeviceClientRegistrationInfoClient.getProvisioningDeviceClientStatus() == ProvisioningDeviceClientStatus.PROVISIONING_DEVICE_STATUS_DISABLED ||
                       provisioningStatus.provisioningDeviceClientRegistrationInfoClient.getProvisioningDeviceClientStatus() == ProvisioningDeviceClientStatus.PROVISIONING_DEVICE_STATUS_FAILED)
               {
                   provisioningStatus.exception.printStackTrace();
                   
                   System.out.println("Registration error, bailing out");
                   break;
               }
               Thread.sleep(MAX_TIME_TO_WAIT_FOR_REGISTRATION);
               iotconfig.insertDeviceMock(deviceInfo);
             //   provisioningDeviceClient.closeNow();
              
          
               break;
              }
          
	      } 
       catch (Exception e) {
		// TODO: handle exception
    	   if(provisioningDeviceClient!=null)
    	   {
    		   provisioningDeviceClient.closeNow();
    	   }
    	   System.out.println(e.toString());
	     }
       
        
        // provisioning ends
        
    	return deviceInfo;
    	
    }
	
	public IndividualEnrollment getIndividualDeviceInfo(String deviceId) 
	{
		IndividualEnrollment getResult=null;
		try {
			 System.out.println("\nGet the individualEnrollment information...");
	         getResult = provisioningServiceClient.getIndividualEnrollment(deviceId);
	        System.out.println(getResult);
	        return getResult;
		}  catch (Exception e) {
			// TODO: handle finally clause
			return getResult;
		}
		
		
		
	}
	
	public List<QueryResult> getAllIndividualEnrollments()
	{
		ArrayList<QueryResult> result = new ArrayList();
		QuerySpecification querySpecification =
                new QuerySpecificationBuilder("*", QuerySpecificationBuilder.FromType.ENROLLMENTS)
                        .createSqlQuery();
        Query query = provisioningServiceClient.createIndividualEnrollmentQuery(querySpecification);

        while(query.hasNext())
        {
            System.out.println("\nQuery the next enrollments...");
            QueryResult queryResult = query.next();
            Object obj[]  =  queryResult.getItems();
            for(Object o:obj)
            {
            	IndividualEnrollment ieObj = (IndividualEnrollment)o;
            	
            }
            result.add(queryResult);
            System.out.println(queryResult);
        }
		return result;
	}
	
	public boolean deleteAnEnrollment(String registrationId)
	{
		try {
			 System.out.println("\nDelete the individualEnrollment...");
		        provisioningServiceClient.deleteIndividualEnrollment(registrationId);
		        return true;
		} catch (Exception e) {
			// TODO: handle exception
			return false;
		}
		
	}
	
	
}
