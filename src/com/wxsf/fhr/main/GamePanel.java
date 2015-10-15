package com.wxsf.fhr.main;
import java.applet.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import com.wxsf.fhr.model.*;
import com.wxsf.fhr.control.*;
public class GamePanel extends JPanel implements Runnable, KeyListener,MouseMotionListener,MouseListener
{
    public GamePanel()
    {
        dbImage = null;
        enemys = new ObjectsArray("Enemy", 50);
        bullets = new ObjectsArray("Bullet", 400);
        shoots = new ObjectsArray("Shoot", 100);
        effects = new ObjectsArray("Effect", 400);
        player = new Player();
        getKeys = new GetKeys();
        running = false;
        do_reset = false;
        resetInterval = 0;
        backLoop = 0;
		menuMode =9;
        ImageIcon imageicon = new ImageIcon(getClass().getResource("/image/objects.png"));		//角色+BOOS们的序列图
        image = imageicon.getImage();
        ImageIcon imageicon1 = new ImageIcon(getClass().getResource("/image/bg1.jpg"));		//背景
        backImage = imageicon1.getImage();
        ImageIcon ic = new ImageIcon(getClass().getResource("/image/character.png"));			//选人界面序列图
        show=ic.getImage();
        ImageIcon ibak = new ImageIcon(getClass().getResource("/image/redwhitebak.png"));
        redwhitebak=ibak.getImage();
        ibak = new ImageIcon(getClass().getResource("/image/9bak.png"));
        _9bak=ibak.getImage();
        //////////////////////音效
        Ashoot=new AudioClip[17];
        Ashoot1=new AudioClip[17];
        for(int j = 0; j < 17; j++)
        {
            Ashoot[j] = Applet.newAudioClip(getClass().getResource("/sound/shoot.wav"));
            Ashoot1[j] = Applet.newAudioClip(getClass().getResource("/sound/shoot1.wav"));
        }   		
        Acrash=Applet.newAudioClip(getClass().getResource("/sound/crash.wav"));
        APirate=Applet.newAudioClip(getClass().getResource("/sound/Mission Impossible Theme.wav"));
        AbossDead1=Applet.newAudioClip(getClass().getResource("/sound/bossDead1.wav"));
        AbossDead2=Applet.newAudioClip(getClass().getResource("/sound/bossDead2.wav"));
        Ax=Applet.newAudioClip(getClass().getResource("/sound/x.wav"));
        shootNum = 0;
        setFocusable(true);
////////////////////////////////////////////
        addKeyListener(this);
        addMouseListener(this);
        addMouseMotionListener(this);
////////////////////////////////////////////
        SuperObject.gameObjectInit(this);

        gameLoop = new Thread(this);
        gameLoop.start();
    }
    /////////////////keyListener////////////////////////
    public void keyTyped(KeyEvent keyevent)
    {}

    public void keyPressed(KeyEvent keyevent)
    {
        getKeys.keyPressed(keyevent.getKeyCode());
    }

    public void keyReleased(KeyEvent keyevent)
    {
        getKeys.keyReleased(keyevent.getKeyCode());
    }
    ///////////////MouseMotionListener////////////////////
    public void mouseMoved(MouseEvent e){
    	moveP=e.getPoint();
    }
    
    public void mouseDragged(MouseEvent e){
    	
    }
    ////////////////MouseListener////////////////////////
    public void mouseClicked(MouseEvent e){

    }
    public void mouseEntered(MouseEvent e){

    }
    public void mouseExited(MouseEvent e){
    	
    }
    public void mousePressed(MouseEvent e){
    	checkP=e.getPoint();
    	//封面////////////////////////
    	if(menuMode==9){
    	if((checkP.getX()>=463 && checkP.getX()<=563) && (checkP.getY()>=416 && checkP.getY()<=448))	//开始游戏
    		setMenuMode(1);
    	if((checkP.getX()>=463 && checkP.getX()<=515) && (checkP.getY()>=450 && checkP.getY()<=482))	//说明
    		setMenuMode(8);
    	if((checkP.getX()>=463 && checkP.getX()<=563) && (checkP.getY()>=482 && checkP.getY()<=514))	//制作名单
    		setMenuMode(7);
    	if((checkP.getX()>=463 && checkP.getX()<=563) && (checkP.getY()>=514 && checkP.getY()<=546))	//退出
    		setMenuMode(6);
    	}
    	//说明//////////////////////
    	if(menuMode==8){
        	if((checkP.getX()>=486 && checkP.getX()<=600) && (checkP.getY()>=550 && checkP.getY()<=600))	
        		setMenuMode(9);
    	}
        if(menuMode == 3)					//boss死亡
        {
        	gameSet();
        	if((checkP.getX()>=60 && checkP.getX()<=236) && (checkP.getY()>=509 && checkP.getY()<=593))	
        		setMenuMode(0);
        	if((checkP.getX()>=363 && checkP.getX()<=585) && (checkP.getY()>=501 && checkP.getY()<=596))	
        		setMenuMode(9);
        }
        if(menuMode == 4)					//玩家死亡
        {
        	gameSet();
        	if((checkP.getX()>=60 && checkP.getX()<=236) && (checkP.getY()>=509 && checkP.getY()<=593))	
        		setMenuMode(0);
        	if((checkP.getX()>=363 && checkP.getX()<=585) && (checkP.getY()>=501 && checkP.getY()<=596))	
        		setMenuMode(9);
        }
    }
    public void mouseReleased(MouseEvent e){
    	
    }
    public void gameSet()					//游戏初次载入
    {
        do_reset = false;
        resetInterval = 1;					//子弹睡眠时间
        menuMode = 9;
        player.setData(312D, 539D, 0.0D, 0.0D, 4, 0, 0, 5, NowCharacter);		//载入角色信息
        shoots.allErase();
        bullets.allErase();
        enemys.allErase();
        effects.allErase();
    }
    public void gameReset()					//游戏重新载入
    {
        do_reset = false;
        resetInterval = 1;					//子弹间隔时间
        menuMode = 1;
        player.setData(312D, 539D, 0.0D, 0.0D, 4, 0, 0, 5, NowCharacter);		//载入角色信息
        shoots.allErase();
        bullets.allErase();
        enemys.allErase();
        effects.allErase();
    }

    private void gameUpdate()		
    {
        if(menuMode == 0)
        {
            shoots.allMove();
            player.move(getKeys);
            bullets.allMove();
            enemys.allMove();
            effects.allMove();
            if(getKeys.esc)
                menuMode = 2;
        }
    }

    private void gameRender()		//游戏资源载入
    {
        if(dbImage == null)
        {
            dbImage = createImage(640, 640);
            if(dbImage == null)
                return;
            g = dbImage.getGraphics();
        }			
        ///////////////////////循环背景//////////////////////////////	
        backLoop = (backLoop + 8) % 640;			//每帧下移8像素，累积移动超过640像素取余为初始数重新累计
        for(int i = -1; i < 2; i++)
        {
            for(int j = 0; j < 2; j++)
                g.drawImage(backImage, j * 320, i * 640 + backLoop, (j + 1) * 320, (i + 1) * 640 + backLoop, 0, 0, 480, 640, null);
        }
        shoots.allDraw(g);
        player.draw(g);
        bullets.allDraw(g);
        enemys.allDraw(g);
        effects.allDraw(g);
        SuperObject gameobject=enemys.getObject(0);
        if((gameobject = enemys.getObject(0)).getExist())
        {
            int k1 = gameobject.getLife();
            if(gameobject.getSize() >=100)
            {
                g.setColor(Color.BLACK);
                g.fillRect(19, 39, 602, 14);
                g.setColor(Color.RED);
                g.fillRect(20, 40, 600, 12);
                g.setColor(Color.GREEN);
                g.fillRect(20, 40, (600 * k1) / 2000, 12);
                System.out.println("boss残血:"+k1);
                if(k1<=5)
                {
                	AbossDead1.play();
                	try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
                	AbossDead2.play();
                	setMenuMode(3);
                	gameobject.setData(320D, 0.0D, 0.0D, 0.0D, 100, 0, 100, 2000, 'w');
                }
            }
        }


        ImageIcon iNum = new ImageIcon(getClass().getResource("/image/Num.png"));
        Image Num=iNum.getImage();
        String PlaySc=String.valueOf(player.getScore());
        for(int i=0;i<PlaySc.length();i++){
        	g.drawImage(Num, 570+(i)*10,0,570+(i)*10+10,20,/**/Integer.valueOf(PlaySc.substring(i, i+1))*10, 0,Integer.valueOf(PlaySc.substring(i, i+1))*10+10,20, null);
        }
        ImageIcon iScore = new ImageIcon(getClass().getResource("/image/Score.png"));
        Image Score=iScore.getImage();
        g.drawImage(Score, 514, 0, null);

        //////////////生命值
        ImageIcon bloodbar = new ImageIcon(getClass().getResource("/image/bloodbar.png"));
        Image ibloodbar=bloodbar.getImage();
        g.drawImage(ibloodbar, 10, 500+(5-player.getLife())*17, 110, 600, 0, (5-player.getLife())*17, 100, 100, null);
        ImageIcon bloodbox = new ImageIcon(getClass().getResource("/image/box.png"));
        Image ibloodbox=bloodbox.getImage();
        g.drawImage(ibloodbox, 9, 499, null);
        /////////////法力值
        ImageIcon powerbar = new ImageIcon(getClass().getResource("/image/powerbar.png"));
        Image ipowerbar=powerbar.getImage();
        g.drawImage(ipowerbar, 520, 600-(player.getPower()/5), 620, 600, 0, (100-player.getPower()/5), 100, 100, null);
        g.drawImage(ibloodbox, 519, 499, null);
        
        if(menuMode != 0)
        {
            g.setColor(Color.BLACK);
            g.fillRect(0, 0, 640, 640);
            g.setColor(Color.WHITE);
            if(menuMode==9){						//游戏开始界面
            	ImageIcon imageicon = new ImageIcon(getClass().getResource("/image/startMenu.png"));		//角色+BOOS们的序列图
                Image startMenu = imageicon.getImage();
                imageicon = new ImageIcon(getClass().getResource("/image/start.png"));		//开始按钮
                Image start=imageicon.getImage();
                imageicon = new ImageIcon(getClass().getResource("/image/help.png"));		//帮助
                Image help=imageicon.getImage();
                imageicon = new ImageIcon(getClass().getResource("/image/Staff.png"));		//制作人员
                Image Staff=imageicon.getImage();
                imageicon = new ImageIcon(getClass().getResource("/image/Exit.png"));		//退出
                Image Exit=imageicon.getImage();
                ////////////////////////////////////////////
                g.drawImage(startMenu, 0, 0, null);
                g.drawImage(start, 463, 416, 563, 448, 0, 0, 100, 32, null);
                g.drawImage(help, 463, 450, 515, 482, 0, 0, 52, 32, null);
                g.drawImage(Staff, 463, 482, 563, 514, 0, 0, 100, 32, null);
                g.drawImage(Exit, 463, 514, 563, 546, 0, 0, 100, 32, null);
                ///////////////////////////////////////////鼠标移动到按钮是的效果
            	if((moveP.getX()>=463 && moveP.getX()<=563) && (moveP.getY()>=416 && moveP.getY()<=448))	//开始游戏
            		g.drawRect(463, 416, 100, 32);
            	if((moveP.getX()>=463 && moveP.getX()<=515) && (moveP.getY()>=450 && moveP.getY()<=482))	//说明
            		g.drawRect(463, 450, 50, 32);
            	if((moveP.getX()>=463 && moveP.getX()<=563) && (moveP.getY()>=482 && moveP.getY()<=514))	//制作名单
            		g.drawRect(463, 482, 100, 32);
            	if((moveP.getX()>=463 && moveP.getX()<=563) && (moveP.getY()>=514 && moveP.getY()<=546))	//退出
            		g.drawRect(463, 514, 100, 32);

            }
            else if(menuMode == 8){						//说明//TODO
            	ImageIcon imageicon = new ImageIcon(getClass().getResource("/image/help-page1.png"));
                Image help = imageicon.getImage();
                g.drawImage(help, 0, 0, null);
                imageicon = new ImageIcon(getClass().getResource("/image/B return.png"));
                Image returnn = imageicon.getImage();
                g.drawImage(returnn, 470, 550,624,600,0,0,300,100, null);
                if((moveP.getX()>=486 && moveP.getX()<=600) && (moveP.getY()>=550 && moveP.getY()<=600))
                	g.drawRect(486, 550, 114, 50);
            	}
            
            else if(menuMode == 7){						//制作者
                ImageIcon imageicon = new ImageIcon(getClass().getResource("/producer/Staff.png"));
                Image staff = imageicon.getImage();
                g.drawImage(staff, 0,0,null);
             
                ImageIcon iET = new ImageIcon(getClass().getResource("/producer/ET.png"));			//ET
                Image ET = iET.getImage();
                if((moveP.getX()>=320 && moveP.getX()<=640) && (moveP.getY()>=320 && moveP.getY()<=640))
                	g.drawImage(ET, 320, 320, null);
                
                ImageIcon iPanda = new ImageIcon(getClass().getResource("/producer/Panda.png"));			//大叔
                Image Panda = iPanda.getImage();
                if((moveP.getX()>=0 && moveP.getX()<=320) && (moveP.getY()>=320 && moveP.getY()<=640))
                	g.drawImage(Panda, 0, 320, null);
                
                ImageIcon iAhu = new ImageIcon(getClass().getResource("/producer/Ahu.png"));			//阿胡
                Image Ahu = iAhu.getImage();
                if((moveP.getX()>=0 && moveP.getX()<=320) && (moveP.getY()>=0 && moveP.getY()<=320))
                	g.drawImage(Ahu, 0, 0, null);
                
                ImageIcon iSY = new ImageIcon(getClass().getResource("/producer/SY.png"));			//孙煜
                Image SY = iSY.getImage();
                if((moveP.getX()>=320 && moveP.getX()<=640) && (moveP.getY()>=0 && moveP.getY()<=320))
                	g.drawImage(SY, 320, 0, null);
                
                ImageIcon icheckMe = new ImageIcon(getClass().getResource("/producer/checkMe.png"));
                Image checkMe = icheckMe.getImage();
                g.drawImage(checkMe, 292,292,null);
                if((checkP.getX()>=292 && checkP.getX()<=343) && (checkP.getY()>=292 && checkP.getY()<=343))
                	setMenuMode(5);
            }
            else if(menuMode == 6){						//退出
            	System.exit(0);
            }
            else if(menuMode==5){							//标哥
                ImageIcon iAbiao = new ImageIcon(getClass().getResource("/producer/Abiao.png"));
                Image Abiao = iAbiao.getImage();
                g.drawImage(Abiao, 0,0,null);
            	if(getKeys.x){
            		setMenuMode(9);
            	}
            }
            //TODO
            else if(menuMode == 1)						//选人界面
            {
                g.drawString("0912 暴走弹幕", 64, 64);
                g.drawString("按z开始游戏", 233, 64);
                if(player.getColor() == 'w')			//巫女
                {
                	g.drawImage(redwhitebak, 0, 0,null);		//画背景
                    g.drawImage(show,160, 160, 640, 640,482,0,960,480,null);//画人物
                    if(getKeys.right){
                        player.setData(240D, 360D, 0.0D, 0.0D, 4, 0, 0, 5, 'c');
                        NowCharacter='c';
                        }
                } else									//琪露诺
                {
                	g.drawImage(_9bak, 0, 0,null);				//画背景
                    g.drawImage(show,0, 160, 480, 640,0,0,480,480,null);//画人物
                    
                    if(getKeys.left){
                        player.setData(240D, 360D, 0.0D, 0.0D, 4, 0, 0, 5, 'w');
                        NowCharacter='w';
                        }
                }
                if(getKeys.z)
                    menuMode = 0;
            } 
            else  if(menuMode == 2)					//暂停界面
            {
                ImageIcon iStop = new ImageIcon(getClass().getResource("/image/Stop.png"));		
                Image Stop = iStop.getImage();
                g.drawImage(Stop, 0,0,null);
                if(getKeys.z)
                    menuMode = 0;
            } else
            if(menuMode == 3)					//boss死亡
            {
                ImageIcon icc = new ImageIcon(getClass().getResource("/image/win.png"));		
                Image win = icc.getImage();
                g.drawImage(win, 0,0,null);
//            	if((moveP.getX()>=60 && moveP.getX()<=236) && (moveP.getY()>=496 && moveP.getY()<=593))	
//            		g.drawRect(60, 496, 171, 97);
//            	if((moveP.getX()>=363 && moveP.getX()<=585) && (moveP.getY()>=501 && moveP.getY()<=596))
//            		g.drawRect(363, 501, 222, 95);
            }
            if(menuMode == 4)					//玩家死亡
            {
                ImageIcon icc = new ImageIcon(getClass().getResource("/image/loss.png"));		
                Image loss = icc.getImage();
                g.drawImage(loss, 0,0,null);
//            	if((moveP.getX()>=60 && moveP.getX()<=236) && (moveP.getY()>=496 && moveP.getY()<=593))	
//            		g.drawRect(60, 496, 171, 97);
//            	if((moveP.getX()>=363 && moveP.getX()<=585) && (moveP.getY()>=501 && moveP.getY()<=596))
//            		g.drawRect(363, 501, 222, 95);
            }
        }
        shootNum= (shootNum + 1) % 17;
    }

    private void paintScreen()
    {
    	Graphics g = getGraphics();
        if(g != null && dbImage != null)
        g.drawImage(dbImage, 0, 0, null);
    }

    public void run()
    {
        running = true;
//        do_reset = true;
        gameSet();
        do
        {
            if(!running)
                break;
//            if(do_reset)
//                gameReset();
            gameUpdate();
            gameRender();
            paintScreen();
            ////////////////////屏幕睡眠///////////////
            try {
				Thread.sleep(20);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            ////////////////////////////////////////
            if(getKeys.f12){
            	gameReset();
            	setMenuMode(9);	
            }
            if(!player.getExist())							//玩家之死
            {
            	setMenuMode(4);
            } 
        } while(true);
        System.exit(0);
    }

    public void setMenuMode(int i)
    {
        menuMode = i;
        if(menuMode==5){
        	APirate.loop();}
        else
        	APirate.stop();
    }
    private Graphics g;
    private Image dbImage;
    public ObjectsArray enemys;
    public ObjectsArray bullets;
    public ObjectsArray shoots;
    public ObjectsArray effects;
    public Player player;
    private GetKeys getKeys;
    public Image image;
    public Image backImage;
    private boolean running;
    private boolean do_reset;
    private int resetInterval;
    private int backLoop;
    private int menuMode;
    private Thread gameLoop;
    public Image show;
    public char NowCharacter='w';			//当前选择角色
    public Image _9bak,redwhitebak;
    public Point checkP;
    public Point moveP=new Point(0,0);
    public int shootNum;
    public AudioClip Ashoot[],Ashoot1[];
    public AudioClip Acrash;
    public AudioClip APirate;
    public AudioClip AbossDead1;
    public AudioClip AbossDead2;
    public AudioClip Ax;
}
