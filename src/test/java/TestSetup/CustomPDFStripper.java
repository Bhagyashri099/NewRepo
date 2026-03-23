package TestSetup;

import java.io.IOException;

import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.text.TextPosition;

public class CustomPDFStripper extends PDFTextStripper {

    private String lastFontName = "";
    private String lastColor = "";

    public CustomPDFStripper() throws IOException {
        super();
    }

    @Override
    protected void processTextPosition(TextPosition text) {
    	
    	String currentFont = (text.getFont() != null) ? text.getFont().getName() : "";
        
       //   capture the font if it's not empty/null
        if (currentFont != null && !currentFont.trim().isEmpty()) {
            lastFontName = currentFont;
            System.out.println(lastFontName);
        }
        String color=getGraphicsState().getNonStrokingColor().getColorSpace().getName();
       
        System.out.print(color);
        
        
        try {
            
            float[] components = getGraphicsState().getNonStrokingColor().getComponents();
            
            // Convert the float array into a string matching Excel: [0.0, 0.0, 0.0]
            lastColor = java.util.Arrays.toString(components);
        } catch (Exception e) {
            lastColor = "Unknown";
        }
        super.processTextPosition(text);
    }

    // Method called by Step Definition to validate Font
    public String getLastFontName() {
        return lastFontName;
        
    }

    // Method called by Step Definition to validate Color
    public String getLastColor() {
        return lastColor;
    }
}
