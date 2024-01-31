package com.vis.monitor.current.server.details;

import java.nio.file.Path;
import java.nio.file.Paths;

public class CurrentServer {

    public static void main(String[] args) {
       
    	checkingTomcat();
    }	
    	
    public static void checkingTomcat () {
        Path recordingFile = Paths.get("tomcat_monitor.jfr");

        // Start JFR recording
        startJFRRecording(recordingFile);

        try {
            // Simulate some workload on the Tomcat application
            performWorkload();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            // Stop JFR recording
            stopJFRRecording();
        }
    }

    private static void startJFRRecording(Path recordingFile) {
        // Start JFR recording with specified settings
        String startRecordingCommand = "jcmd <4674> JFR.start name=TomcatRecording settings=default duration=30s filename=" + recordingFile;
        executeCommand(startRecordingCommand);
    }

    private static void stopJFRRecording() {
        // Stop JFR recording
        String stopRecordingCommand = "jcmd <4674> JFR.stop name=TomcatRecording";
        executeCommand(stopRecordingCommand);
    }

    private static void executeCommand(String command) {
        try {
            Process process = Runtime.getRuntime().exec(command);
            int exitCode = process.waitFor();
            if (exitCode != 0) {
                System.err.println("Error executing command: " + command);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void performWorkload() throws InterruptedException {
        // Simulate some workload (e.g., processing requests, handling tasks)
        for (int i = 0; i < 10; i++) {
            System.out.println("Processing request " + i);
            Thread.sleep(1000);
        }
    }
}