package org.productivity.java.syslog4j.impl;

import java.util.ArrayList;
import java.util.List;

import org.productivity.java.syslog4j.SyslogBackLogHandlerIF;
import org.productivity.java.syslog4j.SyslogConfigIF;
import org.productivity.java.syslog4j.SyslogIF;
import org.productivity.java.syslog4j.SyslogMessageIF;
import org.productivity.java.syslog4j.SyslogMessageModifierIF;
import org.productivity.java.syslog4j.SyslogMessageProcessorIF;
import org.productivity.java.syslog4j.SyslogRuntimeException;
import org.productivity.java.syslog4j.impl.message.processor.SyslogMessageProcessor;
import org.productivity.java.syslog4j.impl.message.processor.structured.StructuredSyslogMessageProcessor;
import org.productivity.java.syslog4j.impl.message.structured.StructuredSyslogMessage;
import org.productivity.java.syslog4j.impl.message.structured.StructuredSyslogMessageIF;
import org.productivity.java.syslog4j.util.SyslogUtility;

/**
* AbstractSyslog provides a base abstract implementation of the SyslogIF.
* 
* <p>Syslog4j is licensed under the Lesser GNU Public License v2.1.  A copy
* of the LGPL license is available in the META-INF folder in all
* distributions of Syslog4j and in the base directory of the "doc" ZIP.</p>
* 
* @author &lt;syslog4j@productivity.org&gt;
* @version $Id: AbstractSyslog.java,v 1.29 2011/01/11 04:58:52 cvs Exp $
*/
public abstract class AbstractSyslog implements SyslogIF {
	private static final long serialVersionUID = 2632017043774808264L;

	protected String syslogProtocol = null;
	
	protected AbstractSyslogConfigIF syslogConfig = null;
	
	protected SyslogMessageProcessorIF syslogMessageProcessor = null;
	protected SyslogMessageProcessorIF structuredSyslogMessageProcessor = null;
	
	protected Object backLogStatusSyncObject = new Object();
	
	protected boolean backLogStatus = false;
	protected List notifiedBackLogHandlers = new ArrayList();
	
	protected boolean getBackLogStatus() {
		synchronized(this.backLogStatusSyncObject) {
			return this.backLogStatus;
		}
	}

	/**
	 * @param backLogStatus - true if in a "down" backLog state, false if in an "up" (operational) non-backLog state
	 */
	public void setBackLogStatus(boolean backLogStatus) {
		if (this.backLogStatus != backLogStatus) {
			synchronized(this.backLogStatusSyncObject) {
				if (!backLogStatus) {
					for(int i=0; i<this.notifiedBackLogHandlers.size(); i++) {
						SyslogBackLogHandlerIF backLogHandler = (SyslogBackLogHandlerIF) this.notifiedBackLogHandlers.get(i);
							
						backLogHandler.up(this);
					}
					
					this.notifiedBackLogHandlers.clear();
				}
				
				this.backLogStatus = backLogStatus;
			}
		}
	}

	public void initialize(String protocol, SyslogConfigIF config) throws SyslogRuntimeException {
		this.syslogProtocol = protocol;

		try {
			this.syslogConfig = (AbstractSyslogConfigIF) config;
			
		} catch (ClassCastException cce) {
			throw new SyslogRuntimeException("provided config must implement AbstractSyslogConfigIF");
		}
		
		initialize();
	}
	
	public SyslogMessageProcessorIF getMessageProcessor() {
		if (this.syslogMessageProcessor == null) {
			this.syslogMessageProcessor = SyslogMessageProcessor.getDefault();
		}
		
		return this.syslogMessageProcessor;
	}

	public SyslogMessageProcessorIF getStructuredMessageProcessor() {
		if (this.structuredSyslogMessageProcessor == null) {
			this.structuredSyslogMessageProcessor = StructuredSyslogMessageProcessor.getDefault();
		}
		
		return this.structuredSyslogMessageProcessor;
	}

	public void setMessageProcessor(SyslogMessageProcessorIF messageProcessor) {
		this.syslogMessageProcessor = messageProcessor;
	}

	public void setStructuredMessageProcessor(SyslogMessageProcessorIF messageProcessor) {
		this.structuredSyslogMessageProcessor = messageProcessor;
	}

	public String getProtocol() {
		return this.syslogProtocol;
	}
	
	public SyslogConfigIF getConfig() {
		return this.syslogConfig;
	}

	public void log(int level, String message) {
		if (this.syslogConfig.isUseStructuredData()) {
			StructuredSyslogMessageIF structuredMessage = new StructuredSyslogMessage(null,null,message);
			
			log(getStructuredMessageProcessor(),level,structuredMessage.createMessage());
			
		} else {
			log(getMessageProcessor(),level,message);
		}
	}

	public void log(int level, SyslogMessageIF message) {
		if (message instanceof StructuredSyslogMessageIF) {
			if (getMessageProcessor() instanceof StructuredSyslogMessageProcessor) {
				log(getMessageProcessor(),level,message.createMessage());
				
			} else {
				log(getStructuredMessageProcessor(),level,message.createMessage());
			}
			
		} else {
			log(getMessageProcessor(),level,message.createMessage());
		}
	}

	public void debug(String message) {
		log(LEVEL_DEBUG,message);
	}

	public void notice(String message) {
		log(LEVEL_NOTICE,message);
	}

	public void info(String message) {
		log(LEVEL_INFO,message);
	}

	public void warn(String message) {
		log(LEVEL_WARN,message);
	}
	
	public void error(String message) {
		log(LEVEL_ERROR,message);
	}

	public void critical(String message) {
		log(LEVEL_CRITICAL,message);
	}

	public void alert(String message) {
		log(LEVEL_ALERT,message);
	}

	public void emergency(String message) {
		log(LEVEL_EMERGENCY,message);
	}
	
	public void debug(SyslogMessageIF message) {
		log(LEVEL_DEBUG,message);
	}

	public void notice(SyslogMessageIF message) {
		log(LEVEL_NOTICE,message);
	}

	public void info(SyslogMessageIF message) {
		log(LEVEL_INFO,message);
	}

	public void warn(SyslogMessageIF message) {
		log(LEVEL_WARN,message);
	}
	
	public void error(SyslogMessageIF message) {
		log(LEVEL_ERROR,message);
	}

	public void critical(SyslogMessageIF message) {
		log(LEVEL_CRITICAL,message);
	}

	public void alert(SyslogMessageIF message) {
		log(LEVEL_ALERT,message);
	}

	public void emergency(SyslogMessageIF message) {
		log(LEVEL_EMERGENCY,message);
	}
	
	protected String prefixMessage(String message, String suffix) {
		String ident = this.syslogConfig.getIdent();
		
		String _message = ((ident == null || "".equals(ident.trim())) ? "" : (ident + suffix)) + message;
		
		return _message;
	}
	
	public void log(SyslogMessageProcessorIF messageProcessor, int level, String message) {
		String _message = null;
		
		if (this.syslogConfig.isIncludeIdentInMessageModifier()) {
			_message = prefixMessage(message,IDENT_SUFFIX_DEFAULT);
			_message = modifyMessage(level,_message);
			
		} else {
			_message = modifyMessage(level,message);
			_message = prefixMessage(_message,IDENT_SUFFIX_DEFAULT);
		}
		
		try {
			write(messageProcessor, level,_message);
			
		} catch (SyslogRuntimeException sre) {
			if (sre.getCause() != null) {
				backLog(level,_message,sre.getCause());
				
			} else {
				backLog(level,_message,sre);
			}
			
			if (this.syslogConfig.isThrowExceptionOnWrite()) {
				throw sre;
			}
		}
	}

	protected void write(SyslogMessageProcessorIF messageProcessor, int level, String message) throws SyslogRuntimeException {
		String header = messageProcessor.createSyslogHeader(this.syslogConfig.getFacility(),level,this.syslogConfig.getLocalName(),this.syslogConfig.isSendLocalTimestamp(),this.syslogConfig.isSendLocalName());

		byte[] h = SyslogUtility.getBytes(this.syslogConfig,header);
		byte[] m = SyslogUtility.getBytes(this.syslogConfig,message);
		
		int mLength = m.length;

		int availableLen = this.syslogConfig.getMaxMessageLength() - h.length;

		if (this.syslogConfig.isTruncateMessage()) {
			if (availableLen > 0 && mLength > availableLen) {
				mLength = availableLen;
			}
		}

		if (mLength <= availableLen) {
			byte[] data = messageProcessor.createPacketData(h,m,0,mLength);
			
			write(level,data);
			
		} else {
			byte[] splitBeginText = this.syslogConfig.getSplitMessageBeginText();
			byte[] splitEndText = this.syslogConfig.getSplitMessageEndText();
		
			int pos = 0;
			int left = mLength;
			
			while(left > 0) {
				boolean firstTime = (pos == 0);
				
				boolean doSplitBeginText = splitBeginText != null && !firstTime;
				boolean doSplitEndText = splitBeginText != null && (firstTime || (left > (availableLen - splitBeginText.length)));
				
				int actualAvailableLen = availableLen;
				
				actualAvailableLen -= (splitBeginText != null && doSplitBeginText) ? splitBeginText.length : 0;
				actualAvailableLen -= (splitEndText != null && doSplitEndText) ? splitEndText.length : 0;
				
				if (actualAvailableLen > left) {
					actualAvailableLen = left;
				}
				
				if (actualAvailableLen < 0) {
					throw new SyslogRuntimeException("Message length < 0; recommendation: increase the size of maxMessageLength");
				}
				
				byte[] data = messageProcessor.createPacketData(h,m,pos,actualAvailableLen,doSplitBeginText ? splitBeginText : null,doSplitEndText ? splitEndText : null);
					
				write(level,data);
				
				pos += actualAvailableLen;
				left -= actualAvailableLen;
			}
		}
	}

	protected abstract void initialize() throws SyslogRuntimeException;
	
	protected abstract void write(int level, byte[] message) throws SyslogRuntimeException;
	
	protected String modifyMessage(int level, String message) {
		List _messageModifiers = this.syslogConfig.getMessageModifiers();
		
		if (_messageModifiers == null || _messageModifiers.size() < 1) {
			return message;
		}
		
		String _message = message;
		
		int facility = this.syslogConfig.getFacility();
		
		for(int i=0; i<_messageModifiers.size(); i++) {
			SyslogMessageModifierIF messageModifier = (SyslogMessageModifierIF) _messageModifiers.get(i);
			
			_message = messageModifier.modify(this, facility, level, _message);
		}
		
		return _message;
	}
	
	public void backLog(int level, String message, Throwable reasonThrowable) {
		backLog(level,message,reasonThrowable != null ? reasonThrowable.toString() : "UNKNOWN");
	}
	
	public void backLog(int level, String message, String reason) {
		boolean status = getBackLogStatus();
		
		if (!status) {
			setBackLogStatus(true);
		}
		
		List backLogHandlers = this.syslogConfig.getBackLogHandlers();
		
		for(int i=0; i<backLogHandlers.size(); i++) {
			SyslogBackLogHandlerIF backLogHandler = (SyslogBackLogHandlerIF) backLogHandlers.get(i);
			
			try {
				if (!status) {
					backLogHandler.down(this, reason);
					this.notifiedBackLogHandlers.add(backLogHandler);
				}
				
				backLogHandler.log(this,level,message,reason);
				break;
				
			} catch (Exception e) {
				// Ignore this Exception and go onto next backLogHandler
			}
		}
	}
	
	public abstract AbstractSyslogWriter getWriter();
	
	public abstract void returnWriter(AbstractSyslogWriter syslogWriter);
	
	public Thread createWriterThread(AbstractSyslogWriter syslogWriter) {
		Thread newWriterThread = new Thread(syslogWriter);
		newWriterThread.setName("SyslogWriter: " + getProtocol());
		newWriterThread.setDaemon(syslogConfig.isUseDaemonThread());
		if (syslogConfig.getThreadPriority() > -1) {
			newWriterThread.setPriority(syslogConfig.getThreadPriority());
		}
		syslogWriter.setThread(newWriterThread);
		newWriterThread.start();
		
		return newWriterThread;
	}
	

	public AbstractSyslogWriter createWriter(){
		Class clazz = this.syslogConfig.getSyslogWriterClass();
		
		AbstractSyslogWriter newWriter = null;
		
		try {
			newWriter = (AbstractSyslogWriter) clazz.newInstance();
			newWriter.initialize(this);
			
		} catch (InstantiationException ie) {
			if (this.syslogConfig.isThrowExceptionOnInitialize()) {
				throw new SyslogRuntimeException(ie);
			}
			
		} catch (IllegalAccessException iae) {
			if (this.syslogConfig.isThrowExceptionOnInitialize()) {
				throw new SyslogRuntimeException(iae);
			}
		}

		return newWriter;
	}
}
