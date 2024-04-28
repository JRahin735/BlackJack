// @authors: Rahin Jain (665219123), Aarav Surkatha (667714562)

import javafx.application.Application;

import java.text.BreakIterator;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class BlackjackGame extends Application {

    private ArrayList<Card> playerHand = new ArrayList<Card>();
    private ArrayList <Card> bankerHand = new ArrayList<Card>();
    private BlackjackDealer theDealer = new BlackjackDealer();
    private BlackjackGameLogic gameLogic = new BlackjackGameLogic();
    double currentBet = 0;
    double totalWinnings = 0;


    public ArrayList<Card> getPlayerHand () {return playerHand;}
    public ArrayList<Card> getBankerHand () {return bankerHand;}
    public void setTotalWinnings (double amt) {totalWinnings += amt;}
    public double getCurrentBet () {return currentBet;}
    public double getTotalWinnings () {return totalWinnings;}
    public void setCurrentBet (double bet) {currentBet = bet;}

    public ArrayList<Card> showPlayerCards () {

        ArrayList<Card> cardsToShow = new ArrayList<Card>();

        for (int i = 0; i < playerHand.size(); i++) {
            if(i == 2) {
                cardsToShow.add(null);
                continue;
            }
            cardsToShow.add(playerHand.get(i));
        }

        return cardsToShow;
    }

    // check whether the bet will make user's total money negative
    public boolean allowBet () {

        if ((totalWinnings - currentBet) < 0) {
            currentBet = 0;
            return false;
        }
        else {
            // allow bet
            return true;
        }
    }

    public void startGame () {

        theDealer.generateDeck();
        theDealer.shuffleDeck();
        playerHand = theDealer.dealHand();
        bankerHand = theDealer.dealHand();
    }

    // add a card
    // 2 scenarios - more than 21, then bust - less than 21, then nothing
    // 0-> bust, 1-> continue
    public int playerHit () {

        playerHand.add(theDealer.drawOne());
        if (gameLogic.handTotal(playerHand) > 21) {
            // BUST
            return 0;
        }
        else {
            return 1;
        }
    }

    // banker's turn in a loop
    public void playerStay () {

        // banker hits until 16 or above
        while (gameLogic.evaluateBankerDraw(bankerHand)) {
            bankerHand.add(theDealer.drawOne());
        }
    }

    private boolean isBlackjack () {

        boolean hasAce = false;
        boolean hasFaceCardor10 = false;

        for (int i = 0; i < playerHand.size(); i++) {
            if (playerHand.get(i).getValue() == 1) {
                hasAce = true;
            }
            if (playerHand.get(i).getValue() >= 10) {
                hasFaceCardor10 = true;
            }
        }
        if (hasAce && hasFaceCardor10 && gameLogic.handTotal(playerHand)==21) {
            return true;
        }
        else {
            return false;
        }
    }

    // This method will determine if the user won or lost their bet and return the amount won
    // or lost based on the value in currentBet.
    public double evaluateWinnings(){

        // deciding on a winner
        String winner = gameLogic.whoWon(playerHand, bankerHand);

        if (winner == "player") {

            if (isBlackjack()) {
                double currentWinnings = currentBet + (currentBet*1.50);
                totalWinnings += currentWinnings;
                return currentWinnings;
            }

            // assuming that a normal win results in 100% increase
            double currentWinnings = currentBet*2;
            totalWinnings += currentWinnings;
            return currentWinnings;
        }
        else if (winner == "dealer") {

            double currentLoss = -1 * currentBet;
            totalWinnings += currentLoss;
            return currentLoss;
        }
        else {
            // DRAW
            // totalWinnings remains unchanged
            return 0.0;
        }
    }

    private Scene homepageScene, bettingPageScene, gameAreaPageScene;


    @Override
    public void start(Stage primaryStage) throws Exception {

        // ------------------------ Homepage setup ------------------------
        {
            // elements setup
            Text Heading = new Text("BLACKJACK!\n\n");
            Heading.setFont(Font.font("laluna", FontWeight.BOLD, 60));

            Button playGameButton = new Button("GO TO GAME AREA");
            playGameButton.setStyle("-fx-background-color: #161616; -fx-text-fill: white;");
            playGameButton.setPrefWidth(160);
            playGameButton.setPrefHeight(40);

            Text addAmountText = new Text("\nADD AMOUNT TO BALANCE\n");
            addAmountText.setFont(Font.font("laluna", FontWeight.BOLD, 20));

            TextField enterAmountField = new TextField();
            enterAmountField.setPromptText("Enter Amount");
            enterAmountField.setStyle("-fx-pref-width: 50px; -fx-pref-height: 40px;");


            Button addToTotalAmountButton = new Button("ADD");
            addToTotalAmountButton.setStyle("-fx-background-color: #161616; -fx-text-fill: white;");
            addToTotalAmountButton.setPrefWidth(160);
            addToTotalAmountButton.setPrefHeight(40);

            Text currentAmountAvailableText = new Text("Current amount available: " + getTotalWinnings());
            currentAmountAvailableText.setFont(Font.font("laluna", FontWeight.BOLD, 20));

            // Actions
            addToTotalAmountButton.setOnAction(e -> {

                try {
                    double amountToAdd = Double.parseDouble(enterAmountField.getText());
                    setTotalWinnings(amountToAdd);
                    currentAmountAvailableText.setText("Current amount available: " + getTotalWinnings());
                    enterAmountField.clear();
                }
                catch (NumberFormatException ex) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Input Error");
                    alert.setHeaderText(null);
                    alert.setContentText("Please enter a valid number.");
                    alert.showAndWait();
                }
            });

            playGameButton.setOnAction(e -> primaryStage.setScene(bettingPageScene));

            VBox homepageLayout = new VBox(10);
            homepageLayout.getChildren().addAll(Heading, playGameButton, addAmountText, enterAmountField, addToTotalAmountButton, currentAmountAvailableText);
            homepageLayout.setAlignment(Pos.CENTER);

            Color backgroundColor = Color.rgb(0, 95, 47);
            BackgroundFill backgroundFill = new BackgroundFill(backgroundColor, CornerRadii.EMPTY, Insets.EMPTY);
            Background bgHOME = new Background(backgroundFill);
            homepageLayout.setBackground(bgHOME);

            primaryStage.sceneProperty().addListener((observable, bettingPageScene, homepageScene) -> {
                if (homepageScene != null) {
                    currentAmountAvailableText.setText("Current amount available: " + getTotalWinnings());
                }
            });

            homepageScene = new Scene(homepageLayout, 1000, 1000);
        }

        // ------------------------ Betting Page setup ------------------------

        {
            // elements setup
            Text warning = new Text("Beware: Engaging in betting even with fake money \ncan lead to legal consequences and financial losses.");
            warning.setFont(Font.font("laluna", FontWeight.BOLD, 20));

            Button loadButton = new Button("BET");
            loadButton.setStyle("-fx-background-color: #161616; -fx-text-fill: white;");
            loadButton.setPrefWidth(160);
            loadButton.setPrefHeight(40);

            Text chooseAmountText = new Text("Choose an amount lower than your total");
            chooseAmountText.setFont(Font.font("laluna", FontWeight.BOLD, 20));

            TextField betAmount = new TextField();
            betAmount.setPromptText("Enter Amount");
            betAmount.setStyle("-fx-pref-width: 50px; -fx-pref-height: 40px;");

            Button placeBetButton = new Button("Place Bet");
            placeBetButton.setStyle("-fx-background-color: #161616; -fx-text-fill: white;");
            placeBetButton.setPrefWidth(160);
            placeBetButton.setPrefHeight(40);

            Text totalAmountAvailableText = new Text("Total amount available:" + getTotalWinnings());
            totalAmountAvailableText.setText("Total amount available:" + getTotalWinnings());
            totalAmountAvailableText.setFont(Font.font("laluna", FontWeight.BOLD, 20));

            Button backButton = new Button("Back");
            backButton.setStyle("-fx-background-color: #161616; -fx-text-fill: white;");
            backButton.setPrefWidth(160);
            backButton.setPrefHeight(40);

            chooseAmountText.setVisible(false);
            betAmount.setVisible(false);
            placeBetButton.setVisible(false);
            totalAmountAvailableText.setVisible(false);

            loadButton.setOnAction(e ->
                    {
                        totalAmountAvailableText.setText("Total amount available:" + getTotalWinnings());
                        loadButton.setVisible(false);
                        warning.setVisible(false);

                        chooseAmountText.setVisible(true);
                        betAmount.setVisible(true);
                        placeBetButton.setVisible(true);
                        totalAmountAvailableText.setVisible(true);
                    }
                    );


            placeBetButton.setOnAction(e ->
                    {
                        setCurrentBet(Double.parseDouble(betAmount.getText()));

                        if (allowBet()) {
                            setTotalWinnings(-1*getCurrentBet());
                            startGame();
                            primaryStage.setScene(gameAreaPageScene);
                        } else {
                            betAmount.clear();
                            Alert alert = new Alert(Alert.AlertType.WARNING);
                            alert.setTitle("Betting Amount Error");
                            alert.setContentText("The amount you chose to bet is more than your total balance.");
                            alert.showAndWait();
                        }
                    }
            );
            backButton.setOnAction(e -> primaryStage.setScene(homepageScene));

            VBox bettingPageLayout = new VBox(10);
            bettingPageLayout.getChildren().addAll(warning, loadButton, chooseAmountText, betAmount, placeBetButton, totalAmountAvailableText, backButton);
            bettingPageLayout.setAlignment(Pos.CENTER);

            Color backgroundColor = Color.rgb(0, 95, 47);
            BackgroundFill backgroundFill = new BackgroundFill(backgroundColor, CornerRadii.EMPTY, Insets.EMPTY);
            Background bgHOME = new Background(backgroundFill);
            bettingPageLayout.setBackground(bgHOME);

            primaryStage.sceneProperty().addListener((observable, gameAreaPageScene, bettingPageScene) -> {
                if (bettingPageScene != null) {
                    totalAmountAvailableText.setText("Total amount available:" + getTotalWinnings());
                }
            });

            bettingPageScene = new Scene(bettingPageLayout, 1000, 1000);
        }



        // ------------------------ GameArea Page setup ------------------------
        {

            // Elements

            HBox playerCardBox = new HBox(10);
            HBox bankerCardBox = new HBox(10);


            AtomicInteger hitCount = new AtomicInteger();
            Text warning = new Text("You won't be able to quit the game once you continue");
            warning.setFont(Font.font("laluna", FontWeight.BOLD, 20));

            Button loadPage = new Button("CONTINUE");
            loadPage.setStyle("-fx-background-color: #161616; -fx-text-fill: white;");
            loadPage.setPrefWidth(160);
            loadPage.setPrefHeight(40);

            Button hitButton = new Button("HIT");
            hitButton.setStyle("-fx-background-color: #161616; -fx-text-fill: white;");
            hitButton.setPrefWidth(160);
            hitButton.setPrefHeight(40);

            Button stayButton = new Button("STAY");
            stayButton.setStyle("-fx-background-color: #161616; -fx-text-fill: white;");
            stayButton.setPrefWidth(160);
            stayButton.setPrefHeight(40);

            Button exitButton = new Button("EXIT");
            exitButton.setStyle("-fx-background-color: #161616; -fx-text-fill: white;");
            exitButton.setPrefWidth(160);
            exitButton.setPrefHeight(40);

            HBox buttonBox = new HBox(20, hitButton, stayButton);
            buttonBox.setAlignment(Pos.CENTER);

            Text bustMessage = new Text("BUST! Amount lost: " + getCurrentBet());
            bustMessage.setFont(Font.font("laluna", FontWeight.BOLD, 20));
            bustMessage.setVisible(false);

            Text winMessage = new Text("YOU WON! Amount won: " + getCurrentBet());
            winMessage.setFont(Font.font("laluna", FontWeight.BOLD, 20));
            winMessage.setVisible(false);

            Text lossMessage = new Text("YOU LOSE! Amount lost: " + getCurrentBet());
            lossMessage.setFont(Font.font("laluna", FontWeight.BOLD, 20));
            lossMessage.setVisible(false);

            Text drawMessage = new Text("This is a draw, NO EXCHANGE OF MONEY!");
            drawMessage.setFont(Font.font("laluna", FontWeight.BOLD, 20));
            drawMessage.setVisible(false);


            hitButton.setVisible(false);
            stayButton.setVisible(false);
            exitButton.setVisible(false);


            ImageView dealerIMG = new ImageView(new Image("assets/dealer.png"));
            dealerIMG.setPreserveRatio(true);
            dealerIMG.setFitHeight(100);


            HBox bottomImages = new HBox(20);
            bottomImages.getChildren().addAll(dealerIMG, bustMessage, winMessage, lossMessage, drawMessage);

            HBox.setHgrow(dealerIMG, Priority.ALWAYS);
            dealerIMG.fitWidthProperty().bind(bottomImages.widthProperty().divide(2));
            bottomImages.setAlignment(Pos.CENTER);


            // Actions

            exitButton.setOnAction( e->
            {
                playerHand.clear();
                bankerHand.clear();
                playerCardBox.getChildren().clear();
                bankerCardBox.getChildren().clear();
                setCurrentBet(0);
                primaryStage.setScene(bettingPageScene);
            });

            loadPage.setOnAction(e ->
            {
                loadPage.setVisible(false);
                warning.setVisible(false);
                hitButton.setVisible(true);
                stayButton.setVisible(true);
                startGame();

                // Cards
                for (Card card : getPlayerHand()) {
                    if (card != null) {
                        int cardValue = card.getValue();
                        String[] valueInStr = new String[]{"ace", "2", "3", "4", "5", "6", "7", "8", "9", "10", "jack", "queen", "king"};
                        ImageView cardIMG = new ImageView(new Image("cards/" + (valueInStr[cardValue-1]) + "_of_" + card.getSuit() + ".png"));
                        cardIMG.setPreserveRatio(true);
                        cardIMG.setFitHeight(200);
                        playerCardBox.getChildren().add(cardIMG);
                    }
                    else {
                        ImageView cardIMG = new ImageView(new Image("cards/blank_card.png"));
                        cardIMG.setPreserveRatio(true);
                        cardIMG.setFitHeight(200);
                        playerCardBox.getChildren().add(cardIMG);
                    }
                }
            });


            hitButton.setOnAction(e -> {

                hitCount.getAndIncrement();
                // Logic for "HIT" action
                if (playerHit() == 0) {
                    // BUST

                    // Cards
                    Card card = getPlayerHand().get(hitCount.get() +1);

                    int cardValue = card.getValue();
                    String[] valueInStr = new String[]{"ace", "2", "3", "4", "5", "6", "7", "8", "9", "10", "jack", "queen", "king"};
                    ImageView cardIMG = new ImageView(new Image("cards/" + (valueInStr[cardValue-1]) + "_of_" + card.getSuit() + ".png"));
                    cardIMG.setPreserveRatio(true);
                    cardIMG.setFitHeight(200);
                    playerCardBox.getChildren().add(cardIMG);

                    Card opCard = new Card("DIAMONDS", 1000);
                    getBankerHand().add(opCard);
                    getPlayerHand().clear();
                    getBankerHand().clear();
                    bustMessage.setVisible(true);
                    bustMessage.setText(": BUST! Amount lost: " + getCurrentBet());
                    hitButton.setVisible(false);
                    stayButton.setVisible(false);
                    exitButton.setVisible(true);

                } else {

                    // Cards
                    Card card = getPlayerHand().get(hitCount.get() +1);

                    int cardValue = card.getValue();
                    String[] valueInStr = new String[]{"ace", "2", "3", "4", "5", "6", "7", "8", "9", "10", "jack", "queen", "king"};
                    ImageView cardIMG = new ImageView(new Image("cards/" + (valueInStr[cardValue-1]) + "_of_" + card.getSuit() + ".png"));
                    cardIMG.setPreserveRatio(true);
                    cardIMG.setFitHeight(200);
                    playerCardBox.getChildren().add(cardIMG);
                }
            });

            stayButton.setOnAction(e -> {

                // Logic for "STAY" action
                playerStay();

                for (Card card: getBankerHand()) {

                    int cardValue = card.getValue();
                    String[] valueInStr = new String[]{"ace", "2", "3", "4", "5", "6", "7", "8", "9", "10", "jack", "queen", "king"};
                    ImageView cardIMG = new ImageView(new Image("cards/" + (valueInStr[cardValue-1]) + "_of_" + card.getSuit() + ".png"));
                    cardIMG.setPreserveRatio(true);
                    cardIMG.setFitHeight(200);
                    bankerCardBox.getChildren().add(cardIMG);
                }

                double winnings = evaluateWinnings();
                if (winnings > 0) {

                    // WON
                    getPlayerHand().clear();
                    getBankerHand().clear();
                    winMessage.setVisible(true);
                    winMessage.setText("YOU WON! Amount won: " + getCurrentBet());
                    hitButton.setVisible(false);
                    stayButton.setVisible(false);
                    exitButton.setVisible(true);
                } else if (winnings < 0) {

                    // LOSE
                    getPlayerHand().clear();
                    getBankerHand().clear();
                    lossMessage.setVisible(true);
                    lossMessage.setText("YOU LOSE! Amount lost: " + getCurrentBet());
                    hitButton.setVisible(false);
                    stayButton.setVisible(false);
                    exitButton.setVisible(true);
                } else {

                    // DRAW
                    getPlayerHand().clear();
                    getBankerHand().clear();
                    drawMessage.setVisible(true);
                    drawMessage.setText("This is a draw, NO EXCHANGE OF MONEY!");
                    hitButton.setVisible(false);
                    stayButton.setVisible(false);
                    exitButton.setVisible(true);
                }
            });

            // Layout for the game area page
            VBox gameAreaLayout = new VBox(20); // 20 is the spacing between VBox elements
            gameAreaLayout.getChildren().addAll(exitButton, warning, loadPage, playerCardBox, bankerCardBox, buttonBox, bottomImages);
            VBox.setVgrow(playerCardBox, Priority.ALWAYS);
            VBox.setVgrow(buttonBox, Priority.ALWAYS);
            VBox.setVgrow(bottomImages, Priority.ALWAYS);

            Color backgroundColor = Color.rgb(0, 95, 47);
            BackgroundFill backgroundFill = new BackgroundFill(backgroundColor, CornerRadii.EMPTY, Insets.EMPTY);
            Background bgHOME = new Background(backgroundFill);
            gameAreaLayout.setBackground(bgHOME);


            primaryStage.sceneProperty().addListener((observable, bettingPageScene, gameAreaPageScene) -> {
                if (gameAreaPageScene != null) {

                    hitCount.set(0);
                    getPlayerHand().clear();
                    getBankerHand().clear();
                    playerCardBox.getChildren().clear();
                    bankerCardBox.getChildren().clear();

                    startGame();

                    loadPage.setVisible(true);
                    warning.setVisible(true);
                    bustMessage.setVisible(false);
                    winMessage.setVisible(false);
                    lossMessage.setVisible(false);
                    drawMessage.setVisible(false);

                }
            });


            gameAreaPageScene = new Scene(gameAreaLayout, 1000, 1000);
        }

        // ------------------------ Set initial scene ------------------------

        primaryStage.setTitle("Blackjack Game");
        primaryStage.setScene(homepageScene);
        primaryStage.show();
    }



    public static void main(String[] args) {
        launch(args);
    }
}