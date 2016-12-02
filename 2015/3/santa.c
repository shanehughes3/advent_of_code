/* * * * * * * * * * * * *
 * santa.c
 * a different one
 * 
 * figures out how any houses santa visits
 * given a circuitous route 
 * * * * * * * * * * * * */

#include <stdio.h>
#include <string.h>

int grid[2000][2000];

int main(int argc, char* argv[])
{
	if (argc != 2)
	{
		printf("Usage: ./santa <directions-file.txt>\n");
		return 1;
	}

	FILE* directions = fopen(argv[1], "r");

	if (directions == NULL)
	{
		printf("Unable to open file %s\n", argv[1]);
		return 2;
	}

	fseek(directions, 0, SEEK_SET);


	int* grid_ptr = *grid;
	memset(grid_ptr, 0, sizeof(grid));
	grid [1000][1000] = 1;

	int x = 1000;
	int y = 1000;
	int total_houses = 1;
	char buffer;
	int eof = 0;

	while (eof == 0)
	{
		buffer = fgetc(directions);
		
		if (feof(directions))
		{
			eof = 1;
			break;
		}
		
		switch (buffer)
		{
		case '^':
			y++;
			break;
		case '>':
			x++;
			break;
		case 'v':
			y--;
			break;
		case '<':
			x--;
			break;
		}

		if (x < 0 || y < 0 || x > 2000 || y > 2000)
		{
			printf("Out of bounds: (%d, %d)\n", x, y);
			return 3;
		}
		
		if (grid[x][y] == 0)
		{
			total_houses++;
			grid[x][y] = 1;
		}
	}
	
	fclose(directions);
	printf("Total houses: %d\n", total_houses);

	return 0;	
}
