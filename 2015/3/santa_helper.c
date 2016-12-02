/* * * * * * * * * * * * *
 * santa_helper.c
 * a different one
 * 
 * figures out how any houses santa visits
 * given a circuitous route 
 * and now a helper!
 * * * * * * * * * * * * */

#include <stdio.h>
#include <string.h>

int grid[2000][2000];

typedef struct
{
    int x;
    int y;
} coordinates;

coordinates move(coordinates person, char buffer);
int check_bounds(coordinates person, char *name);

int main(int argc, char* argv[])
{
	if (argc != 2)
	{
		printf("Usage: ./santa_helper <directions-file.txt>\n");
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

	coordinates santa;
	santa.x = 1000, santa.y = 1000;
	coordinates helper;
	helper.x = 1000, helper.y = 1000;
	int total_houses = 1;
	char buffer_s;
	char buffer_h;
	int eof = 0;

	while (eof == 0)
	{
		buffer_s = fgetc(directions);
		buffer_h = fgetc(directions);
		
		if (feof(directions))
		{
			eof = 1;
			break;
		}
		
		santa = move(santa, buffer_s);
		helper = move(helper, buffer_h);
		
		if (check_bounds(santa, "Santa") ||
		    check_bounds(helper, "Helper"))
		{
		    return 3;
		}

		
		if (grid[santa.x][santa.y] == 0)
		{
			total_houses++;
			grid[santa.x][santa.y] = 1;
		}

		if (grid[helper.x][helper.y] == 0)
		{
		    total_houses++;
		    grid[helper.x][helper.y] = 1;
		}
	}
	
	fclose(directions);
	printf("Total houses: %d\n", total_houses);

	return 0;	
}

coordinates move(coordinates person, char buffer)
{
       	switch (buffer)
	{
	case '^':
	    person.y++;
	    return person;
	case '>':
	    person.x++;
	    return person;
	case 'v':
	    person.y--;
	    return person;
	case '<':
	    person.x--;
	    return person;
	}
}

int check_bounds(coordinates person, char* name)
{
       	if (person.x < 0 || person.y < 0 || 
	    person.x > 2000 || person.y > 2000)
	{
		printf("%s out of bounds: (%d, %d)\n",
		       name, person.x, person.y);
		return 1;
	}
	return 0;
}
