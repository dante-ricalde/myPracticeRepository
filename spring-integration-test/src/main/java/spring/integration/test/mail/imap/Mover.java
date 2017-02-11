package spring.integration.test.mail.imap;

import javax.mail.FetchProfile;
import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Store;
import javax.mail.URLName;
import javax.mail.internet.MimeMessage;

/**
 * 
 * @author dr.ricalde
 * 
 */
public class Mover {

	public void process(MimeMessage message) throws Exception {
		Folder folder = message.getFolder();
		folder.open(Folder.READ_WRITE);
		String messageId = message.getMessageID();
		if (Math.random() > 0.5) {
			setFlagToMessage(folder, messageId, new SetFlags() {
				@Override
				public void setFlag(Message message) throws MessagingException {
					message.setFlag(Flags.Flag.SEEN, true);
				}
			});
			throw new RuntimeException("EXCEPTION THROWN TRYING TO MOVE THE MAIL MESSAGE ...");
		} else {
			setFlagToMessage(folder, messageId, new SetFlags() {
				@Override
				public void setFlag(Message message) throws MessagingException {
					message.setFlag(Flags.Flag.DELETED, true);
				}
			});
			URLName url = new URLName("imaps://dante.ricalde.delgado%40gmail.com:Pescadito1@imap.gmail.com/INBOX");
			Store store = message.getSession().getStore(url);
			store.connect();
			Folder fooFolder = store.getFolder("FOO");
			fooFolder.appendMessages(new MimeMessage[] { message });
			if (fooFolder.isOpen()) {
				fooFolder.close(false);
			}
		}
		folder.expunge();
		folder.close(true);
	}

	private void setFlagToMessage(Folder folder, String messageId, SetFlags setFlags) throws MessagingException {
		Message[] messages = folder.getMessages();
		FetchProfile contentsProfile = new FetchProfile();
		contentsProfile.add(FetchProfile.Item.ENVELOPE);
		contentsProfile.add(FetchProfile.Item.CONTENT_INFO);
		contentsProfile.add(FetchProfile.Item.FLAGS);
		folder.fetch(messages, contentsProfile);
		// find this message and mark for deletion
		for (int i = 0; i < messages.length; i++) {
			if (((MimeMessage) messages[i]).getMessageID().equals(messageId)) {
				setFlags.setFlag(messages[i]);
				break;
			}
		}
	}

	private interface SetFlags {

		void setFlag(Message message) throws MessagingException;
	}

}
