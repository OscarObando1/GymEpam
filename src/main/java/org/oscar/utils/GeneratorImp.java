package org.oscar.utils;

import org.oscar.model.User;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Random;

@Component
public class GeneratorImp implements IGenerator {

    public String createUser(String firstName, String lastName, Map<?,? extends User> map){
        String username;
        if(isOccupied(firstName,lastName,map)){
            username = firstName+"."+lastName+AuxCounter.auxCounter;
            AuxCounter.auxCounter++;
        }else username = firstName+"."+lastName;
        return username;
    }

    public String generatePass(){
        Random random = new Random();
        String password ="";
        for (int i = 0; i < 10; i++) {
            char temp =(char) (65 + random.nextInt(90 - 65 + 1));
            password+=temp;
        }
        return password;
    }

    public boolean isOccupied(String firstName, String lastName, Map<?,? extends User> map){
        boolean isOccupied = map.values().stream().anyMatch(user -> user.getFirstName().equalsIgnoreCase(firstName)&&
                user.getLastName().equalsIgnoreCase(lastName));
        return isOccupied;
    }
}
