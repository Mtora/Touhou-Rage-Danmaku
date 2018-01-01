package com.wxsf.fhr.model;
import java.awt.Graphics;

import com.wxsf.fhr.main.GamePanel;

public class SuperObject
{

    public SuperObject()
    {
        px = py = vx = vy = 0.0;
        frame = size = type = life = 0;
        color = 'r';
        th = 0.0D;
        exist = false;
        anime = 0;
    }

    public static void gameObjectInit(GamePanel gamepanel)
    {
        p = gamepanel;
    }

    public void setData(double d, double d1, double d2, double d3, int i, int j, int k, int l, char c)
    {
        px = d;
        py = d1;
        vx = d2;
        vy = d3;
        size = i;
        frame = j;
        type = k;
        life = l;
        color = c;
        exist = true;
    }//th = Math.toRadians(-9D);

    public double setTh(double d, double d1)
    {
        if(d1 == 0.0D)
            th = Math.toRadians(90D);
        else
            th = Math.atan(d / d1);
        return th;
    }

    public double setTh(double d)
    {
        return th = d;
    }

    public void move()
    {
        px += vx;
        py += vy;
        frame++;					//每次移动是frame+1
        if(px < 0.0D || py < 0.0D || px > 640D || py > 640D)
            exist = false;
    }

    public void draw(Graphics g)
    {
        if(!exist)
            return;
        int c = 0;
 //       boolean flag = false;
        if(color == 'g')						//绿弹弹
            c = 65;
        if(color == 'b')						//蓝弹弹
            c = 128;
        if(color == 'c')					//笨蛋
            c = 194;
        if(color == 'w')					//巫女
            c = 224;
        if(color == 'h')					//小黑
            c = 256;
        if(color == 'p')					//噗噗
            c = 320;
        if(color == 'x')
        	c=10;
/////////////////////////////////////TODO
        if(size <= 32)							//小boss
        {
            int i = ((size / 4 - 1)) * 32;
            g.drawImage(p.image, (int)px - 16, (int)py - 16, (int)px + 16, (int)py + 16, c, i, c + 32, i + 32, null);
        }

       ////////////////////////////////////////////////////////大boss
        	if(size==100){										//普通状态
        		g.drawImage(p.image, (int)px - 32, (int)py - 32, (int)px + 32, (int)py + 32, 195, 449, 255, 510, null);
        	}
        	else if(size>100&&size<200)									//狂怒状态
        		g.drawImage(p.image, (int)px - 32, (int)py - 32, (int)px + 32, (int)py + 32, 259, 449, 319, 510, null);
        }

    public void erase()
    {
        exist = false;
    }

    public void eraseWithEffect()
    {
        if(!exist)
            return;
        if((tmp2 = p.effects.getEmpty()) != null)
            tmp2.setData(px, py, 0.0D, 0.0D, size / 2, 0, size / 4, 5, color);
        erase();
    }

    public double getPx()
    {
        return px;
    }

    public double getPy()
    {
        return py;
    }

    public double getVx()
    {
        return vx;
    }

    public double getVy()
    {
        return vy;
    }

    public double getTh()
    {
        return th;
    }

    public int getSize()
    {
        return size;
    }

    public int getFrame()
    {
        return frame;
    }

    public int getType()
    {
        return type;
    }

    public int getLife()
    {
        return life;
    }

    public char getColor()
    {
        return color;
    }

    public boolean getExist()
    {
        return exist;
    }

    protected static GamePanel p;
    protected double px;
    protected double py;
    protected double vx;
    protected double vy;
    protected double th;
    protected int size;
    protected int frame;
    protected int type;
    protected int life;
    protected int anime;
    protected char color;
    protected boolean exist;
    protected SuperObject tmp;
    protected SuperObject tmp2;
}
