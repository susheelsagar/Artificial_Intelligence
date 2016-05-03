package wumpusworld;

/**
 * Contains starting code for creating your own Wumpus World agent.
 * Currently the agent only make a random decision each turn.
 * 
 * @author Johan Hagelbäck
 */
public class MyAgent implements Agent
{
    private World w;
    int rnd;
    static double q[][],reward;
    static int n[][];
    static int action ;
    static int prev_state,prev_direction;
    static int flag;
    int Wumpus;
    /**
     * Creates a new instance of your solver agent.
     * 
     * @param world Current world state 
     */
    public MyAgent(World world)
    {
        int i,j,k;
        w = world;   
        
        if(flag==0)
        {            
        q = new double[16][4];
        n = new int[16][4];
        
        
        /*Preventing from running into bottom wall*/
        for(i=0;i<4;i++)
        {
            q[i][2]= -1;            
        }
        /*Preventing from running into top wall*/
        for(i=12;i<16;i++)
        {
            q[i][0]=-1;
        }
        /*Preventing from running into left wall*/
        q[12][3]=-1;
        q[8][3]=-1;
        q[4][3]=-1;
        q[0][3] =-1;
        
        /* Preventing from running into right wall*/
        q[3][1]=-1;
        q[7][1]=-1;
        q[11][1]=-1;
        q[15][1]=-1;
        
        
        /*As we start from the first block the state is indexed as 0*/
        prev_state=0;
        /*The the arrow is facing right the w.getDirection() returns 1 initially*/
        prev_direction = 1;
        flag++;         
        }
        
    }
    
    /*Function to re-initialize the Q-table */
    public void refresh()
    {
        
        int i,j,k;       
        
        for(i=0;i<16;i++)
                for(j=0;j<4;j++)
                    
                    {
                        q[i][j]= 0;
                        n[i][j]=0;
                    }
        /*Preventing from running into bottom wall*/
        for(i=0;i<4;i++)
        {
            q[i][2]= -1;            
        }
        /*Preventing from running into top wall*/
        for(i=12;i<16;i++)
        {
            q[i][0]=-1;
        }
        /*Preventing from running into left wall*/
        q[12][3]=-1;
        q[8][3]=-1;
        q[4][3]=-1;
        q[0][3] =-1;
        
        /* Preventing from running into right wall*/
        q[3][1]=-1;
        q[7][1]=-1;
        q[11][1]=-1;
        q[15][1]=-1;
        
        
        /*As we start from the first block the state is indexed as 0*/
        prev_state=0;
        reward=0;
        /*The the arrow is facing right the w.getDirection() returns 1 initially*/
        prev_direction = 1;
        action=0;
        
        GUI.game_count=0;
        
    }
    
    
            
    /**
     * Asks your solver agent to execute an action.
     */

    @Override
    public void doAction()
    {
        int i,j,k;
        
        int cX = w.getPlayerX();
        int cY = w.getPlayerY();       
        
        /*Code to kill the WUMPUS*/
        if(GUI.game_count!=0)
        {
        if(w.wumpusAlive())
        {
        if(GUI.wX!=-1)
        {
            if(GUI.wY==1)
            {
                 
                w.doAction(World.A_SHOOT);
                return;
            }
            else if(GUI.wX==1)
            {
                
                w.doAction(World.A_TURN_LEFT);
                w.doAction(World.A_SHOOT);
                return;
            }
            else if(GUI.wX==2)
            {
                
                w.doAction(World.A_MOVE);
                
                w.doAction(World.A_TURN_LEFT);
                w.doAction(World.A_SHOOT);
                return;
            }
            else if(GUI.wX==3)
            {
                if(GUI.wY==3)
                {
                    w.doAction(World.A_TURN_LEFT);
                    w.doAction(World.A_MOVE);
                    w.doAction(World.A_MOVE);
                    w.doAction(World.A_TURN_RIGHT);
                    w.doAction(World.A_SHOOT);
                    return;
                }
                else
                {
                    w.doAction(World.A_MOVE);              
                    w.doAction(World.A_MOVE);                
                    w.doAction(World.A_TURN_LEFT);
                    w.doAction(World.A_SHOOT);
                    return;
                }
            }
            
        }
        }
        }   
        
        
        //Basic action:
        //Grab Gold if we can.
        if (w.hasGlitter(cX, cY))
        {
            action = learn(w);
            w.doAction(World.A_GRAB);
            return;
        }
        
        //Basic action:
        //We are in a pit. Climb up.
        if (w.isInPit())
        {
            action = learn(w);
            w.doAction(World.A_CLIMB);
            
            return;
        }
        
        
        //Test the environment
        if (w.hasBreeze(cX, cY))
        {
            System.out.println("I am in a Breeze");
        }
        if (w.hasStench(cX, cY))
        {
            System.out.println("I am in a Stench");
        }
        if (w.hasPit(cX, cY))
        {
            System.out.println("I am in a Pit");
        }
        if (w.getDirection() == World.DIR_RIGHT)
        {
            System.out.println("I am facing Right");
        }
        if (w.getDirection() == World.DIR_LEFT)
        {
            System.out.println("I am facing Left");
        }
        if (w.getDirection() == World.DIR_UP)
        {
            System.out.println("I am facing Up");
        }
        if (w.getDirection() == World.DIR_DOWN)
        {
            System.out.println("I am facing Down");
        }  
              
        int action;
        /*Calling the code for Q-Learning agent*/
        action = learn(w);
        /*Code for actions starts here*/
        //Action to go up                
        if(action==0)
        {   
            int x= w.getDirection();
            if(x==0)
            {
                w.doAction(World.A_MOVE);
            }
            else if(x==1)
            {
                w.doAction(World.A_TURN_LEFT);
                w.doAction(World.A_MOVE);
            }
            else if(x==2)
            {
                w.doAction(World.A_TURN_LEFT);
                w.doAction(World.A_TURN_LEFT);
                w.doAction(World.A_MOVE);
            }
            else if(x==3)
            {
                w.doAction(World.A_TURN_RIGHT);
                w.doAction(World.A_MOVE);
            }
            
        }
        //Action to go right
        else if(action==1)
        {
            int x= w.getDirection();
            
            if(x==0)
            {
                w.doAction(World.A_TURN_RIGHT);
                w.doAction(World.A_MOVE);
            }
            else if(x==1)
            {
                w.doAction(World.A_MOVE);
            }
            else if(x==2)
            {
                w.doAction(World.A_TURN_LEFT);
                w.doAction(World.A_MOVE);
            }
            else if(x==3)
            {
                w.doAction(World.A_TURN_LEFT);
                w.doAction(World.A_TURN_LEFT);
                w.doAction(World.A_MOVE);
            }
        }
        //Action to go down
        else if(action==2)
        {
            int x= w.getDirection();
            
            if(x==0)
            {
                w.doAction(World.A_TURN_RIGHT);
                w.doAction(World.A_TURN_RIGHT);
                w.doAction(World.A_MOVE);
            }
            else if(x==1)
            {
                w.doAction(World.A_TURN_RIGHT);
                w.doAction(World.A_MOVE);
            }                
            
            else if(x==2)
            {
                w.doAction(World.A_MOVE);
            }
            else if(x==3)
            {
                w.doAction(World.A_TURN_LEFT);
                w.doAction(World.A_MOVE);
            }               
        }
        //Action to go left
        else if(action==3)
        {
            int x= w.getDirection();
            
            if(x==0)
            {
                w.doAction(World.A_TURN_LEFT);
                w.doAction(World.A_MOVE);
            }
            else if(x==1)
            {
                w.doAction(World.A_TURN_RIGHT);
                w.doAction(World.A_TURN_RIGHT);
                w.doAction(World.A_MOVE);
            }                
            
            else if(x==2)
            {
                w.doAction(World.A_TURN_RIGHT);
                w.doAction(World.A_MOVE);
            }
            else if(x==3)
            {                
                w.doAction(World.A_MOVE);
            }
        }          
    }
    /*code for Q-Learning starts here*/
    public int learn(World w)
    {
        int i;        
        int cX= w.getPlayerX();
        int cY= w.getPlayerY();
        int direction = w.getDirection();
        int position = cX +((cY-1)*4) -1;
        
        if(w.hasGlitter(cX, cY))
        {
            reward = 1;            
            return 0;
        }
        else if(w.hasPit(cX, cY))
        {            
            reward = -0.6;            
        }
        else if(w.hasBreeze(cX, cY))
        {
            reward=-0.1;
        }
        
        else if(w.hasStench(cX, cY))
        {
            reward=-0.4;
        }
        
        else if(w.hasWumpus(cX, cY))
        {
            reward = -1;
        }
        else 
        {    
            reward = -0.05;
        }
        
        n[prev_state][action]++;                  
        q[prev_state][action] +=reward;
        prev_state=position;prev_direction=direction;        
        
        /*Preventing from running into bottom wall*/
        for(i=0;i<4;i++)
        {
            q[i][2]= -1;            
        }
        /*Preventing from running into top wall*/
        for(i=12;i<16;i++)
        {
            q[i][0]=-1;
        }
        /*Preventing from running into left wall*/
        q[12][3]=-1;
        q[8][3]=-1;
        q[4][3]=-1;
        q[0][3] =-1;
        
        /* Preventing from running into right wall*/
        q[3][1]=-1;
        q[7][1]=-1;
        q[11][1]=-1;
        q[15][1]=-1;       
        
        action = argmax(position,direction);          
        
        return action;
        
    }    
    
    /*the function that returns the best action*/
    public int argmax(int position, int direction)
    {
        int k,arg=0;
        double temp=0.0,temp2;
        for(k=0;k<4;k++)
        {
            temp2=utility(position,direction,k);
            if(k==0)
            {
                temp=temp2;
            }
            //System.out.println("The k value is "+ k + "the utlility value is "+ temp2);
            if(temp2>=temp)
            {              
                temp=temp2;
                arg=k;             
            }               
        }        
        return arg;
    }
    
    /*The utility function */
    public double utility(int position, int direction, int action)
    {        
        /*A bad utility value when the agent is about to run into the wall*/
        if(position==0||position==1||position==2||position==3)
        {
            if(action==2)
            {
                return -1000;
            }
        }
        if(position==12||position==13||position==14||position==15)
        {
            if(action==0)
            {
                return -1000;
            }
        }
        if(position==0||position==4||position==8||position==12)
        {
            if(action==3)
            {
                return -1000;
            }
        }
        if(position==3||position==7||position==11||position==15)
        {
            if(action==1)
            {
                return -1000;
            }
        }        
        
        /*exploration vs exploitation trade off*/
        if(n[position][action]<2)
            return 1;        
        else
            return q[position][action];        
    }
    
}

