package com.example.comp1008week5;

//extends means MemoryCard will inherit methods from Card
public class MemoryCard extends Card {
    private boolean matched;


    /**
     * This is a constructor. It is called when we want to instantiate (create an
     * instance) or our object.
     * It looks like a method, but does not have a return type and must ave the
     * exact same name as the class
     *
     * @param faceName
     * @param suit
     */
    public MemoryCard(String faceName, String suit) {
        super(faceName, suit);
        matched = false;
    }

    public boolean isMatched() {
        return matched;
    }

    public void setMatched(boolean matched) {
        this.matched = matched;
    }

    public boolean isSameCard(MemoryCard otherCard){
        return (this.getSuit().equals(otherCard.getSuit())
                && this.getFaceName().equals(otherCard.getFaceName()));
    }
}
