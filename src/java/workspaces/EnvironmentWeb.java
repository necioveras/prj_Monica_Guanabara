// CArtAgO artifact code for project prj_AGuanabara

package workspaces;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.URLEncoder;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import cartago.Artifact;
import cartago.INTERNAL_OPERATION;
import cartago.OPERATION;
import cartago.OpFeedbackParam;

import complexTypes.Itinerary;
import complexTypes.ItineraryList;

public class EnvironmentWeb extends Artifact {
	void init() {
		defineObsProperty("source", "");
	}
	
	@OPERATION
	void loadItineraries(int from, int to, String dateFrom, String dateTo, 
			OpFeedbackParam<Integer> numberGo, OpFeedbackParam<Integer> numberReturn,
			OpFeedbackParam<ItineraryList> listGo, OpFeedbackParam<ItineraryList> listReturn){
		try {
			//Socket s = new Socket("redeastor.guanabaraonline.com.br", 8443);
			Socket s = new Socket("redeastor.guanabaraonline.com.br", 80);
			PrintWriter out = new PrintWriter(s.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));                        
            
            String path = "/cgi-bin/br5g.cgi";
            String vars = "ida=idavuelta&txt_desde=" + URLEncoder.encode(String.valueOf(from), "ISO-8859-1")+ 
            		"&txt_hasta="+ URLEncoder.encode(String.valueOf(to), "ISO-8859-1")+
            		"&fecha="+URLEncoder.encode(dateFrom, "ISO-8859-1")+
            		"&fecha_vuelta="+URLEncoder.encode(dateTo, "ISO-8859-1")+
            		"&Submit2=Consultar";            
                                    
            out.println("POST " + path + " HTTP/1.1");
            out.println("Host: redeastor.guanabaraonline.com.br");
            out.println("Content-Type: application/x-www-form-urlencoded");
            out.println("Content-Length: " + vars.length());
            out.println("Cache-Control: no-cache");
            out.println();                
            out.println(vars);
 
            boolean loop = true, p = false;
            StringBuffer sb = new StringBuffer();
            StringBuffer page = new StringBuffer();            
            System.out.println("Recebendo dados....");
            // recupera a resposta quando ela estiver disponível
            while (loop) {
                if (in.ready()) {
                    int i = 0;
                    while ((i = in.read()) != -1) {
                    	if (((char) i == '<') || (p == true)){
                    		page.append((char)i);
                    		p = true;
                    	}
                    	else
                    		 sb.append((char) i);
                    }
                    loop = false;
                }
            }	                                 
            System.out.println("Dados recebidos...");
            Document d = Jsoup.parse(page.toString());
            System.out.println("Dados pré-processados...");
            numberGo.set(processResults(d, "srv_ida", listGo));
            numberReturn.set(processResults(d, "srv_vuelta", listReturn));
            System.out.println("Dados extraídos...");
			s.close();
			
		} catch (java.io.IOException e) { e.printStackTrace();}
	}	
	
	@INTERNAL_OPERATION
	int processResults(Document d, String srv, OpFeedbackParam<ItineraryList> list){		
		int count = 0;
		ItineraryList l = new ItineraryList();		
		for (Element e : d.getElementsByAttributeValue("name", srv)){
			Itinerary i = new Itinerary();
			i.setTimeToGo(e.parent().parent().children().get(1).html());
			i.setSeat(Integer.parseInt(e.parent().parent().children().get(3).html()));
			i.setPrice(e.parent().parent().children().get(4).html());
			i.setService(e.parent().parent().children().get(5).html());
			l.add(i);
			 count++;
		}			
		list.set(l);
		return count;
	}	
	
	@OPERATION
	void sendDataToGoogleTest(String q){
		try {
			Socket s = new Socket("www.google.com.br", 80);
			PrintWriter out = new PrintWriter(s.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
            
            q = URLEncoder.encode(q, "utf-8");
            String path = "/search?q=" + q;
            
            out.println("GET " + path + " HTTP/1.1");
            out.println("Host: www.google.com.br");
            out.println("Connection: keep-alive");
            out.println("text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
            out.println();
 
            boolean loop = true;
            StringBuffer sb = new StringBuffer();
 
            // recupera a resposta quando ela estiver disponível
            while (loop) {
                if (in.ready()) {
                    int i = 0;
                    while ((i = in.read()) != -1) {
                        sb.append((char) i);
                    }
                    loop = false;
                }
            }
            
            System.out.println(sb);
			s.close();
		} catch (java.io.IOException e) { e.printStackTrace();}		
	}	
	
}

