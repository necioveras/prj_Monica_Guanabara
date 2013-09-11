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
    	String msg = message.getString();
    	
    	//inserir as credenciais
    	ConfigurationBuilder cb = new ConfigurationBuilder();
    	cb.setDebugEnabled(false)
    		.setOAuthConsumerKey("xxxxxxxxxxxxxxxxxxxxxxxxxx")
    		.setOAuthConsumerSecret("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx")
    		.setOAuthAccessToken("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx")
    		.setOAuthAccessTokenSecret("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxx")
    	  	.setUser("monicaguanabara")
    	  	.setPassword("xxxxxxxxxxx");
    	
    	TwitterFactory tf = new TwitterFactory(cb.build());
    	Twitter twitter = tf.getInstance();    	    	    	    	    
    	
        System.out.println("Twitando:" + msg);      
        
        twitter.sendDirectMessage("necioveras", msg);    	    	
        
        return true;    	
    }
}
