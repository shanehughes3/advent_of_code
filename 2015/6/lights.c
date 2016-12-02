/* * * * * * * * * * * * *
 * lights.c
 * 
 * simulates a 1000x1000 grid of lights that are turned on and off
 * per instructions in a given file
 * * * * * * * * * * * * */

#include <stdio.h>
#include <string.h>

int main(int argc, char* argv[])
{
	if (argc != 2)
	{
		printf("Usage: ./lights <inputfile.txt>\n");
		return 1;
	}

	FILE* input = fopen(argv[1], "r");
	if (input == NULL)
	{
		printf("Error opening file %s\n", argv[1]);
		return 2;
	}

	int lights[1000][1000];
	char line[50];
	char* word;
	int x1;
	int x2;
	int y1;
	int y2;
	int i, j;
	long total = 0;

	memset(&lights, 0, 1000000 * sizeof(int));

	fseek(input, 0, SEEK_SET);

	while ((fgets(line, 50, input)) != NULL)
	{
		word = strtok(line, " ");
		if (!strcmp(word, "turn"))
		{
			word = strtok(NULL, " ");
			if (!strcmp(word, "on"))
			{
				x1 = atoi(strtok(NULL, ","));
				y1 = atoi(strtok(NULL, " "));
				word = strtok(NULL, " ");
				x2 = atoi(strtok(NULL, ","));
				y2 = atoi(strtok(NULL, "\n"));

				for (i = x1; i < x2 + 1; i++)
				{
					for (j = y1; j < y2 + 1; j++)
					{
						lights[i][j] = 1;
					}
				}
			}
			else if (!strcmp(word, "off"))
			{
				x1 = atoi(strtok(NULL, ","));
				y1 = atoi(strtok(NULL, " "));
				word = strtok(NULL, " ");
				x2 = atoi(strtok(NULL, ","));
				y2 = atoi(strtok(NULL, "\n"));

				for (i = x1; i < x2 + 1; i++)
				{
					for (j = y1; j < y2 + 1; j++)
					{
						lights[i][j] = 0;
					}
				}
			}
		}
		else if (!strcmp(word, "toggle"))
		{
			x1 = atoi(strtok(NULL, ","));
			y1 = atoi(strtok(NULL, " "));
			word = strtok(NULL, " ");
			x2 = atoi(strtok(NULL, ","));
			y2 = atoi(strtok(NULL, "\n"));
			for (i = x1; i < x2 + 1; i++)
			{
				for (j = y1; j < y2 + 1; j++)
				{
					if (lights[i][j] == 0)
						lights[i][j] = 1;
					else if (lights[i][j] == 1)
						lights[i][j] = 0;
				}
			}
		}
	}

	for (i = 0; i < 1000; i++)
	{
		for (j = 0; j < 1000; j++)
		{
			if (lights[i][j] == 1)
				total++;
		}
	}

	printf("Total lights lit: %li\n", total);
	fclose(input);
	return 0;
}
