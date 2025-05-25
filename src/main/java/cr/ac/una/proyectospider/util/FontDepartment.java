package cr.ac.una.proyectospider.util;

import javafx.scene.text.Font;

public class FontDepartment {

    public static void loadFonts() {
        try {

            Font.loadFont(FontDepartment.class.getResourceAsStream("/cr/ac/una/proyectospider/resources/raster.ttf"), 16);
            Font.loadFont(FontDepartment.class.getResourceAsStream("/cr/ac/una/proyectospider/resources/Cynatar.otf"), 16);
            Font.loadFont(FontDepartment.class.getResourceAsStream("/cr/ac/una/proyectospider/resources/joystix.otf"), 16);

            System.out.println("✅ Fuentes cargadas correctamente.");
        } catch (Exception e) {
            System.out.println("⚠️ Error cargando fuentes: " + e.getMessage());
        }
    }
}
