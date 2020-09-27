package com.mdrok._spring_core.repositories_tests;

import com.mdrok._spring_core.Application;
import com.mdrok._spring_core.controllers.StringController;
import com.mdrok._spring_core.repositories.StringRepository;
import com.mdrok._spring_core.services.StringService;
import static org.assertj.core.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = { Application.class })
public class StringRepositoryTests {

    @Autowired
    @InjectMocks
    StringRepository stringRepository;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void add_whenInputStringIsNull_thenReturnIsFalse(){
        // arrange
        String arg1 = null;
        Integer arg2 = 5;
        Boolean expectedResult = false;

        // act
        Boolean realResult = stringRepository.add(arg1, arg2);

        // assert
        assertThat(realResult).isEqualTo(expectedResult);
    }

    @Test
    public void add_whenInputStringIsEmptyString_thenReturnIsFalse(){
        // arrange
        String arg1 = "";
        Integer arg2 = 5;
        Boolean expectedResult = false;

        // act
        Boolean realResult = stringRepository.add(arg1, arg2);

        // assert
        assertThat(realResult).isEqualTo(expectedResult);
    }

    @Test
    public void add_whenInputIntegerIsZero_thenReturnIsFalse(){
        // arrange
        String arg1 = "some words";
        Integer arg2 = 0;
        Boolean expectedResult = false;

        // act
        Boolean realResult = stringRepository.add(arg1, arg2);

        // assert
        assertThat(realResult).isEqualTo(expectedResult);
    }

    @Test
    public void add_whenInputIntegerAndStringAreValid_thenReturnIsTrue(){
        // arrange
        String arg1 = "some words";
        Integer arg2 = 2;
        Boolean expectedResult = true;

        // act
        Boolean realResult = stringRepository.add(arg1, arg2);

        // assert
        assertThat(realResult).isEqualTo(expectedResult);
    }

    @Test
    public void alreadyAdded_whenInputIsNull_thenReturnIsFalse(){

        // arrange
        String input = null;
        stringRepository.add("some words", 2);
        Boolean expectedResult = false;

        // act
        Boolean realResult = stringRepository.alreadyAdded(input);

        // assert
        assertThat(realResult).isEqualTo(expectedResult);
    }

    @Test
    public void alreadyAdded_whenInputIsEmptyString_thenReturnIsFalse(){
        // arrange
        String input = "";
        stringRepository.add("some words", 2);
        Boolean expectedResult = false;

        // act
        Boolean realResult = stringRepository.alreadyAdded(input);

        // assert
        assertThat(realResult).isEqualTo(expectedResult);
    }

    @Test
    public void alreadyAdded_whenInputIsValidStringAndAlreadyAdded_thenReturnIsTrue(){
        // arrange
        String input = "some words";
        stringRepository.add("some words", 2);
        Boolean expectedResult = true;

        // act
        Boolean realResult = stringRepository.alreadyAdded(input);

        // assert
        assertThat(realResult).isEqualTo(expectedResult);
    }

    @Test
    public void alreadyAdded_whenInputIsValidStringAndNotAdded_thenReturnIsFalse(){
        // arrange
        String input = "some other words";
        stringRepository.add("some words", 2);
        Boolean expectedResult = false;

        // act
        Boolean realResult = stringRepository.alreadyAdded(input);

        // assert
        assertThat(realResult).isEqualTo(expectedResult);
    }

    @Test
    public void getWordCount_whenInputIsNull_thenReturnIsIntegerWithValueZero(){

        // arrange
        String input = null;
        stringRepository.add("some words", 2);
        Integer expectedResult = 0;

        // act
        Integer realResult = stringRepository.getWordCount(input);

        // assert
        assertThat(realResult).isEqualTo(expectedResult);
    }

    @Test
    public void getWordCount_whenInputIsEmptyString_thenReturnIsIntegerWithValueZero(){

        // arrange
        String input = "";
        stringRepository.add("some words", 2);
        Integer expectedResult = 0;

        // act
        Integer realResult = stringRepository.getWordCount(input);

        // assert
        assertThat(realResult).isEqualTo(expectedResult);
    }

    @Test
    public void getWordCount_whenInputIsValidStringAndAlreadyAdded_thenReturnIsInteger(){

        // arrange
        String input = "some words";
        stringRepository.add("some words", 2);
        Integer expectedResult = 2;

        // act
        Integer realResult = stringRepository.getWordCount(input);

        // assert
        assertThat(realResult).isEqualTo(expectedResult);
    }

    @Test
    public void getWordCount_whenInputIsValidStringAndNotAlreadyAdded_thenReturnIsNull(){

        // arrange
        String input = "some other words";
        stringRepository.add("some words", 2);
        Integer expectedResult = null;

        // act
        Integer realResult = stringRepository.getWordCount(input);

        // assert
        assertThat(realResult).isEqualTo(expectedResult);
    }
}
