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
   loadItineraries(31, 71, "130906", "130909").
	
-?setupArtifact(E) : true <-
	.wait(30);
	!create.
