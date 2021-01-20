// Copyright (c) Microsoft. All rights reserved.
// Licensed under the MIT license. See LICENSE file in the project root for full license information.

package com.iotdevices.registry.azure;

import com.microsoft.azure.sdk.iot.provisioning.service.ProvisioningServiceClient;
import com.microsoft.azure.sdk.iot.provisioning.service.Query;
import com.microsoft.azure.sdk.iot.provisioning.service.configs.*;
import com.microsoft.azure.sdk.iot.provisioning.service.exceptions.ProvisioningServiceClientException;

/**
 * Create, get, query, and delete an individual enrollment on the Microsoft Azure IoT Hub Device Provisioning Service
 */
public class ServiceEnrollmentSample
{
    /*
     * Details of the Provisioning.
     */
	private static final String PROVISIONING_CONNECTION_STRING = "HostName=TMPOCDPS.azure-devices-provisioning.net;SharedAccessKeyName=provisioningserviceowner;SharedAccessKey=+/2ffE09ySRBQ7cQwLx4MbSMyEV8F6dW+cD3oPFnxuQ=";

    private static final String REGISTRATION_ID = "kaz2wx2ismgmhx4e7gahwysxs5grpkqtdvjfymh6gyyc3lvgvw2q";
    private static final String TPM_ENDORSEMENT_KEY = "AToAAQALAAMAsgAgg3GXZ0SEs/gakMyNRqXXJP1S124GUgtk8qHaGzMUaaoABgCAAEMAEAgAAAAAAAEAoj2EAcnJbfXnxh/0s6FfuDkaD7Vh9A0GFz3odfVVgqJoLGNi+THnjr1fy5vnKQ7np0sBFE64qbQE5WFCU0Weqi+iLSSxAJjwiRgB8nuC336Ya/pLr6ZYGPbi7vhvRaimlK0rgVBIyRuB7e/GvG9b2Fyt0XYNG6dKmrrKE4MJtVjkoS/KpKCA6Km86+DvtH21k+E6mZ3I6mtEeiVdDsAAKgM6ghuZBYagEsLm7QZRe5hjjnmUtN9kxdthHen9HbxHmmMayUfE70PRZ2t4WXnuAN0JN7K5vkhySgLIyQCTDXCw3OVKI7k1svrKAD77WW3+gQ4qmR+oHqe8q1f4MJh1bw==";
    // Optional parameters
    private static final String IOTHUB_HOST_NAME = "TMPOC.azure-devices.net";
    private static final String DEVICE_ID = "myJavaDevice";
    private static final ProvisioningStatus PROVISIONING_STATUS = ProvisioningStatus.ENABLED;

    public static void main(String[] args) throws ProvisioningServiceClientException
    {
        System.out.println("Starting sample...");

        // *********************************** Create a Provisioning Service Client ************************************
        ProvisioningServiceClient provisioningServiceClient =
                ProvisioningServiceClient.createFromConnectionString(PROVISIONING_CONNECTION_STRING);

        // ******************************** Create a new individualEnrollment config **********************************
        System.out.println("\nCreate a new individualEnrollment...");
        Attestation attestation = new TpmAttestation(TPM_ENDORSEMENT_KEY);
        
        IndividualEnrollment individualEnrollment =
                new IndividualEnrollment(
                        REGISTRATION_ID,
                        attestation);

        // The following parameters are optional. Remove it if you don't need.
        individualEnrollment.setDeviceIdFinal(DEVICE_ID);
        individualEnrollment.setIotHubHostNameFinal(IOTHUB_HOST_NAME);
        individualEnrollment.setProvisioningStatusFinal(PROVISIONING_STATUS);

        // ************************************ Create the individualEnrollment *************************************
        System.out.println("\nAdd new individualEnrollment...");
        IndividualEnrollment individualEnrollmentResult =  provisioningServiceClient.createOrUpdateIndividualEnrollment(individualEnrollment);
        System.out.println("\nIndividualEnrollment created with success...");
        System.out.println(individualEnrollmentResult);

        // ************************************* Get info of individualEnrollment *************************************
        System.out.println("\nGet the individualEnrollment information...");
        IndividualEnrollment getResult = provisioningServiceClient.getIndividualEnrollment(REGISTRATION_ID);
        System.out.println(getResult);

        // ************************************ Query info of individualEnrollment ************************************
        System.out.println("\nCreate a query for enrollments...");
        QuerySpecification querySpecification =
                new QuerySpecificationBuilder("*", QuerySpecificationBuilder.FromType.ENROLLMENTS)
                        .createSqlQuery();
        Query query = provisioningServiceClient.createIndividualEnrollmentQuery(querySpecification);

        while(query.hasNext())
        {
            System.out.println("\nQuery the next enrollments...");
            QueryResult queryResult = query.next();
            System.out.println(queryResult);
        }

        // *********************************** Delete info of individualEnrollment ************************************
        System.out.println("\nDelete the individualEnrollment...");
        provisioningServiceClient.deleteIndividualEnrollment(REGISTRATION_ID);
    }
}
