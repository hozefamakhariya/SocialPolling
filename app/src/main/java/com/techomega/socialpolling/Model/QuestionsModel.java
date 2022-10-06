package com.techomega.socialpolling.Model;

public class QuestionsModel {

    String question;
    String choice1;
    String choice2;
    String choice3;
    String choice4;
    String category;
    String image;

    public QuestionsModel(){

    }

    public String getCategory() {
        return category;
    }

    public String getChoice1() {
        return choice1;
    }

    public String getChoice2() {
        return choice2;
    }

    public String getChoice3() {
        return choice3;
    }

    public String getChoice4() {
        return choice4;
    }

    public String getImage() {
        return image;
    }

    public String getQuestion() {
        return question;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setChoice1(String choice1) {
        this.choice1 = choice1;
    }

    public void setChoice2(String choice2) {
        this.choice2 = choice2;
    }

    public void setChoice3(String choice3) {
        this.choice3 = choice3;
    }

    public void setChoice4(String choice4) {
        this.choice4 = choice4;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

}
