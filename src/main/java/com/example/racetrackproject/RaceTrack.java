package com.example.racetrackproject;


import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Random;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class RaceTrack extends Application {
    public int sceneWidth = 500;
    public int sceneHeight = 200;
    public void start(Stage stage) throws IOException {
        String pathToImage = "/home/gson/Downloads/racing.jpg";
        //creating the image object
        InputStream stream = new FileInputStream(pathToImage);
        InputStream stream2 = new FileInputStream(pathToImage);
        InputStream stream3 = new FileInputStream(pathToImage);

        Image image = new Image(stream);
        Image image2 = new Image(stream2);
        Image image3 = new Image(stream3);

        //Creating the image view
        ImageView imageView = new ImageView();
        ImageView imageView2 = new ImageView();
        ImageView imageView3 = new ImageView();

        //Setting image to the image view
        imageView.setImage(image);
        imageView2.setImage(image2);
        imageView3.setImage(image3);


        MultithreadClass MT = new MultithreadClass(imageView);
        MultithreadClass MT2 = new MultithreadClass(imageView2);
        MultithreadClass MT3 = new MultithreadClass(imageView3);

        imageView.setY(60);
        imageView2.setY(110);
        imageView3.setY(160);

        // Creating the Start button
        Button startButton = new Button("Start");
        startButton.setTranslateX(80);
        startButton.setTranslateY(20);
        startButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                MT.start();
                MT2.start();
                MT3.start();
            }
        });

        // Creating the Pause button
        Button pauseButton = new Button("Pause");
        pauseButton.setTranslateX(210);
        pauseButton.setTranslateY(20);
        pauseButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if(MT.isAlive() || MT2.isAlive() || MT3.isAlive()) {
                    MT.setStop(true);
                    MT2.setStop(true);
                    MT3.setStop(true);
                    try {
                        MT.join();
                        MT2.join();
                        MT3.join();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        // Creating the Reset button
        Button resetButton = new Button("Reset");
        resetButton.setTranslateX(330);
        resetButton.setTranslateY(20);
        resetButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                imageView.setX(0);
                imageView2.setX(0);
                imageView3.setX(0);
            }
        });

        imageView.setPreserveRatio(true);
        imageView2.setPreserveRatio(true);
        imageView3.setPreserveRatio(true);

        //Setting the Scene object
        Group root = new Group(startButton, pauseButton, resetButton, imageView, imageView2, imageView3);
        Scene scene = new Scene(root, sceneWidth, sceneHeight);


        stage.setTitle("Richmond Raceway");
        stage.setScene(scene);
        stage.show();
    }



    public static void main(String args[]) {
        launch(args);
    }


    public class MultithreadClass extends Thread {
        public ImageView imgview;
        public MultithreadClass(ImageView I) {
            this.imgview = I;
        }
        public int advanceX;
        public int initialX = 0;
        Random random = new Random();
        private boolean stop = false;
        private boolean end = false;

        public void setStop(boolean stop) {
            this.stop = stop;
        }

        public void winnerFound() {
            this.end = true;
        }

        @Override
        public void run() {
            while (imgview.getX() <= 400 && !end) {
                if (imgview.getX() >= 397) {
                    System.out.println("Winner found " + Thread.currentThread().getName());
                    winnerFound();
                }
                if (stop) {
                    break;
                }

                advanceX = initialX + random.nextInt(10);
                Platform.runLater(() -> imgview.setX(advanceX));
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                initialX = advanceX;
            }
        }
    }


}
