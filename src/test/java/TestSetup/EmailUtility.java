package TestSetup;
import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.EmailAttachment;
import org.apache.commons.mail.MultiPartEmail;

public class EmailUtility {

    public static void sendReportAfterExecution(String reportPath) {
        try {
            // 1. Create the attachment for the Extent Report
            EmailAttachment attachment = new EmailAttachment();
            attachment.setPath(reportPath); 
            attachment.setDisposition(EmailAttachment.ATTACHMENT);
            attachment.setDescription("API Test Execution Report");
            attachment.setName("ExtentReport.pdf");

            MultiPartEmail email = new MultiPartEmail();
            email.setHostName("smtp.gmail.com");

            // REQUIRED SETTINGS FOR PORT 587
            email.setSmtpPort(587);               // Use Port 587
            email.setStartTLSEnabled(true);       // Enable STARTTLS
            email.setSSLOnConnect(false);         // Set this to FALSE for Port 587

           
            email.setAuthenticator(new DefaultAuthenticator("seleniumjava2408@gmail.com", "kwbufgzgpqkuoypf"));

         
            email.addTo("seleniumjava2408@gmail.com", "Stakeholder Name");
            email.setFrom("seleniumjava2408@gmail.com", "QA Automation Team");
            email.setSubject("Automation Test Results: REST API Execution");
            email.setMsg("Please find the attached Extent Report for the latest test run.");

            // 3. Add the attachment and send
            email.attach(attachment);
            email.send();
            System.out.println("Email sent successfully with report.");
            
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Failed to send email: " + e.getMessage());
        }
    }
}
