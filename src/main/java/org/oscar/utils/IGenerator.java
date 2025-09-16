package org.oscar.utils;

import org.oscar.model.User;

import java.util.Map;


public interface IGenerator {
    String createUser(String firstName, String lastName, Map<?,? extends User> map);
    public String generatePass();
    boolean isOccupied(String firstName, String lastName, Map<?,? extends User> map);

}
