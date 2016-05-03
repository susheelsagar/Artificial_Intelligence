/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ai;
import kalaha.GameState;

/**
 *
 * @authors susheel sagar, Biswajeet Mohanty, Ruthvik Chinta
 */
public class Minimax 
{
    
    int k;
    
    /*The execution of the Minimax  start with the function startMinimax
    */
    
    public int startMinimax(GameState s, int depth)
    { 
        int final_move=0;       
        return final_move=max(s,depth,-20000,20000);
    }
    
    /*The utility function for the 
        minimax algorithm*/
    
    public int utility(GameState s)
    {
        int i,utility_value=0;
        String board2= s.toString();
        String board_chars[] = board2.split(";");
        int board[] = new int [15];
        for(i=0;i<board_chars.length;i++)
        {   
            board[i]= Integer.parseInt(board_chars[i]);
        }
            
      /*To check whether AI is player 1 or player 2
        */
        if(board[14]==2)
        {
            return board[0]-board[7];    
        }
        
        else if(board[14]==1)
        {
         return board[7]-board[0];
        }
        else 
             return 0;
     
     }
    
    /*Code for max start here */
    
    public int max (GameState s, int depth,int alpha, int beta)
    {
        int l=-20000,final_move=0;
       
        int move;
        
        depth++;
        if(depth==1)
        {
               for(move=1;move<7;move++)
               {
                    int temp=0;
                    GameState s2 = (GameState)s.clone();
                    if(s2.moveIsPossible(move)) 
                    {
                    
                        s2.makeMove(move);
                    
                        temp=min(s2,depth,alpha,beta);
                        /*Finds the MAX(min(s,depth,alpha,beta)*/
                        if(l<=temp)
                        {
                            l=temp;
                            final_move=move;
                        }
                    }
                    /*alpha beta pruning starts here*/
                    if(l>=beta)
                    {
                        return final_move;
                    }
                    if(alpha<l)
                        alpha=l;        
                }
               return final_move;   // The final move to be returned to the function getMove().
        }
        
        /*14 is cosidered as the maximum depth */
        if(depth<14&&depth!=1)
        {
            for(move=1;move<7;move++)
            {
                int temp=0;
                GameState s2 = (GameState)s.clone();
                if(s2.moveIsPossible(move))
                {
                    
                    s2.makeMove(move);
                    
                    temp=min(s2,depth,alpha,beta);
                    /*The below statement is to find 
                        MAX(min(s,depth,aplha,beta))*/
                    if(l<=temp)
                    {
                        l=temp;
                    }
                }
                /*alpha beta pruning*/
                if(l>=beta)
                    return l;
                if(alpha<l)
                    alpha=l;
            }
            return l;
        }
        return 0;
    }
    
    public int min(GameState s,int depth,int alpha, int beta)
    {
        
        int move,min_value,l=20000;
        depth++;
        
        if(depth==14)
        {
            
           return min_value=utility(s);
        }
        
        if(depth<14)
        {
            for(move=1;move<7;move++)
            {
                int temp=0;
                GameState s2 = (GameState)s.clone();
                if(s2.moveIsPossible(move))
                {
                    s2.makeMove(move);
                       temp=max(s2,depth,alpha,beta);
                       /*code for MIN(max(s,depth,alpha,beta))*/ 
                       if(l>=temp)
                        {
                            l=temp;
                        }
                }
                /*alpha beta pruning*/
                if(l<=alpha)
                    return l;
                if(l<beta)
                    beta=l;
            }    
            return l;
            
        }
       return 0; 
    }
}