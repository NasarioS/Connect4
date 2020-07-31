import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.shape.Ellipse;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;


public class Board extends Application {
	private Scene gameScene;
	private Stage stage;
	private PlacePiece p;
	public static final int WIDTH = 7;
	public static final int HEIGHT = 6;
	public static final int TILE_SIZE = 95;
	private Group tGroup, bGroup;
	private Tile Board[][] = new Tile [WIDTH][HEIGHT];
	private PlacePiece buttons[] = new PlacePiece [WIDTH];
	private Pane root;
	private int turn;
	private Text playerColor;
	
	private Parent createContent() {
		root = new Pane();
		//group for Tiles 
		tGroup = new Group();
		bGroup = new Group();
		//Display Information for Turn, gets updated after a users turn
		Text turnp = new Text(WIDTH * (TILE_SIZE + 2), 15, "Turn:");
		playerColor = new Text(WIDTH * (TILE_SIZE+2) + 30, 15, "RED");
		playerColor.setFont(Font.font("Impact", FontWeight.BOLD, 12));
		playerColor.setFill(Color.RED);
		
		root.getChildren().addAll(tGroup, bGroup, turnp, playerColor);
		root.setPrefSize(WIDTH * (TILE_SIZE + 2) + TILE_SIZE, HEIGHT * (TILE_SIZE + 2) + 25);
		Tile t;
		//creates board, and reverse order for y to make placing pieces easier.
		for(int x = 0; x < WIDTH; x++) {
			for(int y = 0; y < HEIGHT; y++){
				t = new Tile(x * (TILE_SIZE + 2), y * (TILE_SIZE + 2));
				tGroup.getChildren().add(t);
				Board[x][5-y] = t;
			}
			//Place Piece is where the buttons for placing Pieces are created
			p = new PlacePiece(x, this);
			buttons[x] = p;
			bGroup.getChildren().addAll(p.getBlack(), p.getRed());
			
		}
		
		
		
		return root;
		}
	// function for "dropping" a piece on to the board
	// when the board was first made it consisted of squares and circles
	// instead of adding something, it sets the tile to having a piece and 
	// chances the circle color to the piece color
	public boolean dropPiece(int turn, int x, int y) {
		if(this.turn == turn && this.turn == Piece.RED.turn) {
			if(Board[x][y].getChildren().get(1) instanceof Ellipse) {
				Ellipse e = (Ellipse) Board[x][y].getChildren().get(1);
				e.setFill(Color.RED);
				playerColor.setText("BLACK");
				playerColor.setFill(Color.BLACK);
				Board[x][y].setPiece(Piece.RED);
			}
			this.turn++;
			return true;
		}
		else if(this.turn == turn) {
			if(Board[x][y].getChildren().get(1) instanceof Ellipse) {
				Ellipse e = (Ellipse) Board[x][y].getChildren().get(1);
				e.setFill(Color.BLACK);
				playerColor.setText("RED");
				playerColor.setFill(Color.RED);
				Board[x][y].setPiece(Piece.BLACK);
			}
			this.turn--;
			return true;
		}
		return false;
	}
	@Override
	public void start(Stage primaryStage) throws Exception {
		gameScene = new Scene(createContent());
		stage = new Stage();
		stage.setScene(gameScene);
		stage.setTitle("Conncet 4");
		stage.show();
		fixButtons();
	}
	// relocating buttons based on button size, cannot be done until buttons can be seen on screen
	private void fixButtons() {
		for (int i = 0; i < 7; i++) {
			buttons[i].setRedX(buttons[i].getRedX() - buttons[i].getRedWidth());
		}
	}
	public static void main(String[] args) {
		launch(args);

	}



}
