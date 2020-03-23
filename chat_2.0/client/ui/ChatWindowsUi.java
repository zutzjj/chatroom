package client.ui;

import java.util.Map;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import client.ClientMethod;
import client.DataBuffer;
import common.Request;
import common.RequestHead;

/*
 * Client的UI类 聊天界面
 * 2020.3.23 zjj_zut
 * 
 */

public class ChatWindowsUi extends Thread{
	private String id;
	private Shell parentshell;

	public ChatWindowsUi(Shell shell, String id) {
		super();
		// TODO Auto-generated constructor stub
		this.id = id;
		this.parentshell = shell;

	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		super.run();
		
        //boolean buffer = false;
		ClientMethod cm = new ClientMethod();
		Shell shell = new Shell(parentshell);
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
						String message = DataBuffer.username+":"+edit_text.getText()+"\n";
						edit_text.setText("");
						show_text.append(message);
						Request request = new Request();
						request.setRequesthead(RequestHead.SEND_REQUEST);
						request.setRequestbody("id", id);
						String uid = Long.toString(DataBuffer.userid);
						request.setRequestbody("uid", uid);
						request.setRequestbody("message", message);
						cm.saveLocalMessage(id, message);
						cm.sendRequest(request);
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
        //打开聊天窗口先进行加载本地消息，然后清空缓存消息
        for (Map.Entry<String, String> entry : DataBuffer.localmessage.getMessagemap().entrySet()) {
			
		      //System.out.println(id+entry2.getKey() + ":" + entry2.getValue());
		      if(id.equals(entry.getKey()) && entry.getValue()!=null)
		      {
		    	  show_text.setText(entry.getValue());
		    	  DataBuffer.message.cleanCache(id);
		    	  
		    	  break;
		      }
        }
        
		      
		shell.open();
		
		
		
		
        while(!shell.isDisposed()){ 
        	if(!DataBuffer.display.readAndDispatch())
        		DataBuffer.display.sleep(); 
        	
        			for (Map.Entry<String, String> entry2 : DataBuffer.message.getMessagemap().entrySet()) {
            			
      			      //System.out.println(id+entry2.getKey() + ":" + entry2.getValue());
      			      if(id.equals(entry2.getKey()) && entry2.getValue()!=null)
      			      {
      			    	  show_text.append(entry2.getValue());
      			    	  entry2.setValue(null);
      			    	  
      			    	  break;
      			      }
      			
      				        	
      			
      			}
        			
        		
        			
        } 		
	}
}