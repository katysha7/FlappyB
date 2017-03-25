import javafx.application.Application;
import javafx.animation.AnimationTimer;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import java.util.ArrayList;
import java.util.Random;


public class FlappyBird extends Application {
    public static Pane appRoot = new Pane(); // панель приложения
    public static Pane gameRoot = new Pane(); // поле, где расположены все стенки

    public static ArrayList<Wall> walls = new ArrayList<>(); // список стен
    Bird bird = new Bird(); // птичка
    public static int score = 0; // переменная счёта
    public Label scoreLabel = new Label("Score: " + score); // поле, куда будем записывать счёт

    public Parent createContent() {                             // метод, отвечающий за создание сцены
        gameRoot.setPrefSize(600, 600);     // размер приложения
        // создаём столбцы:
        for (int i = 0; i < 100; i++) {  // каждую итерацию создаётся стенка
            int enter = (int) (Math.random() * 100 + 50); // ширина проёма, через который будет проскакивать птичка - от 50 до 150
            int height = new Random().nextInt(600 - enter); // переменная высоты стенки (на весь экран - проём)
            Wall wall = new Wall(height);   // создаём стенку
            wall.setTranslateX(i * 350 + 600); // стена через каждые 350 пикселей + 600 - отодвигаем за пределы экрана,
            // чтоб появилась чуть позже
            wall.setTranslateY(0);  // стена начинается сверху
            walls.add(wall);    // добавили стену в список стен

            Wall wall1 = new Wall(600 - enter - height); // создаём вторую стену
            wall1.setTranslateX(i * 350 + 600); // размер приложения - проём - первая стенка
            wall1.setTranslateY(height + enter);    // высота первой стенки + проём
            walls.add(wall1); // добавили стену в список стен
            gameRoot.getChildren().addAll(wall, wall1); // добавили стенки на gameRoot
        }
        gameRoot.getChildren().add(bird); // добавили птичку на панель
        appRoot.getChildren().addAll(gameRoot); // добавляем в appRoot gameRoot
        return appRoot;         // возвращаем appRoot, т.к. это корневой узел
    }

    public void update() { // метод ("ОБНОВИТЬ")вызывается каждый кадр
        if (bird.velocity.getY() < 5) { // каждый кадр двигаемся на 5 пикселей вниз (ГРАВИТАЦИЯ)
            bird.velocity = bird.velocity.add(0, 1);
        }

        bird.moveX((int) bird.velocity.getX()); // двигаем птичку на определённое количество координат
        bird.moveY((int) bird.velocity.getY()); // двигаем птичку на определённое количество координат
        scoreLabel.setText("Score: " + score); // обновление счёта

        bird.translateXProperty().addListener((obs, oldValue, newValue) -> { // чтобы птичка не улетала за кадр
            int offset = newValue.intValue();   // текущее значение положения птички
            if (offset > 200)
                gameRoot.setLayoutX(-(offset - 200)); // при пересечении птичкой 200 пикселей,
            // смещаем gameRoot влево
        });
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Scene scene = new Scene(createContent());           // создаём сцену
        scene.setOnMouseClicked(event -> bird.jump()); // при клике на сцену происходит прыжок птички
        primaryStage.setScene(scene);   // устанавливаем сцену на окно
        primaryStage.show();

        AnimationTimer timer = new AnimationTimer() { // анимационный таймер
            @Override
            public void handle(long now) {            // метод вызывается каждый кадр игры
                update();                                // обновление каждый кадр
            }
        };
        timer.start();

    }

}
