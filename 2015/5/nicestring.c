/* * * * * * * * * * * * *
 * nicestring.c
 * 
 * counts how many lines in a file match 
 * several "old" criteria:
 * -contains at least three vowels
 * -contains at least one double letter
 * -does not contain ab, cd, pq, or xy
 * 
 * also counts number of lines matching "new" criteria:
 * -contains a pair of letters that repeats at least once
 * -contains a letter that repeats with exactly one letter between
 * * * * * * * * * * * * */

#include <stdio.h>
#include <string.h>

int vowels(char* line);
int doublechar(char* line);
int except(char* line);
int doublepair(char* line);
int seprep(char* line);

int main(int argc, char* argv[])
{
	if (argc != 2)
	{
		printf("Usage: ./nicestring.c <inputfile.txt>\n");
		return 1;
	}

	FILE* input = fopen(argv[1], "r");
	if (input == NULL)
	{
		printf("Error opening file %s\n", argv[1]);
		return 2;
	}

	char line[17];
	int total_old = 0;
	int total_new = 0;
	
	while(fgets(line, 20, input))
	{
		if (vowels(line) && doublechar(line) && except(line))
		{
			total_old++;
		}
		if (doublepair(line) && seprep(line))
		{
			total_new++;
		}
	}
	
	printf("Total old: %d\n", total_old);
	printf("Total new: %d\n", total_new);
}

// counts total ocurrences of vowels, returns 1 if number is 3 or greater
int vowels(char* line)
{
	int n = 0;
	int i;

        for (i = 0; i < strlen(line); i++)
	{
		if (line[i] == 'a' ||
		    line[i] == 'e' ||
		    line[i] == 'i' ||
		    line[i] == 'o' ||
		    line[i] == 'u')
		{
			n++;
		}
	}

	if (n >= 3)
	{
		return 1;
	}
	else
	{
		return 0;
	}
}

// looks for a pair of repeated characters, returns 1 if found
int doublechar(char* line)
{
	int i;
	for (i = 0; i < strlen(line) - 1; i++)
	{
		if (line[i] == line[i + 1])
			return 1;
	}
	return 0;
}

// looks for any prohibited strings, returns 1 if not found
int except(char* line)
{
	if(strstr(line, "ab") ||
	   strstr(line, "cd") ||
	   strstr(line, "pq") ||
	   strstr(line, "xy"))
	{
		return 0;
	}
	else
		return 1;
}

// looks for a repeated pair of letters, returns 1 if found
int doublepair(char* line)
{
	int i;
	for (i = 0; i < strlen(line) - 1; i++)
	{
		char substr[3];
		sprintf(substr, "%c%c", line[i], line[i + 1]);

		if (strstr(line + i + 2, substr))
		{
			return 1;
		}
	}
	return 0;
}

// looks for a pair of letters with exactly one between, returns 1 if found
int seprep(char* line)
{
	int i;
	for (i = 0; i < strlen(line) - 1; i++)
	{
		if (line[i] == line[i + 2])
			return 1;
	}
	return 0;
}
