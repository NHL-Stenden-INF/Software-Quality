package com.jabberpoint.infrastructure;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.scene.image.Image;

/**
 * A wrapper interface for GraphicsContext to facilitate testing.
 * This allows us to mock the GraphicsContext in tests since the actual
 * GraphicsContext class is final and cannot be mocked directly.
 */
public interface GraphicsContextWrapper {
    
    void clearRect(double x, double y, double w, double h);
    
    void setFill(Color color);
    
    void setStroke(Color color);
    
    void setLineWidth(double width);
    
    void setFont(Font font);
    
    void setTextAlign(TextAlignment align);
    
    void fillText(String text, double x, double y);
    
    void strokeText(String text, double x, double y);
    
    void fillRect(double x, double y, double width, double height);
    
    void strokeRect(double x, double y, double width, double height);
    
    void fillOval(double x, double y, double width, double height);
    
    void strokeOval(double x, double y, double width, double height);
    
    void drawImage(Image image, double x, double y);
    
    void drawImage(Image image, double x, double y, double width, double height);
    
    /**
     * Creates a wrapper around a real GraphicsContext
     * @param gc The real GraphicsContext to wrap
     * @return A wrapper that delegates to the real GraphicsContext
     */
    static GraphicsContextWrapper wrap(GraphicsContext gc) {
        return new GraphicsContextWrapper() {
            @Override
            public void setFill(Color color) {
                gc.setFill(color);
            }
            
            @Override
            public void setStroke(Color color) {
                gc.setStroke(color);
            }
            
            @Override
            public void setLineWidth(double width) {
                gc.setLineWidth(width);
            }
            
            @Override
            public void setFont(Font font) {
                gc.setFont(font);
            }
            
            @Override
            public void setTextAlign(TextAlignment align) {
                gc.setTextAlign(align);
            }
            
            @Override
            public void fillText(String text, double x, double y) {
                gc.fillText(text, x, y);
            }
            
            @Override
            public void strokeText(String text, double x, double y) {
                gc.strokeText(text, x, y);
            }
            
            @Override
            public void fillRect(double x, double y, double width, double height) {
                gc.fillRect(x, y, width, height);
            }
            
            @Override
            public void strokeRect(double x, double y, double width, double height) {
                gc.strokeRect(x, y, width, height);
            }
            
            @Override
            public void fillOval(double x, double y, double width, double height) {
                gc.fillOval(x, y, width, height);
            }
            
            @Override
            public void strokeOval(double x, double y, double width, double height) {
                gc.strokeOval(x, y, width, height);
            }
            
            @Override
            public void drawImage(Image image, double x, double y) {
                gc.drawImage(image, x, y);
            }
            
            @Override
            public void drawImage(Image image, double x, double y, double width, double height) {
                gc.drawImage(image, x, y, width, height);
            }
            
            @Override
            public void clearRect(double x, double y, double w, double h) {
                gc.clearRect(x, y, w, h);
            }
        };
    }
} 