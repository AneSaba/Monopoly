import java.io.*;
import java.util.*;
//Aneesh Saba
//Period 2
public abstract class Monopoly extends SquareType
{
	public static void main(String[] args)
	{
		Scanner input = new Scanner(System.in);
		String[] x = new String[40];
		boolean isFirst = initRoll();
		boolean game = false;
		String[] extra = new String[40];
		String[][] properties = new String[40][20];
		ArrayList<String> p1prop = new ArrayList<String>();
		ArrayList<String> p2prop = new ArrayList<String>();
		initProp(properties);
		initBoard(extra);
		initBoard(x);
		printBoard(x,extra);
		int p1money = 1500;
		int p2money = 1500;
		int p1location = 0;
		int p2location = 0;
		int p1roll = 0;
		int p2roll = 0;
		int temp = 0;
		int[] trade = new int[2];
		int moneychange = 0;
		int[] bankrupt = new int[2];
		int[] chance = new int[2];
		int[] propAction = new int[2];
		boolean getOutOfJailp1 = false;
		boolean getOutOfJailp2 = false;
		boolean p1jail=false;
		int numOfJailp1 = 0;
		int numOfJailp2 = 0;
		boolean p2jail = false;
		int oneroll = 0;
		int tworoll = 0;
		while(game!=true)
		{
			if(isFirst==true)
			{
				//Player 1
				if(p1jail!=true)
				{
					initBoard(extra);
					System.out.println("What would you like to do - P1?");
					System.out.println("1. Roll");
					System.out.println("2. Manage Properties");
					temp = input.nextInt();
					if(temp==1)
					{
						p1roll = PlayerOneRoll();
						x[p1location] = " ";
						p1location += p1roll;
						if(p1location>=40)
						{
							p1location-=39;
							p1money+=200;
						}
						x[p1location] = "X";
						printBoard(x,extra);
					}
					else
					{
						moneychange = manage(1,p1prop,properties);
						p1money+=moneychange;
					}
					if(Integer.parseInt(properties[p1location][2])==0)
					{
						System.out.println("What would you like to do - P1?");
						System.out.println("1. Buy");
						System.out.println("2. Don't buy");
						temp = input.nextInt();
						if(temp==1)
						{
							p1prop.add(properties[p1location][0]);
							p1money-=Integer.parseInt(properties[p1location][3]);
							properties[p1location][2] = "1";
						}
					}
					bankrupt = bankruptCheck(p1money,p2money,1,p1prop,p2prop,properties);
					p1money = bankrupt[0];
					p2money = bankrupt[1];
					if(properties[p1location][1].equals("Chance"))
					{
						chance = chance(p1location);
						if(chance[0]==50)
							p1money+=50;
						else if(chance[0]==10)
							getOutOfJailp1 = true;
						else
						{
							x[p1location] = " ";
							if(p1location<chance[1])
								p1location = chance[1];
							else
							{
								p1money+=200;
								p1location = chance[1];
							}
						}

					}
				}
				if(properties[p1location][1].equals("Go To Jail")||(p1jail==true&&properties[p1location][1].equals("Jail")))
				{
					System.out.println("What would you like to do");
					System.out.println("1. Roll");
					System.out.println("2. Trade");
					System.out.println("3. Manage Properties");
					System.out.println("4. Pay");
					p1location = 30;
					if(getOutOfJailp1==true)
					{
						System.out.println("5. Use chance card");
					}
					temp = input.nextInt();
					if(numOfJailp1==2)
					{
						System.out.println("You pay $50");
						p1money-=50;
						p1jail = false;
						numOfJailp1 = 0;
						p1roll = PlayerOneRoll();
						x[p1location] = " ";
						p1location += p1roll;
						if(p1location>=40)
						{
							p1location-=39;
							p1money+=200;
						}
						x[p1location] = "X";
						printBoard(x,extra);
					}
					else if(temp==1)
					{
						System.out.println("Enter R to roll");
						String s = input.nextLine();
						oneroll = (int)(Math.random() * 6)+1;
						tworoll = (int)(Math.random() * 6)+1;
						if(oneroll==tworoll)
						{
							p1jail = false;
							numOfJailp1 = 0;
						}
						else
							numOfJailp1++;

					}
					else if(temp==2)
					{
						trade = trade(1,p1money,p2money,p1prop,p2prop,properties);
						p1money=trade[0];
						p2money=trade[1];
					}
					else if(temp==3)
					{
						moneychange = manage(1,p1prop,properties);
						p1money+=moneychange;
					}
					else if(temp==4)
					{
						System.out.println("You pay $50");
						p1money-=50;
						p1jail = false;
						numOfJailp1 = 0;
						p1roll = PlayerOneRoll();
						x[p1location] = " ";
						p1location += p1roll;
						if(p1location>=40)
						{
							p1location-=39;
							p1money+=200;
						}
						x[p1location] = "X";
						printBoard(x,extra);
					}
					else if(temp==5)
					{
						p1jail = false;
						numOfJailp1 = 0;
					}
				}
				if(p1jail!=true)
				{
					propAction = propAction(properties,1,p1location,p1money,p2money,p1roll,p2roll);
					p1money = propAction[0];
					p2money = propAction[1];
					System.out.println("P1 Money - $"+p1money);
					System.out.println("What would you like to do - P1?");
					System.out.println("1. End Turn");
					System.out.println("2. Trade");
					System.out.println("3. Manage Properties");
					temp = input.nextInt();
					if(temp==2)
					{
						trade = trade(1,p1money,p2money,p1prop,p2prop,properties);
						p1money=trade[0];
						p2money=trade[1];
					}
					else if(temp==3)
					{
						moneychange = manage(1,p1prop,properties);
						p1money+=moneychange;
					}
				}
				//Player 2
				if(p2jail!=true)
				{
					initBoard(extra);
					System.out.println("What would you like to do - P2?");
					System.out.println("1. Roll");
					System.out.println("2. Manage Properties");
					temp = input.nextInt();
					if(temp==1)
					{
						p2roll = PlayerTwoRoll();
						x[p2location] = " ";
						p2location += p2roll;
						if(p2location>=40)
						{
							p2location-=39;
							p2money+=200;
						}
						x[p2location] = "O";
						printBoard(x,extra);
					}
					else
					{
						moneychange = manage(2,p2prop,properties);
						p2money+=moneychange;
					}
					if(Integer.parseInt(properties[p2location][2])==0)
					{
						System.out.println("What would you like to do - P2?");
						System.out.println("1. Buy");
						System.out.println("2. Don't buy");
						temp = input.nextInt();
						if(temp==1)
						{
							p2prop.add(properties[p2location][0]);
							p2money-=Integer.parseInt(properties[p2location][3]);
							properties[p2location][2] = "2";
						}
					}
					bankrupt = bankruptCheck(p1money,p2money,2,p1prop,p2prop,properties);
					p1money = bankrupt[0];
					p2money = bankrupt[1];
					if(properties[p2location][1].equals("Chance"))
					{
						chance = chance(p2location);
						if(chance[0]==50)
							p2money+=50;
						else if(chance[0]==10)
							getOutOfJailp2 = true;
						else
						{
							x[p2location] = " ";
							if(p2location<chance[1])
								p2location = chance[1];
							else
							{
								p2money+=200;
								p2location = chance[1];
							}
						}

					}
				}
				if(properties[p2location][1].equals("Go To Jail")||(p2jail==true&&properties[p2location][1].equals("Jail")))
				{
					System.out.println("What would you like to do");
					System.out.println("1. Roll");
					System.out.println("2. Trade");
					System.out.println("3. Manage Properties");
					System.out.println("4. Pay");
					p2location = 30;
					if(getOutOfJailp2==true)
					{
						System.out.println("5. Use chance card");
					}
					temp = input.nextInt();
					if(numOfJailp2==2)
					{
						System.out.println("You pay $50");
						p2money-=50;
						p2jail = false;
						numOfJailp2 = 0;
						p2roll = PlayerTwoRoll();
						x[p2location] = " ";
						p2location += p1roll;
						if(p2location>=40)
						{
							p2location-=39;
							p2money+=200;
						}
						x[p2location] = "O";
						printBoard(x,extra);
					}
					else if(temp==1)
					{
						System.out.println("Enter R to roll");
						String s = input.nextLine();
						oneroll = (int)(Math.random() * 6)+1;
						tworoll = (int)(Math.random() * 6)+1;
						if(oneroll==tworoll)
						{
							p2jail = false;
							numOfJailp2 = 0;
						}
						else
							numOfJailp2++;

					}
					else if(temp==2)
					{
						trade = trade(2,p1money,p2money,p1prop,p2prop,properties);
						p1money=trade[0];
						p2money=trade[1];
					}
					else if(temp==3)
					{
						moneychange = manage(2,p1prop,properties);
						p2money+=moneychange;
					}
					else if(temp==4)
					{
						System.out.println("You pay $50");
						p2money-=50;
						p2jail = false;
						numOfJailp2 = 0;
						p2roll = PlayerTwoRoll();
						x[p2location] = " ";
						p2location += p1roll;
						if(p1location>=40)
						{
							p2location-=39;
							p2money+=200;
						}
						x[p2location] = "O";
						printBoard(x,extra);
					}
					else if(temp==5)
					{
						p2jail = false;
						numOfJailp2 = 0;
					}
				}
				if(p2jail!=true)
				{
					propAction = propAction(properties,2,p1location,p1money,p2money,p1roll,p2roll);
					p1money = propAction[0];
					p2money = propAction[1];
					System.out.println("P2 Money - $"+p2money);
					System.out.println("What would you like to do - P2?");
					System.out.println("1. End Turn");
					System.out.println("2. Trade");
					System.out.println("3. Manage Properties");
					temp = input.nextInt();
					if(temp==2)
					{
						trade = trade(2,p1money,p2money,p1prop,p2prop,properties);
						p1money=trade[0];
						p2money=trade[1];
					}
					else if(temp==3)
					{
						moneychange = manage(2,p1prop,properties);
						p1money+=moneychange;
					}
				}
			}
			else
			{
				
				//Player 2
				if(p2jail!=true)
				{
					initBoard(extra);
					System.out.println("What would you like to do - P2?");
					System.out.println("1. Roll");
					System.out.println("2. Manage Properties");
					temp = input.nextInt();
					if(temp==1)
					{
						p2roll = PlayerTwoRoll();
						x[p2location] = " ";
						p2location += p2roll;
						if(p2location>=40)
						{
							p2location-=39;
							p2money+=200;
						}
						x[p2location] = "O";
						printBoard(x,extra);
					}
					else
					{
						moneychange = manage(2,p2prop,properties);
						p2money+=moneychange;
					}
					if(Integer.parseInt(properties[p2location][2])==0)
					{
						System.out.println("What would you like to do - P2?");
						System.out.println("1. Buy");
						System.out.println("2. Don't buy");
						temp = input.nextInt();
						if(temp==1)
						{
							p2prop.add(properties[p2location][0]);
							p2money-=Integer.parseInt(properties[p2location][3]);
							properties[p2location][2] = "2";
						}
					}
					bankrupt = bankruptCheck(p1money,p2money,2,p1prop,p2prop,properties);
					p1money = bankrupt[0];
					p2money = bankrupt[1];
					if(properties[p2location][1].equals("Chance"))
					{
						chance = chance(p1location);
						if(chance[0]==50)
							p2money+=50;
						else if(chance[0]==10)
							getOutOfJailp2 = true;
						else
						{
							x[p2location] = " ";
							if(p2location<chance[1])
								p2location = chance[1];
							else
							{
								p2money+=200;
								p2location = chance[1];
							}
						}

					}
				}
				if(properties[p2location][1].equals("Go To Jail")||(p2jail==true&&properties[p2location][1].equals("Jail")))
				{
					System.out.println("What would you like to do");
					System.out.println("1. Roll");
					System.out.println("2. Trade");
					System.out.println("3. Manage Properties");
					System.out.println("4. Pay");
					p2location = 30;
					if(getOutOfJailp2==true)
					{
						System.out.println("5. Use chance card");
					}
					temp = input.nextInt();
					if(numOfJailp2==2)
					{
						System.out.println("You pay $50");
						p2money-=50;
						p2jail = false;
						numOfJailp2 = 0;
						p2roll = PlayerTwoRoll();
						x[p2location] = " ";
						p2location += p1roll;
						if(p2location>=40)
						{
							p2location-=39;
							p2money+=200;
						}
						x[p2location] = "O";
						printBoard(x,extra);
					}
					else if(temp==1)
					{
						System.out.println("Enter R to roll");
						String s = input.nextLine();
						oneroll = (int)(Math.random() * 6)+1;
						tworoll = (int)(Math.random() * 6)+1;
						if(oneroll==tworoll)
						{
							p2jail = false;
							numOfJailp2 = 0;
						}
						else
							numOfJailp2++;

					}
					else if(temp==2)
					{
						trade = trade(2,p1money,p2money,p1prop,p2prop,properties);
						p1money=trade[0];
						p2money=trade[1];
					}
					else if(temp==3)
					{
						moneychange = manage(2,p1prop,properties);
						p2money+=moneychange;
					}
					else if(temp==4)
					{
						System.out.println("You pay $50");
						p2money-=50;
						p2jail = false;
						numOfJailp2 = 0;
						p2roll = PlayerTwoRoll();
						x[p2location] = " ";
						p2location += p1roll;
						if(p1location>=40)
						{
							p2location-=39;
							p2money+=200;
						}
						x[p2location] = "O";
						printBoard(x,extra);
					}
					else if(temp==5)
					{
						p2jail = false;
						numOfJailp2 = 0;
					}
				}
				if(p2jail!=true)
				{
					propAction = propAction(properties,2,p1location,p1money,p2money,p1roll,p2roll);
					p1money = propAction[0];
					p2money = propAction[1];
					System.out.println("P2 Money - $"+p2money);
					System.out.println("What would you like to do - P2?");
					System.out.println("1. End Turn");
					System.out.println("2. Trade");
					System.out.println("3. Manage Properties");
					temp = input.nextInt();
					if(temp==2)
					{
						trade = trade(2,p1money,p2money,p1prop,p2prop,properties);
						p1money=trade[0];
						p2money=trade[1];
					}
					else if(temp==3)
					{
						moneychange = manage(2,p1prop,properties);
						p1money+=moneychange;
					}
				}
				//Player 1
				if(p1jail!=true)
				{
					initBoard(extra);
					System.out.println("What would you like to do - P1?");
					System.out.println("1. Roll");
					System.out.println("2. Manage Properties");
					temp = input.nextInt();
					if(temp==1)
					{
						p1roll = PlayerOneRoll();
						x[p1location] = " ";
						p1location += p1roll;
						if(p1location>=40)
						{
							p1location-=39;
							p1money+=200;
						}
						x[p1location] = "X";
						printBoard(x,extra);
					}
					else
					{
						moneychange = manage(1,p1prop,properties);
						p1money+=moneychange;
					}
					if(Integer.parseInt(properties[p1location][2])==0)
					{
						System.out.println("What would you like to do - P1?");
						System.out.println("1. Buy");
						System.out.println("2. Don't buy");
						temp = input.nextInt();
						if(temp==1)
						{
							p1prop.add(properties[p1location][0]);
							p1money-=Integer.parseInt(properties[p1location][3]);
							properties[p1location][2] = "1";
						}
					}
					bankrupt = bankruptCheck(p1money,p2money,1,p1prop,p2prop,properties);
					p1money = bankrupt[0];
					p2money = bankrupt[1];
					if(properties[p1location][1].equals("Chance"))
					{
						chance = chance(p1location);
						if(chance[0]==50)
							p1money+=50;
						else if(chance[0]==10)
							getOutOfJailp1 = true;
						else
						{
							x[p1location] = " ";
							if(p1location<chance[1])
								p1location = chance[1];
							else
							{
								p1money+=200;
								p1location = chance[1];
							}
						}

					}
				}
				if(properties[p1location][1].equals("Go To Jail")||(p1jail==true&&properties[p1location][1].equals("Jail")))
				{
					System.out.println("What would you like to do");
					System.out.println("1. Roll");
					System.out.println("2. Trade");
					System.out.println("3. Manage Properties");
					System.out.println("4. Pay");
					p1location = 30;
					if(getOutOfJailp1==true)
					{
						System.out.println("5. Use chance card");
					}
					temp = input.nextInt();
					if(numOfJailp1==2)
					{
						System.out.println("You pay $50");
						p1money-=50;
						p1jail = false;
						numOfJailp1 = 0;
						p1roll = PlayerOneRoll();
						x[p1location] = " ";
						p1location += p1roll;
						if(p1location>=40)
						{
							p1location-=39;
							p1money+=200;
						}
						x[p1location] = "X";
						printBoard(x,extra);
					}
					else if(temp==1)
					{
						System.out.println("Enter R to roll");
						String s = input.nextLine();
						oneroll = (int)(Math.random() * 6)+1;
						tworoll = (int)(Math.random() * 6)+1;
						if(oneroll==tworoll)
						{
							p1jail = false;
							numOfJailp1 = 0;
						}
						else
							numOfJailp1++;

					}
					else if(temp==2)
					{
						trade = trade(1,p1money,p2money,p1prop,p2prop,properties);
						p1money=trade[0];
						p2money=trade[1];
					}
					else if(temp==3)
					{
						moneychange = manage(1,p1prop,properties);
						p1money+=moneychange;
					}
					else if(temp==4)
					{
						System.out.println("You pay $50");
						p1money-=50;
						p1jail = false;
						numOfJailp1 = 0;
						p1roll = PlayerOneRoll();
						x[p1location] = " ";
						p1location += p1roll;
						if(p1location>=40)
						{
							p1location-=39;
							p1money+=200;
						}
						x[p1location] = "X";
						printBoard(x,extra);
					}
					else if(temp==5)
					{
						p1jail = false;
						numOfJailp1 = 0;
					}
				}
				if(p1jail!=true)
				{
					propAction = propAction(properties,1,p1location,p1money,p2money,p1roll,p2roll);
					p1money = propAction[0];
					p2money = propAction[1];
					System.out.println("P1 Money - $"+p1money);
					System.out.println("What would you like to do - P1?");
					System.out.println("1. End Turn");
					System.out.println("2. Trade");
					System.out.println("3. Manage Properties");
					temp = input.nextInt();
					if(temp==2)
					{
						trade = trade(1,p1money,p2money,p1prop,p2prop,properties);
						p1money=trade[0];
						p2money=trade[1];
					}
					else if(temp==3)
					{
						moneychange = manage(1,p1prop,properties);
						p1money+=moneychange;
					}
				}
			}
		}
	}
	public static int[] propAction(String[][] arr,int playernum,int playerposition,int p1money,int p2money,int p1roll,int p2roll)
	{
		//rent
		//chance
		//jail
		int moneychange1 = 0;
		int moneychange2 = 0;
		int[] moneychange = new int[2];
		if(arr[playerposition][1].equals("Property"))
		{
			if(playernum==1)
			{
				if(arr[playerposition][2].equals("2"))
				{
					if(arr[playerposition][12].equals(""))
					{
						moneychange1 -= Integer.parseInt(arr[playerposition][5]);
						moneychange2 += Integer.parseInt(arr[playerposition][5]);
					}
					else if(arr[playerposition][12].equals("X"))
					{
						moneychange1 -= Integer.parseInt(arr[playerposition][6]);
						moneychange2 += Integer.parseInt(arr[playerposition][6]);
					}
					else if(arr[playerposition][12].equals("XX"))
					{
						moneychange1 -= Integer.parseInt(arr[playerposition][7]);
						moneychange2 += Integer.parseInt(arr[playerposition][7]);
					}
					else if(arr[playerposition][12].equals("XXX"))
					{
						moneychange1 -= Integer.parseInt(arr[playerposition][8]);
						moneychange2 += Integer.parseInt(arr[playerposition][8]);
					}
					else if(arr[playerposition][12].equals("XXXX"))
					{
						moneychange1 -= Integer.parseInt(arr[playerposition][9]);
						moneychange2 += Integer.parseInt(arr[playerposition][9]);
					}
					else if(arr[playerposition][12].equals("XXXXX"))
					{
						moneychange1 -= Integer.parseInt(arr[playerposition][10]);
						moneychange2 += Integer.parseInt(arr[playerposition][10]);
					}
					System.out.println("You paid $"+Math.abs(moneychange1));
				}
			}
			else if(playernum==2)
			{
				if(arr[playerposition][2].equals("1"))
				{
					if(arr[playerposition][12].equals(""))
					{
						moneychange2 -= Integer.parseInt(arr[playerposition][5]);
						moneychange1 += Integer.parseInt(arr[playerposition][5]);
					}
					else if(arr[playerposition][12].equals("X"))
					{
						moneychange2 -= Integer.parseInt(arr[playerposition][6]);
						moneychange1 += Integer.parseInt(arr[playerposition][6]);
					}
					else if(arr[playerposition][12].equals("XX"))
					{
						moneychange2 -= Integer.parseInt(arr[playerposition][7]);
						moneychange1 += Integer.parseInt(arr[playerposition][7]);
					}
					else if(arr[playerposition][12].equals("XXX"))
					{
						moneychange2 -= Integer.parseInt(arr[playerposition][8]);
						moneychange1 += Integer.parseInt(arr[playerposition][8]);
					}
					else if(arr[playerposition][12].equals("XXXX"))
					{
						moneychange2 -= Integer.parseInt(arr[playerposition][9]);
						moneychange1 += Integer.parseInt(arr[playerposition][9]);
					}
					else if(arr[playerposition][12].equals("XXXXX"))
					{
						moneychange2 -= Integer.parseInt(arr[playerposition][10]);
						moneychange1 += Integer.parseInt(arr[playerposition][10]);
					}
					System.out.println("You paid $"+Math.abs(moneychange1));
				}
			}
		}
		else if(arr[playerposition][1].equals("Railroad"))
		{
			int numOfRailroads = 0;
			if(playernum==1)
			{
				if(arr[playerposition][2].equals("2"))
				{
					for(int i = 0;i<arr.length;i++)
					{
						if(arr[i][1].equals("Railroad"))
						{
							if(arr[i][2].equals("2"))
								numOfRailroads++;
						}
					}
					if(numOfRailroads==1)
					{
						moneychange1 -= 25;
						moneychange2 += 25;
					}
					else if(numOfRailroads==2)
					{
						moneychange1 -= 50;
						moneychange2 += 50;
					}
					else if(numOfRailroads==3)
					{
						moneychange1 -= 100;
						moneychange2 += 100;
					}
					else if(numOfRailroads==4)
					{
						moneychange1 -= 200;
						moneychange2 += 200;
					}
					System.out.println("You paid $"+Math.abs(moneychange1));
				}
			}
			else if(playernum==2)
			{
				if(arr[playerposition][2].equals("1"))
				{
					for(int i = 0;i<arr.length;i++)
					{
						if(arr[i][1].equals("Railroad"))
						{
							if(arr[i][2].equals("1"))
								numOfRailroads++;
						}
					}
					if(numOfRailroads==1)
					{
						moneychange1 -= 25;
						moneychange2 += 25;
					}
					else if(numOfRailroads==2)
					{
						moneychange1 -= 50;
						moneychange2 += 50;
					}
					else if(numOfRailroads==3)
					{
						moneychange1 -= 100;
						moneychange2 += 100;
					}
					else if(numOfRailroads==4)
					{
						moneychange1 -= 200;
						moneychange2 += 200;
					}
					System.out.println("You paid $"+Math.abs(moneychange1));
				}
			}
		}
		else if(arr[playerposition][1].equals("Utility"))
		{
			int numOfUtilities = 0;
			if(playernum==1)
			{
				if(arr[playerposition][2].equals("2"))
				{
					for(int i = 0;i<arr.length;i++)
					{
						if(arr[i][1].equals("Utility"))
						{
							if(arr[i][2].equals("2"))
								numOfUtilities++;
						}
					}
					if(numOfUtilities==1)
					{
						moneychange1 -= 4*p1roll;
						moneychange2 += 4*p1roll;
					}
					else if(numOfUtilities==2)
					{
						moneychange1 -= 10*p1roll;
						moneychange2 += 10*p1roll;
					}
					System.out.println("You paid $"+Math.abs(moneychange1));
				}
			}
			else if(playernum==2)
			{
				if(arr[playerposition][2].equals("1"))
				{
					for(int i = 0;i<arr.length;i++)
					{
						if(arr[i][1].equals("Utility"))
						{
							if(arr[i][2].equals("1"))
								numOfUtilities++;
						}
					}
					if(numOfUtilities==1)
					{
						moneychange1 -= 4*p2roll;
						moneychange2 += 4*p2roll;
					}
					else if(numOfUtilities==2)
					{
						moneychange1 -= 10*p2roll;
						moneychange2 += 10*p2roll;
					}
					System.out.println("You paid $"+Math.abs(moneychange1));
				}
			}
		}
		else if(arr[playerposition][1].equals("Tax"))
		{
			if(playernum==1)
			{
				moneychange1-= Integer.parseInt(arr[playerposition][3]);
			}
			else
				moneychange2 -= Integer.parseInt(arr[playerposition][3]);
			System.out.println("You paid $"+moneychange1);
		}
		p1money+= moneychange1;
		p2money+= moneychange2;


		int[] x = {p1money,p2money};
		return x;
	}
	public static int[] chance(int playerposition)
	{
		String[][] chance = new String[7][6];
		chance[0][0] = "Advance to GO : Collect $200";chance[0][1] = "200";chance[0][2] = "0";
		chance[1][0] = "Advance to Illinois Avenue : Collect $200 if you pass GO";chance[1][1] = "200";chance[1][2] = "24";
		chance[2][0] = "Advance to St. Charles Place : Collect $200 if you pass GO";chance[2][1] = "200";chance[2][2] = "11";
		chance[3][0] = "Advance to a random Utility : Collect $200 if you pass GO";chance[3][1] = "200";chance[3][2] = "12";chance[3][3] = "28";
		chance[4][0] = "Advance to a random Railroad : Collect $200 if you pass GO";chance[4][1] = "200";chance[4][2] = "15";chance[4][3] = "5";chance[4][4] = "35";chance[4][5] = "25";
		chance[5][0] = "Bank pays dividend of 50";chance[5][1] = "50";chance[5][2] = "0";
		chance[6][0] = "Get a GET OUT OF JAIL FREE CARD";chance[6][1] = "10";chance[6][2] = playerposition+"";
		int roll = (int)(Math.random() * 6);
		int[] chanceReturn = new int[2];
		if(roll==3)
		{
			int threeroll = (int)(Math.random() * 3) + 2;
			chanceReturn[1] = Integer.parseInt(chance[3][threeroll]);
		}
		else if(roll==4)
		{
			int fourroll = (int)(Math.random() * 5) + 2;
			chanceReturn[1] = Integer.parseInt(chance[4][fourroll]);
		}
		else
		{
			chanceReturn[1] = Integer.parseInt(chance[roll][2]);
		}
		System.out.println(chance[roll][0]);
		chanceReturn[0] = Integer.parseInt(chance[roll][1]);
		return chanceReturn;



	}
	public static boolean initRoll()
	{
		Scanner input = new Scanner(System.in);
		System.out.println("Determine who starts: ");
		int p1roll = PlayerOneRoll();
		int p2roll = PlayerTwoRoll();
		while(p1roll==p2roll)
		{
			System.out.println("It's a tie so reroll");
			p1roll = PlayerOneRoll();
			p2roll = PlayerTwoRoll();
		}
		if(p1roll>p2roll)
		{
			System.out.println("Player one goes first");
			return true;
		}
		else
		{
			System.out.println("Player two goes first");
			return false;
		}

	}
	public static int roll()
	{
		return ((int)(Math.random() * 6) + 1)+((int)(Math.random() * 6) + 1);
	}
	public static int PlayerOneRoll()
	{
		Scanner input = new Scanner(System.in);
		System.out.println("Player One - Enter R to roll");
		String s = input.nextLine();
		int roll = roll();
		System.out.println("You rolled a "+roll);
		return roll;
	}
	public static int PlayerTwoRoll()
	{
		Scanner input = new Scanner(System.in);
		System.out.println("Player Two - Enter R to roll");
		String s = input.nextLine();
		int roll = roll();
		System.out.println("You rolled a "+roll);
		return roll;
	}
	public static void initBoard(String[] x)
	{
		for(int i = 0;i<x.length;i++)
			x[i] = " ";
	}
	public static int[] trade(int playernum,int p1money,int p2money,ArrayList<String> p1prop,ArrayList<String> p2prop,String[][] prop)
	{
		
		Scanner input = new Scanner(System.in);
		int[] wanted = new int[p2prop.size()];
		int[] givenprop = new int[p1prop.size()];
		int moneywanted = 0;
		int temp = 0;
		String tempStr = "";
		int z = 1;
		int[] money = new int[2];
		int moneygiven = 0;
		while(z!=0)
		{
			System.out.println("Enter what you want from the other player(12 for both)");
			System.out.println("1. Money");
			System.out.println("2. Properties");
			temp = input.nextInt();
			moneywanted = 0;
			moneygiven = 0;
			if(playernum==1)
			{
				wanted = new int[p2prop.size()];
				givenprop = new int[p1prop.size()];
			}
			else
			{
				wanted = new int[p1prop.size()];
				givenprop = new int[p2prop.size()];
			}
			money = new int[2];
			System.out.println("P1 Money $"+p1money);
			System.out.println("P2 Money $"+p2money);
			if(temp==1)
			{
				System.out.println("Enter the amount of money you want:");
				moneywanted = input.nextInt();
			}
			else if(temp==12)
			{
				System.out.println("Enter the amount of money you want:");
				moneywanted = input.nextInt();

				System.out.println("Enter the properties you want:");
				System.out.println("(For multiple, input one and hit enter)");
				if(playernum==1)
				{
					for(int i = 0;i<wanted.length;i++)
					System.out.println(i+1+". "+p2prop.get(i));
					System.out.println("Or 0 to exit");
					for(int i = 0;i<wanted.length;i++)
					{
						temp = input.nextInt();
						if(temp==0)
							i = p2prop.size();
						else
							wanted[i] = temp-1;
					}
				}
				else
				{
					for(int i = 0;i<wanted.length;i++)
					System.out.println(i+1+". "+p1prop.get(i));
					System.out.println("Or 0 to exit");
					for(int i = 0;i<wanted.length;i++)
					{
						temp = input.nextInt();
						if(temp==0)
							i = p1prop.size();
						else
							wanted[i] = temp-1;
					}
				}
			}
			else
			{
				System.out.println("Enter the properties you want:");
				System.out.println("(For multiple, input one and hit enter)");
				if(playernum==1)
				{
					for(int i = 0;i<wanted.length;i++)
					System.out.println(i+1+". "+p2prop.get(i));
					System.out.println("Or 0 to exit");
					for(int i = 0;i<wanted.length;i++)
					{
						temp = input.nextInt();
						if(temp==0)
							i = p2prop.size();
						else
							wanted[i] = temp-1;
					}
				}
				else
				{
					for(int i = 0;i<wanted.length;i++)
					System.out.println(i+1+". "+p1prop.get(i));
					System.out.println("Or 0 to exit");
					for(int i = 0;i<wanted.length;i++)
					{
						temp = input.nextInt();
						if(temp==0)
							i = p1prop.size();
						else
							wanted[i] = temp-1;
					}
				}

			}
			System.out.println("Enter what you to give to the other player(12 for both)");
			System.out.println("1. Money");
			System.out.println("2. Properties");
			input.nextLine();
			temp = input.nextInt();
			if(temp==12)
			{
				System.out.println("Enter the amount of money you want to give:");
				moneygiven = input.nextInt();
				System.out.println("Enter the properties you want to give:");
				System.out.println("(For multiple, input one and hit enter)");
				if(playernum==1)
				{
					for(int i = 0;i<wanted.length;i++)
						System.out.println(i+1+". "+p1prop.get(i));
					System.out.println("Or 0 to exit");
					for(int i = 0;i<wanted.length;i++)
					{
						temp = input.nextInt();
						if(temp==0)
							i = p1prop.size();
						else
							wanted[i] = temp-1;
					}
				}
				else
				{
					for(int i = 0;i<wanted.length;i++)
						System.out.println(i+1+". "+p2prop.get(i));
					System.out.println("Or 0 to exit");
					for(int i = 0;i<wanted.length;i++)
					{
						temp = input.nextInt();
						if(temp==0)
							i = p1prop.size();
						else
							wanted[i] = temp-1;
					}
				}
			}
			else if(temp==1)
			{
				System.out.println("Enter the amount of money you want to give:");
				moneygiven = input.nextInt();
			}
			else
			{
				System.out.println("Enter the properties you want to give:");
				System.out.println("(For multiple, input one and hit enter)");
				if(playernum==1)
				{
					for(int i = 0;i<wanted.length;i++)
						System.out.println(i+1+". "+p2prop.get(i));
					System.out.println("Or 0 to exit");
					for(int i = 0;i<wanted.length;i++)
					{
						temp = input.nextInt();
						if(temp==0)
							i = p2prop.size();
						else
							wanted[i] = temp-1;
					}
				}
				else
				{
					for(int i = 0;i<wanted.length;i++)
					System.out.println(i+1+". "+p1prop.get(i));
					System.out.println("Or 0 to exit");
					for(int i = 0;i<wanted.length;i++)
					{
						temp = input.nextInt();
						if(temp==0)
							i = p1prop.size();
						else
							wanted[i] = temp-1;
					}
				}

			}
			if(playernum==1)
				System.out.println("Player Two, do you accept(Y or N)?");
			else
				System.out.println("Player One, do you accept(Y or N)?");
			input.nextLine();
			tempStr = input.nextLine();
			if(tempStr.equals("Y"))
			{
				for(int i = 0;i<wanted.length;i++)
				{
					if(playernum==1)
						p1prop.add(p2prop.get(wanted[i]));
					else
						p2prop.add(p1prop.get(wanted[i]));
				}
				for(int i = 0;i<wanted.length;i++)
				{
					
					if(playernum==1)
					{
						p2prop.remove(wanted[i]);
						p2prop.add(wanted[i],"0");
					}
					else
					{
						p1prop.remove(wanted[i]);
						p1prop.add(wanted[i],"0");
					}
				}
				for(int i = 0;i<givenprop.length;i++)
				{
					if(playernum==1)
						p2prop.add(p1prop.get(givenprop[i]));
					else
						p1prop.add(p2prop.get(givenprop[i]));
				}
				for(int i = 0;i<givenprop.length;i++)
				{
					if(playernum==1)
					{
						p1prop.remove(wanted[i]);
						p1prop.add(wanted[i],"0");
					}
					else
					{
						p2prop.remove(wanted[i]);
						p2prop.add(wanted[i],"0");
					}
				}
				if(playernum==1)
				{
					money[0] = p1money - moneygiven+moneywanted;
					money[1] = p2money + moneygiven-moneywanted;
				}
				else
				{
					money[0] = moneywanted;
					money[1] = moneygiven;
				}
				for(int i = 0;i<p1prop.size();i++)
				{
					if(Integer.parseInt(p1prop.get(i))==0)
						p1prop.remove(i);
				}
				for(int i = 0;i<p2prop.size();i++)
				{
					if(Integer.parseInt(p2prop.get(i))==0)
						p2prop.remove(i);
				}
			}
			System.out.println("P1 Money $"+p1money);
			System.out.println("P2 Money $"+p2money);
			System.out.println("P1 Properties");
			for(int i = 0;i<p1prop.size();i++)
				System.out.println(i+". "+p1prop.get(i));
			System.out.println("P2 Properties");
			for(int i = 0;i<p2prop.size();i++)
				System.out.println(i+". "+p2prop.get(i));
			System.out.println("Would you like to trade again?(Y or N)");
			tempStr = input.nextLine();
			if(tempStr.equals("N"))
				z = 0;
		}
			
		
		return money;
	}
	public static int[] bankruptCheck(int p1money,int p2money,int playernum,ArrayList<String> p1prop,ArrayList<String> p2prop,String[][] arr)
	{
		Scanner input = new Scanner(System.in);
		int temp = 0;
		int[] trade = new int[2];
		while(p1money<0||p2money<0)
		{
			System.out.println("WARNING: YOU ARE AT RISK OF GOING BANKRUPT");
			System.out.println("Choose what you want to do: ");
			System.out.println("1. Manage your properties");
			System.out.println("2. Trade");
			System.out.println("3. Pull the switch and go bankrupt :(");
			temp = input.nextInt();
			if(temp==1)
			{
				if(playernum==1)
					p1money += manage(playernum,p1prop,arr);
				else if(playernum==2)
					p2money += manage(playernum,p2prop,arr);
			}
			else if(temp==2)
			{
				trade = trade(playernum,p1money,p2money,p1prop,p2prop,arr);
				p1money =trade[0];
				p2money =trade[1];			
			}
			else
			{
				if(playernum==1)
					System.out.println("Player 2 Wins");
				else
					System.out.println("Player 1 Wins");
				System.exit(0);
			}

		}
		int[] x = {p1money,p2money};
		return x;
	}
	public static int manage(int playernum,ArrayList<String> prop,String[][] arr)
	{
		Scanner input = new Scanner(System.in);
		int money = 0;
		int z = 1;
		int temp = 0;
		int pprop = 0;
		int xyz = 1;
		boolean exit = false;
		if(prop.size()==0)
		{
			System.out.println("Bro, you have no properties");
			return 0;
		}
		while(z!=0)
		{
			System.out.println("Choose your property: ");
			for(int i = 0;i<prop.size();i++)
			{
				System.out.println(i+1+". "+prop.get(i));
			}
			pprop = input.nextInt();
			pprop--;
			System.out.println("What would you like to do?");
			System.out.println("1. Mortgage");
			System.out.println("2. Upgrade with Houses or Hotels");
			System.out.println("3. Remove your Houses or Hotels");
			for(int i = 0;i<arr.length;i++)
			{
				if(prop.get(pprop).equals(arr[i][0]))
				{
					if(arr[i][12].equals("")&&temp==3)
					{
						System.out.println("You can't downgrade");
						 z = 30;
					}
					else if(arr[i][12].equals("XXXXX")&&temp==2)
					{
						System.out.println("You can't upgrade");
						z = 30;
					}
					else if(!arr[i][1].equals("Property"))
					{
						System.out.println("You can't upgrade or downgrade");
						System.out.println("(Why you wanna add or remove houses from a railroad or utility?)");
						z = 30;
					}
				}
			}
			temp = input.nextInt();

			if(temp==1)
			{
				for(int i = 0;i<arr.length;i++)
				{
					if(prop.get(pprop).equals(arr[i][0]))
					{
						if(arr[i][11].equals("X"))
						{
							System.out.println("It's already mortgaged");
							System.out.println("Would you like to unmortgage?");
							System.out.println("0 - Yes; 1 - No");
							temp = input.nextInt();
							if(temp==0)
							{
								money -= Integer.parseInt(arr[i][3])/2;
							}

						}
						else if(!arr[i][12].equals(""))
							System.out.println("Remove your houses first");
						else
						{
							arr[i][11] = "X";
							money += Integer.parseInt(arr[i][3])/2;
						}

					}
				}
			}
			else if(z==30)
			{
				System.out.print("");
			}
			else if(temp==2)
			{
				for(int i = 0;i<arr.length;i++)
				{
					if(prop.get(pprop).equals(arr[i][0]))
					{
						System.out.println("Your houses are here: "+arr[i][12]);
						System.out.println("Enter whether or not you would like to add a house");
						System.out.println("0 - Yes;1 - No");
						temp = input.nextInt();
						if(temp==1)
							System.out.print("");
						else if(arr[i][12].equals(""))
						{
							for(int j = 0;j<arr.length;j++)
							{
								if(arr[i][13].equals(arr[j][13])&&arr[j][12].equals("")||arr[i][13].equals(arr[j][13])&&arr[j][12].equals("X"))
									System.out.print("");
								else
								{
									System.out.println("You need to build evenly");
									exit = true;
								}
							}
							if(exit==false)
							{
								arr[i][4]+="X";
								money -= Integer.parseInt(arr[i][4]);
							}
						}
						else if(arr[i][12].equals("X"))
						{
							for(int j = 0;j<arr.length;j++)
							{
								if( (arr[i][13].equals(arr[j][13])&&arr[j][12].equals("")) || (arr[i][13].equals(arr[j][13])&&arr[j][12].equals("X")) || (arr[i][13].equals(arr[j][13])&&arr[j][12].equals("XX")))
									System.out.print("");
								else
								{
									System.out.println("You need to build evenly");
									exit = true;
								}
							}
							if(exit==false)
							{
								arr[i][4]+="X";
								money -= Integer.parseInt(arr[i][4]);
							}
						}
						else if(arr[i][12].equals("XX"))
						{
							for(int j = 0;j<arr.length;j++)
							{
								if( (arr[i][13].equals(arr[j][13])&&arr[j][12].equals("X")) || (arr[i][13].equals(arr[j][13])&&arr[j][12].equals("XX")) || (arr[i][13].equals(arr[j][13])&&arr[j][12].equals("XXX")))
									System.out.print("");
								else
								{
									System.out.println("You need to build evenly");
									exit = true;
								}
							}
							if(exit==false)
							{
								arr[i][4]+="X";
								money -= Integer.parseInt(arr[i][4]);
							}
						}
						else if(arr[i][12].equals("XXX"))
						{
							for(int j = 0;j<arr.length;j++)
							{
								if( (arr[i][13].equals(arr[j][13])&&arr[j][12].equals("XX")) || (arr[i][13].equals(arr[j][13])&&arr[j][12].equals("XXX")) || (arr[i][13].equals(arr[j][13])&&arr[j][12].equals("XXXX")) )
									System.out.print("");
								else
								{
									System.out.println("You need to build evenly");
									exit = true;
								}
							}
							if(exit==false)
							{
								arr[i][4]+="X";
								money -= Integer.parseInt(arr[i][4]);
							}
						}
						else if(arr[i][12].equals("XXXX"))
						{
							for(int j = 0;j<arr.length;j++)
							{
								if( (arr[i][13].equals(arr[j][13])&&arr[j][12].equals("XXX")) || (arr[i][13].equals(arr[j][13])&&arr[j][12].equals("XXXX")) || (arr[i][13].equals(arr[j][13])&&arr[j][12].equals("XXXXX")) )
									System.out.print("");
								else
								{
									System.out.println("You need to build evenly");
									exit = true;
								}
							}
							if(exit==false)
							{
								arr[i][4]+="X";
								money -= Integer.parseInt(arr[i][4]);
							}
						}
						else if(arr[i][12].equals("XXXXX"))
						{
							for(int j = 0;j<arr.length;j++)
							{
								if( (arr[i][13].equals(arr[j][13])&&arr[j][12].equals("XXXX")) || (arr[i][13].equals(arr[j][13])&&arr[j][12].equals("XXXXX")) )
									System.out.print("");
								else
								{
									System.out.println("You need to build evenly");
									exit = true;
								}
							}
							if(exit==false)
							{
								arr[i][4]+="X";
								money -= Integer.parseInt(arr[i][4]);
							}
						}
					}
				}
			}
			else if(temp==3)
			{
				for(int i = 0;i<arr.length;i++)
				{
					if(prop.get(pprop).equals(arr[i][0]))
					{
						System.out.println("Your houses are here: "+arr[i][12]);
						System.out.println("Enter whether or not you would like to remove a house");
						System.out.println("0 - Yes;1 - No");
						temp = input.nextInt();
						if(temp==1)
							System.out.print("");
						else if(arr[i][12].equals("X"))
						{
							for(int j = 0;j<arr.length;j++)
							{
								if( (arr[i][13].equals(arr[j][13])&&arr[j][12].equals("")) || (arr[i][13].equals(arr[j][13])&&arr[j][12].equals("X")) || (arr[i][13].equals(arr[j][13])&&arr[j][12].equals("XX")))
									System.out.print("");
								else
								{
									System.out.println("You need to build evenly");
									exit = true;
								}
							}
							if(exit==false)
							{
								arr[i][4] = arr[i][4].substring(0,arr[i][4].length()-1);
								money += Integer.parseInt(arr[i][4])/2;
							}
						}
						else if(arr[i][12].equals("XX"))
						{
							for(int j = 0;j<arr.length;j++)
							{
								if( (arr[i][13].equals(arr[j][13])&&arr[j][12].equals("X")) || (arr[i][13].equals(arr[j][13])&&arr[j][12].equals("XX")) || (arr[i][13].equals(arr[j][13])&&arr[j][12].equals("XXX")))
									System.out.print("");
								else
								{
									System.out.println("You need to build evenly");
									exit = true;
								}
							}
							if(exit==false)
							{
								arr[i][4] = arr[i][4].substring(0,arr[i][4].length()-1);
								money += Integer.parseInt(arr[i][4])/2;
							}
						}
						else if(arr[i][12].equals("XXX"))
						{
							for(int j = 0;j<arr.length;j++)
							{
								if( (arr[i][13].equals(arr[j][13])&&arr[j][12].equals("XX")) || (arr[i][13].equals(arr[j][13])&&arr[j][12].equals("XXX")) || (arr[i][13].equals(arr[j][13])&&arr[j][12].equals("XXXX")) )
									System.out.print("");
								else
								{
									System.out.println("You need to build evenly");
									exit = true;
								}
							}
							if(exit==false)
							{
								arr[i][4] = arr[i][4].substring(0,arr[i][4].length()-1);
								money += Integer.parseInt(arr[i][4])/2;
							}
						}
						else if(arr[i][12].equals("XXXX"))
						{
							for(int j = 0;j<arr.length;j++)
							{
								if( (arr[i][13].equals(arr[j][13])&&arr[j][12].equals("XXX")) || (arr[i][13].equals(arr[j][13])&&arr[j][12].equals("XXXX")) || (arr[i][13].equals(arr[j][13])&&arr[j][12].equals("XXXXX")) )
									System.out.print("");
								else
								{
									System.out.println("You need to build evenly");
									exit = true;
								}
							}
							if(exit==false)
							{
								arr[i][4] = arr[i][4].substring(0,arr[i][4].length()-1);
								money += Integer.parseInt(arr[i][4])/2;
							}
						}
						else if(arr[i][12].equals("XXXXX"))
						{
							for(int j = 0;j<arr.length;j++)
							{
								if( (arr[i][13].equals(arr[j][13])&&arr[j][12].equals("XXXX")) || (arr[i][13].equals(arr[j][13])&&arr[j][12].equals("XXXXX")) )
									System.out.print("");
								else
								{
									System.out.println("You need to build evenly");
									exit = true;
								}
							}
							if(exit==false)
							{
								arr[i][4] = arr[i][4].substring(0,arr[i][4].length()-1);
								money += Integer.parseInt(arr[i][4])/2;
							}
						}
					}
				}
			}
			System.out.println("Would you like to continue?");
			System.out.println("0 to exit or 1 to continue: ");
			z = input.nextInt();
		}
		return money;
	}
	public static void initProp(String[][] temp)
	{
		//0 - Property Name
		//1 - Property Type
		//2 - Owner
		//3 - Price
		//4 - Price Per House
		//5 - Rent
		//6 - Rent 1 House
		//7 - Rent 2 House
		//8 - Rent 3 House
		//9 - Rent 4 House
		//10 - Hotel Rent
		//11 - Mortgaged/Not
		//12 - HouseNumber
		//13 - Property Color
		temp[0][0] = "GO";temp[0][1]  = "GO";temp[0][2] = "10";
		temp[1][0] = "Mediterreanian Ave";temp[1][1]  = "Property";temp[1][2] = "0";temp[1][3] = "60";temp[1][4] = "50";temp[1][5] = "2";temp[1][6] = "10";temp[1][7] = "30";temp[1][8] = "90";temp[1][9] = "160";temp[1][10] = "250";temp[1][11] = "";temp[1][12] = "";temp[1][13] = "Brown";
		temp[2][0] = "Chance 1";temp[2][1]  = "Chance";temp[2][2] = "10";//Comm chest
		temp[3][0] = "Baltic Ave";temp[3][1]  = "Property";temp[3][2] = "0";temp[3][3] = "60";temp[3][4] = "50";temp[3][5] = "4";temp[3][6] = "20";temp[3][7] = "60";temp[3][8] = "180";temp[3][9] = "320";temp[3][10] = "450";temp[3][11] = "";temp[3][12] = "";temp[3][13] = "Brown";
		temp[4][0] = "INCOME TAX";temp[4][1]  = "Tax";temp[4][2] = "10";temp[4][3] = "200";
		temp[5][0] = "Reading Railroad";temp[5][1]  = "Railroad";temp[5][2] = "0";temp[5][3] = "200";
		temp[6][0] = "Oriental Ave";temp[6][1]  = "Property";temp[6][2] = "0";temp[6][3] = "100";temp[6][4] = "50";temp[6][5] = "6";temp[6][6] = "30";temp[6][7] = "90";temp[6][8] = "270";temp[6][9] = "400";temp[6][10] = "550";temp[6][11] = "";temp[6][12] = "";temp[6][13] = "Cyan";
		temp[7][0] = "Chance 2";temp[7][1]  = "Chance";temp[7][2] = "10";
		temp[8][0] = "Vermont Ave";temp[8][1]  = "Property";temp[8][2] = "0";temp[8][3] = "100";temp[8][4] = "50";temp[8][5] = "6";temp[8][6] = "30";temp[8][7] = "90";temp[8][8] = "270";temp[8][9] = "400";temp[8][10] = "550";temp[8][11] = "";temp[8][12] = "";temp[8][13] = "Cyan";
		temp[9][0] = "Connecticut Ave";temp[9][1]  = "Property";temp[9][2] = "0";temp[9][3] = "120";temp[9][4] = "50";temp[9][5] = "8";temp[9][6] = "40";temp[9][7] = "100";temp[9][8] = "300";temp[9][9] = "450";temp[9][10] = "600";temp[9][11] = "";temp[9][12] = "";temp[9][13] = "Cyan";
		temp[10][0] = "Jail";temp[10][1]  = "Jail";temp[10][2] = "10";
		temp[11][0] = "St. Charles Place";temp[11][1]  = "Property";temp[11][2] = "0";temp[11][3] = "140";temp[11][4] = "100";temp[11][5] = "10";temp[11][6] = "50";temp[11][7] = "150";temp[11][8] = "450";temp[11][9] = "625";temp[11][10] = "750";temp[11][11] = "";temp[11][12] = "";temp[11][13] = "Magenta";
		temp[12][0] = "Electic Company";temp[12][1]  = "Utility";temp[12][2] = "0";temp[12][3] = "150";
		temp[13][0] = "States Ave";temp[13][1]  = "Property";temp[13][2] = "0";temp[13][3] = "140";temp[13][4] = "100";temp[13][5] = "10";temp[13][6] = "50";temp[13][7] = "150";temp[13][8] = "450";temp[13][9] = "625";temp[13][10] = "750";temp[13][11] = "";temp[13][12] = "";temp[13][13] = "Magenta";
		temp[14][0] = "Virginia Ave";temp[14][1]  = "Property";temp[14][2] = "0";temp[14][3] = "160";temp[14][4] = "100";temp[14][5] = "12";temp[14][6] = "60";temp[14][7] = "180";temp[14][8] = "500";temp[14][9] = "700";temp[14][10] = "900";temp[14][11] = "";temp[14][12] = "";temp[14][13] = "Magenta";
		temp[15][0] = "Pennsylvania RR";temp[15][1]  = "Railroad";temp[15][2] = "0";temp[15][3] = "200";
		temp[16][0] = "St James Place";temp[16][1] = "Property";temp[16][2] = "0";temp[16][3] = "180";temp[15][4] = "100";temp[16][5] = "14";temp[16][6] = "70";temp[16][7] = "200";temp[16][8] = "550";temp[16][9] = "750";temp[16][10] = "950";temp[16][11] = "";temp[16][12] = "";temp[16][13] = "Orange";
		temp[17][0] = "Chance 3";temp[17][1] = "Chance";temp[17][2] = "10";//comm chest
		temp[18][0] = "Tennessee Ave";temp[18][1] = "Property";temp[18][2] = "0";temp[18][3] = "180";temp[15][4] = "100";temp[18][5] = "14";temp[18][6] = "70";temp[18][7] = "200";temp[18][8] = "550";temp[18][9] = "750";temp[18][10] = "950";temp[18][11] = "";temp[18][12] = "";temp[18][13] = "Orange";
		temp[19][0] = "New York Ave";temp[19][1] = "Property";temp[19][2] = "0";temp[19][3] = "200";temp[19][4] = "100";temp[19][5] = "16";temp[19][6] = "80";temp[19][7] = "220";temp[19][8] = "600";temp[19][9] = "800";temp[19][10] = "1000";temp[19][11] = "";temp[19][12] = "";temp[19][13] = "Orange";
		temp[20][0] = "Free Parking";temp[20][1] = "Parking";temp[20][2] = "10";
		temp[21][0] = "Kentucky Ave";temp[21][1] = "Property";temp[21][2] = "0";temp[21][3] = "220";temp[21][4] = "150";temp[21][5] = "18";temp[21][6] = "90";temp[21][7] = "250";temp[21][8] = "700";temp[21][9] = "875";temp[21][10] = "1050";temp[21][11] = "";temp[21][12] = "";temp[21][13] = "Red";
		temp[22][0] = "Chance 4";temp[22][1] = "Chance";temp[22][2] = "10";
		temp[23][0] = "Indiana Ave";temp[23][1] = "Property";temp[23][2] = "0";temp[23][3] = "220";temp[23][4] = "150";temp[23][5] = "18";temp[23][6] = "90";temp[23][7] = "250";temp[23][8] = "700";temp[23][9] = "875";temp[23][10] = "1050";temp[23][11] = "";temp[23][12] = "";temp[23][13] = "Red";
		temp[24][0] = "Illinois Ave";temp[24][1] = "Property";temp[24][2] = "0";temp[24][3] = "240";temp[24][4] = "150";temp[24][5] = "20";temp[24][6] = "100";temp[24][7] = "300";temp[24][8] = "750";temp[24][9] = "925";temp[24][10] = "1100";temp[24][11] = "";temp[24][12] = "";temp[24][13] = "Red";
		temp[25][0] = "B&O Railroad";temp[25][1] = "Railroad";temp[25][2] = "0";temp[25][3] = "200";
		temp[26][0] = "Atlantic Ave";temp[26][1] = "Property";temp[26][2] = "0";temp[26][3] = "260";temp[26][4] = "150";temp[26][5] = "22";temp[26][6] = "110";temp[26][7] = "330";temp[26][8] = "800";temp[26][9] = "975";temp[26][10] = "1150";temp[26][11] = "";temp[26][12] = "";temp[26][13] = "Yellow";
		temp[27][0] = "Vetnor Ave";temp[27][1] = "Property";temp[27][2] = "0";temp[27][3] = "260";temp[27][4] = "150";temp[27][5] = "22";temp[27][6] = "110";temp[27][7] = "330";temp[27][8] = "800";temp[27][9] = "975";temp[27][10] = "1150";temp[27][11] = "";temp[27][12] = "";temp[27][13] = "Yellow";
		temp[28][0] = "Water Works";temp[28][1] = "Utility";temp[28][2] = "0";temp[28][3] = "150";
		temp[29][0] = "Marvin Gardens";temp[29][1] = "Property";temp[29][2] = "0";temp[29][3] = "280";temp[29][4] = "150";temp[29][5] = "24";temp[29][6] = "120";temp[29][7] = "370";temp[29][8] = "850";temp[29][9] = "1025";temp[29][10] = "1200";temp[29][11] = "";temp[29][12] = "";temp[29][13] = "Yellow";
		temp[30][0] = "GO TO JAIL";temp[30][1] = "Go To Jail";temp[30][2] = "10";
		temp[31][0] = "Pacific Ave";temp[31][1] = "Property";temp[31][2] = "0";temp[31][3] = "300";temp[31][4] = "200";temp[31][5] = "26";temp[31][6] = "130";temp[31][7] = "390";temp[31][8] = "900";temp[31][9] = "1100";temp[31][10] = "1275";temp[31][11] = "";temp[31][12] = "";temp[31][13] = "Green";
		temp[32][0] = "North Carolina Ave";temp[32][1] = "Property";temp[32][2] = "0";temp[32][3] = "300";temp[32][4] = "200";temp[32][5] = "26";temp[32][6] = "130";temp[32][7] = "390";temp[32][8] = "900";temp[32][9] = "1100";temp[32][10] = "1275";temp[32][11] = "";temp[32][12] = "";temp[32][13] = "Green";
		temp[33][0] = "Chance 5";temp[33][1] = "Chance";temp[33][2] = "10";
		temp[34][0] = "Pennsylvania Ave";temp[34][1] = "Property";temp[34][2] = "0";temp[34][3] = "320";temp[34][4] = "200";temp[34][5] = "28";temp[34][6] = "150";temp[34][7] = "450";temp[34][8] = "1000";temp[34][9] = "1200";temp[34][10] = "1400";temp[34][11] = "";temp[34][12] = "";temp[34][13] = "Green";
		temp[35][0] = "Short Line RR";temp[35][1] = "Railroad";temp[35][2] = "0";temp[35][3] = "200";
		temp[36][0] = "Chance 6";temp[36][1] = "Chance";temp[36][2] = "10";
		temp[37][0] = "Park Place";temp[37][1] = "Property";temp[37][2] = "0";temp[37][3] = "350";temp[37][4] = "200";temp[37][5] = "35";temp[37][6] = "175";temp[37][7] = "500";temp[37][8] = "1100";temp[37][9] = "1300";temp[37][10] = "1500";temp[37][11] = "";temp[37][12] = "";temp[37][13] = "Blue";
		temp[38][0] = "LUXURY TAX";temp[38][1] = "Tax";temp[38][2] = "10";temp[38][3] = "75";
		temp[39][0] = "Boardwalk";temp[39][1] = "Property";temp[39][2] = "0";temp[39][3] = "400";temp[39][4] = "200";temp[39][5] = "50";temp[39][6] = "200";temp[39][7] = "600";temp[39][8] = "1400";temp[39][9] = "1700";temp[39][10] = "2000";temp[39][11] = "";temp[39][12] = "";temp[39][13] = "Blue";
	}
	public static void printBoard(String[] x,String[] extra)
	{
		System.out.println("              "+extra[21]+"   "+extra[22]+"   "+extra[23]+"   "+extra[24]+"   "+extra[25]+"   "+extra[26]+"   "+extra[27]+"   "+extra[28]+"   "+extra[29]);
		System.out.println("              "+x[21]+"   "+x[22]+"   "+x[23]+"   "+x[24]+"   "+x[25]+"   "+x[26]+"   "+x[27]+"   "+x[28]+"   "+x[29]);
		System.out.println("  --------------------------------------------------------");
		System.out.println(extra[20]+x[20]+"| 21 | 22 | 23 | 24 | 25 | 26 | 27 | 28 | 29 | 30 | 31 |"+x[30]+extra[30]);
		System.out.println("  --------------------------------------------------------");
		System.out.println(extra[19]+x[19]+"| 20 |                                            | 32 |"+x[31]+extra[31]);
		System.out.println("  --------------------------------------------------------");
		System.out.println(extra[18]+x[18]+"| 19 |                                            | 33 |"+x[32]+extra[32]);
		System.out.println("  --------------------------------------------------------");
		System.out.println(extra[17]+x[17]+"| 18 |                                            | 34 |"+x[33]+extra[33]);
		System.out.println("  --------------------------------------------------------");
		System.out.println(extra[16]+x[16]+"| 17 |                                            | 35 |"+x[34]+extra[34]);
		System.out.println("  --------------------------------------------------------");
		System.out.println(extra[15]+x[15]+"| 16 |                                            | 37 |"+x[35]+extra[35]);
		System.out.println("  --------------------------------------------------------");
		System.out.println(extra[14]+x[14]+"| 15 |                                            | 37 |"+x[36]+extra[36]);
		System.out.println("  --------------------------------------------------------");
		System.out.println(extra[13]+x[13]+"| 14 |                                            | 38 |"+x[37]+extra[37]);
		System.out.println("  --------------------------------------------------------");
		System.out.println(extra[12]+x[12]+"| 13 |                                            | 39 |"+x[38]+extra[38]);
		System.out.println("  --------------------------------------------------------");
		System.out.println(extra[11]+x[11]+"| 12 |                                            | 40 |"+x[39]+extra[39]);
		System.out.println("  --------------------------------------------------------");
		System.out.println(extra[10]+x[10]+"|    11   | 10 | 9 | 8 | 7 | 6 | 5 | 4 | 3 | 2 |   1   |"+x[0]+extra[0]);
		System.out.println("  --------------------------------------------------------");
		System.out.println("               "+x[9]+"   "+x[8]+"   "+x[7]+"   "+x[6]+"   "+x[5]+"   "+x[4]+"   "+x[3]+"   "+x[2]+"   "+x[1]);
		System.out.println("               "+extra[9]+"   "+extra[8]+"   "+extra[7]+"   "+extra[6]+"   "+extra[5]+"   "+extra[4]+"   "+extra[3]+"   "+extra[2]+"   "+extra[1]);

		System.out.println("1.  GO                           ");
		System.out.println("2.  Mediterreanian Ave [brown]   ");
		System.out.println("3.  Chance 1                     ");
		System.out.println("4.  Baltic Ave         [brown]   ");
		System.out.println("5.  INCOME TAX                   ");
		System.out.println("6.  Reading Railroad             ");
		System.out.println("7.  Oriental Ave       [cyan]    ");
		System.out.println("8.  Chance 2                     ");
		System.out.println("9.  Vermont Ave        [cyan]    ");
		System.out.println("10. Connecticut Ave    [cyan]    ");
		System.out.println("11. Jail                         ");
		System.out.println("12. St. Charles Place  [magenta] ");
		System.out.println("13. Electic Company    (Utility) ");
		System.out.println("14. States Ave         [magenta] ");
		System.out.println("15. Virginia Ave       [magenta] ");
		System.out.println("16. Pennsylvania RR              ");
		System.out.println("17. St James Place     [orange]  ");
		System.out.println("18. Chance 3                     ");
		System.out.println("19. Tennessee Ave      [orange]  ");
		System.out.println("20. New York Ave       [orange]  ");
		System.out.println("21. Free Parking                 ");
		System.out.println("22. Kentucky Ave       [red]     ");
		System.out.println("23. Chance 4                     ");
		System.out.println("24. Indiana Ave        [red]     ");
		System.out.println("25. Illinois Ave       [red]     ");
		System.out.println("26. B&O Railroad                 ");
		System.out.println("27. Atlantic Ave       [yellow]  ");
		System.out.println("28. Ventnor Ave        [yellow]  ");
		System.out.println("29. Water Works        (Utility) ");
		System.out.println("30. Marvin Gardens     [yellow]  ");
		System.out.println("31. GO TO JAIL                   ");
		System.out.println("32. Pacific Ave        [green]   ");
		System.out.println("33. North Carolina Ave [green]   ");
		System.out.println("34. Chance 5                     ");
		System.out.println("35. Pennsylvania Ave   [green]   ");
		System.out.println("36. Short Line RR                ");
		System.out.println("37. Chance 6                     ");
		System.out.println("38. Park Place         [blue]    ");
		System.out.println("39. LUXURY TAX                   ");
		System.out.println("40. Boardwalk          [blue]    ");
	}
}