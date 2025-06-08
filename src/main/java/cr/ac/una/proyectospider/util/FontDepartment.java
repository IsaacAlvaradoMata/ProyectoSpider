package cr.ac.una.proyectospider.util;

import javafx.scene.text.Font;

public class FontDepartment {

    public static void loadFonts() {
        try {

            Font.loadFont(FontDepartment.class.getResourceAsStream("/cr/ac/una/proyectospider/resources/Raster.ttf"), 16);
            Font.loadFont(FontDepartment.class.getResourceAsStream("/cr/ac/una/proyectospider/resources/Cynatar.ttf"), 16);
            Font.loadFont(FontDepartment.class.getResourceAsStream("/cr/ac/una/proyectospider/resources/Joystix.ttf"), 16);

            System.out.println("✅ Fuentes cargadas correctamente.");
        } catch (Exception e) {
            System.out.println("⚠️ Error cargando fuentes: " + e.getMessage());
        }
    }
}
