package com.marquinhus;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class PrimeiroTeste {

    Calculadora calculadora;
    int numero1 = 10 , numero2 = 5;

    @BeforeEach
    public void setUp() {
        calculadora = new Calculadora();
    }

    @Test
    public void deveSomar2Numeros() {
        // execução
        int resultado = calculadora.somar(numero1, numero2);

        // verificações
        assertThat(resultado).isEqualTo(15);
    }

    @Test // (expected = RuntimeException.class)
    public void naoDeveSomarNumerosNegativos() {
        // cenário
        int numero1 = -10 , numero2 = 5;

        // execução
        org.junit.jupiter.api.Assertions.assertThrows(RuntimeException.class, () -> calculadora.somar(numero1, numero2));
    }

    @Test
    public void deveSubtrair2Numeros() {
        // execução
        int resultado = calculadora.subtrair(numero1, numero2);

        // verificações
        assertThat(resultado).isEqualTo(5);
    }

    @Test
    public void deveMultiplicar2Numeros() {
        // execução
        int resultado = calculadora.multiplicar(numero1, numero2);

        // verificações
        assertThat(resultado).isEqualTo(50);
    }

    @Test
    public void deveDividir2Numeros() {
        // execução
        float resultado = calculadora.dividir(numero1, numero2);

        // verificações
        assertThat(resultado).isEqualTo(2);
    }

    @Test
    public void naoDeveDividirNumerosPorZero() {
        // cenário
        int numero1 = 0 , numero2 = 5;

        // execução
        org.junit.jupiter.api.Assertions.assertThrows(RuntimeException.class, () -> calculadora.dividir(numero1, numero2));
    }
}

class Calculadora {
    int somar(int num, int num2) {
        if (num < 0 || num2 < 0) {
            throw new RuntimeException("Não é permitidosomar números negativos");
        }
        return num + num2;
    }

    int subtrair(int num, int num2) {
        return num - num2;
    }

    int multiplicar(int num, int num2) {
        return num * num2;
    }

    float dividir(int num, int num2) {
        if (num == 0 || num2 == 0) {
            throw new RuntimeException("Não é possivel dividir por 0");
        }
        return num / num2;
    }
}