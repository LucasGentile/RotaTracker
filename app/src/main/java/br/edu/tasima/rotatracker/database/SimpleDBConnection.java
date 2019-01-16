package br.edu.tasima.rotatracker.database;

import com.amazonaws.auth.CognitoCachingCredentialsProvider;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.simpledb.AmazonSimpleDB;
import com.amazonaws.services.simpledb.AmazonSimpleDBClient;

import br.edu.tasima.rotatracker.RotaTrackerApp;

public class SimpleDBConnection {
    public static AmazonSimpleDB awsSimpleDB;

    // 1. Get Simple DB connection.
    public static AmazonSimpleDB getAwsSimpleDB() {
        if (awsSimpleDB == null) {
            CognitoCachingCredentialsProvider credentialsProvider = new CognitoCachingCredentialsProvider(
                    RotaTrackerApp.getAppContext(),
                    "us-east-1:07383333-6da7-4ed8-8292-7c0c411aecc3", // Identity pool ID
                    Regions.US_EAST_1 // Region
            );

            awsSimpleDB = new AmazonSimpleDBClient(credentialsProvider);
        }
        return awsSimpleDB;
    }
}