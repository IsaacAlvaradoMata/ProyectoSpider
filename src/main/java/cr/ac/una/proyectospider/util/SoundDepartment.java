package cr.ac.una.proyectospider.util;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class SoundDepartment {
    private static MediaPlayer flipPlayer;
    private static MediaPlayer errorPlayer;
    private static MediaPlayer hintPlayer;
    private static MediaPlayer undoPlayer;
        private static MediaPlayer undoAllPlayer;

    public static void playFlip() {
        try {
            var url = SoundDepartment.class.getResource("/cr/ac/una/proyectospider/resources/flipcard.mp3");
            if (url == null) {
                System.err.println("[SoundDepartment] flipcard.mp3 not found in resources. No sound will be played.");
                return;
            }
            if (flipPlayer != null) {
                flipPlayer.stop();
            }
            Media sound = new Media(url.toExternalForm());
            flipPlayer = new MediaPlayer(sound);
            flipPlayer.play();
        } catch (Exception e) {
            System.err.println("[SoundDepartment] Could not play flip sound: " + e.getMessage());
        }
    }

    public static void playError() {
        try {
            var url = SoundDepartment.class.getResource("/cr/ac/una/proyectospider/resources/error.mp3");
            if (url == null) {
                System.err.println("[SoundDepartment] error.mp3 not found in resources. No error sound will be played.");
                return;
            }
            if (errorPlayer != null) {
                errorPlayer.stop();
            }
            Media sound = new Media(url.toExternalForm());
            errorPlayer = new MediaPlayer(sound);
            errorPlayer.play();
        } catch (Exception e) {
            System.err.println("[SoundDepartment] Could not play error sound: " + e.getMessage());
        }
    }

    public static void playHint() {
        try {
            var url = SoundDepartment.class.getResource("/cr/ac/una/proyectospider/resources/hint.mp3");
            if (url == null) {
                System.err.println("[SoundDepartment] hint.mp3 not found in resources. No hint sound will be played.");
                return;
            }
            if (hintPlayer != null) {
                hintPlayer.stop();
            }
            Media sound = new Media(url.toExternalForm());
            hintPlayer = new MediaPlayer(sound);
            hintPlayer.play();
        } catch (Exception e) {
            System.err.println("[SoundDepartment] Could not play hint sound: " + e.getMessage());
        }
    }

    public static void playUndo() {
        try {
            var url = SoundDepartment.class.getResource("/cr/ac/una/proyectospider/resources/undo.mp3");
            if (url == null) {
                System.err.println("[SoundDepartment] undo.mp3 not found in resources. No undo sound will be played.");
                return;
            }
            if (undoPlayer != null) {
                undoPlayer.stop();
            }
            Media sound = new Media(url.toExternalForm());
            undoPlayer = new MediaPlayer(sound);
            undoPlayer.play();
        } catch (Exception e) {
            System.err.println("[SoundDepartment] Could not play undo sound: " + e.getMessage());
        }
    }

    public static void playUndoAll() {
        try {
            var url = SoundDepartment.class.getResource("/cr/ac/una/proyectospider/resources/undoall.mp3");
            if (url == null) {
                System.err.println("[SoundDepartment] undoall.mp3 not found in resources. No undoall sound will be played.");
                return;
            }
            if (undoAllPlayer != null) {
                undoAllPlayer.stop();
            }
            Media sound = new Media(url.toExternalForm());
            undoAllPlayer = new MediaPlayer(sound);
            undoAllPlayer.play();
        } catch (Exception e) {
            System.err.println("[SoundDepartment] Could not play undoall sound: " + e.getMessage());
        }
    }
}
