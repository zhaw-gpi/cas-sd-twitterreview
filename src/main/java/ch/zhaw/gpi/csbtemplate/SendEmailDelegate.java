package ch.zhaw.gpi.csbtemplate;

import javax.inject.Named;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * SendEmailDelegate
 */
@Named("sendEmail")
public class SendEmailDelegate implements JavaDelegate {

    @Autowired
    private EmailService emailService;

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        String status = (String) execution.getVariable("checkResult");
        String email = (String) execution.getVariable("email");
        String fullName = (String) execution.getVariable("fullName");
        String tweet = (String) execution.getVariable("tweetContent");
        String comment = (String) execution.getVariable("comment");

        String subject;
        String body;

        if(status.equals("rejected")){
            subject = "Tweet-Anfrage abgelehnt";
            body = "Guten Tag " + fullName + "\n\nIhre folgende TweetAnfrage wurde abgelehnt: " + tweet
                + "\n\nDie Begründung ist: " + comment + "\n\nIhre Prozessplattform";
        } else {
            subject = "Tweet veröffentlicht";
            body = "Guten Tag " + fullName + "\n\nIhre folgende TweetAnfrage wurde veröffentlicht: " + tweet
                + "\n\nIhre Prozessplattform";
        }

        emailService.sendSimpleMail(email, subject, body);
    }

}