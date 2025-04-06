package com.jabberpoint.style;

public enum FontSize {
    SMALL(16),
    MEDIUM(24),
    LARGE(32),
    EXTRA_LARGE(52);
    
    private final double size;

    FontSize(double size) {
        this.size = size;
    }

    public double getJavaFXFontSize() {
        return size;
    }

    public double getSize() {
        return size;
    }
    
    /**
     * Safely converts a string to FontSize, defaulting to MEDIUM if invalid
     * @param sizeStr The string representation of the font size
     * @return The corresponding FontSize or MEDIUM if not found
     */
    public static FontSize fromString(String sizeStr) {
        if (sizeStr == null || sizeStr.isEmpty()) {
            System.err.println("Empty fontSize value, applying default MEDIUM");
            return FontSize.MEDIUM;
        }
        
        try {
            return FontSize.valueOf(sizeStr.toUpperCase());
        } catch (IllegalArgumentException e) {
            System.err.println("Invalid fontSize value '" + sizeStr + "', applying default MEDIUM");
            return FontSize.MEDIUM;
        }
    }
}
