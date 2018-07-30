package application;

import java.io.IOException;

import br.com.casadocodigo.livraria.produtos.Produto;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.stage.Stage;
import repositorio.RepositorioDeProdutos;
import util.Exportador;

public class Main extends Application {

	@Override
	public void start(Stage primaryStage) {

		Button button = new Button("Exportar CSV");
		button.setLayoutX(575);
		button.setLayoutY(25);

		Group group = new Group();
		Scene scene = new Scene(group, 690, 510);
		primaryStage.setScene(scene);

		Label label = new Label("Listagem de Livros");
		label.setFont(Font.font("Lucida Grande", FontPosture.REGULAR, 30));
		label.setPadding(new Insets(20, 0, 10, 10));

		ObservableList<Produto> produtos = new RepositorioDeProdutos().lista();

		TableView<Produto> tableView = new TableView<>(produtos);
		TableColumn nomeColumn = new TableColumn("Nome");
		nomeColumn.setMinWidth(180);
		nomeColumn.setCellValueFactory(new PropertyValueFactory("nome"));
		TableColumn descColumn = new TableColumn("Descrição");
		descColumn.setMinWidth(230);
		descColumn.setCellValueFactory(new PropertyValueFactory("descricao"));
		TableColumn valorColumn = new TableColumn("Valor");
		valorColumn.setMinWidth(60);
		valorColumn.setCellValueFactory(new PropertyValueFactory("valor"));
		TableColumn isbnColumn = new TableColumn("ISBN");
		isbnColumn.setMinWidth(180);
		isbnColumn.setCellValueFactory(new PropertyValueFactory("isbn"));

		tableView.getColumns().addAll(nomeColumn, descColumn, valorColumn, isbnColumn);

		VBox vbox = new VBox(tableView);
		vbox.setPadding(new Insets(70, 0, 0, 10));

		group.getChildren().addAll(label, vbox, button);

		button.setOnAction(evnt -> exportaEmCSV(produtos));

		primaryStage.setScene(scene);
		primaryStage.setTitle("Sistema de livraria com JavaFX");
		primaryStage.show();
	}

	private void exportaEmCSV(ObservableList<Produto> produtos) {
		try {
			new Exportador().paraCSV(produtos);
		} catch (IOException e) {
			System.out.println("Erro ao exportar: " + e);
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}
