package com.wxsf.fhr.main;
import java.awt.*;

import javax.swing.*;
public class Game{
    static JFrame jf;
	public static void main(String[] args){
		jf=new JFrame();
		jf.setTitle("±©×ßµ¯Ä»");
		jf.setDefaultCloseOperation(3);
		jf.setResizable(false);
		jf.setBounds((int)(Toolkit.getDefaultToolkit().getScreenSize().getWidth()-640)/2,(int)(Toolkit.getDefaultToolkit().getScreenSize().getHeight()-640)/2,640,640);
		GamePanel gamepanel = new GamePanel();
		jf.add(gamepanel);
		jf.setVisible(true);
	}
}

