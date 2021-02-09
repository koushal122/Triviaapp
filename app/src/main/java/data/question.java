package data;

public class question {

    private String question;
    private  boolean answer;

    public question()
    {

    }

    public question(String que,boolean bo)
    {
        question=que;
        answer=bo;
    }

    public String getQuestion() {
        return question;
    }

    public void setAnswer(boolean answer) {
        this.answer = answer;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public boolean isAnswer() {
        return answer;
    }

    @Override
    public String toString() {
        return "question{" +
                "question='" + question + '\'' +
                ", answer=" + answer +
                '}';
    }
}
