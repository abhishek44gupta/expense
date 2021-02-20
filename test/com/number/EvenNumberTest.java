package com.number;


import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class EvenNumberTest {

    @Test
    public void filterEvenNumber_NullList(){
        List<Integer> result = EvenNumber.filterEvenNumbers(null);
        assertEquals(Collections.emptyList(), result);
    }

    @Test
    public void filterEvenNumber_EmptyList(){
        List<Integer> result = EvenNumber.filterEvenNumbers(Collections.emptyList());
        assertEquals(Collections.emptyList(), result);
    }

    @Test
    public void filterEvenNumber_OddNumbers(){
        List<Integer> oddNumbers  = Arrays.asList(1,3,5,9);
        List<Integer> result = EvenNumber.filterEvenNumbers(oddNumbers);
        assertEquals(Collections.emptyList(), result);
    }

    @Test
    public void filterEvenNumber_EvenNumbers(){
        List<Integer> evenNumbers = Arrays.asList(2,4,6,8);
        List<Integer> result = EvenNumber.filterEvenNumbers(evenNumbers);
        assertEquals(evenNumbers, result);
    }

    @Test
    public void filterEvenNumber_MixedSet(){
        List<Integer> numbers = Arrays.asList(1,2,3,4,6,8,9,200);
        List<Integer> result = EvenNumber.filterEvenNumbers(numbers);
        List<Integer> expected = Arrays.asList(2,4,6,8,200);
        assertEquals(expected, result);
    }
}
