package cr.ac.una.proyectospider.util;

import cr.ac.una.proyectospider.model.CartasPartidaDto;
import javafx.animation.*;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.effect.Blend;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.PerspectiveTransform;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class AnimationDepartment {

    private static final List<Animation> activeAnimations = new ArrayList<>();
    private static final List<AnimationTimer> activeTimers = new ArrayList<>();
    private static boolean victoryTriggered = false;
    private static Pane activeCelebrationLayer = null;

    public static void stopAllAnimations() {
        for (Animation anim : activeAnimations) {
            anim.stop();
        }
        for (AnimationTimer timer : activeTimers) {
            timer.stop();
        }
        activeAnimations.clear();
        activeTimers.clear();
        System.out.println("Animations stopped");
    }


    public static void fadeIn(Node node, Duration delay) {
        node.setOpacity(0);
        PauseTransition wait = new PauseTransition(delay);
        wait.setOnFinished(e -> {
            FadeTransition fade = new FadeTransition(Duration.millis(800), node);
            fade.setFromValue(0);
            fade.setToValue(1);
            fade.play();
            activeAnimations.add(fade);
        });
        wait.play();
        activeAnimations.add(wait);
    }


    public static void glitchTextWithFlicker(Label label) {
        Timeline glitchCycle = new Timeline();
        glitchCycle.setCycleCount(Animation.INDEFINITE);
        glitchCycle.getKeyFrames().add(new KeyFrame(Duration.seconds(3.5), e -> {
            Timeline glitch = new Timeline(
                    new KeyFrame(Duration.seconds(0), ev -> {
                        label.setTranslateX(0);
                        label.setTranslateY(0);
                        label.setOpacity(1);
                        label.setTextFill(Color.web("#ffc107"));
                    }),
                    new KeyFrame(Duration.seconds(0.05), ev -> {
                        label.setTranslateX(3);
                        label.setTranslateY(-2);
                        label.setOpacity(0.6);
                        label.setTextFill(Color.web("#ff00ff"));
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
                    new KeyFrame(Duration.seconds(0.2), ev -> {
                        label.setTranslateX(2);
                        label.setTranslateY(-2);
                        label.setOpacity(0.5);
                        label.setTextFill(Color.web("#00ffff"));
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
            activeAnimations.add(glitch);
        }));
        glitchCycle.play();
        activeAnimations.add(glitchCycle);
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
        activeAnimations.add(timeline);
    }


    public static void titleSplitEntrance(VBox leftLabel, VBox rightLabel, double delayInSeconds) {
        leftLabel.setOpacity(0);
        rightLabel.setOpacity(0);
        leftLabel.setTranslateX(-600);
        rightLabel.setTranslateX(600);
        leftLabel.setScaleX(1.0);
        leftLabel.setScaleY(1.0);
        rightLabel.setScaleX(1.0);
        rightLabel.setScaleY(1.0);
        Timeline entrance = new Timeline(
                new KeyFrame(Duration.seconds(delayInSeconds),
                        new KeyValue(leftLabel.opacityProperty(), 0),
                        new KeyValue(rightLabel.opacityProperty(), 0)),
                new KeyFrame(Duration.seconds(delayInSeconds + 0.5),
                        new KeyValue(leftLabel.opacityProperty(), 1),
                        new KeyValue(rightLabel.opacityProperty(), 1)),
                new KeyFrame(Duration.seconds(delayInSeconds + 1.5),
                        new KeyValue(leftLabel.translateXProperty(), 0, Interpolator.EASE_BOTH),
                        new KeyValue(rightLabel.translateXProperty(), 0, Interpolator.EASE_BOTH)),
                new KeyFrame(Duration.seconds(delayInSeconds + 1.6),
                        new KeyValue(leftLabel.scaleXProperty(), 1.2),
                        new KeyValue(leftLabel.scaleYProperty(), 1.2),
                        new KeyValue(rightLabel.scaleXProperty(), 1.2),
                        new KeyValue(rightLabel.scaleYProperty(), 1.2)),
                new KeyFrame(Duration.seconds(delayInSeconds + 1.8),
                        new KeyValue(leftLabel.scaleXProperty(), 1.0),
                        new KeyValue(leftLabel.scaleYProperty(), 1.0),
                        new KeyValue(rightLabel.scaleXProperty(), 1.0),
                        new KeyValue(rightLabel.scaleYProperty(), 1.0))
        );
        entrance.play();
        activeAnimations.add(entrance);
    }


    public static void animateNeonGlow(ImageView imageView) {
        Timeline glowCycle = new Timeline(
                new KeyFrame(Duration.seconds(0), e -> imageView.setEffect(new DropShadow(30, Color.web("#ff00ff")))),
                new KeyFrame(Duration.seconds(1.5), e -> imageView.setEffect(new DropShadow(30, Color.web("#00ffff")))),
                new KeyFrame(Duration.seconds(3), e -> imageView.setEffect(new DropShadow(30, Color.web("#ffc107")))),
                new KeyFrame(Duration.seconds(4.5), e -> imageView.setEffect(new DropShadow(30, Color.web("#ff00ff"))))
        );
        glowCycle.setCycleCount(Animation.INDEFINITE);
        glowCycle.play();
        activeAnimations.add(glowCycle);
    }

    public static void animateNeonGlow2(Node node) {
        Timeline glowCycle = new Timeline(
                new KeyFrame(Duration.seconds(0), e ->
                        node.setEffect(new DropShadow(30, Color.web("#ff00ff")))
                ),
                new KeyFrame(Duration.seconds(1.5), e ->
                        node.setEffect(new DropShadow(30, Color.web("#00ffff")))
                ),
                new KeyFrame(Duration.seconds(3), e ->
                        node.setEffect(new DropShadow(30, Color.web("#ffc107")))
                ),
                new KeyFrame(Duration.seconds(4.5), e ->
                        node.setEffect(new DropShadow(30, Color.web("#ff00ff")))
                )
        );

        glowCycle.setCycleCount(Animation.INDEFINITE);
        glowCycle.play();

        activeAnimations.add(glowCycle);
    }

    public static void animateNeonGlowStrong(Node node) {
        Timeline glowCycle = new Timeline(
                new KeyFrame(Duration.seconds(0), e -> {
                    DropShadow glow = new DropShadow(20, Color.web("#ff00ff"));
                    glow.setSpread(0.7);
                    node.setEffect(glow);
                }),
                new KeyFrame(Duration.seconds(1.5), e -> {
                    DropShadow glow = new DropShadow(20, Color.web("#00ffff"));
                    glow.setSpread(0.7);
                    node.setEffect(glow);
                }),
                new KeyFrame(Duration.seconds(3), e -> {
                    DropShadow glow = new DropShadow(20, Color.web("#ffc107"));
                    glow.setSpread(0.7);
                    node.setEffect(glow);
                }),
                new KeyFrame(Duration.seconds(4.5), e -> {
                    DropShadow glow = new DropShadow(20, Color.web("#ff00ff"));
                    glow.setSpread(0.6);
                    node.setEffect(glow);
                })
        );
        glowCycle.setCycleCount(Animation.INDEFINITE);
        glowCycle.play();
        activeAnimations.add(glowCycle);
    }


    public static void pulse(Node node, double durationSeconds) {
        ScaleTransition pulse = new ScaleTransition(Duration.seconds(durationSeconds), node);
        pulse.setFromX(1.0);
        pulse.setFromY(1.0);
        pulse.setToX(1.15);
        pulse.setToY(1.15);
        pulse.setAutoReverse(true);
        pulse.setInterpolator(Interpolator.EASE_BOTH);
        pulse.setCycleCount(Animation.INDEFINITE);
        pulse.play();
        activeAnimations.add(pulse);
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
        activeAnimations.add(anim);
    }


    public static void subtleBounce(Node node, double delaySeconds) {
        double originalY = node.getTranslateY();

        TranslateTransition bounce = new TranslateTransition(Duration.seconds(2), node);
        bounce.setFromY(originalY - 2.5);
        bounce.setToY(originalY + 2.5);
        bounce.setAutoReverse(true);
        bounce.setCycleCount(Animation.INDEFINITE);
        bounce.setInterpolator(Interpolator.EASE_BOTH);
        bounce.setDelay(Duration.seconds(delaySeconds));
        bounce.play();
        activeAnimations.add(bounce);
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
        activeAnimations.add(slideIn);
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
        SequentialTransition sequence = new SequentialTransition(
                new ParallelTransition(slideIn, fade),
                settle
        );
        sequence.play();
        activeAnimations.add(sequence);
    }


    public static void animateNeonBorderWithLED(StackPane container, TextField textField, double delaySeconds) {
        container.getChildren().removeIf(node ->
                node instanceof Rectangle && node.getStyleClass().contains("neon-border")
        );

        Rectangle neonBorder = new Rectangle();
        neonBorder.getStyleClass().add("neon-border");

        neonBorder.setWidth(textField.getPrefWidth());
        neonBorder.setHeight(textField.getPrefHeight());
        neonBorder.setArcWidth(10);
        neonBorder.setArcHeight(10);
        neonBorder.setFill(null);
        neonBorder.setStroke(Color.web("#ffc107"));
        neonBorder.setStrokeWidth(2);
        neonBorder.setOpacity(0);
        neonBorder.getStrokeDashArray().setAll(12.0, 8.0);

        neonBorder.setManaged(false);
        neonBorder.layoutXProperty().bind(textField.layoutXProperty());
        neonBorder.layoutYProperty().bind(textField.layoutYProperty());
        container.getChildren().add(neonBorder);

        AnimationTimer timer = new AnimationTimer() {
            private double offset = 0;

            @Override
            public void handle(long now) {
                offset += 0.5;
                neonBorder.setStrokeDashOffset(offset);
            }
        };

        PauseTransition delay = new PauseTransition(Duration.seconds(delaySeconds));
        delay.setOnFinished(e -> {
            neonBorder.setOpacity(1);
            timer.start();
            activeTimers.add(timer);
        });
        delay.play();
        activeAnimations.add(delay);
    }


    public static void glitchFadeOut(Node targetNode, Duration totalDuration, Runnable onFinished) {
        SoundDepartment.playTransition();
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

    public static void glitchFadeIn(Node targetNode, Duration totalDuration) {
        SoundDepartment.playTransition2();
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

    public static void typewriterEffect(Label label, String text, Duration delay, double speedPerChar) {
        label.setText("");
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
            activeAnimations.add(typer);
        });
        initialDelay.play();
        activeAnimations.add(initialDelay);
    }


    public static void blinkHackeandoSequence(Label label, Duration blinkRate, Duration totalDuration, Duration startDelay) {
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
            activeAnimations.add(blink);

            PauseTransition stopBlink = new PauseTransition(totalDuration);
            stopBlink.setOnFinished(e -> {
                blink.stop();
                label.setText("ACCESO CONCEDIDO");
                label.setVisible(true);
                label.setTextFill(Color.web("#000000FF"));
            });
            stopBlink.play();
            activeAnimations.add(stopBlink);
        });
        delayStart.play();
        activeAnimations.add(delayStart);
    }


    public static void applyFullCRTGlitchEffect(StackPane terminal, Duration glitchInterval) {
        Rectangle existingScanline = null;
        for (javafx.scene.Node child : terminal.getChildren()) {
            if (child instanceof Rectangle && "scanline".equals(child.getId())) {
                existingScanline = (Rectangle) child;
                break;
            }
        }

        Rectangle scanline;
        if (existingScanline != null) {
            scanline = existingScanline;
        } else {
            scanline = new Rectangle();
            scanline.setId("scanline");
            scanline.setFill(Color.web("#39ff14", 0.6));
            scanline.setHeight(3);
            scanline.widthProperty().bind(terminal.widthProperty());
            scanline.setManaged(false);
            scanline.setVisible(false);
            terminal.getChildren().add(scanline);
        }

        Timeline glitchLoop = new Timeline(new KeyFrame(glitchInterval, e -> {
            PerspectiveTransform curve = new PerspectiveTransform();
            double w = terminal.getWidth();
            double h = terminal.getHeight();
            double depth = 40;

            curve.setUlx(0 + depth);
            curve.setUly(0);
            curve.setUrx(w - depth);
            curve.setUry(0);
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

            scanline.setVisible(true);

            Timeline scanAnimation = new Timeline(
                    new KeyFrame(Duration.ZERO,
                            new KeyValue(scanline.layoutYProperty(), 0)),
                    new KeyFrame(Duration.seconds(1),
                            new KeyValue(scanline.layoutYProperty(), terminal.getHeight() - scanline.getHeight()))
            );
            scanAnimation.setOnFinished(ev -> scanline.setVisible(false));
            scanAnimation.play();
            activeAnimations.add(scanAnimation);

            PauseTransition restore = new PauseTransition(Duration.millis(250));
            restore.setOnFinished(ev -> {
                terminal.setEffect(null);
                terminal.setOpacity(1.0);
            });
            restore.play();
            activeAnimations.add(restore);
        }));

        glitchLoop.setCycleCount(Animation.INDEFINITE);
        glitchLoop.play();
        activeAnimations.add(glitchLoop);
    }

    public static void slideFromLeft(Node node, Duration delay) {
        node.setOpacity(0);
        node.setTranslateX(-200);

        TranslateTransition slide = new TranslateTransition(Duration.millis(800), node);
        slide.setFromX(-200);
        slide.setToX(0);
        slide.setDelay(delay);
        slide.setInterpolator(Interpolator.EASE_OUT);

        FadeTransition fade = new FadeTransition(Duration.millis(800), node);
        fade.setFromValue(0);
        fade.setToValue(1);
        fade.setDelay(delay);

        ParallelTransition anim = new ParallelTransition(slide, fade);
        anim.play();
        activeAnimations.add(anim);
    }

    public static void slideFromRight(Node node, Duration delay) {
        node.setOpacity(0);
        node.setTranslateX(200);

        TranslateTransition slide = new TranslateTransition(Duration.millis(800), node);
        slide.setFromX(200);
        slide.setToX(0);
        slide.setDelay(delay);
        slide.setInterpolator(Interpolator.EASE_OUT);

        FadeTransition fade = new FadeTransition(Duration.millis(800), node);
        fade.setFromValue(0);
        fade.setToValue(1);
        fade.setDelay(delay);

        ParallelTransition anim = new ParallelTransition(slide, fade);
        anim.play();
        activeAnimations.add(anim);
    }

    public static void floatingSideToSide(Node node, double moveDistance, double cycleDuration) {
        TranslateTransition translate = new TranslateTransition(Duration.seconds(cycleDuration), node);
        translate.setByX(moveDistance);
        translate.setAutoReverse(true);
        translate.setCycleCount(Animation.INDEFINITE);
        translate.setInterpolator(Interpolator.EASE_BOTH);
        translate.play();
        activeAnimations.add(translate);
    }

    public static void slideLoopLeft(Node node, double distance, double durationSeconds) {
        TranslateTransition slide = new TranslateTransition(Duration.seconds(durationSeconds), node);
        slide.setFromX(0);
        slide.setToX(-distance);
        slide.setAutoReverse(true);
        slide.setCycleCount(Animation.INDEFINITE);
        slide.setInterpolator(Interpolator.EASE_BOTH);
        slide.play();
        activeAnimations.add(slide);
    }

    public static void slideLoopRight(Node node, double distance, double durationSeconds) {
        TranslateTransition slide = new TranslateTransition(Duration.seconds(durationSeconds), node);
        slide.setFromX(0);
        slide.setToX(distance);
        slide.setAutoReverse(true);
        slide.setCycleCount(Animation.INDEFINITE);
        slide.setInterpolator(Interpolator.EASE_BOTH);
        slide.play();
        activeAnimations.add(slide);
    }

    public static void slideLoopWithScale(Node node, double distance, double durationSeconds) {
        TranslateTransition slide = new TranslateTransition(Duration.seconds(durationSeconds), node);
        slide.setFromX(0);
        slide.setToX(distance);
        slide.setAutoReverse(true);
        slide.setCycleCount(Animation.INDEFINITE);
        slide.setInterpolator(Interpolator.EASE_BOTH);

        ScaleTransition scale = new ScaleTransition(Duration.seconds(durationSeconds / 2), node);
        scale.setToX(1.2);
        scale.setToY(1.2);
        scale.setAutoReverse(true);
        scale.setCycleCount(Animation.INDEFINITE);
        scale.setInterpolator(Interpolator.EASE_BOTH);

        ParallelTransition combo = new ParallelTransition(slide, scale);
        combo.play();
        activeAnimations.add(combo);
    }

    public static void glitchFadeInBackground(ImageView target, Duration duration) {
        target.setOpacity(0);

        Timeline glitch = new Timeline(
                new KeyFrame(Duration.seconds(0.0), e -> {
                    target.setTranslateX(0);
                    target.setOpacity(0.2);
                    target.setEffect(new DropShadow(20, Color.web("#ff00ff")));
                }),
                new KeyFrame(Duration.seconds(0.2), e -> {
                    target.setTranslateX(8);
                    target.setOpacity(0.4);
                    target.setEffect(new DropShadow(30, Color.web("#00ffff")));
                }),
                new KeyFrame(Duration.seconds(0.4), e -> {
                    target.setTranslateX(-6);
                    target.setOpacity(0.3);
                    target.setEffect(new DropShadow(30, Color.web("#39ff14")));
                }),
                new KeyFrame(Duration.seconds(0.6), e -> {
                    target.setTranslateX(4);
                    target.setOpacity(0.5);
                }),
                new KeyFrame(Duration.seconds(0.8), e -> {
                    target.setTranslateX(-2);
                    target.setOpacity(0.6);
                }),
                new KeyFrame(Duration.seconds(1), e -> {
                    target.setTranslateX(0);
                    target.setOpacity(0.8);
                })
        );

        FadeTransition fade = new FadeTransition(duration, target);
        fade.setFromValue(0.8);
        fade.setToValue(1.0);
        fade.setDelay(Duration.seconds(1));

        fade.setOnFinished(e -> {
            target.setTranslateX(0);
            target.setEffect(null);
        });

        new SequentialTransition(glitch, fade).play();
    }

    public static void animateSpiderWithWeb(ImageView tela, ImageView arana, Duration pauseBetweenCycles) {
        tela.setOpacity(0);
        tela.setScaleX(1.0);
        tela.setScaleY(1.0);
        tela.setTranslateY(0);
        arana.setOpacity(0);
        arana.setTranslateY(-600);

        Rectangle revealClip = new Rectangle(0, 0, tela.getFitWidth(), 0);
        tela.setClip(revealClip);
        tela.setOpacity(1);

        Timeline revealWeb = new Timeline(
                new KeyFrame(Duration.seconds(0),
                        new KeyValue(revealClip.heightProperty(), 0)),
                new KeyFrame(Duration.seconds(0.7),
                        new KeyValue(revealClip.heightProperty(), tela.getFitHeight(), Interpolator.EASE_OUT))
        );

        revealWeb.setOnFinished(ev -> tela.setClip(null));

        PauseTransition delayBeforeDrop = new PauseTransition(Duration.seconds(0.2));
        delayBeforeDrop.setOnFinished(ev -> {
            arana.setOpacity(1);
            TranslateTransition bajar = new TranslateTransition(Duration.seconds(2), arana);
            bajar.setToY(95);
            bajar.play();
        });

        PauseTransition delayBeforeRise = new PauseTransition(Duration.seconds(3.5));
        delayBeforeRise.setOnFinished(ev -> {
            TranslateTransition subir = new TranslateTransition(Duration.seconds(3), arana);
            subir.setToY(-600);
            subir.setInterpolator(Interpolator.EASE_BOTH);
            subir.setOnFinished(e -> {
                arana.setOpacity(0);
                Rectangle closeClip = new Rectangle(0, 0, tela.getFitWidth(), tela.getFitHeight());
                tela.setClip(closeClip);

                Timeline hideWeb = new Timeline(
                        new KeyFrame(Duration.ZERO,
                                new KeyValue(closeClip.heightProperty(), tela.getFitHeight())),
                        new KeyFrame(Duration.seconds(0.6),
                                new KeyValue(closeClip.heightProperty(), 0, Interpolator.EASE_IN))
                );

                hideWeb.setOnFinished(end -> {
                    tela.setClip(null);
                    tela.setOpacity(0);
                });

                hideWeb.play();
            });
            subir.play();
        });

        SequentialTransition ciclo = new SequentialTransition(revealWeb, delayBeforeDrop, delayBeforeRise);
        ciclo.setOnFinished(e -> {
            PauseTransition pausa = new PauseTransition(pauseBetweenCycles);
            pausa.setOnFinished(ev -> animateSpiderWithWeb(tela, arana, pauseBetweenCycles));
            pausa.play();
            activeAnimations.add(pausa);
        });

        ciclo.play();
        activeAnimations.add(ciclo);
    }

    public static void playVictoryAnimation(
            StackPane parent,
            boolean usarEstiloClasico,
            Runnable onFinished
    ) {
        stopAllAnimations();
        SoundDepartment.playVictory();
        parent.getChildren().removeIf(n -> "celebrationLayer".equals(n.getId()));

        Pane celebrationLayer = new Pane();
        celebrationLayer.setId("celebrationLayer");
        celebrationLayer.setPickOnBounds(false);
        celebrationLayer.setMouseTransparent(false);
        celebrationLayer.setStyle("-fx-background-color: rgba(0, 0, 0, 0.5);");
        celebrationLayer.prefWidthProperty().bind(parent.widthProperty());
        celebrationLayer.prefHeightProperty().bind(parent.heightProperty());
        celebrationLayer.setOpacity(0);
        parent.getChildren().add(celebrationLayer);

        FadeTransition fadeInLayer = new FadeTransition(Duration.seconds(1), celebrationLayer);
        fadeInLayer.setFromValue(0);
        fadeInLayer.setToValue(1);
        fadeInLayer.setInterpolator(Interpolator.EASE_IN);
        fadeInLayer.setOnFinished(e -> {
            startVictoryEffects(celebrationLayer, parent, usarEstiloClasico, onFinished);
        });
        fadeInLayer.play();
        activeAnimations.add(fadeInLayer);
    }

    private static void startVictoryEffects(
            Pane celebrationLayer,
            StackPane parent,
            boolean usarEstiloClasico,
            Runnable onFinished
    ) {
        double sceneWidth = parent.getScene().getWidth();
        double sceneHeight = parent.getScene().getHeight();
        Random rnd = new Random();

        String rutaCarta = usarEstiloClasico
                ? "/cr/ac/una/proyectospider/resources/rear.png"
                : "/cr/ac/una/proyectospider/resources/rearS.png";
        Image imagenCarta = new Image(
                AnimationDepartment.class.getResourceAsStream(rutaCarta)
        );

        Timeline cartasSpawner = new Timeline(new KeyFrame(Duration.seconds(0.1), ev -> {
            ImageView cartaIV = new ImageView(imagenCarta);
            cartaIV.setFitWidth(40);
            cartaIV.setPreserveRatio(true);
            cartaIV.setSmooth(true);

            double startX = rnd.nextDouble() * sceneWidth;
            double startY = -50 - rnd.nextDouble() * 200;
            cartaIV.setLayoutX(startX);
            cartaIV.setLayoutY(startY);
            celebrationLayer.getChildren().add(0, cartaIV);

            TranslateTransition tt = new TranslateTransition(Duration.seconds(2 + rnd.nextDouble()), cartaIV);
            tt.setFromY(0);
            tt.setToY(sceneHeight + 100 - startY);
            tt.setInterpolator(Interpolator.LINEAR);
            tt.setOnFinished(e2 -> celebrationLayer.getChildren().remove(cartaIV));
            tt.play();
            activeAnimations.add(tt);

            RotateTransition rot = new RotateTransition(Duration.seconds(1 + rnd.nextDouble()), cartaIV);
            rot.setByAngle(360 * (rnd.nextBoolean() ? 1 : -1));
            rot.setCycleCount(Animation.INDEFINITE);
            rot.setInterpolator(Interpolator.LINEAR);
            rot.play();
            activeAnimations.add(rot);

            TranslateTransition sway = new TranslateTransition(Duration.seconds(1 + rnd.nextDouble()), cartaIV);
            sway.setByX(20 * (rnd.nextBoolean() ? 1 : -1));
            sway.setAutoReverse(true);
            sway.setCycleCount(Animation.INDEFINITE);
            sway.play();
            activeAnimations.add(sway);
        }));
        cartasSpawner.setCycleCount(Animation.INDEFINITE);
        cartasSpawner.play();
        activeAnimations.add(cartasSpawner);

        Color[] paleta = new Color[]{
                Color.RED, Color.ORANGE, Color.YELLOW,
                Color.LIME, Color.CYAN, Color.MAGENTA
        };
        Timeline confettiSpawner = new Timeline(new KeyFrame(Duration.seconds(0.05), ev -> {
            Circle confetti = new Circle(4);
            confetti.setFill(paleta[rnd.nextInt(paleta.length)]);

            double startX = rnd.nextDouble() * sceneWidth;
            double startY = -20 - rnd.nextDouble() * 100;
            confetti.setLayoutX(startX);
            confetti.setLayoutY(startY);
            celebrationLayer.getChildren().add(0, confetti);

            TranslateTransition ttConf = new TranslateTransition(
                    Duration.seconds(2 + rnd.nextDouble()), confetti);
            ttConf.setFromY(0);
            ttConf.setToY(sceneHeight + 50 - startY);
            ttConf.setInterpolator(Interpolator.LINEAR);
            ttConf.setOnFinished(e2 -> celebrationLayer.getChildren().remove(confetti));
            ttConf.play();
            activeAnimations.add(ttConf);

            RotateTransition rotConf = new RotateTransition(
                    Duration.seconds(2 + rnd.nextDouble()), confetti);
            rotConf.setByAngle(360);
            rotConf.setCycleCount(Animation.INDEFINITE);
            rotConf.setInterpolator(Interpolator.LINEAR);
            rotConf.play();
            activeAnimations.add(rotConf);
        }));
        confettiSpawner.setCycleCount(Animation.INDEFINITE);
        confettiSpawner.play();
        activeAnimations.add(confettiSpawner);

        Image spiderImg = new Image(
                AnimationDepartment.class.getResourceAsStream(
                        "/cr/ac/una/proyectospider/resources/spiderPoints.png"));
        Image webImg = new Image(
                AnimationDepartment.class.getResourceAsStream(
                        "/cr/ac/una/proyectospider/resources/TelaIcon.png"));

        double baseDelay = 0.5;
        String[] spiderAppearSounds = {"Spider1", "Spider2", "Spider3", "Spider4"};
        for (int i = 0; i < 4; i++) {
            final int idx = i;

            ImageView tela = new ImageView(webImg);
            tela.setFitWidth(180);
            tela.setPreserveRatio(true);
            tela.setSmooth(true);

            double telaX = idx * (sceneWidth / 4.0) + (sceneWidth / 8.0) - (tela.getFitWidth() / 2.0);
            tela.setLayoutX(telaX);
            tela.setLayoutY(0);
            tela.setOpacity(0);
            celebrationLayer.getChildren().add(tela);

            tela.applyCss();
            celebrationLayer.applyCss();
            celebrationLayer.layout();
            double fullHeight = tela.getBoundsInLocal().getHeight();

            ImageView arana = new ImageView(spiderImg);
            arana.setFitWidth(120);
            arana.setPreserveRatio(true);
            arana.setSmooth(true);

            double aranaX = telaX + (tela.getFitWidth() / 2.0) - (arana.getFitWidth() / 2.0);
            arana.setLayoutX(aranaX);
            arana.setLayoutY(-300);
            arana.setOpacity(0);
            celebrationLayer.getChildren().add(0, arana);

            Rectangle revealClip = new Rectangle(0, 0, tela.getFitWidth(), 0);
            tela.setClip(revealClip);
            tela.setOpacity(1);

            Timeline revealWeb = new Timeline(
                    new KeyFrame(Duration.seconds(0),
                            new KeyValue(revealClip.heightProperty(), 0)),
                    new KeyFrame(Duration.seconds(0.7),
                            new KeyValue(revealClip.heightProperty(), fullHeight, Interpolator.EASE_OUT))
            );
            revealWeb.setOnFinished(evt -> {
                tela.setClip(null);

            });
            revealWeb.setDelay(Duration.seconds(idx * baseDelay));

            PauseTransition delayBeforeDrop = new PauseTransition(Duration.seconds(0.4 + idx * 0.4));
            delayBeforeDrop.setOnFinished(evt -> {
                arana.setOpacity(1);
                TranslateTransition bajar = new TranslateTransition(Duration.seconds(2), arana);
                bajar.setToY(300);
                bajar.play();
                SoundDepartment.play(spiderAppearSounds[idx]);
            });

            PauseTransition delayBeforeRise = new PauseTransition(Duration.seconds(3.5 + idx * 0.2));
            delayBeforeRise.setOnFinished(evt -> {
                TranslateTransition subir = new TranslateTransition(Duration.seconds(3), arana);
                subir.setToY(-300);
                SoundDepartment.play(spiderAppearSounds[idx]);
                subir.setInterpolator(Interpolator.EASE_BOTH);
                subir.setOnFinished(e2 -> {
                    arana.setOpacity(0);

                    Rectangle closeClip = new Rectangle(0, 0, tela.getFitWidth(), fullHeight);
                    tela.setClip(closeClip);

                    Timeline hideWeb = new Timeline(
                            new KeyFrame(Duration.ZERO,
                                    new KeyValue(closeClip.heightProperty(), fullHeight)),
                            new KeyFrame(Duration.seconds(0.6),
                                    new KeyValue(closeClip.heightProperty(), 0, Interpolator.EASE_IN))
                    );
                    hideWeb.setOnFinished(e3 -> {
                        tela.setClip(null);
                        tela.setOpacity(0);

                        if (idx == 3) {
                            cartasSpawner.stop();
                            confettiSpawner.stop();
                            parent.getChildren().remove(activeCelebrationLayer);
                            FadeTransition fadeOutLayer = new FadeTransition(Duration.seconds(1), celebrationLayer);
                            fadeOutLayer.setFromValue(1);
                            fadeOutLayer.setToValue(0);
                            fadeOutLayer.setInterpolator(Interpolator.EASE_OUT);
                            fadeOutLayer.setOnFinished(e4 -> {
                                stopAllAnimations();
                                parent.getChildren().remove(celebrationLayer);
                                if (onFinished != null) onFinished.run();
                            });
                            fadeOutLayer.play();
                            activeAnimations.add(fadeOutLayer);
                        }
                    });
                    hideWeb.play();
                });
                subir.play();
            });

            SequentialTransition ciclo = new SequentialTransition(
                    revealWeb,
                    delayBeforeDrop,
                    delayBeforeRise
            );
            ciclo.play();
            activeAnimations.add(ciclo);
        }

        Font cynatar = Font.loadFont(
                AnimationDepartment.class.getResourceAsStream("/cr/ac/una/proyectospider/resources/Cynatar.ttf"),
                150
        );

        Label lblVictoria = new Label("¡VICTORIA!");
        lblVictoria.setTextFill(Color.web("#ffc107"));
        lblVictoria.setFont(cynatar);
        lblVictoria.setTextAlignment(TextAlignment.CENTER);
        lblVictoria.setOpacity(0);
        lblVictoria.setScaleX(0.1);
        lblVictoria.setScaleY(0.1);

        AnimationDepartment.animateNeonGlow2(lblVictoria);
        AnimationDepartment.glitchTextWithFlicker(lblVictoria);
        AnimationDepartment.pulse(lblVictoria, 1.5);

        lblVictoria.layoutXProperty().bind(
                parent.widthProperty().divide(2).subtract(lblVictoria.widthProperty().divide(2))
        );
        lblVictoria.layoutYProperty().bind(
                parent.heightProperty().divide(2).subtract(lblVictoria.heightProperty().divide(2))
        );
        celebrationLayer.getChildren().add(lblVictoria);
        lblVictoria.toFront();

        FadeTransition fade = new FadeTransition(Duration.seconds(1.2), lblVictoria);
        fade.setFromValue(0);
        fade.setToValue(1);
        fade.setInterpolator(Interpolator.EASE_OUT);

        ScaleTransition scaleUp = new ScaleTransition(Duration.seconds(1.2), lblVictoria);
        scaleUp.setFromX(0.1);
        scaleUp.setFromY(0.1);
        scaleUp.setToX(1.0);
        scaleUp.setToY(1.0);
        scaleUp.setInterpolator(Interpolator.EASE_OUT);

        new ParallelTransition(fade, scaleUp).play();
    }


    public static void flipCardAnimation(ImageView cardView, Image imagenBocaArriba, Runnable onFinished) {
        SoundDepartment.playFlip();
        cardView.setRotationAxis(javafx.geometry.Point3D.ZERO.add(0, 1, 0));

        RotateTransition flip1 = new RotateTransition(Duration.millis(180), cardView);
        flip1.setFromAngle(0);
        flip1.setToAngle(90);
        flip1.setInterpolator(Interpolator.EASE_IN);

        RotateTransition flip2 = new RotateTransition(Duration.millis(180), cardView);
        flip2.setFromAngle(270);
        flip2.setToAngle(360);
        flip2.setInterpolator(Interpolator.EASE_OUT);

        flip1.setOnFinished(e -> {
            cardView.setImage(imagenBocaArriba);
            cardView.setRotate(270);
            flip2.play();
        });
        flip2.setOnFinished(e -> {
            cardView.setRotate(0);
            if (onFinished != null) onFinished.run();
        });
        flip1.play();
    }


    public static void animarUndoVisual(List<?> grupo, int columnaOrigen, Pane spGamebackground,
                                        java.util.Map<?, ImageView> cartaToImageView, javafx.scene.layout.HBox hboxTablero,
                                        List<?> cartasEnJuego, java.util.function.IntFunction<Double> calcularEspaciadoVertical,
                                        Object cartaDebajo, Boolean cartaDebajoEstabaBocaAbajo, Runnable onFinished,
                                        String reversoGuardado) {
        if (grupo == null || grupo.isEmpty()) {
            if (onFinished != null) onFinished.run();
            return;
        }
        Pane animPane = new Pane();
        animPane.setPickOnBounds(false);
        spGamebackground.getChildren().add(animPane);

        List<ImageView> clones = new ArrayList<>();
        List<Double> origX = new ArrayList<>();
        List<Double> origY = new ArrayList<>();
        List<ImageView> originales = new ArrayList<>();

        for (Object carta : grupo) {
            ImageView original = cartaToImageView.get(carta);
            if (original == null) continue;
            originales.add(original);
            javafx.geometry.Bounds bounds = original.localToScene(original.getBoundsInLocal());
            javafx.geometry.Bounds parentBounds = spGamebackground.sceneToLocal(bounds);
            ImageView clone = new ImageView(original.getImage());
            clone.setFitWidth(original.getFitWidth());
            clone.setPreserveRatio(true);
            clone.setSmooth(true);
            clone.setLayoutX(parentBounds.getMinX());
            clone.setLayoutY(parentBounds.getMinY());
            clones.add(clone);
            origX.add(parentBounds.getMinX());
            origY.add(parentBounds.getMinY());
            animPane.getChildren().add(clone);
            original.setVisible(false);
        }

        Pane columnaOrigenPane = (Pane) hboxTablero.getChildren().get(columnaOrigen);
        double destX = 0, destY = 0;
        javafx.geometry.Bounds destBounds = columnaOrigenPane.localToScene(columnaOrigenPane.getBoundsInLocal());
        javafx.geometry.Bounds destParentBounds = spGamebackground.sceneToLocal(destBounds);
        destX = destParentBounds.getMinX();
        int cartasEnColOrig = (int) cartasEnJuego.stream()
                .filter(c -> {
                    try {
                        java.lang.reflect.Method getColumna = c.getClass().getMethod("getColumna");
                        return (int) getColumna.invoke(c) == columnaOrigen;
                    } catch (Exception e) {
                        return false;
                    }
                })
                .count();
        int totalCartasDespues = cartasEnColOrig + grupo.size();
        double spacing = calcularEspaciadoVertical.apply(totalCartasDespues);


        double baseY;
        if (cartasEnColOrig > 0) {
            Object ultima = cartasEnJuego.stream()
                    .filter(c -> {
                        try {
                            java.lang.reflect.Method getColumna = c.getClass().getMethod("getColumna");
                            return (int) getColumna.invoke(c) == columnaOrigen;
                        } catch (Exception e) {
                            return false;
                        }
                    })
                    .max((a, b) -> {
                        try {
                            java.lang.reflect.Method getOrden = a.getClass().getMethod("getOrden");
                            int ordenA = (int) getOrden.invoke(a);
                            int ordenB = (int) getOrden.invoke(b);
                            return Integer.compare(ordenA, ordenB);
                        } catch (Exception e) {
                            return 0;
                        }
                    })
                    .orElse(null);
            if (ultima != null) {
                ImageView ivUltima = cartaToImageView.get(ultima);
                if (ivUltima != null) {
                    javafx.geometry.Bounds b = ivUltima.localToScene(ivUltima.getBoundsInLocal());
                    javafx.geometry.Bounds pb = spGamebackground.sceneToLocal(b);
                    baseY = pb.getMinY() + spacing;
                } else {
                    baseY = destParentBounds.getMinY() + cartasEnColOrig * spacing;
                }
            } else {
                baseY = destParentBounds.getMinY() + cartasEnColOrig * spacing;
            }
        } else {
            baseY = destParentBounds.getMinY();
        }

        javafx.animation.ParallelTransition toOrig = new javafx.animation.ParallelTransition();
        for (int i = 0; i < clones.size(); i++) {
            ImageView clone = clones.get(i);
            double targetX = destX;
            double targetY = baseY + i * spacing;
            javafx.animation.TranslateTransition back = new javafx.animation.TranslateTransition(javafx.util.Duration.millis(400), clone);
            back.setToX(targetX - origX.get(i));
            back.setToY(targetY - origY.get(i));
            toOrig.getChildren().add(back);
        }

        Runnable doMove = () -> {
            javafx.animation.SequentialTransition seq = new javafx.animation.SequentialTransition(
                    toOrig,
                    new javafx.animation.PauseTransition(javafx.util.Duration.millis(350))
            );
            seq.setOnFinished(e -> {
                spGamebackground.getChildren().remove(animPane);
                for (ImageView original : originales) {
                    original.setVisible(true);
                }
                if (onFinished != null) onFinished.run();
            });
            seq.play();
        };

        if (cartaDebajo != null && Boolean.TRUE.equals(cartaDebajoEstabaBocaAbajo)) {
            ImageView ivDebajo = cartaToImageView.get(cartaDebajo);
            if (ivDebajo != null) {
                boolean usarEstiloClasico = AppContext.RUTA_CARTAS_CLASICAS.equals(reversoGuardado);
                String imgTrasera = usarEstiloClasico ? "rear.png" : "rearS.png";
                javafx.scene.image.Image imgTraseraObj = new javafx.scene.image.Image(
                        AnimationDepartment.class.getResourceAsStream("/cr/ac/una/proyectospider/resources/" + imgTrasera)
                );
                AnimationDepartment.flipCardAnimation(ivDebajo, imgTraseraObj, () -> {
                    doMove.run();
                });
                return;
            }
        }
        doMove.run();
    }


    public static void animarRepartoCartasVisual(
            List<?> cartasRepartidas,
            Pane spGamebackground,
            java.util.Map<?, ImageView> cartaToImageView,
            javafx.scene.layout.HBox hboxTablero,
            ImageView imgMazo,
            java.util.function.IntFunction<Double> calcularEspaciadoVertical,
            List<?> cartasEnJuego,
            Runnable onFinished
    ) {
        if (cartasRepartidas == null || cartasRepartidas.isEmpty() || imgMazo == null) {
            if (onFinished != null) onFinished.run();
            return;
        }
        Pane animPane = new Pane();
        animPane.setPickOnBounds(false);
        spGamebackground.getChildren().add(animPane);

        List<ImageView> clones = new ArrayList<>();
        List<Double> origX = new ArrayList<>();
        List<Double> origY = new ArrayList<>();
        List<Integer> columnasDestino = new ArrayList<>();
        List<Integer> ordenesDestino = new ArrayList<>();

        javafx.geometry.Bounds mazoBounds = imgMazo.localToScene(imgMazo.getBoundsInLocal());
        javafx.geometry.Bounds mazoParentBounds = spGamebackground.sceneToLocal(mazoBounds);
        double mazoX = mazoParentBounds.getMinX();
        double mazoY = mazoParentBounds.getMinY();

        for (int i = 0; i < cartasRepartidas.size(); i++) {
            Object carta = cartasRepartidas.get(i);
            ImageView clone;
            try {
                java.lang.reflect.Method getImagenNombre = carta.getClass().getMethod("getImagenNombre");
                String imgNombre = (String) getImagenNombre.invoke(carta);
                Image imgFrontal = new Image(AnimationDepartment.class.getResourceAsStream("/cr/ac/una/proyectospider/resources/" + imgNombre));
                clone = new ImageView(imgFrontal);
                ImageView original = cartaToImageView.get(carta);
                if (original != null) {
                    clone.setFitWidth(original.getFitWidth());
                } else if (imgMazo != null) {
                    clone.setFitWidth(imgMazo.getFitWidth());
                }
            } catch (Exception ex) {
                clone = new ImageView(imgMazo.getImage());
                clone.setFitWidth(imgMazo.getFitWidth());
            }
            clone.setPreserveRatio(true);
            clone.setSmooth(true);
            clone.setLayoutX(mazoX);
            clone.setLayoutY(mazoY);
            clones.add(clone);
            origX.add(mazoX);
            origY.add(mazoY);
            animPane.getChildren().add(clone);

            try {
                java.lang.reflect.Method getColumna = carta.getClass().getMethod("getColumna");
                java.lang.reflect.Method getOrden = carta.getClass().getMethod("getOrden");
                columnasDestino.add((Integer) getColumna.invoke(carta));
                ordenesDestino.add((Integer) getOrden.invoke(carta));
            } catch (Exception e) {
                columnasDestino.add(i);
                ordenesDestino.add(0);
            }
        }

        List<Double> destX = new ArrayList<>();
        List<Double> destY = new ArrayList<>();
        for (int i = 0; i < clones.size(); i++) {
            int col = columnasDestino.get(i);
            int orden = ordenesDestino.get(i);
            Pane columnaPane = (Pane) hboxTablero.getChildren().get(col);
            javafx.geometry.Bounds colBounds = columnaPane.localToScene(columnaPane.getBoundsInLocal());
            javafx.geometry.Bounds colParentBounds = spGamebackground.sceneToLocal(colBounds);
            double x = colParentBounds.getMinX();
            int totalCartas = (int) cartasEnJuego.stream().filter(c -> {
                try {
                    java.lang.reflect.Method getColumna = c.getClass().getMethod("getColumna");
                    return (int) getColumna.invoke(c) == col;
                } catch (Exception e) {
                    return false;
                }
            }).count();
            double spacing = calcularEspaciadoVertical.apply(totalCartas);
            double y = colParentBounds.getMinY() + orden * spacing;
            destX.add(x);
            destY.add(y);
        }

        javafx.animation.ParallelTransition toDest = new javafx.animation.ParallelTransition();
        for (int i = 0; i < clones.size(); i++) {
            ImageView clone = clones.get(i);
            double targetX = destX.get(i);
            double targetY = destY.get(i);
            javafx.animation.TranslateTransition go = new javafx.animation.TranslateTransition(javafx.util.Duration.millis(500 + i * 60), clone);
            go.setToX(targetX - origX.get(i));
            go.setToY(targetY - origY.get(i));
            toDest.getChildren().add(go);
        }

        toDest.setOnFinished(e -> {
            spGamebackground.getChildren().remove(animPane);
            if (onFinished != null) onFinished.run();
        });
        toDest.play();
    }

    public static void animarSecuenciaAHaciaPila(

            List<CartasPartidaDto> grupoDe13Cartas,
            int pilaDestinoIndex,
            Map<CartasPartidaDto, ImageView> cartaToImageView,
            StackPane spGamebackground,
            javafx.scene.layout.HBox hboxTableroSuperior,
            Runnable onFinished
    ) {
        if (grupoDe13Cartas == null || grupoDe13Cartas.size() != 13) {
            if (onFinished != null) onFinished.run();
            return;
        }
        SoundDepartment.playPila();
        Pane animPane = new Pane();
        animPane.setPickOnBounds(false);
        spGamebackground.getChildren().add(animPane);

        List<ImageView> clones = new ArrayList<>();
        List<Double> origX = new ArrayList<>();
        List<Double> origY = new ArrayList<>();

        ImageView pilaDestinoIV = (ImageView) hboxTableroSuperior.getChildren().get(2 + pilaDestinoIndex);
        javafx.geometry.Bounds pilaBounds = pilaDestinoIV.localToScene(pilaDestinoIV.getBoundsInLocal());
        javafx.geometry.Bounds pilaParentBounds = spGamebackground.sceneToLocal(pilaBounds);
        double destX = pilaParentBounds.getMinX();
        double destY = pilaParentBounds.getMinY();

        for (CartasPartidaDto carta : grupoDe13Cartas) {
            ImageView original = cartaToImageView.get(carta);
            if (original == null) continue;
            javafx.geometry.Bounds bounds = original.localToScene(original.getBoundsInLocal());
            javafx.geometry.Bounds parentBounds = spGamebackground.sceneToLocal(bounds);
            ImageView clone = new ImageView(original.getImage());
            clone.setFitWidth(original.getFitWidth());
            clone.setPreserveRatio(true);
            clone.setSmooth(true);
            clone.setLayoutX(parentBounds.getMinX());
            clone.setLayoutY(parentBounds.getMinY());
            clones.add(clone);
            origX.add(parentBounds.getMinX());
            origY.add(parentBounds.getMinY());
            animPane.getChildren().add(clone);
            original.setVisible(false);
        }

        ParallelTransition parallel = new ParallelTransition();
        Duration baseDelay = Duration.ZERO;
        Duration delayStep = Duration.millis(40);
        Duration duracion = Duration.millis(400);

        for (int i = 0; i < clones.size(); i++) {
            ImageView clone = clones.get(i);
            TranslateTransition tt = new TranslateTransition(duracion, clone);
            tt.setToX(destX - origX.get(i));
            tt.setToY(destY - origY.get(i));
            tt.setDelay(baseDelay.add(delayStep.multiply(i)));
            tt.setInterpolator(Interpolator.EASE_BOTH);
            parallel.getChildren().add(tt);
        }

        parallel.setOnFinished(e -> {
            spGamebackground.getChildren().remove(animPane);
            if (onFinished != null) onFinished.run();
        });

        parallel.play();
        activeAnimations.add(parallel);
    }

    public static void shakeNode(Node node) {
        TranslateTransition tt = new TranslateTransition(Duration.millis(60), node);
        tt.setFromX(0);
        tt.setByX(12);
        tt.setCycleCount(6);
        tt.setAutoReverse(true);
        tt.setOnFinished(e -> node.setTranslateX(0));
        tt.play();
    }

    public static void animarCartasAlMazoVisual(
            List<?> cartasAAnimar,
            Pane spGamebackground,
            java.util.Map<?, ImageView> cartaToImageView,
            javafx.scene.layout.HBox hboxTablero,
            ImageView imgMazo,
            Runnable onFinished
    ) {
        SoundDepartment.playDeal();
        if (cartasAAnimar == null || cartasAAnimar.isEmpty() || imgMazo == null) {
            if (onFinished != null) onFinished.run();
            return;
        }
        Pane animPane = new Pane();
        animPane.setPickOnBounds(false);
        spGamebackground.getChildren().add(animPane);

        List<ImageView> clones = new ArrayList<>();
        List<Double> origX = new ArrayList<>();
        List<Double> origY = new ArrayList<>();

        for (Object carta : cartasAAnimar) {
            ImageView original = cartaToImageView.get(carta);
            if (original == null) continue;
            javafx.geometry.Bounds bounds = original.localToScene(original.getBoundsInLocal());
            javafx.geometry.Bounds parentBounds = spGamebackground.sceneToLocal(bounds);
            ImageView clone = new ImageView(original.getImage());
            clone.setFitWidth(original.getFitWidth());
            clone.setPreserveRatio(true);
            clone.setSmooth(true);
            clone.setLayoutX(parentBounds.getMinX());
            clone.setLayoutY(parentBounds.getMinY());
            clones.add(clone);
            origX.add(parentBounds.getMinX());
            origY.add(parentBounds.getMinY());
            animPane.getChildren().add(clone);
            original.setVisible(false);
        }

        javafx.geometry.Bounds mazoBounds = imgMazo.localToScene(imgMazo.getBoundsInLocal());
        javafx.geometry.Bounds mazoParentBounds = spGamebackground.sceneToLocal(mazoBounds);
        double mazoX = mazoParentBounds.getMinX();
        double mazoY = mazoParentBounds.getMinY();

        javafx.animation.ParallelTransition toMazo = new javafx.animation.ParallelTransition();
        for (int i = 0; i < clones.size(); i++) {
            ImageView clone = clones.get(i);
            javafx.animation.TranslateTransition go = new javafx.animation.TranslateTransition(javafx.util.Duration.millis(500 + i * 60), clone);
            go.setToX(mazoX - origX.get(i));
            go.setToY(mazoY - origY.get(i));
            toMazo.getChildren().add(go);
        }

        toMazo.setOnFinished(e -> {
            spGamebackground.getChildren().remove(animPane);
            for (int i = 0; i < cartasAAnimar.size(); i++) {
                Object carta = cartasAAnimar.get(i);
                ImageView original = cartaToImageView.get(carta);
                if (original != null) original.setVisible(true);
            }
            if (onFinished != null) onFinished.run();
        });
        toMazo.play();
    }

}
