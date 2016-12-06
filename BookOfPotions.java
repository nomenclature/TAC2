import java.io.*;
import java.util.Scanner;

public class BookOfPotions
{
	private IView view, headerView;
	private Player player;

	//Scanner input = new Scanner(System.in);

	public BookOfPotions (Player player)
	{
		this.player = player;
		view = new View();
		headerView = new HeaderView("POTIONS: ");
	}

	public void listPotionIngredients(String questHandle)
	{
		if(questHandle.equals("health"))
		{
			headerView.display("Ingredients required: RubyDragon, Beer, Berries\n");
			headerView.display("Does user wish to continue?\n");

		}
	}

	public void invoke() 
	{
		
		{
			view.display( "\n");
			headerView.display("Type in potion name to continue\n");

			headerView.display("Potion of Lyme Disease Resistance\n");
			headerView.display("Potion of Health\n");
			headerView.display("Potion of Irish Luck\n");
			
			String input = view.readLine().trim().toLowerCase();
			if(input.equals("potion of health"))
			{
				headerView.display("Heal your Lyme Disease for 10 quests\n");
				listPotionIngredients(input);
			}
			else if(input.equals("potion of lyme disease resistance"))
			{
				System.out.println("Vaccine time");
			}
			else if(input.equals("potion of irish luck"))
			{
				System.out.println("Health time");
				
			}
			else if(input.equals("leave"))
			{
				System.out.println("peace");
				
			}

		
		}
	}
}