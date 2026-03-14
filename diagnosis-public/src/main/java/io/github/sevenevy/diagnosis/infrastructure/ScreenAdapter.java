package io.github.sevenevy.diagnosis.infrastructure;

public interface ScreenAdapter {
    
    default void display(String text) {
        System.out.println(text);
    };
}
