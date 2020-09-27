package com.mdrok._spring_core.services_tests;

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
public class StringServiceTests {

    @Autowired
    @InjectMocks
    StringService stringService;

    @Mock
    StringRepository stringRepository;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void count_whenInputIsValidStringAndAlreadyAdded_ReturnIntegerWordCount(){
        // arrange
        String input = "hello there";
        Integer expectedResult = 2;

        Mockito.when(stringRepository.alreadyAdded(input)).thenReturn(true);
        Mockito.when(stringRepository.getWordCount(input)).thenReturn(expectedResult);
        Mockito.when(stringRepository.add(input, expectedResult)).thenReturn(true);

        // act
        Integer realResult = stringService.count(input);

        // assert
        assertThat(realResult).isEqualTo(expectedResult);
    }

    @Test
    public void count_whenInputIsValidStringAndNotAlreadyAdded_ReturnIntegerWordCount(){
        // arrange
        String input = "hello there";
        Integer expectedResult = 2;

        Mockito.when(stringRepository.alreadyAdded(input)).thenReturn(false);
        Mockito.when(stringRepository.getWordCount(input)).thenReturn(expectedResult);
        Mockito.when(stringRepository.add(input, expectedResult)).thenReturn(true);

        // act
        Integer realResult = stringService.count(input);

        // assert
        assertThat(realResult).isEqualTo(expectedResult);
    }
}
