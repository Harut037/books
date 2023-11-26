package com.appware_system.books.validations;

import lombok.Data;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Data
public class ValidationForUser {


    public String isValidFirstName(String firstName){
        String regex = "^[A-Z][a-z]*$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(firstName);
        if (matcher.matches()){
            return firstName;
        }
        return null;

    }

    public String isValidLastName(String lastName){
        String regex = "^[A-Z][a-z]*$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(lastName);
        if (matcher.matches()){
            return lastName;
        }
        return null;

    }



}
