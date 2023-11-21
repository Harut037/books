package com.appware_system.books.validations;

import com.appware_system.books.model.domain.Book;
import com.appware_system.books.service.BooksService;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;


import java.sql.Date;
import java.time.LocalDate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Data
@NoArgsConstructor
public class ValidationForBook {



    public String isValidAuthorName(String authorName){
        String regex = "^[A-Z][a-z]*$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(authorName);
        if (matcher.matches()){
            return authorName;
        }
        return null;

    }

    public String isValidAuthorSurname(String authorSurname){
        String regex = "^[A-Z][a-z]*$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(authorSurname);
        if (matcher.matches()){
            return authorSurname;
        }
        return null;

    }


//    public static void main(String[] args) {
//        ValidationForBook vfb = new ValidationForBook();
//        System.out.printf("", vfb.isValidAuthor("Harutyun", "Nersesyan"));
//    }

    public Date isValidateDate(Date date) {
        String forDate = date.toString();
        LocalDate localDate = LocalDate.parse(forDate);
        if (localDate.isBefore(LocalDate.now())) {
            return date;
        }
        return null;
    }

    public boolean isValidBook(Book book) {
        if (isValidAuthorName(book.getAuthorName()) == null || isValidAuthorSurname(book.getAuthorSurname()) == null) {
            throw new IllegalArgumentException("Invalid author`s name or surname");
        }
        if (isValidateDate(book.getYear()) == null) {
            throw new IllegalArgumentException("The year can`t be after than current date");
        }
        return true;
    }
}
