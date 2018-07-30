package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import br.com.casadocodigo.livraria.Autor;
import br.com.casadocodigo.livraria.produtos.LivroFisico;
import br.com.casadocodigo.livraria.produtos.Produto;
import db.ConnectionFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ProdutoDAO {

	public ObservableList<Produto> lista() {
		ObservableList<Produto> produtos = FXCollections.observableArrayList();
		try (Connection conn = new ConnectionFactory().getConnection()) {
			PreparedStatement ps;
			ResultSet res;

			ps = conn.prepareStatement("select * from produtos");
			res = ps.executeQuery();
			while (res.next()) {
				LivroFisico livro = new LivroFisico(new Autor());
				livro.setNome(res.getString("nome"));
				livro.setDescricao(res.getString("descricao"));
				livro.setValor(res.getDouble("valor"));
				livro.setIsbn(res.getString("isbn"));
				produtos.add(livro);
			}

			ps.close();
			res.close();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

		return produtos;
	}

	public void adicionar(Produto produto) {
		try (Connection conn = new ConnectionFactory().getConnection()) {
			PreparedStatement ps;

			ps = conn.prepareStatement("insert into produtos(nome, descricao, valor, isbn) values (?, ?, ?, ?)");
			ps.executeQuery();

			ps.setString(0, produto.getNome());
			ps.setString(1, produto.getDescricao());
			ps.setDouble(2, produto.getValor());
			ps.setString(3, produto.getIsbn());

			ps.execute();
			ps.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}