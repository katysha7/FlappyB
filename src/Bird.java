import javafx.scene.layout.Pane;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Bird extends Pane {
    public Point2D velocity = new Point2D(0, 0);
    Rectangle rectangle;

    public Bird() {
        rectangle = new Rectangle(20, 20, Color.RED);
        velocity = new Point2D(0, 0); // точка на экране, которая будет принимать определённые координаты;
        // по ним с помощью методов moveX и moveY будет двигаться птичка
        setTranslateY(300);
        setTranslateX(100);
        getChildren().addAll(rectangle); // добавляем прямоугольник на панель птички
    }

    public void moveY(int value) { // на сколько пикселей переместить bird
        boolean moveDown = value > 0 ? true : false; // если объект больше 0, двигаемся вниз, меньше - прыгаем
        for (int i = 0; i < Math.abs(value); i++) {
            for (Wall w : FlappyBird.walls) {    // проверяем столкновение
                if (this.getBoundsInParent().intersects(w.getBoundsInParent())) {
                    if (moveDown) {
                        setTranslateY(getTranslateY() - 1);
                        return;
                    } else {
                        setTranslateY(getTranslateY() + 1);
                        return;
                    }
                }
            }
            if (getTranslateY() < 0) { // чтобы bird не проваливалась за границы экрана
                setTranslateY(0);
            }

            if (getTranslateY() > 580) {
                setTranslateY(580);
            }
            setTranslateY(getTranslateY() + (moveDown ? 1 : -1));
        }

    }

    public void moveX(int value) {
        for (int i = 0; i < value; i++) {
            for (Wall w : FlappyBird.walls) { // проверяем столкновение
                if (getBoundsInParent().intersects(w.getBoundsInParent())) {
                    if (getTranslateX() + 20 == w.getTranslateX()) { // если правая граница bird (Х) = левой границе стенки,
                        // устанавливаем положение по X на 1ед. назад
                        setTranslateX(getTranslateX() - 1);
                        return;
                    }
                }
                if (getTranslateX() + 20 == w.getTranslateX()) {
                    FlappyBird.score++;
                }
            }
            setTranslateX(getTranslateX() + 1);
        }
    }

    public void jump() {
        velocity = new Point2D(3, -15); // если прыгаем, то координата - на 3 пикселя вправо и на 10 вверх
    }


}
