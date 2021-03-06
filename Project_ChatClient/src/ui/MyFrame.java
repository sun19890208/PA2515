package ui;

import java.awt.Graphics;
import java.awt.Image;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.lang.reflect.Constructor;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.UIManager;

//import com.sun.awt.AWTUtilities;

import config.ComponentConfig;
import config.FrameConfig;
import control.Control;

import dto.Dto;

abstract public class MyFrame extends JFrame{
	/**
	 * Frame point.x
	 */
	protected final int x;

	/**
	 * Frame point.y
	 */
	protected final int y;

	/**
	 * Frame width
	 */
	protected final int w;

	/**
	 * Frame height
	 */
	protected final int h;
	
	/**
	 * Frame title
	 */
	protected final String title;
	
	/**
	 * Frame graphic's path
	 */
	protected final String framePath;
	
	/**
	 * frame style
	 */
	protected final int frameStyle;
	
	/**
	 * data transfer object
	 */
	protected Dto dto;
	
	protected List<ComponentConfig> ComponentsCfg;
	
	protected Control control;
	
	protected JPanel panel;
	
	
	/**
	 * Construction of MyFrame
	 */
	protected MyFrame(FrameConfig frameCfg, Control control) {
		super();
		this.title = frameCfg.getTitle(); 
		this.x = frameCfg.getX(); 
		this.y = frameCfg.getY();
		this.w = frameCfg.getW();
		this.h = frameCfg.getH();
		this.framePath = frameCfg.getPath();
		this.frameStyle = frameCfg.getFrameStyle();
		this.ComponentsCfg = frameCfg.getComponentsConfig();
		this.control = control;
		Image image=new ImageIcon("graphic/tray.png").getImage();
		this.setIconImage(image);
//		AWTUtilities.setWindowOpacity(this, 0.8F);
	}


	/**
	 * Create a frame
	 */
	protected void createFrame() {
		this.setTitle(title);
		this.setBounds(x, y, w, h);
		panel = new JPanel(null);
		panel.setOpaque(false);
		//Set Backgroud graphic
		JLabel label = new JLabel(Img.getImgIcon(framePath));
		label.setBounds(0, 0, w, h);
		getLayeredPane().setLayout(null);
		getLayeredPane().add(label, new Integer(Integer.MIN_VALUE));
		// Whether remove the default decorate
//		this.setUndecorated(true);
//		this.getRootPane().setWindowDecorationStyle(frameStyle);
		//init components
		for (ComponentConfig cCfg : ComponentsCfg) {
			//reflect use className get the Class
			Class<?> cls;
			try {
				cls = Class.forName(cCfg.getClassName());
				// Get Constructor
				Constructor<?> ctr = cls.getConstructor();
//=====================================JTabbedPane==============================================
				if(cCfg.getClassName().equals("javax.swing.JTabbedPane"))
					UIManager.put("TabbedPane.contentOpaque", false);
//==============================================================================================
				// Create objec based on consturctor
				final JComponent c = (JComponent) ctr.newInstance();
				final String value = cCfg.getValue();
//===================================== Button =============================================================================
				//If the component is a button add icon and listener
				if(cCfg.getClassName().equals("javax.swing.JButton")){
					//Set Icon of Button
					((JButton)c).setIcon(Img.getImgIcon(cCfg.getPath()));
					//Set ActionListener of Button
					if(!value.equals("")){
						((JButton)c).addActionListener(new ActionListener() {
							@Override
							public void actionPerformed(ActionEvent e) {
								try {
									control.getClientService().getClass().getMethod(value).invoke(control.getClientService());
								} catch (Exception e1) {
									e1.printStackTrace();
								}
							}
						});
					}
				}
//===================================== Label =============================================================================	
				else if(cCfg.getClassName().equals("javax.swing.JLabel")){
					//Set Icon of JLabel
					final String labelPath = cCfg.getPath();
					((JLabel)c).setIcon(Img.getImgIcon(labelPath));
					final String[] str = labelPath.split("/");
					c.setOpaque(false);
					//Set MouseListener of Label
					if(!value.equals("") && !value.equals("tipLabel")){
						((JLabel)c).addMouseListener(new MouseAdapter() {
							public void mouseClicked(MouseEvent e) {
								try {
									control.getClientService().getClass().getMethod(value).invoke(control.getClientService());
								} catch (Exception e1) {
									e1.printStackTrace();
								}
							}
							//Mouse enter or exit, show another picture
							public void mouseEntered(MouseEvent e2){
								((JLabel)c).setIcon(Img.getImgIcon(str[0]+"/c"+str[1]));
							}
							public void mouseExited(MouseEvent e3){
								((JLabel)c).setIcon(Img.getImgIcon(labelPath));
							}
						});
					}
				}
				
//===================================== CheckBox =============================================================================
				else if(cCfg.getClassName().equals("javax.swing.JCheckBox")){
					((JCheckBox)c).setText(cCfg.getPath());
					c.setOpaque(false);
					//Set MouseListener of Label
					((JCheckBox)c).addActionListener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
							try {
								control.getClientService().getClass().getMethod(value).invoke(control.getClientService());
							} catch (Exception e1) {
								e1.printStackTrace();
							}
						}
					});
				}
				
//===================================== JRadioButton =============================================================================
				else if(cCfg.getClassName().equals("javax.swing.JRadioButton")){
					((JRadioButton)c).setText(cCfg.getPath());
					c.setOpaque(false);
				}
//===============================================================================================
				control.getDto().getComponentList().put(cCfg.getValue(), c);
				c.setBounds(cCfg.getX(),cCfg.getY(),cCfg.getW(),cCfg.getH());
				panel.add(c);
				this.setContentPane(panel);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public void setDto(Dto dto) {
		this.dto = dto;
	}
	
	public abstract void paint(Graphics g);
}
