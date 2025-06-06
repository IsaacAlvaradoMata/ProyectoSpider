package cr.ac.una.proyectospider.util;

import javafx.animation.FadeTransition;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.util.Duration;

import java.util.function.Consumer;

public class CustomAlert {

    public static void showInfo(StackPane parent, String titulo, String mensaje, Runnable onClose) {
        Pane overlay = new Pane();
        overlay.setStyle("-fx-background-color: rgba(0,0,0,0.5);");
        overlay.prefWidthProperty().bind(parent.widthProperty());
        overlay.prefHeightProperty().bind(parent.heightProperty());
        overlay.setPickOnBounds(true);
        overlay.setMouseTransparent(false);

        VBox dialog = new VBox(20);
        dialog.setAlignment(Pos.CENTER);
        dialog.setMaxWidth(600);
        dialog.setMinHeight(Region.USE_PREF_SIZE);
        dialog.setPrefHeight(Region.USE_COMPUTED_SIZE);
        dialog.setMaxHeight(300);
        dialog.getStyleClass().add("vbox-alerta");


        Label lblTitulo = new Label(titulo);
        lblTitulo.setTextFill(Color.web("#FFD700"));
        lblTitulo.setFont(Font.font("Cynatar", FontWeight.EXTRA_BOLD, 40));
        lblTitulo.setWrapText(true);
        lblTitulo.getStyleClass().add("label-alerta");


        Label lblMensaje = new Label(mensaje);
        lblMensaje.setTextFill(Color.web("#00fff7"));
        lblMensaje.setFont(Font.font("Cynatar", FontWeight.NORMAL, 26));
        lblMensaje.setWrapText(true);
        lblMensaje.setMaxWidth(450);
        lblMensaje.setAlignment(Pos.CENTER);
        lblMensaje.getStyleClass().add("label-alerta");

        Button btnOk = new Button("Aceptar");
        btnOk.setPrefWidth(120);
        btnOk.setFont(Font.font("Cynatar", FontWeight.BOLD, 16));
        btnOk.getStyleClass().add("botones-alerta");

        btnOk.addEventHandler(MouseEvent.MOUSE_ENTERED, e -> btnOk.setEffect(
                new DropShadow(15, Color.web("#ff65ff"))
        ));
        btnOk.addEventHandler(MouseEvent.MOUSE_EXITED, e -> btnOk.setEffect(null));

        dialog.getChildren().addAll(lblTitulo, lblMensaje, btnOk);

        parent.getChildren().addAll(overlay, dialog);

        FadeTransition fadeInOv = new FadeTransition(Duration.seconds(0.25), overlay);
        fadeInOv.setFromValue(0);
        fadeInOv.setToValue(1);
        fadeInOv.play();
        FadeTransition fadeInDlg = new FadeTransition(Duration.seconds(0.25), dialog);
        fadeInDlg.setFromValue(0);
        fadeInDlg.setToValue(1);
        fadeInDlg.play();

        btnOk.setOnAction(evt -> {
            FadeTransition fadeOutDlg = new FadeTransition(Duration.seconds(0.2), dialog);
            fadeOutDlg.setFromValue(1);
            fadeOutDlg.setToValue(0);
            FadeTransition fadeOutOv2 = new FadeTransition(Duration.seconds(0.2), overlay);
            fadeOutOv2.setFromValue(1);
            fadeOutOv2.setToValue(0);

            fadeOutOv2.setOnFinished(e2 -> {
                parent.getChildren().removeAll(overlay, dialog);
                if (onClose != null) onClose.run();
            });

            fadeOutDlg.play();
            fadeOutOv2.play();
        });
    }


    public static void showConfirmation(StackPane parent, String titulo, String mensaje, Consumer<Boolean> callback) {
        Pane overlay = new Pane();
        overlay.setStyle("-fx-background-color: rgba(0,0,0,0.5);");
        overlay.prefWidthProperty().bind(parent.widthProperty());
        overlay.prefHeightProperty().bind(parent.heightProperty());
        overlay.setPickOnBounds(true);
        overlay.setMouseTransparent(false);

        VBox dialog = new VBox(20);
        dialog.setAlignment(Pos.CENTER);
        dialog.setMaxWidth(600);
        dialog.setMinHeight(Region.USE_PREF_SIZE);
        dialog.setPrefHeight(Region.USE_COMPUTED_SIZE);
        dialog.setMaxHeight(300);
        dialog.getStyleClass().add("vbox-alerta");

        Label lblTitulo = new Label(titulo);
        lblTitulo.setTextFill(Color.web("#FFD700"));
        lblTitulo.setFont(Font.font("Cynatar", FontWeight.EXTRA_BOLD, 40));
        lblTitulo.setWrapText(true);
        lblTitulo.getStyleClass().add("label-alerta");

        Label lblMensaje = new Label(mensaje);
        lblMensaje.setTextFill(Color.web("#00fff7"));
        lblMensaje.setFont(Font.font("Cynatar", FontWeight.NORMAL, 26));
        lblMensaje.setWrapText(true);
        lblMensaje.setMaxWidth(450);
        lblMensaje.setAlignment(Pos.CENTER);
        lblMensaje.getStyleClass().add("label-alerta");

        Button btnSi = new Button("Sí");
        btnSi.setPrefWidth(100);
        btnSi.setFont(Font.font("Cynatar", FontWeight.BOLD, 16));
        btnSi.getStyleClass().add("botones-alerta");
        btnSi.addEventHandler(MouseEvent.MOUSE_ENTERED, e -> btnSi.setEffect(
                new DropShadow(15, Color.web("#ff65ff"))
        ));
        btnSi.addEventHandler(MouseEvent.MOUSE_EXITED, e -> btnSi.setEffect(null));

        Button btnNo = new Button("No");
        btnNo.setPrefWidth(100);
        btnNo.setFont(Font.font("Cynatar", FontWeight.BOLD, 16));
        btnNo.getStyleClass().add("botones-alerta");
        btnNo.addEventHandler(MouseEvent.MOUSE_ENTERED, e -> btnNo.setEffect(
                new DropShadow(15, Color.web("#ff65ff"))
        ));
        btnNo.addEventHandler(MouseEvent.MOUSE_EXITED, e -> btnNo.setEffect(null));

        HBox botones = new HBox(20, btnSi, btnNo);
        botones.setAlignment(Pos.CENTER);

        dialog.getChildren().addAll(lblTitulo, lblMensaje, botones);

        parent.getChildren().addAll(overlay, dialog);

        FadeTransition fadeInOv = new FadeTransition(Duration.seconds(0.25), overlay);
        fadeInOv.setFromValue(0);
        fadeInOv.setToValue(1);
        fadeInOv.play();
        FadeTransition fadeInDlg = new FadeTransition(Duration.seconds(0.25), dialog);
        fadeInDlg.setFromValue(0);
        fadeInDlg.setToValue(1);
        fadeInDlg.play();

        btnSi.setOnAction(evt -> {
            FadeTransition fadeOutDlg = new FadeTransition(Duration.seconds(0.2), dialog);
            fadeOutDlg.setFromValue(1);
            fadeOutDlg.setToValue(0);
            FadeTransition fadeOutOv2 = new FadeTransition(Duration.seconds(0.2), overlay);
            fadeOutOv2.setFromValue(1);
            fadeOutOv2.setToValue(0);

            fadeOutOv2.setOnFinished(e2 -> {
                parent.getChildren().removeAll(overlay, dialog);
                if (callback != null) callback.accept(true);
            });

            fadeOutDlg.play();
            fadeOutOv2.play();
        });

        btnNo.setOnAction(evt -> {
            FadeTransition fadeOutDlg = new FadeTransition(Duration.seconds(0.2), dialog);
            fadeOutDlg.setFromValue(1);
            fadeOutDlg.setToValue(0);
            FadeTransition fadeOutOv2 = new FadeTransition(Duration.seconds(0.2), overlay);
            fadeOutOv2.setFromValue(1);
            fadeOutOv2.setToValue(0);

            fadeOutOv2.setOnFinished(e2 -> {
                parent.getChildren().removeAll(overlay, dialog);
                if (callback != null) callback.accept(false);
            });

            fadeOutDlg.play();
            fadeOutOv2.play();
        });
    }
}
