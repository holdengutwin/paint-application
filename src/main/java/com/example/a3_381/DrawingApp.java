package com.example.a3_381;

import javafx.application.Application;
import javafx.scene.Scene;

import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class DrawingApp extends Application {

    public void start(Stage stage) {

        DrawingController controller = new DrawingController();
        DrawingModel model = new DrawingModel();
        InteractionModel imodel = new InteractionModel();
        MiniDrawingController miniController = new MiniDrawingController();

        MainUI mainUI = new MainUI();
        model.addSubscriber(mainUI);
        imodel.addSubscriber(mainUI);
        controller.addModel(model);
        controller.addInteractionModel(imodel);

        miniController.addModel(model);
        miniController.addInteractionModel(imodel);

        mainUI.setController(controller);
        mainUI.setMiniController(miniController);

        mainUI.setModel(model);
        mainUI.setInteractionModel(imodel);

        imodel.notifySubscribers();
        Scene scene = new Scene(mainUI);


        stage.setScene(scene);
        stage.setTitle("Assignment 3: 2D Graphics, MVC, Multiple Views");
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
