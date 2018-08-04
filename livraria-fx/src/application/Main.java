package application;

import java.io.IOException;

import br.com.casadocodigo.livraria.produtos.Produto;
import dao.ProdutoDAO;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import util.Exportador;

@SuppressWarnings({ "unchecked", "rawtypes" })
public class Main extends Application {

	@Override
	public void start(Stage primaryStage) {

		Button button = new Button("Exportar CSV");

		Group group = new Group();
		Scene scene = new Scene(group, 690, 510);
		primaryStage.setScene(scene);

		Label label = new Label("Listagem de Livros");
		label.setId("label-listagem");

		ObservableList<Produto> produtos = new ProdutoDAO().lista();

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
		vbox.setId("vbox");

		Label progresso = new Label();
		progresso.setId("label-progresso");

		double valorTotal = produtos.stream().mapToDouble(Produto::getValor).sum();
		Label labelFooter = new Label(String.format(
				"Você tem R$%.2f em estoque, "
						+ "com um total de %d produtos.",
				valorTotal, produtos.size()));
		labelFooter.setId("label-footer");

		group.getChildren().addAll(label, vbox, button, progresso, labelFooter);

		button.setOnAction(evnt -> {
			Task<Void> task = new Task<Void>() {

				@Override
				protected Void call() throws Exception {
					descansarPorVinteSegundo();
					exportaEmCSV(produtos);
					return null;
				}
			};

			task.setOnRunning(e -> progresso.setText("Exportando..."));
			task.setOnSucceeded(e -> progresso.setText("Concluído"));

			new Thread(task).start();
		});

		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());

		primaryStage.setScene(scene);
		primaryStage.setTitle("Sistema de livraria com JavaFX");
		primaryStage.show();
	}

	private void descansarPorVinteSegundo() {
		try {
			Thread.sleep(20_000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
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
