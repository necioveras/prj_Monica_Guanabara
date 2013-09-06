// CArtAgO artifact code for project prj_AGuanabara

package workspaces;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.URLEncoder;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import cartago.Artifact;
import cartago.INTERNAL_OPERATION;
import cartago.OPERATION;

public class EnvironmentWeb extends Artifact {
	void init() {
		defineObsProperty("source", "");
	}
	
	@OPERATION
	void loadItineraries(int from, int to, String dateFrom, String dateTo){
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
            System.out.println(sb);
            System.out.println(page);
			s.close();			
			/*File f = new File("result.html");
			f.setWritable(true);
			FileWriter fw = new FileWriter(f);
			fw.write(page.toString());
			fw.close();
            processResults(f);*/
		} catch (java.io.IOException e) { e.printStackTrace();}
	}	
	
	@INTERNAL_OPERATION
	void processResults(File f){
		try {		
			DocumentBuilderFactory build = DocumentBuilderFactory.newInstance();
			build.setIgnoringComments(false);
			build.setIgnoringElementContentWhitespace(false);
			DocumentBuilder db;
			db = build.newDocumentBuilder();
			System.out.println("processando...");			
			Document doc = db.parse(f);			
			System.out.println("analisando...");
			NodeList nl = doc.getElementsByTagName("srv_ida");
			System.out.println("Encontramos " + nl.getLength() + " serviço(s) de ida.");
			
			} catch (ParserConfigurationException e) {e.printStackTrace();} 
		       catch (IOException i) { i.printStackTrace();}	
		       catch (SAXException s) {s.printStackTrace();}				
		
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

