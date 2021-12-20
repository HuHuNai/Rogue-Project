package Game;

import Utils.*;
import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import engine.*;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;

public class Game {
    private Random random;
    private int width=60;
    private int height=40;
    private int size=18*3;
    private TileEngine tileEngine = new TileEngine();

    private ScrollPane scrollPane;
    private Pane pane;
    private StackPane stackPane;
    private AnchorPane mainPane;
    private Scene scene;
    private RandomMapGenerator randomMapGenerator;
    private Pane pausingPane;

    private Pane infoPane;
    ProgressBar playerHp;
    ProgressBar playerEp;
    ProgressBar playerExp;
    Text playerHpText;
    Text playerEpText;
    Text playerExpText;
    ProgressBar skillTime;
    Text skillTimeText;
    Text floorText;

    private StartingUI startingUI;


    private InputListener inputListener;

    private int level=0;
    private boolean isDead;

    private final long FRAME=1000000000/50;

    private Player player;

    private ArrayList<Guard> guards= new ArrayList();
    private ArrayList<Fighter> fighters = new ArrayList<>();
    private Exit exit;
    private boolean pausing;

    private ArrayList<Collidable> attacks = new ArrayList<>();

    private AnimationTimer animationTimer1 = new AnimationTimer() {
        long lastTick = 0;
        @Override
        public void handle(long now) {
            if (lastTick==0){
                lastTick = now;
                tick();
            }
            if (now - lastTick >= FRAME) {
                lastTick = now;
                tick();
            }
        }
    };

    private boolean isNearExit(Player player){
        return exit.checkCollision(player);
    }

    private void afterDeath(){
        animationTimer1.stop();
        DeathUi deathUi = new DeathUi();
        Pane currentPane= deathUi.pane;
        stackPane.getChildren().add(currentPane);
        deathUi.exit.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                System.exit(0);
            }
        });
        deathUi.tryAgain.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                refresh(true);
                stackPane.getChildren().remove(currentPane);
                animationTimer1.start();
            }
        });

    }

    private void tick(){
        if (inputListener.isEscPressed){
            if(!pausing){
                animationTimer1.stop();
                pausing=true;
                stackPane.getChildren().add(pausingPane);
            }
        }
        if (inputListener.isEPressed && isNearExit(player)){
            refresh(false);
            return;
        }
        if (player.toBeDeleted){
            afterDeath();
            refresh(true);
        }
        scrollPane.setVvalue(player.yPos);
        scrollPane.setHvalue(player.xPos);
        showInfo();
        playerStateRefresh();
        movePlayer();
        for (int i=0;i<fighters.size();i+=1){
            Fighter fighter=fighters.get(i);
            fighter.checkDead();
            if (fighter.toBeDeleted){
                fighters.remove(i);
                pane.getChildren().remove(fighter.shape);
                player.expUp((int)(10+level/2));
            }
            fighter.chase(player, tileEngine.getMap());
            fighter.checkGettingHurt(tileEngine.getMap());
            fighter.checkVincible();
            fighter.checkMovable();
            fighter.checkCollision(player);
            fighter.update();
        }
        for (int i=0;i<guards.size();){
            NPC guard = guards.get(i);
            guard.checkDead();
            if (guard.toBeDeleted){
                guards.remove(i);
                pane.getChildren().remove(guard.shape);
                player.expUp((int)(10+level/2));
            }
            else {
                guard.attack(player, attacks, pane);
                guard.checkGettingHurt(tileEngine.getMap());
                guard.checkVincible();
                guard.checkMovable();
                guard.checkAttackable();
                guard.update();
                i += 1;
            }
        }
        for (int i=0;i<attacks.size();){
            Collidable bullet=attacks.get(i);
            bullet.checkCollision(player,guards);
            bullet.checkCollision(player,fighters);
            bullet.move(tileEngine.getMap());
            if (bullet.toBeDeleted){
                attacks.remove(i);
                pane.getChildren().remove(bullet.shape);
            }
            else{
                i+=1;
            }
        }
        player.update();

    }

    private void refresh(boolean isDead){
        if (isDead){
            level=0;
            player.heal(99999999);
        }
        else {
            level+=1;
        }
        player.heal(50);
        Tile[][]  map=randomMapGenerator.randomWorld();
        tileEngine.setMap(map);
        setGuards(pane);
        setPlayer(pane);
        setExit();
    }

    private void playerStateRefresh(){
        player.checkDead();
        player.dodge(tileEngine.getMap());
        player.refresh();
        player.checkGettingHurt(tileEngine.getMap());
        player.checkMovable();
        player.checkVincible();
        player.checkAttackable();
        player.checkShootingTimer();
        player.checkSkill();
    }

    private void movePlayer(){
        if (inputListener.isLeftMousePressed){
            player.attack(inputListener.mousePosX,inputListener.mousePosY,attacks,pane);
        }

        if (inputListener.isFPressed){
            player.skill(fighters,guards);
        }

        if (!player.movable){
            return;
        }
        if (inputListener.isSpacePressed && player.isDodging<0 && player.ep>=25){
            if (inputListener.isAPressed){
                if (inputListener.isWPressed){
                    player.isDodging=4;
                    player.dodgingTimer=6*FRAME;
                    player.vincibleTimer=6*FRAME;
                    player.vincible=false;
                    player.ep-=25;
                    return;
                }
                if (inputListener.isSPressed){
                    player.isDodging=6;
                    player.dodgingTimer=6*FRAME;
                    player.vincibleTimer=6*FRAME;
                    player.vincible=false;
                    player.ep-=25;
                    return;
                }
                player.isDodging=2;
                player.dodgingTimer=6*FRAME;
                player.vincibleTimer=6*FRAME;
                player.vincible=false;
                player.ep-=25;
                return;
            }
            if (inputListener.isDPressed){
                if (inputListener.isWPressed){
                    player.isDodging=5;
                    player.dodgingTimer=6*FRAME;
                    player.vincibleTimer=6*FRAME;
                    player.vincible=false;
                    player.ep-=25;
                    return;
                }
                if (inputListener.isSPressed){
                    player.isDodging=7;
                    player.dodgingTimer=6*FRAME;
                    player.vincibleTimer=6*FRAME;
                    player.vincible=false;
                    player.ep-=25;
                    return;
                }
                player.isDodging=3;
                player.dodgingTimer=6*FRAME;
                player.vincibleTimer=6*FRAME;
                player.vincible=false;
                player.ep-=25;
                return;
            }
            if (inputListener.isWPressed){
                player.isDodging=0;
                player.dodgingTimer=6*FRAME;
                player.vincibleTimer=6*FRAME;
                player.vincible=false;
                player.ep-=25;
                return;
            }
            if (inputListener.isSPressed){
                player.isDodging=1;
                player.dodgingTimer=6*FRAME;
                player.vincibleTimer=6*FRAME;
                player.vincible=false;
                player.ep-=25;
                return;
            }
        }
        if (inputListener.isAPressed){
            if (inputListener.isWPressed){
                player.move(4,tileEngine.getMap());
                return;
            }
            if (inputListener.isSPressed){
                player.move(6,tileEngine.getMap());
                return;
            }
            player.move(2,tileEngine.getMap());
            return;
        }
        if (inputListener.isDPressed){
            if (inputListener.isWPressed){
                player.move(5,tileEngine.getMap());
                return;
            }
            if (inputListener.isSPressed){
                player.move(7,tileEngine.getMap());
                return;
            }
            player.move(3,tileEngine.getMap());
            return;
        }
        if (inputListener.isWPressed){
            player.move(0,tileEngine.getMap());
            return;
        }
        if (inputListener.isSPressed){
            player.move(1,tileEngine.getMap());
            return;
        }

    }



    public Game(long seed){
        random=new Random(seed);
        randomMapGenerator = new RandomMapGenerator(random,width,height);
    }

    public void startingMenu(){
        startingUI= new StartingUI();
        startingUI.setBackground();
        Pane startingPane=startingUI.pane;
        scene = new Scene(startingPane);
        startingUI.circle.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                startGame(0,null);
            }
        });
        startingUI.rectangle.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                startGame(1,null);
            }
        });
        startingUI.exit.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                System.exit(0);
            }
        });
        startingUI.loadGame.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                Player player=load();
                startGame(player.getType(),player);
            }
        });
    }

    public void startGame(int type,Player player){
        tileEngine.initialize(width,height,size);
        Tile[][] map=null;
        boolean out=false;
        try {
            mainPane= FXMLLoader.load(getClass().getResource("/UI.fxml"));
        }catch (Exception e){
            System.out.println(e);
        }

        scene.setRoot(mainPane);

        map=randomMapGenerator.randomWorld();
        tileEngine.setMap(map);
        pane =tileEngine.getPane();
        pane.setBackground(new Background(new BackgroundImage(new Image(getClass().getResourceAsStream("/Background/Background2.png")),null,null,null,null)));
        setGuards(pane);
        setPlayer(pane,type);
        if (player!=null){
            this.player.copyInfo(player);
        }
        setExit();

        stackPane = (StackPane) mainPane.getChildren().get(0);
        scrollPane=(ScrollPane) stackPane.getChildren().get(0);

        scrollPane.setContent(pane);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setHmax(width*size);
        scrollPane.setVmax(height*size);
        scrollPane.setPannable(false);

        infoPane = new Pane();
        infoPane.setPickOnBounds(false);
        playerHp=new ProgressBar(1);
        playerHp.setStyle("-fx-accent: Tomato;");
        playerHp.setLayoutX(40);
        playerHp.setLayoutY(40);
        playerHp.setPrefSize(150,30);
        playerHpText = new Text();
        playerHpText.setFont(Font.loadFont(getClass().getResourceAsStream("/Font/kenvector_future.ttf"),15));
        playerHpText.setLayoutX(50);
        playerHpText.setLayoutY(60);



        playerEp=new ProgressBar(1);
        playerEp.setLayoutX(40);
        playerEp.setLayoutY(80);
        playerEp.setPrefSize(150,30);
        playerEp.setStyle("-fx-accent: DarkSeaGreen;");
        playerEpText = new Text();
        playerEpText.setFont(Font.loadFont(getClass().getResourceAsStream("/Font/kenvector_future.ttf"),15));
        playerEpText.setLayoutX(50);
        playerEpText.setLayoutY(60+40);

        playerExp=new ProgressBar(0);
        playerExp.setLayoutX(40);
        playerExp.setLayoutY(120);
        playerExp.setPrefSize(150,30);
        playerExp.setStyle("-fx-accent: Gold;");
        playerExpText = new Text();
        playerExpText.setFont(Font.loadFont(getClass().getResourceAsStream("/Font/kenvector_future.ttf"),15));
        playerExpText.setLayoutX(50);
        playerExpText.setLayoutY(60+40+40);

        skillTime = new ProgressBar();
        skillTime.setStyle(" -fx-progress-color: PaleTurquoise;");
        skillTime.setPrefSize(150,30);
        skillTime.setLayoutX(40);
        skillTime.setLayoutY(160);
        skillTimeText =new Text();
        skillTimeText.setFont(Font.loadFont(getClass().getResourceAsStream("/Font/kenvector_future.ttf"),15));
        skillTimeText.setLayoutX(50);
        skillTimeText.setLayoutY(60+40+40+40);

        floorText=new Text();
        floorText.setFont(Font.loadFont(getClass().getResourceAsStream("/Font/kenvector_future.ttf"),25));
        floorText.setLayoutX(1080-160);
        floorText.setLayoutY(80);

        infoPane.getChildren().addAll(playerHp,playerEp,playerExp,playerHpText,playerEpText,playerExpText,skillTime,skillTimeText,floorText);
        infoPane.setPickOnBounds(false);
        stackPane.getChildren().add(infoPane);



        inputListener=new InputListener(scrollPane,pane);

        PausingUI pausingUI= new PausingUI();
        pausingPane=pausingUI.pausingPane;
        pausingPane.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                if(pausing && keyEvent.getCode()== KeyCode.ESCAPE){
                    stackPane.getChildren().remove(pausingPane);
                    animationTimer1.start();
                    pausing=false;
                }
            }
        });
        pausingUI.save.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                save();
            }
        });
        pausingUI.back.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                stackPane.getChildren().remove(pausingPane);
                animationTimer1.start();
                pausing=false;
            }
        });
        pausingUI.exit.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                System.exit(0);
            }
        });

        animationTimer1.start();
    }


    private void setGuards(Pane pane){
        guards.clear();
        fighters.clear();
        int total = 3+(int)Math.log(level+1);
        ArrayList<Room> rooms = randomMapGenerator.getRooms();
        for (int i=1;i<rooms.size();i+=1){
            Room r=rooms.get(i);
            int gNum = RandomUtils.uniform(random,0,total+1);
            int randX;
            int randY;
            for (int j=0;j<gNum;j+=1){
                randX= RandomUtils.uniform(random,r.getStart().x+1,r.getEnd().x-1);
                randY= RandomUtils.uniform(random,r.getStart().y+1,r.getEnd().y-1);
                Guard guard =new Guard(randX*size,randY*size,r,level);
                guards.add(guard);
                pane.getChildren().add(guard.shape);
            }
            for (int j=0;j<total-gNum;j+=1){
                randX= RandomUtils.uniform(random,r.getStart().x+1,r.getEnd().x-1);
                randY= RandomUtils.uniform(random,r.getStart().y+1,r.getEnd().y-1);
                Fighter fighter = new Fighter(randX*size,randY*size,r,level);
                fighters.add(fighter);
                pane.getChildren().add(fighter.shape);
            }
        }


    }

    private void showInfo(){
        double[] playerInfo = player.getInfo();
        playerHp.setProgress(playerInfo[0]/playerInfo[1]);
        playerHpText.setText("HP: "+(int)playerInfo[0]+"/"+(int)playerInfo[1]);
        playerEp.setProgress(playerInfo[2]/100);
        playerEpText.setText("Ep: "+(int)playerInfo[2]+"/100");
        playerExp.setProgress(playerInfo[3]/playerInfo[5]);
        playerExpText.setText("Level: "+(int)playerInfo[4]);
        skillTime.setProgress(1-playerInfo[6]);
        String skillInfo =skillTime.getProgress()==1? "Skill Ready":"Reloading";
        skillTimeText.setText(skillInfo);
        floorText.setText("Floor: "+level);
    }

    private void save(){
        File save = new File("save.txt");
        try{
            FileOutputStream fos= new FileOutputStream(save);
            ObjectOutputStream oos = null;
            try {
                oos = new ObjectOutputStream(fos);
                oos.writeObject(player);
                oos.flush();
            }catch (IOException e){
                System.out.println(e);
            }
        }catch (FileNotFoundException e){
            System.out.println("File not found!"+e.getMessage());
        }
    }

    private Player load(){
        File save = new File("save.txt");
        FileInputStream fis = null;
        Player player=null;
        try{
            fis = new FileInputStream(save);
            ObjectInputStream ois = null;
            try {
                ois = new ObjectInputStream(fis);
                try {
                    player = (Player) ois.readObject();
                }catch (ClassNotFoundException e){
                    System.out.println(e.getMessage());
                }
            }catch (IOException e){
                e.printStackTrace();;
            }
        }catch (FileNotFoundException e){
            System.out.println("File not found"+e.getMessage());
        }
        catch (Exception e){
            System.out.println("An error has occurred."+e.getMessage());
        }
        return player;
    }

    private void setPlayer(Pane pane,int type){
        Room r = randomMapGenerator.randRoom();
        player=new Player(r.getCenter().x*size,r.getCenter().y*size,type,random);
        pane.getChildren().add(player.getShape());
    }

    private void setPlayer(Pane pane){
        Room r = randomMapGenerator.randRoom();
        player.setPos(r.getCenter().x*size,r.getCenter().y*size);
        pane.getChildren().add(player.getShape());
    }

    private void setExit(){
        ArrayList<Room> rooms = randomMapGenerator.getRooms();
        Room r = rooms.get(rooms.size()-1);
        int  randX= RandomUtils.uniform(random,r.getStart().x+1,r.getEnd().x-1);
        int  randY= RandomUtils.uniform(random,r.getStart().y+1,r.getEnd().y-1);
        exit= new Exit(size,randX*size,randY*size);
        pane.getChildren().add(exit.getShape());
    }

    public Scene getScene(){
        return scene;
    }
}
