/* * * * * * * * * * *
 * wizard.c
 * 
 * simulates a game where the player can choose one of five options each 
 * turn
 * prints the lowest amount of mana spent required to win in both normal 
 * and hard modes
 * * * * * * * * * * */
#include <stdio.h>
#include <stdlib.h>

int player_turn(int depth, int player, int boss, int shield, int poison, 
		int recharge, int mana, int mana_spent, int difficulty);
int boss_turn(int depth, int player, int boss, int shield, int poison, 
	      int recharge, int mana, int mana_spent, int difficulty);

int main(int argc, char* argv[])
{
	
	printf("Min. normal gold: %d\n", player_turn(0, 50, 51, 0, 0, 0, 
						     500, 0, 0));
	printf("Min. hard gold: %d\n", player_turn(0, 50, 51, 0, 0, 0, 
						   500, 0, 1));

	return 0;
}

/* returns lowest amount of mana spent to win or 10000 if lost
 * iterates over all future options
 * calls boss_turn()
 */
int player_turn(int depth, int player, int boss, int shield, int poison, 
		int recharge, int mana, int mana_spent, int difficulty)
{
	int options[5] = {10000, 10000, 10000, 10000, 10000};
	int min = 10000;
	int i;
	
	depth++;
	if (depth > 20)
	{
	        return 10000;
	}
	if (difficulty)
	{
		player -= 1;
		if (player < 1)
		{
			return 10000;
		}
	}

	/* prelim effects */
	if (shield > 0)
	{
		shield--;
	}
	if (poison > 0)
	{
		poison--;
		boss -= 3;
		if (boss < 1)
		{
			return mana_spent;
		}
	}
	if (recharge > 0)
	{
		recharge--;
		mana += 101;
	}

	/* if mana is depleted */
	if (mana < 53)
	{
		return 10000;
	}
	/* magic missile */
	else
	{
		options[0] = boss_turn(depth, player, boss - 4, shield, 
				       poison, recharge, mana - 53, 
				       mana_spent + 53, difficulty);
	}
	/* drain */
	if (mana >= 73)
	{
		options[1] = boss_turn(depth, player + 2, boss - 2,
				       shield, poison, recharge, mana - 73,
				       mana_spent + 73, difficulty);
	}
	/* shield */
	if (mana >= 113 && shield == 0)
	{
		options[2] = boss_turn(depth, player, boss, 6, poison,
				       recharge, mana - 113, 
				       mana_spent + 113, difficulty);
	}
	/* poison */
	if (mana >= 173 && poison == 0)
	{
		options[3] = boss_turn(depth, player, boss, shield, 6,
				       recharge, mana - 173, 
				       mana_spent + 173, difficulty);
	}
	/* recharge */
	if (mana >= 229 && recharge == 0)
	{
		options[4] = boss_turn(depth, player, boss, shield, 
				       poison, 5, mana - 229, 
				       mana_spent + 229, difficulty);
	}

	
	for (i = 0; i < 5; i++)
	{
		if (options[i] < min)
		{
			min = options[i];
		}
	}

	return min;
}

/* returns lowest mana spent for the player to win, or 10000 if player
 * loses
 * calls player_turn if both boss and player survive
 */ 
int boss_turn(int depth, int player, int boss, int shield, int poison, 
	      int recharge, int mana, int mana_spent, int difficulty)
{
	int damage = 9;
	int armor = 0;
	depth++;

	if (boss < 1)
	{
		return mana_spent;
	}

	/* prelim effects */
	if (shield > 0)
	{
		shield--;
		armor = 7;
	}
	if (poison > 0)
	{
		poison--;
		boss -= 3;
		if (boss < 1)
		{
			return mana_spent;
		}
	}
	if (recharge > 0)
	{
		recharge--;
		mana += 101;
	}


	player -= damage - armor;
	if (player < 1)
	{
		return 10000;
	}
	else
	{
		return player_turn(depth, player, boss, shield, poison,
				   recharge, mana, mana_spent, difficulty);
	}
}
