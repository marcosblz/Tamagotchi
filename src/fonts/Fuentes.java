package fonts;

import java.awt.Font;
import java.io.InputStream;

public class Fuentes {
    private Font font = null;
    public String ARCADE = "ARCADE.TTF";

    //https://www.youtube.com/watch?v=7bgCt-ZPK0c&ab_channel=NoeMercado
    
    public Font fuente(String fontName, int estilo, float tamanio)
    {
         try {
            //Se carga la fuente
            InputStream is =  getClass().getResourceAsStream(fontName);
            font = Font.createFont(Font.TRUETYPE_FONT, is);
        } catch (Exception ex) {
            
            System.err.println(fontName + " No se cargo la fuente");  
            
        }
        Font tfont = font.deriveFont(estilo, tamanio);
        return tfont;
    }
}