package it.eng.unipa.projectwork.singleton;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.ejb.EJB;
import javax.ejb.Schedule;
import javax.ejb.Schedules;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import it.eng.unipa.projectwork.email.Message;
import it.eng.unipa.projectwork.email.Message.TYPE;
import it.eng.unipa.projectwork.email.ReceptionMail;
import it.eng.unipa.projectwork.email.SendMail;
import it.eng.unipa.projectwork.email.exception.MailNotSendException;
import it.eng.unipa.projectwork.model.Auction;
import it.eng.unipa.projectwork.model.Product;
import it.eng.unipa.projectwork.model.Supplier;
import it.eng.unipa.projectwork.model.pricingstrategy.PRICING;
import it.eng.unipa.projectwork.service.AuctionService;
import it.eng.unipa.projectwork.service.UserService;

@Singleton
@Startup
public class ReceptionMailManager {

	@EJB
	UserService userService;

	@EJB
	AuctionService auctionService;


	@EJB
	ReceptionMail receptionMail;
	
	@EJB
	SendMail sendMail;

	/**
	 * 
	@Timeout
	public void timeout(Timer timer) {
	    System.out.println("TimerBean: timeout occurred");
	}
	 * @param timer
	 */

	@Schedules ({
		@Schedule(second="0",minute="*",hour="*",persistent=false)
		
	})
	public void receptionMail() {
		
		List<Message> l = receptionMail.getMails();
		for(Message message: l) {
			try {
				eleaboraSingolaMail(message);
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		//checkMail();
	}
	/*public void sendMail(){
		List<Auction> l = auctionService.loadActiveAuctions((a)->a);
		String today = new SimpleDateFormat("dd/MM/yyyy hh:mm").format(new Date());
		String subject = "Active auctions now: "+today;
		if(!l.isEmpty()){
			String body = body(l);
			try {
				sendMail.sendMailAllUser(new Message(subject, body,TYPE.HTML));
			} catch (MailNotSendException e) {

				e.printStackTrace();
			}
		}
	}
*/
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	private void eleaboraSingolaMail(Message message) throws MailNotSendException, ParseException {
		Message response = new Message("ERRORE ELABORAZIONE AUCTION", "Non è stato possibile processare la"
				+ "tua richiesta d'inserimento Asta. Riprova!", TYPE.HTML);
		try {
			Supplier user = userService.getSupplierFromEmail(message.getMittente());
			if(user!=null) {
				Auction auction = convertiMessaggioInAuction(message);
				if(auction!=null) {
					Auction aret = auctionService.add(auction, user.getUsername(), (a)->a);
					response = new Message("OK AUCTION "+aret.getOid(), "OK LA TUA AUCTION E' STATA INSERITA CORRETTAMENTE", TYPE.HTML);
				}
			}
		}finally {
			sendMail.sendMail(response, message.getMittente());
		}
	}

	private Auction convertiMessaggioInAuction(Message message) throws ParseException {	
		Map<String,String> bodyMap=SplitBodyLine2(message.getBody());
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");		
        Date startAuction = formatter.parse(bodyMap.get("INIZIO ASTA"));
        //System.out.println(formatter.format(date));
        Date endAuction = formatter.parse(bodyMap.get("FINE ASTA"));		
		String title = message.getSubject();
		String description = bodyMap.get("DESCRIZIONE");
		String mittente = message.getMittente();
		String product = bodyMap.get("PRODOTTO");
//		PRICING pricing = null;
//		boolean suspend  = false; 

		 
return new Auction(title,description,mittente,product,startAuction,endAuction,null,false);
	}		

	
	private static Pattern twopart = Pattern.compile("(\\d+):(\\d+)");
	
	
	public String[] SplitBodyLine(String body) {
	    String split[];
	    split = body.split("\\n");
	    return split;
	}
	    
	public Map<String,String> SplitBodyLine2(String body) {
		Map<String,String> map=new HashMap<>();
		String split[];
		 split = body.split("\\n");
		for (int i=0; i<split.length; i++) {			
		 Matcher m = twopart.matcher(split[i]);
	        if (m.matches()) {
	        	map.put(""+m.group(1), ""+m.group(2));
	        } else {
	            map ("","");
	        	}
	        }
		return map;
	}
		 		 
		 private Map<String, String> map(String string, String string2) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	private String body(List<Auction> as){
		StringBuilder sb = new StringBuilder("<table>");
		sb.append("<tr><th>").append("title").append("</th>").append("<th>").append("description").append("</th></tr>");
		for(Auction a : as){
			sb.append("<tr><th>").append(a.getTitle()).append("</th>").append("<th>").append(a.getDescription()).append("</th></tr>");

		}
		sb.append("</table>");
		return sb.toString();
	}
}

