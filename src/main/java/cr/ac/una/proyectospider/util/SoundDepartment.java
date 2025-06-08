package cr.ac.una.proyectospider.util;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class SoundDepartment {
    private static MediaPlayer flipPlayer;
    private static MediaPlayer errorPlayer;
    private static MediaPlayer hintPlayer;
    private static MediaPlayer undoPlayer;
    private static MediaPlayer undoAllPlayer;
    private static MediaPlayer Alert;
    private static MediaPlayer Click;
    private static MediaPlayer Transition;
    private static MediaPlayer Transition2;
    private static MediaPlayer RadioButton;
    private static MediaPlayer Win;
    private static MediaPlayer Deal;
    private static MediaPlayer Surrender;
    private static MediaPlayer ExitnSave;
    private static MediaPlayer PutCard;
    private static MediaPlayer Spider1;
    private static MediaPlayer Spider2;
    private static MediaPlayer Spider3;
    private static MediaPlayer Spider4;
    private static MediaPlayer Victory;

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
            var url = SoundDepartment.class.getResource("/cr/ac/una/proyectospider/resources/pista.wav");
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
            var url = SoundDepartment.class.getResource("/cr/ac/una/proyectospider/resources/UndoAll.mp3");
            if (url == null) {
                System.err.println("[SoundDepartment] UndoAll.mp3 not found in resources. No undoall sound will be played.");
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


    public static void playAlert() {
        try {
            var url = SoundDepartment.class.getResource("/cr/ac/una/proyectospider/resources/Alerts.wav");
            if (url == null) {
                System.err.println("[SoundDepartment] Alerts.wav not found in resources. No undoall sound will be played.");
                return;
            }
            if (Alert != null) {
                Alert.stop();
            }
            Media sound = new Media(url.toExternalForm());
            Alert = new MediaPlayer(sound);
            Alert.setVolume(0.2);
            Alert.play();
        } catch (Exception e) {
            System.err.println("[SoundDepartment] Could not play undoall sound: " + e.getMessage());
        }
    }

    public static void playClick() {
        try {
            var url = SoundDepartment.class.getResource("/cr/ac/una/proyectospider/resources/click.wav");
            if (url == null) {
                System.err.println("[SoundDepartment] click.wav not found in resources. No undoall sound will be played.");
                return;
            }
            if (Click != null) {
                Click.stop();
            }
            Media sound = new Media(url.toExternalForm());
            Click = new MediaPlayer(sound);

            Click.play();
        } catch (Exception e) {
            System.err.println("[SoundDepartment] Could not play undoall sound: " + e.getMessage());
        }
    }

    public static void playTransition() {
        try {
            var url = SoundDepartment.class.getResource("/cr/ac/una/proyectospider/resources/Transition.wav");
            if (url == null) {
                System.err.println("[SoundDepartment] Transition.wav not found in resources. No undoall sound will be played.");
                return;
            }
            if (Transition != null) {
                Transition.stop();
            }
            Media sound = new Media(url.toExternalForm());
            Transition = new MediaPlayer(sound);
            Transition.play();
        } catch (Exception e) {
            System.err.println("[SoundDepartment] Could not play undoall sound: " + e.getMessage());
        }
    }

    public static void playTransition2() {
        try {
            var url = SoundDepartment.class.getResource("/cr/ac/una/proyectospider/resources/Transition2.wav");
            if (url == null) {
                System.err.println("[SoundDepartment] Transition2.wav not found in resources. No undoall sound will be played.");
                return;
            }
            if (Transition2 != null) {
                Transition2.stop();
            }
            Media sound = new Media(url.toExternalForm());
            Transition2 = new MediaPlayer(sound);
            Transition2.play();
        } catch (Exception e) {
            System.err.println("[SoundDepartment] Could not play undoall sound: " + e.getMessage());
        }
    }

    public static void playRadioButton() {
        try {
            var url = SoundDepartment.class.getResource("/cr/ac/una/proyectospider/resources/RadioButtons.mp3");
            if (url == null) {
                System.err.println("[SoundDepartment] RadioButtons.mp3 not found in resources. No undoall sound will be played.");
                return;
            }
            if (RadioButton != null) {
                RadioButton.stop();
            }
            Media sound = new Media(url.toExternalForm());
            RadioButton = new MediaPlayer(sound);
            RadioButton.play();
        } catch (Exception e) {
            System.err.println("[SoundDepartment] Could not play undoall sound: " + e.getMessage());
        }
    }

    public static void playWin() {
        try {
            var url = SoundDepartment.class.getResource("/cr/ac/una/proyectospider/resources/EnterWin.mp3");
            if (url == null) {
                System.err.println("[SoundDepartment] EnterWin.mp3 not found in resources. No undoall sound will be played.");
                return;
            }
            if (Win != null) {
                Win.stop();
            }
            Media sound = new Media(url.toExternalForm());
            Win = new MediaPlayer(sound);
            Win.play();
        } catch (Exception e) {
            System.err.println("[SoundDepartment] Could not play undoall sound: " + e.getMessage());
        }
    }

    public static void playDeal() {
        try {
            var url = SoundDepartment.class.getResource("/cr/ac/una/proyectospider/resources/Deal.wav");
            if (url == null) {
                System.err.println("[SoundDepartment] Deal.wav not found in resources. No undoall sound will be played.");
                return;
            }
            if (Deal != null) {
                Deal.stop();
            }
            Media sound = new Media(url.toExternalForm());
            Deal = new MediaPlayer(sound);
            Deal.play();
        } catch (Exception e) {
            System.err.println("[SoundDepartment] Could not play undoall sound: " + e.getMessage());
        }
    }

    public static void playSurrender() {
        try {
            var url = SoundDepartment.class.getResource("/cr/ac/una/proyectospider/resources/gameover.mp3");
            if (url == null) {
                System.err.println("[SoundDepartment] gameover.mp3 not found in resources. No undoall sound will be played.");
                return;
            }
            if (Surrender != null) {
                Surrender.stop();
            }
            Media sound = new Media(url.toExternalForm());
            Surrender = new MediaPlayer(sound);
            Surrender.play();
        } catch (Exception e) {
            System.err.println("[SoundDepartment] Could not play undoall sound: " + e.getMessage());
        }
    }

    public static void playExitnSave() {
        try {
            var url = SoundDepartment.class.getResource("/cr/ac/una/proyectospider/resources/Save.mp3");
            if (url == null) {
                System.err.println("[SoundDepartment] Save.mp3 not found in resources. No undoall sound will be played.");
                return;
            }
            if (ExitnSave != null) {
                ExitnSave.stop();
            }
            Media sound = new Media(url.toExternalForm());
            ExitnSave = new MediaPlayer(sound);
            ExitnSave.play();
        } catch (Exception e) {
            System.err.println("[SoundDepartment] Could not play undoall sound: " + e.getMessage());
        }
    }

    public static void playSpider1() {
        try {
            var url = SoundDepartment.class.getResource("/cr/ac/una/proyectospider/resources/Spider1.mp3");
            if (url == null) {
                System.err.println("[SoundDepartment] Spider1 not found in resources. No undoall sound will be played.");
                return;
            }
            if (Spider1 != null) {
                Spider1.stop();
            }
            Media sound = new Media(url.toExternalForm());
            Spider1 = new MediaPlayer(sound);
            Spider1.play();
        } catch (Exception e) {
            System.err.println("[SoundDepartment] Could not play undoall sound: " + e.getMessage());
        }
    }

    public static void playSpider2() {
        try {
            var url = SoundDepartment.class.getResource("/cr/ac/una/proyectospider/resources/Spider2.mp3");
            if (url == null) {
                System.err.println("[SoundDepartment] Spider2 not found in resources. No undoall sound will be played.");
                return;
            }
            if (Spider2 != null) {
                Spider2.stop();
            }
            Media sound = new Media(url.toExternalForm());
            Spider2 = new MediaPlayer(sound);
            Spider2.play();
        } catch (Exception e) {
            System.err.println("[SoundDepartment] Could not play undoall sound: " + e.getMessage());
        }
    }

    public static void playSpider3() {
        try {
            var url = SoundDepartment.class.getResource("/cr/ac/una/proyectospider/resources/Spider3.mp3");
            if (url == null) {
                System.err.println("[SoundDepartment] Spider3.mp3 not found in resources. No undoall sound will be played.");
                return;
            }
            if (Spider3 != null) {
                Spider3.stop();
            }
            Media sound = new Media(url.toExternalForm());
            Spider3 = new MediaPlayer(sound);
            Spider3.play();
        } catch (Exception e) {
            System.err.println("[SoundDepartment] Could not play undoall sound: " + e.getMessage());
        }
    }

    public static void playSpider4() {
        try {
            var url = SoundDepartment.class.getResource("/cr/ac/una/proyectospider/resources/Spider4.mp3");
            if (url == null) {
                System.err.println("[SoundDepartment] Spider4.mp3 not found in resources. No undoall sound will be played.");
                return;
            }
            if (Spider4 != null) {
                Spider4.stop();
            }
            Media sound = new Media(url.toExternalForm());
            Spider4 = new MediaPlayer(sound);
            Spider4.play();
        } catch (Exception e) {
            System.err.println("[SoundDepartment] Could not play undoall sound: " + e.getMessage());
        }
    }

    public static void play(String soundName) {
        switch (soundName) {
            case "Spider1": playSpider1(); break;
            case "Spider2": playSpider2(); break;
            case "Spider3": playSpider3(); break;
            case "Spider4": playSpider4(); break;
            // Puedes agregar más casos si necesitas otros sonidos
            default:
                System.err.println("[SoundDepartment] Unknown sound: " + soundName);
        }
    }


    public static void playPutCard() {
        try {
            var url = SoundDepartment.class.getResource("/cr/ac/una/proyectospider/resources/card.mp3");
            if (url == null) {
                System.err.println("[SoundDepartment] card.mp3 not found in resources. No undoall sound will be played.");
                return;
            }
            if (PutCard != null) {
                PutCard.stop();
            }
            Media sound = new Media(url.toExternalForm());
            PutCard = new MediaPlayer(sound);
            PutCard.play();
        } catch (Exception e) {
            System.err.println("[SoundDepartment] Could not play undoall sound: " + e.getMessage());
        }
    }

    public static void playVictory() {
        try {
            var url = SoundDepartment.class.getResource("/cr/ac/una/proyectospider/resources/CYBERPUNK.mp3");
            if (url == null) {
                System.err.println("[SoundDepartment] CYBERPUNK.mp3 not found in resources. No undoall sound will be played.");
                return;
            }
            if (Victory != null) {
                Victory.stop();
            }
            Media sound = new Media(url.toExternalForm());
            Victory = new MediaPlayer(sound);
            Victory.play();
        } catch (Exception e) {
            System.err.println("[SoundDepartment] Could not play undoall sound: " + e.getMessage());
        }
    }





}
