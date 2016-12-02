/* * * * * * * * * * *
 * santa.c
 * helps santa find some weird address from parentheses
 * * * * * * * * * * */

#include <stdio.h>
#include <stdlib.h>
#include <stdint.h>

int main(int argc, char* argv[])
{
	if (argc != 2)
	{
		printf("Usage: ./santa <directions-file>\n");
		return 1;
	}
	
	char* infile = argv[1];

	FILE* directions = fopen(infile, "r");
	if (directions == NULL)
	{
		printf("Error: could not open %s.\n", infile);
		return 2;
	}
	
	char current_char;
	int current_floor = 0;
	int position = 1;
	int basement_yet = 0;

	while (!feof(directions))
	{
		fread(&current_char, 1, 1, directions);

		if (current_char == '(')
		    current_floor++;
		else if (current_char == ')')
		    current_floor--;	
		
		if (basement_yet == 0)
		{
		    if (current_floor < 0)
		    {
			printf("Position first in basement: %d\n", position);
			basement_yet = 1;
		    }
		}
		position++;
	}
	
	fclose(directions);
	printf("Floor: %d\n", current_floor);

	return 0;

}
