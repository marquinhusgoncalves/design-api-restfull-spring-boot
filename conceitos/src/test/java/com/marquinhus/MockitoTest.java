package com.marquinhus;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class MockitoTest {
    @Mock
    List<String> lista = Mockito.mock(ArrayList.class);

    @Test
    public void primeiroTesteMockito() {
        Mockito.when(lista.size()).thenReturn(20);

        int size = 0;
        if (1 == 1) {
            size = lista.size();
        }

        Assertions.assertThat(size).isEqualTo(20);
        Mockito.verify(lista, Mockito.times(1)).size();
    }
}
