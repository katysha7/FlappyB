import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;


public class Wall extends Pane {
    // класс наследуется от Pane (панелька отображаться должна)

    Rectangle rectangle;
    public int height;

    public Wall(int height) {
        this.height = height;
        rectangle = new Rectangle(20, height); // создаём прямоугольник

        getChildren().add(rectangle); // добавляем прямоугольник на панель
    }

}
