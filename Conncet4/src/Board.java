import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.shape.Ellipse;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;


public class Board extends Application {
	private Scene gameScene, winScene, tieScene;
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
	private static final int total = WIDTH * HEIGHT;
	private int turns;
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
		turns = 1; 
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
			turns++;
			this.turn++;
			check(x , y, Piece.RED);
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
			turns++;
			this.turn--;
			check(x,y, Piece.BLACK);
			return true;
		}
		return false;
	}
	//check conncet4 after a single piece is dropped and check if board is full
	private void check(int x, int y, Piece p) {
		//8 Directions, these will check continuous checker pieces, rVU =  right Diagonal Up, LVD = Left Diagonal Down, etc.
		boolean left = true, right = true, down = true, rDU = true, rDD = true, lDU = true, lDD = true;
		//Horizontal, vertical, left Diagonal, right Diagonal, checks for number of continuous pieces
		int h = 1, v = 1, lD = 1, rD = 1;
		
		for(int i = 1; i < 4; i++) {
			//left horizontal
			if(x-i > -1) {
				if(Board[x-i][y].getPiece() == p && left) {
					h++;
				}
				else
					left = false;
			}
			//right horizontal
			if(x+i < WIDTH) {
				if(Board[x+i][y].getPiece() == p && right) {
					h++;
				}
				else
					right = false;
			}
		
			//vertical down
			if(y-i > -1) {
				if(Board[x][y-i].getPiece() == p && down) {
					v++;
				}
				else
					down = false;
			}
			
			//Left Diagonal up(top left)
			if(x - i > -1 && y + i < HEIGHT) {
				if(Board[x-i][y+i].getPiece() == p && lDU) {
					lD++;
				}
				else
					lDU = false;
			}
			//Left Diagonal down (bottom right) 
			if(x + i < WIDTH && y - i > -1) {
				if(Board[x+i][y-i].getPiece() == p && lDD) {
					lD++;
				}
				else
					lDD = false;
			}
			
			//Right Diagonal up (top right)
			if(x+i < WIDTH && y+i < HEIGHT) {
				if(Board[x+i][y+i].getPiece() == p && rDU) {
					rD++;
				}
				else 
					rDU = false;
			}
			//Right Diagonal down (bottom left)
			if(x-i > -1 && y-i > -1) {
				if(Board[x-i][y-i].getPiece() == p && rDD) {
					rD++;
				}
				else 
					rDD = false;
			}
			
		}
		//4 in a row or more
		if(h > 3 || v > 3 || rD > 3 || lD > 3) {
			gameWin(p);
		}
		// out of moves
		if(turns > total) {
			outOfMoves();
		}
	}
	private void gameWin(Piece p) {
		Pane pane = new Pane();
		pane.setPrefSize(WIDTH*TILE_SIZE/2, HEIGHT*TILE_SIZE/4);
		winScene = new Scene(pane);
		stage.setScene(winScene);
		
		Text win = new Text("WINS!!");
		Text red = new Text("RED Player");
		red.setFill(Color.RED);
		stage.hide();
		Text black =  new Text("BLACK Player");
		Button startNew = new Button("New Game");
		Button exit =  new Button ("Exit");
		pane.getChildren().addAll(win, startNew, exit);
		stage.show();
		
		red.setLayoutY(TILE_SIZE/3);
		black.setLayoutY(TILE_SIZE / 3);
		
		win.setLayoutX(WIDTH * TILE_SIZE /4 - win.getLayoutBounds().getWidth()/2);
		win.setLayoutY(red.getLayoutY() + 15);
		
		startNew.setLayoutX(WIDTH * TILE_SIZE /4 - (startNew.getLayoutBounds().getWidth() + 2.5));
		startNew.setLayoutY(win.getLayoutY() + 5);
		
		exit.setLayoutX(WIDTH * TILE_SIZE /4 + 2.5);
		exit.setLayoutY(win.getLayoutY() + 5);
		
		
		startNew.setOnAction(e -> {
			root.getChildren().removeAll();
			root.getChildren().clear();
			tGroup.getChildren().removeAll();
			tGroup.getChildren().clear();
			bGroup.getChildren().removeAll();
			bGroup.getChildren().clear();
			Board = new Tile[WIDTH][HEIGHT];
			gameScene = new Scene(createContent());
			stage.setScene(gameScene);
			fixButtons();
		});
		
		exit.setOnAction(e-> {
			Stage stage = (Stage) exit.getScene().getWindow();
		    stage.close();
		});
		
		if(p == Piece.BLACK) {
			pane.getChildren().add(black);
			black.setLayoutX(WIDTH * TILE_SIZE / 4 - black.getLayoutBounds().getWidth() / 2);
		}
		else {
			pane.getChildren().add(red);
			red.setLayoutX(WIDTH * TILE_SIZE/4 - red.getLayoutBounds().getWidth()/ 2);
		}
		
		
		
	}
	
	
	
	private void outOfMoves() {
		Pane pane = new Pane();
		pane.setPrefSize(WIDTH*TILE_SIZE/2, HEIGHT*TILE_SIZE/4);
		winScene = new Scene(pane);
		stage.hide();
		stage.setScene(winScene);
		
		Text outOfMoves = new Text("Out Of Moves, Game Ends in a Tie");
		
		Button startNew = new Button("New Game");
		Button exit =  new Button ("Exit");
		pane.getChildren().addAll( outOfMoves, startNew, exit);
		stage.show();
		
		outOfMoves.setLayoutY(TILE_SIZE/3);
		
		
		
		System.out.println(startNew.getWidth());
		startNew.setLayoutY(outOfMoves.getLayoutY() + 5);
		startNew.setLayoutX(WIDTH * TILE_SIZE /4 - (startNew.getLayoutBounds().getWidth() + 2.5));
		exit.setLayoutX(WIDTH * TILE_SIZE /4 + 2.5);
		exit.setLayoutY(outOfMoves.getLayoutY() + 5);
		outOfMoves.setLayoutX(WIDTH * TILE_SIZE / 4 - outOfMoves.getLayoutBounds().getWidth() / 2);
		
		startNew.setOnAction(e -> {
			root.getChildren().removeAll();
			root.getChildren().clear();
			tGroup.getChildren().removeAll();
			tGroup.getChildren().clear();
			bGroup.getChildren().removeAll();
			bGroup.getChildren().clear();
			Board = new Tile[WIDTH][HEIGHT];
			gameScene = new Scene(createContent());
			stage.setScene(gameScene);
			fixButtons();
		});
		
		exit.setOnAction(e-> {
			Stage stage = (Stage) exit.getScene().getWindow();
		    stage.close();
		});
		
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
