import java.util.*;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.net.URI;
class Shuffle{
	public void shuffleArray(int[] ar)
	{
		Random rnd = new Random();
		for (int i = ar.length - 1; i > 0; i--)
		{
		int index = rnd.nextInt(i + 1);
		int a = ar[index];
		ar[index] = ar[i];
		ar[i] = a;
		}
	}
}
class Warpie implements ActionListener
{
	//Begin variables
	String x,status;
	JFrame frm;
	Container con;
	GridLayout mainGrid;
	JButton roll;
	GridLayout gameGrid;
	Panel pnl[]=new Panel[5];
	JButton help;
	JLabel mySts;
	JLabel compSts;
	JLabel[] lbl=new JLabel[25];
	JLabel updSts;
	JLabel moveSts;
	
	int min=0;
	int pool[]={-6,-5,-4,-3,-3,-2,-2,-1,-1,-1,0,1,1,1,2,2,3,3,4,5,6};
	int ar25[]={1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25};
	String countString[]=new String[25];
	int max=pool.length-1;
	int meDice,compDice,myCount,compCount,myLoc,compLoc,lastMyLoc,lastCompLoc,nx,oldMyPlace,oldCompPlace,move;
	boolean myArea[]=new boolean[25];
	boolean compArea[]=new boolean[25];
	//End variables 
	
	//Begin constructor
	void runGame()
	{
		Shuffle sss=new Shuffle();
		sss.shuffleArray(ar25);
		frm=new JFrame("Warpie Game");
		con=frm.getContentPane();
		mainGrid=new GridLayout(11,1);
		con.setLayout(mainGrid);
		updSts=new JLabel("Playing",JLabel.CENTER);
		updSts.setBackground(Color.decode("#8cfffb"));
		updSts.setOpaque(true);
		updSts.setFont(new Font("Serif", Font.PLAIN, 40));
		con.add(updSts);
		moveSts=new JLabel("Move: "+move,JLabel.CENTER);
		moveSts.setBackground(Color.decode("#8cfffb"));
		moveSts.setOpaque(true);
		moveSts.setFont(new Font("Serif", Font.PLAIN, 40));
		con.add(moveSts);
		gameGrid=new GridLayout(1,5);
		for(int j=0;j<pnl.length;j++)
		{
			pnl[j]=new Panel();
			pnl[j].setLayout(gameGrid);
			int lim=((j+1)*5);
			for(int i=lim-5;i<lim;i++)
			{
				lbl[i]=new JLabel(ar25[i]+"",JLabel.CENTER);
				lbl[i].setBackground(Color.decode("#96fdaf"));
				lbl[i].setBorder(BorderFactory.createLineBorder(Color.black));
				lbl[i].setOpaque(true);
				lbl[i].setFont(new Font("Serif", Font.PLAIN, 30));
				pnl[j].add(lbl[i]);
			}
			con.add(pnl[j]);
			
		}
		roll=new JButton("Roll the die");
		roll.addActionListener(this);
		mySts=new JLabel("You got: "+meDice,JLabel.CENTER);
		compSts=new JLabel("Computer got: "+compDice,JLabel.CENTER);
		mySts.setBackground(Color.decode("#fdf796"));
		mySts.setOpaque(true);
		mySts.setFont(new Font("Serif", Font.PLAIN, 30));
		compSts.setBackground(Color.decode("#fdc496"));
		compSts.setOpaque(true);
		compSts.setFont(new Font("Serif", Font.PLAIN, 30));
		help=new JButton("How to play?");
		help.addActionListener(this);
		con.add(roll);
		con.add(mySts);
		con.add(compSts);
		con.add(help);
		
		frm.setSize(500,726);
		frm.setVisible(true);

		frm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	//End constructor
	public static void main(String args[])
	{
		Warpie w=new Warpie();
		w.runGame();
	}
	
	//Other Methods
	public void actionPerformed(ActionEvent e)
	{
		x=e.getActionCommand();
		updSts.setText("Playing");
		status="";
		//
		//
		//
		if(x=="Roll the die")
		{
			//
			meDice=pool[(int)Math.floor(Math.random()*(max-min+1)+min)];
			myLoc+=meDice;
			if(myLoc>25||myLoc<1)
			{
				myLoc-=meDice;
				updSts.setText("Player can't move");
			}
			if(myLoc==compLoc)
			{
				updSts.setText("Player can't move");
				myLoc-=meDice;	
			}
			mySts.setText("You got:"+meDice+" & Location: "+myLoc);
			compDice=pool[(int)Math.floor(Math.random()*(max-min+1)+min)];
			compLoc+=compDice;
			if(compLoc>25||compLoc<1)
			{
				compLoc-=compDice;
				updSts.setText("Computer can't move");
			}
			if(compLoc==myLoc)
			{
				updSts.setText("Computer can't move");
				compLoc-=compDice;
			}
			compSts.setText("Computer got:"+compDice+" & Location: "+compLoc);
			for(int q=0;q<ar25.length;q++)
			{
				nx=Integer.parseInt(lbl[q].getText());
				if(nx==myLoc)
				{	
					lbl[q].setBackground(Color.decode("#fdf796"));
					countString[q]="yellow";
					break;
				}
			}
			for(int q=0;q<ar25.length;q++)
			{
				nx=Integer.parseInt(lbl[q].getText());
				if(nx==compLoc)
				{	
					lbl[q].setBackground(Color.decode("#fdc496"));
					countString[q]="orange";
					break;
				}
			}
			if(move==45)
			{
				for(int q=0;q<countString.length;q++)
				{
					if(countString[q]=="yellow")
						myCount=myCount+1;
					if(countString[q]=="orange")
						compCount=compCount+1;
				}
				roll.setText("Game Ended");
				if(myCount>compCount)
				{
					updSts.setText("Player won, congrats!");
				}
				if(compCount>myCount)
				{
					updSts.setText("Player lost, better luck next time!");
				}
				mySts.setText("Player Area coverage: "+myCount);
				compSts.setText("Computer Area coverage: "+compCount);
			}
			else
				move=move+1;
			moveSts.setText("Move: "+move);
		}
		//
		//
		//
		//
		if(x=="How to play?")
		{
			try {
				String url = "help.txt";
				Desktop desktop = java.awt.Desktop.getDesktop();
				URI oURL = new URI(url);
				desktop.browse(oURL);
			} catch (Exception ae) {
				ae.printStackTrace();
			}
		}
		if(status!="")
			updSts.setText(status);
	}
}