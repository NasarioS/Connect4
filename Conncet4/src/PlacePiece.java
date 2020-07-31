import javafx.scene.control.Button;
import javafx.scene.paint.Color;

public class PlacePiece{
	private Button red;
	private Button black;
	private int row;
	public PlacePiece(int y, Board b){
		row = 0;
		red = new Button("Red");
		black = new Button("Black");
		//setting buttons location, the Y location is fixed later after shown on screen 
		red.setLayoutX((Board.TILE_SIZE + 2) * y + (Board.TILE_SIZE / 2));
		black.setLayoutX((Board.TILE_SIZE + 2) * y + (Board.TILE_SIZE / 2));
		
		red.setLayoutY((Board.TILE_SIZE + 2)*Board.HEIGHT);
		black.setLayoutY((Board.TILE_SIZE + 2)*Board.HEIGHT);
		red.setTextFill(Color.RED);
		black.setTextFill(Color.BLACK);
		
		
		red.setOnAction(e -> {
			if(this.row < Board.HEIGHT) {
				if(b.dropPiece(Piece.RED.turn, y, row))
					addRow();
			}
		});
		black.setOnAction(e -> {
			if(this.row < Board.HEIGHT) {
				if(b.dropPiece(Piece.BLACK.turn, y, row)) 
					addRow();
			}
		});
	}
	//getters and setters for the buttons location and size
	public void addRow() {
		row++;
	}
	public Button getBlack() { return this.black; }
	public Button getRed() { return this.red; }
	
	public int getRow() { return this.row; }; 
	public double getRedWidth() { return this.red.getWidth(); }
	public double getBlackWidth() { return this.black.getWidth(); }
	
	public double getBtnHeight() { return this.red.getHeight(); }
	
	public double getBlackX() { return this.black.getLayoutX(); }
	public double getRedX() { return this.red.getLayoutX(); }
	
	public void setRedX(double x) {
		this.red.setLayoutX(x);
	}
}
