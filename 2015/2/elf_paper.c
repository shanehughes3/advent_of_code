/* * * * * * * * * * * *
 * elf_paper.c
 * calculates how much wrapping paper
 * some elves need given a txt file of dimensions 
 * * * * * * * * * * * */

#include <stdio.h>
#include <string.h>

int main(int argc, char* argv[])
{
	if (argc != 2)
	{
		printf("Usage: ./elf_paper <dimensions_file.txt>\n");
		return 1;
	}
	
	char* dimensions_file = argv[1];
	
	FILE* d_file = fopen(dimensions_file, "r");
	
	if (d_file == NULL)
	{
	    printf("Error opening file %s.\n", dimensions_file);
	    return 2;
	}

	int total_footage = 0;
	int total_ribbon = 0;
	int cover;
	int slack;
	int wrap;
	int bow;
	int a;
	int b;
	int c;
	char* a_str;
	char* b_str;
	char* c_str;
	char buffer[21];
	char* bufferptr;
	const char delimiters[] = "x\n\0";
	
	fseek(d_file, 0, SEEK_SET);

	while (!feof(d_file))
	{
	    fgets(buffer, 20, d_file);
	    
	    if (!feof(d_file))
	    {
		bufferptr = buffer;
		a_str = strsep(&bufferptr, delimiters);
		b_str = strsep(&bufferptr, delimiters);
		c_str = strsep(&bufferptr, delimiters);

		a = atoi(a_str);
		b = atoi(b_str);
		c = atoi(c_str);

		cover = (2 * a * b) + (2 * a * c) + (2 * b * c);
		if (a > b && a > c)
		{
		    slack = b * c;
		    wrap = (2 * b) + (2 * c);
		}
		else if (b >= a && b > c)
		{
		    slack = a * c;
		    wrap = (2 * a) + (2 * c);
		}
		else if (c >= a && c >= b)
		{
		    slack = a * b;
		    wrap = (2 * a) + (2 * b);
		}

		bow = a * b * c;
		total_ribbon = total_ribbon + wrap + bow;
		total_footage = total_footage + slack + cover;

	    }
	}
	
	fclose(d_file);
	printf("Total footage: %d\n", total_footage);
	printf("Total ribbon: %d\n", total_ribbon);

	return 0;
}
