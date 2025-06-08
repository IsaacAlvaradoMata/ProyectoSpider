package cr.ac.una.proyectospider.util;

import javafx.application.Platform;
import javafx.beans.Observable;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.DragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

public class CustomCursor {
    private SimpleIntegerProperty hotSpotX = new SimpleIntegerProperty();
    private SimpleIntegerProperty hotSpotY = new SimpleIntegerProperty();

    private Scene scene;
    private Pane sceneRoot;
    private Pane content;
    private EventHandler<MouseEvent> eventHandler1;
    private EventHandler<MouseEvent> eventHandler2;
    private EventHandler<MouseEvent> eventHandler3;
    private EventHandler<DragEvent> dragOverHandler;

    public CustomCursor(Scene scene, Pane sceneRoot, Pane content, int hotspotX, int hotspotY) {
        setRoot(scene, sceneRoot, content, hotspotX, hotspotY);
    }

    public void setRoot(Scene scene, Pane sceneRoot, Pane content, int hotSpotX, int hotSpotY) {
        unRegister();
        this.scene = scene;
        this.sceneRoot = sceneRoot;
        this.content = content;

        this.hotSpotX.set(hotSpotX);
        this.hotSpotY.set(hotSpotY);

        content.setManaged(false);
        content.setMouseTransparent(true);

        ObservableList<Node> observable = sceneRoot.getChildren();
        observable.addListener((Observable osb) -> {
            if (content.getParent() != null && observable.get(observable.size() - 1) != content) {
                Platform.runLater(content::toFront);
            }
        });

        if (!observable.contains(content)) {
            observable.add(content);
        }

        eventHandler1 = evt -> {
            if (!sceneRoot.getChildren().contains(content)) {
                observable.add(content);
            }
        };
        eventHandler2 = evt -> observable.remove(content);
        eventHandler3 = evt -> {
            content.setLayoutX(evt.getX() - hotSpotX);
            content.setLayoutY(evt.getY() - hotSpotY);
        };

        dragOverHandler = evt -> {
            content.setLayoutX(evt.getX() - hotSpotX);
            content.setLayoutY(evt.getY() - hotSpotY);
        };

        scene.addEventFilter(MouseEvent.MOUSE_ENTERED, eventHandler1);
        scene.addEventFilter(MouseEvent.MOUSE_EXITED, eventHandler2);
        scene.addEventFilter(MouseEvent.MOUSE_MOVED, eventHandler3);
        scene.addEventFilter(DragEvent.DRAG_OVER, dragOverHandler);
    }

    public void unRegister() {
        if (scene != null) {
            sceneRoot.getChildren().remove(content);
            scene.removeEventFilter(MouseEvent.MOUSE_ENTERED, eventHandler1);
            scene.removeEventFilter(MouseEvent.MOUSE_EXITED, eventHandler2);
            scene.removeEventFilter(MouseEvent.MOUSE_MOVED, eventHandler3);
            if (dragOverHandler != null) {
                scene.removeEventFilter(DragEvent.DRAG_OVER, dragOverHandler);
            }
        }
    }

    public void reRegister() {
        if (scene != null) {
            setRoot(scene, sceneRoot, content, hotSpotX.get(), hotSpotY.get());
        }
    }

    public SimpleIntegerProperty hotSpotXProperty() {
        return hotSpotX;
    }
    public SimpleIntegerProperty hotSpotYProperty() {
        return hotSpotY;
    }

    public void setHotSpot(int x, int y) {
        this.hotSpotX.set(x);
        this.hotSpotY.set(y);
    }
}
