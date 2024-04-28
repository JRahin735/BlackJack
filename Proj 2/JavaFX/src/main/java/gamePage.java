//import javafx.application.Application;
//import javafx.geometry.Pos;
//import javafx.scene.Scene;
//import javafx.scene.control.Alert;
//import javafx.scene.control.Button;
//import javafx.scene.control.TextField;
//import javafx.scene.image.Image;
//import javafx.scene.image.ImageView;
//import javafx.scene.layout.BorderPane;
//import javafx.scene.layout.HBox;
//import javafx.scene.layout.Priority;
//import javafx.scene.layout.VBox;
//import javafx.scene.text.Text;
//import javafx.stage.Stage;
//
//import java.util.ArrayList;
//
//public class gamePage extends Application {
//
//    public static void main(String[] args) {
//        launch(args);
//    }
//
//    // Define scenes globally so they can be referenced in methods
//    private Scene homepageScene, bettingPageScene, gameAreaPageScene, bustPageScene, winPageScene, lostPageScene, drawPageScene;
//    private BlackjackGame game = new BlackjackGame();
//
//    private ArrayList<Card> playerHand;
//    private ArrayList <Card> bankerHand;
//    private BlackjackDealer theDealer;
//    private BlackjackGameLogic gameLogic;
//    private double currentBet=0;
//    private double totalWinnings=0;
//
//    // getters and setters
//    public ArrayList<Card> getPlayerHand () {return playerHand;}
//    public ArrayList<Card> getBankerHand () {return bankerHand;}
//    public void setTotalWinnings (double amt) {totalWinnings += amt;}
//    public double getTotalWinnings () {return totalWinnings;}
//    public double getCurrentBet () {return currentBet;}
//    public void setCurrentBet (double bet) {currentBet = bet;}
//
//    // display cards
//    public ArrayList<Card> showPlayerCards () {
//
//        ArrayList<Card> cardsToShow = new ArrayList<Card>();
//
//        for (int i = 0; i < playerHand.size(); i++) {
//            if(i == 2) {
//                cardsToShow.add(null);
//                continue;
//            }
//            cardsToShow.add(playerHand.get(i));
//        }
//
//        return cardsToShow;
//    }
//
//    // check whether the bet will make user's total money negative
//    public boolean allowBet () {
//
//        if ((totalWinnings - currentBet) < 0) {
//            currentBet = 0;
//            return false;
//        }
//        else {
//            // allow bet
//            return true;
//        }
//    }
//
//    public void startGame () {
//
//        theDealer.generateDeck();
//        theDealer.shuffleDeck();
//
//        playerHand = theDealer.dealHand();
//        bankerHand = theDealer.dealHand();
//    }
//
//    // add a card
//    // 2 scenarios - more than 21, then bust - less than 21, then nothing
//    // 0-> bust, 1-> continue
//    public int playerHit () {
//
//        playerHand.add(theDealer.drawOne());
//
//        if (gameLogic.handTotal(playerHand) > 21) {
//            // BUST
//            return 0;
//        }
//        else {
//            return 1;
//        }
//    }
//
//    // banker's turn in a loop
//    public void playerStay () {
//
//        // banker hits until 16 or above
//        while (gameLogic.evaluateBankerDraw(bankerHand)) {
//            bankerHand.add(theDealer.drawOne());
//        }
//    }
//
//
//
//    @Override
//    public void start(Stage primaryStage) {
//
//        // ------------------------ Homepage setup ------------------------
//        {
//            // elements setup
//            Button playGameButton = new Button("Play Game");
//            Text addAmountText = new Text("Add Amount");
//            TextField enterAmountField = new TextField();
//            enterAmountField.setPromptText("Enter Amount");
//            Button addToTotalAmountButton = new Button("Add to total amount");
//            Text currentAmountAvailableText = new Text("Current amount available: " + getTotalWinnings());
//            Button backToHomepageButton = new Button("Back");
//
//            // Actions
//            playGameButton.setOnAction(e -> primaryStage.setScene(bettingPageScene));
//            addToTotalAmountButton.setOnAction(e -> {
//
//                try {
//                    double amountToAdd = Double.parseDouble(enterAmountField.getText());
//                    setTotalWinnings(amountToAdd);
//
//                    currentAmountAvailableText.setText("Current amount available: " + getTotalWinnings());
//                    enterAmountField.clear();
//                }
//                catch (NumberFormatException ex) {
//                    Alert alert = new Alert(Alert.AlertType.ERROR);
//                    alert.setTitle("Input Error");
//                    alert.setHeaderText(null);
//                    alert.setContentText("Please enter a valid number.");
//                    alert.showAndWait();
//                }
//            });
//
//
//            VBox homepageLayout = new VBox(10);
//            homepageLayout.getChildren().addAll(playGameButton, addAmountText, enterAmountField, addToTotalAmountButton, currentAmountAvailableText);
//            homepageLayout.setAlignment(Pos.CENTER);
//
//            homepageScene = new Scene(homepageLayout, 500, 800);
//        }
//
//        // ------------------------ Betting Page setup ------------------------
//
//        {
//            // elements setup
//            Text chooseAmountText = new Text("Choose an amount lower than your total");
//            TextField betAmount = new TextField();
//            betAmount.setPromptText("Enter Amount");
//            Button placeBetButton = new Button("Place Bet");
//            Text totalAmountAvailableText = new Text("Total amount available:" + getTotalWinnings());
//            Button backButton = new Button("Back");
//
//            placeBetButton.setOnAction(e ->
//                    {
//                        setCurrentBet(Double.parseDouble(betAmount.getText()));
//
//                        if (allowBet()) {
//                            setTotalWinnings(-1*getCurrentBet());
//                            startGame();
//                            primaryStage.setScene(gameAreaPageScene);
//                        } else {
//                            betAmount.clear(); // Clear the input field
//                            Alert alert = new Alert(Alert.AlertType.WARNING);
//                            alert.setTitle("Betting Amount Error");
//                            alert.setContentText("The amount you chose to bet is more than your total balance.");
//                            alert.showAndWait();
//                        }
//                    }
//            );
//            backButton.setOnAction(e -> primaryStage.setScene(homepageScene)); // Set scene back to homepage
//
//            VBox bettingPageLayout = new VBox(10);
//            bettingPageLayout.getChildren().addAll(chooseAmountText, betAmount, placeBetButton, totalAmountAvailableText, backButton);
//            bettingPageLayout.setAlignment(Pos.CENTER);
//
//            bettingPageScene = new Scene(bettingPageLayout, 500, 800);
//        }
//
//        // ------------------------ GameArea Page setup ------------------------
//        {
//            // Cards
//            HBox cardImagesTop = new HBox(10);
//            for (Card card : game.getPlayerHand()) {
//                if (card != null) {
//
////                    ImageView cardIMG = new ImageView(new Image("cards/" + card.getValue() + "_of_" + card.getSuit() + ".png"));
////to remove
//                    ImageView cardIMG = new ImageView(new Image("assets/dealer.png"));
//                    cardIMG.setPreserveRatio(true);
//                    cardIMG.setFitHeight(200);
//                    cardImagesTop.getChildren().add(cardIMG);
//                }
//            }
//
//            // Buttons
//            Button hitButton = new Button("HIT");
//            Button stayButton = new Button("STAY");
//            HBox buttonBox = new HBox(20, hitButton, stayButton); // 20 is the spacing between buttons
//            buttonBox.setAlignment(Pos.CENTER);
//
//            ImageView dealerIMG = new ImageView(new Image("assets/dealer.png"));
//            dealerIMG.setPreserveRatio(true);
//            dealerIMG.setFitHeight(200); // Adjust as necessary
//
//            ImageView playerIMG = new ImageView(new Image("assets/player.png"));
//            playerIMG.setPreserveRatio(true);
//            playerIMG.setFitHeight(200); // Adjust as necessary
//
//            HBox bottomImages = new HBox(20);
//            bottomImages.getChildren().addAll(dealerIMG, playerIMG);
//
//            HBox.setHgrow(dealerIMG, Priority.ALWAYS);
//            HBox.setHgrow(playerIMG, Priority.ALWAYS);
//            dealerIMG.fitWidthProperty().bind(bottomImages.widthProperty().divide(2));
//            playerIMG.fitWidthProperty().bind(bottomImages.widthProperty().divide(2));
//            bottomImages.setAlignment(Pos.CENTER);
//
//            // Layout for the game area page
//            VBox gameAreaLayout = new VBox(20); // 20 is the spacing between VBox elements
//            gameAreaLayout.getChildren().addAll(cardImagesTop, buttonBox, bottomImages);
//            VBox.setVgrow(cardImagesTop, Priority.ALWAYS);
//            VBox.setVgrow(buttonBox, Priority.ALWAYS);
//            VBox.setVgrow(bottomImages, Priority.ALWAYS);
//
//            gameAreaPageScene = new Scene(gameAreaLayout, 500, 800);
//
//            // Add actions for hit and stay buttons
//            hitButton.setOnAction(e -> {
//
//                // Logic for "HIT" action
//                if (game.playerHit() == 0) {
//                    // BUST
//                    Card opCard = new Card("DIAMONDS", 1000);
//                    game.getBankerHand().add(opCard);
//                    double winnings = game.evaluateWinnings();
//                    primaryStage.setScene(bustPageScene);
//                } else {
//
//                }
//
//            });
//
//            stayButton.setOnAction(e -> {
//
//                // Logic for "STAY" action
//                game.playerStay();
//                double winnings = game.evaluateWinnings();
//                if (winnings > 0) {
//                    // WON
//                    primaryStage.setScene(winPageScene);
//                } else if (winnings < 0) {
//                    // LOSE
//                    primaryStage.setScene(lostPageScene);
//                } else {
//                    // DRAW
//                    primaryStage.setScene(drawPageScene);
//                }
//
//            });
//        }
//        // ------------------------ Bust Page setup ------------------------
//        {
//            Text bustText = new Text("BUST");
//            Text bustAmountText = new Text("Amount lost: " + game.getCurrentBet()); // Assuming getCurrentBet returns the bet amount
//            Button newRoundBustButton = new Button("New Round");
//            Button homeBustButton = new Button("Home");
//
//            newRoundBustButton.setOnAction(e -> primaryStage.setScene(bettingPageScene)); // Start new round
//            homeBustButton.setOnAction(e -> primaryStage.setScene(homepageScene)); // Go to homepage
//
//            VBox bustPageLayout = new VBox(10);
//            bustPageLayout.getChildren().addAll(bustText, bustAmountText, newRoundBustButton, homeBustButton);
//            bustPageLayout.setAlignment(Pos.CENTER);
//
//            bustPageScene = new Scene(bustPageLayout, 500, 800);
//        }
//
//        // ------------------------ Win Page setup ------------------------
//        {
//            Text winText = new Text("YOU WIN");
//            Text winAmountText = new Text("Amount won: " + game.getCurrentBet()); // Adjust as needed for win amount
//            Button newRoundWinButton = new Button("New Round");
//            Button homeWinButton = new Button("Home");
//
//            newRoundWinButton.setOnAction(e -> primaryStage.setScene(bettingPageScene));
//            homeWinButton.setOnAction(e -> primaryStage.setScene(homepageScene));
//
//            VBox winPageLayout = new VBox(10);
//            winPageLayout.getChildren().addAll(winText, winAmountText, newRoundWinButton, homeWinButton);
//            winPageLayout.setAlignment(Pos.CENTER);
//
//            winPageScene = new Scene(winPageLayout, 500, 800);
//        }
//
//        // ------------------------ Lost Page setup ------------------------
//        {
//            Text lostText = new Text("YOU LOSE");
//            Text lostAmountText = new Text("Amount lost: " + game.getCurrentBet()); // Assuming getCurrentBet returns the bet amount
//            Button newRoundLostButton = new Button("New Round");
//            Button homeLostButton = new Button("Home");
//
//            newRoundLostButton.setOnAction(e -> primaryStage.setScene(bettingPageScene));
//            homeLostButton.setOnAction(e -> primaryStage.setScene(homepageScene));
//
//            VBox lostPageLayout = new VBox(10);
//            lostPageLayout.getChildren().addAll(lostText, lostAmountText, newRoundLostButton, homeLostButton);
//            lostPageLayout.setAlignment(Pos.CENTER);
//
//            lostPageScene = new Scene(lostPageLayout, 500, 800);
//        }
//
//        // ------------------------ Draw Page setup ------------------------
//        {
//            Text drawText = new Text("DRAW");
//            Text drawAmountText = new Text("No amount exchanged");
//            Button newRoundDrawButton = new Button("New Round");
//            Button homeDrawButton = new Button("Home");
//
//            newRoundDrawButton.setOnAction(e -> primaryStage.setScene(bettingPageScene));
//            homeDrawButton.setOnAction(e -> primaryStage.setScene(homepageScene));
//
//            VBox drawPageLayout = new VBox(10);
//            drawPageLayout.getChildren().addAll(drawText, drawAmountText, newRoundDrawButton, homeDrawButton);
//            drawPageLayout.setAlignment(Pos.CENTER);
//
//            drawPageScene = new Scene(drawPageLayout, 500, 800);
//        }
//
//        // ------------------------ Set initial scene ------------------------
//
//        primaryStage.setTitle("Blackjack Game");
//        primaryStage.setScene(homepageScene);
//        primaryStage.show();
//    }
//
//}
