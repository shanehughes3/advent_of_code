/* * * * * * * * * * * * *
 * list.c
 *
 * determines how many characters are in a file before and after
 * escaping certain characters
 * * * * * * * * * * * * */

#include <stdio.h>

int main(int argc, char* argv[])
{
	if (argc != 2)
	{
		printf("Usage: ./list <inputfile.txt>\n");
		return 1;
	}

	FILE* input = fopen(argv[1], "r");
	if (!input)
	{
		printf("Error opening file %s\n", argv[1]);
	}

	int total_literal = 0;
	int total_memory = 0;
	int total_encode = 0;
	char buffer;
	
	while((buffer = fgetc(input)) != EOF)
	{
		total_literal++;
		
		if (buffer == '\\')
		{
			buffer = fgetc(input);
			total_literal++;

			if (buffer == '\\')
			{
				// \\ in literal
				total_memory++;
				total_encode += 4;
			}
			else if (buffer == '"')
			{
				// \" in literal
				total_memory++;
				total_encode += 4;
			}
			else if (buffer == 'x')
			{
				// \xff (e.g.) in literal
				buffer = fgetc(input);
				total_literal++;
				buffer = fgetc(input);
				total_literal++;
				total_memory++;
				total_encode += 5;
			}

		}
		else if (buffer == '"')
		{
			// " in literal - start or end of line
			total_encode += 3;
		}
		else if (buffer == '\n' || buffer == '\r')
		{
			total_literal--;
		}
		else
		{
			total_memory++;
			total_encode++;
		}
	}

	printf("\nTotal literal: %d\n", total_literal);
	printf("Total in memory: %d\n", total_memory);
	printf("Difference: %d\n\n", total_literal - total_memory);
	printf("Total encoding: %d\n", total_encode);
	printf("Difference: %d\n\n", total_encode - total_literal);

	fclose(input);
	return 0;
}
