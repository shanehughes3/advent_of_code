/* * * * * * * * * *
 * sleigh.c
 * 
 * finds the "quantum entanglement" (product of the weights) of presents in
 * an ideal configuration for santa's sleigh
 * * * * * * * * * */

#include <stdio.h>
#include <stdlib.h>

struct bag_option
{
	int weight;
	int number;
	long long q_ent;
};

void parse_input(int presents[28], char* input_name);
int find_weight(int presents[28], int bags);
struct bag_option load_present(int depth, int presents[], int target_weight, 
			       struct bag_option current);

int main(int argc, char* argv[])
{
	int presents[28];
	struct bag_option bag = { 0, 0, 1 };
	int target_weight_3, target_weight_4;

	if (argc != 2)
	{
		printf("Usage: ./sleigh <input.txt>\n");
		return 1;
	}

	parse_input(presents, argv[1]);
	target_weight_3 = find_weight(presents, 3);
	struct bag_option optimum_3 = load_present(0, presents, 
						   target_weight_3, bag);
	target_weight_4 = find_weight(presents, 4);
	struct bag_option optimum_4 = load_present(0, presents, 
						   target_weight_4, bag);

	printf("%llu, %llu\n", optimum_3.q_ent, optimum_4.q_ent);
	
	return 0;
}

void parse_input(int presents[28], char* input_name)
{
	FILE* input = fopen(input_name, "r");
	int n = 0;
	char line[10];

	if (!input)
	{
		printf("Error: could not open file %s\n", input_name);
		exit(2);
	}

	while (fgets(line, 10, input))
	{
		presents[n] = atoi(line);
		n++;
	}

	fclose(input);
}

int find_weight(int presents[28], int bags)
{
	int i;
	int total = 0;
	
	for (i = 0; i < 28; i++)
	{
		total += presents[i];
	}

	return total / bags;
}

struct bag_option load_present(int depth, int presents[], int target_weight, 
			       struct bag_option current)
{
	struct bag_option new;
	struct bag_option best[2];

	if (depth == 28)
	{
		
		if (current.weight == target_weight)
		{
			return current;
		}
		else
		{
			new.number = 999;
			new.q_ent = 1101;
			return new;
		}
	}

	best[0] = load_present(depth + 1, presents, 
			       target_weight, current);

	new = current;
	new.weight += presents[27 - depth];
	if (new.weight > target_weight)
	{
		best[1].number = 997;
		best[1].q_ent = 998;
	}
	else
	{
		new.number++;
		new.q_ent *= presents[27 - depth];
		best[1] = load_present(depth + 1, presents,
				       target_weight, new);
	}

	
        if (best[0].number < best[1].number ||
	    (best[0].number == best[1].number &&
	    best[0].q_ent < best[1].q_ent))
	{
		return best[0];
	}
	else
	{
		return best[1];
	}
}

