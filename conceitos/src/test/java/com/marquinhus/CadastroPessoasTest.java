package com.marquinhus;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class CadastroPessoasTest {

    @Test
    @DisplayName("Deve criar o Cadastro de pessoas")
    public void deveriaCriarOCadastroDePessoas () {
        // cenário e execução
        CadastroPessoas cadastro = new CadastroPessoas();

        Assertions.assertThat(cadastro.getPessoas()).isEmpty();
    }

    @Test
    @DisplayName("Deve adicionar uma pessoa")
    public void deveAdicionarUmaPessoa () {
        // cenário
        CadastroPessoas cadastro = new CadastroPessoas();
        Pessoa pessoa = new Pessoa();
        pessoa.setNome("Wilson");

        // execução
        cadastro.adicionar(pessoa);

        //verificação
        Assertions
                .assertThat(cadastro.getPessoas())
                .isNotEmpty()
                .hasSize(1)
                .contains(pessoa);
    }

    @Test
    @DisplayName("Não deve adicionar pessoa com nome vazio")
    public void naoDeveAdicionarPessoaComNomeVazio () {
        // cenário
        CadastroPessoas cadastro = new CadastroPessoas();
        Pessoa pessoa = new Pessoa();

        // execução
        org.junit.jupiter.api.Assertions.assertThrows(PessoaSemNomeException.class, () -> cadastro.adicionar(pessoa));
    }

    @Test
    @DisplayName("Deve remover uma pessoa")
    public void deveRemoverPessoa () {
        // cenário
        CadastroPessoas cadastro = new CadastroPessoas();
        Pessoa pessoa = new Pessoa();
        pessoa.setNome("Wilson");
        cadastro.adicionar(pessoa);

        // execução
        cadastro.remover(pessoa);

        // validação
        Assertions.assertThat(cadastro.getPessoas())
                .isEmpty();
    }

    @Test
    @DisplayName("Deve lançar erro ao tentar remover uma pessoa de uma lista vazia")
    public void deveLançarErroAoTentarRemoverPessoaDeListaVazia () {
        // cenário
        CadastroPessoas cadastro = new CadastroPessoas();
        Pessoa pessoa = new Pessoa();

        // execução
        org.junit.jupiter.api.Assertions.assertThrows(CadastroVazioException.class, () -> cadastro.remover(pessoa));
    }
}
