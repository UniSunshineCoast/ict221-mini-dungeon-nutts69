package dungeon.gui;

import dungeon.engine.*;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class RunGame extends Application {
    private static final int CELL_SIZE = 40;
    private GameEngine engine;
    private GridPane grid;

    // Sprites
    private Image imgPlayer, imgWall, imgGold, imgMelee, imgRanged,
            imgTrap, imgPotion, imgFloor, imgLadder;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        // 1) Load all images from resources
        imgPlayer = load("/player.png");
        imgWall   = load("/wall.png");
        imgGold   = load("/gold.png");
        imgMelee  = load("/melee.png");
        imgRanged = load("/ranged.png");
        imgTrap   = load("/trap.png");
        imgPotion = load("/potion.png");
        imgFloor  = load("/floor.png");
        imgLadder = load("/ladder.png");

        engine = new GameEngine(); // your ctor
        grid = new GridPane();
        grid.setAlignment(Pos.CENTER);

        // Control buttons
        Button up    = new Button("↑");
        Button down  = new Button("↓");
        Button left  = new Button("←");
        Button right = new Button("→");
        HBox controls = new HBox(10, left, up, down, right);
        controls.setAlignment(Pos.CENTER);

        // Status bar
        Label status = new Label();
        HBox statusBar = new HBox(20, status);
        statusBar.setAlignment(Pos.CENTER);

        // Layout
        BorderPane root = new BorderPane();
        root.setCenter(grid);
        root.setBottom(controls);
        root.setTop(statusBar);

        // Wire up movement
        up.setOnAction(e -> moveAndRefresh(Direction.UP,    status));
        down.setOnAction(e -> moveAndRefresh(Direction.DOWN, status));
        left.setOnAction(e -> moveAndRefresh(Direction.LEFT, status));
        right.setOnAction(e -> moveAndRefresh(Direction.RIGHT,status));

        // Initial draw
        refreshGrid(status);

        stage.setScene(new Scene(root));
        stage.setTitle("MiniDungeon");
        stage.show();
    }

    private Image load(String path) {
        return new Image(getClass().getResourceAsStream(path));
    }

    private void moveAndRefresh(Direction dir, Label status) {
        MoveResult res = engine.move(dir);
        status.setText(res.name() +
                "  HP:" + engine.getPlayerHP() +
                "  Score:" + engine.getScore() +
                "  Steps:" + engine.getStepsTaken() + "/" + engine.getMaxSteps()
        );
        refreshGrid(status);
    }

    private void refreshGrid(Label status) {
        grid.getChildren().clear();
        int lvl = engine.getLevel();
        for (int r = 0; r < GameMap.SIZE; r++) {
            for (int c = 0; c < GameMap.SIZE; c++) {
                MapItem item = engine.getMap().getCell(lvl, r, c);
                ImageView iv = new ImageView(spriteFor(item));
                iv.setFitWidth(CELL_SIZE);
                iv.setFitHeight(CELL_SIZE);

                // Highlight player
                if (r==engine.getPlayerRow() && c==engine.getPlayerCol()) {
                    iv = new ImageView(imgPlayer);
                    iv.setFitWidth(CELL_SIZE);
                    iv.setFitHeight(CELL_SIZE);
                }
                grid.add(iv, c, r);
            }
        }
    }

    private Image spriteFor(MapItem m) {
        switch(m.getClass().getSimpleName()) {
            case "WallCell":          return imgWall;
            case "GoldCell":          return imgGold;
            case "MeleeMutantCell":   return imgMelee;
            case "RangedMutantCell":  return imgRanged;
            case "TrapCell":          return imgTrap;
            case "HealthPotionCell":  return imgPotion;
            case "LadderCell":        return imgLadder;
            case "EntryCell":
            case "EmptySpaceCell":    return imgFloor;
            default:                  return imgFloor;
        }
    }
}
