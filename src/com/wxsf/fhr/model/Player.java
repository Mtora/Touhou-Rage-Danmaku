package com.wxsf.fhr.model;

import java.awt.Graphics;

import com.wxsf.fhr.control.GetKeys;


public class Player extends SuperObject
{

    public Player()
    {
        hiscore = 0;
    }

    public void setData(double d, double d1, double d2, double d3, int i, int j, int k, int l, char c)
    {
        super.setData(d, d1, d2, d3, i, j, k, l, c);
        shotInterval = 0;
        power = 100;
        guard = 0;
        score = 0;
    }

    public void move(GetKeys getkeys)
    {
        if(!exist)
            return;
        if(vx < 0.0D)
            anime = 0;
        else
        if(vx > 0.0D)
            anime = 2;
        else
            anime = 1;
        if(shotInterval < 1)
        {
            if(getkeys.z)
            {
                shotInterval = 1;
                for(int k = -8; k <= 8; k += 16)
                    if((tmp = p.shoots.getEmpty()) != null)
                        tmp.setData(px + (double)k, py, 0.0D, -24D, 16, 0, 0, 0, color);  //发射子弹

                if(color == 'w')							//巫女
                {
                    int i = 21;
                    if(power < 400)
                        i = 15;
                    if(power < 300)
                        i = 9;
                    if(power < 200)
                        i = 3;
                    if(power < 100)
                        i = 0;
                    byte byte0;							//按住shift，聚集子弹
                    if(getkeys.shift)				
                    {
                        byte0 = 6;
                    } else
                    {
                        i *= 2;
                        byte0 = 12;
                    }
                    for(int l = 90 - i; l <= 90 + i; l += byte0)
                        if((tmp = p.shoots.getEmpty()) != null){
                            tmp.setData(px, py, -Math.cos(Math.toRadians(l)) * 24D, -Math.sin(Math.toRadians(l)) * 24D, 20, 0, 0, 0, color);
                            p.Ashoot[p.shootNum].play();
                            }
                } else
                if(color == 'c')							//琪露诺
                {
                	
                    int j = 12;
                    if(power < 400)
                        j = 9;
                    if(power < 300)
                        j = 6;
                    if(power < 200)
                        j = 3;
                    if(power < 100)
                        j = 0;
                    byte byte1;							//按住shift，聚集子弹
                    if(getkeys.shift)				
                    {
                        byte1 = 6;
                    } else
                    {
                        j *= 4;
                        byte1 = 24;
                    }
                    for(int i1 = -j; i1 <= j; i1 += byte1)
                        if((tmp = p.shoots.getEmpty()) != null){
                            tmp.setData(px + (double)i1, py, 0.0D, -18D, 20, 0, 0, 0, color);
                            p.Ashoot1[p.shootNum].play();
                        }
                }
            }
        } 
        else
        {
            shotInterval--;
        }
        if(guard == 0 && getkeys.x && power >= 100)
        {
        	p.Ax.play();
            power -= 100;
            guard = -30;
        }
        if(guard > 0)
        {
            guard--;
        } else
        if(guard < 0)
        {
            if(guard < -4)
            {
                for(int l1 = 0; l1 < 20; l1++)
                    if((tmp2 = p.effects.getEmpty()) != null)
                        tmp2.setData(Math.random() * 640D, Math.random() * 640D, 0.0D, 0.0D, 101, 0, 8, 4, 'c');
            }
            if((tmp = p.shoots.getEmpty()) != null)						//小怪消除
            	tmp.setData(0, 0, 9999, 9999, 9999, 0, 0, 0, ' ');	//移999像素出边界，宏判定对象消除
            	
            for(int i2 = 0; i2 < p.bullets.getArrayMax(); i2++)
                if((tmp = p.bullets.getObject(i2)).getExist())
                {
                    score += 2;
                    tmp.eraseWithEffect();
                }
            guard++;
        } else
        {
            int j2 = 0;
            do
            {
                if(j2 >= p.bullets.getArrayMax())
                    break;
                if((tmp = p.bullets.getObject(j2)).getExist() && (double)((size + tmp.getSize()) / 2) > Math.hypot(px - tmp.getPx(), py - tmp.getPy()))/////玩家被击中事件
                {
                	//TODO
                	p.Acrash.play();												//玩家被击中音效
                	
                    for(int k2 = 15; k2 < 375; k2 += 30)			//玩家被击中效果
                        if((tmp2 = p.effects.getEmpty()) != null)
                            tmp2.setData(px, py, Math.cos(Math.toRadians(k2)) * 16D, Math.sin(Math.toRadians(k2)) * 16D, 0, 0, 4, 10, 'y');

                    tmp.eraseWithEffect();
                    guard = 30;
                    life--;
                    power = (power + 499) / 2;
                    if(life < 0)
                    {
                        life = 0;
                        eraseWithEffect();
                    }
                    break;
                }
                j2++;
            } while(true);
        }
        vx = 0.0D;
        vy = 0.0D;
        if(getkeys.up)
            vy -= 8D;
        if(getkeys.down)
            vy += 8D;
        if(getkeys.left)
            vx = -8D;
        if(getkeys.right)
            vx = 8D;
//        if(vx != 0.0D && vy != 0.0D)
//        {
//            vx *= 0.69999999999999996D;
//            vy *= 0.69999999999999996D;
//        }
        if(getkeys.shift)
        {
            vx *= 0.5D;
            vy *= 0.5D;
//            if((tmp2 = p.effects.getEmpty()) != null)
//            	tmp2.setData(px + vx, py + vy, 0.0D, 0.0D, 8, 0, 0, 0, 'b');
        }
        super.move();
////////////////////////////////玩家出界纠正/////////////////////////////////////////
        	if(px < 20)
        		{px = 20;}
        	if(py < 20)
        		{py = 20;}
        	if(px > 620)
        		{px = 620;}
    		if(py > 620)
    			{py = 620;}
////////////////////////////////////////////////////////////////////////////////
            if(frame < 360)							//左小黑
                st1b_l();
            else
            if(frame < 660)							//右小黑
                st1b_r();
            else
            if(frame >= 700)
                if(frame < 1000)
                {
                    st1b_l();
                    st1b_r();
                } else
                if(frame >= 1050)
                    if(frame < 1300)
                        st1g();
                    else
                    if(frame >= 1350)
                        if(frame < 1600)
                        {
                            st1g();
                            st1b_l();
                        } else
                        if(frame >= 1650)
                            if(frame < 1900)
                            {
                                st1g();
                                st1b_r();
                            } else
                            if(frame < 2200)
                            {
                                st1g();
                                st1b_l();
                                st1b_r();
                            } else
                            if(frame < 2500)
                            {
                                st1g();
                                st1g();
                            } else
                            if(frame < 2700)
                            {
                                st1g();
                                st1g();
                                st1b_l();
                                st1b_r();
                            } else
     ///////////////////////////////////////大boss出场/////////////////////////////
                            if(frame == 2900)
                            {
                                p.enemys.allErase();
                                tmp = p.enemys.getObject(0);
                                tmp.setData(320D, 0.0D, 0.0D, 0.0D, 100, 0, 100, 2000, 'w');
                            }
            
            System.out.println("当前帧数："+frame);
    }

    private void st1b_l()									//左小黑调用方法
    {
        if(frame % 30 == 0 && (tmp = p.enemys.getEmpty()) != null)
            tmp.setData(0.0D, Math.random() * 96D + 48D, 2D, 0.0D, 32, 0, 2, 3, 'h');
    }
    private void st1g()										//噗噗噗调用方法
    {
        if(frame % 30 == 7 && (tmp = p.enemys.getEmpty()) != null)
            tmp.setData(Math.random() * 640D + 40D, 0.0D, 0.0D, 1.0D, 32, 0, 3, 10, 'p');
    }
    private void st1b_r()									//右小黑调用方法
    {
        if(frame % 30 == 15 && (tmp = p.enemys.getEmpty()) != null)
            tmp.setData(640D, Math.random() * 96D + 48D, -2D, 0.0D, 32, 0, 2, 3, 'h');
    }
    public void draw(Graphics g)
    {
        super.draw(g);
    }

    public void plusScore(int i)
    {
        score += i;
        if(score > hiscore)
            hiscore = score;
    }

    public void plusPower(int i)
    {
        power += i;
        if(power > 499)
        {
            score += (power - 499) * 5;
            power = 499;
        }
    }

    public void plusLife(int i)
    {
        life += i;
        if(life > 9)
        {
            score += 10000;
            life = 9;
        }
    }

    public int getPower()
    {
        return power;
    }

    public int getScore()
    {
        return score;
    }

    public int getHiscore()
    {
        return hiscore;
    }

//    public int getStage()
//    {
//        if(frame / 10000 < 3)
//            return frame / 10000;
//        else
//            return 2;
//    }
    private int shotInterval;
    private int guard;
    private int power;
    private int score;
    private int hiscore;
}
