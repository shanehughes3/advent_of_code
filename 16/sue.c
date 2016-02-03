/* * * * * * * * * * * * *
 * sue.c
 *
 * Finds a particular aunt sue from a list of 500 given certain
 * parameters about her
 * real_sue uses updated logic based on the same input numbers
 * * * * * * * * * * * * */

#include <stdio.h>
#include <stdlib.h>
#include <string.h>

void parse_input(int sue_list[][10], char* input_name);
int get_item(char* item);
void find_sue(int sue_list[][10], int target_sue[]);
int check_real_sue(int sue_list[][10], int target_sue[], int i);

int main(int argc, char* argv[])
{
	int sue_list[501][10];
	int target_sue[10] = {3, 7, 2, 3, 0, 0, 5, 3, 2, 1};
	
	if (argc != 2)
	{
		printf("Usage: ./sue <inputfile.txt>\n");
		return 1;
	}

	memset(sue_list, -1, sizeof(sue_list));

	parse_input(sue_list, argv[1]);

	find_sue(sue_list, target_sue);	
	
	return 0;
	
}

void parse_input(int sue_list[][10], char* input_name)
{
	char line[150];
	char* item;
	int sue, quantity;
	
	FILE* input = fopen(input_name, "r");
	
	if (!input)
	{
		printf("Error opening file %s\n", input_name);
		exit(2);
	}
	
	while(fgets(line, 150, input))
	{
		strtok(line, " ");
		sue = atoi(strtok(NULL, ":"));
		while((item = strtok(NULL, ": ")))
		{
			quantity = atoi(strtok(NULL, " ,"));
			sue_list[sue][get_item(item)] = quantity;
		}
	}
	
	fclose(input);
}

int get_item(char* item)
{
	if (!strcmp(item, "children"))
		return 0;
	else if (!strcmp(item, "cats"))
		return 1;
	else if (!strcmp(item, "samoyeds"))
		return 2;
	else if (!strcmp(item, "pomeranians"))
		return 3;
	else if (!strcmp(item, "akitas"))
		return 4;
	else if (!strcmp(item, "vizslas"))
		return 5;
	else if (!strcmp(item, "goldfish"))
		return 6;
	else if (!strcmp(item, "trees"))
		return 7;
	else if (!strcmp(item, "cars"))
		return 8;
	else if (!strcmp(item, "perfumes"))
		return 9;
	else
	{
		printf("Error: invalid item name %s\n", item);
		exit(3);
	}
}


/* iterates through list of sues, checking what parameters are given
 * 
 * prints any and all matches */

void find_sue(int sue_list[][10], int target_sue[])
{
	int i, j;

	for (i = 1; i < 501; i++)
	{
		for (j = 0; j < 10; j++)
		{
			if (sue_list[i][j] != -1 && 
			    sue_list[i][j] != target_sue[j])
			{
				break;
			}
			if (j == 9)
			{
				printf("Sue %d\n", i);
			}
		}
		if(check_real_sue(sue_list, target_sue, i))
		{
			printf("Real Sue %d\n", i);
		}
	}
}

/* returns whether specified sue matches updated given parameters
 */

int check_real_sue(int sue_list[][10], int target_sue[], int i)
{
	if ((sue_list[i][0] != -1 && sue_list[i][0] != target_sue[0]) ||
	    (sue_list[i][1] != -1 && sue_list[i][1] <= target_sue[1]) ||
	    (sue_list[i][2] != -1 && sue_list[i][2] != target_sue[2]) ||
	    (sue_list[i][3] != -1 && sue_list[i][3] >= target_sue[3]) ||
	    (sue_list[i][4] != -1 && sue_list[i][4] != target_sue[4]) ||
	    (sue_list[i][5] != -1 && sue_list[i][5] != target_sue[5]) ||
	    (sue_list[i][6] != -1 && sue_list[i][6] >= target_sue[6]) ||
	    (sue_list[i][7] != -1 && sue_list[i][7] <= target_sue[7]) ||
	    (sue_list[i][8] != -1 && sue_list[i][8] != target_sue[8]) ||
	    (sue_list[i][9] != -1 && sue_list[i][9] != target_sue[9]))
	{
		return 0;
	}
	else
	{
		return 1;
	}
}
