package ge.turtlecat.theorytest.bean;


import com.orm.SugarRecord;
import com.orm.dsl.Ignore;

/**
 * Created by Alex on 11/21/2015.
 */
public class Ticket extends SugarRecord {
    private String img = "";
    private String question;
    private String description;
    private int topic;
    private String answers;
    private int corrAnswer;
    private int answered;
    @Ignore
    private String[] answerArr;

    public Ticket() {

    }


    public Ticket(String img, String question, String description, int topic, String answers, int corrAnswer, int answered) {
        this.img = img;
        this.question = question;
        this.description = description;
        this.topic = topic;
        this.answers = answers;
        this.corrAnswer = corrAnswer;
        this.answered = answered;
    }


    public int getAnswered() {
        return answered;
    }

    public void setAnswered(int answered) {
        this.answered = answered;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getTopic() {
        return topic;
    }

    public void setTopic(int topic) {
        this.topic = topic;
    }

    public String[] getAnswerArray() {
        if (answerArr == null)
            answerArr = answers.split("⌇");
        return answerArr;
    }

    public String getAnswers() {
        return answers;
    }

    public void setAnswers(String answers) {
        this.answers = answers;
    }

    public int getCorrAnswer() {
        return corrAnswer;
    }

    public void setCorrAnswer(int corrAnswer) {
        this.corrAnswer = corrAnswer;
    }

    public boolean hasAnsweredCorrectly() {
        return answered == corrAnswer;
    }
}
