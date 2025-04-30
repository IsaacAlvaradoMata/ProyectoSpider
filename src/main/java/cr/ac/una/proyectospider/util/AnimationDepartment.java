package cr.ac.una.proyectospider.util;

import javafx.animation.*;
import javafx.application.Platform;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.Node;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.effect.Blend;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.PerspectiveTransform;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public class AnimationDepartment {

    public static void fadeIn(Node node, Duration delay) {
        node.setOpacity(0);
        PauseTransition wait = new PauseTransition(delay);
        wait.setOnFinished(e -> {
            FadeTransition fade = new FadeTransition(Duration.millis(800), node);
            fade.setFromValue(0);
            fade.setToValue(1);
            fade.play();
        });
        wait.play();
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

    public static void animateNeonGlow(ImageView imageView) {
        Timeline glowCycle = new Timeline(
                new KeyFrame(Duration.seconds(0), e -> {
                    imageView.setEffect(new DropShadow(20, Color.web("#ff00ff"))); // Magenta
                }),
                new KeyFrame(Duration.seconds(1.5), e -> {
                    imageView.setEffect(new DropShadow(20, Color.web("#00ffff"))); // Cyan
                }),
                new KeyFrame(Duration.seconds(3), e -> {
                    imageView.setEffect(new DropShadow(20, Color.web("#ffc107"))); // Dorado
                }),
                new KeyFrame(Duration.seconds(4.5), e -> {
                    imageView.setEffect(new DropShadow(20, Color.web("#ff00ff"))); // Vuelve al magenta
                })
        );
        glowCycle.setCycleCount(Animation.INDEFINITE);
        glowCycle.play();
    }

    // Pulsar Glow Animation (loop)
    public static void pulse(Node node, double durationSeconds) {
        ScaleTransition pulse = new ScaleTransition(Duration.seconds(durationSeconds), node);

        // 🔥 Aumentamos intensidad del zoom
        pulse.setFromX(1.0);
        pulse.setFromY(1.0);
        pulse.setToX(1.15); // antes 1.05
        pulse.setToY(1.15);

        // 🎚️ Más fluido y expresivo
        pulse.setAutoReverse(true);
        pulse.setInterpolator(Interpolator.EASE_BOTH); // entrada y salida suaves
        pulse.setCycleCount(Animation.INDEFINITE);
        pulse.play();
    }

    public static void slideFromTop(Node node, Duration delay) {
        node.setOpacity(0);
        TranslateTransition slide = new TranslateTransition(Duration.millis(800), node);
        slide.setFromY(-100);
        slide.setToY(0);
        slide.setDelay(delay);
        slide.setInterpolator(Interpolator.EASE_OUT);

        FadeTransition fade = new FadeTransition(Duration.millis(800), node);
        fade.setFromValue(0);
        fade.setToValue(1);
        fade.setDelay(delay);

        ParallelTransition anim = new ParallelTransition(slide, fade);
        anim.play();

    }


    public static void subtleBounce(Node node, double delaySeconds) {
        TranslateTransition bounce = new TranslateTransition(Duration.seconds(2), node);
        bounce.setByY(5);
        bounce.setAutoReverse(true);
        bounce.setCycleCount(Animation.INDEFINITE);
        bounce.setInterpolator(Interpolator.EASE_BOTH);
        bounce.setDelay(Duration.seconds(delaySeconds)); // 🎯 Delay agregado
        bounce.play();
    }

    public static void slideInFromBottom(Node node, double delaySeconds) {
        node.setOpacity(0);
        node.setTranslateY(100);

        Timeline slideIn = new Timeline(
                new KeyFrame(Duration.seconds(delaySeconds),
                        new KeyValue(node.opacityProperty(), 0),
                        new KeyValue(node.translateYProperty(), 50)),
                new KeyFrame(Duration.seconds(delaySeconds + 0.5),
                        new KeyValue(node.opacityProperty(), 1),
                        new KeyValue(node.translateYProperty(), 0))
        );
        slideIn.play();
    }


    public static void slideUpWithEpicBounceClean(Node node, Duration delay, double sceneHeight) {
        node.setTranslateY(0);

        double startY = sceneHeight + 100;
        node.setOpacity(0);

        TranslateTransition slideIn = new TranslateTransition(Duration.millis(1500), node);
        slideIn.setFromY(startY);
        slideIn.setToY(-20);
        slideIn.setInterpolator(Interpolator.EASE_OUT);
        slideIn.setDelay(delay);

        TranslateTransition settle = new TranslateTransition(Duration.millis(300), node);
        settle.setFromY(-20);
        settle.setToY(0);
        settle.setInterpolator(Interpolator.EASE_IN);

        FadeTransition fade = new FadeTransition(Duration.millis(1000), node);
        fade.setFromValue(0);
        fade.setToValue(1);
        fade.setDelay(delay);

        new SequentialTransition(
                new ParallelTransition(slideIn, fade),
                settle
        ).play();
    }

    public static void animateNeonBorderWithLED(StackPane container, TextField textField, double delaySeconds) {
        Rectangle neonBorder = new Rectangle();
        neonBorder.setWidth(textField.getPrefWidth());
        neonBorder.setHeight(textField.getPrefHeight());
        neonBorder.setArcWidth(10);
        neonBorder.setArcHeight(10);
        neonBorder.setFill(null);
        neonBorder.setStroke(Color.web("#ffc107")); // Neon Cyan
        neonBorder.setStrokeWidth(2);
        neonBorder.setOpacity(0);
        neonBorder.getStrokeDashArray().setAll(12.0, 8.0); // Dash suave

        // Posición exacta sobre el TextField
        neonBorder.setManaged(false);
        neonBorder.layoutXProperty().bind(textField.layoutXProperty());
        neonBorder.layoutYProperty().bind(textField.layoutYProperty());

        container.getChildren().add(neonBorder);

        // 🚀 Animación con AnimationTimer (súper fluida)
        AnimationTimer timer = new AnimationTimer() {
            private double offset = 0;

            @Override
            public void handle(long now) {
                offset += 0.5; // Ajusta velocidad aquí
                neonBorder.setStrokeDashOffset(offset);
            }
        };

        // ⏱️ Controlar el delay
        PauseTransition delay = new PauseTransition(Duration.seconds(delaySeconds));
        delay.setOnFinished(e -> {
            neonBorder.setOpacity(1);
            timer.start();
        });
        delay.play();
    }


//    GLITCH DE SALIDA
    public static void glitchFadeOut(Node targetNode, Duration totalDuration, Runnable onFinished) {
        DropShadow dropShadow = new DropShadow(20, Color.web("#ff00ff"));

        Timeline glitchFadeEffect = new Timeline(
                new KeyFrame(Duration.seconds(0), e -> {
                    dropShadow.setColor(Color.web("#ff00ff"));
                    dropShadow.setSpread(0.1);
                    targetNode.setEffect(dropShadow);
                    targetNode.setTranslateX(0);
                    targetNode.setOpacity(1.0);
                }),
                new KeyFrame(Duration.seconds(0.2), e -> {
                    dropShadow.setColor(Color.web("#00ffff"));
                    dropShadow.setSpread(0.2);
                    targetNode.setTranslateX(5);
                    targetNode.setOpacity(0.95);
                }),
                new KeyFrame(Duration.seconds(0.3), e -> {
                    dropShadow.setColor(Color.web("#39ff14"));
                    dropShadow.setSpread(0.25);
                    targetNode.setTranslateX(-5);
                    targetNode.setOpacity(0.85);
                }),
                new KeyFrame(Duration.seconds(0.4), e -> {
                    dropShadow.setColor(Color.web("#ff00ff"));
                    dropShadow.setSpread(0.3);
                    targetNode.setTranslateX(8);
                    targetNode.setOpacity(0.75);
                }),
                new KeyFrame(Duration.seconds(0.5), e -> {
                    dropShadow.setColor(Color.web("#00ffff"));
                    dropShadow.setSpread(0.4);
                    targetNode.setTranslateX(-8);
                    targetNode.setOpacity(0.65);
                }),
                new KeyFrame(Duration.seconds(0.6), e -> {
                    dropShadow.setColor(Color.web("#39ff14"));
                    dropShadow.setSpread(0.5);
                    targetNode.setTranslateX(12);
                    targetNode.setOpacity(0.55);
                }),
                new KeyFrame(Duration.seconds(0.7), e -> {
                    dropShadow.setColor(Color.web("#ff00ff"));
                    dropShadow.setSpread(0.6);
                    targetNode.setTranslateX(-12);
                    targetNode.setOpacity(0.45);
                }),
                new KeyFrame(Duration.seconds(0.8), e -> {
                    targetNode.setTranslateX(5);
                    targetNode.setOpacity(0.35);
                }),
                new KeyFrame(Duration.seconds(0.9), e -> {
                    targetNode.setTranslateX(-5);
                    targetNode.setOpacity(0.25);
                }),
                new KeyFrame(Duration.seconds(1.0), e -> {
                    targetNode.setTranslateX(0);
                    targetNode.setOpacity(0.15);
                }),
                new KeyFrame(Duration.seconds(1.1), e -> {
                    targetNode.setEffect(null);
                    targetNode.setOpacity(0.0);
                })
        );

        glitchFadeEffect.setOnFinished(e -> {
            if (onFinished != null) onFinished.run();
        });

        glitchFadeEffect.play();
    }

//    GLITCH DE ENTRADA
    public static void glitchFadeIn(Node targetNode, Duration totalDuration) {
        DropShadow magentaShadow = new DropShadow(20, Color.web("#ff00ff"));
        DropShadow cyanShadow = new DropShadow(20, Color.web("#00ffff"));
        DropShadow greenShadow = new DropShadow(20, Color.web("#39ff14"));

        targetNode.setOpacity(0.3);
        targetNode.setTranslateX(0);

        Timeline glitchInEffect = new Timeline(
                new KeyFrame(Duration.seconds(0), e -> {
                    targetNode.setEffect(magentaShadow);
                    targetNode.setTranslateX(0);
                    targetNode.setOpacity(0.3);
                }),
                new KeyFrame(Duration.seconds(0.1), e -> {
                    targetNode.setEffect(cyanShadow);
                    targetNode.setTranslateX(10);
                    targetNode.setOpacity(0.4);
                }),
                new KeyFrame(Duration.seconds(0.2), e -> {
                    targetNode.setEffect(greenShadow);
                    targetNode.setTranslateX(-10);
                    targetNode.setOpacity(0.55);
                }),
                new KeyFrame(Duration.seconds(0.3), e -> {
                    targetNode.setEffect(magentaShadow);
                    targetNode.setTranslateX(15);
                    targetNode.setOpacity(0.7);
                }),
                new KeyFrame(Duration.seconds(0.4), e -> {
                    targetNode.setEffect(cyanShadow);
                    targetNode.setTranslateX(-15);
                    targetNode.setOpacity(0.8);
                }),
                new KeyFrame(Duration.seconds(0.5), e -> {
                    targetNode.setEffect(greenShadow);
                    targetNode.setTranslateX(5);
                    targetNode.setOpacity(0.9);
                }),
                new KeyFrame(Duration.seconds(0.6), e -> {
                    targetNode.setEffect(null);
                    targetNode.setTranslateX(0);
                    targetNode.setOpacity(1.0);
                })
        );

        glitchInEffect.play();
    }

//    EFECTO ESCRITURA
    public static void typewriterEffect(Label label, String text, Duration delay, double speedPerChar) {
        label.setText(""); // Start empty
        label.setOpacity(1);

        PauseTransition initialDelay = new PauseTransition(delay);

        initialDelay.setOnFinished(event -> {
            Timeline typer = new Timeline();
            for (int i = 0; i < text.length(); i++) {
                final int index = i;
                KeyFrame kf = new KeyFrame(Duration.seconds(i * speedPerChar), e -> {
                    label.setText(text.substring(0, index + 1));
                });
                typer.getKeyFrames().add(kf);
            }
            typer.play();
        });

        initialDelay.play();
    }

//    ANIMACION DE LABEL HACKEANDO

    public static void blinkHackeandoSequence(Label label, Duration blinkRate, Duration totalDuration, Duration startDelay) {
        // Ocultar hasta que comience
        label.setVisible(false);

        PauseTransition delayStart = new PauseTransition(startDelay);
        delayStart.setOnFinished(event -> {
            label.setVisible(true);

            Timeline blink = new Timeline(
                    new KeyFrame(Duration.ZERO, e -> label.setVisible(true)),
                    new KeyFrame(blinkRate.divide(2), e -> label.setVisible(false)),
                    new KeyFrame(blinkRate, e -> label.setVisible(true))
            );
            blink.setCycleCount(Animation.INDEFINITE);
            blink.play();

            // Detener y reemplazar texto después de cierto tiempo
            PauseTransition stopBlink = new PauseTransition(totalDuration);
            stopBlink.setOnFinished(e -> {
                blink.stop();
                label.setText("ACCESO CONCEDIDO");
                label.setVisible(true);
                label.setTextFill(Color.web("#000000FF"));
            });
            stopBlink.play();
        });

        delayStart.play();
    }

//    GLITCH DE TERMINAL
public static void applyFullCRTGlitchEffect(StackPane terminal, Duration glitchInterval) {
    // === Scanline ===
    Rectangle scanline = new Rectangle();
    scanline.setFill(Color.web("#39ff14", 0.8));
    scanline.setHeight(10);
    scanline.widthProperty().bind(terminal.widthProperty());
    scanline.setManaged(false);
    scanline.setVisible(false);

    terminal.getChildren().add(scanline);

    Timeline glitchLoop = new Timeline(new KeyFrame(glitchInterval, e -> {
        // Distorsión tipo CRT (curvatura) + Oscurecer
        PerspectiveTransform curve = new PerspectiveTransform();
        double w = terminal.getWidth();
        double h = terminal.getHeight();
        double depth = 40; // Intensidad de la curva (ajustable)

// Bordes superiores hundidos hacia el centro
        curve.setUlx(0 + depth);
        curve.setUly(0);

        curve.setUrx(w - depth);
        curve.setUry(0);

// Bordes inferiores empujados hacia afuera
        curve.setLlx(0);
        curve.setLly(h);

        curve.setLrx(w);
        curve.setLry(h);


        ColorAdjust dark = new ColorAdjust();
        dark.setBrightness(-0.25);
        dark.setContrast(0.2);

        Blend blend = new Blend();
        blend.setBottomInput(curve);
        blend.setTopInput(dark);

        terminal.setEffect(blend);
        terminal.setOpacity(0.85);

        // ===== Real barrido correcto =====
        scanline.setVisible(true);

        // Nueva animación pura de LayoutY
        Timeline scanAnimation = new Timeline(
                new KeyFrame(Duration.ZERO,
                        new javafx.animation.KeyValue(scanline.layoutYProperty(), 0)),
                new KeyFrame(Duration.seconds(1),
                        new javafx.animation.KeyValue(scanline.layoutYProperty(), terminal.getHeight() - scanline.getHeight()))
        );
        scanAnimation.setOnFinished(ev -> scanline.setVisible(false));
        scanAnimation.play();

        // Restaurar terminal luego de breve glitch
        PauseTransition restore = new PauseTransition(Duration.millis(250));
        restore.setOnFinished(ev -> {
            terminal.setEffect(null);
            terminal.setOpacity(1.0);
        });
        restore.play();
    }));

    glitchLoop.setCycleCount(Animation.INDEFINITE);
    glitchLoop.play();
}










}


