package com.wxsf.fhr.model;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;


public class Effect extends SuperObject
{

    public Effect()
    {
    }

    public void move()
    {
        super.move();
        if(life < 0)
            erase();
        life--;
        size +=type;
    }

    public void draw(Graphics g)
    {
        if(!exist)
            return;
        ////////////////////////////////////////////////////////////×²»÷Ð§¹û»æÖÆ
        ImageIcon imageicon = new ImageIcon(getClass().getResource("/image/fire.png"));
        Image image = imageicon.getImage();
        g.drawImage(image, (int)px - size / 2, (int)py - size / 2, (int)px + size / 2, (int)py + size / 2, 0, 0, 40, 40, null);
    }
}
