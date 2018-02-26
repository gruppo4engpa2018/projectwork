package it.eng.unipa.projectwork.email.exception;

public class MailNotReceptionException extends Exception {


		private static final long serialVersionUID = 1L;

		public MailNotReceptionException(Exception e) {
			super(e);
		}

	}
