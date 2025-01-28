package db;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class MockitoTest {
    @Test
    void testMock() {
        // Criar mock de uma classe
        MyClass myClassMock = Mockito.mock(MyClass.class);

        // Configurar o mock
        when(myClassMock.getValue()).thenReturn("Mocked Value");

        // Verificar o comportamento
        assertEquals("Mocked Value", myClassMock.getValue());
    }
}

class MyClass {
    String getValue() {
        return "Real Value";
    }
}
