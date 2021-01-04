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
    private static final String PROVISIONING_CONNECTION_STRING = "HostName=MyDeviceProvisioningService1.azure-devices-provisioning.net;SharedAccessKeyName=provisioningserviceowner;SharedAccessKey=wD/DST5qDrh/wyGrRb5FcchbMx97kXLAMHtbGFVDFJU=";

    private static final String REGISTRATION_ID = "MyFirstRegistartion";
    private static final String TPM_ENDORSEMENT_KEY = "AToAAQALAAMAsgAgg3GXZ0SEs/gakMyNRqXXJP1S124GUgtk8qHaGzMUaaoABgCAAEMAEAgAAAAAAAEAxsj2gUS" +
            "cTk1UjuioeTlfGYZrrimExB+bScH75adUMRIi2UOMxG1kw4y+9RW/IVoMl4e620VxZad0ARX2gUqVjYO7KPVt3d" +
            "yKhZS3dkcvfBisBhP1XH9B33VqHG9SHnbnQXdBUaCgKAfxome8UmBKfe+naTsE5fkvjb/do3/dD6l4sGBwFCnKR" +
            "dln4XpM03zLpoHFao8zOwt8l/uP3qUIxmCYv9A7m69Ms+5/pCkTu/rK4mRDsfhZ0QLfbzVI6zQFOKF/rwsfBtFe" +
            "WlWtcuJMKlXdD8TXWElTzgh7JS4qhFzreL0c1mI0GCj+Aws0usZh7dLIVPnlgZcBhgy1SSDQMQ==";
    // Optional parameters
    private static final String IOTHUB_HOST_NAME = "PavanIOTHub.azure-devices.net";
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
