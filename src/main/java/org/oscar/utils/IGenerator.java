package org.oscar.utils;


public interface IGenerator {
    String createUser(String firstName, String lastName);
    public String generatePass();
    long isOccupied(String firstName, String lastName);

}
