/* * * * * * * * * * * * * *
 * olympics.c
 *
 * calculates the winning reindeer after a race of given duration
 * based upon the speeds in an input file
 * * * * * * * * * * * * * */

#include <stdio.h>
#include <stdlib.h>
#include <string.h>

void parse_file(char* input_file, int speed_table[][3]);
int get_name(char* name);
int calculate_deer(int deer, int duration, int speed_table[][3]);
int calculate_points(int duration, int speed_table[][3]);

int main(int argc, char* argv[])
{
	int speed_table[9][3];
	int duration, n, max, distance;
	max = 0;
	
	if (argc != 3)
	{
		printf("Usage: ./olympics <inputfile.txt> <race duration>\n");
		return 1;
	}

	duration = atoi(argv[2]);

	parse_file(argv[1], speed_table);

	for (n = 0; n < 9; n++)
	{
		distance = calculate_deer(n, duration, speed_table);
		max = (distance > max) ? distance : max;
	}
	
	printf("Max distance: %d\n", max);
	printf("Max points: %d\n", calculate_points(duration, speed_table));

	return 0;
}

/* parses a file containing deer speed info in the format:
 * "<deer> can fly <speed> km/s for <fly> seconds, but
 * then must rest for <rest> seconds." */
void parse_file(char* input_file, int speed_table[][3])
{
	FILE* input = fopen(input_file, "r");
	char line[100];
	int deer, speed, fly, rest, n;

	if (!input)
	{
		printf("Error opening file %s\n", input_file);
		exit(2);
	}

	while (fgets(line, 100, input))
	{
		deer = get_name(strtok(line, " "));
		strtok(NULL, " ");
		strtok(NULL, " ");
		speed = atoi(strtok(NULL, " "));
		strtok(NULL, " ");
		strtok(NULL, " ");
		fly = atoi(strtok(NULL, " "));
		for (n = 0; n < 6; n++)
		{
			strtok(NULL, " ");
		}
		rest = atoi(strtok(NULL, " "));

		speed_table[deer][0] = speed;
		speed_table[deer][1] = fly;
		speed_table[deer][2] = rest;
	}
	
	fclose(input);
}

/* returns the number of the deer given a string */
int get_name(char* name)
{
	if (!strcmp(name, "Rudolph"))
		return 0;
	if (!strcmp(name, "Cupid"))
		return 1;
	if (!strcmp(name, "Prancer"))
		return 2;
	if (!strcmp(name, "Donner"))
		return 3;
	if (!strcmp(name, "Dasher"))
		return 4;
	if (!strcmp(name, "Comet"))
		return 5;
	if (!strcmp(name, "Blitzen"))
		return 6;
	if (!strcmp(name, "Vixen"))
		return 7;
	if (!strcmp(name, "Dancer"))
		return 8;
	else
	{
		printf("Invalid reindeer name %s\n", name);
		exit(3);
	}
}

/* returns the distance a given deer travels in a given duration */
int calculate_deer(int deer, int duration, int speed_table[][3])
{
	int total, iterations, period;
	
	period = speed_table[deer][1] + speed_table[deer][2];

	iterations = duration / period;
	total = iterations * speed_table[deer][0] * speed_table[deer][1];

	if (iterations > 0)
	{
		if (duration % period < speed_table[deer][1])	
		{
			total += speed_table[deer][0] * 
				(duration % period);
		}
		else
		{
			total += speed_table[deer][0] * 
				speed_table[deer][1];
		}
	}
	else 
	{
		if (duration > speed_table[deer][1])
		{
			total += speed_table[deer][0] * speed_table[deer][1];
		}
		else
		{
			total += speed_table[deer][0] * duration;
		}
	}

	return total;
}

/* returns the number of the deer who accumulates the most points
 * in the given duration */
int calculate_points(int duration, int speed_table[][3])
{
	int points[9] = { 0 };
	int dist[9];
	int i, j, k, max;

	for (i = 1; i < duration + 1; i++)
	{
		max = 0;
		for (j = 0; j < 9; j++)
		{
			dist[j] = calculate_deer(j, i, speed_table);
			
			if (dist[j] > max)
			{
				max = dist[j];
			}
		}

		for (k = 0; k < 9; k++)
		{
			if (dist[k] == max)
			{
				points[k] += 1;
			}
		}
	}
	
	max = 0;
	for (j = 0; j < 9; j++)
	{
		if (points[j] > max)
		{
			max = points[j];
		}			
	}
	return max;
}

