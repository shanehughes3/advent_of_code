/* * * * * * * * * * * *
 * jsonnum_red.c
 *
 * counts all numbers in JSON object except those in an object with a
 * property of "red"
 * * * * * * * * * * * */

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <ctype.h>

void parse_input(char* input_name, char input_array[]);
int inspect_objects(char input[]);

int main(int argc, char* argv[])
{
	char input[40000];

	if (argc != 2)
	{
		printf("Usage: ./jsonnum_red <inputfile.txt>\n");
		return 1;
	}

	parse_input(argv[1], input);

	printf("%d\n", inspect_objects(input));
}

/* parses input txt file and places it into input[] array
 */
void parse_input(char* input_name, char input_array[])
{
	FILE* input = fopen(input_name, "r");
	int n = 0;

	if (!input)
	{
		printf("Error: failed to open file %s\n", input_name);
		exit(2);
	}

	while (!feof(input))
	{
		input_array[n++] = fgetc(input);
	}

	input_array[n] = '\0';
	
	fclose(input);	
}

/* returns the sum of all numbers in root-level object if no "red" property
 * is found in object
 * calls itself to count sum in children
 */
int inspect_objects(char input[])
{
	char substr[40000];
	int total = 0;
	int n = 0;
	int match = 0;
	int start, end, current_num;

	while (input[n] != '\0' && input[n] != '\n')
	{
		if (input[n] == '{')
		{
			if (match == 0)
			{
				start = n;
			}
			match++;
		}
		else if (input[n] == '}')
		{
			match--;
			
			if (match == 0)
			{
				end = n;
				memcpy(substr, &input[start + 1], 
				       end - start);
				substr[end - start - 1] = '\0';
				total += inspect_objects(substr);
			}
		}
		else if (input[n] == 'r')
		{
			if (input[n - 2] == ':' &&
			    input[n + 1] == 'e' &&
			    input[n + 2] == 'd' &&
			    match == 0)
			{
				return 0;
			}
		}
		else if ((isdigit(input[n]) || input[n] == '-') &&
			 match == 0)
		{
			current_num = 0;
			if (input[n] == '-')
			{
				n++;
				while (isdigit(input[n]))
				{
					current_num = (current_num * 10) 
						- (input[n] - '0');
					n++;
				}
			}
			else
			{
				while (isdigit(input[n]))
				{
					current_num = (current_num * 10)
						+ (input[n] - '0');
					n++;
				}
			}
			total += current_num;
			n--;
		}

		n++;
	}

	return total;
}
