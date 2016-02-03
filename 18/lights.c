/* * * * * * * * * * * *
 * lights.c
 * 
 * implements a game of life algorithm given a specified input of 
 * christmas lights in a 100x100 grid for a given number of steps
 * 
 * prints the number of live points after given steps for a standard 
 * system as well as a system where the corners are always live
 * * * * * * * * * * * */

#include <stdio.h>
#include <stdlib.h>
#include <string.h>

void parse_input(int lights_current[][100], char* input_name);
void next_step(int lights_current[][100]);
int next_state(int lights_current[][100], int i, int j);
int count_live(int lights_current[][100]);
void set_corners(int lights_current[][100]);

int main(int argc, char* argv[])
{
	int steps, i;
	int lights_current[100][100];

	if (argc != 3)
	{
		printf("Usage: ./lights.c <input.txt> <steps>\n");
		return 1;
	}

	steps = atoi(argv[2]);
	
	/* standard system */
	parse_input(lights_current, argv[1]);

	for (i = 0; i < steps; i++)
	{
		next_step(lights_current);
	}

	printf("Total: %d\n", count_live(lights_current));

	/* corners always live */
	parse_input(lights_current, argv[1]);
	set_corners(lights_current);

	for (i = 0; i < steps; i++)
	{
		next_step(lights_current);
		set_corners(lights_current);
	}

	printf("Total with corners on: %d\n", count_live(lights_current));

	return 0;
}

/* populates 100x100 array with seed data from file 
 */
void parse_input(int lights_current[][100], char* input_name)
{
	FILE* input = fopen(input_name, "r");
	char line[110];
	int i;
	int n = 0;

	if (!input)
	{
		printf("Error: failed to open file %s\n", input_name);
		exit(2);
	}

	while (fgets(line, 110, input))
	{
	        if (n > 99)
		{
			printf("Error: problem reading file\n");
			exit(3);
		}
		for (i = 0; i < 100; i++)
		{
			if (line[i] == '#')
			{
				lights_current[n][i] = 1;
			}
			else if (line[i] == '.')
			{
				lights_current[n][i] = 0;
			}
		}
		n++;
	}

	fclose(input);
}

/* updates array to next step
 * calculation takes place on a disposable array - all changes in step
 * take place simultaneously
 */
void next_step(int lights_current[][100])
{
	int lights_next[100][100];
	int i, j;

	for (i = 0; i < 100; i++)
	{
		for (j = 0; j < 100; j++)
		{
			lights_next[i][j] = next_state(lights_current, i, j);
		}
	}

	memcpy(lights_current, lights_next, sizeof(int) * 100 * 100);
}

/* returns the next state of the given point
 */
int next_state(int lights_current[][100], int i, int j)
{
	int adjacent = 0;

	if (i < 99)
	{
		adjacent += lights_current[i + 1][j];
	}
	if (j < 99)
	{
		adjacent += lights_current[i][j + 1];
	}
	if (i > 0)
	{
		adjacent += lights_current[i - 1][j];
	}
	if (j > 0)
	{
		adjacent += lights_current[i][j - 1];
	}
	if (i < 99 && j < 99)
	{
		adjacent += lights_current[i + 1][j + 1];
	}
	if (i > 0 && j > 0)
	{
		adjacent += lights_current[i - 1][j - 1];
	}
	if (i < 99 && j > 0)
	{
		adjacent += lights_current[i + 1][j - 1];
	}
	if (i > 0 && j < 99)
	{
		adjacent += lights_current[i - 1][j + 1];
	}

	if (adjacent == 3 || (lights_current[i][j] && adjacent == 2))
	{
		return 1;
	}
        else
	{
		return 0;
	}
}

/* returns the number of live points
 */
int count_live(int lights_current[][100])
{
	int i, j;
	int n = 0;

	for (i = 0; i < 100; i++)
	{
		for (j = 0; j < 100; j++)
		{
			n += lights_current[i][j];
		}
	}
	
	return n;
}

/* sets all four corners to live
 */
void set_corners(int lights_current[][100])
{
	lights_current[0][0] = 1;
	lights_current[0][99] = 1;
	lights_current[99][0] = 1;
	lights_current[99][99] = 1;
}

