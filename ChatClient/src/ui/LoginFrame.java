package ui;

import java.awt.Graphics;

import javax.swing.JFrame;

import config.FrameConfig;
import control.Control;

public class LoginFrame extends MyFrame{
	public LoginFrame(FrameConfig fCfg, final Control control) {
		super(fCfg,control);
		//Create Frame
		this.createFrame();
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		//When DEBUG login frame using code below
//		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
//		this.addWindowListener(new WindowAdapter() {
//			public void windowClosing(WindowEvent e) {
//				control.getClientService().logout();
//				System.exit(0);
//			}
//		});
	}
	@Override
	public void paint(Graphics g) {
		paintComponents(g);
	}
}
