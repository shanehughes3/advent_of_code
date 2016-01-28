/* * * * * * * * * * * *
 * jsonnum.c
 *
 * finds all numbers in a json document and adds them together
 * * * * * * * * * * * */

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <ctype.h>

long next_num(FILE* input);
long long sub_red(char* buffer);

int main(int argc, char* argv[])
{
	if (argc != 2)
	{
		printf("Usage: /.jsonnum.c <inputfile.txt>\n");
		return 1;
	}

	FILE* input = fopen(argv[1], "r");
	if (!input)
	{
		printf("Error opening file %s\n", argv[1]);
		return 2;
	}

	long long total = 0;
	long long total_red = 0;
	char* buffer = malloc(sizeof(char) * 40000);

	while (!feof(input))
	{
		total += next_num(input);
	}

	printf("Total: %lld\n", total);

	fseek(input, 0, SEEK_SET);
	fgets(buffer, 40000, input);

	total_red = sub_red(buffer);

	printf("Total minus red: %lld\n", total - total_red);

	free(buffer);
	fclose(input);
	return 0;
}

long next_num(FILE* input)
{
	char number[10] = { '\0' };
	char next_char;
	int i = 0;
	do {
		next_char = fgetc(input);
		if (isdigit(next_char))
		{
			number[i++] = next_char;
		}
		else if (next_char == '-' && !number[0])
		{
			number[0] = next_char;
			i++;
		}
		else if (next_char == EOF)
		{
			break;
		}
	} while (isdigit(next_char) || next_char == '-' || !number[0]);

	return strtol(number, NULL, 10);
}

long long sub_red(char* buffer)
{
	char* red;
	red = buffer;
	char* previous;
	previous = red;
	long long total = 0;
	//int l1 = 0;
	//int l2 = 0;
	//int l3 = 0;

	
	while ((red = strstr(red, "red")))
	{
		//printf("Levels: %d %d %d\n", l1++, l2, l3);
		//printf("%p\n", red);
		int i = 0;
		int j = 2;
		int k = 0;
		int l;
		char number[10];
		char test;
		char next_char;
		int count_brace = 0;
		int count_bracket = 0;
		
		// find if "red" is in object or array
		do {
			test = *(red - ++i);
			if (test == '}')
				count_brace++;
			else if (test == '{')
				count_brace--;
			else if (test == ']')
				count_bracket++;
			else if (test == '[')
				count_bracket--;
		} while (count_brace >= 0 && count_bracket >= 0);

		if (test == '[')
		{
			red += j;
			continue;
		}
		// if in object
		else 
		{
			//printf("Levels: %d %d %d\n", l1, l2++, l3);
			count_brace = 0;
			do {
				// find closing brace
				test = *(red + ++j);
				if (test == '{')
					count_brace++;
				else if (test == '}')
					count_brace--;
			} while (count_brace >= 0);
			
			// prevent subtracting children twice
			if (&test < previous)
			{
				i = i - (previous - &test);
			}
			
			previous = &test;

			// i equals distance back to first brace
			// j equals distance forward to closing brace
			k = 0 - i;
			// l equals number array item number
			while (k < j)
			{
				//printf("Levels: %d %d %d\n", l1, l2, l3++);
				l = 0;
				number[0] = '\0';
				do {
					next_char = *(red + k++);
					if (isdigit(next_char))
					{
						number[l++] = next_char;
					}
					else if (next_char == '-' && !number[0])
					{
						number[0] = next_char;
						l++;
					}
				} while (isdigit(next_char) ||
					 next_char == '-' ||
					 !number[0]);
				total += strtol(number, NULL, 10);
				//	printf("Total red: %lld\n", total);

			}
		}
		red += j;
	}

	return total;
}
