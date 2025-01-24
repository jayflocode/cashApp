package org.example.moneyapplication;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.io.IOException;
import java.sql.SQLException;

// class extends Application class which is used by JavaFX
public class cashApplication extends Application {
    static Scene scene1, scene2, scene3, scene4;  // scenes/pages used for GUI
    static String font1 = "Tahoma";    // default font
    static String font2 = "Verdana";   // default font 2
    static String style1 = "-fx-background-color: lightgreen;-fx-text-fill: white";  //default font 2
    static String style2 = "-fx-background-color: black;-fx-text-fill: white";//default font 3
    static String transferAmount;  // stores transfer amount value
    static String SendAccount;  // stores account where money is being sent to
    static String activeSession;  // stores which account is in the active session
    private static final Logger logger = LogManager.getLogger(cashApplication.class);


    @Override

    // start of the first stage or login screen stage

    public void start(Stage stage) throws IOException {


        stage.setTitle("Login to Application");
        stage.show();


        //grid pane configuration

        GridPane logGridField = new GridPane();
        logGridField.setAlignment(Pos.CENTER);
        logGridField.setHgap(25);
        logGridField.setVgap(25);
        logGridField.setPadding(new Insets(25, 25, 25, 25));

        logger.info("Primary Stage Loaded");

        scene1 = new Scene(logGridField, 400, 600);

        scene1.getStylesheets().add("app.css");

        logGridField.getStyleClass().add("loginScreen");

        // Labels, Text fields for start page

        HBox title = new HBox();
        Label cashApplication = new Label("Cash Application");
        cashApplication.getStyleClass().add("title");
        cashApplication.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        title.setAlignment(Pos.CENTER);


        Label userName = new Label("Username:");
        userName.getStyleClass().add("credentialFields"); //css for userName

        TextField userNameField = new TextField();

        Label password = new Label("Password:");
        password.getStyleClass().add("credentialFields");//css for password

        PasswordField passwordField = new PasswordField();
        Button loginButton = new Button("Login");

        HBox buttonBox = new HBox();
        buttonBox.setAlignment(Pos.BOTTOM_RIGHT);

        title.getChildren().add(cashApplication);
        buttonBox.getChildren().add(loginButton);


        logGridField.add(title, 0, 0, 2, 1);
        logGridField.add(userName, 0, 1);
        logGridField.add(userNameField, 1, 1);
        logGridField.add(password, 0, 2);
        logGridField.add(passwordField, 1, 2);
        logGridField.add(buttonBox, 0, 3, 2, 1);


        // action listener for login button
        loginButton.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent e) {

                try {
                    mySql connection = new mySql();
                    //evaluates if connection is valid using sql database
                    logger.info("Logging in to Server");
                    if (connection.checkLogin(userNameField.getText(), passwordField.getText())) {

                        // sets active session to the user login field to evaluate other stages
                        activeSession = userNameField.getText();
                        logger.info("Connection Succeeded");
                        stage2(stage);

                    } else {

                        logger.fatal("Database Connection Error");
                        System.out.println("Login failed");
                    }

                } catch (Exception x) {

                    System.out.println(x.getMessage());
                }
            }
        });

        stage.setScene(scene1);


    }

    void stage2(Stage stage2) throws IOException {

        // second page

        stage2.setTitle("Cash Application");
        stage2.show();


        GridPane gridNumField = new GridPane();
        gridNumField.setAlignment(Pos.CENTER);
        gridNumField.setHgap(30);
        gridNumField.setVgap(15);
        gridNumField.setPadding(new Insets(25, 25, 25, 25));

        logger.info("2nd Stage is loading after Authentication");


        scene2 = new Scene(gridNumField, 400, 600);
        scene2.getStylesheets().add("app.css");
        gridNumField.getStyleClass().add("loginScreen");

        mySql connection = new mySql();

        // stores current balance of logged in account
        String bal;
        try {
            bal = String.valueOf(connection.sqlCheckBalance(activeSession));
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        // labels and text fields

        Label account = new Label("Account Number: " + activeSession);
        account.getStyleClass().add("account");  // css for account

        // displays current balance as label
        Label balance = new Label("Balance " + bal);
        balance.getStyleClass().add("balance"); // css for balance

        balance.setMaxWidth(Double.MAX_VALUE);
        balance.setAlignment(Pos.CENTER);


        Label amount = new Label();

        HBox amountBox = new HBox(amount);
        amountBox.setAlignment(Pos.CENTER);
        amount.setFont(Font.font(font1, FontWeight.NORMAL, 20));

        gridNumField.add(account, 0, 0);
        gridNumField.add(balance, 0, 1);
        gridNumField.add(amountBox, 0, 2);

        HBox buttonHBox1 = new HBox();
        HBox buttonHBox2 = new HBox();
        HBox buttonHBox3 = new HBox();
        HBox buttonHBox4 = new HBox();
        HBox buttonHBox5 = new HBox();

        //number keypad

        Button seven = new Button("7");
        Button eight = new Button("8");
        Button nine = new Button("9");

        Button four = new Button("4");
        Button five = new Button("5");
        Button six = new Button("6");

        Button one = new Button("1");
        Button two = new Button("2");
        Button three = new Button("3");

        Button pay = new Button("Pay Money");
        pay.setFont(Font.font(font2, FontWeight.NORMAL, 15));

        Button del = new Button("<");
        Button period = new Button(".");
        Button zero = new Button("0");

        // custom styles and fonts for keypad

        seven.setStyle(style1);
        eight.setStyle(style1);
        nine.setStyle(style1);
        four.setStyle(style1);
        five.setStyle(style1);
        six.setStyle(style1);
        one.setStyle(style1);
        two.setStyle(style1);
        three.setStyle(style1);
        zero.setStyle(style1);
        period.setStyle(style1);
        del.setStyle(style1);
        pay.setStyle(style2);

        //custom styling for numberpad hbox spacing and padding

        buttonHBox1.setSpacing(4);
        buttonHBox1.setAlignment(Pos.CENTER);
        buttonHBox1.setScaleX(2);
        buttonHBox1.setScaleY(2);
        buttonHBox1.getChildren().addAll(seven, eight, nine);

        buttonHBox2.setSpacing(4);
        buttonHBox2.setAlignment(Pos.CENTER);
        buttonHBox2.setScaleX(2);
        buttonHBox2.setScaleY(2);
        buttonHBox2.getChildren().addAll(four, five, six);

        buttonHBox3.setSpacing(4);
        buttonHBox3.setAlignment(Pos.CENTER);
        buttonHBox3.setScaleX(2);
        buttonHBox3.setScaleY(2);
        buttonHBox3.getChildren().addAll(one, two, three);


        buttonHBox4.setSpacing(4);
        buttonHBox4.setAlignment(Pos.CENTER);
        buttonHBox4.setScaleX(2);
        buttonHBox4.setScaleY(2);
        buttonHBox4.getChildren().addAll(zero, period, del);

        buttonHBox5.setAlignment(Pos.CENTER);
        buttonHBox5.getChildren().add(pay);

        gridNumField.add(buttonHBox1, 0, 4);
        gridNumField.add(buttonHBox2, 0, 6);
        gridNumField.add(buttonHBox3, 0, 8);
        gridNumField.add(buttonHBox4, 0, 10);
        gridNumField.add(buttonHBox5, 0, 12);


        stage2.setScene(scene2);

        // all listeners for keypad
        zero.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent e) {

                amount.setText(amount.getText() + "0");

                // strings store values on text input field listeners

            }
        });

        period.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent e) {

                amount.setText(amount.getText() + ".");

                // strings store values on text input field listeners

            }
        });

        del.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent e) {

                if (!amount.getText().isEmpty()) {

                    amount.setText(amount.getText().replaceAll(".$", ""));


                }


                // strings store values on text input field listeners

            }
        });

        seven.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent e) {

                amount.setText(amount.getText() + "7");

                // strings store values on text input field listeners

            }
        });

        eight.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent e) {

                amount.setText(amount.getText() + "8");

                // strings store values on text input field listeners

            }
        });

        nine.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent e) {

                amount.setText(amount.getText() + "9");

                // strings store values on text input field listeners
            }
        });

        four.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent e) {
                amount.setText(amount.getText() + "4");
                // strings store values on text input field listeners
            }
        });

        five.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent e) {
                amount.setText(amount.getText() + "5");
                // strings store values on text input field listeners
            }
        });

        six.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent e) {

                amount.setText(amount.getText() + "6");

                // strings store values on text input field listeners

            }
        });

        one.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent e) {

                amount.setText(amount.getText() + "1");

                // strings store values on text input field listeners

            }
        });

        two.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent e) {

                amount.setText(amount.getText() + "2");

                // strings store values on text input field listeners

            }
        });


        three.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent e) {

                amount.setText(amount.getText() + "3");

                // strings store values on text input field listeners

            }
        });

        pay.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent e) {


                transferAmount = amount.getText();

                try {

                    logger.info("Payment Amount has been Submitted");
                    stage3(stage2);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }

                // strings store values on text input field listeners

            }
        });


    }

    void stage3(Stage stage3) throws IOException {

        //stage where payment is submitted

        stage3.setTitle("Payment Details");
        stage3.show();

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setPadding(new Insets(10, 25, 25, 25));
        grid.setHgap(10);
        grid.setVgap(10);

        HBox fromBox = new HBox();
        fromBox.setSpacing(5);
        Label fromField = new Label("From:");
        Label fromAccount = new Label(activeSession);

        HBox toFieldBox = new HBox(fromField);
        toFieldBox.setSpacing(5);
        Label toField = new Label("To: ");
        TextField phoneNumber = new TextField();
        phoneNumber.setPrefWidth(100);
        phoneNumber.setPromptText("Phone Number");

        fromBox.getChildren().addAll(fromField, fromAccount);
        toFieldBox.getChildren().addAll(toField, phoneNumber);

        Label amount = new Label("Amount: $" + transferAmount);
        HBox amountBox = new HBox(amount);

        Button back = new Button("Back");
        Button payNow = new Button("Pay Now");
        HBox payNowBox = new HBox();
        payNowBox.setSpacing(10);
        payNowBox.getChildren().addAll(back, payNow);


        amountBox.setAlignment(Pos.CENTER);
        payNowBox.setAlignment(Pos.CENTER);

        grid.add(fromBox, 0, 0);
        grid.add(toFieldBox, 0, 2);
        grid.add(amountBox, 0, 3);
        grid.add(payNowBox, 0, 4);


        scene3 = new Scene(grid, 400, 600);
        scene3.getStylesheets().add("app.css");

        fromField.getStyleClass().add("paymentFields");
        fromAccount.getStyleClass().add("account2");
        toField.getStyleClass().add("paymentFields");
        amount.getStyleClass().add("paymentFields");

        back.getStyleClass().add("buttonBlack");
        payNow.getStyleClass().add("buttonBlack");

        grid.getStyleClass().add("loginScreen");
        stage3.setScene(scene3);


        payNow.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent e) {

                mySql connectNow = new mySql();

                SendAccount = phoneNumber.getText();
                double transfer = Double.parseDouble(transferAmount);

                try {

                    logger.info("Session is verifying if Money is Available");

                    if (!SendAccount.equals(activeSession)) { //checks that is not sending to the same account
                        // 300 <= 5000
                        if (transfer <= connectNow.sqlCheckBalance(activeSession)) {

                            logger.info("Money is available");
                            logger.info("Deducting Money from Sender Account");
                            connectNow.removeMoney(activeSession, transferAmount);
                            logger.info("Adding money to recipient account");
                            connectNow.addMoney(SendAccount, transferAmount);

                            stage4(stage3);

                        } else {
                            System.out.println("Money Not Available");
                            logger.fatal("Transaction Failed");
                        }
                    }
                } catch (SQLException | ClassNotFoundException ex) {
                    throw new RuntimeException(ex);
                } catch (Exception ex) {
                    System.out.println(ex.getMessage());
                }

                // strings store values on text input field listeners
            }
        });


        back.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent e) {

                try {
                    stage2(stage3);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                // strings store values on text input field listeners

            }
        });

    }

    void stage4(Stage stage) throws IOException {

        // last page used to display the amount transferred complete

        stage.setTitle("Transfer Complete");

        logger.info("Fourth Stage is loading");
        logger.info("Transfer is now Complete");


        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setPadding(new Insets(10, 25, 25, 25));
        grid.setHgap(10);
        grid.setVgap(10);

        Label transferComplete = new Label("$" + transferAmount + " sent to " + SendAccount);
        Button back = new Button("Return");
        HBox backBox = new HBox(back);
        backBox.setAlignment(Pos.CENTER);

        grid.add(transferComplete, 0, 0);
        grid.add(backBox, 0, 5);


        scene4 = new Scene(grid, 400, 600);
        scene4.getStylesheets().add("app.css");
        grid.getStyleClass().add("loginScreen");
        transferComplete.getStyleClass().add("title");
        back.getStyleClass().add("buttonBlack");
        stage.setScene(scene4);


        back.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent e) {

                try {
                    stage2(stage);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                // strings store values on text input field listeners

            }
        });

    }

}
