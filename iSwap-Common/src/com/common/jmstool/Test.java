package com.common.jmstool;



public class Test {
	public static void main(String[] rga){
		JMSAttr attr = new JMSAttr();
		attr.setInitFactory("com.sun.jndi.fscontext.RefFSContextFactory");
		attr.setUrl("file:/C:/JNDI-Directory");
		attr.setQueFactory("ISWAP");
		attr.setSqueName("test2");
		attr.setRqueName("test2");
		JMSTool tool = new JMSTool();
		JMSConntect conn= tool.createSendConnect(attr);
		for(int i=0;i<100;i++){
			//tool.sendAsyn(conn, "dsadsadsadsadsadsadsadsadsadsadsadsds");
			//System.out.println(tool.receiver(conn, 500));
		}
		try {
			System.out.println(tool.receiver(conn, 500));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		conn.close();
		
//		
////		tool.sendAsyn("ddddddd");
////		tool.receiver("test1");
//		tool.receiver("test2");
////		
////		
//		Properties cProperties = new Properties();
//		cProperties.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.fscontext.RefFSContextFactory");
//		cProperties.put(Context.PROVIDER_URL, "file:/C:/JNDI-Directory");
//		InitialContext ctx;
//		try {
//			ctx = new InitialContext(cProperties);
//
//			Queue queue = (Queue) ctx.lookup("test2");
//
//			QueueConnectionFactory qcf = (QueueConnectionFactory) ctx.lookup("ISWAP");
//
//			QueueConnection qc = qcf.createQueueConnection();
//			
//			QueueSession qs = qc.createQueueSession(false,Session.AUTO_ACKNOWLEDGE);
//
//			QueueSender sender = qs.createSender(queue);
//			
////			for(int i=0;i<1000;i++){
////				Message message =  qs.createTextMessage("test2:"+i);
////				sender.send(message);
////				System.out.println(i);
////			}
//			
//
//		} catch (NamingException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (JMSException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}

	}
	

}
