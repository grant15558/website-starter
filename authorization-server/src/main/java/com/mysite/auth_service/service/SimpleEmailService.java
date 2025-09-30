package com.mysite.auth_service.service;

import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;
import software.amazon.awssdk.auth.credentials.AwsSessionCredentials;
import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.ses.SesClient;
import software.amazon.awssdk.services.ses.model.Content;
import software.amazon.awssdk.services.ses.model.Destination;
import software.amazon.awssdk.services.ses.model.Message;
import software.amazon.awssdk.services.ses.model.Body;
import software.amazon.awssdk.services.ses.model.SendEmailRequest;
import software.amazon.awssdk.services.ses.model.SesException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.mysite.auth_service.configuration.exceptions.AuthApiException;

import jakarta.mail.MessagingException;
import jakarta.validation.constraints.NotNull;
import lombok.NonNull;

@Service
public class SimpleEmailService {

        public SimpleEmailService() {
        }

        public void sendEmail(String emailAddress, String subject, @NonNull @NotNull String body)
                        throws AuthApiException {

                String sender = "no-reply@mysite.com";
                String recipient = emailAddress;
                subject = subject.isEmpty() ? "Default Subject" : subject;
                Region region = Region.US_EAST_1;
                SesClient client;
                AwsCredentialsProvider awsCredentialsProvider;
                try {
                        awsCredentialsProvider = DefaultCredentialsProvider.create();

                        // Initialize SES client using the automatic credentials provider
                        client = SesClient.builder()
                                        .region(region)
                                        .credentialsProvider(awsCredentialsProvider)
                                        .build();
                } catch (Exception unsupportedOperationException) {

                        // // Check if sessionToken is defined (not null or empty)
                        // if (sessionToken != null && !sessionToken.isEmpty()) {

                        //         // Development environment, use temporary credentials (session token)
                        //         AwsSessionCredentials sessionCredentials = AwsSessionCredentials.create(
                        //                         keyId,
                        //                         key,
                        //                         sessionToken);
                        //         awsCredentialsProvider = StaticCredentialsProvider.create(sessionCredentials);

                        // } else {
                        //         // Production environment, use permanent service account credentials
                        //         AwsBasicCredentials basicCredentials = AwsBasicCredentials.create(
                        //                         keyId,
                        //                         key);
                        //         awsCredentialsProvider = StaticCredentialsProvider.create(basicCredentials);

                        // }

                        // if (awsCredentialsProvider != null) {
                        //         client = SesClient.builder()
                        //                         .region(region)
                        //                         .credentialsProvider(awsCredentialsProvider)
                        //                         .build();
                        // }
                        throw new AuthApiException("SES cannot connect, bad credentials.");

                }

                try {
                        send(client, sender, recipient, subject, body);
                        client.close();

                } catch (MessagingException e) {
                        throw new AuthApiException("SES cannot connect, bad credentials.");

                }
                
        }

        private void send(
                        SesClient client,
                        String sender,
                        String recipient,
                        String subject,
                        String bodyHTML) throws MessagingException {

                Destination destination = Destination.builder()
                                .toAddresses(recipient)
                                .build();

                Content content = Content.builder()
                                .data(bodyHTML)
                                .build();

                Content sub = Content.builder()
                                .data(subject)
                                .build();

                Body body = Body.builder()
                                .html(content)
                                .build();

                Message msg = Message.builder()
                                .subject(sub)
                                .body(body)
                                .build();

                SendEmailRequest emailRequest = SendEmailRequest.builder()
                                .destination(destination)
                                .message(msg)
                                .source(sender)
                                .build();
                client.sendEmail(emailRequest);

        }
}
