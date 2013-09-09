// Internal action code for project prj_Monica_Guanabara

package internalActions;

import jason.asSemantics.DefaultInternalAction;
import jason.asSemantics.TransitionSystem;
import jason.asSemantics.Unifier;
import jason.asSyntax.StringTerm;
import jason.asSyntax.Term;
import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

public class sendDMTwitter extends DefaultInternalAction {

    @Override
    public Object execute(TransitionSystem ts, Unifier un, Term[] args) throws Exception {
        
    	StringTerm message = (StringTerm) args[0];
    	
    	ConfigurationBuilder cb = new ConfigurationBuilder();
    	cb.setDebugEnabled(false)
    		.setOAuthConsumerKey("ZjCoxMWZKbIVcjoGYmbOw")
    		.setOAuthConsumerSecret("zawGuu66gBx3iRX51PLn83WJz1TOY6IHXF4FFGSbfc")
    		.setOAuthAccessToken("40087177-PenGKYWnoSZ1t2yTqFAGJABRrovYNlcSY3hAtBRfE")
    		.setOAuthAccessTokenSecret("UeERqtwBonHr1AH6zHnafwZQiHoaqU7PG1AlRhf8")
    	  	.setUser("necioveras")
    	  	.setPassword("");
    	
    	TwitterFactory tf = new TwitterFactory(cb.build());
    	Twitter twitter = tf.getInstance();    	    	
    	    	    	
    	String msg = message.getString();
        System.out.println("Twitando:" + msg);        	
        twitter.sendDirectMessage(twitter.getId(), msg);    	    	
        
        return true;    	
    }
}
