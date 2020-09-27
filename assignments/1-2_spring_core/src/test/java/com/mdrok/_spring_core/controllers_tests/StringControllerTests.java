package com.mdrok._spring_core.controllers_tests;

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
public class StringControllerTests {

    @Autowired
    @InjectMocks
    StringController stringController;

    @Mock
    StringService stringService;

    @Mock
    StringRepository stringRepository;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void invert_whenFilledStringIsInput_thenInvertedStringIsReturned(){
        // arrange
        String input = "hello";
        String expectedResult = "olleh";

        Mockito.when(stringService.invert(input)).thenReturn(expectedResult);

        // act
        String realResult = stringController.invert(input);

        // assert
        assertThat(realResult).isEqualTo(expectedResult);
    }

    @Test
    public void invert_whenEmptyStringIsInput_thenEmptyStringIsReturned(){
        // arrange
        String input = "";
        String expectedResult = "";

        Mockito.when(stringService.invert(input)).thenReturn(expectedResult);

        // act
        String realResult = stringController.invert(input);

        // assert
        assertThat(realResult).isEqualTo(expectedResult);
    }

    @Test
    public void invert_whenNullIsInput_thenNullIsReturned(){
        // arrange
        String input = null;
        String expectedResult = null;

        Mockito.when(stringService.invert(input)).thenReturn(expectedResult);

        // act
        String realResult = stringController.invert(input);

        // assert
        assertThat(realResult).isEqualTo(expectedResult);
    }

    @Test
    public void capitalise_whenFilledStringIsInput_thenInvertedStringIsReturned(){
        // arrange
        String input = "hello";
        String expectedResult = "HELLO";

        Mockito.when(stringService.capitalise(input)).thenReturn(expectedResult);

        // act
        String realResult = stringController.capitalise(input);

        // assert
        assertThat(realResult).isEqualTo(expectedResult);
    }

    @Test
    public void capitalise_whenEmptyStringIsInput_thenEmptyStringIsReturned(){
        // arrange
        String input = "";
        String expectedResult = "";

        Mockito.when(stringService.capitalise(input)).thenReturn(expectedResult);

        // act
        String realResult = stringController.capitalise(input);

        // assert
        assertThat(realResult).isEqualTo(expectedResult);
    }

    @Test
    public void capitalise_whenNullIsInput_thenNullIsReturned(){
        // arrange
        String input = null;
        String expectedResult = null;

        Mockito.when(stringService.capitalise(input)).thenReturn(expectedResult);

        // act
        String realResult = stringController.capitalise(input);

        // assert
        assertThat(realResult).isEqualTo(expectedResult);
    }
}
