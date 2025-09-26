package org.oscar.utils;


public interface IGenerator {
    String createUser(String firstName, String lastName);
    public String generatePass();
    boolean isOccupied(String firstName, String lastName);

}
