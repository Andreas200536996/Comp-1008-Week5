package com.example.comp1008week5;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.ResourceBundle;

public class MemoryGameController implements Initializable {


    @FXML
    private Label correctLabel;

    @FXML
    private FlowPane flowPane;

    @FXML
    private Label guessesLabel;

    private int guesses, correct;
    private ArrayList<MemoryCard> cardsInGame;
    private MemoryCard firstCard, secondCard;

    @FXML
    void playAgain() {
        cardsInGame = new ArrayList<>(); //This is sort of like the table you would put the cards on IRL

        //create a deck of Cards and shuffle them
        DeckOfCards deck = new DeckOfCards();
        deck.shuffle();

        //take the first 5 cards (for a game with 10 total cards) and duplicate each one so there can be a match
        for (int i=0; i<flowPane.getChildren().size()/2;i++){
            Card cardDealt = deck.dealTopCard();
            cardsInGame.add(new MemoryCard(cardDealt.getFaceName(),cardDealt.getSuit()));
            cardsInGame.add(new MemoryCard(cardDealt.getFaceName(),cardDealt.getSuit()));
        }
        Collections.shuffle(cardsInGame);
        flipAllCards();



    }



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        playAgain();

        //add a click listener to each ImageView object
        for (int i=0; i<flowPane.getChildren().size();i++) {
            ImageView imageView = (ImageView) flowPane.getChildren().get(i);
            imageView.setUserData(i); //give each ImageView a number we can reference

            //add a click listener to the ImageView
            imageView.setOnMouseClicked(event ->{
                flipCard((int) imageView.getUserData());
            });
        }
    }

    private void flipCard(int indexOfCard){
        ImageView imageView = getImageViewFromIndex(indexOfCard);

        //show the backs of all the cards that are not matched
        if (firstCard==null && secondCard==null)
            flipAllCards();

        if (firstCard == null){//this is the first card flipped
            firstCard = cardsInGame.get(indexOfCard);
            imageView.setImage(firstCard.getImage());
        }
        //check if this is the second card
        //when you use else if, it will only check the "else if" in the scenario that the original "if" was false
        else if(secondCard==null){
            secondCard = cardsInGame.get(indexOfCard);
            imageView.setImage(secondCard.getImage());
            checkForMatch();
        }
    }

    /**
     * This method will check if the two cards showing are the same card. If they are,
     * then it will set their "matched" parameter to be true and set both first and second card to be null again
     * @return
     */
    private void checkForMatch() {
        guesses++;
        if (firstCard.isSameCard(secondCard)){
            firstCard.setMatched(true);
            secondCard.setMatched(true);
            correct++;
        }
        //this guess is now complete, so set the first and second card to be null
        firstCard = null;
        secondCard = null;
        updateLabels();
    }



    private ImageView getImageViewFromIndex(int index){
        return (ImageView) flowPane.getChildren().get(index);
    }

    /**
     * This method loops over all the imageView objects and will check if the
     * card is matched. If it is matched, show the face, if it is not matched, show the back
     */

    private void flipAllCards()
    {
        //loop over all the memory cards
        for (int i=0; i<flowPane.getChildren().size(); i++)
        {
            ImageView imageView = (ImageView) flowPane.getChildren().get(i);//Get the imagView object related to card
            MemoryCard memoryCard = cardsInGame.get(i);//get the MemoryCard object from the list of MemoryCards
            if (memoryCard.isMatched())// check if its matched
                imageView.setImage(memoryCard.getImage());//if matched show front of card
            else
                imageView.setImage(memoryCard.getBackOfCardImage());// if not matched show back of card
        }
    }

    /**
     * This method will display the number of guesses and the number of correct matches
     */

    private void updateLabels(){

        guessesLabel.setText(String.valueOf(guesses));
        correctLabel.setText(String.valueOf(correct));

    }
}
