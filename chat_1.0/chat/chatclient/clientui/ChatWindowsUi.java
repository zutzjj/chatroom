package chatclient.clientui;

import java.net.Socket;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import chatclient.ClientMain;
import chatclient.ClientMethod;


/*
 * Client的UI类 聊天界面UI 多线程
 * 2020.3.17 zjj_zut
 * 
 */
public class ChatWindowsUi extends Thread{
	private Socket s;
	private Display display;
	private String uid;
	//private String local_message;
	public ChatWindowsUi(Socket s, Display display, String uid) {
		super();
		this.s = s;
		this.display = display;
		this.uid = uid;
	}

	public void run()
	{
		
		ClientMethod cm = new ClientMethod(s);
		Shell shell = new Shell(display);
		shell.setSize(780,620);
		FormLayout layout = new FormLayout();
        shell.setLayout(layout);
        
        Button cancelButton = new Button(shell,SWT.PUSH);
        cancelButton.setText("关闭");
        FormData formData1 = new FormData();
        formData1.right = new FormAttachment(100,-10); 
        formData1.bottom = new FormAttachment(100,-5); 
        cancelButton.setLayoutData(formData1);
        
        Button send_message_Button = new Button(shell,SWT.PUSH);
        send_message_Button.setText("发送");
        FormData formData2 = new FormData();
        formData2.right = new FormAttachment(100,-80);
        formData2.bottom = new FormAttachment(100,-5);
        send_message_Button.setLayoutData(formData2);
        
        Text edit_text = new Text(shell,SWT.MULTI | SWT.BORDER | SWT.V_SCROLL | SWT.H_SCROLL);
        FormData formData3 = new FormData();
        formData3.top = new FormAttachment(100,-200);
        formData3.bottom = new FormAttachment(cancelButton,-5);
        formData3.left = new FormAttachment(0,5);
        formData3.right = new FormAttachment(100,-5);
        edit_text.setLayoutData(formData3);
        Color color = new Color(null,255,0,0);
        edit_text.setForeground(color);    
        
        Text show_text = new Text(shell,SWT.MULTI | SWT.BORDER | SWT.V_SCROLL | SWT.H_SCROLL);
        FormData formData4 = new FormData();
        formData4.top = new FormAttachment(0,10);
        formData4.bottom = new FormAttachment(edit_text,-20);
        formData4.left = new FormAttachment(0,5);
        formData4.right = new FormAttachment(100,-5);
        show_text.setLayoutData(formData4);
        show_text.setEditable(false);
        show_text.setForeground(color); 
        
       
       
        send_message_Button.addMouseListener(new MouseAdapter()
        		{

					@Override
					public void mouseDown(MouseEvent e) {
						// TODO Auto-generated method stub
						super.mouseDown(e);
						String message = edit_text.getText();
						edit_text.setText("");
						show_text.append(ClientMain.username+":"+message+"\n");
						//local_message += ClientMain.username+":"+message+"\n";
						cm.sentMessage("send//"+uid+"//"+message);
				}
        		});
        cancelButton.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseDown(MouseEvent e) {
				// TODO Auto-generated method stub
				super.mouseDown(e);
				shell.dispose();
			}
        }
        		);
        
		shell.open();
		
		for(int i = 0; i<ClientMain.ct.getLocal_receive_message().size()-1;i++)
		{
			if(ClientMain.ct.getLocal_receive_message().get(i).getUid().equals(uid))
			{
				if(ClientMain.ct.getLocal_receive_message().get(i).getMessage()!="")
				{
					String show_message = ClientMain.ct.getLocal_receive_message().get(i).getMessage();
					//local_message = show_message;
    			    show_text.setText(show_message);  			    
				}
				
			}
				
		}
		
		
        while(!shell.isDisposed()){ 
        	if(!display.readAndDispatch())
        	display.sleep(); 
        	//如果有收到新消息就刷新show_text
        		if(ClientMain.ct.is_receive())	    
        		{		        				        		
        			for(int i = 0; i<ClientMain.ct.getReceive_message().size();i++)			        		
        			{		        		
        				if(ClientMain.ct.getReceive_message().get(i).getUid().equals(uid))				        		
        				{				        			
        					if(ClientMain.ct.getReceive_message().get(i).getMessage()!="")			        		
        					{				        				
        						String show_message = ClientMain.ct.getReceive_message().get(i).getMessage();				        				
        						String message = ClientMain.ct.getReceive_message().get(i).getUname()+":"+show_message+"\n";				            							        				
        						show_text.append(message);				            			
        						ClientMain.ct.getReceive_message().get(i).setMessage("");			    			
        					}			         			
        				}			        	
        			}				        		    		      		
        			ClientMain.ct.setIs_receive(false);		        	
        		}					
        } 		
	}
}
