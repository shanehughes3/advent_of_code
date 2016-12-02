/* * * * * * * * * * * * *
 * rpg.c
 * 
 * finds the optimum equipment purchase in a video game that will defeat
 * a boss with given characteristics
 * * * * * * * * * * * * */

#include <stdio.h>

int find_min(int weapons[][3], int armor[][3], int rings[][3]);
int battle_sim(int weapons[][3], int armor[][3], int rings[][3], int i,
	       int j, int k, int l);
int count_gold(int weapons[][3], int armor[][3], int rings[][3], int i,
	       int j, int k, int l);

int main(int argc, char* argv[])
{
	int weapons[][3] = {{8, 4, 0},
			    {10, 5, 0},
			    {25, 6, 0},
			    {40, 7, 0},
			    {74, 8, 0}};
	int armor[][3] = {{13, 0, 1},
			  {31, 0, 2},
			  {53, 0, 3},
			  {75, 0, 4},
			  {102, 0, 5}};
	int rings[][3] = {{25, 1, 0},
			  {50, 2, 0},
			  {100, 3, 0},
			  {20, 0, 1},
			  {40, 0, 2},
			  {80, 0, 3}};

	printf("Min gold: %d\n", find_min(weapons, armor, rings));


	return 0;
}

int find_min(int weapons[][3], int armor[][3], int rings[][3])
{
	int i, j, k, l, n;
	int min = 1000;
	int max = 0;

	for (i = 0; i < 5; i++)
	{
		for (j = -1; j < 5; j++)
		{
			for (k = -1; k < 6; k++)
			{
				for (l = -1; l < 6; l++)
				{
					if ((k != l || (k < 0 && l < 0))
					    && battle_sim(weapons,
							  armor, 
							  rings,
							  i, j, k, l))
					{
						n = count_gold(weapons,
							       armor,
							       rings,
							       i, j, k, l); 
						min = (n < min) ? n : min;
					}
					else if (k != l || (k < 0 && l < 0))
					{
						n = count_gold(weapons,
							       armor,
							       rings,
							       i, j, k, l);
						max = (n > max) ? n : max;
					}
				}
			}
		}
	}

	printf("Max gold: %d\n", max);
	return min;
}

int battle_sim(int weapons[][3], int armor[][3], int rings[][3], int i,
	       int j, int k, int l)
{
	int player = 100;
	int boss = 103;
	int p_damage, p_armor;
	int b_damage = 9;
	int b_armor = 2;
	int p_deals, b_deals;

	p_damage = weapons[i][1];
	if (k >= 0 && k < 3)
	{
		p_damage += rings[k][1];
	}
	if (l >= 0 && l < 3)
	{
		p_damage += rings[l][1];
	}

	p_armor = 0;
	if (j >= 0)
	{
		p_armor += armor[j][2];
	}
	if (k >= 3)
	{
		p_armor += rings[k][2];
	}
	if (l >= 3)
	{
		p_armor += rings[l][2];
	}

	p_deals = p_damage - b_armor;
	b_deals = b_damage - p_armor;
	p_deals = (p_deals < 1) ? 1 : p_deals;

	b_deals = (b_deals < 1) ? 1 : b_deals;

	while (player > 0)
	{
		boss -= p_deals;
		if (boss < 1)
		{
			return 1;
		}
		player -= b_deals;
	}

	return 0;
}

int count_gold(int weapons[][3], int armor[][3], int rings[][3], int i,
	       int j, int k, int l)
{
	int gold = 0;

	gold += weapons[i][0];
	if (j >= 0)
	{
		gold += armor[j][0];
	}
	if (k >= 0)
	{
		gold += rings[k][0];
	}
	if (l >= 0)
	{
		gold += rings[l][0];
	}

	return gold;
}
