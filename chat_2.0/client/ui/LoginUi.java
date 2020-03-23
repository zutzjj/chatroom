package client.ui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.VerifyEvent;
import org.eclipse.swt.events.VerifyListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import client.ClientMethod;
import client.DataBuffer;
import common.Request;
import common.RequestHead;

/*
 * Client的UI类 登录界面
 * 2020.3.23 zjj_zut
 * 
 */
public class LoginUi {
	

	public LoginUi() {
		super();
		
		init();
	}
	public void init()
	{
		ClientMethod cm =new ClientMethod();
		
		Shell shell = new Shell(DataBuffer.display,SWT.MIN);
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
        id_login_Text.setText("10001");
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
        
        id_login_Text.addVerifyListener(new VerifyListener()
        		{

					@Override
					public void verifyText(VerifyEvent e) {
						// TODO Auto-generated method stub
						boolean b = ("0123456789".indexOf(e.text)>=0);     
			              e.doit = b; 
					}
        	
        		});

        register_Button.addMouseListener(new MouseAdapter()
    		   {

				@Override
				public void mouseDown(MouseEvent e) {
					// TODO Auto-generated method stub
					super.mouseDown(e);
					RegisterUi rui = new RegisterUi();
				}
    	   
    		   });
        login_Button.addMouseListener(new MouseAdapter()
        		{
        	@Override
					public void mouseDown(MouseEvent e) {
						// TODO Auto-generated method stub
						super.mouseDown(e);
						String id = id_login_Text.getText();
						Request request = new Request();
						request.setRequesthead(RequestHead.LOGIN_REQUEST);
						request.setRequestbody("id", Long.parseLong(id));
						request.setRequestbody("paw", paw_login_Text.getText());
						cm.sendRequest(request);
					}
        		});
	    shell.open();
	    
	    
        while(!shell.isDisposed()){ 
        	
        	if(!DataBuffer.display.readAndDispatch())
        		DataBuffer.display.sleep();
        	//判断是否登录成功并是否接受到所有用户信息
        	if(DataBuffer.ct.id_error)
        	{
        		MessageBox box = new MessageBox(shell,SWT.OK|SWT.ICON_WARNING);
        		box.setMessage("账号错误");
        		DataBuffer.ct.id_error = false;
        		box.open();
        	}
        	if(DataBuffer.ct.paw_error)
        	{
        		MessageBox box = new MessageBox(shell,SWT.OK|SWT.ICON_WARNING);
        		box.setMessage("密码错误");
        		DataBuffer.ct.paw_error = false;
        		box.open();
        	}
        	if(DataBuffer.ct.login_clash)
        	{
        		MessageBox box = new MessageBox(shell,SWT.OK|SWT.ICON_WARNING);
        		box.setMessage("登录冲突");
        		DataBuffer.ct.login_clash = false;
        		box.open();
        	}
        		
        	if(DataBuffer.ct.success_login&&DataBuffer.ct.receive_all_user)
        	{
        		shell.dispose();
        		ChatUserListUi cului = new ChatUserListUi();
        		
        	}
        	if(DataBuffer.exit)
        	{
        		cm.closeThread();
        	}
        		
        	}
        //判断界面关闭时是否成功登录
        if(!DataBuffer.ct.success_login)
        {
        	cm.closeThread();
        }
        
           
	}

}