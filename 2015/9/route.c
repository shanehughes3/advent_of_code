/* * * * * * * * * * *
 * route.c
 * 
 * finds the shortest total route given distances between
 * points on the route
 * * * * * * * * * * */

#include <stdio.h>
#include <string.h>

int get_place_num(char* place);

int main(int argc, char* argv[])
{
	if (argc != 2)
	{
		printf("Usage: ./route <inputfile.txt>\n");
		return 1;
	}

	FILE* input = fopen(argv[1], "r");
	if (!input)
	{
		printf("Error opening file %s\n", argv[1]);
		return 2;
	}

	char line[50];
	char* word;
	int x, y, dist;
	int distances[8][8];

	while (fgets(line, 50, input))
	{
		word = strtok(line, " ");
		x = get_place_num(word);
		word = strtok(NULL, " ");
		word = strtok(NULL, " ");
		y = get_place_num(word);
		word = strtok(NULL, " ");
		word = strtok(NULL, "\n");
		dist = atoi(word);

		distances[x][y] = dist;
		distances[y][x] = dist;
	}

	int total_dist;
	int min_dist = 32767;
	int max_dist = 0;
	int i, j, k, l, m, n, o, p;

	for (i = 0; i < 8; i++)
	{
		for (j = 0; j < 8; j++)
		{
			if (j != i){
			for (k = 0; k < 8; k++)
			{
				if (k != i && k != j){
				for (l = 0; l < 8; l++)
				{
					if (l != i && l != j && l != k){
					for (m = 0; m < 8; m++)
					{
						if (m != i && m != j && m != k && m != l){
						for (n = 0; n < 8; n++)
						{
							if (n != i && n != j && n != k &&
							    n != l && n != m){
							for (o = 0; o < 8; o++)
							{
								if (o != i && o != j && o != k &&
								    o != l && o != m && o != n){
								for (p = 0; p < 8; p++)
								{
									if (p != i && p != j &&
									    p != k && p != l &&
									    p != m && p != n &&
									    p != o)
									{
										total_dist =
											distances[i][j] +
											distances[j][k] +
											distances[k][l] +
											distances[l][m] +
											distances[m][n] +
											distances[n][o] +
											distances[o][p];
										if (total_dist < min_dist)
											min_dist = total_dist;
										if (total_dist > max_dist)
											max_dist = total_dist;
									}
								}}
							}}
						}}
					}}
				}}
			}}
		}
	}

	printf("Min distance: %d\n", min_dist);
	printf("Max distance: %d\n", max_dist);
	fclose(input);
	return 0;
}

int get_place_num(char* place)
{
	if (!strcmp(place, "Tristram"))
		return 0;
	else if (!strcmp(place, "AlphaCentauri"))
		return 1;
	else if (!strcmp(place, "Snowdin"))
		return 2;
	else if (!strcmp(place, "Tambi"))
		return 3;
	else if (!strcmp(place, "Faerun"))
		return 4;
	else if (!strcmp(place, "Norrath"))
		return 5;
	else if (!strcmp(place, "Straylight"))
		return 6;
	else if (!strcmp(place, "Arbre"))
		return 7;
	else
	{
		printf("Invalid place: %s\n", place);
		return 8;
	}
}
