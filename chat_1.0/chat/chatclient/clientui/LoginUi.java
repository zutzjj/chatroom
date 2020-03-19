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
 * Client的UI类 登录界面
 * 2020.3.17 zjj_zut
 * 
 */
public class LoginUi {
	private Socket s;
	private Display display;

	public LoginUi(Socket s,Display display) {
		super();
		this.s = s;
		this.display = display;
		init(display);
	}
	public void init(Display display)
	{
		ClientMethod cm =new ClientMethod(s);
		
		Shell shell = new Shell(display,SWT.MIN);
		shell.setSize(535,400);
		FormLayout layout = new FormLayout();
        shell.setLayout(layout);
        
        
        
        Button login_Button = new Button(shell,SWT.PUSH);
        login_Button.setText("登录");
        FormData formData1 = new FormData();
        formData1.right = new FormAttachment(100,-120);
        formData1.left = new FormAttachment(0,120);
        formData1.bottom = new FormAttachment(100,-60);
        formData1.top = new FormAttachment(0,250);
        login_Button.setLayoutData(formData1);
        
       
        Text paw_login_Text = new Text(shell,SWT.BORDER);
        paw_login_Text.setText("paw");
        FormData formData2 = new FormData();
        formData2.bottom = new FormAttachment(login_Button,-35);
        formData2.top = new FormAttachment(login_Button,-120);
        formData2.left = new FormAttachment(0,120);
        formData2.right = new FormAttachment(100,-120);
        paw_login_Text.setLayoutData(formData2);
        
        Text id_login_Text = new Text(shell,SWT.BORDER);
        id_login_Text.setText("your_id");
        FormData formData3 = new FormData();
        formData3.top = new FormAttachment(paw_login_Text,-85);
        formData3.bottom = new FormAttachment(paw_login_Text,-25);
        formData3.left = new FormAttachment(0,120);
        formData3.right = new FormAttachment(100,-120);
        id_login_Text.setLayoutData(formData3);
        Color color = new Color(null,255,0,0);
        id_login_Text.setForeground(color);    
        
        Button register_Button = new Button(shell, SWT.PUSH);
        register_Button.setText("注册账号");
        FormData formData4 = new FormData();
        formData4.left = new FormAttachment(0,5);
        formData4.right = new FormAttachment(0,80);
        formData4.top = new FormAttachment(100,-45);
        formData4.bottom = new FormAttachment(100,-15);
        register_Button.setLayoutData(formData4);
        register_Button.setSelection(true);
        
        register_Button.addMouseListener(new MouseAdapter()
    		   {

				@Override
				public void mouseDown(MouseEvent e) {
					// TODO Auto-generated method stub
					super.mouseDown(e);
					RegisterUi rui = new RegisterUi(s,display);
				}
    	   
    		   });
        login_Button.addMouseListener(new MouseAdapter()
        		{
        	@Override
					public void mouseDown(MouseEvent e) {
						// TODO Auto-generated method stub
						super.mouseDown(e);
						String login_message = "login//"+id_login_Text.getText()+"//"+paw_login_Text.getText();
						cm.sentMessage(login_message);
					}
        		});
	    shell.open();
	    
	    
        while(!shell.isDisposed()){ 
        	
        	if(!display.readAndDispatch())
        	display.sleep();
        	//判断是否登录成功并是否接受到所有用户信息
        	if(ClientMain.ct.isSuccess_login()&&ClientMain.ct.isReceive_all_user())
        	{
        		shell.dispose();
        		ChatUserListUi cului = new ChatUserListUi(s,display);
        		
        	}
        	}
        //判断界面关闭时是否成功登录
        if(!ClientMain.ct.isSuccess_login())
        {
        	cm.closeThread();
        }
        
           
	}

}
