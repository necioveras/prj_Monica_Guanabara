// Agent sample_agent in project prj_AGuanabara

/* Initial beliefs and rules */

/* Initial goals */

!create.

/* Plans */

+!create: true <- 
	?setupArtifact(ID).
	
+?setupArtifact(E) : true <-
   makeArtifact("env_web", "workspaces.EnvironmentWeb", [], E);
   focus(E);
   println("Estou no ambiente Web");   
   !monitoring.
	
-?setupArtifact(E) : true <-
	.wait(30);
	!create.

+!monitoring: true <-
 	loadItineraries(31, 71, "130913", "130916", NumSrvGo, NumSrvReturn, ListGo, ListReturn);
 	!buildTwit("Itinerário de ida:", NumSrvGo, ListGo);
 	!buildTwit("Itinerário de volta:", NumSrvReturn, ListReturn).
 	
+!buildTwit(Type, Number, List): true <- 	
	cartago.new_obj("java.lang.StringBuilder", [Type], Message);
 	for(.range(I, 0, Number-1)){
 		cartago.invoke_obj(List,getItinerary(I),Itinerary);
 		cartago.invoke_obj(Itinerary, getTimeToGo, TimeToGo);
 		cartago.invoke_obj(Itinerary, getSeat, Seat);
 		cartago.invoke_obj(Itinerary, getPrice, Price);
 		cartago.invoke_obj(Itinerary, getService, Service);
 		.concat("Serviço (",I+1,") para ", Type, " Hora de Saída: ", TimeToGo, " com ", Seat, " poltronas livres a R$", Price, " tipo:", Service, Msg);
 		cartago.invoke_obj(Message, append(Msg));
 		internalActions.sendDMTwitter(Msg); 
 		.wait(5000);				 
 	};
 	cartago.invoke_obj(Message, toString, Msg);
 	println("Mensagens enviadas:", Msg).  		
 	
 	
 	