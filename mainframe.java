import java.io.IOException;
import java.net.URL;
import java.util.regex.Pattern;
import java.util.ResourceBundle;
import javafx.scene.control.Label;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.HBox;
import java.util.Random;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;


public class mainframe {  
    //@FXML
    //private HBox one, two, three, four, five, six;
    @FXML
    //These buttons corresponds to the two button on the wordle game screen 
    //in which one's function is to start another round while the other one compares the letter 
    //that is being inputed with the random word chosen from the wordbank
    private Button Check,Restart;
    @FXML
    // The label outputs messages either when you successfully completed the game or failed to do so
    private Label result;
    //@FXML
    //private AnchorPane view;
    //@FXML
    //private TextField title;
    @FXML
    // This corresponds to the 6x6 boxes on the wordle game screen 
    private VBox vbox;
    //Another method to do so is also shown
   // private TextField oneone,onetwo,onethree,onefour,onefive,twoone,twotwo,twothree,twofour,twofive,threeone,threetwo,threethree,threefour,threefive,fourone,fourtwo,fourthree,fourfour,fourfive,fiveone,fivetwo,fivethree,fivefour,fivefive,sixone,sixtwo,sixthree,sixfour,sixfive;
    
    String wordle;
    // corresponds to a five word letter that is randomly chosen from the wordbank below
    int guesses;
    // corresonds to the number of guesses it takes in order to get the correct word
    boolean correct;
    // set correct as a boolean in order check if the letter inputed by the user matches the letter chosen from the wordbank
    // This is later set to false in the initialize section 
   
    // This initialize the mainframe or the wordle game screen everytime you refresh or load into the game
    public void restart(ActionEvent event) throws IOException {
        initialize();
    }


    public void initialize() throws IOException {
        // over 40 letter is used in this randomly chosen word bank
        String wordBank[] = {"crane","coast","about","piano","alone","above","media","radio","voice","alive","ocean","image","olive","quiet","video","cause","sauce","movie","juice","noise","abuse","opera","naive","email","Azure"};
        Random random = new Random();
        int index = random.nextInt(wordBank.length);
        wordle = wordBank[index];
        guesses = 0; // set number of guesses to zero 
        correct = false;
        result.setText("");//clear all message when the game is started    
        boolean first = true;
    

        //This sets the color of the boxes in which the row you are on is shown 
        //with a darker tone of the color while the other one is lighter


        for(Node box: vbox.getChildren()){
            for(Node child:((HBox)box).getChildren()){
                TextField temp = (TextField) child;
                if(first)
                    temp.setDisable(false);
                else
                    temp.setDisable(true);
                    temp.setText("");
                    temp.setStyle("-fx-control-inner-background: purple;");
            }


            first = false;
        }

    }
    public void check(ActionEvent event) throws IOException{

     if (!correct && guesses < 6) { 
        //All under the condition that less than six attempts has being made by the user 
        boolean valid = true;
        // changes to false if the attempt / guess is invaild 
        result.setText("");
        // all text is cleared again
        result.setStyle("-fx-text-fill: black;");
        //Allows only letter to be inputed in the box, else valid = false
        HBox box = (HBox) vbox.getChildren().get(guesses);
        for (Node child : box.getChildren()) {
            TextField temp = (TextField) child;
            String letter = temp.getText();
            if (letter.isEmpty() || !Character.isLetter(letter.charAt(0)))
                valid = false;
        }
        //The number of guess is updated here 
        if (valid)
            updateGuess();
        //if all condition is not met than the following command will be excuted 
        else if (!valid) {
            result.setText("please make sure all the boxes are filled and there is only one letter in each box");
            result.setStyle("-fx-text-fill: blue;");
        }
    // When all six row is filled and the letter inputed by the user still 
    // does not match the word chosen from the wordbank
    } else {
        result.setText("Click Restart in order to play again");
        result.setStyle("-fx-text-fill: green;");
    }
}
 
    public void updateGuess() throws IOException {
        int x = 0;
        // assign x as 0 so the code knows which letter it is comparing against the letter inputed by the user
        String word = wordle;
        // Let word equal to wordle 
        correct = true;
        // set the condition of correct to true so the codes will know when will the win condition is met
       
        HBox box = (HBox) vbox.getChildren().get(guesses);
        // get the hbox that corresponds to the number of row the user is on

        
        for (Node child : box.getChildren()) {
            //This process is repeated each of the Hbox within the code
            TextField temp = (TextField) child;
            temp.setText(temp.getText().substring(0, 1).toLowerCase());
            temp.setDisable(true);
            //This allows the user to input lower case letter in each of the boxes so
            //When comparing the user input with the letter from wordbank, they are both in lowercase
            String letter = temp.getText();
            if (letter.charAt(0) == wordle.charAt(x)) {
                // word is set equal to replaceFirst
                word = word.replaceFirst(Pattern.quote("" + letter.charAt(0)), "");
                // This enable the code to outline the correct letter in correct location as green just as the normal wordle would do
                temp.setStyle("-fx-control-inner-background: green;");
            } else
                correct = false;
                // if the letter did not match, then boolean correct will be set to false once again
                x++;
                //integer x is increased 
        }

       //The process is repeated but this time 
        for (Node child : box.getChildren()) {
            TextField temp = (TextField) child;
            String letter = temp.getText();
            if (word.contains(letter)) {
               //This if statement enable the code to outline the correct letter but in the wrong location as yellow just as the normal wordle would do 
                word = word.replaceFirst(Pattern.quote("" + letter.charAt(0)), "");
                //Word is set equal to replaceFirst 
                temp.setStyle("-fx-control-inner-background: yellow;");
            }
        }
        guesses++;// integer guesses is increased

        //Give editing access if the user think they inputed the wrong thing in the box.
        // (does not work after you clicked check and all six row is filled )
        if (guesses < 6 && !correct) {
            box = (HBox) vbox.getChildren().get(guesses);
            for (Node child : box.getChildren()) {
                ((TextField) child).setDisable(false);
            }
        } else if (correct) {
            //Win condition
            result.setText("YOU GOT THE WORD!!!, it was" + wordle);
            result.setStyle("-fx-text-fill: brown;");
        } else {
            //tell the user to try again
            result.setText("You failed to guess the \nword. It was " + wordle);
            result.setStyle("-fx-text-fill: purple;");
        }
    }
}