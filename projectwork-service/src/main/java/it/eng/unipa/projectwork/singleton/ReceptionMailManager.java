package it.eng.unipa.projectwork.singleton;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

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
	private void eleaboraSingolaMail(Message message) throws MailNotSendException {
		Message response = new Message("ERRORE ELABORAZIONE AUCTION", "dd", TYPE.HTML);
		try {
			Supplier user = userService.getSupplierFromEmail(message.getMittente());
			if(user!=null) {
				Auction auction = convertiMessaggioInAuction(message);
				if(auction!=null) {
					Auction aret = auctionService.add(auction, user.getUsername(), (a)->a);
					response = new Message("OK AUCTION "+aret.getOid(), "OK", TYPE.HTML);
				}
			}
		}finally {
			sendMail.sendMail(response, message.getMittente());
		}
	}

	private Auction convertiMessaggioInAuction(Message message) {
//		String title = title;
//		String description = description;
//		String supplier = supplier;
//		String product = product;
//		Date startAuction = startAuction;
//		Date endAuction = endAuction;
//		PRICING pricing = pricing;
//		boolean suspend  = suspend; 
//		HashMap<String, String> ogg_mail = new HashMap<String, String>();
//		 ogg_mail.put(mittente, message.getMittente());
//		 ogg_mail.put(title, message.getSubject())
//		 
//		 
//		return au new Auction(String title,String description,Supplier supplier,Product product,Date startAuction,Date endAuction,PRICING pricing,boolean suspend) {
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

