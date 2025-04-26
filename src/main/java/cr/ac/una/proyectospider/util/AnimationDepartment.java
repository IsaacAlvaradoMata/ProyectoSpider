package cr.ac.una.proyectospider.util;

import javafx.animation.*;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public class AnimationDepartment {

    public static void fadeIn(Node node, Duration delay) {
        node.setOpacity(0);
        FadeTransition fade = new FadeTransition(Duration.millis(800), node);
        fade.setFromValue(0);
        fade.setToValue(1);
        fade.setDelay(delay);
        fade.play();
    }

    public static void glitchTextWithFlicker(Label label) {
        Timeline glitchCycle = new Timeline();

        glitchCycle.setCycleCount(Animation.INDEFINITE);

        glitchCycle.getKeyFrames().add(new KeyFrame(Duration.seconds(3.5), e -> {
            // Glitch + Flicker
            Timeline glitch = new Timeline(
                    // Primer glitch + flicker
                    new KeyFrame(Duration.seconds(0), ev -> {
                        label.setTranslateX(0);
                        label.setTranslateY(0);
                        label.setOpacity(1);
                        label.setTextFill(Color.web("#ffc107")); // Neon dorado
                    }),
                    new KeyFrame(Duration.seconds(0.05), ev -> {
                        label.setTranslateX(3);
                        label.setTranslateY(-2);
                        label.setOpacity(0.6);
                        label.setTextFill(Color.web("#ff00ff")); // Neon magenta
                    }),
                    new KeyFrame(Duration.seconds(0.1), ev -> {
                        label.setTranslateX(-3);
                        label.setTranslateY(2);
                        label.setOpacity(1);
                        label.setTextFill(Color.web("#ffc107"));
                    }),
                    new KeyFrame(Duration.seconds(0.15), ev -> {
                        label.setTranslateX(0);
                        label.setTranslateY(0);
                        label.setOpacity(1);
                    }),
                    // Segundo glitch + flicker
                    new KeyFrame(Duration.seconds(0.2), ev -> {
                        label.setTranslateX(2);
                        label.setTranslateY(-2);
                        label.setOpacity(0.5);
                        label.setTextFill(Color.web("#00ffff")); // Neon cyan
                    }),
                    new KeyFrame(Duration.seconds(0.25), ev -> {
                        label.setTranslateX(-2);
                        label.setTranslateY(1);
                        label.setOpacity(1);
                        label.setTextFill(Color.web("#ffc107"));
                    }),
                    new KeyFrame(Duration.seconds(0.3), ev -> {
                        label.setTranslateX(0);
                        label.setTranslateY(0);
                        label.setOpacity(1);
                    })
            );

            glitch.play();
        }));

        glitchCycle.play();
    }

    public static void epicLogoAnimation(Node node) {
        node.setOpacity(0);
        node.setScaleX(0.5);
        node.setScaleY(0.5);

        DropShadow glow = new DropShadow(20, Color.web("#ffc107"));
        node.setEffect(glow);

        Timeline timeline = new Timeline(
                new KeyFrame(Duration.seconds(0),
                        new KeyValue(node.opacityProperty(), 0),
                        new KeyValue(node.scaleXProperty(), 0.5),
                        new KeyValue(node.scaleYProperty(), 0.5),
                        new KeyValue(glow.radiusProperty(), 5)
                ),
                new KeyFrame(Duration.seconds(1.5),
                        new KeyValue(node.opacityProperty(), 1),
                        new KeyValue(node.scaleXProperty(), 1.0),
                        new KeyValue(node.scaleYProperty(), 1.0),
                        new KeyValue(glow.radiusProperty(), 30)
                )
        );

        timeline.play();
    }

    public static void titleSplitEntrance(VBox leftLabel, VBox rightLabel, double delayInSeconds) {
        leftLabel.setOpacity(0);
        rightLabel.setOpacity(0);

        leftLabel.setTranslateX(-600); // Desde la izquierda
        rightLabel.setTranslateX(600); // Desde la derecha

        leftLabel.setScaleX(1.0);
        leftLabel.setScaleY(1.0);
        rightLabel.setScaleX(1.0);
        rightLabel.setScaleY(1.0);

        Timeline entrance = new Timeline(
                new KeyFrame(Duration.seconds(delayInSeconds),
                        new KeyValue(leftLabel.opacityProperty(), 0),
                        new KeyValue(rightLabel.opacityProperty(), 0)
                ),
                new KeyFrame(Duration.seconds(delayInSeconds + 0.5),
                        new KeyValue(leftLabel.opacityProperty(), 1),
                        new KeyValue(rightLabel.opacityProperty(), 1)
                ),
                new KeyFrame(Duration.seconds(delayInSeconds + 1.5),
                        new KeyValue(leftLabel.translateXProperty(), 0, Interpolator.EASE_BOTH),
                        new KeyValue(rightLabel.translateXProperty(), 0, Interpolator.EASE_BOTH)
                ),
                // Zoom justo al colisionar
                new KeyFrame(Duration.seconds(delayInSeconds + 1.6),
                        new KeyValue(leftLabel.scaleXProperty(), 1.2),
                        new KeyValue(leftLabel.scaleYProperty(), 1.2),
                        new KeyValue(rightLabel.scaleXProperty(), 1.2),
                        new KeyValue(rightLabel.scaleYProperty(), 1.2)
                ),
                new KeyFrame(Duration.seconds(delayInSeconds + 1.8),
                        new KeyValue(leftLabel.scaleXProperty(), 1.0),
                        new KeyValue(leftLabel.scaleYProperty(), 1.0),
                        new KeyValue(rightLabel.scaleXProperty(), 1.0),
                        new KeyValue(rightLabel.scaleYProperty(), 1.0)
                )
        );

        entrance.play();
    }





    // Fade In con Callback (útil para intro)
    public static void fadeInWithCallback(Node node, double durationSeconds, Runnable onFinished) {
        FadeTransition fade = new FadeTransition(Duration.seconds(durationSeconds), node);
        fade.setFromValue(0);
        fade.setToValue(1);
        fade.setOnFinished(event -> onFinished.run());
        fade.play();
    }

    // Pulsar Glow Animation (loop)
    public static void pulse(Node node, double durationSeconds) {
        ScaleTransition pulse = new ScaleTransition(Duration.seconds(durationSeconds), node);
        pulse.setFromX(1.0);
        pulse.setFromY(1.0);
        pulse.setToX(1.05);
        pulse.setToY(1.05);
        pulse.setAutoReverse(true);
        pulse.setCycleCount(Animation.INDEFINITE);
        pulse.play();
    }

    // Transición a otra vista con retraso
    public static void delayedTransition(double delaySeconds, Runnable transitionAction) {
        PauseTransition pause = new PauseTransition(Duration.seconds(delaySeconds));
        pause.setOnFinished(event -> transitionAction.run());
        pause.play();
    }
}
