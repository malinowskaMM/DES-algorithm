package project.model;

import java.util.Random;

public class Key {
    public String getKey() {
        Random random = new Random();
        StringBuilder key = new StringBuilder();
        for(int i = 0; i < 8; i++) {
            char temp = (char) ((char)random.nextInt(126-65)+65);
            key.append(temp);
        }
        return key.toString();
    }
}
