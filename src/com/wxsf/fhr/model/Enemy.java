package com.wxsf.fhr.model;
import java.awt.Graphics;


public class Enemy extends SuperObject
{

    public Enemy()
    {}

    public void setData(double d, double d1, double d2, double d3, int i, int j, int k, int l, char c)
    {
        super.setData(d, d1, d2, d3, i, j, k, l, c);
        c0 = c1 = c2 = c3 = c4 = c5 = 0.0D;
    }

    public void move()
    {
        if(!exist)
            return;

        if(type == 2)											//小黑
        {
            if(frame % 25 == 0)
            {
                th = playerTh();
                if((tmp = p.bullets.getEmpty()) != null)
                    tmp.setData(px, py, Math.cos(th) * 6D, Math.sin(th) * 6D, 20, 0, 0, 0, 'b');
            }
        } else

        if(type == 3)											//噗噗噗
        {
            if(frame % 15 == 0)
            {
                c0 = Math.random() * 30D;
                for(int i = 0; i < 360; i += 60)
                    if((tmp = p.bullets.getEmpty()) != null)
                        tmp.setData(px, py, Math.cos(Math.toRadians((double)i + c0)) * 6D, Math.sin(Math.toRadians((double)i + c0)) * 6D, 20, 0, 0, 0, 'g');
            }
        } else

        if(type == 100)										//大佬逼
        {
            if(life > 1600)
            {
                if(py < 128D)
                    vy = 1.0D;
                else
                    vy = 0.0D;
                if(frame % 30 == 0)
                {
                    c0 = Math.random() * 30D;
                    for(int j2 = 0; j2 < 360; j2 += 15)
                        if((tmp = p.bullets.getEmpty()) != null)
                            tmp.setData(px, py, Math.cos(Math.toRadians((double)j2 + c0)) * 6D, Math.sin(Math.toRadians((double)j2 + c0)) * 6D, 20, 0, 0, 0, 'b');
                }
            } else
            if(life<1600)
            {
            	size=101;
                if(vx == 0.0D)
                    vx = 3D;
                if(vy == 0.0D)
                    vy = 2D;
                if(py <= 10D && vy < 0.0D)
                    vy *= -1D;
                if(py >= 500D && vy > 0.0D)
                    vy *= -1D;
                if(px <= 10D && vx < 0.0D)
                    vx *= -1D;
                if(px >= 630D && vx > 0.0D)
                    vx *= -1D;
                if(frame % 10 == 0)
                {
                    c0 = Math.random() * 30D;
                    for(int j3 = 0; j3 < 360; j3 += 20)
                        if((tmp = p.bullets.getEmpty()) != null)
                            tmp.setData(px, py, Math.cos(Math.toRadians((double)j3 + c0)) * 6D, Math.sin(Math.toRadians((double)j3 + c0)) * 6D, 20, 0, 0, 0, 'b');

                }
            }
        }

        for(int j6 = 0; j6 < p.shoots.getArrayMax(); j6++)			//撞击
        {
            if(!(tmp = p.shoots.getObject(j6)).getExist()|| Math.hypot(px - tmp.getPx(), py - tmp.getPy()) >= (double)((size + tmp.getSize()) / 2))
                continue;
            if(life > 0)
                life--;
            else
            {
                crash();
                break;
            }
            if(tmp.getSize() < 100)
                tmp.eraseWithEffect();
            p.player.plusScore(1);
        }
        super.move();
        }

    public void crash()						////////撞击事件
    {
        p.player.plusScore(type * 10);		//加分
        p.player.plusPower(type);			//攻击力提升
        eraseWithEffect();					//当boss死亡时从屏幕清除
    }


    public void draw(Graphics g)
    {
        super.draw(g);
    }

    public double playerTh()
    {
        return Math.atan2(p.player.getPy() - py, p.player.getPx() - px);
    }

    double c0;
    double c1;
    double c2;
    double c3;
    double c4;
    double c5;
}
