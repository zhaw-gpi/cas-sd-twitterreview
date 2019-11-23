package ch.zhaw.gpi.csbtemplate;

import javax.inject.Named;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

/**
 * GetUserInformationDelegate
 */
@Named("getUserInformation")
public class GetUserInformationDelegate implements JavaDelegate {

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        String userName = (String) execution.getVariable("mitarbeiter");

        RestTemplate restTemplate = new RestTemplate();

        String fullName;
        String email;
        try {
            ResponseEntity<User> userResponse = restTemplate.exchange("http://localhost:8070/users/{userName}", HttpMethod.GET,  null, User.class, userName);
            User user = userResponse.getBody();
            fullName = user.getFirstName() + " " + user.getOfficialName();
            email = user.geteMail();
        } catch (Exception e) {
            fullName = "Mr. X";
            email = "a@b.ch";
        }

        execution.setVariable("fullName", fullName);
        execution.setVariable("email", email);
    }

    
}