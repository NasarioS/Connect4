import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Rectangle;

public class Tile extends StackPane {
	private boolean piece;
	private Rectangle r;
	private Ellipse e;
	
	public void setPiece() {
		this.piece = true;
	}
	public boolean hasPiece() {
		return this.piece;
	}
	
	public Tile(double x, double y) {
		this.piece = false;
		this.setPrefSize(Board.TILE_SIZE,Board.TILE_SIZE);
		r = new Rectangle();
		r.setFill(Color.BLUE);
		r.setWidth(Board.TILE_SIZE);
		r.setHeight(Board.TILE_SIZE);
		r.setStrokeWidth(2);
		r.setStroke(Color.BLACK);
		this.relocate(x,y);
		
		e = new Ellipse();
		e.setRadiusX(Board.TILE_SIZE * 0.86 / 2);
		e.setRadiusY(Board.TILE_SIZE * 0.86/2);
		e.setFill(Color.WHITE);
		
		this.getChildren().addAll(r,e);
	}
}
