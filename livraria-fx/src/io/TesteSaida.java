package io;

import java.io.IOException;
import java.util.Arrays;

import br.com.casadocodigo.livraria.Autor;
import br.com.casadocodigo.livraria.produtos.Livro;
import br.com.casadocodigo.livraria.produtos.LivroFisico;
import util.Exportador;

public class TesteSaida {

	public static void main(String[] args) throws IOException {

		Livro livro = new LivroFisico(new Autor());
		livro.setNome("Java 8 Prático");
		livro.setDescricao("Novos recursos da linguagem");
		livro.setValor(59.90);
		livro.setIsbn("978-85-66250-46-6");
		Livro maisUmlivro = new LivroFisico(new Autor());
		maisUmlivro.setNome("Desbravando a O.O.");
		maisUmlivro.setDescricao("Livro de Java e O.O");
		maisUmlivro.setValor(59.90);
		maisUmlivro.setIsbn("321-54-67890-11-2");
		new Exportador().paraCSV(Arrays.asList(livro, maisUmlivro));

	}

}
