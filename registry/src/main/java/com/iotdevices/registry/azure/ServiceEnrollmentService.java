package com.iotdevices.registry.azure;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.iotdevices.registry.service.DeviceInfo;
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

	private static final String PROVISIONING_CONNECTION_STRING = "HostName=MyDeviceProvisioningService1.azure-devices-provisioning.net;SharedAccessKeyName=provisioningserviceowner;SharedAccessKey=wD/DST5qDrh/wyGrRb5FcchbMx97kXLAMHtbGFVDFJU=";


    private static final String TPM_ENDORSEMENT_KEY = "AToAAQALAAMAsgAgg3GXZ0SEs/gakMyNRqXXJP1S124GUgtk8qHaGzMUaaoABgCAAEMAEAgAAAAAAAEAxsj2gUS" +
            "cTk1UjuioeTlfGYZrrimExB+bScH75adUMRIi2UOMxG1kw4y+9RW/IVoMl4e620VxZad0ARX2gUqVjYO7KPVt3d" +
            "yKhZS3dkcvfBisBhP1XH9B33VqHG9SHnbnQXdBUaCgKAfxome8UmBKfe+naTsE5fkvjb/do3/dD6l4sGBwFCnKR" +
            "dln4XpM03zLpoHFao8zOwt8l/uP3qUIxmCYv9A7m69Ms+5/pCkTu/rK4mRDsfhZ0QLfbzVI6zQFOKF/rwsfBtFe" +
            "WlWtcuJMKlXdD8TXWElTzgh7JS4qhFzreL0c1mI0GCj+Aws0usZh7dLIVPnlgZcBhgy1SSDQMQ==";
    // Optional parameters
    private static final String IOTHUB_HOST_NAME = "PavanIOTHub.azure-devices.net";
    private static final ProvisioningStatus PROVISIONING_STATUS = ProvisioningStatus.ENABLED;
    private ProvisioningServiceClient provisioningServiceClient =
            ProvisioningServiceClient.createFromConnectionString(PROVISIONING_CONNECTION_STRING);
    
    public IndividualEnrollment createEnrollment(DeviceInfo deviceInfo) throws ProvisioningServiceClientException
    {
    	System.out.println("Starting sample...");

        // *********************************** Create a Provisioning Service Client ************************************
       

        // ******************************** Create a new individualEnrollment config **********************************
        System.out.println("\nCreate a new individualEnrollment...");
        Attestation attestation = new TpmAttestation(TPM_ENDORSEMENT_KEY);
        
        IndividualEnrollment individualEnrollment =
                new IndividualEnrollment(
                		deviceInfo.getName(),
                        attestation);

        // The following parameters are optional. Remove it if you don't need.
        individualEnrollment.setDeviceIdFinal(deviceInfo.getDescription());
        individualEnrollment.setIotHubHostNameFinal(IOTHUB_HOST_NAME);
        individualEnrollment.setProvisioningStatusFinal(PROVISIONING_STATUS);

        // ************************************ Create the individualEnrollment *************************************
        System.out.println("\nAdd new individualEnrollment...");
        IndividualEnrollment individualEnrollmentResult =  provisioningServiceClient.createOrUpdateIndividualEnrollment(individualEnrollment);
        System.out.println("\nIndividualEnrollment created with success...");
        System.out.println(individualEnrollmentResult);
    	return individualEnrollmentResult;
    	
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
