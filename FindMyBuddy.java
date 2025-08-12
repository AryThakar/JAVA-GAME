import java.util.*;
class Game
{
	Scanner sc=new Scanner(System.in);
	boolean Play()
	{
		int i=0;
		int o=0;
		int max=3;
		int min=1;
		int rand;
		int r=3;
		for(i=0;i<3;i++)
		{
			rand=(int)(Math.random()*((max-min)+1)+min);
			System.out.println("your remaining chance is: "+(r-i));
			System.out.println("    ***         ***         ***     ");
			System.out.println("   *****       *****       *****    ");
			System.out.println("  *******     *******     *******   ");
			System.out.println("  *******     *******     *******   ");
			System.out.println("  *******     *******     *******   ");
			System.out.println("  *******     *******     *******   ");
			System.out.println(" *********   *********   *********  ");
			System.out.println("*********** *********** *********** ");
			System.out.println("Enter Your Choice");
			int n=sc.nextInt();
			if(rand==1&&n>0&&n<4)
			{
				System.out.println("  _______       ***         ***     ");
		        System.out.println(" /       \"     *****       *****    ");
		        System.out.println(" | *   * |    *******     *******   ");
		        System.out.println(" |   ^   |    *******     *******   ");
		        System.out.println(" | ^^^^^ |    *******     *******   ");
		        System.out.println("  _______/    *******     *******   ");
		        System.out.println("             *********   *********  ");
		        System.out.println("            *********** *********** ");
				if(rand==n)
				{
					return true;
				}
				else if(i<3)
				{
					System.out.println("Next Try");
				}
			}
			else if(rand==2&&n>0&&n<4)
			{
				System.out.println("    ***       _______       ***     ");
				System.out.println("   *****     /       \"     *****    ");
				System.out.println("  *******    | *   * |    *******   ");
				System.out.println("  *******    |   ^   |    *******   ");
				System.out.println("  *******    | ^^^^^ |    *******   ");
				System.out.println("  *******     _______/    *******   ");
				System.out.println(" *********               *********  ");
				System.out.println("***********             *********** ");
				if(rand==n)
				{
					return true;
				}
				else if(i<3)
				{
					System.out.println("Next Try");
				}
			}
			else if(rand==3&&n>0&&n<4)
			{
			    System.out.println("    ***         ***       _______   ");
				System.out.println("   *****       *****     /       \" ");
				System.out.println("  *******     *******    | *   * |  ");
				System.out.println("  *******     *******    |   ^   |  ");
				System.out.println("  *******     *******    | ^^^^^ |  ");
				System.out.println("  *******     *******     _______/  ");
				System.out.println(" *********   *********              ");
				System.out.println("*********** ***********             ");
				if(rand==n)
				{
					return true;
				}
				else if(i<3)
				{
					System.out.println("Next Try");
				}
				
			}
			else
			{
				System.out.println("Enter your Valid Choice");
				i--;
			}
		}
		return false;
	}
	
}
class FindMyBuddy
{
	public static void main(String[]args)
	{
		Scanner sc=new Scanner(System.in);
		System.out.println("=============================");
		System.out.println(" ----------Welcome---------- ");
		System.out.println("=============================");
		System.out.println();
		System.out.println();
		System.out.println();
		System.out.println();
		System.out.println("=============================");
		System.out.println("*****Find The Buddy Game*****");
		System.out.println("Hello");
		System.out.println("My Name is Buddy.");
		System.out.println("You Can Call Me Buddy.");
		System.out.println("You Are My Best Friend ?");
		System.out.println("Lets Find Me.");
		System.out.println("=============================");
		System.out.println();
		System.out.println();
		System.out.println();
		System.out.println();
		System.out.println("Your Choice Between 1to3");
		System.out.println("You have only three chance");
		
		Game x=new Game();
		boolean b=x.Play();
		if(b)
		{
			System.out.println("You Won");
		}
		else
		{
			System.out.println("you loose");
		}
	}
}


