package com.wxsf.fhr.main;

import java.awt.Graphics;

import com.wxsf.fhr.model.Bullet;
import com.wxsf.fhr.model.Effect;
import com.wxsf.fhr.model.Enemy;
import com.wxsf.fhr.model.Shoot;
import com.wxsf.fhr.model.SuperObject;

public class ObjectsArray
{

    public ObjectsArray(String s, int j)
    {
        emptySearch = 0;
        arrayMax = j;
        gameObject = new SuperObject[j];
        for(i = 0; i < j; i++)
        {
            if(s.equals("Enemy"))
                gameObject[i] = new Enemy();
            if(s.equals("Bullet"))
                gameObject[i] = new Bullet();
            if(s.equals("Shoot"))
                gameObject[i] = new Shoot();
            if(s.equals("Effect"))
                gameObject[i] = new Effect();
        }

    }

    public void allMove()
    {
        for(i = 0; i < arrayMax; i++)
            if(gameObject[i].getExist())
                gameObject[i].move();

    }

    public void allDraw(Graphics g)
    {
        for(i = 0; i < arrayMax; i++)
            if(gameObject[i].getExist())
                gameObject[i].draw(g);

    }

    public void allErase()
    {
        for(i = 0; i < arrayMax; i++)
            gameObject[i].erase();

    }

    public SuperObject getEmpty()			//返回空闲数组位
    {
        for(i = 0; i < arrayMax; i++)
        {
            if(!gameObject[emptySearch].getExist())			//如果该数组位未被占用
                return gameObject[emptySearch];				//传回该数组
            emptySearch++;												//下标+1
            if(emptySearch >= arrayMax)						//如果下标超过规定数组
                emptySearch = 0;							//返回第零位数组
        }

        return null;
    }

    public SuperObject getObject(int j)
    {
        return gameObject[j];
    }

    public int getArrayMax()
    {
        return arrayMax;
    }

    private SuperObject gameObject[];
    private int emptySearch;
    private int arrayMax;
    private int i;
}
