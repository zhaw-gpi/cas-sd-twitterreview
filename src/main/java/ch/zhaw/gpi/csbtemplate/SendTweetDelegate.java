package ch.zhaw.gpi.csbtemplate;

import javax.inject.Named;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Enthält die Business Logik, welche für vom External Task Client gefetchte
 * Tasks abarbeitet und der Process Engine als erledigt mitteilt. In diesem
 * Beispiel umfasst dies das Senden eines Tweets über den Twitter Service.
 *
 * @author scep
 */
@Named("sendTweet")
public class SendTweetDelegate implements JavaDelegate {

    // Das eigentliche Posten des Tweets ist ausgelagert an eine Service-Klasse,
    // die hier instanziert wird.
    @Autowired
    TwitterService twitterService;

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        // Tweet Content aus Prozessvariable auslesen
        String tweetContent = (String) execution.getVariable("tweetContent");

        twitterService.updateStatus(tweetContent);
    }
}
