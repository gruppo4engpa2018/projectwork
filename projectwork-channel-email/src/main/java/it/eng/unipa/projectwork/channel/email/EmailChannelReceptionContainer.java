package it.eng.unipa.projectwork.channel.email;

import it.eng.unipa.projectwork.channel.AbstractChannelContainer;
import javax.ejb.DependsOn;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import it.eng.unipa.projectwork.email.impl.ReceptionMailImpl;
import it.eng.unipa.projectwork.channel.AbstractChannelContainer;
import it.eng.unipa.projectwork.email.SendMail;
import it.eng.unipa.projectwork.model.User;
import it.eng.unipa.projectwork.service.UserService;

@Singleton

public class EmailChannelReceptionContainer extends AbstractChannelContainer<EmailChannel>{

	@Schedule(minute="0/5".persistent=false)	
	public void newCheck() {
		checkMail();
	}
	
	
	
	
	@Override
	public String getType() {
		// TODO Auto-generated method stub
		return null;
	}

}
